package xyz.pixelatedw.mineminenomi.data.triggers;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import java.util.Optional;

public class SelectFactionTrigger extends SimpleCriterionTrigger<SelectFactionTrigger.Instance> {
   private static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath("mineminenomi", "select_faction");

   @Override
   public Codec<Instance> codec() {
       return Instance.CODEC;
   }

   public void trigger(ServerPlayer player, ResourceLocation faction) {
      this.trigger(player, (inst) -> inst.matches(faction));
   }

   public static record Instance(Optional<ContextAwarePredicate> player, ResourceLocation faction) implements SimpleCriterionTrigger.SimpleInstance {
      public static final Codec<Instance> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ContextAwarePredicate.CODEC.optionalFieldOf("player").forGetter(Instance::player),
            ResourceLocation.CODEC.fieldOf("faction").forGetter(Instance::faction)
      ).apply(instance, Instance::new));

      public boolean matches(ResourceLocation faction) {
         return this.faction.equals(faction);
      }
   }
}
