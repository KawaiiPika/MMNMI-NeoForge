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
import xyz.pixelatedw.mineminenomi.data.entity.haki.HakiCapability;
import xyz.pixelatedw.mineminenomi.data.entity.haki.IHakiData;

public class SUpdateHakiColorPacket {
   private int entityId;
   private int color;

   public SUpdateHakiColorPacket() {
   }

   public SUpdateHakiColorPacket(int entityId, int color) {
      this.entityId = entityId;
      this.color = color;
   }

   public void encode(FriendlyByteBuf buffer) {
      buffer.writeInt(this.entityId);
      buffer.writeInt(this.color);
   }

   public static SUpdateHakiColorPacket decode(FriendlyByteBuf buffer) {
      SUpdateHakiColorPacket msg = new SUpdateHakiColorPacket();
      msg.entityId = buffer.readInt();
      msg.color = buffer.readInt();
      return msg;
   }

   public static void handle(SUpdateHakiColorPacket message, Supplier<NetworkEvent.Context> ctx) {
      if (((NetworkEvent.Context)ctx.get()).getDirection() == NetworkDirection.PLAY_TO_CLIENT) {
         ((NetworkEvent.Context)ctx.get()).enqueueWork(() -> SUpdateHakiColorPacket.ClientHandler.handle(message));
      }

      ((NetworkEvent.Context)ctx.get()).setPacketHandled(true);
   }

   public static class ClientHandler {
      @OnlyIn(Dist.CLIENT)
      public static void handle(SUpdateHakiColorPacket message) {
         Entity target = Minecraft.m_91087_().f_91073_.m_6815_(message.entityId);
         if (target != null && target instanceof LivingEntity) {
            IHakiData props = (IHakiData)HakiCapability.get((LivingEntity)target).orElse((Object)null);
            if (props != null) {
               props.setHaoshokuHakiColour(message.color);
            }
         }
      }
   }
}
