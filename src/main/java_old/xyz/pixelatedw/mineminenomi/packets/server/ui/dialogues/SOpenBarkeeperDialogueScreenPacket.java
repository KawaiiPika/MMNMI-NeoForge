package xyz.pixelatedw.mineminenomi.packets.server.ui.dialogues;

import java.util.function.Supplier;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import xyz.pixelatedw.mineminenomi.ui.screens.dialogues.BarkeeperDialogueScreen;

public class SOpenBarkeeperDialogueScreenPacket {
   private int entity;

   public SOpenBarkeeperDialogueScreenPacket() {
   }

   public SOpenBarkeeperDialogueScreenPacket(Player player, LivingEntity entity) {
      this.entity = entity.m_19879_();
   }

   public void encode(FriendlyByteBuf buffer) {
      buffer.writeInt(this.entity);
   }

   public static SOpenBarkeeperDialogueScreenPacket decode(FriendlyByteBuf buffer) {
      SOpenBarkeeperDialogueScreenPacket msg = new SOpenBarkeeperDialogueScreenPacket();
      msg.entity = buffer.readInt();
      return msg;
   }

   public static void handle(SOpenBarkeeperDialogueScreenPacket message, Supplier<NetworkEvent.Context> ctx) {
      if (((NetworkEvent.Context)ctx.get()).getDirection() == NetworkDirection.PLAY_TO_CLIENT) {
         ((NetworkEvent.Context)ctx.get()).enqueueWork(() -> SOpenBarkeeperDialogueScreenPacket.ClientHandler.handle(message));
      }

      ((NetworkEvent.Context)ctx.get()).setPacketHandled(true);
   }

   public static class ClientHandler {
      @OnlyIn(Dist.CLIENT)
      public static void handle(SOpenBarkeeperDialogueScreenPacket message) {
         Entity entity = Minecraft.m_91087_().f_91073_.m_6815_(message.entity);
         if (entity instanceof LivingEntity living) {
            Minecraft.m_91087_().m_91152_(new BarkeeperDialogueScreen(living));
         }
      }
   }
}
