package xyz.pixelatedw.mineminenomi.abilities.nikyu;

import java.util.function.Predicate;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
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
import xyz.pixelatedw.mineminenomi.api.abilities.components.AnimationComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DamageTakenComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DealDamageComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.HitTrackerComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityUseConditions;
import xyz.pixelatedw.mineminenomi.api.helpers.TargetHelper;
import xyz.pixelatedw.mineminenomi.init.ModAnimations;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class PuniAbility extends Ability {
   private static final int COOLDOWN = 80;
   private static final int HOLD_TIME = 40;
   private static final float RANGE = 2.5F;
   private static final int DAMAGE = 15;
   public static final RegistryObject<AbilityCore<PuniAbility>> INSTANCE = ModRegistry.registerAbility("puni", "Puni", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("The user takes a defensive posture that uses their paw pads to repel and counter enemy attacks", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, PuniAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(80.0F), ContinuousComponent.getTooltip(40.0F), RangeComponent.getTooltip(2.5F, RangeComponent.RangeType.LINE), DealDamageComponent.getTooltip(15.0F)).build("mineminenomi");
   });
   private final ContinuousComponent continuousComponent = (new ContinuousComponent(this)).addStartEvent(this::startContinuityEvent).addTickEvent(this::duringContinuityEvent).addEndEvent(this::endContinuityEvent);
   private final AnimationComponent animationComponent = new AnimationComponent(this);
   private final HitTrackerComponent hitTrackerComponent = new HitTrackerComponent(this);
   private final DealDamageComponent dealDamageComponent = new DealDamageComponent(this);
   private final DamageTakenComponent damageTakenComponent = (new DamageTakenComponent(this)).addOnAttackEvent(this::onDamageTaken);

   public PuniAbility(AbilityCore<PuniAbility> core) {
      super(core);
      this.addComponents(new AbilityComponent[]{this.continuousComponent, this.animationComponent, this.hitTrackerComponent, this.dealDamageComponent, this.damageTakenComponent});
      this.addCanUseCheck(AbilityUseConditions::requiresEmptyHand);
      this.addUseEvent(this::useEvent);
   }

   private void useEvent(LivingEntity entity, IAbility ability) {
      this.continuousComponent.triggerContinuity(entity, 40.0F);
   }

   private void startContinuityEvent(LivingEntity entity, IAbility ability) {
      this.animationComponent.start(entity, ModAnimations.POINT_ARMS);
   }

   private float onDamageTaken(LivingEntity entity, IAbility ability, DamageSource damageSource, float damage) {
      if (this.continuousComponent.isContinuous()) {
         Entity attacker = damageSource.m_7640_();
         if (attacker != null && attacker instanceof LivingEntity) {
            LivingEntity target = (LivingEntity)attacker;
            boolean canSeeAttacker = TargetHelper.isEntityInView(entity, target, 60.0F);
            boolean lastAttackerDistance = target.m_20270_(entity) < 6.25F;
            if (canSeeAttacker && lastAttackerDistance && this.hitTrackerComponent.canHit(target) && this.dealDamageComponent.hurtTarget(entity, target, 15.0F)) {
               Vec3 speed = entity.m_20154_().m_82542_((double)6.0F, (double)1.0F, (double)6.0F);
               AbilityHelper.setDeltaMovement(target, speed.f_82479_, (double)1.5F, speed.f_82481_);
               return 0.0F;
            }
         }
      }

      return damage;
   }

   private void duringContinuityEvent(LivingEntity entity, IAbility ability) {
      if (this.canUse(entity).isFail()) {
         this.continuousComponent.stopContinuity(entity);
      } else {
         entity.m_7292_(new MobEffectInstance((MobEffect)ModEffects.MOVEMENT_BLOCKED.get(), 5, 1, false, false));

         for(Entity target : WyHelper.getNearbyEntities(entity.m_20182_(), entity.m_9236_(), (double)2.5F, (Predicate)null)) {
            if (target != entity && TargetHelper.isEntityInView(entity, target, 60.0F) && target instanceof Projectile) {
               AbilityHelper.setDeltaMovement(target, -target.m_20184_().f_82479_, -target.m_20184_().f_82480_, -target.m_20184_().f_82481_);
            }
         }

      }
   }

   private void endContinuityEvent(LivingEntity entity, IAbility ability) {
      this.animationComponent.stop(entity);
      this.cooldownComponent.startCooldown(entity, 80.0F);
   }
}
