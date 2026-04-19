package xyz.pixelatedw.mineminenomi.packets.server.entity;

import java.util.function.Supplier;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import xyz.pixelatedw.mineminenomi.data.entity.kairosekicoating.IKairosekiCoating;
import xyz.pixelatedw.mineminenomi.data.entity.kairosekicoating.KairosekiCoatingCapability;

public class SKairosekiCoatingPacket {
   private int entityId;
   private int coatingLevel;

   public SKairosekiCoatingPacket() {
   }

   public SKairosekiCoatingPacket(int entityId, int coatingLevel) {
      this.entityId = entityId;
      this.coatingLevel = coatingLevel;
   }

   public void encode(FriendlyByteBuf buffer) {
      buffer.writeInt(this.entityId);
      buffer.writeInt(this.coatingLevel);
   }

   public static SKairosekiCoatingPacket decode(FriendlyByteBuf buffer) {
      SKairosekiCoatingPacket msg = new SKairosekiCoatingPacket();
      msg.entityId = buffer.readInt();
      msg.coatingLevel = buffer.readInt();
      return msg;
   }

   public static void handle(SKairosekiCoatingPacket message, Supplier<NetworkEvent.Context> ctx) {
      if (((NetworkEvent.Context)ctx.get()).getDirection() == NetworkDirection.PLAY_TO_CLIENT) {
         ((NetworkEvent.Context)ctx.get()).enqueueWork(() -> SKairosekiCoatingPacket.ClientHandler.handle(message));
      }

      ((NetworkEvent.Context)ctx.get()).setPacketHandled(true);
   }

   public static class ClientHandler {
      @OnlyIn(Dist.CLIENT)
      public static void handle(SKairosekiCoatingPacket message) {
         ClientLevel world = Minecraft.m_91087_().f_91073_;
         Entity entity = world.m_6815_(message.entityId);
         if (entity != null) {
            IKairosekiCoating coatingData = (IKairosekiCoating)KairosekiCoatingCapability.get(entity).orElse((Object)null);
            if (coatingData != null) {
               coatingData.setCoatingLevel(message.coatingLevel);
            }
         }
      }
   }
}
