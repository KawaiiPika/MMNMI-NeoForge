package xyz.pixelatedw.mineminenomi.data.functions;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.DyeableLeatherItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import xyz.pixelatedw.mineminenomi.api.WyDebug;
import xyz.pixelatedw.mineminenomi.api.items.IMultiChannelColorItem;
import xyz.pixelatedw.mineminenomi.init.ModLootTypes;

public class SetItemColorFunction extends LootItemConditionalFunction {
   private int color;

   protected SetItemColorFunction(LootItemCondition[] conditions, int color) {
      super(conditions);
      this.color = color;
   }

   public ItemStack m_7372_(ItemStack stack, LootContext context) {
      Item var5 = stack.m_41720_();
      if (var5 instanceof DyeableLeatherItem item) {
         item.m_41115_(stack, this.color);
      } else {
         var5 = stack.m_41720_();
         if (var5 instanceof IMultiChannelColorItem item) {
            item.setLayerColor(stack, 0, this.color);
         } else {
            WyDebug.error("Item cannot be colored because it doesn't implement any known color interface!");
         }
      }

      return stack;
   }

   public LootItemFunctionType m_7162_() {
      return (LootItemFunctionType)ModLootTypes.SET_ITEM_COLOR.get();
   }

   public static LootItemConditionalFunction.Builder<?> builder(int color) {
      return m_80683_((condition) -> new SetItemColorFunction(condition, color));
   }

   public static class Serializer extends LootItemConditionalFunction.Serializer<SetItemColorFunction> {
      public void serialize(JsonObject object, SetItemColorFunction functionClazz, JsonSerializationContext serializationContext) {
         super.m_6170_(object, functionClazz, serializationContext);
         object.add("color", serializationContext.serialize(functionClazz.color));
      }

      public SetItemColorFunction deserialize(JsonObject object, JsonDeserializationContext deserializationContext, LootItemCondition[] conditions) {
         int color = GsonHelper.m_13927_(object, "color");
         return new SetItemColorFunction(conditions, color);
      }
   }
}
