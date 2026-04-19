package xyz.pixelatedw.mineminenomi.packets.server.ability;

import java.util.function.Supplier;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import xyz.pixelatedw.mineminenomi.data.entity.gcd.GCDCapability;

public class SStartGCDPacket {
   private int entityId;

   public SStartGCDPacket() {
   }

   public SStartGCDPacket(int entityId) {
      this.entityId = entityId;
   }

   public void encode(FriendlyByteBuf buffer) {
      buffer.writeInt(this.entityId);
   }

   public static SStartGCDPacket decode(FriendlyByteBuf buffer) {
      SStartGCDPacket msg = new SStartGCDPacket();
      msg.entityId = buffer.readInt();
      return msg;
   }

   public static void handle(SStartGCDPacket message, Supplier<NetworkEvent.Context> ctx) {
      if (((NetworkEvent.Context)ctx.get()).getDirection() == NetworkDirection.PLAY_TO_CLIENT) {
         ((NetworkEvent.Context)ctx.get()).enqueueWork(() -> SStartGCDPacket.ClientHandler.handle(message));
      }

      ((NetworkEvent.Context)ctx.get()).setPacketHandled(true);
   }

   public static class ClientHandler {
      @OnlyIn(Dist.CLIENT)
      public static void handle(SStartGCDPacket message) {
         Entity target = Minecraft.m_91087_().f_91073_.m_6815_(message.entityId);
         if (target != null) {
            if (target instanceof Player) {
               Player player = (Player)target;
               GCDCapability.startGCD(player);
            }

         }
      }
   }
}
