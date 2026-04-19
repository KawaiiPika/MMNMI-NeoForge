package xyz.pixelatedw.mineminenomi.packets.client.ui;

import java.util.function.Supplier;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import xyz.pixelatedw.mineminenomi.data.entity.stats.EntityStatsCapability;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.packets.server.SSyncAbilityDataPacket;
import xyz.pixelatedw.mineminenomi.packets.server.ui.SOpenAbilitySelectionScreenPacket;

public class COpenAbilitiesListScreenPacket {
   public void encode(FriendlyByteBuf buffer) {
   }

   public static COpenAbilitiesListScreenPacket decode(FriendlyByteBuf buffer) {
      COpenAbilitiesListScreenPacket msg = new COpenAbilitiesListScreenPacket();
      return msg;
   }

   public static void handle(COpenAbilitiesListScreenPacket message, Supplier<NetworkEvent.Context> ctx) {
      if (((NetworkEvent.Context)ctx.get()).getDirection() == NetworkDirection.PLAY_TO_SERVER) {
         ((NetworkEvent.Context)ctx.get()).enqueueWork(() -> {
            ServerPlayer player = ((NetworkEvent.Context)ctx.get()).getSender();
            boolean hasSelection = (Boolean)EntityStatsCapability.get(player).map((props) -> props.hasRace() && props.hasFaction() && props.hasFightingStyle()).orElse(false);
            if (hasSelection) {
               ModNetwork.sendTo(new SSyncAbilityDataPacket(player), player);
               ModNetwork.sendTo(new SOpenAbilitySelectionScreenPacket(), player);
            }
         });
      }

      ((NetworkEvent.Context)ctx.get()).setPacketHandled(true);
   }
}
