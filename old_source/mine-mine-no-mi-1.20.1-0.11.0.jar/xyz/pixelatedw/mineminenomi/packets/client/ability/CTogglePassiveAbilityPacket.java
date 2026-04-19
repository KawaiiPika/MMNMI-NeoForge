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
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityType;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponentKey;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityCapability;
import xyz.pixelatedw.mineminenomi.data.entity.ability.IAbilityData;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.packets.server.ability.component.SSetPauseTickPacket;

public class CTogglePassiveAbilityPacket {
   private ResourceLocation abilityKey;

   public CTogglePassiveAbilityPacket() {
   }

   public CTogglePassiveAbilityPacket(AbilityCore<?> ability) {
      this.abilityKey = ability.getRegistryKey();
   }

   public void encode(FriendlyByteBuf buffer) {
      buffer.m_130085_(this.abilityKey);
   }

   public static CTogglePassiveAbilityPacket decode(FriendlyByteBuf buffer) {
      CTogglePassiveAbilityPacket msg = new CTogglePassiveAbilityPacket();
      msg.abilityKey = buffer.m_130281_();
      return msg;
   }

   public static void handle(CTogglePassiveAbilityPacket message, Supplier<NetworkEvent.Context> ctx) {
      if (((NetworkEvent.Context)ctx.get()).getDirection() == NetworkDirection.PLAY_TO_SERVER) {
         ((NetworkEvent.Context)ctx.get()).enqueueWork(() -> {
            ServerPlayer player = ((NetworkEvent.Context)ctx.get()).getSender();
            IAbilityData abilityData = (IAbilityData)AbilityCapability.get(player).orElse((Object)null);
            if (abilityData != null) {
               AbilityCore<?> core = (AbilityCore)((IForgeRegistry)WyRegistry.ABILITIES.get()).getValue(message.abilityKey);
               if (core != null && core.getType() == AbilityType.PASSIVE) {
                  IAbility ability = abilityData.getPassiveAbility(core);
                  if (ability != null) {
                     ability.getComponent((AbilityComponentKey)ModAbilityComponents.PAUSE_TICK.get()).ifPresent((comp) -> {
                        comp.setPause(player, !comp.isPaused());
                        ModNetwork.sendToAllTrackingAndSelf(new SSetPauseTickPacket(player, core, comp.isPaused()), player);
                     });
                  }
               }
            }
         });
      }

      ((NetworkEvent.Context)ctx.get()).setPacketHandled(true);
   }
}
