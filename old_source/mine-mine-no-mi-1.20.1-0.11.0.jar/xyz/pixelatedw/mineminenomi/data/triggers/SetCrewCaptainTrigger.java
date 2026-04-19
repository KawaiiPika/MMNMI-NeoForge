package xyz.pixelatedw.mineminenomi.data.triggers;

import com.google.gson.JsonObject;
import net.minecraft.advancements.critereon.AbstractCriterionTriggerInstance;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.DeserializationContext;
import net.minecraft.advancements.critereon.SerializationContext;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

public class SetCrewCaptainTrigger extends SimpleCriterionTrigger<Instance> {
   private static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath("mineminenomi", "set_crew_captain");

   public ResourceLocation m_7295_() {
      return ID;
   }

   public Instance createInstance(JsonObject json, ContextAwarePredicate entityPredicate, DeserializationContext parser) {
      return new Instance(entityPredicate);
   }

   public void trigger(ServerPlayer player) {
      this.m_66234_(player, (inst) -> true);
   }

   public static class Instance extends AbstractCriterionTriggerInstance {
      public Instance(ContextAwarePredicate entityPredicate) {
         super(SetCrewCaptainTrigger.ID, entityPredicate);
      }

      public JsonObject m_7683_(SerializationContext pConditions) {
         JsonObject jsonobject = super.m_7683_(pConditions);
         return jsonobject;
      }
   }
}
