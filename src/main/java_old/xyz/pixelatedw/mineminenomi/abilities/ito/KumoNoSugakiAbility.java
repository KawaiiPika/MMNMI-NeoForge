package xyz.pixelatedw.mineminenomi.abilities.ito;

import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
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
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.init.ModAnimations;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class KumoNoSugakiAbility extends GuardAbility {
   private static final int HOLD_TIME = 120;
   private static final int MIN_COOLDOWN = 20;
   private static final int MAX_COOLDOWN = 140;
   private static final GuardAbility.GuardValue GUARD_VALUE;
   public static final RegistryObject<AbilityCore<KumoNoSugakiAbility>> INSTANCE;
   private final AnimationComponent animationComponent = new AnimationComponent(this);

   public KumoNoSugakiAbility(AbilityCore<KumoNoSugakiAbility> core) {
      super(core);
      this.addComponents(new AbilityComponent[]{this.animationComponent});
      this.continuousComponent.addStartEvent(100, this::startContinuousEvent).addTickEvent(100, this::tickContinuousEvent).addEndEvent(100, this::endContinuousEvent);
   }

   private void startContinuousEvent(LivingEntity entity, IAbility ability) {
      this.animationComponent.start(entity, ModAnimations.POINT_ARMS);
   }

   private void tickContinuousEvent(LivingEntity entity, IAbility ability) {
      Vec3 look = entity.m_20154_().m_82541_().m_82490_((double)1.5F);
      AbilityHelper.slowEntityFall(entity);
      if (this.continuousComponent.getContinueTime() % 2.0F == 0.0F) {
         WyHelper.spawnParticleEffect((ParticleEffect)ModParticleEffects.KUMO_NO_SUGAKI.get(), entity, entity.m_20185_() + look.f_82479_, entity.m_20186_() + (double)0.25F + look.f_82480_, entity.m_20189_() + look.f_82481_);
      }

   }

   private void endContinuousEvent(LivingEntity entity, IAbility ability) {
      this.animationComponent.stop(entity);
      this.cooldownComponent.startCooldown(entity, 20.0F + this.continuousComponent.getContinueTime());
   }

   public float getHoldTime() {
      return 120.0F;
   }

   public void onGuard(LivingEntity entity, DamageSource source, float originalDamage, float newDamage) {
   }

   public GuardAbility.GuardValue getGuard(LivingEntity entity) {
      return GUARD_VALUE;
   }

   static {
      GUARD_VALUE = GuardAbility.GuardValue.percentage(0.4F, GuardAbility.GuardBreakKind.PER_HIT, 100.0F);
      INSTANCE = ModRegistry.registerAbility("kumo_no_sugaki", "Kumo no Sugaki", (id, name) -> {
         Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Creates a huge web that protects the user from any attack", (Object)null));
         return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, KumoNoSugakiAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(20.0F, 140.0F)).build("mineminenomi");
      });
   }
}
