package xyz.pixelatedw.mineminenomi.abilities.zushi;

import java.util.List;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DealDamageComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent;
import xyz.pixelatedw.mineminenomi.api.blockgen.BlockGenerators;
import xyz.pixelatedw.mineminenomi.api.blockgen.SimpleBlockPlacer;
import xyz.pixelatedw.mineminenomi.api.damagesources.IDamageSourceHandler;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceElement;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.protection.DefaultProtectionRules;
import xyz.pixelatedw.mineminenomi.api.util.Interval;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class JigokuTabiAbility extends Ability {
   private static final int HOLD_TIME = 120;
   private static final int MIN_COOLDOWN = 100;
   private static final int MAX_COOLDOWN = 340;
   private static final int RANGE = 24;
   private static final int DEFAULT_FORCE = 4;
   private static final int FORCE_INCREASE = 60;
   private static final int MIN_DAMAGE = 8;
   private static final int MAX_DAMAGE = 12;
   public static final RegistryObject<AbilityCore<JigokuTabiAbility>> INSTANCE = ModRegistry.registerAbility("jigoku_tabi", "Jigoku Tabi", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Causes a powerful downward force of gravity, sending the enemies down in a crater. The longer its used the more damage it'll deal and the larger the crater will become.", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, JigokuTabiAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(100.0F, 340.0F), ContinuousComponent.getTooltip(120.0F), RangeComponent.getTooltip(24.0F, RangeComponent.RangeType.AOE), DealDamageComponent.getTooltip(8.0F, 12.0F)).setSourceElement(SourceElement.GRAVITY).build("mineminenomi");
   });
   private final ContinuousComponent continuousComponent = (new ContinuousComponent(this)).addStartEvent(this::startContinuityEvent).addTickEvent(this::duringContinuityEvent).addEndEvent(this::endContinuityEvent);
   private final RangeComponent rangeComponent = new RangeComponent(this);
   private final DealDamageComponent dealDamageComponent = new DealDamageComponent(this);
   private int force = 4;
   private Interval damageInterval = new Interval(20);
   private Interval addForceInterval = new Interval(60);

   public JigokuTabiAbility(AbilityCore<JigokuTabiAbility> core) {
      super(core);
      this.addComponents(new AbilityComponent[]{this.continuousComponent, this.rangeComponent, this.dealDamageComponent});
      this.addUseEvent(this::useEvent);
   }

   private void useEvent(LivingEntity entity, IAbility ability) {
      this.continuousComponent.triggerContinuity(entity, 120.0F);
   }

   private void startContinuityEvent(LivingEntity entity, IAbility ability) {
      this.force = 4;
      this.damageInterval.restartIntervalToZero();
   }

   private void duringContinuityEvent(LivingEntity entity, IAbility ability) {
      if (!entity.m_9236_().f_46443_) {
         List<LivingEntity> targets = this.rangeComponent.getTargetsInArea(entity, 24.0F);
         DamageSource source = this.dealDamageComponent.getDamageSource(entity);
         IDamageSourceHandler sourceHandler = IDamageSourceHandler.getHandler(source);
         sourceHandler.bypassLogia();
         sourceHandler.addAbilityPiercing(1.0F, this.getCore());
         sourceHandler.setUnavoidable();
         SimpleBlockPlacer placer = (new SimpleBlockPlacer()).setBlock(Blocks.f_50016_.m_49966_()).setSize(this.force, 2).setRule(DefaultProtectionRules.CORE_FOLIAGE_ORE);

         for(LivingEntity target : targets) {
            AbilityHelper.setDeltaMovement(target, (double)0.0F, target.m_20184_().f_82480_ - (double)4.0F, (double)0.0F);
            if (this.damageInterval.canTick()) {
               MobEffectInstance instance = new MobEffectInstance(MobEffects.f_19597_, 25, 5, false, false);
               target.m_7292_(instance);
               this.dealDamageComponent.hurtTarget(entity, target, (float)(this.force * 2), source);
               GraviZoneAbility.gravityRing(target, 3, 2, false);
               placer.generate(entity.m_9236_(), target.m_20183_(), BlockGenerators.SPHERE);
            }
         }

         if (this.addForceInterval.canTick()) {
            ++this.force;
         }

      }
   }

   private void endContinuityEvent(LivingEntity entity, IAbility ability) {
      float cooldown = 100.0F + this.continuousComponent.getContinueTime() * 2.0F;
      this.cooldownComponent.startCooldown(entity, cooldown);
   }
}
