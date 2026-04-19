package xyz.pixelatedw.mineminenomi.api.events.stats;

import net.minecraft.world.entity.player.Player;
import net.minecraftforge.eventbus.api.Cancelable;
import xyz.pixelatedw.mineminenomi.api.enums.Currency;
import xyz.pixelatedw.mineminenomi.api.enums.StatChangeSource;

public class CurrencyEvent extends StatEvent {
   private long amount;
   private Currency currency;

   public CurrencyEvent(Player player, long amount, Currency currency, StatChangeSource source) {
      super(player, source);
      this.amount = amount;
      this.currency = currency;
   }

   public Currency getCurrency() {
      return this.currency;
   }

   public long getAmount() {
      return this.amount;
   }

   public void setAmount(long amount) {
      this.amount = amount;
   }

   @Cancelable
   public static class Pre extends CurrencyEvent {
      public Pre(Player player, long amount, Currency currency, StatChangeSource source) {
         super(player, amount, currency, source);
      }
   }

   public static class Post extends CurrencyEvent {
      public Post(Player player, long amount, Currency currency, StatChangeSource source) {
         super(player, amount, currency, source);
      }
   }
}
