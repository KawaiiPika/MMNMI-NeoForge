package xyz.pixelatedw.mineminenomi.packets.server.entity;

import java.util.function.Supplier;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import xyz.pixelatedw.mineminenomi.api.entities.NuLightningEntity;

public class SUpdateLightningEntity {
   private int entityId;
   private float length;
   private int fadeTime;
   private Vec3 lookVec;

   public SUpdateLightningEntity() {
   }

   public SUpdateLightningEntity(NuLightningEntity entity) {
      this.entityId = entity.m_19879_();
      this.length = entity.getLength();
      this.fadeTime = entity.getFadeTime();
      this.lookVec = entity.m_20154_().m_82541_();
   }

   public void encode(FriendlyByteBuf buffer) {
      buffer.writeInt(this.entityId);
      buffer.writeFloat(this.length);
      buffer.writeInt(this.fadeTime);
      buffer.writeDouble(this.lookVec.f_82479_);
      buffer.writeDouble(this.lookVec.f_82480_);
      buffer.writeDouble(this.lookVec.f_82481_);
   }

   public static SUpdateLightningEntity decode(FriendlyByteBuf buffer) {
      SUpdateLightningEntity msg = new SUpdateLightningEntity();
      msg.entityId = buffer.readInt();
      msg.length = buffer.readFloat();
      msg.fadeTime = buffer.readInt();
      msg.lookVec = new Vec3(buffer.readDouble(), buffer.readDouble(), buffer.readDouble());
      return msg;
   }

   public static void handle(SUpdateLightningEntity message, Supplier<NetworkEvent.Context> ctx) {
      if (((NetworkEvent.Context)ctx.get()).getDirection() == NetworkDirection.PLAY_TO_CLIENT) {
         ((NetworkEvent.Context)ctx.get()).enqueueWork(() -> SUpdateLightningEntity.ClientHandler.handle(message));
      }

      ((NetworkEvent.Context)ctx.get()).setPacketHandled(true);
   }

   public static class ClientHandler {
      @OnlyIn(Dist.CLIENT)
      public static void handle(SUpdateLightningEntity message) {
         Entity target = Minecraft.m_91087_().f_91073_.m_6815_(message.entityId);
         if (target != null && target instanceof NuLightningEntity proj) {
            proj.setLength(message.length);
            proj.startFade(message.fadeTime);
            proj.setBeamLookVector(message.lookVec);
         }
      }
   }
}
