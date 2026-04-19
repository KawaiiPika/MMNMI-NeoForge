package xyz.pixelatedw.mineminenomi.api.abilities.components;

import java.util.List;
import java.util.UUID;
import java.util.function.Function;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityStat;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.damagesources.TargetPredicate;
import xyz.pixelatedw.mineminenomi.api.helpers.TargetHelper;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18nAbilities;

public class RangeComponent extends AbilityComponent<IAbility> {
   private static final UUID RANGE_BONUS_MANAGER_UUID = UUID.fromString("c3bc77bd-2f17-4d99-bcb6-e2305b5c075f");
   private float previousRange;
   private float previousDistance;
   private TargetPredicate areaCheck;
   private TargetPredicate lineCheck;
   private final BonusManager bonusManager;

   public static AbilityDescriptionLine.IDescriptionLine getTooltip(Function<LivingEntity, Float> rangeFunc, RangeType type) {
      return (entity, ability) -> {
         float range = (Float)rangeFunc.apply(entity);
         return getToolTip(entity, ability, range, range, type);
      };
   }

   public static AbilityDescriptionLine.IDescriptionLine getTooltip(float range, RangeType type) {
      return getTooltip(range, range, type);
   }

   public static AbilityDescriptionLine.IDescriptionLine getTooltip(float min, float max, RangeType type) {
      return (entity, ability) -> getToolTip(entity, ability, min, max, type);
   }

   public static Component getToolTip(LivingEntity entity, IAbility ability, float min, float max, RangeType type) {
      AbilityStat.Builder statBuilder = new AbilityStat.Builder(ModI18nAbilities.DESCRIPTION_STAT_NAME_RANGE, min, max);
      ability.getComponent((AbilityComponentKey)ModAbilityComponents.RANGE.get()).ifPresent((comp) -> {
         float bonus = comp.getBonusManager().applyBonus(comp.getPreviousRange()) - comp.getPreviousRange();
         AbilityStat.AbilityStatType bonusType = bonus > 0.0F ? AbilityStat.AbilityStatType.BUFF : (bonus < 0.0F ? AbilityStat.AbilityStatType.DEBUFF : AbilityStat.AbilityStatType.NEUTRAL);
         statBuilder.withBonus(bonus, bonusType);
      });
      Component var10000;
      switch (type) {
         case AOE -> var10000 = ModI18nAbilities.DESCRIPTION_STAT_UNIT_AOE_BLOCKS;
         case CONE -> var10000 = ModI18nAbilities.DESCRIPTION_STAT_UNIT_CONE_BLOCKS;
         case LINE -> var10000 = ModI18nAbilities.DESCRIPTION_STAT_UNIT_LINE_BLOCKS;
         default -> var10000 = ModI18nAbilities.DESCRIPTION_STAT_UNIT_BLOCKS;
      }

      Component unit = var10000;
      return statBuilder.withUnit(unit).build().getStatDescription();
   }

   public RangeComponent(IAbility ability) {
      super((AbilityComponentKey)ModAbilityComponents.RANGE.get(), ability);
      this.areaCheck = TargetPredicate.DEFAULT_AREA_CHECK;
      this.lineCheck = TargetPredicate.DEFAULT_LINE_CHECK;
      this.bonusManager = new BonusManager(RANGE_BONUS_MANAGER_UUID);
      this.addBonusManager(this.bonusManager);
   }

   public List<LivingEntity> getTargetsInArea(LivingEntity entity, float range) {
      return this.getTargetsInArea(entity, range, this.areaCheck);
   }

   public List<LivingEntity> getTargetsInArea(LivingEntity entity, BlockPos centerPos, float range) {
      return this.getTargetsInArea(entity, centerPos, range, this.areaCheck);
   }

   public List<LivingEntity> getTargetsInArea(LivingEntity entity, float range, @Nullable TargetPredicate predicate) {
      return this.getTargetsInArea(entity, entity.m_20183_(), range, predicate);
   }

   public List<LivingEntity> getTargetsInArea(LivingEntity entity, BlockPos centerPos, float range, @Nullable TargetPredicate predicate) {
      return this.<LivingEntity>getTargetsInArea(entity, centerPos, range, predicate, LivingEntity.class);
   }

   public <E extends LivingEntity> List<E> getTargetsInArea(LivingEntity entity, BlockPos centerPos, float range, @Nullable TargetPredicate predicate, Class<E> clz) {
      this.ensureIsRegistered();
      this.previousRange = range;
      range = this.bonusManager.applyBonus(range);
      return TargetHelper.<E>getEntitiesInArea(entity.m_9236_(), entity, centerPos, (double)range, predicate, clz);
   }

   public List<LivingEntity> getTargetsInLine(LivingEntity entity, float distance, float width) {
      return this.getTargetsInLine(entity, distance, width, this.lineCheck);
   }

   public List<LivingEntity> getTargetsInLine(LivingEntity entity, BlockPos centerPos, float distance, float width) {
      return this.getTargetsInLine(entity, centerPos, distance, width, this.lineCheck);
   }

   public List<LivingEntity> getTargetsInLine(LivingEntity entity, float distance, float width, @Nullable TargetPredicate predicate) {
      return this.getTargetsInLine(entity, entity.m_20183_(), distance, width, predicate);
   }

   public List<LivingEntity> getTargetsInLine(LivingEntity entity, BlockPos centerPos, float distance, float width, @Nullable TargetPredicate predicate) {
      return this.<LivingEntity>getTargetsInLine(entity, entity.m_20183_(), distance, width, predicate, LivingEntity.class);
   }

   public <E extends LivingEntity> List<E> getTargetsInLine(LivingEntity entity, BlockPos centerPos, float distance, float width, @Nullable TargetPredicate predicate, Class<E> clz) {
      this.ensureIsRegistered();
      this.previousRange = width;
      this.previousDistance = distance;
      distance = this.bonusManager.applyBonus(distance);
      width = this.bonusManager.applyBonus(width);
      return TargetHelper.<E>getEntitiesInLine(entity, centerPos, distance, width, predicate, clz);
   }

   public float getPreviousRange() {
      return this.previousRange;
   }

   public float getRange() {
      return this.bonusManager.applyBonus(this.previousRange);
   }

   public float getPreviousDistance() {
      return this.previousDistance;
   }

   public float getDistance() {
      return this.bonusManager.applyBonus(this.previousDistance);
   }

   public void setAreaCheck(TargetPredicate check) {
      this.areaCheck = check;
   }

   public void setLineCheck(TargetPredicate check) {
      this.lineCheck = check;
   }

   public BonusManager getBonusManager() {
      return this.bonusManager;
   }

   public static enum RangeType {
      AOE,
      CONE,
      LINE;

      // $FF: synthetic method
      private static RangeType[] $values() {
         return new RangeType[]{AOE, CONE, LINE};
      }
   }
}
