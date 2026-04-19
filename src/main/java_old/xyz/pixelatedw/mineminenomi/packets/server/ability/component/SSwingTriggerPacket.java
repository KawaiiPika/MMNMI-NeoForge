package xyz.pixelatedw.mineminenomi.packets.server.ability.component;

import java.util.function.Supplier;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponentKey;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityCapability;
import xyz.pixelatedw.mineminenomi.data.entity.ability.IAbilityData;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;

public class SSwingTriggerPacket<E extends Enum<E>> {
   private int entityId;
   private int abilityId;

   public SSwingTriggerPacket() {
   }

   public SSwingTriggerPacket(LivingEntity entity, IAbility ability) {
      this.entityId = entity.m_19879_();
      this.abilityId = (Integer)AbilityCapability.get(entity).map((props) -> props.getEquippedAbilitySlot(ability)).orElse(-1);
   }

   public void encode(FriendlyByteBuf buffer) {
      buffer.writeInt(this.entityId);
      buffer.writeInt(this.abilityId);
   }

   public static SSwingTriggerPacket<?> decode(FriendlyByteBuf buffer) {
      SSwingTriggerPacket<?> msg = new SSwingTriggerPacket();
      msg.entityId = buffer.readInt();
      msg.abilityId = buffer.readInt();
      return msg;
   }

   public static void handle(SSwingTriggerPacket<?> message, Supplier<NetworkEvent.Context> ctx) {
      if (((NetworkEvent.Context)ctx.get()).getDirection() == NetworkDirection.PLAY_TO_CLIENT) {
         ((NetworkEvent.Context)ctx.get()).enqueueWork(() -> SSwingTriggerPacket.ClientHandler.handle(message));
      }

      ((NetworkEvent.Context)ctx.get()).setPacketHandled(true);
   }

   public static class ClientHandler {
      @OnlyIn(Dist.CLIENT)
      public static <E extends Enum<E>> void handle(SSwingTriggerPacket<E> message) {
         Entity target = Minecraft.m_91087_().f_91073_.m_6815_(message.entityId);
         if (target != null && target instanceof LivingEntity entity) {
            IAbilityData props = (IAbilityData)AbilityCapability.get(entity).orElse((Object)null);
            if (props != null) {
               IAbility abl = props.getEquippedAbility(message.abilityId);
               if (abl != null) {
                  abl.getComponent((AbilityComponentKey)ModAbilityComponents.SWING_TRIGGER.get()).ifPresent((c) -> c.swing(entity));
               }
            }
         }
      }
   }
}
