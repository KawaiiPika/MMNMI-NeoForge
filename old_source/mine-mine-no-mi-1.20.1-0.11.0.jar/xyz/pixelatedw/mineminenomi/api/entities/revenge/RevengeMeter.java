package xyz.pixelatedw.mineminenomi.api.entities.revenge;

import com.google.common.collect.EvictingQueue;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;

public class RevengeMeter {
   private static final int QUEUE_ENTRIES = 20;
   private LivingEntity entity;
   private int revengeValue;
   private int maxRevengeValue = 100;
   private int passiveDecreasingValue = 1;
   private List<IRevengeCheck> checks = new ArrayList();
   private Map<Class<? extends IRevengeCheck>, Integer> passedChecksTotal = new HashMap();
   private Queue<IRevengeCheck> passedChecksHistory = EvictingQueue.create(20);

   public RevengeMeter(LivingEntity entity, int maxRevenge, int passiveDecreasingValue) {
      this.entity = entity;
      this.maxRevengeValue = maxRevenge;
      this.passiveDecreasingValue = passiveDecreasingValue;
   }

   public void addCheck(IRevengeCheck check) {
      check.setParent(this);
      this.checks.add(check);
   }

   public void tick() {
      if (this.entity.m_9236_().m_46467_() % 60L == 0L) {
         if (this.revengeValue > 0 && this.passiveDecreasingValue > 0) {
            this.reduceRevengeValue(this.passiveDecreasingValue);
         }

      }
   }

   public List<IRevengeCheck> getChecks() {
      return this.checks;
   }

   public Map<Class<? extends IRevengeCheck>, Integer> getLastChecksMap() {
      return this.passedChecksTotal;
   }

   public Queue<IRevengeCheck> getLastChecks() {
      return this.passedChecksHistory;
   }

   public boolean isCheckPresentInLastNEntries(Class<? extends IRevengeCheck> clz, int entriesToCheck, int neededEntries) {
      if (this.passedChecksHistory.isEmpty()) {
         return false;
      } else {
         entriesToCheck = Math.min(entriesToCheck, this.passedChecksHistory.size());
         entriesToCheck = Math.max(0, this.passedChecksHistory.size() - entriesToCheck);
         long count = this.passedChecksHistory.stream().skip((long)entriesToCheck).filter((c) -> c.getClass().equals(clz)).count();
         return count >= (long)neededEntries;
      }
   }

   public int countCheckTriggers(Class<? extends IRevengeCheck> clz) {
      return (Integer)this.getLastChecksMap().getOrDefault(clz, 0);
   }

   public void passCheck(IRevengeCheck check) {
      this.passedChecksHistory.add(check);
      Integer checksMapVal = (Integer)this.passedChecksTotal.get(check.getClass());
      if (checksMapVal == null) {
         this.passedChecksTotal.put(check.getClass(), 1);
      } else {
         this.passedChecksTotal.put(check.getClass(), checksMapVal + 1);
      }

   }

   public void resetMarkers() {
      this.revengeValue = 0;
      this.passedChecksTotal.clear();
      this.passedChecksHistory.clear();
      this.checks.forEach((check) -> check.resetMarkers());
   }

   public boolean isRevengeMaxed() {
      return this.getRevengePercentage() >= 1.0F;
   }

   public boolean isRevengeAbove(float percentage) {
      return this.getRevengePercentage() > percentage;
   }

   public boolean isRevengeUnder(float percentage) {
      return this.getRevengePercentage() < percentage;
   }

   public int getRevengeValue() {
      return Mth.m_14045_(this.revengeValue, 0, this.maxRevengeValue);
   }

   public int getMaxRevengeValue() {
      return this.maxRevengeValue;
   }

   public void addRevengeValue(int value) {
      this.setRevengeValue(this.revengeValue + value);
   }

   public void addRevengeValueFrom(IRevengeCheck check) {
      this.addRevengeValue(check.revengeMeterGain());
      this.passCheck(check);
   }

   public void reduceRevengeValue(int value) {
      this.setRevengeValue(this.revengeValue - value);
   }

   public void setRevengeValue(int value) {
      this.revengeValue = Mth.m_14045_(value, 0, this.maxRevengeValue);
   }

   public float getRevengePercentage() {
      return (float)this.getRevengeValue() / (float)this.maxRevengeValue;
   }
}
