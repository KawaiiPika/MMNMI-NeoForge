package xyz.pixelatedw.mineminenomi.api.util;

public class PausableTimer {
   private long startTime = 0L;
   private long elapsed = 0L;
   private long pauseTime = 0L;
   private boolean paused = false;

   public void start() {
      if (this.startTime == 0L) {
         this.startTime = System.currentTimeMillis();
      } else if (this.paused) {
         this.elapsed += System.currentTimeMillis() - this.pauseTime;
         this.paused = false;
      }

   }

   public void pause() {
      if (!this.paused && this.startTime != 0L) {
         this.pauseTime = System.currentTimeMillis();
         this.paused = true;
      }

   }

   public void reset() {
      this.startTime = 0L;
      this.elapsed = 0L;
      this.pauseTime = 0L;
      this.paused = false;
   }

   public long getElapsedTime() {
      if (this.startTime == 0L) {
         return 0L;
      } else {
         return this.paused ? this.pauseTime - this.startTime - this.elapsed : System.currentTimeMillis() - this.startTime - this.elapsed;
      }
   }
}
