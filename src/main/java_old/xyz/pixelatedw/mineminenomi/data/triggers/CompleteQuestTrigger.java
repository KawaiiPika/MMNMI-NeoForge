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
import net.minecraftforge.registries.IForgeRegistry;
import xyz.pixelatedw.mineminenomi.api.WyRegistry;
import xyz.pixelatedw.mineminenomi.api.quests.QuestId;

public class CompleteQuestTrigger extends SimpleCriterionTrigger<Instance> {
   private static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath("mineminenomi", "complete_quest");

   public ResourceLocation m_7295_() {
      return ID;
   }

   public Instance createInstance(JsonObject json, ContextAwarePredicate pEntityPredicate, DeserializationContext pConditionsParser) {
      QuestId<?> challenge = (QuestId)((IForgeRegistry)WyRegistry.QUESTS.get()).getValue(ResourceLocation.parse(GsonHelper.m_13906_(json, "quest")));
      return new Instance(pEntityPredicate, challenge);
   }

   public void trigger(ServerPlayer player, QuestId<?> quest) {
      this.m_66234_(player, (instance) -> instance.matches(player, quest));
   }

   public static class Instance extends AbstractCriterionTriggerInstance {
      private QuestId<?> quest;

      public Instance(ContextAwarePredicate entityPredicate, QuestId<?> quest) {
         super(CompleteQuestTrigger.ID, entityPredicate);
         this.quest = quest;
      }

      public static Instance completeChallenge(@Nullable QuestId<?> challenge) {
         return new Instance(ContextAwarePredicate.f_285567_, challenge);
      }

      public boolean matches(ServerPlayer player, QuestId<?> challenge) {
         return this.quest.equals(challenge);
      }

      public JsonObject m_7683_(SerializationContext pConditions) {
         JsonObject jsonobject = super.m_7683_(pConditions);
         jsonobject.addProperty("quest", this.quest.getRegistryKey().toString());
         return jsonobject;
      }
   }
}
