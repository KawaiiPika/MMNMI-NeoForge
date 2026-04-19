package xyz.pixelatedw.mineminenomi.packets.server;

import java.util.function.Supplier;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import xyz.pixelatedw.mineminenomi.data.entity.stats.EntityStatsCapability;
import xyz.pixelatedw.mineminenomi.data.entity.stats.IEntityStats;

public class SSetRogueModePacket {
   private boolean isInRogue;

   public SSetRogueModePacket() {
   }

   public SSetRogueModePacket(boolean isInRogue) {
      this.isInRogue = isInRogue;
   }

   public void encode(FriendlyByteBuf buffer) {
      buffer.writeBoolean(this.isInRogue);
   }

   public static SSetRogueModePacket decode(FriendlyByteBuf buffer) {
      SSetRogueModePacket msg = new SSetRogueModePacket();
      msg.isInRogue = buffer.readBoolean();
      return msg;
   }

   public static void handle(SSetRogueModePacket message, Supplier<NetworkEvent.Context> ctx) {
      if (((NetworkEvent.Context)ctx.get()).getDirection() == NetworkDirection.PLAY_TO_CLIENT) {
         ((NetworkEvent.Context)ctx.get()).enqueueWork(() -> SSetRogueModePacket.ClientHandler.handle(message));
      }

      ((NetworkEvent.Context)ctx.get()).setPacketHandled(true);
   }

   public static class ClientHandler {
      @OnlyIn(Dist.CLIENT)
      public static void handle(SSetRogueModePacket message) {
         Minecraft mc = Minecraft.m_91087_();
         LocalPlayer player = mc.f_91074_;
         if (player != null) {
            IEntityStats props = (IEntityStats)EntityStatsCapability.get(player).orElse((Object)null);
            if (props != null) {
               props.setRogue(message.isInRogue);
            }
         }
      }
   }
}
