package xyz.pixelatedw.mineminenomi.packets.server.ui.acks;

import java.util.function.Supplier;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import xyz.pixelatedw.mineminenomi.api.ui.IEventReceiverScreen;
import xyz.pixelatedw.mineminenomi.api.ui.SimpleMessageScreenEvent;

public class SSimpleMessageScreenEventPacket {
   private SimpleMessageScreenEvent event;

   public SSimpleMessageScreenEventPacket() {
   }

   public SSimpleMessageScreenEventPacket(SimpleMessageScreenEvent message) {
      this.event = message;
   }

   public void encode(FriendlyByteBuf buffer) {
      buffer.m_130079_(this.event.serializeNBT());
   }

   public static SSimpleMessageScreenEventPacket decode(FriendlyByteBuf buffer) {
      SSimpleMessageScreenEventPacket msg = new SSimpleMessageScreenEventPacket();
      msg.event = new SimpleMessageScreenEvent();
      msg.event.deserializeNBT(buffer.m_130260_());
      return msg;
   }

   public static void handle(SSimpleMessageScreenEventPacket message, Supplier<NetworkEvent.Context> ctx) {
      if (((NetworkEvent.Context)ctx.get()).getDirection() == NetworkDirection.PLAY_TO_CLIENT) {
         ((NetworkEvent.Context)ctx.get()).enqueueWork(() -> SSimpleMessageScreenEventPacket.ClientHandler.handle(message));
      }

      ((NetworkEvent.Context)ctx.get()).setPacketHandled(true);
   }

   public static class ClientHandler {
      @OnlyIn(Dist.CLIENT)
      public static void handle(SSimpleMessageScreenEventPacket message) {
         Minecraft mc = Minecraft.m_91087_();
         Screen screen = mc.f_91080_;
         if (screen instanceof IEventReceiverScreen eventReceiver) {
            eventReceiver.handleEvent(message.event);
         }

      }
   }
}
