package xyz.pixelatedw.mineminenomi.api.abilities.components;

public enum BonusOperation {
   ADD,
   MUL;

   public String toString() {
      return this.toString().toUpperCase();
   }

   // $FF: synthetic method
   private static BonusOperation[] $values() {
      return new BonusOperation[]{ADD, MUL};
   }
}
