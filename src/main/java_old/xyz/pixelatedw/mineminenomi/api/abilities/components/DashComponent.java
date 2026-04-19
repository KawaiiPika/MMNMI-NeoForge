package xyz.pixelatedw.mineminenomi.api.abilities.components;

import java.util.function.Consumer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;

public class DashComponent extends AbilityComponent<IAbility> {
   private Consumer<LivingEntity> dashConsumer;
   private int dashDuration;
   private boolean fixedCamera = false;

   public DashComponent(IAbility ability) {
      super((AbilityComponentKey)ModAbilityComponents.DASH.get(), ability);
   }

   public DashComponent setFixedCamera(boolean flag) {
      this.fixedCamera = flag;
      return this;
   }

   protected void doTick(LivingEntity entity) {
      if (!this.getAbility().hasComponent((AbilityComponentKey)ModAbilityComponents.DISABLE.get()) || !((DisableComponent)this.getAbility().getComponent((AbilityComponentKey)ModAbilityComponents.DISABLE.get()).get()).isDisabled()) {
         if (this.dashDuration > 0) {
            this.dashConsumer.accept(entity);
            if (--this.dashDuration == 0) {
            }
         }

      }
   }

   public void dashXZ(LivingEntity entity, float speedXZ) {
      this.move(entity, speedXZ, 0.0F, 0.0F, false);
   }

   public void dashXYZ(LivingEntity entity, float speedXYZ) {
      this.dashXYZ(entity, speedXYZ, speedXYZ);
   }

   public void dashXYZ(LivingEntity entity, float speedXZ, float speedY) {
      this.move(entity, speedXZ, speedY, 0.0F, true);
   }

   public void dashXYZ(LivingEntity entity, float speedXZ, float speedY, float bumpY) {
      this.move(entity, speedXZ, speedY, bumpY, true);
   }

   public void timedDashXZ(LivingEntity entity, float speedXZ, int duration) {
      this.timedMove(entity, speedXZ, 0.0F, false, duration);
   }

   public void timedDashXYZ(LivingEntity entity, float speedXYZ, int duration) {
      this.timedMove(entity, speedXYZ, speedXYZ, true, duration);
   }

   public void timedDashXYZ(LivingEntity entity, float speedXZ, float speedY, int duration) {
      this.timedMove(entity, speedXZ, speedY, true, duration);
   }

   private void move(LivingEntity entity, float speedXZ, float speedY, float bumpY, boolean useY) {
      float finalSpeedXZ = Mth.m_14036_(speedXZ, -5.0F, 5.0F);
      float finalSpeedY = Mth.m_14036_(speedY, -5.0F, 5.0F);
      this.dashDuration = 0;
      Vec3 lookVec = entity.m_20154_().m_82541_().m_82542_((double)finalSpeedXZ, useY ? (double)finalSpeedY : (double)0.0F, (double)finalSpeedXZ);
      AbilityHelper.setDeltaMovement(entity, lookVec.f_82479_, useY ? lookVec.f_82480_ + (double)bumpY : entity.m_20184_().m_7098_(), lookVec.f_82481_);
   }

   private void timedMove(LivingEntity entity, float speedXZ, float speedY, boolean useY, int duration) {
      float finalSpeedXZ = Mth.m_14036_(speedXZ, -5.0F, 5.0F);
      float finalSpeedY = Mth.m_14036_(speedY, -5.0F, 5.0F);
      this.dashDuration = duration;
      Vec3 defaultLookVec = entity.m_20154_().m_82541_().m_82542_((double)finalSpeedXZ, useY ? (double)finalSpeedY : (double)0.0F, (double)finalSpeedXZ);
      this.dashConsumer = (target) -> {
         Vec3 lookVec = defaultLookVec;
         if (!this.fixedCamera) {
            lookVec = entity.m_20154_().m_82541_().m_82542_((double)finalSpeedXZ, useY ? (double)finalSpeedY : (double)0.0F, (double)finalSpeedXZ);
         }

         AbilityHelper.setDeltaMovement(entity, lookVec.f_82479_, useY ? lookVec.f_82480_ : entity.m_20184_().m_7098_(), lookVec.f_82481_);
      };
   }
}
