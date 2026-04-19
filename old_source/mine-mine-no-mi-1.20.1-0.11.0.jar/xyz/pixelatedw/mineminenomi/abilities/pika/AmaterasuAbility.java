package xyz.pixelatedw.mineminenomi.abilities.pika;

import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
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
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ExplosionComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ProjectileComponent;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceElement;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.pika.AmaterasuProjectile;
import xyz.pixelatedw.mineminenomi.init.ModAnimations;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.ModSounds;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class AmaterasuAbility extends Ability {
   private static final int COOLDOWN = 240;
   private static final int CHARGE_TIME = 80;
   public static final RegistryObject<AbilityCore<AmaterasuAbility>> INSTANCE = ModRegistry.registerAbility("amaterasu", "Amaterasu", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Charges up a powerful concentrated light beam. The longer its charged the more powerful it becomes.", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, AmaterasuAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(240.0F), ChargeComponent.getTooltip(80.0F)).addAdvancedDescriptionLine(ProjectileComponent.getProjectileTooltips()).setSourceHakiNature(SourceHakiNature.SPECIAL).setSourceElement(SourceElement.LIGHT).build("mineminenomi");
   });
   private final ChargeComponent chargeComponent = (new ChargeComponent(this)).addStartEvent(this::startChargeEvent).addTickEvent(this::duringChargeEvent).addEndEvent(this::stopChargeEvent);
   private final AnimationComponent animationComponent = new AnimationComponent(this);
   private final ExplosionComponent explosionComponent = new ExplosionComponent(this, 1508);
   private final ProjectileComponent projectileComponent = new ProjectileComponent(this, this::createProjectile);

   public AmaterasuAbility(AbilityCore<AmaterasuAbility> core) {
      super(core);
      this.addComponents(new AbilityComponent[]{this.chargeComponent, this.animationComponent, this.explosionComponent, this.projectileComponent});
      this.addUseEvent(this::useEvent);
   }

   private void useEvent(LivingEntity entity, IAbility ability) {
      this.chargeComponent.startCharging(entity, 80.0F);
   }

   private void startChargeEvent(LivingEntity entity, IAbility ability) {
      entity.m_9236_().m_5594_((Player)null, entity.m_20183_(), (SoundEvent)ModSounds.PIKA_CHARGE_SFX.get(), SoundSource.PLAYERS, 2.0F, 1.0F);
      this.animationComponent.start(entity, ModAnimations.AIM_SNIPER);
   }

   private void duringChargeEvent(LivingEntity entity, IAbility ability) {
      AbilityHelper.slowEntityFall(entity);
      WyHelper.spawnParticleEffect((ParticleEffect)ModParticleEffects.PIKA_CHARGING.get(), entity, entity.m_20185_(), entity.m_20186_(), entity.m_20189_());
   }

   private void stopChargeEvent(LivingEntity entity, IAbility ability) {
      entity.m_9236_().m_5594_((Player)null, entity.m_20183_(), (SoundEvent)ModSounds.PIKA_SFX.get(), SoundSource.PLAYERS, 2.0F, 1.0F);
      this.projectileComponent.shoot(entity);
      this.cooldownComponent.startCooldown(entity, 240.0F);
      this.animationComponent.stop(entity);
   }

   private AmaterasuProjectile createProjectile(LivingEntity entity) {
      AmaterasuProjectile projectile = new AmaterasuProjectile(entity.m_9236_(), entity, this);
      return projectile;
   }
}
