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
import xyz.pixelatedw.mineminenomi.api.WyRegistry;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;

public class UnlockAbilityTrigger extends SimpleCriterionTrigger<Instance> {
   private static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath("mineminenomi", "unlock_ability");

   public ResourceLocation m_7295_() {
      return ID;
   }

   public Instance createInstance(JsonObject json, ContextAwarePredicate pEntityPredicate, DeserializationContext pConditionsParser) {
      AbilityCore<?> ability = (AbilityCore)((IForgeRegistry)WyRegistry.ABILITIES.get()).getValue(ResourceLocation.parse(GsonHelper.m_13906_(json, "ability")));
      return new Instance(pEntityPredicate, ability);
   }

   public void trigger(ServerPlayer player, AbilityCore<?> ability) {
      this.m_66234_(player, (instance) -> instance.matches(player, ability));
   }

   public static class Instance extends AbstractCriterionTriggerInstance {
      private AbilityCore<?> ability;

      public Instance(ContextAwarePredicate entityPredicate, AbilityCore<?> ability) {
         super(UnlockAbilityTrigger.ID, entityPredicate);
         this.ability = ability;
      }

      public boolean matches(ServerPlayer player, AbilityCore<?> ability) {
         return this.ability.equals(ability);
      }

      public static Instance ability(AbilityCore<?> core) {
         return new Instance(ContextAwarePredicate.f_285567_, core);
      }

      public JsonObject m_7683_(SerializationContext pConditions) {
         JsonObject jsonobject = super.m_7683_(pConditions);
         jsonobject.addProperty("ability", this.ability.getRegistryKey().toString());
         return jsonobject;
      }
   }
}
