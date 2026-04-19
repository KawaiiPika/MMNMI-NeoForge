package xyz.pixelatedw.mineminenomi.packets.server.ui.acks;

import java.util.function.Supplier;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import xyz.pixelatedw.mineminenomi.api.ui.IEventReceiverScreen;

public class SScreenDataEventPacket {
   private CompoundTag eventData;

   public SScreenDataEventPacket() {
   }

   public SScreenDataEventPacket(INBTSerializable<CompoundTag> message) {
      this.eventData = (CompoundTag)message.serializeNBT();
   }

   public void encode(FriendlyByteBuf buffer) {
      buffer.m_130079_(this.eventData);
   }

   public static SScreenDataEventPacket decode(FriendlyByteBuf buffer) {
      SScreenDataEventPacket msg = new SScreenDataEventPacket();
      msg.eventData = buffer.m_130260_();
      return msg;
   }

   public static void handle(SScreenDataEventPacket message, Supplier<NetworkEvent.Context> ctx) {
      if (((NetworkEvent.Context)ctx.get()).getDirection() == NetworkDirection.PLAY_TO_CLIENT) {
         ((NetworkEvent.Context)ctx.get()).enqueueWork(() -> SScreenDataEventPacket.ClientHandler.handle(message));
      }

      ((NetworkEvent.Context)ctx.get()).setPacketHandled(true);
   }

   public static class ClientHandler {
      @OnlyIn(Dist.CLIENT)
      public static void handle(SScreenDataEventPacket message) {
         Screen screen = Minecraft.m_91087_().f_91080_;
         IEventReceiverScreen.tryHandle(screen, message.eventData);
      }
   }
}
