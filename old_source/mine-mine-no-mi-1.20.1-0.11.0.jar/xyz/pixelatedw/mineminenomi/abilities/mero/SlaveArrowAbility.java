package xyz.pixelatedw.mineminenomi.abilities.mero;

import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
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
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ProjectileComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RepeaterComponent;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.mero.SlaveArrowProjectile;
import xyz.pixelatedw.mineminenomi.init.ModAnimations;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class SlaveArrowAbility extends Ability {
   private static final int CHARGE_TIME = 60;
   private static final int COOLDOWN = 260;
   public static final RegistryObject<AbilityCore<SlaveArrowAbility>> INSTANCE = ModRegistry.registerAbility("slave_arrow", "Slave Arrow", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Creates a big heart from which the user shoots multiple love arrows, petrifying enemies.", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, SlaveArrowAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(260.0F), ChargeComponent.getTooltip(60.0F)).addAdvancedDescriptionLine(ProjectileComponent.getProjectileTooltips()).setSourceHakiNature(SourceHakiNature.SPECIAL).build("mineminenomi");
   });
   private final ChargeComponent chargeComponent = (new ChargeComponent(this)).addStartEvent(this::startChargingEvent).addTickEvent(this::duringChargingEvent).addEndEvent(this::endChargingEvent);
   private final ContinuousComponent continuousComponent = (new ContinuousComponent(this)).addStartEvent(this::startContinuityEvent).addEndEvent(this::endContinuityEvent);
   private final RepeaterComponent repeaterComponent = (new RepeaterComponent(this)).addStopEvent(this::endRepeaterEvent).addTriggerEvent(this::triggerRepeaterEvent);
   private final ProjectileComponent projectileComponent = new ProjectileComponent(this, this::createProjectile);
   private final AnimationComponent animationComponent = new AnimationComponent(this);

   public SlaveArrowAbility(AbilityCore<SlaveArrowAbility> core) {
      super(core);
      this.addComponents(new AbilityComponent[]{this.chargeComponent, this.continuousComponent, this.repeaterComponent, this.projectileComponent, this.animationComponent});
      this.addUseEvent(this::useEvent);
   }

   private void useEvent(LivingEntity entity, IAbility ability) {
      this.chargeComponent.startCharging(entity, 60.0F);
   }

   private void startChargingEvent(LivingEntity entity, IAbility ability) {
      this.animationComponent.start(entity, ModAnimations.AIM_SNIPER);
      Vec3 look = entity.m_20154_().m_82541_().m_82490_((double)2.5F);
      WyHelper.spawnParticleEffect((ParticleEffect)ModParticleEffects.SLAVE_ARROW.get(), entity, entity.m_20185_() + look.f_82479_, entity.m_20186_() + (double)0.25F + look.f_82480_, entity.m_20189_() + look.f_82481_);
   }

   private void duringChargingEvent(LivingEntity entity, IAbility ability) {
      entity.m_7292_(new MobEffectInstance((MobEffect)ModEffects.MOVEMENT_BLOCKED.get(), 5, 1, false, false));
   }

   private void endChargingEvent(LivingEntity entity, IAbility ability) {
      this.continuousComponent.triggerContinuity(entity);
   }

   private void startContinuityEvent(LivingEntity entity, IAbility ability) {
      this.repeaterComponent.start(entity, 10, 2);
   }

   private void endContinuityEvent(LivingEntity entity, IAbility ability) {
      this.animationComponent.stop(entity);
      this.cooldownComponent.startCooldown(entity, 260.0F);
   }

   private void triggerRepeaterEvent(LivingEntity entity, IAbility ability) {
      this.projectileComponent.shoot(entity, 3.0F, 4.0F);
   }

   private void endRepeaterEvent(LivingEntity entity, IAbility ability) {
      this.continuousComponent.stopContinuity(entity);
   }

   private SlaveArrowProjectile createProjectile(LivingEntity entity) {
      SlaveArrowProjectile proj = new SlaveArrowProjectile(entity.m_9236_(), entity, this);
      return proj;
   }
}
