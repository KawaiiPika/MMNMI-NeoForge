package xyz.pixelatedw.mineminenomi.data.conditions;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import xyz.pixelatedw.mineminenomi.init.ModLootTypes;

public class RandomChanceWithLuckCondition implements LootItemCondition {
   private final float probability;
   private final float perLuckBonus;

   public RandomChanceWithLuckCondition(float chance, float perLuckBonus) {
      this.probability = chance;
      this.perLuckBonus = perLuckBonus;
   }

   public LootItemConditionType m_7940_() {
      return (LootItemConditionType)ModLootTypes.RANDOM_CHANCE_WITH_LUCK.get();
   }

   public boolean test(LootContext ctx) {
      float luckBonus = ctx.m_78945_() * this.perLuckBonus;
      return ctx.m_230907_().m_188501_() < this.probability + luckBonus;
   }

   public static LootItemCondition.Builder randomChance(float chance) {
      return () -> new RandomChanceWithLuckCondition(chance, 0.05F);
   }

   public static LootItemCondition.Builder randomChance(float chance, float luckBonus) {
      return () -> new RandomChanceWithLuckCondition(chance, luckBonus);
   }

   public static class Serializer implements net.minecraft.world.level.storage.loot.Serializer<RandomChanceWithLuckCondition> {
      public void serialize(JsonObject json, RandomChanceWithLuckCondition cond, JsonSerializationContext ctx) {
         json.addProperty("chance", cond.probability);
         json.addProperty("luckBonus", cond.perLuckBonus);
      }

      public RandomChanceWithLuckCondition deserialize(JsonObject json, JsonDeserializationContext ctx) {
         return new RandomChanceWithLuckCondition(GsonHelper.m_13915_(json, "chance"), GsonHelper.m_13915_(json, "luckBonus"));
      }
   }
}
