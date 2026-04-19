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
import net.minecraftforge.registries.IForgeRegistry;
import org.jetbrains.annotations.Nullable;
import xyz.pixelatedw.mineminenomi.api.WyRegistry;
import xyz.pixelatedw.mineminenomi.api.challenges.ChallengeCore;

public class CompleteChallengeTrigger extends SimpleCriterionTrigger<Instance> {
   private static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath("mineminenomi", "complete_challenge");

   public ResourceLocation m_7295_() {
      return ID;
   }

   public Instance createInstance(JsonObject json, ContextAwarePredicate pred, DeserializationContext ctx) {
      ChallengeCore<?> challenge = (ChallengeCore)((IForgeRegistry)WyRegistry.CHALLENGES.get()).getValue(ResourceLocation.parse(GsonHelper.m_13906_(json, "challenge")));
      return new Instance(pred, challenge);
   }

   public void trigger(ServerPlayer player, ChallengeCore<?> challenge) {
      this.m_66234_(player, (instance) -> instance.matches(player, challenge));
   }

   public static class Instance extends AbstractCriterionTriggerInstance {
      private ChallengeCore<?> challenge;

      public Instance(ContextAwarePredicate entityPredicate, ChallengeCore<?> challenge) {
         super(CompleteChallengeTrigger.ID, entityPredicate);
         this.challenge = challenge;
      }

      public static Instance completeChallenge(@Nullable ChallengeCore<?> challenge) {
         return new Instance(ContextAwarePredicate.f_285567_, challenge);
      }

      public boolean matches(ServerPlayer player, ChallengeCore<?> challenge) {
         return this.challenge.equals(challenge);
      }

      public JsonObject m_7683_(SerializationContext pConditions) {
         JsonObject jsonobject = super.m_7683_(pConditions);
         jsonobject.addProperty("challenge", this.challenge.getRegistryKey().toString());
         return jsonobject;
      }
   }
}
