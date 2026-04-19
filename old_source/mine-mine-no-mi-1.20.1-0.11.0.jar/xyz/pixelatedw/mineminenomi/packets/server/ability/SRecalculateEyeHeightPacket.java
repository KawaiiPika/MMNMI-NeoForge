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

public class SRecalculateEyeHeightPacket {
   private int entityId;

   public SRecalculateEyeHeightPacket() {
   }

   public SRecalculateEyeHeightPacket(int entityId) {
      this.entityId = entityId;
   }

   public void encode(FriendlyByteBuf buffer) {
      buffer.writeInt(this.entityId);
   }

   public static SRecalculateEyeHeightPacket decode(FriendlyByteBuf buffer) {
      SRecalculateEyeHeightPacket msg = new SRecalculateEyeHeightPacket();
      msg.entityId = buffer.readInt();
      return msg;
   }

   public static void handle(SRecalculateEyeHeightPacket message, Supplier<NetworkEvent.Context> ctx) {
      if (((NetworkEvent.Context)ctx.get()).getDirection() == NetworkDirection.PLAY_TO_CLIENT) {
         ((NetworkEvent.Context)ctx.get()).enqueueWork(() -> SRecalculateEyeHeightPacket.ClientHandler.handle(message));
      }

      ((NetworkEvent.Context)ctx.get()).setPacketHandled(true);
   }

   public static class ClientHandler {
      @OnlyIn(Dist.CLIENT)
      public static void handle(SRecalculateEyeHeightPacket message) {
         Entity target = Minecraft.m_91087_().f_91073_.m_6815_(message.entityId);
         if (target != null && target instanceof LivingEntity) {
            target.m_6210_();
         }
      }
   }
}
