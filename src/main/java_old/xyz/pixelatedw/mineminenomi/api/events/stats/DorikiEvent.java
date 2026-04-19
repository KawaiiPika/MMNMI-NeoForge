package xyz.pixelatedw.mineminenomi.api.events.stats;

import net.minecraft.world.entity.player.Player;
import net.minecraftforge.eventbus.api.Cancelable;
import xyz.pixelatedw.mineminenomi.api.enums.StatChangeSource;

public class DorikiEvent extends StatEvent {
   private double doriki;

   public DorikiEvent(Player player, double doriki, StatChangeSource source) {
      super(player, source);
      this.doriki = doriki;
   }

   public double getDoriki() {
      return this.doriki;
   }

   public void setDoriki(double amount) {
      this.doriki = amount;
   }

   @Cancelable
   public static class Pre extends DorikiEvent {
      public Pre(Player player, double doriki, StatChangeSource source) {
         super(player, doriki, source);
      }
   }

   public static class Post extends DorikiEvent {
      public Post(Player player, double doriki, StatChangeSource source) {
         super(player, doriki, source);
      }
   }
}
