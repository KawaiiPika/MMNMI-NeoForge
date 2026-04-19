package xyz.pixelatedw.mineminenomi.packets.server;

import java.util.function.Supplier;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import xyz.pixelatedw.mineminenomi.data.entity.stats.EntityStatsCapability;
import xyz.pixelatedw.mineminenomi.data.entity.stats.IEntityStats;

public class SAddScreenShaderPacket {
   private ResourceLocation shader;

   public SAddScreenShaderPacket() {
   }

   public SAddScreenShaderPacket(ResourceLocation shader) {
      this.shader = shader;
   }

   public void encode(FriendlyByteBuf buffer) {
      buffer.m_130085_(this.shader);
   }

   public static SAddScreenShaderPacket decode(FriendlyByteBuf buffer) {
      SAddScreenShaderPacket msg = new SAddScreenShaderPacket();
      msg.shader = buffer.m_130281_();
      return msg;
   }

   public static void handle(SAddScreenShaderPacket message, Supplier<NetworkEvent.Context> ctx) {
      if (((NetworkEvent.Context)ctx.get()).getDirection() == NetworkDirection.PLAY_TO_CLIENT) {
         ((NetworkEvent.Context)ctx.get()).enqueueWork(() -> SAddScreenShaderPacket.ClientHandler.handle(message));
      }

      ((NetworkEvent.Context)ctx.get()).setPacketHandled(true);
   }

   public static class ClientHandler {
      @OnlyIn(Dist.CLIENT)
      public static void handle(SAddScreenShaderPacket message) {
         Minecraft mc = Minecraft.m_91087_();
         LocalPlayer player = mc.f_91074_;
         if (player != null) {
            IEntityStats props = (IEntityStats)EntityStatsCapability.get(player).orElse((Object)null);
            if (props != null) {
               props.addScreenShader(message.shader);
            }

         }
      }
   }
}
