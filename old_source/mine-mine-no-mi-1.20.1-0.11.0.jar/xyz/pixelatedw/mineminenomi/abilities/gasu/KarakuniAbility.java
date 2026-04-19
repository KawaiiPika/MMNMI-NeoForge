package xyz.pixelatedw.mineminenomi.abilities.gasu;

import java.util.List;
import java.util.UUID;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.level.block.Blocks;
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
import xyz.pixelatedw.mineminenomi.api.abilities.components.DealDamageComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent;
import xyz.pixelatedw.mineminenomi.api.damagesources.IDamageSourceHandler;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.morph.MorphInfo;
import xyz.pixelatedw.mineminenomi.api.util.Interval;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModMorphs;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class KarakuniAbility extends Ability {
   private static final UUID SHINOKUNI_RANGE_BONUS = UUID.fromString("05f3fc04-68e5-4f7b-a513-7237ad6fd643");
   private static final int COOLDOWN = 600;
   private static final int HOLD_TIME = 100;
   private static final int RANGE = 9;
   public static final RegistryObject<AbilityCore<KarakuniAbility>> INSTANCE = ModRegistry.registerAbility("karakuni", "Karakuni", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Removes the oxygen around the user, suffocating and weakening everyone in the vicinity", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, KarakuniAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(600.0F), ContinuousComponent.getTooltip(100.0F), RangeComponent.getTooltip(9.0F, RangeComponent.RangeType.AOE)).setSourceType(SourceType.INTERNAL).build("mineminenomi");
   });
   private final ContinuousComponent continuousComponent = (new ContinuousComponent(this)).addStartEvent(100, this::startContinuityEvent).addTickEvent(this::duringContinuityEvent).addEndEvent(this::stopContinuityEvent);
   private final RangeComponent rangeComponent = new RangeComponent(this);
   private final DealDamageComponent dealDamageComponent = new DealDamageComponent(this);
   private Interval suffocateInterval = new Interval(2);

   public KarakuniAbility(AbilityCore<KarakuniAbility> core) {
      super(core);
      this.addComponents(new AbilityComponent[]{this.continuousComponent, this.rangeComponent, this.dealDamageComponent});
      this.addUseEvent(this::useEvent);
   }

   private void useEvent(LivingEntity entity, IAbility ability) {
      this.continuousComponent.triggerContinuity(entity, 100.0F);
   }

   private void startContinuityEvent(LivingEntity entity, IAbility ability) {
      this.suffocateInterval.restartIntervalToZero();
   }

   private void duringContinuityEvent(LivingEntity entity, IAbility ability) {
      if (this.suffocateInterval.canTick()) {
         this.rangeComponent.getBonusManager().removeBonus(SHINOKUNI_RANGE_BONUS);
         if (((MorphInfo)ModMorphs.SHINOKUNI.get()).isActive(entity)) {
            this.rangeComponent.getBonusManager().addBonus(SHINOKUNI_RANGE_BONUS, "Shinokuni Range Bonus", BonusOperation.ADD, 3.0F);
         }

         List<LivingEntity> targets = this.rangeComponent.getTargetsInArea(entity, 9.0F);
         List<BlockPos> blocks = WyHelper.getNearbyBlocks(entity, 9);
         this.dealDamageComponent.getDamageSource(entity);

         for(LivingEntity target : targets) {
            if (target.m_6336_() != MobType.f_21641_) {
               target.m_7292_(new MobEffectInstance(MobEffects.f_19597_, 500, 2, false, false));
               target.m_7292_(new MobEffectInstance(MobEffects.f_19613_, 500, 1, false, false));
               target.m_20301_(target.m_20146_() - 50);
               int airLeft = target.m_20146_();
               if (airLeft <= 0) {
                  if (target.m_21223_() > 8.0F) {
                     DamageSource source = this.dealDamageComponent.getDamageSource(entity);
                     IDamageSourceHandler sourceHandler = IDamageSourceHandler.getHandler(source);
                     sourceHandler.bypassLogia();
                     sourceHandler.addAbilityPiercing(1.0F, this.getCore());
                     sourceHandler.setUnavoidable();
                     this.dealDamageComponent.hurtTarget(entity, target, 8.0F, source);
                  } else {
                     target.m_7292_(new MobEffectInstance((MobEffect)ModEffects.UNCONSCIOUS.get(), 200, 1));
                  }
               }

               if (target.m_6060_()) {
                  target.m_20095_();
               }
            }
         }

         if (entity.m_6060_()) {
            entity.m_20095_();
         }

         BlockPos.MutableBlockPos blockUp = new BlockPos.MutableBlockPos();

         for(BlockPos blockPos : blocks) {
            blockUp.m_122178_(blockPos.m_123341_(), blockPos.m_123342_() + 1, blockPos.m_123343_());
            if (entity.m_9236_().m_8055_(blockPos).m_60734_() == Blocks.f_50083_ && entity.m_9236_().m_8055_(blockUp).m_60734_() == Blocks.f_50016_) {
               entity.m_9236_().m_7471_(blockPos, false);
            }
         }
      }

   }

   private void stopContinuityEvent(LivingEntity entity, IAbility ability) {
      this.cooldownComponent.startCooldown(entity, 600.0F);
   }
}
