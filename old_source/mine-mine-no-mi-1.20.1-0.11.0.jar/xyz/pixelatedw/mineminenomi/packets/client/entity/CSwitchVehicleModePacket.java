package xyz.pixelatedw.mineminenomi.packets.client.entity;

import java.util.function.Supplier;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import xyz.pixelatedw.mineminenomi.api.entities.IVehicleAltMode;

public class CSwitchVehicleModePacket {
   public void encode(FriendlyByteBuf buffer) {
   }

   public static CSwitchVehicleModePacket decode(FriendlyByteBuf buffer) {
      CSwitchVehicleModePacket msg = new CSwitchVehicleModePacket();
      return msg;
   }

   public static void handle(CSwitchVehicleModePacket message, Supplier<NetworkEvent.Context> ctx) {
      if (((NetworkEvent.Context)ctx.get()).getDirection() == NetworkDirection.PLAY_TO_SERVER) {
         ((NetworkEvent.Context)ctx.get()).enqueueWork(() -> {
            ServerPlayer player = ((NetworkEvent.Context)ctx.get()).getSender();
            if (player != null && player.m_6084_() && player.m_20159_()) {
               LivingEntity controller = player.m_20202_().m_6688_();
               if (controller != null && controller.equals(player)) {
                  Entity patt1176$temp = player.m_20202_();
                  if (patt1176$temp instanceof IVehicleAltMode) {
                     IVehicleAltMode altMode = (IVehicleAltMode)patt1176$temp;
                     altMode.changeVehicleMode();
                  }
               }
            }

         });
      }

      ((NetworkEvent.Context)ctx.get()).setPacketHandled(true);
   }
}
