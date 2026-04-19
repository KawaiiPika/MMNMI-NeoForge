package xyz.pixelatedw.mineminenomi.abilities;

import java.util.Collections;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.commands.arguments.EntityAnchorArgument.Anchor;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.RegistryObject;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AnimationComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DealDamageComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.StackComponent;
import xyz.pixelatedw.mineminenomi.api.animations.AnimationId;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.damagesources.TargetPredicate;
import xyz.pixelatedw.mineminenomi.api.math.VectorHelper;
import xyz.pixelatedw.mineminenomi.init.ModAnimations;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class ShakushiAbility extends Ability {
   private static final int COOLDOWN = 400;
   private static final int CHARGE_TIME = 40;
   private static final int RANGE = 10;
   private static final int DAMAGE = 30;
   private static final int STACKS = 5;
   private static final AnimationId<?>[] HIT_ANIMATIONS;
   public static final RegistryObject<AbilityCore<ShakushiAbility>> INSTANCE;
   private final ChargeComponent chargeComponent = (new ChargeComponent(this)).addTickEvent(this::tickChargeEvent).addEndEvent(this::endChargeEvent);
   private final RangeComponent rangeComponent = new RangeComponent(this);
   private final DealDamageComponent dealDamageComponent = new DealDamageComponent(this);
   private final AnimationComponent animationComponent = new AnimationComponent(this);
   private final StackComponent stackComponent = new StackComponent(this);

   public ShakushiAbility(AbilityCore<ShakushiAbility> core) {
      super(core);
      this.addComponents(new AbilityComponent[]{this.rangeComponent, this.chargeComponent, this.dealDamageComponent, this.animationComponent, this.stackComponent});
      this.addUseEvent(this::useEvent);
      this.addTickEvent(this::tickEvent);
   }

   private void useEvent(LivingEntity entity, IAbility ability) {
      if (this.chargeComponent.isCharging()) {
         this.chargeComponent.stopCharging(entity);
         this.cooldownComponent.startCooldown(entity, 400.0F);
      } else {
         this.stackComponent.setDefaultStacks(5);
         this.stackComponent.setStacks(entity, ability, 5);
         this.animationComponent.start(entity, ModAnimations.LOW_SWING_ARMS, 39);
         this.chargeComponent.startCharging(entity, 40.0F);
      }
   }

   private void tickEvent(LivingEntity entity, IAbility ability) {
      if (!this.isCharging() && this.stackComponent.getStacks() > 0 && this.stackComponent.getStacks() < this.stackComponent.getDefaultStacks()) {
         this.chargeComponent.startCharging(entity, 20.0F);
      }

   }

   private void tickChargeEvent(LivingEntity entity, IAbility ability) {
      if (this.stackComponent.getStacks() > 0 && this.stackComponent.getStacks() < this.stackComponent.getDefaultStacks() && this.chargeComponent.getChargePercentage() >= 0.5F && !entity.m_21023_((MobEffect)ModEffects.VANISH.get())) {
         entity.m_7292_(new MobEffectInstance((MobEffect)ModEffects.VANISH.get(), 40, 0, false, false));
         entity.m_7292_(new MobEffectInstance((MobEffect)ModEffects.MOVEMENT_BLOCKED.get(), 40, 0, false, false));
         LivingEntity target = this.getNearbyTarget(entity);
         if (target != null) {
            BlockPos newPos = WyHelper.findValidGroundLocation((Entity)entity, (BlockPos)entity.m_20183_(), 5, 0);
            if (newPos != null) {
               entity.m_20324_((double)newPos.m_123341_(), (double)newPos.m_123342_(), (double)newPos.m_123343_());
            }
         }
      }

   }

   private void endChargeEvent(LivingEntity entity, IAbility ability) {
      if (!entity.m_9236_().f_46443_) {
         this.stackComponent.addStacks(entity, ability, -1);
         entity.m_21195_((MobEffect)ModEffects.VANISH.get());
         LivingEntity target = this.getNearbyTarget(entity);
         if (target != null && target.m_6084_()) {
            Vec3 targetLook = VectorHelper.calculateViewVectorFromBodyRot(target.m_146909_(), target.f_20883_).m_82542_((double)-2.0F, (double)0.0F, (double)-2.0F);
            Vec3 newPos = target.m_20182_().m_82549_(targetLook);
            entity.m_7292_(new MobEffectInstance((MobEffect)ModEffects.MOVEMENT_BLOCKED.get(), 5, 0, false, false));
            WyHelper.spawnParticleEffect((ParticleEffect)ModParticleEffects.GEPPO.get(), entity, entity.m_20185_(), entity.m_20186_() + (double)0.5F, entity.m_20189_());
            entity.m_20324_(newPos.f_82479_, newPos.f_82480_, newPos.f_82481_);
            entity.m_7618_(Anchor.EYES, target.m_20182_().m_82520_((double)0.0F, (double)target.m_20192_(), (double)0.0F));

            for(LivingEntity target2 : this.rangeComponent.getTargetsInArea(entity, target.m_20183_(), 2.0F)) {
               this.dealDamageComponent.hurtTarget(entity, target2, 30.0F);
               WyHelper.spawnParticles(ParticleTypes.f_123766_, (ServerLevel)entity.m_9236_(), target2.m_20185_(), target2.m_20186_() + (double)target2.m_20192_(), target2.m_20189_());
            }

            AnimationId<?> anim = HIT_ANIMATIONS[entity.m_217043_().m_188503_(HIT_ANIMATIONS.length)];
            this.animationComponent.start(entity, anim, 7);
            entity.m_21011_(InteractionHand.MAIN_HAND, true);
         }

         if (this.stackComponent.getStacks() <= 0 || target == null || !target.m_6084_()) {
            this.stackComponent.revertStacksToDefault(entity, this);
            this.cooldownComponent.startCooldown(entity, 400.0F);
            entity.m_21195_((MobEffect)ModEffects.MOVEMENT_BLOCKED.get());
         }
      }

   }

   @Nullable
   private LivingEntity getNearbyTarget(LivingEntity entity) {
      List<LivingEntity> targets = this.rangeComponent.getTargetsInArea(entity, 10.0F, TargetPredicate.EVERYTHING);
      if (targets.size() > 0) {
         Collections.shuffle(targets);
         return (LivingEntity)targets.get(0);
      } else {
         return null;
      }
   }

   static {
      HIT_ANIMATIONS = new AnimationId[]{ModAnimations.CROSSED_ATTACK, ModAnimations.BODY_ROTATION_WIDE_ARMS, ModAnimations.SCRATCHING};
      INSTANCE = ModRegistry.registerAbility("shakushi", "Shakushi", (id, name) -> (new AbilityCore.Builder(id, name, AbilityCategory.STYLE, ShakushiAbility::new)).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(400.0F), ChargeComponent.getTooltip(40.0F), RangeComponent.getTooltip(10.0F, RangeComponent.RangeType.AOE), DealDamageComponent.getTooltip(30.0F), StackComponent.getTooltip(5)).setSourceHakiNature(SourceHakiNature.IMBUING).setSourceType(SourceType.SLASH).build("mineminenomi"));
   }
}
