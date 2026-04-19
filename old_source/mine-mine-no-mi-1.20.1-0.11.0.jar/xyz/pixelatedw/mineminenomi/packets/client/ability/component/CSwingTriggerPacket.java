package xyz.pixelatedw.mineminenomi.packets.client.ability.component;

import java.util.function.Supplier;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponentKey;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityCapability;
import xyz.pixelatedw.mineminenomi.data.entity.ability.IAbilityData;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;

public class CSwingTriggerPacket {
   private int slot;

   public CSwingTriggerPacket() {
   }

   public CSwingTriggerPacket(LivingEntity entity, IAbility ability) {
      this.slot = (Integer)AbilityCapability.get(entity).map((props) -> props.getEquippedAbilitySlot(ability)).orElse(-1);
   }

   public void encode(FriendlyByteBuf buffer) {
      buffer.writeInt(this.slot);
   }

   public static CSwingTriggerPacket decode(FriendlyByteBuf buffer) {
      CSwingTriggerPacket msg = new CSwingTriggerPacket();
      msg.slot = buffer.readInt();
      return msg;
   }

   public static void handle(CSwingTriggerPacket message, Supplier<NetworkEvent.Context> ctx) {
      if (((NetworkEvent.Context)ctx.get()).getDirection() == NetworkDirection.PLAY_TO_SERVER) {
         ((NetworkEvent.Context)ctx.get()).enqueueWork(() -> {
            ServerPlayer player = ((NetworkEvent.Context)ctx.get()).getSender();
            IAbilityData abilityProps = (IAbilityData)AbilityCapability.get(player).orElse((Object)null);
            if (abilityProps != null) {
               Ability abl = (Ability)abilityProps.getEquippedAbility(message.slot);
               if (abl != null && !player.m_5833_()) {
                  abl.getComponent((AbilityComponentKey)ModAbilityComponents.SWING_TRIGGER.get()).ifPresent((component) -> component.swing(player));
               }
            }
         });
      }

      ((NetworkEvent.Context)ctx.get()).setPacketHandled(true);
   }
}
