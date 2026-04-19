package xyz.pixelatedw.mineminenomi.abilities.cyborg;

import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
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
import xyz.pixelatedw.mineminenomi.api.abilities.components.ColaUsageComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DamageTakenComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.data.entity.stats.EntityStatsCapability;
import xyz.pixelatedw.mineminenomi.data.entity.stats.IEntityStats;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class CoupDeBooAbility extends Ability {
   private static final float COOLDOWN = 200.0F;
   private static final int COLA_REQUIRED = 20;
   private static final int RANGE = 5;
   public static final RegistryObject<AbilityCore<CoupDeBooAbility>> INSTANCE = ModRegistry.registerAbility("coup_de_boo", "Coup De Boo", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Launches the user into the sky.", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.RACIAL, CoupDeBooAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(200.0F), ColaUsageComponent.getColaTooltip(20), RangeComponent.getTooltip(5.0F, RangeComponent.RangeType.AOE)).setUnlockCheck(CoupDeBooAbility::canUnlock).build("mineminenomi");
   });
   private final DamageTakenComponent damageTakenComponent = (new DamageTakenComponent(this)).addOnAttackEvent(this::onDamageTaken);
   private final RangeComponent rangeComponent = new RangeComponent(this);
   private final ColaUsageComponent colaUsageComponent = new ColaUsageComponent(this);
   private boolean hasFallDamage = true;

   public CoupDeBooAbility(AbilityCore<CoupDeBooAbility> core) {
      super(core);
      this.addComponents(new AbilityComponent[]{this.damageTakenComponent, this.rangeComponent, this.colaUsageComponent});
      this.addCanUseCheck(ColaUsageComponent.hasEnoughCola(20));
      this.addUseEvent(this::useEvent);
   }

   private void useEvent(LivingEntity entity, IAbility ability) {
      this.hasFallDamage = false;
      IEntityStats props = (IEntityStats)EntityStatsCapability.get(entity).orElse((Object)null);
      if (props != null) {
         Vec3 speed = entity.m_20154_().m_82542_((double)2.0F, (double)1.5F, (double)2.0F);
         AbilityHelper.setDeltaMovement(entity, speed.f_82479_, speed.f_82480_ + (double)3.5F, speed.f_82481_);
         this.colaUsageComponent.consumeCola(entity, 20);
         WyHelper.spawnParticleEffect((ParticleEffect)ModParticleEffects.COUP_DE_BOO.get(), entity, entity.m_20185_(), entity.m_20186_(), entity.m_20189_());

         for(LivingEntity target : this.rangeComponent.getTargetsInArea(entity, 5.0F)) {
            target.m_7292_(new MobEffectInstance(MobEffects.f_19614_, 200, 1));
         }

         this.cooldownComponent.startCooldown(entity, 200.0F);
      }
   }

   private float onDamageTaken(LivingEntity entity, IAbility ability, DamageSource damageSource, float damage) {
      if (!this.hasFallDamage && damageSource.m_276093_(DamageTypes.f_268671_)) {
         this.hasFallDamage = true;
         return 0.0F;
      } else {
         return damage;
      }
   }

   private static boolean canUnlock(LivingEntity user) {
      IEntityStats props = (IEntityStats)EntityStatsCapability.get(user).orElse((Object)null);
      return props == null ? false : props.isCyborg();
   }
}
