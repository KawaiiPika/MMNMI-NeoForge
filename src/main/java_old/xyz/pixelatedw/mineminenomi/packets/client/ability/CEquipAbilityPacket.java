package xyz.pixelatedw.mineminenomi.packets.client.ability;

import java.util.function.Supplier;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.registries.IForgeRegistry;
import xyz.pixelatedw.mineminenomi.api.WyRegistry;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponentKey;
import xyz.pixelatedw.mineminenomi.config.ServerConfig;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityCapability;
import xyz.pixelatedw.mineminenomi.data.entity.ability.IAbilityData;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.packets.server.ability.SEquipAbilityPacket;

public class CEquipAbilityPacket {
   private int slot;
   private ResourceLocation abilityId;

   public CEquipAbilityPacket() {
   }

   public CEquipAbilityPacket(int id, AbilityCore<?> core) {
      this.slot = id;
      this.abilityId = core.getRegistryKey();
   }

   public void encode(FriendlyByteBuf buffer) {
      buffer.writeInt(this.slot);
      buffer.m_130085_(this.abilityId);
   }

   public static CEquipAbilityPacket decode(FriendlyByteBuf buffer) {
      CEquipAbilityPacket msg = new CEquipAbilityPacket();
      msg.slot = buffer.readInt();
      msg.abilityId = buffer.m_130281_();
      return msg;
   }

   public static void handle(CEquipAbilityPacket message, Supplier<NetworkEvent.Context> ctx) {
      if (((NetworkEvent.Context)ctx.get()).getDirection() == NetworkDirection.PLAY_TO_SERVER) {
         ((NetworkEvent.Context)ctx.get()).enqueueWork(() -> {
            try {
               ServerPlayer player = ((NetworkEvent.Context)ctx.get()).getSender();
               if (player != null) {
                  int maxBars = ServerConfig.getAbilityBars() * 8;
                  if (message.slot <= maxBars) {
                     IAbilityData abilityData = (IAbilityData)AbilityCapability.get(player).orElse((Object)null);
                     if (abilityData != null) {
                        AbilityCore<?> core = (AbilityCore)((IForgeRegistry)WyRegistry.ABILITIES.get()).getValue(message.abilityId);
                        if (core != null) {
                           if (abilityData.hasUnlockedAbility(core)) {
                              IAbility oldAbility = abilityData.getEquippedAbility(message.slot);
                              if (oldAbility != null) {
                                 boolean isOnCooldown = (Boolean)oldAbility.getComponent((AbilityComponentKey)ModAbilityComponents.COOLDOWN.get()).map((comp) -> comp.isOnCooldown()).orElse(false);
                                 if (isOnCooldown) {
                                    return;
                                 }

                                 boolean isDisabled = (Boolean)oldAbility.getComponent((AbilityComponentKey)ModAbilityComponents.DISABLE.get()).map((comp) -> comp.isDisabled()).orElse(false);
                                 if (isDisabled) {
                                    return;
                                 }

                                 boolean isOnContinuity = (Boolean)oldAbility.getComponent((AbilityComponentKey)ModAbilityComponents.CONTINUOUS.get()).map((comp) -> comp.isContinuous()).orElse(false);
                                 if (isOnContinuity) {
                                    return;
                                 }

                                 boolean isCharging = (Boolean)oldAbility.getComponent((AbilityComponentKey)ModAbilityComponents.CHARGE.get()).map((comp) -> comp.isCharging()).orElse(false);
                                 if (isCharging) {
                                    return;
                                 }

                                 boolean hasStackDifference = (Boolean)oldAbility.getComponent((AbilityComponentKey)ModAbilityComponents.STACK.get()).map((comp) -> comp.getDefaultStacks() > 0 && comp.getStacks() != comp.getDefaultStacks()).orElse(false);
                                 if (hasStackDifference) {
                                    return;
                                 }
                              }

                              IAbility ability = core.createAbility();
                              abilityData.setEquippedAbility(message.slot, ability);
                              ModNetwork.sendToAllTrackingAndSelf(new SEquipAbilityPacket(player.m_19879_(), message.slot, core), player);
                           }
                        }
                     }
                  }
               }
            } catch (Exception e) {
               e.printStackTrace();
            }
         });
      }

      ((NetworkEvent.Context)ctx.get()).setPacketHandled(true);
   }
}
