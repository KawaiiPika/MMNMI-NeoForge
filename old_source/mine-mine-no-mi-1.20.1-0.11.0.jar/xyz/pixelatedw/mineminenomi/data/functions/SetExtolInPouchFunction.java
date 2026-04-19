package xyz.pixelatedw.mineminenomi.data.functions;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;
import xyz.pixelatedw.mineminenomi.init.ModLootTypes;

public class SetExtolInPouchFunction extends LootItemConditionalFunction {
   private NumberProvider range;

   protected SetExtolInPouchFunction(LootItemCondition[] conditionsIn, NumberProvider rang) {
      super(conditionsIn);
      this.range = rang;
   }

   protected ItemStack m_7372_(ItemStack stack, LootContext context) {
      stack.m_41784_().m_128405_("extol", this.range.m_142683_(context));
      return stack;
   }

   public LootItemFunctionType m_7162_() {
      return (LootItemFunctionType)ModLootTypes.SET_EXTOL_IN_POUCH.get();
   }

   public static LootItemConditionalFunction.Builder<?> builder(NumberProvider range) {
      return m_80683_((condition) -> new SetExtolInPouchFunction(condition, range));
   }

   public static class Serializer extends LootItemConditionalFunction.Serializer<SetExtolInPouchFunction> {
      public void serialize(JsonObject object, SetExtolInPouchFunction functionClazz, JsonSerializationContext serializationContext) {
         super.m_6170_(object, functionClazz, serializationContext);
         object.add("extol_range", serializationContext.serialize(functionClazz.range));
      }

      public SetExtolInPouchFunction deserialize(JsonObject object, JsonDeserializationContext deserializationContext, LootItemCondition[] conditionsIn) {
         NumberProvider range = (NumberProvider)GsonHelper.m_13836_(object, "extol_range", deserializationContext, NumberProvider.class);
         return new SetExtolInPouchFunction(conditionsIn, range);
      }
   }
}
