package xyz.pixelatedw.mineminenomi.api.events.stats;

import net.minecraft.world.entity.player.Player;
import net.minecraftforge.eventbus.api.Cancelable;
import xyz.pixelatedw.mineminenomi.api.enums.StatChangeSource;

public class BountyEvent extends StatEvent {
   private long bounty;

   public BountyEvent(Player player, long bounty, StatChangeSource source) {
      super(player, source);
      this.bounty = bounty;
   }

   public long getBounty() {
      return this.bounty;
   }

   public void setBounty(long amount) {
      this.bounty = amount;
   }

   @Cancelable
   public static class Pre extends BountyEvent {
      public Pre(Player player, long bounty, StatChangeSource source) {
         super(player, bounty, source);
      }
   }

   public static class Post extends BountyEvent {
      public Post(Player player, long bounty, StatChangeSource source) {
         super(player, bounty, source);
      }
   }
}
