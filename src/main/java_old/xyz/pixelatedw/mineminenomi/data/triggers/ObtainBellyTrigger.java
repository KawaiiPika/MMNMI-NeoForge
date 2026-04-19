package xyz.pixelatedw.mineminenomi.data.triggers;

import com.google.gson.JsonObject;
import net.minecraft.advancements.critereon.AbstractCriterionTriggerInstance;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.DeserializationContext;
import net.minecraft.advancements.critereon.SerializationContext;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.advancements.critereon.MinMaxBounds.Ints;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import xyz.pixelatedw.mineminenomi.data.triggers.criterion.BellyPredicate;

public class ObtainBellyTrigger extends SimpleCriterionTrigger<Instance> {
   private static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath("mineminenomi", "obtain_belly");

   public ResourceLocation m_7295_() {
      return ID;
   }

   public Instance createInstance(JsonObject json, ContextAwarePredicate pred, DeserializationContext ctx) {
      BellyPredicate belly = BellyPredicate.fromJson(json.get("belly"));
      return new Instance(pred, belly);
   }

   public void trigger(ServerPlayer player, int amount, boolean isBountyReward) {
      this.m_66234_(player, (instance) -> instance.matches(player, amount, isBountyReward));
   }

   public static class Instance extends AbstractCriterionTriggerInstance {
      private final BellyPredicate predicate;

      public Instance(ContextAwarePredicate entityPredicate, BellyPredicate predicate) {
         super(ObtainBellyTrigger.ID, entityPredicate);
         this.predicate = predicate;
      }

      public boolean matches(ServerPlayer player, int amount, boolean isBountyReward) {
         return this.predicate.matches(player, amount, isBountyReward);
      }

      public static Instance collectBelly(int belly) {
         return new Instance(ContextAwarePredicate.f_285567_, new BellyPredicate(Ints.m_55386_(belly), (Boolean)null));
      }

      public static Instance collectBelly(int belly, boolean fromBounty) {
         return new Instance(ContextAwarePredicate.f_285567_, new BellyPredicate(Ints.m_55386_(belly), fromBounty));
      }

      public JsonObject m_7683_(SerializationContext ctx) {
         JsonObject jsonobject = super.m_7683_(ctx);
         jsonobject.add("belly", this.predicate.serializeToJson());
         return jsonobject;
      }
   }
}
