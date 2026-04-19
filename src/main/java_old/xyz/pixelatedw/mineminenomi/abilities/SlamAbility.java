package xyz.pixelatedw.mineminenomi.abilities;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
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

public class SlamAbility extends DropHitAbility {
   private static final float COOLDOWN = 240.0F;
   private static final float RANGE = 2.0F;
   private static final float DAMAGE = 20.0F;
   public static final TargetPredicate TARGET_TEST = (new TargetPredicate()).testEnemyFaction().testSimpleInView();
   public static final RegistryObject<AbilityCore<SlamAbility>> INSTANCE = ModRegistry.registerAbility("slam", "Slam", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("The user jumps in the air and uses their fists or weapon to smash the target and the ground around it.", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.STYLE, SlamAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(240.0F), RangeComponent.getTooltip(2.0F, RangeComponent.RangeType.AOE), DealDamageComponent.getTooltip(20.0F)).setSourceHakiNature(SourceHakiNature.IMBUING).setSourceType(SourceType.BLUNT).build("mineminenomi");
   });
   private final DealDamageComponent dealDamageComponent = new DealDamageComponent(this);
   private final RangeComponent rangeComponent = new RangeComponent(this);
   private final AnimationComponent animationComponent = new AnimationComponent(this);
   private BreakingBlocksParticleEffect.Details details = new BreakingBlocksParticleEffect.Details(40);

   public SlamAbility(AbilityCore<SlamAbility> core) {
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
         this.animationComponent.start(entity, ModAnimations.WEAPON_SLAM);
      }

   }

   private void endContinuityEvent(LivingEntity entity, IAbility ability) {
      this.animationComponent.stop(entity);
      this.cooldownComponent.startCooldown(entity, 240.0F);
   }

   public void onLanding(LivingEntity entity) {
      for(LivingEntity target : this.rangeComponent.getTargetsInArea(entity, 2.0F, TARGET_TEST)) {
         if (this.hitTrackerComponent.canHit(target) && this.dealDamageComponent.hurtTarget(entity, target, 20.0F)) {
            target.m_7292_(new MobEffectInstance((MobEffect)ModEffects.DIZZY.get(), 40, 0, false, false));
         }
      }

      List<Vec3> positions = new ArrayList();
      int range = (int)Math.ceil((double)this.rangeComponent.getRange());

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
