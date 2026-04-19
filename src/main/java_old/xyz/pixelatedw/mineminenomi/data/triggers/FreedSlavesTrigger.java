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

public class FreedSlavesTrigger extends SimpleCriterionTrigger<Instance> {
   private static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath("mineminenomi", "freed_slaves");

   public ResourceLocation m_7295_() {
      return ID;
   }

   public Instance createInstance(JsonObject pJson, ContextAwarePredicate pEntityPredicate, DeserializationContext pConditionsParser) {
      MinMaxBounds.Ints amount = Ints.m_55373_(pJson.get("amount"));
      return new Instance(pEntityPredicate, amount);
   }

   public void trigger(ServerPlayer player, int amount) {
      this.m_66234_(player, (instance) -> instance.matches(player, amount));
   }

   public static class Instance extends AbstractCriterionTriggerInstance {
      private final MinMaxBounds.Ints predicate;

      public Instance(ContextAwarePredicate entityPredicate, MinMaxBounds.Ints predicate) {
         super(FreedSlavesTrigger.ID, entityPredicate);
         this.predicate = predicate;
      }

      public boolean matches(ServerPlayer player, int amount) {
         return this.predicate.m_55390_(amount);
      }

      public static Instance slavesFreed(int amount) {
         return new Instance(ContextAwarePredicate.f_285567_, Ints.m_55386_(amount));
      }

      public JsonObject m_7683_(SerializationContext pConditions) {
         JsonObject jsonobject = super.m_7683_(pConditions);
         jsonobject.add("amount", this.predicate.m_55328_());
         return jsonobject;
      }
   }
}
