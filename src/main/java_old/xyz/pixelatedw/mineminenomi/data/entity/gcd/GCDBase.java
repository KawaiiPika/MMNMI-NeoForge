package xyz.pixelatedw.mineminenomi.data.entity.gcd;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import xyz.pixelatedw.mineminenomi.api.util.Interval;
import xyz.pixelatedw.mineminenomi.init.ModAttributes;

public class GCDBase implements IGCDData {
   private static final int DEFAULT_GCD = 20;
   private LivingEntity owner;
   private int previousGCD = 20;
   private Interval gcdInterval = new Interval(20);
   private boolean onGCD = false;

   public GCDBase(LivingEntity owner) {
      this.owner = owner;
   }

   public void startGCD() {
      if (!this.isOnGCD()) {
         int defaultGCD = this.getDefaultGCD();
         if (this.previousGCD != defaultGCD) {
            this.previousGCD = defaultGCD;
            this.gcdInterval = new Interval(defaultGCD);
         }

         this.gcdInterval.restartIntervalToMax();
         this.onGCD = true;
      }
   }

   public boolean isOnGCD() {
      return this.onGCD;
   }

   public void tickGCD() {
      if (this.onGCD && this.gcdInterval.canTick()) {
         this.onGCD = false;
      }

   }

   public int getCurrentGCD() {
      return this.gcdInterval.getTick();
   }

   public int getDefaultGCD() {
      try {
         AttributeInstance attr = this.owner.m_21051_((Attribute)ModAttributes.GCD.get());
         return attr == null ? 20 : (int)Math.round(attr.m_22135_());
      } catch (Exception var2) {
         return 20;
      }
   }

   public CompoundTag serializeNBT() {
      CompoundTag nbt = new CompoundTag();
      nbt.m_128379_("onGCD", this.onGCD);
      return nbt;
   }

   public void deserializeNBT(CompoundTag nbt) {
      boolean onGCD = nbt.m_128471_("onGCD");
      if (onGCD) {
         this.startGCD();
      }

   }
}
