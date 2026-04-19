package xyz.pixelatedw.mineminenomi.abilities.mera;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DealDamageComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.HitTrackerComponent;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceElement;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.util.Interval;
import xyz.pixelatedw.mineminenomi.entities.projectiles.KairosekiBulletProjectile;
import xyz.pixelatedw.mineminenomi.entities.projectiles.NormalBulletProjectile;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class KyokaenAbility extends Ability {
   private static final float DAMAGE = 3.0F;
   private static final int ON_HOLD = 100;
   private static final int MIN_COOLDOWN = 40;
   private static final int MAX_COOLDOWN = 140;
   public static final RegistryObject<AbilityCore<KyokaenAbility>> INSTANCE = ModRegistry.registerAbility("kyokaen", "Kyokaen", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Creates a wall of fire protecting the user", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, KyokaenAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(40.0F, 140.0F), ContinuousComponent.getTooltip(100.0F), DealDamageComponent.getTooltip(3.0F)).setSourceHakiNature(SourceHakiNature.SPECIAL).setSourceElement(SourceElement.FIRE).build("mineminenomi");
   });
   private final ContinuousComponent continuousComponent = (new ContinuousComponent(this)).addStartEvent(this::onContinuityStart).addTickEvent(this::onContinuityTick).addEndEvent(this::onContinuityEnd);
   private final HitTrackerComponent hitTrackerComponent = new HitTrackerComponent(this);
   private final DealDamageComponent dealDamageComponent = new DealDamageComponent(this);
   private final Interval particleInterval = new Interval(2);
   private final Interval clearHitsInterval = new Interval(20);

   public KyokaenAbility(AbilityCore<KyokaenAbility> core) {
      super(core);
      this.addComponents(new AbilityComponent[]{this.continuousComponent, this.hitTrackerComponent, this.dealDamageComponent});
      this.addCanUseCheck(MeraHelper::canUseMeraAbilities);
      this.addUseEvent(this::onUseEvent);
   }

   private void onUseEvent(LivingEntity entity, IAbility ability) {
      this.continuousComponent.triggerContinuity(entity, 100.0F);
   }

   private void onContinuityStart(LivingEntity entity, IAbility ability) {
      this.particleInterval.restartIntervalToZero();
      this.hitTrackerComponent.clearHits();
   }

   private void onContinuityTick(LivingEntity entity, IAbility ability) {
      if (!entity.m_9236_().f_46443_) {
         if (super.canUse(entity).isFail()) {
            this.continuousComponent.stopContinuity(entity);
         }

         int range = 2;
         double boxSize = 1.1;

         for(int i = 0; i < range * 2; ++i) {
            double distance = (double)i / (double)2.0F;
            Vec3 lookVec = entity.m_20154_();
            Vec3 pos = new Vec3(entity.m_20185_() + lookVec.f_82479_ * distance, entity.m_20186_() + (double)entity.m_20192_() + lookVec.f_82480_ * distance, entity.m_20189_() + lookVec.f_82481_ * distance);
            AABB aabb = new AABB(pos.f_82479_ - boxSize, pos.f_82480_ - boxSize, pos.f_82481_ - boxSize, pos.f_82479_ + boxSize, pos.f_82480_ + boxSize * (double)2.0F, pos.f_82481_ + boxSize);

            for(Entity target : entity.m_9236_().m_6249_(entity, aabb, (targetx) -> targetx != entity)) {
               if (target instanceof LivingEntity) {
                  if (!target.m_6060_() && this.hitTrackerComponent.canHit(target)) {
                     target.m_20254_(3);
                     this.dealDamageComponent.hurtTarget(entity, (LivingEntity)target, 3.0F);
                  }

                  Vec3 dir = entity.m_20154_().m_82541_().m_82490_((double)3.0F);
                  AbilityHelper.setDeltaMovement(target, dir.f_82479_, (double)0.5F, dir.f_82481_);
               } else if (!(target instanceof AbstractArrow) && !(target instanceof KairosekiBulletProjectile) && !(target instanceof NormalBulletProjectile)) {
                  if (target instanceof ThrowableProjectile) {
                     AbilityHelper.setDeltaMovement(target, target.m_20184_().m_82542_(-1.35, (double)1.0F, -1.35));
                  }
               } else {
                  target.m_142687_(RemovalReason.KILLED);
               }
            }
         }

         if (this.particleInterval.canTick()) {
            EntityHitResult trace = WyHelper.rayTraceEntities(entity, (double)range);
            WyHelper.spawnParticleEffect((ParticleEffect)ModParticleEffects.KYOKAEN.get(), entity, trace.m_82450_().m_7096_(), entity.m_20186_(), trace.m_82450_().m_7094_());
         }

         if (this.clearHitsInterval.canTick()) {
            this.hitTrackerComponent.clearHits();
         }

      }
   }

   private void onContinuityEnd(LivingEntity entity, IAbility ability) {
      float cooldown = 40.0F + this.continuousComponent.getContinueTime();
      super.cooldownComponent.startCooldown(entity, cooldown);
   }
}
