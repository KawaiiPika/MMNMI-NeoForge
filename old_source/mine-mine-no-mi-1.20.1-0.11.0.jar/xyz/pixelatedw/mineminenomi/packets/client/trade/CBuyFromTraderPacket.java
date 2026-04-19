package xyz.pixelatedw.mineminenomi.packets.client.trade;

import java.util.Optional;
import java.util.function.Supplier;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import xyz.pixelatedw.mineminenomi.api.TradeEntry;
import xyz.pixelatedw.mineminenomi.api.enums.Currency;
import xyz.pixelatedw.mineminenomi.api.enums.StatChangeSource;
import xyz.pixelatedw.mineminenomi.data.entity.stats.EntityStatsCapability;
import xyz.pixelatedw.mineminenomi.data.entity.stats.IEntityStats;
import xyz.pixelatedw.mineminenomi.entities.mobs.TraderEntity;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.packets.server.SSyncEntityStatsPacket;

public class CBuyFromTraderPacket {
   private int traderId;
   private ItemStack stack;
   private int amount;

   public CBuyFromTraderPacket() {
   }

   public CBuyFromTraderPacket(int traderId, ItemStack stack, int amount) {
      this.traderId = traderId;
      this.stack = stack;
      this.amount = amount;
   }

   public void encode(FriendlyByteBuf buffer) {
      buffer.writeInt(this.traderId);
      buffer.writeItemStack(this.stack, true);
      buffer.writeInt(this.amount);
   }

   public static CBuyFromTraderPacket decode(FriendlyByteBuf buffer) {
      CBuyFromTraderPacket msg = new CBuyFromTraderPacket();
      msg.traderId = buffer.readInt();
      msg.stack = buffer.m_130267_();
      msg.amount = buffer.readInt();
      return msg;
   }

   public static void handle(CBuyFromTraderPacket message, Supplier<NetworkEvent.Context> ctx) {
      if (((NetworkEvent.Context)ctx.get()).getDirection() == NetworkDirection.PLAY_TO_SERVER) {
         ((NetworkEvent.Context)ctx.get()).enqueueWork(() -> {
            ServerPlayer player = ((NetworkEvent.Context)ctx.get()).getSender();
            Entity entity = player.m_9236_().m_6815_(message.traderId);
            if (entity != null) {
               if (entity instanceof TraderEntity) {
                  TraderEntity trader = (TraderEntity)entity;
                  if (!(player.m_20280_(trader) > (double)25.0F) && message.amount > 0) {
                     ItemStack stack = message.stack;
                     if (stack != null && !stack.m_41619_()) {
                        Optional<TradeEntry> optional = trader.getTradingItems().stream().filter((entry) -> entry.getItemStack().m_41720_().equals(stack.m_41720_())).findFirst();
                        if (optional.isPresent()) {
                           TradeEntry tradeEntry = (TradeEntry)optional.get();
                           long emptySlots = player.m_150109_().f_35974_.stream().filter(ItemStack::m_41619_).count();
                           if (emptySlots >= (long)Mth.m_14167_((float)(message.amount / 64))) {
                              if (message.amount <= tradeEntry.getCount() || tradeEntry.hasInfiniteStock()) {
                                 IEntityStats props = (IEntityStats)EntityStatsCapability.get(player).orElse((Object)null);
                                 if (props != null) {
                                    int totalPrice = tradeEntry.getPrice() * message.amount;
                                    long currency = trader.getCurrency() == Currency.BELLY ? props.getBelly() : props.getExtol();
                                    if (currency >= (long)totalPrice) {
                                       ItemStack boughtStack = new ItemStack(tradeEntry.getItemStack().m_41720_());
                                       boughtStack.m_41764_(message.amount);
                                       if (tradeEntry.getItemStack().m_41784_().m_128471_("isClone")) {
                                          boughtStack.m_41784_().m_128379_("isClone", true);
                                       }

                                       if (!tradeEntry.hasInfiniteStock()) {
                                          int count = tradeEntry.getCount() - message.amount;
                                          if (count <= 0) {
                                             trader.getTradingItems().remove(tradeEntry);
                                          } else {
                                             tradeEntry.setCount(count);
                                          }
                                       }

                                       player.m_150109_().m_36054_(boughtStack);
                                       if (trader.getCurrency() == Currency.BELLY) {
                                          props.alterBelly((long)(-totalPrice), StatChangeSource.NATURAL);
                                       } else if (trader.getCurrency() == Currency.EXTOL) {
                                          props.alterExtol((long)(-totalPrice), StatChangeSource.NATURAL);
                                       }

                                       ModNetwork.sendTo(new SSyncEntityStatsPacket(player), player);
                                       trader.setTradingItems(trader.getTradingItems());
                                    }
                                 }
                              }
                           }
                        }
                     }
                  }
               }
            }
         });
      }

      ((NetworkEvent.Context)ctx.get()).setPacketHandled(true);
   }
}
