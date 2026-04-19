package xyz.pixelatedw.mineminenomi.api.entities.revenge;

public interface IRevengeCheck {
   void setParent(RevengeMeter var1);

   int revengeMeterGain();

   void resetMarkers();

   boolean isFlagged();
}
