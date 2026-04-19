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

public class SelectRaceTrigger extends SimpleCriterionTrigger<Instance> {
   private static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath("mineminenomi", "select_race");

   public ResourceLocation m_7295_() {
      return ID;
   }

   public Instance createInstance(JsonObject json, ContextAwarePredicate entityPredicate, DeserializationContext parser) {
      ResourceLocation race = ResourceLocation.parse(GsonHelper.m_13906_(json, "race"));
      return new Instance(entityPredicate, race);
   }

   public void trigger(ServerPlayer player, ResourceLocation race) {
      this.m_66234_(player, (inst) -> inst.matches(race));
   }

   public static class Instance extends AbstractCriterionTriggerInstance {
      private final ResourceLocation race;

      public Instance(ContextAwarePredicate entityPredicate, ResourceLocation race) {
         super(SelectRaceTrigger.ID, entityPredicate);
         this.race = race;
      }

      public boolean matches(ResourceLocation race) {
         return this.race.equals(race);
      }

      public JsonObject m_7683_(SerializationContext conditions) {
         JsonObject jsonobject = super.m_7683_(conditions);
         jsonobject.addProperty("race", this.race.toString());
         return jsonobject;
      }
   }
}
