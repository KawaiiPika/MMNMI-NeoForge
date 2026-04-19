package xyz.pixelatedw.mineminenomi.abilities;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.DropHitAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AnimationComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DealDamageComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.damagesources.TargetPredicate;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.init.ModAnimations;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.particles.effects.BreakingBlocksParticleEffect;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class BellyFlopAbility extends DropHitAbility {
   private static final float COOLDOWN = 240.0F;
   private static final float RANGE = 1.5F;
   private static final float DAMAGE = 30.0F;
   public static final TargetPredicate TARGET_TEST = (new TargetPredicate()).testEnemyFaction().testSimpleInView();
   public static final RegistryObject<AbilityCore<BellyFlopAbility>> INSTANCE = ModRegistry.registerAbility("belly_flop", "Belly Flop", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("The user jumps in the air and uses their whole body to hit all the targets while falling, targets hit on ground will also get dizzy for a few seconds.", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.STYLE, BellyFlopAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(240.0F), RangeComponent.getTooltip(1.5F, RangeComponent.RangeType.AOE), DealDamageComponent.getTooltip(30.0F)).setSourceHakiNature(SourceHakiNature.HARDENING).setSourceType(SourceType.PHYSICAL).build("mineminenomi");
   });
   private final DealDamageComponent dealDamageComponent = new DealDamageComponent(this);
   private final RangeComponent rangeComponent = new RangeComponent(this);
   private final AnimationComponent animationComponent = new AnimationComponent(this);
   private BreakingBlocksParticleEffect.Details details = new BreakingBlocksParticleEffect.Details(40);

   public BellyFlopAbility(AbilityCore<BellyFlopAbility> core) {
      super(core);
      this.addComponents(new AbilityComponent[]{this.dealDamageComponent, this.rangeComponent, this.animationComponent});
      this.continuousComponent.addStartEvent(100, this::startContinuityEvent);
      this.continuousComponent.addEndEvent(100, this::endContinuityEvent);
      this.continuousComponent.addTickEvent(100, this::tickContinuityEvent);
   }

   private void startContinuityEvent(LivingEntity entity, IAbility ability) {
      this.hitTrackerComponent.clearHits();
      Vec3 speed = entity.m_20154_();
      AbilityHelper.setDeltaMovement(entity, speed.f_82479_, (double)1.5F, speed.f_82481_);
   }

   private void tickContinuityEvent(LivingEntity entity, IAbility ability) {
      if (entity.f_19789_ > 0.0F && !this.hasLanded()) {
         this.animationComponent.start(entity, ModAnimations.BELLY_FLOP);

         for(LivingEntity target : this.rangeComponent.getTargetsInArea(entity, 1.5F, TARGET_TEST)) {
            if (this.hitTrackerComponent.canHit(target)) {
               this.dealDamageComponent.hurtTarget(entity, target, 15.0F);
            }
         }

         for(Entity target : this.hitTrackerComponent.getHits()) {
            if (AbilityHelper.getDifferenceToFloor(target) > (double)1.5F) {
               target.m_6021_(entity.m_20185_(), entity.m_20186_() - (double)1.0F, entity.m_20189_());
            }
         }
      }

   }

   private void endContinuityEvent(LivingEntity entity, IAbility ability) {
      this.animationComponent.stop(entity);
      this.cooldownComponent.startCooldown(entity, 240.0F);
   }

   public void onLanding(LivingEntity entity) {
      for(Entity target : this.hitTrackerComponent.getHits()) {
         if (target instanceof LivingEntity livingTarget) {
            livingTarget.m_7292_(new MobEffectInstance((MobEffect)ModEffects.DIZZY.get(), 40, 0, false, false));
            this.dealDamageComponent.hurtTarget(entity, livingTarget, 15.0F);
         }
      }

      List<Vec3> positions = new ArrayList();
      int range = (int)Math.ceil((double)1.5F);

      for(int x = -range; x < range; ++x) {
         for(int z = -range; z < range; ++z) {
            double posX = entity.m_20185_() + (double)x;
            double posY = entity.m_20186_() - (double)1.0F;
            double posZ = entity.m_20189_() + (double)z;
            Vec3 pos = new Vec3(posX, posY, posZ);
            positions.add(pos);
         }
      }

      if (positions.size() > 0) {
         this.details.setVecPositions(positions);
         WyHelper.spawnParticleEffect((ParticleEffect)ModParticleEffects.BREAKING_BLOCKS.get(), entity, (double)0.0F, (double)0.0F, (double)0.0F, this.details);
      }

      this.continuousComponent.stopContinuity(entity);
   }
}
