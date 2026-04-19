package xyz.pixelatedw.mineminenomi.api.util;

public class Interval {
   protected int interval;
   protected float tick = 0.0F;
   private boolean trackTPS = false;

   public Interval(int interval) {
      this.interval = interval;
   }

   public static Interval startAtZero(int interval) {
      return new Interval(interval);
   }

   public static Interval startAtMax(int interval) {
      Interval intervalObj = new Interval(interval);
      intervalObj.tick = (float)interval;
      return intervalObj;
   }

   public int getInterval() {
      return this.interval;
   }

   public <T> T trackTPS() {
      this.trackTPS = true;
      return (T)this;
   }

   public boolean canTick() {
      if (this.trackTPS) {
         this.tick -= TPSDelta.INSTANCE.getDeltaTime();
      } else {
         --this.tick;
      }

      if (this.tick <= 0.0F) {
         this.tick = (float)this.interval;
         return true;
      } else {
         return false;
      }
   }

   public void restartIntervalToZero() {
      this.tick = 0.0F;
   }

   public void restartIntervalToMax() {
      this.tick = (float)this.interval;
   }

   public int getTick() {
      return Math.round(this.tick);
   }

   public static class Mutable extends Interval {
      public Mutable(int interval) {
         super(interval);
      }

      public void setInterval(int interval) {
         this.interval = interval;
         this.restartIntervalToMax();
      }
   }
}
