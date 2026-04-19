package xyz.pixelatedw.mineminenomi.packets.client.ability;

import java.util.function.Supplier;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import xyz.pixelatedw.mineminenomi.data.entity.haki.HakiCapability;
import xyz.pixelatedw.mineminenomi.data.entity.haki.IHakiData;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.packets.server.ability.SUpdateHakiColorPacket;

public class CSetHakiColorPacket {
   private int color;

   public CSetHakiColorPacket() {
   }

   public CSetHakiColorPacket(int color) {
      this.color = color;
   }

   public void encode(FriendlyByteBuf buffer) {
      buffer.writeInt(this.color);
   }

   public static CSetHakiColorPacket decode(FriendlyByteBuf buffer) {
      CSetHakiColorPacket msg = new CSetHakiColorPacket();
      msg.color = buffer.readInt();
      return msg;
   }

   public static void handle(CSetHakiColorPacket message, Supplier<NetworkEvent.Context> ctx) {
      if (((NetworkEvent.Context)ctx.get()).getDirection() == NetworkDirection.PLAY_TO_SERVER) {
         ((NetworkEvent.Context)ctx.get()).enqueueWork(() -> {
            ServerPlayer player = ((NetworkEvent.Context)ctx.get()).getSender();
            IHakiData props = (IHakiData)HakiCapability.get(player).orElse((Object)null);
            if (props != null) {
               props.setHaoshokuHakiColour(message.color);
               ModNetwork.sendToAllTracking(new SUpdateHakiColorPacket(player.m_19879_(), message.color), player);
            }
         });
      }

      ((NetworkEvent.Context)ctx.get()).setPacketHandled(true);
   }
}
