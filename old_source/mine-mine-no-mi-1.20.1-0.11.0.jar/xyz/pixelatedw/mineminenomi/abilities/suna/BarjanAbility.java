package xyz.pixelatedw.mineminenomi.abilities.suna;

import java.util.List;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AnimationComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.BonusOperation;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DashComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DealDamageComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.HitTrackerComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityUseConditions;
import xyz.pixelatedw.mineminenomi.init.ModAnimations;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class BarjanAbility extends Ability {
   private static final float COOLDOWN_BONUS = 0.8F;
   private static final float DAMAGE_BONUS = 1.25F;
   private static final int COOLDOWN = 200;
   private static final int HOLD_TIME = 40;
   private static final float DAMAGE = 30.0F;
   private static final float RANGE = 1.6F;
   public static final RegistryObject<AbilityCore<BarjanAbility>> INSTANCE = ModRegistry.registerAbility("barjan", "Barjan", (id, name) -> {
      Pair[] var10002 = new Pair[]{ImmutablePair.of("Dashes forwards hitting all enemies it touches dehydrating them.", (Object)null), null};
      Object[] var10006 = new Object[]{"§a" + Math.round(19.999998F) + "%§r", null};
      float var10009 = Math.abs(-0.25F);
      var10006[1] = "§a" + Math.round(var10009 * 100.0F) + "%§r";
      var10002[1] = ImmutablePair.of("While in a desert the cooldown of this ability is reduced by %s and the damage is increased by %s.", var10006);
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, var10002);
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, BarjanAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(200.0F), ContinuousComponent.getTooltip(40.0F), DealDamageComponent.getTooltip(30.0F), RangeComponent.getTooltip(1.6F, RangeComponent.RangeType.AOE)).setSourceHakiNature(SourceHakiNature.IMBUING).build("mineminenomi");
   });
   private final ContinuousComponent continuityComponent = (new ContinuousComponent(this)).addStartEvent(this::startContinuityEvent).addTickEvent(this::duringContinuityEvent).addEndEvent(this::endContinuityEvent);
   private final HitTrackerComponent hitTrackerComponent = new HitTrackerComponent(this);
   private final AnimationComponent animationComponent = new AnimationComponent(this);
   private final RangeComponent rangeComponent = new RangeComponent(this);
   private final DealDamageComponent dealDamageComponent = new DealDamageComponent(this);
   private final DashComponent dashComponent = new DashComponent(this);

   public BarjanAbility(AbilityCore<BarjanAbility> core) {
      super(core);
      this.addComponents(new AbilityComponent[]{this.hitTrackerComponent, this.animationComponent, this.continuityComponent, this.rangeComponent, this.dealDamageComponent, this.dashComponent});
      this.addCanUseCheck(AbilityUseConditions::requiresDryUser);
      this.addCanUseCheck(AbilityUseConditions::canUseMomentumAbilities);
      this.addUseEvent(this::useEvent);
   }

   private void useEvent(LivingEntity entity, IAbility ability) {
      this.continuityComponent.startContinuity(entity, 40.0F);
   }

   private void startContinuityEvent(LivingEntity entity, IAbility ability) {
      this.animationComponent.start(entity, ModAnimations.BARJAN, 40);
      this.dashComponent.dashXYZ(entity, 4.0F);
      this.hitTrackerComponent.clearHits();
   }

   private void duringContinuityEvent(LivingEntity entity, IAbility ability) {
      WyHelper.spawnParticleEffect((ParticleEffect)ModParticleEffects.BARJAN.get(), entity, entity.m_20185_(), entity.m_20186_(), entity.m_20189_());
      List<LivingEntity> targets = this.rangeComponent.getTargetsInArea(entity, 1.6F);
      this.dealDamageComponent.getBonusManager().removeBonus(SunaHelper.DESERT_DAMAGE_BONUS);
      if (SunaHelper.isFruitBoosted(entity)) {
         this.dealDamageComponent.getBonusManager().addBonus(SunaHelper.DESERT_DAMAGE_BONUS, "Desert Damage Bonus", BonusOperation.MUL, 1.25F);
      }

      float finalDamage = 30.0F * (SunaHelper.isFruitBoosted(entity) ? 1.25F : 1.0F);

      for(LivingEntity target : targets) {
         if (this.hitTrackerComponent.canHit(target)) {
            boolean isHurt = this.dealDamageComponent.hurtTarget(entity, target, finalDamage);
            if (isHurt) {
               target.m_7292_(new MobEffectInstance((MobEffect)ModEffects.DEHYDRATION.get(), 200, 2, false, true));
               target.m_7292_(new MobEffectInstance(MobEffects.f_19613_, 150, 0, false, false));
               target.m_7292_(new MobEffectInstance(MobEffects.f_19597_, 150, 0, false, false));
               target.m_7292_(new MobEffectInstance(MobEffects.f_19599_, 150, 0, false, false));
            }
         }
      }

   }

   private void endContinuityEvent(LivingEntity entity, IAbility ability) {
      this.cooldownComponent.getBonusManager().removeBonus(SunaHelper.DESERT_COOLDOWN_BONUS);
      if (SunaHelper.isFruitBoosted(entity)) {
         this.cooldownComponent.getBonusManager().addBonus(SunaHelper.DESERT_COOLDOWN_BONUS, "Desert Cooldown Bonus", BonusOperation.MUL, 0.8F);
      }

      this.cooldownComponent.startCooldown(entity, 200.0F);
   }
}
