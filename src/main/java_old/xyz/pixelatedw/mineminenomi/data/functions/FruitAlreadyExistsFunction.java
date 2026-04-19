package xyz.pixelatedw.mineminenomi.data.functions;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.registries.ForgeRegistries;
import xyz.pixelatedw.mineminenomi.config.ServerConfig;
import xyz.pixelatedw.mineminenomi.data.world.OneFruitWorldData;
import xyz.pixelatedw.mineminenomi.init.ModLootTypes;

public class FruitAlreadyExistsFunction extends LootItemConditionalFunction {
   protected FruitAlreadyExistsFunction(LootItemCondition[] conditionsIn) {
      super(conditionsIn);
   }

   protected ItemStack m_7372_(ItemStack stack, LootContext context) {
      if (!ServerConfig.hasOneFruitPerWorldSimpleLogic()) {
         return stack;
      } else if (stack.m_41619_()) {
         return stack;
      } else {
         OneFruitWorldData worldData = OneFruitWorldData.get();
         ResourceLocation key = ForgeRegistries.ITEMS.getKey(stack.m_41720_());
         return worldData.isFruitAvailable(key) ? stack : new ItemStack(Items.f_41852_);
      }
   }

   public LootItemFunctionType m_7162_() {
      return (LootItemFunctionType)ModLootTypes.FRUIT_ALREADY_EXISTS.get();
   }

   public static LootItemConditionalFunction.Builder<?> builder() {
      return m_80683_((condition) -> new FruitAlreadyExistsFunction(condition));
   }

   public static class Serializer extends LootItemConditionalFunction.Serializer<FruitAlreadyExistsFunction> {
      public void serialize(JsonObject object, FruitAlreadyExistsFunction functionClazz, JsonSerializationContext serializationContext) {
         super.m_6170_(object, functionClazz, serializationContext);
      }

      public FruitAlreadyExistsFunction deserialize(JsonObject object, JsonDeserializationContext deserializationContext, LootItemCondition[] conditionsIn) {
         return new FruitAlreadyExistsFunction(conditionsIn);
      }
   }
}
