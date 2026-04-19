package xyz.pixelatedw.mineminenomi.init;

public enum BuildMode {
   RELEASE,
   DEV,
   EARLY_ACCESS,
   QA,
   PROMO,
   PUBLIC_QA;

   public static final BuildMode MODE = RELEASE;

   // $FF: synthetic method
   private static BuildMode[] $values() {
      return new BuildMode[]{RELEASE, DEV, EARLY_ACCESS, QA, PROMO, PUBLIC_QA};
   }
}
