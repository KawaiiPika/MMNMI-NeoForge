package xyz.pixelatedw.mineminenomi.api.effects;

public interface IIgnoreMilkEffect {
   default boolean isRemoveable() {
      return false;
   }
}
