package xyz.pixelatedw.mineminenomi.abilities.yomi;

import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
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
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceElement;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityUseConditions;
import xyz.pixelatedw.mineminenomi.data.entity.devilfruit.DevilFruitCapability;
import xyz.pixelatedw.mineminenomi.init.ModAnimations;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class SoulParadeAbility extends GuardAbility {
   private static final int HOLD_TIME = 300;
   private static final int MIN_COOLDOWN = 60;
   private static final int MAX_COOLDOWN = 360;
   private static final GuardAbility.GuardValue GUARD_VALUE;
   public static final RegistryObject<AbilityCore<SoulParadeAbility>> INSTANCE;
   private final AnimationComponent animationComponent = new AnimationComponent(this);

   public SoulParadeAbility(AbilityCore<SoulParadeAbility> core) {
      super(core);
      this.addComponents(new AbilityComponent[]{this.animationComponent});
      this.addCanUseCheck(AbilityUseConditions::requiresSword);
      this.continuousComponent.addStartEvent(100, this::startContinuityEvent).addEndEvent(100, this::endContinuityEvent);
   }

   private void startContinuityEvent(LivingEntity entity, IAbility ability) {
      this.animationComponent.start(entity, ModAnimations.CROSSED_ARMS);
      this.blockMovement(entity);
   }

   private void endContinuityEvent(LivingEntity entity, IAbility ability) {
      this.removeMovementBlock(entity);
      this.animationComponent.stop(entity);
      float cooldown = 60.0F + this.continuousComponent.getContinueTime();
      this.cooldownComponent.startCooldown(entity, cooldown);
   }

   private static boolean canUnlock(LivingEntity entity) {
      return (Boolean)DevilFruitCapability.get(entity).map((props) -> props.hasYomiPower()).orElse(false);
   }

   public void onGuard(LivingEntity entity, DamageSource source, float originalDamage, float newDamage) {
      Entity var6 = source.m_7640_();
      if (var6 instanceof LivingEntity attacker) {
         WyHelper.spawnParticleEffect((ParticleEffect)ModParticleEffects.SOUL_PARADE.get(), entity, entity.m_20185_(), entity.m_20186_(), entity.m_20189_());
         attacker.m_7292_(new MobEffectInstance(MobEffects.f_19597_, 70, 1));
         attacker.m_7292_(new MobEffectInstance(MobEffects.f_19599_, 70, 1));
         attacker.m_7292_(new MobEffectInstance((MobEffect)ModEffects.FROZEN.get(), 70, 0));
         this.continuousComponent.stopContinuity(entity);
      }

   }

   public GuardAbility.GuardValue getGuard(LivingEntity entity) {
      return GUARD_VALUE;
   }

   public float getHoldTime() {
      return 300.0F;
   }

   static {
      GUARD_VALUE = GuardAbility.GuardValue.percentage(0.1F, GuardAbility.GuardBreakKind.TOTAL, 50.0F);
      INSTANCE = ModRegistry.registerAbility("soul_parade", "Soul Parade", (id, name) -> {
         Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("The user blocks incoming attacks, an enemy hitting them gets frozen immediately.", (Object)null));
         return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, SoulParadeAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(60.0F, 360.0F), ContinuousComponent.getTooltip(300.0F)).setSourceType(SourceType.SLASH).setSourceElement(SourceElement.ICE).setUnlockCheck(SoulParadeAbility::canUnlock).build("mineminenomi");
      });
   }
}
