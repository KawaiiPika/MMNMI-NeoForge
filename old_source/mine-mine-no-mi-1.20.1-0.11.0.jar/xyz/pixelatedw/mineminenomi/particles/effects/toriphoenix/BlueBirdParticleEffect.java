package xyz.pixelatedw.mineminenomi.particles.effects.toriphoenix;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.init.ModParticleTypes;
import xyz.pixelatedw.mineminenomi.particles.data.SimpleParticleData;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class BlueBirdParticleEffect extends ParticleEffect<Details> {
   public BlueBirdParticleEffect() {
      super(Details::new);
   }

   public void spawn(Entity entity, Level world, double posX, double posY, double posZ, Details details) {
      float incrementPI = 0.049087387F;
      float radius1 = (float)fromRangeToRange((double)0.0F, details.getMaxChargeTime(), (double)0.25F, (double)1.25F, details.getMaxChargeTime() - details.getCurrentChargeTime()) * 1.6F;
      float radius2 = (float)fromRangeToRange((double)0.0F, details.getMaxChargeTime(), 0.15, 0.3, details.getMaxChargeTime() - details.getCurrentChargeTime()) * 1.8F;
      float rotation = (float)((double)incrementPI * fromRangeToRange((double)0.0F, details.getMaxChargeTime(), (double)-128.0F, (double)128.0F, details.getCurrentChargeTime()));
      float rotation2 = (float)((double)incrementPI * fromRangeToRange((double)0.0F, details.getMaxChargeTime(), (double)-128.0F, (double)128.0F, details.getCurrentChargeTime()) + 2.0943951023931953);
      float rotation3 = (float)((double)incrementPI * fromRangeToRange((double)0.0F, details.getMaxChargeTime(), (double)-128.0F, (double)128.0F, details.getCurrentChargeTime()) - 2.0943951023931953);
      Vec3 normalizedH = getPerpendicularHorizontalLine(Vec3.f_82478_, entity.m_20154_(), radius1).m_82542_((double)2.0F, (double)2.0F, (double)2.0F);
      Vec3 normalizedV = getPerpendicularVerticalLine(Vec3.f_82478_, entity.m_20154_(), normalizedH.m_82542_(-Math.PI, -Math.PI, -Math.PI), radius2).m_82542_((double)2.0F, (double)2.0F, (double)2.0F);
      Vec3 finalPosition1 = getParticlePositionInSpiral(rotation, normalizedH, normalizedV);
      Vec3 finalPosition2 = getParticlePositionInSpiral(rotation2, normalizedH, normalizedV);
      Vec3 finalPosition3 = getParticlePositionInSpiral(rotation3, normalizedH, normalizedV);
      SimpleParticleData data = new SimpleParticleData((ParticleType)ModParticleTypes.BLUE_FLAME.get());
      data.setLife(15);
      data.setSize(4.0F);
      data.setMotion(entity.m_20154_().f_82479_ / (double)10.0F, entity.m_20154_().f_82480_ / (double)10.0F, entity.m_20154_().f_82481_ / (double)10.0F);
      double posX1 = entity.m_20185_() + finalPosition1.f_82479_;
      double posY1 = entity.m_20188_() + finalPosition1.f_82480_;
      double posZ1 = entity.m_20189_() + finalPosition1.f_82481_;
      double posX2 = entity.m_20185_() + finalPosition2.f_82479_;
      double posY2 = entity.m_20188_() + finalPosition2.f_82480_;
      double posZ2 = entity.m_20189_() + finalPosition2.f_82481_;
      double posX3 = entity.m_20185_() + finalPosition3.f_82479_;
      double posY3 = entity.m_20188_() + finalPosition3.f_82480_;
      double posZ3 = entity.m_20189_() + finalPosition3.f_82481_;
      world.m_6493_(data, true, posX1, posY1, posZ1, (double)0.0F, (double)0.0F, (double)0.0F);
      world.m_6493_(data, true, posX2, posY2, posZ2, (double)0.0F, (double)0.0F, (double)0.0F);
      world.m_6493_(data, true, posX3, posY3, posZ3, (double)0.0F, (double)0.0F, (double)0.0F);
   }

   private static Vec3 getPerpendicularHorizontalLine(Vec3 startPoint, Vec3 endPoint, float radius) {
      Vec3 axis = new Vec3((double)1.0F, (double)0.0F, (double)1.0F);
      Vec3 newStart = startPoint.m_82559_(axis);
      Vec3 newEnd = endPoint.m_82559_(axis);
      Vec3 line = newEnd.m_82546_(newStart);
      line.m_82520_(line.m_7096_() == (double)0.0F ? 0.001 : (double)0.0F, (double)0.0F, line.m_7094_() == (double)0.0F ? 0.001 : (double)0.0F);
      Vec3 newLine = new Vec3(line.f_82481_, (double)0.0F, -line.f_82479_);
      return newLine.m_82541_().m_82542_((double)radius, (double)radius, (double)radius);
   }

   private static Vec3 getPerpendicularVerticalLine(Vec3 startPoint, Vec3 endPoint, Vec3 horizontalPoint, float radius) {
      Vec3 vec1 = endPoint.m_82546_(startPoint);
      Vec3 vec2 = horizontalPoint.m_82546_(startPoint);
      Vec3 newLine = vec1.m_82537_(vec2);
      return newLine.m_82541_().m_82542_((double)radius, (double)radius, (double)radius);
   }

   private static double fromRangeToRange(double oldMin, double oldMax, double min, double max, double oldValue) {
      return (oldValue - oldMin) * (max - min) / (oldMax - oldMin) + min;
   }

   private static Vec3 getParticlePositionInSpiral(float rotation, Vec3 normalizedH, Vec3 normalizedV) {
      Vec3 incrementH = normalizedH.m_82542_(Math.cos((double)rotation), Math.cos((double)rotation), Math.cos((double)rotation));
      Vec3 incrementV = normalizedV.m_82542_(Math.sin((double)rotation), Math.sin((double)rotation), Math.sin((double)rotation));
      Vec3 HVVec = incrementV.m_82546_(incrementH);
      return incrementV.m_82549_(HVVec.m_82542_((double)0.5F, (double)0.5F, (double)0.5F));
   }

   public static class Details extends ParticleEffect.Details {
      private double currentChargeTime;
      private double maxChargeTime;

      public void save(CompoundTag nbt) {
         nbt.m_128347_("currentChargeTime", this.currentChargeTime);
         nbt.m_128347_("maxChargeTime", this.maxChargeTime);
      }

      public void load(CompoundTag nbt) {
         this.currentChargeTime = nbt.m_128459_("currentChargeTime");
         this.maxChargeTime = nbt.m_128459_("maxChargeTime");
      }

      public void setCurrentChargeTime(double currChargeTime) {
         this.currentChargeTime = currChargeTime;
      }

      public void setMaxChargeTime(double maxChargeTime) {
         this.maxChargeTime = maxChargeTime;
      }

      public double getMaxChargeTime() {
         return this.maxChargeTime;
      }

      public double getCurrentChargeTime() {
         return this.currentChargeTime;
      }
   }
}
