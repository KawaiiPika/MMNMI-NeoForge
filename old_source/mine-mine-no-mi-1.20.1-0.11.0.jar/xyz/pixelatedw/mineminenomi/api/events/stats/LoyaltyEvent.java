package xyz.pixelatedw.mineminenomi.api.events.stats;

import net.minecraft.world.entity.player.Player;
import net.minecraftforge.eventbus.api.Cancelable;
import xyz.pixelatedw.mineminenomi.api.enums.StatChangeSource;

public class LoyaltyEvent extends StatEvent {
   private double loyalty;

   public LoyaltyEvent(Player player, double loyalty, StatChangeSource source) {
      super(player, source);
      this.loyalty = loyalty;
   }

   public double getLoyalty() {
      return this.loyalty;
   }

   public void setLoyalty(double amount) {
      this.loyalty = amount;
   }

   @Cancelable
   public static class Pre extends LoyaltyEvent {
      public Pre(Player player, double loyalty, StatChangeSource source) {
         super(player, loyalty, source);
      }
   }

   public static class Post extends LoyaltyEvent {
      public Post(Player player, double loyalty, StatChangeSource source) {
         super(player, loyalty, source);
      }
   }
}
