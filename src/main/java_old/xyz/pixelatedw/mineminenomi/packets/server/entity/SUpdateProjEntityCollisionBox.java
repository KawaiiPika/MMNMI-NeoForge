package xyz.pixelatedw.mineminenomi.packets.server.entity;

import java.util.function.Supplier;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;

public class SUpdateProjEntityCollisionBox {
   private int entityId;
   private double sizeX;
   private double sizeY;
   private double sizeZ;

   public SUpdateProjEntityCollisionBox() {
   }

   public SUpdateProjEntityCollisionBox(int entityId, double sizeX, double sizeY, double sizeZ) {
      this.entityId = entityId;
      this.sizeX = sizeX;
      this.sizeY = sizeY;
      this.sizeZ = sizeZ;
   }

   public void encode(FriendlyByteBuf buffer) {
      buffer.writeInt(this.entityId);
      buffer.writeDouble(this.sizeX);
      buffer.writeDouble(this.sizeY);
      buffer.writeDouble(this.sizeZ);
   }

   public static SUpdateProjEntityCollisionBox decode(FriendlyByteBuf buffer) {
      SUpdateProjEntityCollisionBox msg = new SUpdateProjEntityCollisionBox();
      msg.entityId = buffer.readInt();
      msg.sizeX = buffer.readDouble();
      msg.sizeY = buffer.readDouble();
      msg.sizeZ = buffer.readDouble();
      return msg;
   }

   public static void handle(SUpdateProjEntityCollisionBox message, Supplier<NetworkEvent.Context> ctx) {
      if (((NetworkEvent.Context)ctx.get()).getDirection() == NetworkDirection.PLAY_TO_CLIENT) {
         ((NetworkEvent.Context)ctx.get()).enqueueWork(() -> SUpdateProjEntityCollisionBox.ClientHandler.handle(message));
      }

      ((NetworkEvent.Context)ctx.get()).setPacketHandled(true);
   }

   public static class ClientHandler {
      @OnlyIn(Dist.CLIENT)
      public static void handle(SUpdateProjEntityCollisionBox message) {
         Entity target = Minecraft.m_91087_().f_91073_.m_6815_(message.entityId);
         if (target != null && target instanceof NuProjectileEntity proj) {
            proj.setEntityCollisionSize(message.sizeX, message.sizeY, message.sizeZ);
         }
      }
   }
}
