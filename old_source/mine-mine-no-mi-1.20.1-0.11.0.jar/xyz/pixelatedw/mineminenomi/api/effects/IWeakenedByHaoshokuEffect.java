package xyz.pixelatedw.mineminenomi.api.effects;

public interface IWeakenedByHaoshokuEffect {
   boolean isWeakenedByHaoshoku();

   default int haoshokuReductionTicks() {
      return 100;
   }
}
