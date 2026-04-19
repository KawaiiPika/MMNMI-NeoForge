package xyz.pixelatedw.mineminenomi.api.challenges;

import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import xyz.pixelatedw.mineminenomi.api.entities.IPhasesEntity;
import xyz.pixelatedw.mineminenomi.api.entities.ai.NPCPhaseManager;
import xyz.pixelatedw.mineminenomi.api.entities.revenge.IRevengeEntity;
import xyz.pixelatedw.mineminenomi.init.ModAttributes;

public interface IChallengeBoss extends IRevengeEntity, IPhasesEntity {
   UUID GROUP_SCALE_HP_UUID = UUID.fromString("a66a0da1-286f-4772-9684-1b30810152f5");
   UUID GROUP_SCALE_TOUGHNESS_UUID = UUID.fromString("64c28e45-7adf-41b5-8d64-933ca6b8288b");
   UUID GROUP_SCALE_ATTACK_UUID = UUID.fromString("378cc4c7-d7a2-47f8-8b94-123ef5229196");
   UUID GROUP_SCALE_GCD_UUID = UUID.fromString("94233c8f-9ca6-427b-a2d1-9745df50c4ba");

   ChallengeInfo getChallengeInfo();

   default void applyDifficultyModifiers(LivingEntity entity) {
      float scale = this.getChallengeInfo().getScaling();
      if (scale > 0.0F) {
         AttributeInstance hpAttr = entity.m_21051_(Attributes.f_22276_);
         if (hpAttr != null) {
            hpAttr.m_22125_(new AttributeModifier(GROUP_SCALE_HP_UUID, "Group Scaling Health Modifier", this.getHealthScalingModifier(scale), Operation.MULTIPLY_BASE));
         }

         AttributeInstance toughnessAttr = entity.m_21051_((Attribute)ModAttributes.TOUGHNESS.get());
         if (toughnessAttr != null) {
            toughnessAttr.m_22125_(new AttributeModifier(GROUP_SCALE_TOUGHNESS_UUID, "Group Scaling Toughness Modifier", this.getToughnessScalingModifier(scale), Operation.ADDITION));
         }

         AttributeInstance attackAttr = entity.m_21051_(Attributes.f_22281_);
         if (attackAttr != null) {
            attackAttr.m_22125_(new AttributeModifier(GROUP_SCALE_ATTACK_UUID, "Group Scaling Health Modifier", this.getAttackScalingModifier(scale), Operation.ADDITION));
         }

         AttributeInstance gcdAttr = entity.m_21051_((Attribute)ModAttributes.GCD.get());
         if (gcdAttr != null) {
            gcdAttr.m_22125_(new AttributeModifier(GROUP_SCALE_GCD_UUID, "Group Scaling GCD Modifier", this.getGCDScalingModifier(scale), Operation.ADDITION));
         }
      }

   }

   @Nullable
   default NPCPhaseManager getPhaseManager() {
      return null;
   }

   default boolean isDifficultyStandard() {
      return this.getChallengeInfo().isDifficultyStandard();
   }

   default boolean isDifficultyHard() {
      return this.getChallengeInfo().isDifficultyHard();
   }

   default boolean isDifficultyHardOrAbove() {
      return this.getChallengeInfo().getDifficulty().ordinal() >= ChallengeDifficulty.HARD.ordinal();
   }

   default boolean isDifficultyUltimate() {
      return this.getChallengeInfo().isDifficultyUltimate();
   }

   default double getHealthScalingModifier(float scale) {
      return (double)scale;
   }

   default double getToughnessScalingModifier(float scale) {
      return (double)(4.0F * scale);
   }

   default double getAttackScalingModifier(float scale) {
      return (double)(3.0F * scale);
   }

   default double getGCDScalingModifier(float scale) {
      return (double)(-(15.0F * scale));
   }
}
