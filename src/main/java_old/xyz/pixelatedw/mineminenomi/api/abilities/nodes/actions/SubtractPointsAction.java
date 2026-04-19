package xyz.pixelatedw.mineminenomi.api.abilities.nodes.actions;

import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.enums.TrainingPointType;
import xyz.pixelatedw.mineminenomi.data.entity.stats.EntityStatsCapability;

public class SubtractPointsAction implements NodeUnlockAction {
   private final TrainingPointType type;
   private final int amount;

   public static SubtractPointsAction marksmanship(int points) {
      return new SubtractPointsAction(TrainingPointType.MARKSMANSHIP, points);
   }

   public static SubtractPointsAction martialArts(int points) {
      return new SubtractPointsAction(TrainingPointType.MARTIAL_ARTS, points);
   }

   public static SubtractPointsAction technology(int points) {
      return new SubtractPointsAction(TrainingPointType.TECHNOLOGY, points);
   }

   public static SubtractPointsAction weaponMastery(int points) {
      return new SubtractPointsAction(TrainingPointType.WEAPON_MASTERY, points);
   }

   public SubtractPointsAction(TrainingPointType type, int amount) {
      this.type = type;
      this.amount = amount;
   }

   public void onUnlock(LivingEntity entity) {
      EntityStatsCapability.getLazy(entity).ifPresent((props) -> props.alterTrainingPoints(this.type, -this.amount));
   }

   public void onLock(LivingEntity entity) {
      EntityStatsCapability.getLazy(entity).ifPresent((props) -> props.alterTrainingPoints(this.type, this.amount));
   }
}
