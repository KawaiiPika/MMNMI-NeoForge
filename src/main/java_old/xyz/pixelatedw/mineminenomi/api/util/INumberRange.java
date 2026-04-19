package xyz.pixelatedw.mineminenomi.api.util;

public interface INumberRange<N extends Number> {
   boolean isInfinite();

   boolean isFixed();

   boolean isRange();

   N getMin();

   N getMax();
}
