package xyz.pixelatedw.mineminenomi.packets.client.ability;

import java.util.function.Supplier;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponentKey;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityCapability;
import xyz.pixelatedw.mineminenomi.data.entity.ability.IAbilityData;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.packets.server.ability.SRemoveAbilityPacket;
import xyz.pixelatedw.mineminenomi.packets.server.ability.SUpdateEquippedAbilityPacket;

public class CRemoveAbilityPacket {
   private int slot;

   public CRemoveAbilityPacket() {
   }

   public CRemoveAbilityPacket(int id) {
      this.slot = id;
   }

   public void encode(FriendlyByteBuf buffer) {
      buffer.writeInt(this.slot);
   }

   public static CRemoveAbilityPacket decode(FriendlyByteBuf buffer) {
      CRemoveAbilityPacket msg = new CRemoveAbilityPacket();
      msg.slot = buffer.readInt();
      return msg;
   }

   public static void handle(CRemoveAbilityPacket message, Supplier<NetworkEvent.Context> ctx) {
      if (((NetworkEvent.Context)ctx.get()).getDirection() == NetworkDirection.PLAY_TO_SERVER) {
         ((NetworkEvent.Context)ctx.get()).enqueueWork(() -> {
            ServerPlayer player = ((NetworkEvent.Context)ctx.get()).getSender();
            IAbilityData abilityData = (IAbilityData)AbilityCapability.get(player).orElse((Object)null);
            if (abilityData != null) {
               IAbility ability = abilityData.getEquippedAbility(message.slot);
               if (ability != null) {
                  boolean isOnCooldown = (Boolean)ability.getComponent((AbilityComponentKey)ModAbilityComponents.COOLDOWN.get()).map((comp) -> comp.isOnCooldown()).orElse(false);
                  if (isOnCooldown) {
                     return;
                  }

                  boolean isDisabled = (Boolean)ability.getComponent((AbilityComponentKey)ModAbilityComponents.DISABLE.get()).map((comp) -> comp.isDisabled()).orElse(false);
                  if (isDisabled) {
                     return;
                  }

                  boolean isOnContinuity = (Boolean)ability.getComponent((AbilityComponentKey)ModAbilityComponents.CONTINUOUS.get()).map((comp) -> comp.isContinuous()).orElse(false);
                  if (isOnContinuity) {
                     return;
                  }

                  boolean isCharging = (Boolean)ability.getComponent((AbilityComponentKey)ModAbilityComponents.CHARGE.get()).map((comp) -> comp.isCharging()).orElse(false);
                  if (isCharging) {
                     return;
                  }

                  boolean hasStackDifference = (Boolean)ability.getComponent((AbilityComponentKey)ModAbilityComponents.STACK.get()).map((comp) -> comp.getDefaultStacks() > 0 && comp.getStacks() != comp.getDefaultStacks()).orElse(false);
                  if (hasStackDifference) {
                     return;
                  }
               }

               abilityData.setEquippedAbility(message.slot, (IAbility)null);
               ModNetwork.sendToAllTrackingAndSelf(new SRemoveAbilityPacket(player.m_19879_(), message.slot), player);
               if (abilityData.getEquippedAbilitySlot(ability) >= 0) {
                  ModNetwork.sendToAllTrackingAndSelf(new SUpdateEquippedAbilityPacket(player, ability), player);
               }

            }
         });
      }

      ((NetworkEvent.Context)ctx.get()).setPacketHandled(true);
   }
}
