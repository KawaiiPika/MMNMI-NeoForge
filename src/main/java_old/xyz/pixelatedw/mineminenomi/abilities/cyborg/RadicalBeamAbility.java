package xyz.pixelatedw.mineminenomi.abilities.cyborg;

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
import xyz.pixelatedw.mineminenomi.api.abilities.components.ColaUsageComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ExplosionComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ProjectileComponent;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceElement;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.data.entity.stats.EntityStatsCapability;
import xyz.pixelatedw.mineminenomi.data.entity.stats.IEntityStats;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.cyborg.RadicalBeamProjectile;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.ModSounds;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class RadicalBeamAbility extends Ability {
   private static final float CHARGE_TIME = 60.0F;
   private static final float COOLDOWN = 260.0F;
   private static final int COLA_REQUIRED = 30;
   public static final float DAMAGE = 50.0F;
   public static final RegistryObject<AbilityCore<RadicalBeamAbility>> INSTANCE = ModRegistry.registerAbility("radical_beam", "Radical Beam", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("The user launches a powerful beam of energy at the opponent.", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.RACIAL, RadicalBeamAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(260.0F), ChargeComponent.getTooltip(60.0F), ColaUsageComponent.getColaTooltip(30)).addAdvancedDescriptionLine(ProjectileComponent.getProjectileTooltips()).setSourceHakiNature(SourceHakiNature.SPECIAL).setSourceElement(SourceElement.LIGHT).setUnlockCheck(RadicalBeamAbility::canUnlock).build("mineminenomi");
   });
   private final ChargeComponent chargeComponent = (new ChargeComponent(this)).addStartEvent(100, this::startChargingEvent).addTickEvent(100, this::tickChargingEvent).addEndEvent(100, this::endChargingEvent);
   private final AnimationComponent animationComponent = new AnimationComponent(this);
   private final ExplosionComponent explosionComponent = new ExplosionComponent(this, 1508);
   private final ColaUsageComponent colaUsageComponent = new ColaUsageComponent(this);
   private final ProjectileComponent projectileComponent = new ProjectileComponent(this, this::createProjectile);

   public RadicalBeamAbility(AbilityCore<RadicalBeamAbility> core) {
      super(core);
      this.addComponents(new AbilityComponent[]{this.chargeComponent, this.animationComponent, this.explosionComponent, this.colaUsageComponent, this.projectileComponent});
      this.addCanUseCheck(ColaUsageComponent.hasEnoughCola(30));
      this.addUseEvent(this::useEvent);
   }

   private void useEvent(LivingEntity entity, IAbility ability) {
      this.chargeComponent.startCharging(entity, 60.0F);
   }

   private void startChargingEvent(LivingEntity entity, IAbility ability) {
      entity.m_9236_().m_5594_((Player)null, entity.m_20183_(), (SoundEvent)ModSounds.CHARGE_CYBORG_BEAM_SFX.get(), SoundSource.PLAYERS, 2.0F, 1.0F);
   }

   private void tickChargingEvent(LivingEntity entity, IAbility ability) {
      if (this.chargeComponent.getChargeTime() == 39.0F) {
         entity.m_9236_().m_5594_((Player)null, entity.m_20183_(), (SoundEvent)ModSounds.PRE_CYBORG_BEAM_SFX.get(), SoundSource.PLAYERS, 2.0F, 1.0F);
      }

      WyHelper.spawnParticleEffect((ParticleEffect)ModParticleEffects.CHARGE_RADICAL_BEAM.get(), entity, entity.m_20185_(), entity.m_20186_(), entity.m_20189_());
   }

   private void endChargingEvent(LivingEntity entity, IAbility ability) {
      entity.m_9236_().m_5594_((Player)null, entity.m_20183_(), (SoundEvent)ModSounds.CYBORG_BEAM_SFX.get(), SoundSource.PLAYERS, 3.0F, 1.0F);
      this.projectileComponent.shoot(entity);
      this.colaUsageComponent.consumeCola(entity, 30);
      this.cooldownComponent.startCooldown(entity, 260.0F);
   }

   private RadicalBeamProjectile createProjectile(LivingEntity entity) {
      return new RadicalBeamProjectile(entity.m_9236_(), entity, this);
   }

   private static boolean canUnlock(LivingEntity user) {
      IEntityStats props = (IEntityStats)EntityStatsCapability.get(user).orElse((Object)null);
      return props == null ? false : props.isCyborg();
   }
}
