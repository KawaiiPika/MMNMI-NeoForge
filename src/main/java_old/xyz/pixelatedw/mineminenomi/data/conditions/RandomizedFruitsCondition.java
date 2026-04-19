package xyz.pixelatedw.mineminenomi.data.conditions;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import xyz.pixelatedw.mineminenomi.config.ServerConfig;
import xyz.pixelatedw.mineminenomi.init.ModLootTypes;

public class RandomizedFruitsCondition implements LootItemCondition {
   private static final RandomizedFruitsCondition INSTANCE = new RandomizedFruitsCondition();

   public static LootItemCondition.Builder builder() {
      return () -> INSTANCE;
   }

   public boolean test(LootContext context) {
      return ServerConfig.getRandomizedFruits();
   }

   public LootItemConditionType m_7940_() {
      return (LootItemConditionType)ModLootTypes.RANDOMIZED_FRUIT.get();
   }

   public static class Serializer implements net.minecraft.world.level.storage.loot.Serializer<RandomizedFruitsCondition> {
      public void serialize(JsonObject pJson, RandomizedFruitsCondition pValue, JsonSerializationContext pSerializationContext) {
      }

      public RandomizedFruitsCondition deserialize(JsonObject pJson, JsonDeserializationContext pSerializationContext) {
         return RandomizedFruitsCondition.INSTANCE;
      }
   }
}
