package xyz.pixelatedw.mineminenomi.api.events.stats;

import net.minecraft.world.entity.player.Player;
import net.minecraftforge.eventbus.api.Cancelable;
import xyz.pixelatedw.mineminenomi.api.enums.HakiType;
import xyz.pixelatedw.mineminenomi.api.enums.StatChangeSource;

public class HakiExpEvent extends StatEvent {
   private float hakiExp;
   private HakiType hakiType;

   public HakiExpEvent(Player player, float hakiExp, HakiType hakiType, StatChangeSource source) {
      super(player, source);
      this.hakiExp = hakiExp;
      this.hakiType = hakiType;
   }

   public HakiType getHakiType() {
      return this.hakiType;
   }

   public float getHakiExp() {
      return this.hakiExp;
   }

   public void setHakiExp(float amount) {
      this.hakiExp = amount;
   }

   @Cancelable
   public static class Pre extends HakiExpEvent {
      public Pre(Player player, float hakiExp, HakiType hakiType, StatChangeSource source) {
         super(player, hakiExp, hakiType, source);
      }
   }

   public static class Post extends HakiExpEvent {
      public Post(Player player, float hakiExp, HakiType hakiType, StatChangeSource source) {
         super(player, hakiExp, hakiType, source);
      }
   }
}
