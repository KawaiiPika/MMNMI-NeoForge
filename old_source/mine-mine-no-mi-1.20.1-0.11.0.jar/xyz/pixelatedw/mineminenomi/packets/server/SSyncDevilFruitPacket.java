package xyz.pixelatedw.mineminenomi.packets.server;

import java.util.Optional;
import java.util.function.Supplier;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import xyz.pixelatedw.mineminenomi.data.entity.devilfruit.DevilFruitCapability;
import xyz.pixelatedw.mineminenomi.data.entity.devilfruit.IDevilFruit;

public class SSyncDevilFruitPacket {
   private int entityId;
   private CompoundTag data;

   public SSyncDevilFruitPacket() {
   }

   public SSyncDevilFruitPacket(LivingEntity entity) {
      this(entity, DevilFruitCapability.get(entity));
   }

   public SSyncDevilFruitPacket(LivingEntity entity, Optional<IDevilFruit> lazyOptional) {
      this.data = new CompoundTag();
      this.data = (CompoundTag)lazyOptional.map((data) -> (CompoundTag)data.serializeNBT()).orElse(new CompoundTag());
      this.entityId = entity.m_19879_();
   }

   public SSyncDevilFruitPacket(LivingEntity entity, IDevilFruit props) {
      this.data = new CompoundTag();
      this.data = (CompoundTag)props.serializeNBT();
      this.entityId = entity.m_19879_();
   }

   public void encode(FriendlyByteBuf buffer) {
      buffer.writeInt(this.entityId);
      buffer.m_130079_(this.data);
   }

   public static SSyncDevilFruitPacket decode(FriendlyByteBuf buffer) {
      SSyncDevilFruitPacket msg = new SSyncDevilFruitPacket();
      msg.entityId = buffer.readInt();
      msg.data = buffer.m_130260_();
      return msg;
   }

   public static void handle(SSyncDevilFruitPacket message, Supplier<NetworkEvent.Context> ctx) {
      if (((NetworkEvent.Context)ctx.get()).getDirection() == NetworkDirection.PLAY_TO_CLIENT) {
         ((NetworkEvent.Context)ctx.get()).enqueueWork(() -> SSyncDevilFruitPacket.ClientHandler.handle(message));
      }

      ((NetworkEvent.Context)ctx.get()).setPacketHandled(true);
   }

   public static class ClientHandler {
      @OnlyIn(Dist.CLIENT)
      public static void handle(SSyncDevilFruitPacket message) {
         Entity target = Minecraft.m_91087_().f_91073_.m_6815_(message.entityId);
         if (target != null) {
            if (!message.data.m_128456_()) {
               if (target instanceof LivingEntity) {
                  LivingEntity living = (LivingEntity)target;
                  DevilFruitCapability.get(living).ifPresent((props) -> props.deserializeNBT(message.data));
               }

            }
         }
      }
   }
}
