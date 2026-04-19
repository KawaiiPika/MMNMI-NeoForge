package xyz.pixelatedw.mineminenomi.api.util;

import xyz.pixelatedw.mineminenomi.ModMain;
import xyz.pixelatedw.mineminenomi.config.ServerConfig;

public class TPSDelta {
   public static final TPSDelta INSTANCE = new TPSDelta();
   private boolean init = false;
   private boolean hasExpTimers = true;
   private long lastTickTime;
   private float deltaTime;

   public TPSDelta() {
      this.lastTickTime = ModMain.PAUSABLE_TIMER.getElapsedTime();
      this.deltaTime = 0.0F;
   }

   public void init() {
      this.init = true;
      this.hasExpTimers = ServerConfig.hasExperimentalTimers();
   }

   public void tick() {
      if (this.hasExpTimers) {
         long currentTime = ModMain.PAUSABLE_TIMER.getElapsedTime();
         long timeElapsed = currentTime - this.lastTickTime;
         this.lastTickTime = currentTime;
         this.deltaTime = Math.max((float)timeElapsed / 50.0F, 1.0F);
      }
   }

   public float getDeltaTime() {
      if (!this.hasExpTimers) {
         return 1.0F;
      } else if (!this.init) {
         throw new RuntimeException("TPSDelta was never initialized!");
      } else {
         return this.deltaTime;
      }
   }

   public long getLastTickTime() {
      if (!this.init) {
         throw new RuntimeException("TPSDelta was never initialized!");
      } else {
         return this.lastTickTime;
      }
   }
}
