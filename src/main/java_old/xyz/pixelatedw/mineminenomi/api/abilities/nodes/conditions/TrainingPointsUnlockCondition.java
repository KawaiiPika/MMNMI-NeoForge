package xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.enums.TrainingPointType;
import xyz.pixelatedw.mineminenomi.data.entity.stats.EntityStatsCapability;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18nNodes;

public class TrainingPointsUnlockCondition implements NodeUnlockCondition {
   private final TrainingPointType type;
   private final double pointsNeeded;

   public static TrainingPointsUnlockCondition marksmanship(double points) {
      return new TrainingPointsUnlockCondition(TrainingPointType.MARKSMANSHIP, points);
   }

   public static TrainingPointsUnlockCondition martialArts(double points) {
      return new TrainingPointsUnlockCondition(TrainingPointType.MARTIAL_ARTS, points);
   }

   public static TrainingPointsUnlockCondition technology(double points) {
      return new TrainingPointsUnlockCondition(TrainingPointType.TECHNOLOGY, points);
   }

   public static TrainingPointsUnlockCondition weaponMastery(double points) {
      return new TrainingPointsUnlockCondition(TrainingPointType.WEAPON_MASTERY, points);
   }

   public TrainingPointsUnlockCondition(TrainingPointType type, double points) {
      this.type = type;
      this.pointsNeeded = points;
   }

   public boolean test(LivingEntity target) {
      int tp = (Integer)EntityStatsCapability.getLazy(target).map((props) -> props.getTrainingPoints(this.type)).orElse(0);
      return (double)tp >= this.pointsNeeded;
   }

   public MutableComponent getTooltip() {
      String var10000;
      switch (this.type) {
         case MARKSMANSHIP -> var10000 = ModI18nNodes.MARKSMANSHIP_POINTS_CHECK;
         case MARTIAL_ARTS -> var10000 = ModI18nNodes.MARTIAL_ARTS_POINTS_CHECK;
         case TECHNOLOGY -> var10000 = ModI18nNodes.TECHNOLOGY_POINTS_CHECK;
         case WEAPON_MASTERY -> var10000 = ModI18nNodes.WEAPON_MASTERY_POINTS_CHECK;
         default -> var10000 = null;
      }

      String id = var10000;
      return id == null ? Component.m_237119_() : Component.m_237110_(id, new Object[]{this.pointsNeeded});
   }
}
