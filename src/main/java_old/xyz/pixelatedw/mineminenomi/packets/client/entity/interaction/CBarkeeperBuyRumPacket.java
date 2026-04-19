package xyz.pixelatedw.mineminenomi.packets.client.entity.interaction;

import java.util.function.Supplier;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import xyz.pixelatedw.mineminenomi.api.enums.StatChangeSource;
import xyz.pixelatedw.mineminenomi.data.entity.stats.EntityStatsCapability;
import xyz.pixelatedw.mineminenomi.data.entity.stats.IEntityStats;
import xyz.pixelatedw.mineminenomi.init.ModItems;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18nInteractions;
import xyz.pixelatedw.mineminenomi.packets.server.SSyncEntityStatsPacket;
import xyz.pixelatedw.mineminenomi.packets.server.ui.dialogues.SDialogueResponsePacket;

public class CBarkeeperBuyRumPacket {
   public void encode(FriendlyByteBuf buffer) {
   }

   public static CBarkeeperBuyRumPacket decode(FriendlyByteBuf buffer) {
      CBarkeeperBuyRumPacket msg = new CBarkeeperBuyRumPacket();
      return msg;
   }

   public static void handle(CBarkeeperBuyRumPacket message, Supplier<NetworkEvent.Context> ctx) {
      if (((NetworkEvent.Context)ctx.get()).getDirection() == NetworkDirection.PLAY_TO_SERVER) {
         ((NetworkEvent.Context)ctx.get()).enqueueWork(() -> {
            ServerPlayer player = ((NetworkEvent.Context)ctx.get()).getSender();
            IEntityStats props = (IEntityStats)EntityStatsCapability.get(player).orElse((Object)null);
            if (props != null) {
               int payment = 100;
               if (props.getBelly() < (long)payment) {
                  ModNetwork.sendTo(SDialogueResponsePacket.setMessage(ModI18nInteractions.getRandomNoBellyMessage()), player);
               } else {
                  props.alterBelly((long)(-payment), StatChangeSource.STORE);
                  player.m_36356_(((Item)ModItems.BOTTLE_OF_RUM.get()).m_7968_());
                  ModNetwork.sendTo(new SSyncEntityStatsPacket(player), player);
                  ModNetwork.sendTo(SDialogueResponsePacket.closeDialogue(), player);
               }
            }
         });
      }

      ((NetworkEvent.Context)ctx.get()).setPacketHandled(true);
   }
}
