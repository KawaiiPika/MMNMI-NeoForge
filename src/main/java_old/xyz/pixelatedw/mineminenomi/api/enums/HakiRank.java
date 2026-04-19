package xyz.pixelatedw.mineminenomi.api.enums;

import net.minecraft.world.entity.player.Player;
import xyz.pixelatedw.mineminenomi.abilities.haki.HakiHelper;

public enum HakiRank {
   BEGINNER,
   INITIATE,
   ADEPT,
   PROFICIENT,
   MASTER;

   public static boolean isAbove(HakiRank rank, HakiType type, Player player) {
      return HakiHelper.getHakiRank(type, player).compareTo(rank) <= 0;
   }

   public static boolean isBelow(HakiRank rank, HakiType type, Player player) {
      return HakiHelper.getHakiRank(type, player).compareTo(rank) > 0;
   }

   // $FF: synthetic method
   private static HakiRank[] $values() {
      return new HakiRank[]{BEGINNER, INITIATE, ADEPT, PROFICIENT, MASTER};
   }
}
