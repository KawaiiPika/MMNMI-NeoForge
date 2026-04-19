package xyz.pixelatedw.mineminenomi.packets.server.ui;

import java.util.function.Supplier;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import xyz.pixelatedw.mineminenomi.ui.screens.CharacterCreatorScreen;

public class SOpenCharacterCreatorScreenPacket {
   private boolean hasRandomizedRace;
   private boolean allowSubRaceSelect;

   public SOpenCharacterCreatorScreenPacket() {
   }

   public SOpenCharacterCreatorScreenPacket(boolean hasRandomizedRace, boolean allowSubRaceSelect) {
      this.hasRandomizedRace = hasRandomizedRace;
      this.allowSubRaceSelect = allowSubRaceSelect;
   }

   public void encode(FriendlyByteBuf buffer) {
      buffer.writeBoolean(this.hasRandomizedRace);
      buffer.writeBoolean(this.allowSubRaceSelect);
   }

   public static SOpenCharacterCreatorScreenPacket decode(FriendlyByteBuf buffer) {
      SOpenCharacterCreatorScreenPacket msg = new SOpenCharacterCreatorScreenPacket();
      msg.hasRandomizedRace = buffer.readBoolean();
      msg.allowSubRaceSelect = buffer.readBoolean();
      return msg;
   }

   public static void handle(SOpenCharacterCreatorScreenPacket message, Supplier<NetworkEvent.Context> ctx) {
      if (((NetworkEvent.Context)ctx.get()).getDirection() == NetworkDirection.PLAY_TO_CLIENT) {
         ((NetworkEvent.Context)ctx.get()).enqueueWork(() -> SOpenCharacterCreatorScreenPacket.ClientHandler.handle(message));
      }

      ((NetworkEvent.Context)ctx.get()).setPacketHandled(true);
   }

   public static class ClientHandler {
      @OnlyIn(Dist.CLIENT)
      public static void handle(SOpenCharacterCreatorScreenPacket message) {
         Minecraft.m_91087_().m_91152_(new CharacterCreatorScreen(message.hasRandomizedRace, message.allowSubRaceSelect));
      }
   }
}
