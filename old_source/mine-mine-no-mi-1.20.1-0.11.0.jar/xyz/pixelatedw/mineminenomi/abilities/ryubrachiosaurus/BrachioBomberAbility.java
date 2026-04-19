package xyz.pixelatedw.mineminenomi.abilities.ryubrachiosaurus;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityExplosion;
import xyz.pixelatedw.mineminenomi.api.abilities.DropHitAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AnimationComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DealDamageComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ExplosionComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityTooltipsHelper;
import xyz.pixelatedw.mineminenomi.api.morph.MorphInfo;
import xyz.pixelatedw.mineminenomi.init.ModAnimations;
import xyz.pixelatedw.mineminenomi.init.ModMorphs;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class BrachioBomberAbility extends DropHitAbility {
   private static final int COOLDOWN = 300;
   private static final float DAMAGE = 20.0F;
   private static final float RANGE = 5.5F;
   public static final RegistryObject<AbilityCore<BrachioBomberAbility>> INSTANCE = ModRegistry.registerAbility("brachio_bomber", "Brachio Bomber", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Dives from a high place and lands on his opponent, crushing them under the user's weight.", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, BrachioBomberAbility::new)).addDescriptionLine(desc).addDescriptionLine(AbilityDescriptionLine.NEW_LINE, AbilityTooltipsHelper.getRequiredMorphTooltip(ModMorphs.BRACHIO_HEAVY, ModMorphs.BRACHIO_GUARD)).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(300.0F), DealDamageComponent.getTooltip(20.0F), RangeComponent.getTooltip(5.5F, RangeComponent.RangeType.AOE)).setSourceHakiNature(SourceHakiNature.HARDENING).build("mineminenomi");
   });
   private final DealDamageComponent dealDamageComponent = new DealDamageComponent(this);
   private final RangeComponent rangeComponent = new RangeComponent(this);
   private final AnimationComponent animationComponent = new AnimationComponent(this);
   private final ExplosionComponent explosionComponent = new ExplosionComponent(this);

   public BrachioBomberAbility(AbilityCore<BrachioBomberAbility> core) {
      super(core);
      this.addComponents(new AbilityComponent[]{this.dealDamageComponent, this.rangeComponent, this.animationComponent, this.explosionComponent});
      this.addCanUseCheck(RyuBrachioHelper::requiresEitherPoint);
      this.addContinueUseCheck(RyuBrachioHelper::requiresEitherPoint);
      this.continuousComponent.addStartEvent(100, this::startContinuityEvent);
      this.continuousComponent.addEndEvent(100, this::endContinuityEvent);
   }

   public void onLanding(LivingEntity entity) {
      WyHelper.spawnParticleEffect((ParticleEffect)ModParticleEffects.GREAT_STOMP.get(), entity, entity.m_20185_(), entity.m_20186_(), entity.m_20189_());
      int size = 7;
      if (((MorphInfo)ModMorphs.BRACHIO_GUARD.get()).isActive(entity)) {
         size = 15;
      } else if (((MorphInfo)ModMorphs.BRACHIO_HEAVY.get()).isActive(entity)) {
         this.animationComponent.stop(entity);
      }

      if (entity.m_9236_() != null && !entity.m_9236_().f_46443_) {
         AbilityExplosion explosion = this.explosionComponent.createExplosion(entity, entity.m_20185_(), entity.m_20186_(), entity.m_20189_(), (float)size);
         explosion.setDestroyBlocks(true);
         explosion.setStaticDamage((float)(size * 2));
         explosion.m_46061_();
      }

      for(LivingEntity target : this.rangeComponent.getTargetsInArea(entity, 5.5F)) {
         if (this.hitTrackerComponent.canHit(target)) {
            this.dealDamageComponent.hurtTarget(entity, target, 20.0F);
         }
      }

   }

   private void endContinuityEvent(LivingEntity entity, IAbility ability) {
      this.cooldownComponent.startCooldown(entity, 300.0F);
   }

   private void startContinuityEvent(LivingEntity entity, IAbility ability) {
      double jump = 1.3;
      if (((MorphInfo)ModMorphs.BRACHIO_GUARD.get()).isActive(entity)) {
         jump = 1.6;
      } else if (((MorphInfo)ModMorphs.BRACHIO_HEAVY.get()).isActive(entity)) {
         this.animationComponent.start(entity, ModAnimations.BELLY_FLOP);
      }

      Vec3 speed = entity.m_20154_().m_82541_();
      AbilityHelper.setDeltaMovement(entity, speed.f_82479_, jump, speed.f_82481_);
   }
}
