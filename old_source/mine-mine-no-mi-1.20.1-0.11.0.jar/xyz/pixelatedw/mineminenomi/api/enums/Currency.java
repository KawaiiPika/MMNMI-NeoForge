package xyz.pixelatedw.mineminenomi.api.enums;

public enum Currency {
   BELLY,
   EXTOL;

   public static final long BELLY_TO_EXTOL = 10000L;

   public static long getExtolFromBelly(long belly) {
      return belly * 10000L;
   }

   public static long getBellyFromExtol(long extol) {
      return extol < 10000L ? -1L : extol / 10000L;
   }

   // $FF: synthetic method
   private static Currency[] $values() {
      return new Currency[]{BELLY, EXTOL};
   }
}
