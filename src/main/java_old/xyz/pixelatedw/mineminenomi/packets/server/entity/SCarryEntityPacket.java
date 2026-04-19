package xyz.pixelatedw.mineminenomi.packets.server.entity;

import java.util.function.Supplier;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import org.jetbrains.annotations.Nullable;
import xyz.pixelatedw.mineminenomi.data.entity.carry.CarryCapability;
import xyz.pixelatedw.mineminenomi.data.entity.carry.ICarryData;

public class SCarryEntityPacket {
   private int targetId;

   public SCarryEntityPacket() {
   }

   public SCarryEntityPacket(@Nullable LivingEntity target) {
      if (target != null) {
         this.targetId = target.m_19879_();
      } else {
         this.targetId = -1;
      }

   }

   public void encode(FriendlyByteBuf buffer) {
      buffer.writeInt(this.targetId);
   }

   public static SCarryEntityPacket decode(FriendlyByteBuf buffer) {
      SCarryEntityPacket msg = new SCarryEntityPacket();
      msg.targetId = buffer.readInt();
      return msg;
   }

   public static void handle(SCarryEntityPacket message, Supplier<NetworkEvent.Context> ctx) {
      if (((NetworkEvent.Context)ctx.get()).getDirection() == NetworkDirection.PLAY_TO_CLIENT) {
         ((NetworkEvent.Context)ctx.get()).enqueueWork(() -> SCarryEntityPacket.ClientHandler.handle(message));
      }

      ((NetworkEvent.Context)ctx.get()).setPacketHandled(true);
   }

   public static class ClientHandler {
      @OnlyIn(Dist.CLIENT)
      public static void handle(SCarryEntityPacket message) {
         Minecraft mc = Minecraft.m_91087_();
         LocalPlayer player = mc.f_91074_;
         if (player != null) {
            ICarryData props = (ICarryData)CarryCapability.get(player).orElse((Object)null);
            if (props != null) {
               int targetId = message.targetId;
               if (targetId >= 0) {
                  Entity entity = mc.f_91073_.m_6815_(message.targetId);
                  if (entity == null || !(entity instanceof LivingEntity)) {
                     props.stopCarrying();
                     return;
                  }

                  LivingEntity living = (LivingEntity)entity;
                  props.startCarrying(living);
               } else {
                  props.stopCarrying();
               }

            }
         }
      }
   }
}
