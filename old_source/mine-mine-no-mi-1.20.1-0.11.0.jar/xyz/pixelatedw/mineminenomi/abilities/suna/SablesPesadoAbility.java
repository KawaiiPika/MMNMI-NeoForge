package xyz.pixelatedw.mineminenomi.abilities.suna;

import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
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
import xyz.pixelatedw.mineminenomi.api.abilities.components.BonusOperation;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ExplosionComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ProjectileComponent;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityUseConditions;
import xyz.pixelatedw.mineminenomi.api.util.Interval;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.suna.SablesPesadoProjectile;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.ModSounds;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class SablesPesadoAbility extends Ability {
   private static final float COOLDOWN_BONUS = 0.8F;
   private static final float CHARGE_BONUS = 0.5F;
   private static final int COOLDOWN = 500;
   private static final int CHARGE_TIME = 100;
   public static final RegistryObject<AbilityCore<SablesPesadoAbility>> INSTANCE = ModRegistry.registerAbility("sables_pesado", "Sables: Pesado", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("The user compresses a sandstorm to its limits and shoots it at extreme speeds.", (Object)null), ImmutablePair.of("While in a desert the cooldown of this ability is reduced by %s and charge time is reduced by %s.", new Object[]{"§a" + Math.round(19.999998F) + "%§r", "§a" + Math.round(50.0F) + "%§r"}));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, SablesPesadoAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(500.0F), ChargeComponent.getTooltip(100.0F)).addAdvancedDescriptionLine(ProjectileComponent.getProjectileTooltips()).build("mineminenomi");
   });
   private final ChargeComponent chargeComponent = (new ChargeComponent(this)).addTickEvent(this::duringChargeEvent).addEndEvent(this::endChargeEvent);
   private final ProjectileComponent projectileComponent = new ProjectileComponent(this, this::createProjectile);
   private final ExplosionComponent explosionComponent = new ExplosionComponent(this);
   private Interval particleInterval = new Interval(5);

   public SablesPesadoAbility(AbilityCore<SablesPesadoAbility> core) {
      super(core);
      this.addComponents(new AbilityComponent[]{this.chargeComponent, this.projectileComponent, this.explosionComponent});
      this.addCanUseCheck(AbilityUseConditions::requiresDryUser);
      this.addUseEvent(this::useEvent);
   }

   private void useEvent(LivingEntity entity, IAbility ability) {
      if (!this.chargeComponent.isCharging()) {
         this.chargeComponent.getMaxChargeBonusManager().removeBonus(SunaHelper.DESERT_CHARGE_BONUS);
         if (SunaHelper.isFruitBoosted(entity)) {
            this.chargeComponent.getMaxChargeBonusManager().addBonus(SunaHelper.DESERT_CHARGE_BONUS, "Desert Charge Bonus", BonusOperation.MUL, 0.5F);
         }

         this.particleInterval.restartIntervalToZero();
         this.chargeComponent.startCharging(entity, 100.0F);
      }

   }

   private void duringChargeEvent(LivingEntity entity, IAbility ability) {
      entity.m_7292_(new MobEffectInstance((MobEffect)ModEffects.MOVEMENT_BLOCKED.get(), 2, 1, false, false));
      entity.m_7292_(new MobEffectInstance((MobEffect)ModEffects.REDUCED_FALL.get(), 2, 1, false, false));
      if (this.particleInterval.canTick()) {
         WyHelper.spawnParticleEffect((ParticleEffect)ModParticleEffects.SABLES_PESADO.get(), entity, entity.m_20185_(), entity.m_20186_(), entity.m_20189_());
      }

   }

   private void endChargeEvent(LivingEntity entity, IAbility ability) {
      entity.m_9236_().m_5594_((Player)null, entity.m_20183_(), (SoundEvent)ModSounds.SABLES_PESADO_SFX.get(), SoundSource.PLAYERS, 2.0F, 1.0F);
      this.projectileComponent.shoot(entity, 3.25F, 4.0F);
      this.cooldownComponent.getBonusManager().removeBonus(SunaHelper.DESERT_COOLDOWN_BONUS);
      if (SunaHelper.isFruitBoosted(entity)) {
         this.cooldownComponent.getBonusManager().addBonus(SunaHelper.DESERT_COOLDOWN_BONUS, "Desert Cooldown Bonus", BonusOperation.MUL, 0.8F);
      }

      this.cooldownComponent.startCooldown(entity, 500.0F);
   }

   private SablesPesadoProjectile createProjectile(LivingEntity entity) {
      SablesPesadoProjectile proj = new SablesPesadoProjectile(entity.m_9236_(), entity, this);
      return proj;
   }
}
