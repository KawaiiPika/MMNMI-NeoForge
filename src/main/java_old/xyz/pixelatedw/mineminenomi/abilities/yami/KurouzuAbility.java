package xyz.pixelatedw.mineminenomi.abilities.yami;

import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult.Type;
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
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DealDamageComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.GrabEntityComponent;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.math.VectorHelper;
import xyz.pixelatedw.mineminenomi.api.util.Interval;
import xyz.pixelatedw.mineminenomi.init.ModAnimations;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModEntityPredicates;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class KurouzuAbility extends Ability {
   private static final float COOLDOWN = 240.0F;
   private static final float DAMAGE = 30.0F;
   private static final float CONTINUITY_TIME = 200.0F;
   private static final float CHARGE_TIME = 20.0F;
   public static final RegistryObject<AbilityCore<KurouzuAbility>> INSTANCE = ModRegistry.registerAbility("kurouzu", "Kurouzu", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Creates a strong gravitational force, that pulls the opponent towards the user.", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, KurouzuAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(240.0F), ContinuousComponent.getTooltip(200.0F), ChargeComponent.getTooltip(20.0F), DealDamageComponent.getTooltip(30.0F)).setSourceType(SourceType.FIST).build("mineminenomi");
   });
   private final ContinuousComponent continuousComponent = (new ContinuousComponent(this, true)).addStartEvent(this::onContinuityStart).addTickEvent(this::onContinuityTick).addEndEvent(this::onContinuityEnd);
   private final GrabEntityComponent grabEntityComponent = new GrabEntityComponent(this, false, false, 1.0F);
   private final ChargeComponent chargeComponent = (new ChargeComponent(this)).addStartEvent(this::onChargeStart).addTickEvent(this::onChargeTick).addEndEvent(this::onChargeEnd);
   private final DealDamageComponent dealDamageComponent = new DealDamageComponent(this);
   private final AnimationComponent animationComponent = new AnimationComponent(this);
   private Interval particleInterval = new Interval(5);

   public KurouzuAbility(AbilityCore<KurouzuAbility> core) {
      super(core);
      this.addComponents(new AbilityComponent[]{this.continuousComponent, this.grabEntityComponent, this.chargeComponent, this.dealDamageComponent, this.animationComponent});
      this.addUseEvent(this::onUseEvent);
   }

   private void onUseEvent(LivingEntity entity, IAbility ability) {
      if (!this.chargeComponent.isCharging()) {
         this.continuousComponent.triggerContinuity(entity, 200.0F);
      }
   }

   private void onContinuityStart(LivingEntity entity, IAbility ability) {
      this.animationComponent.start(entity, ModAnimations.POINT_LEFT_ARM);
   }

   private void onContinuityTick(LivingEntity entity, IAbility ability) {
      if (!entity.m_9236_().f_46443_) {
         HitResult mop = WyHelper.rayTraceBlocksAndEntities(entity, (double)128.0F);
         double i = mop.m_82450_().f_82479_;
         double j = mop.m_82450_().f_82480_ - (mop instanceof EntityHitResult ? (double)1.0F : (double)0.0F);
         double k = mop.m_82450_().f_82481_;
         boolean canTick = this.particleInterval.canTick();
         if (canTick) {
            WyHelper.spawnParticleEffect((ParticleEffect)ModParticleEffects.KUROUZU.get(), entity, i, j, k);
            Vec3 lookVec = entity.m_20154_().m_82541_();
            Vec3 particlePos = entity.m_20182_().m_82520_(lookVec.f_82479_, lookVec.f_82480_ + (double)1.5F, lookVec.f_82481_);
            WyHelper.spawnParticleEffect((ParticleEffect)ModParticleEffects.KUROUZU_CHARGING.get(), entity, particlePos.m_7096_(), particlePos.m_7098_(), particlePos.m_7094_());
         }

         if (mop.m_6662_() != Type.MISS) {
            for(LivingEntity target : WyHelper.getNearbyLiving(new Vec3(i, j, k), entity.m_9236_(), (double)5.0F, ModEntityPredicates.getEnemyFactions(entity))) {
               Vec3 pos = entity.m_20182_().m_82546_(target.m_20182_()).m_82541_();
               AbilityHelper.setDeltaMovement(target, pos);
               if (canTick) {
                  target.m_7292_(new MobEffectInstance(MobEffects.f_19597_, 10, 5));
                  target.m_7292_(new MobEffectInstance((MobEffect)ModEffects.MOVEMENT_BLOCKED.get(), 10, 5));
                  if (this.grabEntityComponent.grabNearest(entity, false)) {
                     this.continuousComponent.stopContinuity(entity);
                     this.chargeComponent.startCharging(entity, 20.0F);
                  }
               }
            }

         }
      }
   }

   private void onContinuityEnd(LivingEntity entity, IAbility ability) {
      if (!entity.m_9236_().f_46443_) {
         if (!this.grabEntityComponent.hasGrabbedEntity()) {
            this.animationComponent.stop(entity);
            super.cooldownComponent.startCooldown(entity, 240.0F);
         }

      }
   }

   private void onChargeStart(LivingEntity entity, IAbility ability) {
      if (!entity.m_9236_().f_46443_) {
         if (!this.grabEntityComponent.canContinueGrab(entity)) {
            this.chargeComponent.stopCharging(entity);
         } else {
            LivingEntity target = this.grabEntityComponent.getGrabbedEntity();
            target.m_7292_(new MobEffectInstance(MobEffects.f_19599_, 100, 5));
            if (target instanceof Player) {
               AbilityHelper.disableAbilities(target, 20, (abl) -> abl.getCore().getCategory() == AbilityCategory.DEVIL_FRUITS);
            }

         }
      }
   }

   private void onChargeTick(LivingEntity entity, IAbility ability) {
      if (!entity.m_9236_().f_46443_) {
         if (!this.grabEntityComponent.canContinueGrab(entity)) {
            this.chargeComponent.stopCharging(entity);
         } else {
            LivingEntity target = this.grabEntityComponent.getGrabbedEntity();
            Vec3 pos = VectorHelper.getRelativeOffset(entity.m_20182_(), entity.f_20883_, new Vec3((double)-0.5F, (double)(-target.m_20206_()) / (double)2.0F, (double)target.m_20205_() - 0.2));
            AbilityHelper.setDeltaMovement(target, pos.m_82546_(target.m_20182_()), true);
         }
      }
   }

   private void onChargeEnd(LivingEntity entity, IAbility ability) {
      if (!entity.m_9236_().f_46443_) {
         if (this.grabEntityComponent.canContinueGrab(entity)) {
            LivingEntity target = this.grabEntityComponent.getGrabbedEntity();
            if (this.dealDamageComponent.hurtTarget(entity, target, 30.0F)) {
               Vec3 dir = entity.m_20154_().m_82542_((double)3.0F, (double)0.0F, (double)3.0F);
               AbilityHelper.setDeltaMovement(target, target.m_20184_().m_82520_(dir.f_82479_, 0.2, dir.f_82481_));
            }
         }

         this.animationComponent.stop(entity);
         super.cooldownComponent.startCooldown(entity, 240.0F);
      }
   }
}
