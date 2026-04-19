package xyz.pixelatedw.mineminenomi.packets.server.trade;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import xyz.pixelatedw.mineminenomi.api.TradeEntry;
import xyz.pixelatedw.mineminenomi.entities.mobs.TraderEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.civilians.SkypieanTraderEntity;

/** @deprecated */
@Deprecated
public class SUpdateTraderOffersPacket {
   private int traderEntity;
   private List<TradeEntry> tradeEntries;
   private int vearthTrades;

   public SUpdateTraderOffersPacket() {
   }

   public SUpdateTraderOffersPacket(int traderEntity, List<TradeEntry> tradeEntries) {
      this.traderEntity = traderEntity;
      this.tradeEntries = tradeEntries;
   }

   public SUpdateTraderOffersPacket(int traderEntity, List<TradeEntry> tradeEntries, int vearthTrades) {
      this.traderEntity = traderEntity;
      this.tradeEntries = tradeEntries;
      this.vearthTrades = vearthTrades;
   }

   public void encode(FriendlyByteBuf buffer) {
      buffer.writeInt(this.traderEntity);
      buffer.writeInt(this.tradeEntries.size());

      for(TradeEntry entry : this.tradeEntries) {
         buffer.writeItemStack(entry.getItemStack(), false);
      }

      buffer.writeInt(this.vearthTrades);
   }

   public static SUpdateTraderOffersPacket decode(FriendlyByteBuf buffer) {
      SUpdateTraderOffersPacket msg = new SUpdateTraderOffersPacket();
      msg.traderEntity = buffer.readInt();
      int size = buffer.readInt();
      List<TradeEntry> entries = new ArrayList();

      for(int i = 0; i < size; ++i) {
         entries.add(new TradeEntry(buffer.m_130267_()));
      }

      msg.tradeEntries = entries;
      msg.vearthTrades = buffer.readInt();
      return msg;
   }

   public static void handle(SUpdateTraderOffersPacket message, Supplier<NetworkEvent.Context> ctx) {
      if (((NetworkEvent.Context)ctx.get()).getDirection() == NetworkDirection.PLAY_TO_CLIENT) {
         ((NetworkEvent.Context)ctx.get()).enqueueWork(() -> SUpdateTraderOffersPacket.ClientHandler.handle(message));
      }

      ((NetworkEvent.Context)ctx.get()).setPacketHandled(true);
   }

   public static class ClientHandler {
      @OnlyIn(Dist.CLIENT)
      public static void handle(SUpdateTraderOffersPacket message) {
         Entity entity = Minecraft.m_91087_().f_91073_.m_6815_(message.traderEntity);
         if (entity instanceof TraderEntity traderEntity) {
            traderEntity.setTradingItems(message.tradeEntries);
            if (entity instanceof SkypieanTraderEntity skypeanTrader) {
               skypeanTrader.setTradesLeft(message.vearthTrades);
            }
         }

      }
   }
}
