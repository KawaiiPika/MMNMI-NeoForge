package xyz.pixelatedw.mineminenomi.data.triggers;

import com.google.gson.JsonObject;
import javax.annotation.Nullable;
import net.minecraft.advancements.critereon.AbstractCriterionTriggerInstance;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.DeserializationContext;
import net.minecraft.advancements.critereon.SerializationContext;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.GsonHelper;

public class ConsumeDevilFruitTrigger extends SimpleCriterionTrigger<Instance> {
   private static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath("mineminenomi", "consume_devil_fruit");

   public ResourceLocation m_7295_() {
      return ID;
   }

   public Instance createInstance(JsonObject json, ContextAwarePredicate entityPredicate, DeserializationContext parser) {
      ResourceLocation fruit = json.has("fruit") ? ResourceLocation.parse(GsonHelper.m_13906_(json, "fruit")) : null;
      return new Instance(entityPredicate, fruit);
   }

   public void trigger(ServerPlayer player, ResourceLocation fruit) {
      this.m_66234_(player, (inst) -> inst.matches(fruit));
   }

   public static class Instance extends AbstractCriterionTriggerInstance {
      @Nullable
      private final ResourceLocation fruit;

      public Instance(ContextAwarePredicate entityPredicate, @Nullable ResourceLocation fruit) {
         super(ConsumeDevilFruitTrigger.ID, entityPredicate);
         this.fruit = fruit;
      }

      public boolean matches(ResourceLocation fruit) {
         return this.fruit == null || this.fruit.equals(fruit);
      }

      public JsonObject m_7683_(SerializationContext pConditions) {
         JsonObject jsonobject = super.m_7683_(pConditions);
         if (this.fruit != null) {
            jsonobject.addProperty("fruit", this.fruit.toString());
         }

         return jsonobject;
      }
   }
}
