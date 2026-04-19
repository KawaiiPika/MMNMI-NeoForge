package xyz.pixelatedw.mineminenomi.data.triggers;

import com.google.gson.JsonObject;
import net.minecraft.advancements.critereon.AbstractCriterionTriggerInstance;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.DeserializationContext;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.advancements.critereon.SerializationContext;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.advancements.critereon.MinMaxBounds.Ints;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

public class ObtainLoyaltyTrigger extends SimpleCriterionTrigger<Instance> {
   private static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath("mineminenomi", "obtain_loyalty");

   public ResourceLocation m_7295_() {
      return ID;
   }

   public Instance createInstance(JsonObject json, ContextAwarePredicate pred, DeserializationContext ctx) {
      MinMaxBounds.Ints loyalty = Ints.m_55373_(json.get("loyalty"));
      return new Instance(pred, loyalty);
   }

   public void trigger(ServerPlayer player, int amount) {
      this.m_66234_(player, (instance) -> instance.matches(player, amount));
   }

   public static class Instance extends AbstractCriterionTriggerInstance {
      private final MinMaxBounds.Ints predicate;

      public Instance(ContextAwarePredicate entityPredicate, MinMaxBounds.Ints predicate) {
         super(ObtainLoyaltyTrigger.ID, entityPredicate);
         this.predicate = predicate;
      }

      public boolean matches(ServerPlayer player, int amount) {
         return this.predicate.m_55390_(amount);
      }

      public JsonObject m_7683_(SerializationContext ctx) {
         JsonObject jsonobject = super.m_7683_(ctx);
         jsonobject.add("loyalty", this.predicate.m_55328_());
         return jsonobject;
      }
   }
}
