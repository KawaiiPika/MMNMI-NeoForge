package xyz.pixelatedw.mineminenomi.packets.server.ui.dialogues;

import java.util.function.Supplier;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import xyz.pixelatedw.mineminenomi.ui.screens.DialogueScreen;

public class SDialogueResponsePacket {
   private Component message;
   private boolean closeDialogue;

   public static SDialogueResponsePacket closeDialogue() {
      SDialogueResponsePacket pkt = new SDialogueResponsePacket();
      pkt.closeDialogue = true;
      return pkt;
   }

   public static SDialogueResponsePacket setMessage(Component message) {
      SDialogueResponsePacket pkt = new SDialogueResponsePacket();
      pkt.message = message;
      return pkt;
   }

   public void encode(FriendlyByteBuf buffer) {
      buffer.writeBoolean(this.closeDialogue);
      boolean hasMessage = this.message != null;
      buffer.writeBoolean(hasMessage);
      if (hasMessage) {
         buffer.m_130083_(this.message);
      }

   }

   public static SDialogueResponsePacket decode(FriendlyByteBuf buffer) {
      SDialogueResponsePacket msg = new SDialogueResponsePacket();
      msg.closeDialogue = buffer.readBoolean();
      boolean hasMessage = buffer.readBoolean();
      if (hasMessage) {
         msg.message = buffer.m_130238_();
      }

      return msg;
   }

   public static void handle(SDialogueResponsePacket message, Supplier<NetworkEvent.Context> ctx) {
      if (((NetworkEvent.Context)ctx.get()).getDirection() == NetworkDirection.PLAY_TO_CLIENT) {
         ((NetworkEvent.Context)ctx.get()).enqueueWork(() -> SDialogueResponsePacket.ClientHandler.handle(message));
      }

      ((NetworkEvent.Context)ctx.get()).setPacketHandled(true);
   }

   public static class ClientHandler {
      @OnlyIn(Dist.CLIENT)
      public static void handle(SDialogueResponsePacket message) {
         Screen screen = Minecraft.m_91087_().f_91080_;
         if (screen instanceof DialogueScreen dialogueScreen) {
            if (message.closeDialogue) {
               dialogueScreen.m_7379_();
            } else {
               if (message.message != null) {
                  dialogueScreen.setMessage(message.message);
               }

            }
         }
      }
   }
}
