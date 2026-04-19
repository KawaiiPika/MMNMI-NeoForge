package xyz.pixelatedw.mineminenomi.packets.server.ui;

import java.util.function.Supplier;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.CraftingScreen;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import xyz.pixelatedw.mineminenomi.ui.components.DyeLayerComponent;

public class SToggleDyeLayersPacket {
   private boolean visible;
   private int currentLayer;
   private int maxLayers;

   public SToggleDyeLayersPacket() {
   }

   public SToggleDyeLayersPacket(boolean visible, int currentLayer, int maxLayers) {
      this.visible = visible;
      this.currentLayer = currentLayer;
      this.maxLayers = maxLayers;
   }

   public void encode(FriendlyByteBuf buffer) {
      buffer.writeBoolean(this.visible);
      buffer.writeInt(this.currentLayer);
      buffer.writeInt(this.maxLayers);
   }

   public static SToggleDyeLayersPacket decode(FriendlyByteBuf buffer) {
      SToggleDyeLayersPacket msg = new SToggleDyeLayersPacket();
      msg.visible = buffer.readBoolean();
      msg.currentLayer = buffer.readInt();
      msg.maxLayers = buffer.readInt();
      return msg;
   }

   public static void handle(SToggleDyeLayersPacket message, Supplier<NetworkEvent.Context> ctx) {
      if (((NetworkEvent.Context)ctx.get()).getDirection() == NetworkDirection.PLAY_TO_CLIENT) {
         ((NetworkEvent.Context)ctx.get()).enqueueWork(() -> SToggleDyeLayersPacket.ClientHandler.handle(message));
      }

      ((NetworkEvent.Context)ctx.get()).setPacketHandled(true);
   }

   public static class ClientHandler {
      @OnlyIn(Dist.CLIENT)
      public static void handle(SToggleDyeLayersPacket message) {
         Minecraft mc = Minecraft.m_91087_();
         Screen screen = mc.f_91080_;
         if (screen instanceof CraftingScreen craftingScreen) {
            for(GuiEventListener elem : craftingScreen.m_6702_()) {
               if (elem instanceof DyeLayerComponent component) {
                  component.setVisible(message.visible);
                  component.setLayer(message.currentLayer);
                  component.setMaxLayers(message.maxLayers);
                  break;
               }
            }
         }

      }
   }
}
