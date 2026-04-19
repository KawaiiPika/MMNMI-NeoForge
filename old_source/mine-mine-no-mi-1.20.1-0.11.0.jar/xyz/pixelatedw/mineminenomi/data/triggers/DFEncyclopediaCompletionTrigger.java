package xyz.pixelatedw.mineminenomi.data.triggers;

import com.google.gson.JsonObject;
import net.minecraft.advancements.critereon.AbstractCriterionTriggerInstance;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.DeserializationContext;
import net.minecraft.advancements.critereon.SerializationContext;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import xyz.pixelatedw.mineminenomi.items.DFEncyclopediaItem;

public class DFEncyclopediaCompletionTrigger extends SimpleCriterionTrigger<Instance> {
   private static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath("mineminenomi", "encyclopedia_completion");

   public ResourceLocation m_7295_() {
      return ID;
   }

   public Instance createInstance(JsonObject json, ContextAwarePredicate entityPredicate, DeserializationContext parser) {
      float completion = GsonHelper.m_13915_(json, "completion");
      return new Instance(entityPredicate, completion);
   }

   public void trigger(ServerPlayer player, ItemStack stack) {
      this.m_66234_(player, (instance) -> instance.matches(player, stack));
   }

   public static class Instance extends AbstractCriterionTriggerInstance {
      private float completion;

      public Instance(ContextAwarePredicate entityPredicate, float completion) {
         super(DFEncyclopediaCompletionTrigger.ID, entityPredicate);
         this.completion = completion;
      }

      public boolean matches(ServerPlayer player, ItemStack stack) {
         return DFEncyclopediaItem.getCompletion(stack) >= this.completion;
      }

      public static Instance completion(float completion) {
         return new Instance(ContextAwarePredicate.f_285567_, completion);
      }

      public JsonObject m_7683_(SerializationContext pConditions) {
         JsonObject jsonobject = super.m_7683_(pConditions);
         jsonobject.addProperty("completion", this.completion);
         return jsonobject;
      }
   }
}
