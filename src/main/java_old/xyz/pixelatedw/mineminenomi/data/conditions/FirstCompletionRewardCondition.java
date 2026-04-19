package xyz.pixelatedw.mineminenomi.data.conditions;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import xyz.pixelatedw.mineminenomi.api.challenges.ChallengeCore;
import xyz.pixelatedw.mineminenomi.data.entity.challenge.ChallengeCapability;
import xyz.pixelatedw.mineminenomi.init.ModLootTypes;

public class FirstCompletionRewardCondition implements LootItemCondition {
   private static final FirstCompletionRewardCondition INSTANCE = new FirstCompletionRewardCondition();

   public static LootItemCondition.Builder builder() {
      return () -> INSTANCE;
   }

   public boolean test(LootContext context) {
      Entity entity = (Entity)context.m_78953_(LootContextParams.f_81455_);
      ChallengeCore<?> challenge = (ChallengeCore)context.m_78953_(ModLootTypes.COMPLETED_CHALLENGE);
      if (entity != null && challenge != null && entity instanceof Player player) {
         boolean isChallengeCompleted = (Boolean)ChallengeCapability.get(player).map((data) -> data.isChallengeCompleted(challenge)).orElse(false);
         if (!isChallengeCompleted) {
            return true;
         }
      }

      return false;
   }

   public LootItemConditionType m_7940_() {
      return (LootItemConditionType)ModLootTypes.FIRST_COMPLETION.get();
   }

   public static class Serializer implements net.minecraft.world.level.storage.loot.Serializer<FirstCompletionRewardCondition> {
      public void serialize(JsonObject pJson, FirstCompletionRewardCondition pValue, JsonSerializationContext pSerializationContext) {
      }

      public FirstCompletionRewardCondition deserialize(JsonObject pJson, JsonDeserializationContext pSerializationContext) {
         return FirstCompletionRewardCondition.INSTANCE;
      }
   }
}
