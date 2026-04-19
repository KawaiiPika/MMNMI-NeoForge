package xyz.pixelatedw.mineminenomi.packets.server;

import java.util.Optional;
import java.util.function.Supplier;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import xyz.pixelatedw.mineminenomi.data.entity.gcd.GCDCapability;
import xyz.pixelatedw.mineminenomi.data.entity.gcd.IGCDData;

public class SSyncGCDDataPacket {
   private int entityId;
   private CompoundTag data;

   public SSyncGCDDataPacket() {
   }

   public SSyncGCDDataPacket(LivingEntity entity) {
      this(entity, GCDCapability.get(entity));
   }

   public SSyncGCDDataPacket(LivingEntity entity, Optional<IGCDData> lazyOptional) {
      this.data = new CompoundTag();
      this.data = (CompoundTag)lazyOptional.map((data) -> (CompoundTag)data.serializeNBT()).orElse(new CompoundTag());
      this.entityId = entity.m_19879_();
   }

   public SSyncGCDDataPacket(Player player, IGCDData props) {
      this.data = new CompoundTag();
      this.data = (CompoundTag)props.serializeNBT();
      this.entityId = player.m_19879_();
   }

   public void encode(FriendlyByteBuf buffer) {
      buffer.writeInt(this.entityId);
      buffer.m_130079_(this.data);
   }

   public static SSyncGCDDataPacket decode(FriendlyByteBuf buffer) {
      SSyncGCDDataPacket msg = new SSyncGCDDataPacket();
      msg.entityId = buffer.readInt();
      msg.data = buffer.m_130260_();
      return msg;
   }

   public static void handle(SSyncGCDDataPacket message, Supplier<NetworkEvent.Context> ctx) {
      if (((NetworkEvent.Context)ctx.get()).getDirection() == NetworkDirection.PLAY_TO_CLIENT) {
         ((NetworkEvent.Context)ctx.get()).enqueueWork(() -> SSyncGCDDataPacket.ClientHandler.handle(message));
      }

      ((NetworkEvent.Context)ctx.get()).setPacketHandled(true);
   }

   public static class ClientHandler {
      @OnlyIn(Dist.CLIENT)
      public static void handle(SSyncGCDDataPacket message) {
         Entity target = Minecraft.m_91087_().f_91073_.m_6815_(message.entityId);
         if (target != null) {
            if (!message.data.m_128456_()) {
               if (target instanceof Player) {
                  Player player = (Player)target;
                  GCDCapability.get(player).ifPresent((props) -> props.deserializeNBT(message.data));
               }

            }
         }
      }
   }
}
