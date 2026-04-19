package xyz.pixelatedw.mineminenomi.packets.server.entity;

import java.util.Optional;
import java.util.function.Supplier;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import xyz.pixelatedw.mineminenomi.data.entity.stats.EntityStatsCapability;
import xyz.pixelatedw.mineminenomi.data.entity.stats.IEntityStats;

public class SUpdateColaAmountPacket {
   private int entityId;
   private int cola;
   private int maxCola;
   private int ultraCola;

   public SUpdateColaAmountPacket() {
   }

   public SUpdateColaAmountPacket(LivingEntity entity) {
      this.entityId = entity.m_19879_();
      Optional<IEntityStats> opt = EntityStatsCapability.get(entity);
      this.cola = (Integer)opt.map((props) -> props.getCola()).orElse(0);
      this.maxCola = (Integer)opt.map((props) -> props.getMaxCola()).orElse(0);
      this.ultraCola = (Integer)opt.map((props) -> props.getUltraCola()).orElse(0);
   }

   public void encode(FriendlyByteBuf buffer) {
      buffer.writeInt(this.entityId);
      buffer.writeInt(this.cola);
      buffer.writeInt(this.maxCola);
      buffer.writeInt(this.ultraCola);
   }

   public static SUpdateColaAmountPacket decode(FriendlyByteBuf buffer) {
      SUpdateColaAmountPacket msg = new SUpdateColaAmountPacket();
      msg.entityId = buffer.readInt();
      msg.cola = buffer.readInt();
      msg.maxCola = buffer.readInt();
      msg.ultraCola = buffer.readInt();
      return msg;
   }

   public static void handle(SUpdateColaAmountPacket message, Supplier<NetworkEvent.Context> ctx) {
      if (((NetworkEvent.Context)ctx.get()).getDirection() == NetworkDirection.PLAY_TO_CLIENT) {
         ((NetworkEvent.Context)ctx.get()).enqueueWork(() -> SUpdateColaAmountPacket.ClientHandler.handle(message));
      }

      ((NetworkEvent.Context)ctx.get()).setPacketHandled(true);
   }

   public static class ClientHandler {
      @OnlyIn(Dist.CLIENT)
      public static void handle(SUpdateColaAmountPacket message) {
         Entity target = Minecraft.m_91087_().f_91073_.m_6815_(message.entityId);
         if (target != null && target instanceof LivingEntity entity) {
            IEntityStats props = (IEntityStats)EntityStatsCapability.get(entity).orElse((Object)null);
            if (props != null) {
               int cola = Mth.m_14045_(message.cola, 0, message.maxCola);
               props.setUltraCola(message.ultraCola);
               props.setCola(cola);
            }
         }
      }
   }
}
