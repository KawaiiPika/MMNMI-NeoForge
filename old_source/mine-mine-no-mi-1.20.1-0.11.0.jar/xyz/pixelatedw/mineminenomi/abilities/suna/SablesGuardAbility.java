package xyz.pixelatedw.mineminenomi.abilities.suna;

import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
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
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityUseConditions;
import xyz.pixelatedw.mineminenomi.entities.TornadoEntity;
import xyz.pixelatedw.mineminenomi.init.ModAnimations;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class SablesGuardAbility extends Ability {
   private static final float COOLDOWN_BONUS = 0.8F;
   private static final int COOLDOWN = 300;
   private static final int HOLD_TIME = 60;
   private static final int RANGE = 10;
   public static final RegistryObject<AbilityCore<SablesGuardAbility>> INSTANCE = ModRegistry.registerAbility("sables_guard", "Sables Guard", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("The user starts a sandstorm with themselves as the center of it, sending all nearby enemies flying away.", (Object)null), ImmutablePair.of("While in a desert the cooldown of this ability is reduced by %s.", new Object[]{"§a" + Math.round(19.999998F) + "%§r"}));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, SablesGuardAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(300.0F), ContinuousComponent.getTooltip(60.0F), RangeComponent.getTooltip(10.0F, RangeComponent.RangeType.AOE)).build("mineminenomi");
   });
   private final ContinuousComponent continuityComponent = (new ContinuousComponent(this)).addStartEvent(this::startContinuityEvent).addTickEvent(this::duringContinuityEvent).addEndEvent(this::endContinuityEvent);
   private final RangeComponent rangeComponent = new RangeComponent(this);
   private final AnimationComponent animationComponent = new AnimationComponent(this);
   private TornadoEntity proj = null;

   public SablesGuardAbility(AbilityCore<SablesGuardAbility> core) {
      super(core);
      this.addComponents(new AbilityComponent[]{this.continuityComponent, this.rangeComponent, this.animationComponent});
      this.addCanUseCheck(AbilityUseConditions::requiresDryUser);
      this.addUseEvent(this::useEvent);
   }

   private void useEvent(LivingEntity entity, IAbility ability) {
      this.continuityComponent.triggerContinuity(entity, 60.0F);
   }

   private void startContinuityEvent(LivingEntity entity, IAbility ability) {
      this.proj = null;
      this.animationComponent.start(entity, ModAnimations.RAISE_RIGHT_ARM);
   }

   private void duringContinuityEvent(LivingEntity entity, IAbility ability) {
      entity.m_7292_(new MobEffectInstance((MobEffect)ModEffects.MOVEMENT_BLOCKED.get(), 5, 1));
      if (this.proj == null) {
         this.proj = new TornadoEntity(entity.m_9236_(), entity);
         this.proj.setMaxLife(60);
         this.proj.setSize(20.0F);
         this.proj.setSpeed(-2.0F);
         this.proj.m_6034_(entity.m_20182_().f_82479_, entity.m_20182_().f_82480_, entity.m_20182_().f_82481_);
         entity.m_9236_().m_7967_(this.proj);
      }

      if (this.proj.m_6084_() && this.proj != null) {
         if (this.continuityComponent.getContinueTime() % 5.0F == 0.0F) {
            for(LivingEntity target : this.rangeComponent.getTargetsInArea(entity, 10.0F)) {
               Vec3 moveVec = target.m_20182_().m_82546_(this.proj.m_20182_()).m_82541_().m_82542_((double)4.0F, (double)1.0F, (double)4.0F).m_82520_((double)0.0F, (double)1.0F, (double)0.0F);
               AbilityHelper.setDeltaMovement(target, moveVec);
            }
         }

      } else {
         this.continuityComponent.stopContinuity(entity);
      }
   }

   private void endContinuityEvent(LivingEntity entity, IAbility ability) {
      if (this.proj != null && this.proj.m_6084_()) {
         this.proj.m_146870_();
      }

      this.animationComponent.stop(entity);
      this.cooldownComponent.getBonusManager().removeBonus(SunaHelper.DESERT_COOLDOWN_BONUS);
      if (SunaHelper.isFruitBoosted(entity)) {
         this.cooldownComponent.getBonusManager().addBonus(SunaHelper.DESERT_COOLDOWN_BONUS, "Desert Cooldown Bonus", BonusOperation.MUL, 0.8F);
      }

      this.cooldownComponent.startCooldown(entity, 300.0F);
   }
}
