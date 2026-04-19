package xyz.pixelatedw.mineminenomi.api.entities;

public class ClientBossExtraEvent {
   private int totalBars;
   private int activeBars;

   public void setTotalBars(int amount) {
      this.totalBars = amount;
   }

   public int getTotalBars() {
      return this.totalBars;
   }

   public void setActiveBars(int amount) {
      this.activeBars = amount;
   }

   public int getActiveBars() {
      return this.activeBars;
   }
}
