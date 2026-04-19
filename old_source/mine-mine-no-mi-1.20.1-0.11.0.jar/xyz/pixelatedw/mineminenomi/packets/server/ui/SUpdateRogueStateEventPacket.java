package xyz.pixelatedw.mineminenomi.packets.server.ui;

import java.util.function.Supplier;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import xyz.pixelatedw.mineminenomi.data.entity.stats.EntityStatsCapability;

public class SUpdateRogueStateEventPacket {
   private boolean state;

   public SUpdateRogueStateEventPacket() {
   }

   public SUpdateRogueStateEventPacket(LivingEntity entity) {
      this.state = (Boolean)EntityStatsCapability.get(entity).map((props) -> props.isRogue()).orElse(false);
   }

   public void encode(FriendlyByteBuf buffer) {
      buffer.writeBoolean(this.state);
   }

   public static SUpdateRogueStateEventPacket decode(FriendlyByteBuf buffer) {
      SUpdateRogueStateEventPacket msg = new SUpdateRogueStateEventPacket();
      msg.state = buffer.readBoolean();
      return msg;
   }

   public static void handle(SUpdateRogueStateEventPacket message, Supplier<NetworkEvent.Context> ctx) {
      if (((NetworkEvent.Context)ctx.get()).getDirection() == NetworkDirection.PLAY_TO_CLIENT) {
         ((NetworkEvent.Context)ctx.get()).enqueueWork(() -> SUpdateRogueStateEventPacket.ClientHandler.handle(message));
      }

      ((NetworkEvent.Context)ctx.get()).setPacketHandled(true);
   }

   public static class ClientHandler {
      @OnlyIn(Dist.CLIENT)
      public static void handle(SUpdateRogueStateEventPacket message) {
         Minecraft mc = Minecraft.m_91087_();
         LocalPlayer player = mc.f_91074_;
         EntityStatsCapability.get(player).ifPresent((props) -> props.setRogue(message.state));
      }
   }
}
