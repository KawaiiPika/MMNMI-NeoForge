package xyz.pixelatedw.mineminenomi.api.util;

public class IntRange implements INumberRange<Integer> {
   private int min;
   private int max;

   public IntRange(int val) {
      this.min = val;
      this.max = val;
   }

   public IntRange(int min, int max) {
      this.min = min;
      this.max = max;
   }

   public boolean isInfinite() {
      return this.min == Integer.MAX_VALUE || this.max == Integer.MAX_VALUE;
   }

   public boolean isRange() {
      return !this.isFixed();
   }

   public boolean isFixed() {
      return this.min == this.max;
   }

   public Integer getMin() {
      return this.min;
   }

   public Integer getMax() {
      return this.max;
   }
}
