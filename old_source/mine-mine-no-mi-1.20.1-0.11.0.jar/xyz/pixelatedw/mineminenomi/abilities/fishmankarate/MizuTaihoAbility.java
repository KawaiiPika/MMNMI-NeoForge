package xyz.pixelatedw.mineminenomi.abilities.fishmankarate;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
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
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.fishmankarate.MizuTaihoProjectile;
import xyz.pixelatedw.mineminenomi.init.ModAnimations;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class MizuTaihoAbility extends Ability {
   private static final int COOLDOWN = 340;
   private static final int CHARGE_TIME = 30;
   public static final RegistryObject<AbilityCore<MizuTaihoAbility>> INSTANCE = ModRegistry.registerAbility("mizu_taiho", "Mizu Taiho", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Launches a giant bubble, dealing massive damage and exploding on contact.", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.RACIAL, MizuTaihoAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(340.0F), ChargeComponent.getTooltip(30.0F)).addAdvancedDescriptionLine(ProjectileComponent.getProjectileTooltips()).setSourceHakiNature(SourceHakiNature.HARDENING).setSourceType(SourceType.FIST).build("mineminenomi");
   });
   private final ChargeComponent chargeComponent = (new ChargeComponent(this)).addStartEvent(100, this::startChargeEvent).addEndEvent(100, this::endChargeEvent);
   private final ProjectileComponent projectileComponent = new ProjectileComponent(this, this::createProjectile);
   private final AnimationComponent animationComponent = new AnimationComponent(this);
   private final ExplosionComponent explosionComponent = new ExplosionComponent(this);

   public MizuTaihoAbility(AbilityCore<MizuTaihoAbility> core) {
      super(core);
      this.addComponents(new AbilityComponent[]{this.chargeComponent, this.projectileComponent, this.animationComponent, this.explosionComponent});
      this.addUseEvent(this::useEvent);
   }

   private void useEvent(LivingEntity entity, IAbility ability) {
      this.chargeComponent.startCharging(entity, 30.0F);
   }

   private void startChargeEvent(LivingEntity entity, IAbility ability) {
      this.animationComponent.start(entity, ModAnimations.CROSSED_ARMS);
   }

   private void endChargeEvent(LivingEntity entity, IAbility ability) {
      WyHelper.spawnParticleEffect((ParticleEffect)ModParticleEffects.MIZU_TAIHO.get(), entity, entity.m_20185_(), (double)1.5F + entity.m_20186_(), entity.m_20189_());
      this.animationComponent.stop(entity);
      this.projectileComponent.shoot(entity, 2.0F, 0.0F);
      this.cooldownComponent.startCooldown(entity, 340.0F);
   }

   private MizuTaihoProjectile createProjectile(LivingEntity entity) {
      MizuTaihoProjectile projectile = new MizuTaihoProjectile(entity.m_9236_(), entity, this);
      return projectile;
   }
}
