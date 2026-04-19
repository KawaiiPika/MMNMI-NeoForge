package xyz.pixelatedw.mineminenomi.packets.client.ability;

import java.util.function.Supplier;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import xyz.pixelatedw.mineminenomi.config.ServerConfig;
import xyz.pixelatedw.mineminenomi.data.entity.stats.EntityStatsCapability;
import xyz.pixelatedw.mineminenomi.data.entity.stats.IEntityStats;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.packets.server.ability.SToggleCombatModePacket;

public class CToggleCombatModePacket {
   private boolean combatMode = false;

   public CToggleCombatModePacket() {
   }

   public CToggleCombatModePacket(boolean combatMode) {
      this.combatMode = combatMode;
   }

   public void encode(FriendlyByteBuf buffer) {
      buffer.writeBoolean(this.combatMode);
   }

   public static CToggleCombatModePacket decode(FriendlyByteBuf buffer) {
      CToggleCombatModePacket msg = new CToggleCombatModePacket();
      msg.combatMode = buffer.readBoolean();
      return msg;
   }

   public static void handle(CToggleCombatModePacket message, Supplier<NetworkEvent.Context> ctx) {
      if (((NetworkEvent.Context)ctx.get()).getDirection() == NetworkDirection.PLAY_TO_SERVER) {
         ((NetworkEvent.Context)ctx.get()).enqueueWork(() -> {
            ServerPlayer player = ((NetworkEvent.Context)ctx.get()).getSender();
            IEntityStats props = (IEntityStats)EntityStatsCapability.get(player).orElse((Object)null);
            if (props != null) {
               props.setCombatMode(message.combatMode);
               ModNetwork.sendTo(new SToggleCombatModePacket(props.isInCombatMode(), ServerConfig.getAbilityBars()), player);
            }
         });
      }

      ((NetworkEvent.Context)ctx.get()).setPacketHandled(true);
   }
}
