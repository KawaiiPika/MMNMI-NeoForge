package xyz.pixelatedw.mineminenomi.api.util;

public class FloatRange implements INumberRange<Float> {
   private float min;
   private float max;

   public FloatRange(float val) {
      this.min = val;
      this.max = val;
   }

   public FloatRange(float min, float max) {
      this.min = min;
      this.max = max;
   }

   public boolean isInfinite() {
      return this.min == Float.POSITIVE_INFINITY || this.max == Float.POSITIVE_INFINITY;
   }

   public boolean isRange() {
      return !this.isFixed();
   }

   public boolean isFixed() {
      return this.min == this.max;
   }

   public Float getMin() {
      return this.min;
   }

   public Float getMax() {
      return this.max;
   }
}
