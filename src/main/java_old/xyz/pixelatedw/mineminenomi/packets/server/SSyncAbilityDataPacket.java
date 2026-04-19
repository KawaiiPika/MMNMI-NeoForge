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
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityCapability;
import xyz.pixelatedw.mineminenomi.data.entity.ability.IAbilityData;

public class SSyncAbilityDataPacket {
   private int entityId;
   private CompoundTag data;

   public SSyncAbilityDataPacket() {
   }

   public SSyncAbilityDataPacket(LivingEntity entity) {
      this(entity, AbilityCapability.get(entity));
   }

   public SSyncAbilityDataPacket(LivingEntity entity, Optional<IAbilityData> lazyOptional) {
      this.data = new CompoundTag();
      this.data = (CompoundTag)lazyOptional.map((data) -> (CompoundTag)data.serializeNBT()).orElse(new CompoundTag());
      this.entityId = entity.m_19879_();
   }

   public SSyncAbilityDataPacket(LivingEntity entity, IAbilityData props) {
      this.data = new CompoundTag();
      this.data = (CompoundTag)props.serializeNBT();
      this.entityId = entity.m_19879_();
   }

   public void encode(FriendlyByteBuf buffer) {
      buffer.writeInt(this.entityId);
      buffer.m_130079_(this.data);
   }

   public static SSyncAbilityDataPacket decode(FriendlyByteBuf buffer) {
      SSyncAbilityDataPacket msg = new SSyncAbilityDataPacket();
      msg.entityId = buffer.readInt();
      msg.data = buffer.m_130260_();
      return msg;
   }

   public static void handle(SSyncAbilityDataPacket message, Supplier<NetworkEvent.Context> ctx) {
      if (((NetworkEvent.Context)ctx.get()).getDirection() == NetworkDirection.PLAY_TO_CLIENT) {
         ((NetworkEvent.Context)ctx.get()).enqueueWork(() -> SSyncAbilityDataPacket.ClientHandler.handle(message));
      }

      ((NetworkEvent.Context)ctx.get()).setPacketHandled(true);
   }

   public static class ClientHandler {
      @OnlyIn(Dist.CLIENT)
      public static void handle(SSyncAbilityDataPacket message) {
         Entity target = Minecraft.m_91087_().f_91073_.m_6815_(message.entityId);
         if (target != null) {
            if (!message.data.m_128456_()) {
               if (target instanceof LivingEntity) {
                  LivingEntity living = (LivingEntity)target;
                  AbilityCapability.get(living).ifPresent((props) -> props.deserializeNBT(message.data));
               }

            }
         }
      }
   }
}
