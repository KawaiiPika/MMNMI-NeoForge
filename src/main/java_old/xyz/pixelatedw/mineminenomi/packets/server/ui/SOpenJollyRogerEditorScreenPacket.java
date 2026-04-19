package xyz.pixelatedw.mineminenomi.packets.server.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.registries.IForgeRegistry;
import xyz.pixelatedw.mineminenomi.ModMain;
import xyz.pixelatedw.mineminenomi.api.WyRegistry;
import xyz.pixelatedw.mineminenomi.api.crew.Crew;
import xyz.pixelatedw.mineminenomi.api.crew.JollyRogerElement;
import xyz.pixelatedw.mineminenomi.ui.screens.JollyRogerEditorScreen;

public class SOpenJollyRogerEditorScreenPacket {
   private boolean isEditing;
   private Crew crew;
   private List<JollyRogerElement> elements;

   public SOpenJollyRogerEditorScreenPacket() {
   }

   public SOpenJollyRogerEditorScreenPacket(boolean isEditing, Crew crew, List<JollyRogerElement> elements) {
      this.isEditing = isEditing;
      this.crew = crew;
      this.elements = elements;
   }

   public void encode(FriendlyByteBuf buffer) {
      buffer.writeBoolean(this.isEditing);
      int size = this.elements.size();
      buffer.writeInt(size);

      for(JollyRogerElement elem : this.elements) {
         buffer.m_130085_(elem.getRegistryKey());
      }

      buffer.m_130079_(this.crew.write());
   }

   public static SOpenJollyRogerEditorScreenPacket decode(FriendlyByteBuf buffer) {
      SOpenJollyRogerEditorScreenPacket msg = new SOpenJollyRogerEditorScreenPacket();
      msg.isEditing = buffer.readBoolean();
      int size = buffer.readInt();
      msg.elements = new ArrayList();

      for(int i = 0; i < size; ++i) {
         ResourceLocation res = buffer.m_130281_();
         JollyRogerElement elem = (JollyRogerElement)((IForgeRegistry)WyRegistry.JOLLY_ROGER_ELEMENTS.get()).getValue(res);
         if (elem != null) {
            msg.elements.add(elem);
         } else {
            ModMain.LOGGER.warn(String.valueOf(res) + " could not be found as a jolly roger element!");
         }
      }

      CompoundTag jollyRogerData = buffer.m_130261_();
      msg.crew = Crew.from(jollyRogerData);
      return msg;
   }

   public static void handle(SOpenJollyRogerEditorScreenPacket message, Supplier<NetworkEvent.Context> ctx) {
      if (((NetworkEvent.Context)ctx.get()).getDirection() == NetworkDirection.PLAY_TO_CLIENT) {
         ((NetworkEvent.Context)ctx.get()).enqueueWork(() -> SOpenJollyRogerEditorScreenPacket.ClientHandler.handle(message));
      }

      ((NetworkEvent.Context)ctx.get()).setPacketHandled(true);
   }

   public static class ClientHandler {
      @OnlyIn(Dist.CLIENT)
      public static void handle(SOpenJollyRogerEditorScreenPacket message) {
         Minecraft.m_91087_().m_91152_(new JollyRogerEditorScreen(message.isEditing, message.crew, message.elements));
      }
   }
}
