package xyz.pixelatedw.mineminenomi.data.functions;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Optional;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import xyz.pixelatedw.mineminenomi.api.DFEncyclopediaEntry;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.init.ModLootTypes;
import xyz.pixelatedw.mineminenomi.init.ModValues;
import xyz.pixelatedw.mineminenomi.items.AkumaNoMiItem;
import xyz.pixelatedw.mineminenomi.items.DFEncyclopediaItem;

public class EncyclopediaCompletionFunction extends LootItemConditionalFunction {
   protected EncyclopediaCompletionFunction(LootItemCondition[] conditionsIn) {
      super(conditionsIn);
   }

   protected ItemStack m_7372_(ItemStack stack, LootContext context) {
      int attempts = context.m_230907_().m_188503_(5) + 1;

      for(int i = 0; i < attempts; ++i) {
         AkumaNoMiItem fruit = (AkumaNoMiItem)WyHelper.shuffle(new ArrayList(ModValues.DEVIL_FRUITS)).stream().findFirst().orElse((Object)null);
         if (fruit != null) {
            DFEncyclopediaEntry template = fruit.getRandomElements(context.m_78952_());
            Optional<Integer> shape = Optional.empty();
            Optional<Color> baseColor = Optional.empty();
            Optional<Color> stemColor = Optional.empty();
            int rolls = context.m_230907_().m_188503_(2);
            if (context.m_230907_().m_188503_(128) == 0) {
               rolls = 3;
            }

            if (rolls < 3) {
               for(int j = 0; j < rolls; ++j) {
                  int rand = context.m_230907_().m_188503_(3);
                  if (rand == 0) {
                     shape = template.getShape();
                  } else if (rand == 1) {
                     baseColor = template.getBaseColor();
                  } else {
                     stemColor = template.getStemColor();
                  }
               }
            } else {
               shape = template.getShape();
               baseColor = template.getBaseColor();
               stemColor = template.getStemColor();
            }

            if (shape.isPresent() || baseColor.isPresent() || stemColor.isPresent()) {
               ResourceLocation key = fruit.getRegistryKey();
               DFEncyclopediaEntry clue = DFEncyclopediaEntry.of(shape, baseColor, stemColor);
               DFEncyclopediaItem.addFruitClues(stack, key, clue);
               stack.m_41764_(1);
            }
         }
      }

      return stack;
   }

   public LootItemFunctionType m_7162_() {
      return (LootItemFunctionType)ModLootTypes.ENCYCLOPEDIA_COMPLETION.get();
   }

   public static LootItemConditionalFunction.Builder<?> builder() {
      return m_80683_((condition) -> new EncyclopediaCompletionFunction(condition));
   }

   public static class Serializer extends LootItemConditionalFunction.Serializer<EncyclopediaCompletionFunction> {
      public void serialize(JsonObject object, EncyclopediaCompletionFunction functionClazz, JsonSerializationContext serializationContext) {
         super.m_6170_(object, functionClazz, serializationContext);
      }

      public EncyclopediaCompletionFunction deserialize(JsonObject object, JsonDeserializationContext deserializationContext, LootItemCondition[] conditionsIn) {
         return new EncyclopediaCompletionFunction(conditionsIn);
      }
   }
}
