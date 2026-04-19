package xyz.pixelatedw.mineminenomi.api.challenges;

public enum ChallengeDifficulty {
   STANDARD,
   HARD,
   ULTIMATE;

   public String capitalize() {
      ChallengeDifficulty diff = values()[this.ordinal()];
      String var10000 = diff.name().substring(0, 1).toUpperCase();
      return var10000 + diff.name().substring(1).toLowerCase();
   }

   // $FF: synthetic method
   private static ChallengeDifficulty[] $values() {
      return new ChallengeDifficulty[]{STANDARD, HARD, ULTIMATE};
   }
}
