package xyz.pixelatedw.mineminenomi.packets.server.entity;

import java.util.UUID;
import java.util.function.Supplier;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import org.jetbrains.annotations.Nullable;
import xyz.pixelatedw.mineminenomi.data.entity.carry.CarryCapability;
import xyz.pixelatedw.mineminenomi.data.entity.carry.ICarryData;
import xyz.pixelatedw.mineminenomi.init.ModValues;

public class SLeashPlayerPacket {
   private UUID leashedId;
   private UUID leashHolderId;

   public SLeashPlayerPacket() {
   }

   public SLeashPlayerPacket(Player leashedEntity, @Nullable Player leashHolder) {
      this.leashedId = leashedEntity.m_20148_();
      if (leashHolder != null) {
         this.leashHolderId = leashHolder.m_20148_();
      } else {
         this.leashHolderId = ModValues.NIL_UUID;
      }

   }

   public void encode(FriendlyByteBuf buffer) {
      buffer.m_130077_(this.leashedId);
      buffer.m_130077_(this.leashHolderId);
   }

   public static SLeashPlayerPacket decode(FriendlyByteBuf buffer) {
      SLeashPlayerPacket msg = new SLeashPlayerPacket();
      msg.leashedId = buffer.m_130259_();
      msg.leashHolderId = buffer.m_130259_();
      return msg;
   }

   public static void handle(SLeashPlayerPacket message, Supplier<NetworkEvent.Context> ctx) {
      if (((NetworkEvent.Context)ctx.get()).getDirection() == NetworkDirection.PLAY_TO_CLIENT) {
         ((NetworkEvent.Context)ctx.get()).enqueueWork(() -> SLeashPlayerPacket.ClientHandler.handle(message));
      }

      ((NetworkEvent.Context)ctx.get()).setPacketHandled(true);
   }

   public static class ClientHandler {
      @OnlyIn(Dist.CLIENT)
      public static void handle(SLeashPlayerPacket message) {
         Minecraft mc = Minecraft.m_91087_();
         UUID leashHolderId = message.leashHolderId;
         UUID leashedId = message.leashedId;
         Player leashedEntity = mc.f_91073_.m_46003_(leashedId);
         if (leashedEntity != null) {
            ICarryData props = (ICarryData)CarryCapability.get(leashedEntity).orElse((Object)null);
            if (props != null) {
               if (!leashHolderId.equals(ModValues.NIL_UUID)) {
                  Player leashHolder = mc.f_91073_.m_46003_(message.leashHolderId);
                  if (leashHolder == null) {
                     props.dropLeash();
                     return;
                  }

                  props.setLeashedTo(leashHolder);
               } else {
                  props.dropLeash();
               }

            }
         }
      }
   }
}
