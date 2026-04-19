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

public class FakeWeaponFunction extends LootItemConditionalFunction {
   protected FakeWeaponFunction(LootItemCondition[] conditionsIn) {
      super(conditionsIn);
   }

   protected ItemStack m_7372_(ItemStack stack, LootContext context) {
      stack.m_41784_().m_128379_("isClone", true);
      return stack;
   }

   public LootItemFunctionType m_7162_() {
      return (LootItemFunctionType)ModLootTypes.FAKE_WEAPON.get();
   }

   public static LootItemConditionalFunction.Builder<?> builder() {
      return m_80683_((condition) -> new FakeWeaponFunction(condition));
   }

   public static class Serializer extends LootItemConditionalFunction.Serializer<FakeWeaponFunction> {
      public void serialize(JsonObject object, FakeWeaponFunction functionClazz, JsonSerializationContext serializationContext) {
         super.m_6170_(object, functionClazz, serializationContext);
      }

      public FakeWeaponFunction deserialize(JsonObject object, JsonDeserializationContext deserializationContext, LootItemCondition[] conditionsIn) {
         return new FakeWeaponFunction(conditionsIn);
      }
   }
}
