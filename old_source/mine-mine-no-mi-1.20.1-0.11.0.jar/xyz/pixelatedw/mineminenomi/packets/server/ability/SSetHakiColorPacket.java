package xyz.pixelatedw.mineminenomi.packets.server.ability;

import java.awt.Color;
import java.util.Random;
import java.util.function.Supplier;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.config.ClientConfig;
import xyz.pixelatedw.mineminenomi.config.ServerConfig;
import xyz.pixelatedw.mineminenomi.data.entity.haki.HakiCapability;
import xyz.pixelatedw.mineminenomi.data.entity.haki.IHakiData;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.packets.client.ability.CSetHakiColorPacket;

public class SSetHakiColorPacket {
   private int entityId;
   private ServerConfig.HaoshokuColoringLogic coloringLogic;

   public SSetHakiColorPacket() {
   }

   public SSetHakiColorPacket(int entityId, ServerConfig.HaoshokuColoringLogic coloringLogic) {
      this.entityId = entityId;
      this.coloringLogic = coloringLogic;
   }

   public void encode(FriendlyByteBuf buffer) {
      buffer.writeInt(this.entityId);
      buffer.writeInt(this.coloringLogic.ordinal());
   }

   public static SSetHakiColorPacket decode(FriendlyByteBuf buffer) {
      SSetHakiColorPacket msg = new SSetHakiColorPacket();
      msg.entityId = buffer.readInt();
      msg.coloringLogic = ServerConfig.HaoshokuColoringLogic.values()[buffer.readInt()];
      return msg;
   }

   public static void handle(SSetHakiColorPacket message, Supplier<NetworkEvent.Context> ctx) {
      if (((NetworkEvent.Context)ctx.get()).getDirection() == NetworkDirection.PLAY_TO_CLIENT) {
         ((NetworkEvent.Context)ctx.get()).enqueueWork(() -> SSetHakiColorPacket.ClientHandler.handle(message));
      }

      ((NetworkEvent.Context)ctx.get()).setPacketHandled(true);
   }

   public static class ClientHandler {
      @OnlyIn(Dist.CLIENT)
      public static void handle(SSetHakiColorPacket message) {
         Entity target = Minecraft.m_91087_().f_91073_.m_6815_(message.entityId);
         if (target != null && target instanceof LivingEntity) {
            IHakiData props = (IHakiData)HakiCapability.get((LivingEntity)target).orElse((Object)null);
            if (props != null) {
               int color = 16711680;
               if (message.coloringLogic == ServerConfig.HaoshokuColoringLogic.CUSTOM) {
                  color = ClientConfig.getHakiColor().getRGB();
               } else if (message.coloringLogic == ServerConfig.HaoshokuColoringLogic.RANDOM) {
                  Random rand = new Random(target.m_20148_().getMostSignificantBits());
                  int r = (int)WyHelper.randomWithRange((Random)rand, 0, 255);
                  int g = (int)WyHelper.randomWithRange((Random)rand, 0, 255);
                  int b = (int)WyHelper.randomWithRange((Random)rand, 0, 255);
                  Color c = new Color(r, g, b);
                  color = c.getRGB();
               }

               int colorRGB = WyHelper.intToRGB(color, 50).getRGB();
               props.setHaoshokuHakiColour(colorRGB);
               ModNetwork.sendToServer(new CSetHakiColorPacket(colorRGB));
            }
         }
      }
   }
}
