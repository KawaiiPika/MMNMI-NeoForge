package xyz.pixelatedw.mineminenomi.api.challenges;

import com.google.common.collect.ImmutableList;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.world.entity.LivingEntity;

public class ChallengeInfo {
   private static final ImmutableList<LivingEntity> EMPTY_GROUP = ImmutableList.of();
   @Nullable
   private InProgressChallenge challengeData;

   public ChallengeDifficulty getDifficulty() {
      return this.challengeData == null ? ChallengeDifficulty.STANDARD : this.challengeData.getCore().getDifficulty();
   }

   public List<LivingEntity> getChallengerGroup() {
      return (List<LivingEntity>)(this.challengeData == null ? EMPTY_GROUP : this.challengeData.getGroup());
   }

   public boolean isDifficultyStandard() {
      return this.getDifficulty() == ChallengeDifficulty.STANDARD;
   }

   public boolean isDifficultyHard() {
      return this.getDifficulty() == ChallengeDifficulty.HARD;
   }

   public boolean isDifficultyUltimate() {
      return this.getDifficulty() == ChallengeDifficulty.ULTIMATE;
   }

   public float getScaling() {
      float scale = (float)((this.getChallengerGroup().size() - 1) * 50 + this.getDifficulty().ordinal() * 75);
      scale = Math.min(scale, 300.0F);
      return scale / 300.0F;
   }

   public void setInProgressChallenge(@Nullable InProgressChallenge data) {
      this.challengeData = data;
   }

   @Nullable
   public InProgressChallenge getInProgressChallengeData() {
      return this.challengeData;
   }
}
