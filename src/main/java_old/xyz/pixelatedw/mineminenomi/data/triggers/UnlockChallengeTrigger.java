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

public class UnlockChallengeTrigger extends SimpleCriterionTrigger<Instance> {
   private static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath("mineminenomi", "unlock_challenge");

   public ResourceLocation m_7295_() {
      return ID;
   }

   public Instance createInstance(JsonObject json, ContextAwarePredicate pEntityPredicate, DeserializationContext pConditionsParser) {
      ChallengeCore<?> challenge = null;
      if (json.has("challenge")) {
         challenge = (ChallengeCore)((IForgeRegistry)WyRegistry.CHALLENGES.get()).getValue(ResourceLocation.parse(GsonHelper.m_13906_(json, "challenge")));
      }

      return new Instance(pEntityPredicate, challenge);
   }

   public void trigger(ServerPlayer player, ChallengeCore<?> challenge) {
      this.m_66234_(player, (instance) -> instance.matches(player, challenge));
   }

   public static class Instance extends AbstractCriterionTriggerInstance {
      private @Nullable ChallengeCore<?> challenge;

      public Instance(ContextAwarePredicate entityPredicate, @Nullable ChallengeCore<?> challenge) {
         super(UnlockChallengeTrigger.ID, entityPredicate);
         this.challenge = challenge;
      }

      public static Instance unlockAnyChallenge() {
         return new Instance(ContextAwarePredicate.f_285567_, (ChallengeCore)null);
      }

      public static Instance unlockChallenge(@Nullable ChallengeCore<?> challenge) {
         return new Instance(ContextAwarePredicate.f_285567_, challenge);
      }

      public boolean matches(ServerPlayer player, ChallengeCore<?> challenge) {
         return this.challenge == null || this.challenge.equals(challenge);
      }

      public JsonObject m_7683_(SerializationContext pConditions) {
         JsonObject jsonobject = super.m_7683_(pConditions);
         if (this.challenge != null) {
            jsonobject.addProperty("challenge", this.challenge.getRegistryKey().toString());
         }

         return jsonobject;
      }
   }
}
