package xyz.pixelatedw.mineminenomi.packets.server.ui;

import java.util.UUID;
import java.util.function.Supplier;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import xyz.pixelatedw.mineminenomi.api.entities.ClientBossExtraEvent;
import xyz.pixelatedw.mineminenomi.data.entity.combat.CombatCapability;
import xyz.pixelatedw.mineminenomi.data.entity.combat.ICombatData;

public class SUpdateOPBossEventPacket {
   private UUID id;
   private int totalBars;
   private int activeBars;

   public SUpdateOPBossEventPacket() {
   }

   public SUpdateOPBossEventPacket(UUID id, int total, int active) {
      this.id = id;
      this.totalBars = total;
      this.activeBars = active;
   }

   public void encode(FriendlyByteBuf buffer) {
      buffer.m_130077_(this.id);
      buffer.writeInt(this.totalBars);
      buffer.writeInt(this.activeBars);
   }

   public static SUpdateOPBossEventPacket decode(FriendlyByteBuf buffer) {
      SUpdateOPBossEventPacket msg = new SUpdateOPBossEventPacket();
      msg.id = buffer.m_130259_();
      msg.totalBars = buffer.readInt();
      msg.activeBars = buffer.readInt();
      return msg;
   }

   public static void handle(SUpdateOPBossEventPacket message, Supplier<NetworkEvent.Context> ctx) {
      if (((NetworkEvent.Context)ctx.get()).getDirection() == NetworkDirection.PLAY_TO_CLIENT) {
         ((NetworkEvent.Context)ctx.get()).enqueueWork(() -> SUpdateOPBossEventPacket.ClientHandler.handle(message));
      }

      ((NetworkEvent.Context)ctx.get()).setPacketHandled(true);
   }

   public static class ClientHandler {
      @OnlyIn(Dist.CLIENT)
      public static void handle(SUpdateOPBossEventPacket message) {
         Minecraft mc = Minecraft.m_91087_();
         LocalPlayer player = mc.f_91074_;
         ClientBossExtraEvent extraInfo = new ClientBossExtraEvent();
         extraInfo.setTotalBars(message.totalBars);
         extraInfo.setActiveBars(message.activeBars);
         ICombatData playerData = (ICombatData)CombatCapability.get(player).orElse((Object)null);
         if (playerData != null) {
            playerData.addExtraBossInfo(message.id, extraInfo);
         }

      }
   }
}
