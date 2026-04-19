package xyz.pixelatedw.mineminenomi.packets.server.ability;

import java.util.function.Supplier;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import xyz.pixelatedw.mineminenomi.data.entity.stats.EntityStatsCapability;

public class SRemoveShadowEventsPacket {
   private int entityId;

   public SRemoveShadowEventsPacket() {
   }

   public SRemoveShadowEventsPacket(LivingEntity entity) {
      this.entityId = entity.m_19879_();
   }

   public void encode(FriendlyByteBuf buffer) {
      buffer.writeInt(this.entityId);
   }

   public static SRemoveShadowEventsPacket decode(FriendlyByteBuf buffer) {
      SRemoveShadowEventsPacket msg = new SRemoveShadowEventsPacket();
      msg.entityId = buffer.readInt();
      return msg;
   }

   public static void handle(SRemoveShadowEventsPacket message, Supplier<NetworkEvent.Context> ctx) {
      if (((NetworkEvent.Context)ctx.get()).getDirection() == NetworkDirection.PLAY_TO_CLIENT) {
         ((NetworkEvent.Context)ctx.get()).enqueueWork(() -> SRemoveShadowEventsPacket.ClientHandler.handle(message));
      }

      ((NetworkEvent.Context)ctx.get()).setPacketHandled(true);
   }

   public static class ClientHandler {
      @OnlyIn(Dist.CLIENT)
      public static void handle(SRemoveShadowEventsPacket message) {
         Entity target = Minecraft.m_91087_().f_91073_.m_6815_(message.entityId);
         if (target != null && target instanceof LivingEntity livingTarget) {
            EntityStatsCapability.get(livingTarget).ifPresent((props) -> props.setShadow(false));
         }
      }
   }
}
