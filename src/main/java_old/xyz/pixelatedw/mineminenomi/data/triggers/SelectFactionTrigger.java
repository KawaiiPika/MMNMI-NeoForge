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

public class SelectFactionTrigger extends SimpleCriterionTrigger<Instance> {
   private static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath("mineminenomi", "select_faction");

   public ResourceLocation m_7295_() {
      return ID;
   }

   public Instance createInstance(JsonObject json, ContextAwarePredicate entityPredicate, DeserializationContext parser) {
      ResourceLocation faction = ResourceLocation.parse(GsonHelper.m_13906_(json, "faction"));
      return new Instance(entityPredicate, faction);
   }

   public void trigger(ServerPlayer player, ResourceLocation faction) {
      this.m_66234_(player, (inst) -> inst.matches(faction));
   }

   public static class Instance extends AbstractCriterionTriggerInstance {
      private final ResourceLocation faction;

      public Instance(ContextAwarePredicate entityPredicate, ResourceLocation faction) {
         super(SelectFactionTrigger.ID, entityPredicate);
         this.faction = faction;
      }

      public boolean matches(ResourceLocation faction) {
         return this.faction.equals(faction);
      }

      public JsonObject m_7683_(SerializationContext conditions) {
         JsonObject jsonobject = super.m_7683_(conditions);
         jsonobject.addProperty("faction", this.faction.toString());
         return jsonobject;
      }
   }
}
