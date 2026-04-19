package xyz.pixelatedw.mineminenomi.api.entities.revenge;

public abstract class BaseRevengeCheck implements IRevengeCheck {
   private RevengeMeter parent;
   private boolean isFlagged;

   public void setParent(RevengeMeter parent) {
      this.parent = parent;
   }

   public void addRevengeValue(int value) {
      this.parent.addRevengeValue(value);
   }

   public void resetMarkers() {
      this.isFlagged = false;
   }

   public void markFlagged() {
      this.isFlagged = true;
   }

   public boolean isFlagged() {
      return this.isFlagged;
   }
}
