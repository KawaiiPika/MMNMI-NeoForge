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

public class SelectStyleTrigger extends SimpleCriterionTrigger<Instance> {
   private static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath("mineminenomi", "select_style");

   public ResourceLocation m_7295_() {
      return ID;
   }

   public Instance createInstance(JsonObject json, ContextAwarePredicate entityPredicate, DeserializationContext parser) {
      ResourceLocation style = ResourceLocation.parse(GsonHelper.m_13906_(json, "style"));
      return new Instance(entityPredicate, style);
   }

   public void trigger(ServerPlayer player, ResourceLocation style) {
      this.m_66234_(player, (inst) -> inst.matches(style));
   }

   public static class Instance extends AbstractCriterionTriggerInstance {
      private final ResourceLocation style;

      public Instance(ContextAwarePredicate entityPredicate, ResourceLocation style) {
         super(SelectStyleTrigger.ID, entityPredicate);
         this.style = style;
      }

      public boolean matches(ResourceLocation style) {
         return this.style.equals(style);
      }

      public JsonObject m_7683_(SerializationContext conditions) {
         JsonObject jsonobject = super.m_7683_(conditions);
         jsonobject.addProperty("style", this.style.toString());
         return jsonobject;
      }
   }
}
