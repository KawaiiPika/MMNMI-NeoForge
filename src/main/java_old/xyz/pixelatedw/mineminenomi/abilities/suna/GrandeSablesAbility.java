package xyz.pixelatedw.mineminenomi.abilities.suna;

import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.AABB;
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
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DashComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DealDamageComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.HitTrackerComponent;
import xyz.pixelatedw.mineminenomi.api.damagesources.IDamageSourceHandler;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityUseConditions;
import xyz.pixelatedw.mineminenomi.api.util.Interval;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class GrandeSablesAbility extends Ability {
   private static final float COOLDOWN_BONUS = 0.8F;
   private static final int COOLDOWN = 200;
   private static final int HOLD_TIME = 50;
   private static final float DAMAGE = 8.0F;
   public static final RegistryObject<AbilityCore<GrandeSablesAbility>> INSTANCE = ModRegistry.registerAbility("grande_sables", "Grande Sables", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Surrounds the user into a sand tornado, increasing their speed and pulling all nearby entities towards it damaging them.", (Object)null), ImmutablePair.of("While in a desert the cooldown of this ability is reduced by %s.", new Object[]{"§a" + Math.round(19.999998F) + "%§r"}));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, GrandeSablesAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(200.0F), ContinuousComponent.getTooltip(50.0F), DealDamageComponent.getTooltip(8.0F)).setSourceType(SourceType.INTERNAL).build("mineminenomi");
   });
   private final ContinuousComponent continuityComponent = (new ContinuousComponent(this)).addStartEvent(this::startContinuityEvent).addTickEvent(this::duringContinuityEvent).addEndEvent(this::endContinuityEvent);
   private final HitTrackerComponent hitTrackerComponent = new HitTrackerComponent(this);
   private final DealDamageComponent dealDamageComponent = new DealDamageComponent(this);
   private final DashComponent dashComponent = new DashComponent(this);
   private Interval particleInterval = new Interval(15);

   public GrandeSablesAbility(AbilityCore<GrandeSablesAbility> core) {
      super(core);
      this.addComponents(new AbilityComponent[]{this.continuityComponent, this.hitTrackerComponent, this.dealDamageComponent, this.dashComponent});
      this.addCanUseCheck(AbilityUseConditions::requiresDryUser);
      this.addCanUseCheck(AbilityUseConditions::canUseMomentumAbilities);
      this.addUseEvent(this::useEvent);
   }

   private void useEvent(LivingEntity entity, IAbility ability) {
      this.continuityComponent.triggerContinuity(entity, 50.0F);
   }

   private void startContinuityEvent(LivingEntity entity, IAbility ability) {
      this.hitTrackerComponent.clearHits();
      AbilityHelper.setDeltaMovement(entity, (double)0.0F, (double)2.0F, (double)0.0F);
      this.particleInterval.restartIntervalToZero();
   }

   private void duringContinuityEvent(LivingEntity entity, IAbility ability) {
      if (!(this.continuityComponent.getContinueTime() < 3.0F)) {
         this.dashComponent.dashXYZ(entity, 1.5F, 0.0F);
         entity.m_7292_(new MobEffectInstance((MobEffect)ModEffects.VANISH.get(), 10, 0, false, false));
         if (this.particleInterval.canTick()) {
            WyHelper.spawnParticleEffect((ParticleEffect)ModParticleEffects.SABLES.get(), entity, entity.m_20185_(), entity.m_20186_(), entity.m_20189_());
         }

         float growthXZ = 8.0F;
         float growthY = 20.0F;
         AABB box = (new AABB(entity.m_20183_())).m_82377_((double)growthXZ, (double)growthY, (double)growthXZ);
         DamageSource source = this.dealDamageComponent.getDamageSource(entity);
         IDamageSourceHandler sourceHandler = IDamageSourceHandler.getHandler(source);
         sourceHandler.bypassLogia();
         sourceHandler.addAbilityPiercing(1.0F, this.getCore());

         for(Entity target : entity.m_9236_().m_6443_(Entity.class, box, (e) -> e != entity)) {
            AbilityHelper.setDeltaMovement(target, target.m_20184_().f_82479_ + (entity.m_20185_() - target.m_20185_()) / (double)20.0F, target.m_20184_().f_82480_ + (entity.m_20186_() - target.m_20186_()) / (double)20.0F, target.m_20184_().f_82481_ + (entity.m_20189_() - target.m_20189_()) / (double)20.0F);
            if (this.hitTrackerComponent.canHit(target) && target instanceof LivingEntity && entity.m_20270_(target) < 2.0F) {
               this.dealDamageComponent.hurtTarget(entity, (LivingEntity)target, 8.0F, source);
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
