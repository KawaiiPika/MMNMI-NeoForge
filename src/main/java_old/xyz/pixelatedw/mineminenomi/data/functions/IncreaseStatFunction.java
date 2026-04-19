package xyz.pixelatedw.mineminenomi.data.functions;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.challenges.Challenge;
import xyz.pixelatedw.mineminenomi.api.enums.StatChangeSource;

public abstract class IncreaseStatFunction extends LootItemConditionalFunction {
   protected NumberProvider amount;
   protected StatChangeSource source;
   protected boolean scaleDownChallengeCompletion = false;

   protected IncreaseStatFunction(LootItemCondition[] conditions, NumberProvider amount, StatChangeSource source) {
      super(conditions);
      this.amount = amount;
      this.source = source;
   }

   public NumberProvider getAmount() {
      return this.amount;
   }

   public StatChangeSource getSource() {
      return this.source;
   }

   public boolean scalesDownChallengeCompletion() {
      return this.scaleDownChallengeCompletion;
   }

   public int scaleValueFromCompletions(Challenge challenge, int amount) {
      int completions = challenge.getCompletions();
      completions = Math.min(completions, 10);
      if (completions > 0) {
         float d = (float)completions * 1.5F;
         amount = (int)((float)amount / d);
      }

      amount = Math.max(amount, 1);
      if (amount % 10 != 0 && amount > 10) {
         amount = WyHelper.roundToNiceNumber((float)amount);
      }

      return amount;
   }

   public static class Serializer<F extends IncreaseStatFunction> extends LootItemConditionalFunction.Serializer<F> {
      private IFunctionFactory<F> factory;

      public Serializer(IFunctionFactory<F> factory) {
         this.factory = factory;
      }

      public void serialize(JsonObject object, F func, JsonSerializationContext context) {
         super.m_6170_(object, func, context);
         object.add("amount", context.serialize(func.getAmount()));
         object.add("source", context.serialize(func.getSource()));
         object.add("scaleDownChallengeCompletion", context.serialize(func.scalesDownChallengeCompletion()));
      }

      public F deserialize(JsonObject object, JsonDeserializationContext context, LootItemCondition[] cond) {
         NumberProvider amount = (NumberProvider)GsonHelper.m_13836_(object, "amount", context, NumberProvider.class);
         StatChangeSource source = (StatChangeSource)context.deserialize(object.get("source"), StatChangeSource.class);
         F func = this.factory.create(cond, amount, source);
         if (object.has("scaleDownChallengeCompletion")) {
            func.scaleDownChallengeCompletion = (Boolean)context.deserialize(object.get("scaleDownChallengeCompletion"), Boolean.class);
         } else {
            func.scaleDownChallengeCompletion = false;
         }

         return func;
      }
   }

   @FunctionalInterface
   public interface IFunctionFactory<F extends IncreaseStatFunction> {
      F create(LootItemCondition[] var1, NumberProvider var2, StatChangeSource var3);
   }
}
