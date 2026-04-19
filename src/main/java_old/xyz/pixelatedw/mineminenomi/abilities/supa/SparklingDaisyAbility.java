package xyz.pixelatedw.mineminenomi.abilities.supa;

import com.google.common.collect.Lists;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.Blocks;
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
import xyz.pixelatedw.mineminenomi.api.abilities.components.DashComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DealDamageComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ExplosionComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.HitTrackerComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ProjectileComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent;
import xyz.pixelatedw.mineminenomi.api.blockgen.BlockGenerators;
import xyz.pixelatedw.mineminenomi.api.blockgen.SimpleBlockPlacer;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityUseConditions;
import xyz.pixelatedw.mineminenomi.api.protection.DefaultProtectionRules;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.supa.SparklingDaisyAftereffectProjectile;
import xyz.pixelatedw.mineminenomi.init.ModAnimations;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.particles.effects.BreakingBlocksParticleEffect;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class SparklingDaisyAbility extends Ability {
   private static final float COOLDOWN = 140.0F;
   private static final int HOLD_TIME = 20;
   private static final int CHARGE_TIME = 40;
   private static final float RANGE = 1.6F;
   private static final float DAMAGE = 25.0F;
   public static final RegistryObject<AbilityCore<SparklingDaisyAbility>> INSTANCE = ModRegistry.registerAbility("sparkling_daisy", "Sparkling Daisy", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Launches the user forward, slicing anything in their path", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, SparklingDaisyAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(140.0F), RangeComponent.getTooltip(1.6F, RangeComponent.RangeType.AOE), DealDamageComponent.getTooltip(25.0F)).setSourceHakiNature(SourceHakiNature.HARDENING).setSourceType(SourceType.FIST).build("mineminenomi");
   });
   private final HitTrackerComponent hitTrackerComponent = new HitTrackerComponent(this);
   private final ChargeComponent chargeComponent = (new ChargeComponent(this)).addStartEvent(this::chargeStartsEvent).addEndEvent(this::chargeEndsEvent);
   private final AnimationComponent animationComponent = new AnimationComponent(this);
   private final ContinuousComponent continuousComponent = (new ContinuousComponent(this)).addTickEvent(this::continuousTickEvent).addEndEvent(this::continuousEndsEvent);
   private final RangeComponent rangeComponent = new RangeComponent(this);
   private final DealDamageComponent dealDamageComponent = new DealDamageComponent(this);
   private final ProjectileComponent projectileComponent = new ProjectileComponent(this, this::createProjectile);
   private final ExplosionComponent explosionComponent = new ExplosionComponent(this);
   private final DashComponent dashComponent = new DashComponent(this);
   private BreakingBlocksParticleEffect.Details details = new BreakingBlocksParticleEffect.Details(20);
   private boolean isFull = false;
   private int projectiles = 1;

   public SparklingDaisyAbility(AbilityCore<SparklingDaisyAbility> core) {
      super(core);
      this.addComponents(new AbilityComponent[]{this.hitTrackerComponent, this.chargeComponent, this.animationComponent, this.continuousComponent, this.rangeComponent, this.dealDamageComponent, this.explosionComponent, this.dashComponent});
      this.addCanUseCheck(AbilityUseConditions::canUseMomentumAbilities);
      this.addUseEvent(this::onUseEvent);
   }

   private void onUseEvent(LivingEntity entity, IAbility ability) {
      this.hitTrackerComponent.clearHits();
      if (this.isFull) {
         this.chargeComponent.startCharging(entity, 40.0F);
      } else {
         this.dashComponent.timedDashXYZ(entity, 2.0F, 20);
         this.continuousComponent.startContinuity(entity, 20.0F);
      }

   }

   private void chargeStartsEvent(LivingEntity entity, IAbility ability) {
      this.animationComponent.start(entity, ModAnimations.SPARKLING_DAISY_CHARGE);
      entity.m_7292_(new MobEffectInstance((MobEffect)ModEffects.MOVEMENT_BLOCKED.get(), 40, 0));
   }

   private void chargeEndsEvent(LivingEntity entity, IAbility ability) {
      this.animationComponent.stop(entity);
      this.dashComponent.timedDashXYZ(entity, 2.0F, 20);
      this.continuousComponent.startContinuity(entity, 20.0F);
      entity.m_21195_((MobEffect)ModEffects.MOVEMENT_BLOCKED.get());
   }

   private void continuousEndsEvent(LivingEntity entity, IAbility ability) {
      if (this.isFull) {
         if (this.projectiles > 0) {
            for(int i = -this.projectiles; i <= this.projectiles; ++i) {
               SparklingDaisyAftereffectProjectile proj = (SparklingDaisyAftereffectProjectile)this.projectileComponent.getNewProjectile(entity);
               proj.m_37251_(entity, entity.m_146909_(), entity.m_146908_() + (float)(i * 55), 0.0F, 0.85F, 1.0F);
               entity.m_9236_().m_7967_(proj);
            }
         }

         this.animationComponent.stop(entity);
      }

      this.cooldownComponent.startCooldown(entity, 140.0F);
   }

   private void continuousTickEvent(LivingEntity entity, IAbility ability) {
      if (entity.m_6084_() && !entity.m_9236_().f_46443_) {
         for(LivingEntity target : this.rangeComponent.getTargetsInLine(entity, 1.6F, 1.6F)) {
            if (this.hitTrackerComponent.canHit(target)) {
               this.dealDamageComponent.hurtTarget(entity, target, 25.0F);
            }
         }

         SimpleBlockPlacer placer = (new SimpleBlockPlacer()).setBlock(Blocks.f_50016_.m_49966_()).setRule(DefaultProtectionRules.CORE_FOLIAGE_ORE).setSize(3).setPositionPredicate((pos) -> (double)pos.m_123342_() >= entity.m_20186_());
         placer.generate(entity.m_9236_(), entity.m_20183_(), BlockGenerators.CUBE);
         if (placer.getPlacedPositions().size() > 0) {
            this.details.setPositions(Lists.newArrayList(placer.getPlacedPositions()));
            WyHelper.spawnParticleEffect((ParticleEffect)ModParticleEffects.BREAKING_BLOCKS.get(), entity, (double)0.0F, (double)0.0F, (double)0.0F, this.details);
         }
      }

   }

   public void setFullForm() {
      this.isFull = true;
   }

   public void setProjectilesAmount(int amount) {
      this.projectiles = amount;
   }

   private SparklingDaisyAftereffectProjectile createProjectile(LivingEntity entity) {
      SparklingDaisyAftereffectProjectile proj = new SparklingDaisyAftereffectProjectile(entity.m_9236_(), entity, this);
      return proj;
   }
}
