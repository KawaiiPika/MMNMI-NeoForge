package xyz.pixelatedw.mineminenomi.packets.server.ability;

import java.util.function.Supplier;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import xyz.pixelatedw.mineminenomi.data.entity.stats.EntityStatsCapability;
import xyz.pixelatedw.mineminenomi.handlers.ui.CombatModeHandler;

public class SToggleCombatModePacket {
   private boolean combatMode = false;
   private int bars = 1;

   public SToggleCombatModePacket() {
   }

   public SToggleCombatModePacket(boolean combatMode, int bars) {
      this.combatMode = combatMode;
      this.bars = bars;
   }

   public void encode(FriendlyByteBuf buffer) {
      buffer.writeBoolean(this.combatMode);
      buffer.writeInt(this.bars);
   }

   public static SToggleCombatModePacket decode(FriendlyByteBuf buffer) {
      SToggleCombatModePacket msg = new SToggleCombatModePacket();
      msg.combatMode = buffer.readBoolean();
      msg.bars = buffer.readInt();
      return msg;
   }

   public static void handle(SToggleCombatModePacket message, Supplier<NetworkEvent.Context> ctx) {
      if (((NetworkEvent.Context)ctx.get()).getDirection() == NetworkDirection.PLAY_TO_CLIENT) {
         ((NetworkEvent.Context)ctx.get()).enqueueWork(() -> SToggleCombatModePacket.ClientHandler.handle(message));
      }

      ((NetworkEvent.Context)ctx.get()).setPacketHandled(true);
   }

   public static class ClientHandler {
      @OnlyIn(Dist.CLIENT)
      public static void handle(SToggleCombatModePacket message) {
         AbstractClientPlayer player = Minecraft.m_91087_().f_91074_;
         if (player != null) {
            EntityStatsCapability.get(player).ifPresent((props) -> props.setCombatMode(message.combatMode));
            CombatModeHandler.abilityBars = message.bars;
         }
      }
   }
}
