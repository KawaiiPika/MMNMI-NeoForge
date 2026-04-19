package xyz.pixelatedw.mineminenomi.packets.client.ability.component;

import java.util.function.Supplier;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponentKey;
import xyz.pixelatedw.mineminenomi.api.util.Result;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityCapability;
import xyz.pixelatedw.mineminenomi.data.entity.ability.IAbilityData;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;

public class CChangeAbilityAltModePacket {
   private int slot;

   public CChangeAbilityAltModePacket() {
   }

   public CChangeAbilityAltModePacket(int slot) {
      this.slot = slot;
   }

   public void encode(FriendlyByteBuf buffer) {
      buffer.writeInt(this.slot);
   }

   public static CChangeAbilityAltModePacket decode(FriendlyByteBuf buffer) {
      CChangeAbilityAltModePacket msg = new CChangeAbilityAltModePacket();
      msg.slot = buffer.readInt();
      return msg;
   }

   public static void handle(CChangeAbilityAltModePacket message, Supplier<NetworkEvent.Context> ctx) {
      if (((NetworkEvent.Context)ctx.get()).getDirection() == NetworkDirection.PLAY_TO_SERVER) {
         ((NetworkEvent.Context)ctx.get()).enqueueWork(() -> {
            ServerPlayer player = ((NetworkEvent.Context)ctx.get()).getSender();
            IAbilityData abilityProps = (IAbilityData)AbilityCapability.get(player).orElse((Object)null);
            if (abilityProps != null) {
               Ability abl = (Ability)abilityProps.getEquippedAbility(message.slot);
               if (abl != null && !player.m_5833_()) {
                  Result result = abl.canUse(player);
                  if (result.isFail()) {
                     if (result.getMessage() != null) {
                        WyHelper.sendMessage(player, result.getMessage());
                     }

                  } else {
                     abl.getComponent((AbilityComponentKey)ModAbilityComponents.ALT_MODE.get()).ifPresent((component) -> {
                        if (!component.isDisabled() && !component.isAutomatic()) {
                           component.setNextInCycle(player);
                        }
                     });
                  }
               }
            }
         });
      }

      ((NetworkEvent.Context)ctx.get()).setPacketHandled(true);
   }
}
