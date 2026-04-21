package xyz.pixelatedw.mineminenomi.api.enums;

import net.minecraft.world.entity.player.Player;

public enum HakiRank {
   BEGINNER,
   INITIATE,
   ADEPT,
   PROFICIENT,
   MASTER;

   public static boolean isAbove(HakiRank rank, HakiType type, Player player) {
      return false;
   }

   public static boolean isBelow(HakiRank rank, HakiType type, Player player) {
      return false;
   }

   // $FF: synthetic method
   private static HakiRank[] $values() {
      return new HakiRank[]{BEGINNER, INITIATE, ADEPT, PROFICIENT, MASTER};
   }
}
