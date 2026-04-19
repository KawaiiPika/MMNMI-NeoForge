package xyz.pixelatedw.mineminenomi.abilities.toriphoenix;

import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.GuardAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AnimationComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityTooltipsHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityUseConditions;
import xyz.pixelatedw.mineminenomi.api.morph.MorphInfo;
import xyz.pixelatedw.mineminenomi.api.util.Interval;
import xyz.pixelatedw.mineminenomi.init.ModAnimations;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModMorphs;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class FujiazamiAbility extends GuardAbility {
   private static final int HOLD_TIME = 80;
   private static final int MIN_COOLDOWN = 80;
   private static final float MAX_COOLDOWN = 200.0F;
   private static final GuardAbility.GuardValue WALK_GUARD = GuardAbility.GuardValue.percentage(0.3F);
   public static final RegistryObject<AbilityCore<FujiazamiAbility>> INSTANCE = ModRegistry.registerAbility("fujiazami", "Fujiazami", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("While midair, the user forms a protective swirl of fire in front of them capable of blocking most attacks.", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, FujiazamiAbility::new)).addDescriptionLine(desc).addDescriptionLine(AbilityDescriptionLine.NEW_LINE, AbilityTooltipsHelper.getRequiredMorphTooltip(ModMorphs.PHOENIX_ASSAULT, ModMorphs.PHOENIX_FLY)).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(80.0F, 200.0F), ContinuousComponent.getTooltip(80.0F)).build("mineminenomi");
   });
   private final ContinuousComponent continuousComponent = (new ContinuousComponent(this)).addStartEvent(this::startContinuityEvent).addTickEvent(this::duringContinuityEvent).addEndEvent(this::endContinuityEvent);
   private final AnimationComponent animationComponent = new AnimationComponent(this);
   private Interval particleInterval = new Interval(2);

   public FujiazamiAbility(AbilityCore<FujiazamiAbility> core) {
      super(core);
      this.addComponents(new AbilityComponent[]{this.continuousComponent, this.animationComponent});
      this.addCanUseCheck(ToriPhoenixHelper::requiresEitherPoint);
      this.addContinueUseCheck(ToriPhoenixHelper::requiresEitherPoint);
      this.addCanUseCheck(AbilityUseConditions::requiresInAir);
      this.addUseEvent(this::useEvent);
   }

   private void useEvent(LivingEntity entity, IAbility ability) {
      this.continuousComponent.triggerContinuity(entity, 80.0F);
   }

   private void startContinuityEvent(LivingEntity entity, IAbility ability) {
      this.particleInterval.restartIntervalToZero();
      this.animationComponent.start(entity, ModAnimations.CROSSED_ARMS);
   }

   private void duringContinuityEvent(LivingEntity entity, IAbility ability) {
      entity.m_7292_(new MobEffectInstance((MobEffect)ModEffects.REDUCED_FALL.get(), 4, 1, false, false));
      boolean flyForm = ((MorphInfo)ModMorphs.PHOENIX_FLY.get()).isActive(entity);
      Vec3 lookVec = entity.m_20154_();
      int range = flyForm ? 3 : 2;
      double boxSize = flyForm ? (double)1.25F : 0.8;

      for(int i = 0; i < range * 2; ++i) {
         double distance = (double)i / (double)2.0F;
         Vec3 pos = new Vec3(entity.m_20185_() + lookVec.f_82479_ * distance, entity.m_20186_() + (double)entity.m_20192_() + lookVec.f_82480_ * distance, entity.m_20189_() + lookVec.f_82481_ * distance);

         for(Entity target : entity.m_9236_().m_6249_(entity, new AABB(pos.f_82479_ - boxSize, pos.f_82480_ - boxSize, pos.f_82481_ - boxSize, pos.f_82479_ + boxSize, pos.f_82480_ + boxSize * (double)2.0F, pos.f_82481_ + boxSize), (targetx) -> targetx != entity)) {
            if (target instanceof LivingEntity) {
               Vec3 speed = entity.m_20154_().m_82541_().m_82542_((double)3.0F, (double)1.0F, (double)3.0F);
               AbilityHelper.setDeltaMovement(target, speed.f_82479_, (double)0.5F, speed.f_82481_);
            } else if (target instanceof AbstractArrow || target instanceof ThrowableProjectile) {
               if (target instanceof NuProjectileEntity) {
                  NuProjectileEntity nuproj = (NuProjectileEntity)target;
                  if (nuproj.getDamage() > (float)(flyForm ? 50 : 30)) {
                     return;
                  }
               }

               target.m_146870_();
            }
         }
      }

      if (this.particleInterval.canTick() && this.continuousComponent.getContinueTime() % 5.0F == 0.0F) {
         lookVec.m_82542_((double)2.5F, (double)1.0F, (double)2.5F);
         Vec3 particlesPos = entity.m_20182_().m_82549_(lookVec);
         WyHelper.spawnParticleEffect((ParticleEffect)ModParticleEffects.FUJIZAMI.get(), entity, particlesPos.f_82479_, entity.m_20186_() + (double)(entity.m_20192_() / 2.0F), particlesPos.f_82481_);
      }

   }

   private void endContinuityEvent(LivingEntity entity, IAbility ability) {
      this.animationComponent.stop(entity);
      float cooldown = 80.0F + this.continuousComponent.getContinueTime() * 1.5F;
      this.cooldownComponent.startCooldown(entity, cooldown);
   }

   public GuardAbility.GuardValue getGuard(LivingEntity entity) {
      return WALK_GUARD;
   }

   public boolean canGuardBreak(LivingEntity entity) {
      return false;
   }

   public void onGuard(LivingEntity entity, DamageSource source, float originalDamage, float finalDamage) {
   }
}
