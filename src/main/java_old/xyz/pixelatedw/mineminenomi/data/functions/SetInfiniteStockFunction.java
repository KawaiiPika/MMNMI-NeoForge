package xyz.pixelatedw.mineminenomi.data.functions;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import xyz.pixelatedw.mineminenomi.init.ModLootTypes;

public class SetInfiniteStockFunction extends LootItemConditionalFunction {
   protected SetInfiniteStockFunction(LootItemCondition[] conditionsIn) {
      super(conditionsIn);
   }

   protected ItemStack m_7372_(ItemStack stack, LootContext context) {
      stack.m_41784_().m_128379_("isInfinite", true);
      stack.m_41764_(1);
      return stack;
   }

   public LootItemFunctionType m_7162_() {
      return (LootItemFunctionType)ModLootTypes.INFINITE_STOCK.get();
   }

   public static LootItemConditionalFunction.Builder<?> builder() {
      return m_80683_((condition) -> new SetInfiniteStockFunction(condition));
   }

   public static class Serializer extends LootItemConditionalFunction.Serializer<SetInfiniteStockFunction> {
      public void serialize(JsonObject object, SetInfiniteStockFunction functionClazz, JsonSerializationContext serializationContext) {
         super.m_6170_(object, functionClazz, serializationContext);
      }

      public SetInfiniteStockFunction deserialize(JsonObject object, JsonDeserializationContext deserializationContext, LootItemCondition[] conditionsIn) {
         return new SetInfiniteStockFunction(conditionsIn);
      }
   }
}
