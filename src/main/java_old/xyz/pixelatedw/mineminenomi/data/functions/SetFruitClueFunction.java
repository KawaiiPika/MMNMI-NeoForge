package xyz.pixelatedw.mineminenomi.data.functions;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Optional;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import xyz.pixelatedw.mineminenomi.api.DFEncyclopediaEntry;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.init.ModLootTypes;
import xyz.pixelatedw.mineminenomi.init.ModValues;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18n;
import xyz.pixelatedw.mineminenomi.items.AkumaNoMiItem;

public class SetFruitClueFunction extends LootItemConditionalFunction {
   protected SetFruitClueFunction(LootItemCondition[] conditionsIn) {
      super(conditionsIn);
   }

   protected ItemStack m_7372_(ItemStack stack, LootContext context) {
      AkumaNoMiItem fruit = (AkumaNoMiItem)WyHelper.shuffle(new ArrayList(ModValues.DEVIL_FRUITS)).stream().findFirst().orElse((Object)null);
      if (fruit != null) {
         DFEncyclopediaEntry template = fruit.getRandomElements(context.m_78952_());
         Optional<Integer> shape = Optional.empty();
         Optional<Color> baseColor = Optional.empty();
         Optional<Color> stemColor = Optional.empty();
         int maxRolls = 2;
         if (context.m_230907_().m_188503_(100) < 10) {
            maxRolls = 3;
         }

         int rolls = 1 + context.m_230907_().m_188503_(maxRolls);

         for(int i = 0; i < rolls; ++i) {
            int rand = context.m_230907_().m_188503_(3);
            if (rand == 0) {
               shape = template.getShape();
            } else if (rand == 1) {
               baseColor = template.getBaseColor();
            } else {
               stemColor = template.getStemColor();
            }
         }

         String key = fruit.getRegistryKey().toString();
         CompoundTag nbt = stack.m_41698_("fruitClues");
         nbt.m_128359_("fruitKey", key);
         if (shape.isPresent()) {
            nbt.m_128405_("baseShape", (Integer)shape.get());
         }

         if (baseColor.isPresent()) {
            nbt.m_128405_("baseColor", ((Color)baseColor.get()).getRGB());
         }

         if (stemColor.isPresent()) {
            nbt.m_128405_("stemColor", ((Color)stemColor.get()).getRGB());
         }

         stack.m_41714_(ModI18n.ITEM_FRUIT_CLUE);
         stack.m_41764_(1);
      }

      return stack;
   }

   public LootItemFunctionType m_7162_() {
      return (LootItemFunctionType)ModLootTypes.FRUIT_CLUE.get();
   }

   public static LootItemConditionalFunction.Builder<?> builder() {
      return m_80683_(SetFruitClueFunction::new);
   }

   public static class Serializer extends LootItemConditionalFunction.Serializer<SetFruitClueFunction> {
      public void serialize(JsonObject object, SetFruitClueFunction functionClazz, JsonSerializationContext serializationContext) {
         super.m_6170_(object, functionClazz, serializationContext);
      }

      public SetFruitClueFunction deserialize(JsonObject object, JsonDeserializationContext deserializationContext, LootItemCondition[] conditionsIn) {
         return new SetFruitClueFunction(conditionsIn);
      }
   }
}
