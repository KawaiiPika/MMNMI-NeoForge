package xyz.pixelatedw.mineminenomi.api;

import java.util.function.Supplier;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.entity.PartEntity;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryManager;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponentKey;
import xyz.pixelatedw.mineminenomi.api.challenges.ChallengeCore;
import xyz.pixelatedw.mineminenomi.api.crew.JollyRogerElement;
import xyz.pixelatedw.mineminenomi.api.entities.PartEntityType;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Faction;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.FightingStyle;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Race;
import xyz.pixelatedw.mineminenomi.api.morph.MorphInfo;
import xyz.pixelatedw.mineminenomi.api.quests.QuestId;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class WyRegistry {
   public static final Supplier<IForgeRegistry<AbilityCore<? extends IAbility>>> ABILITIES = () -> RegistryManager.ACTIVE.getRegistry(WyRegistry.Keys.ABILITIES);
   public static final Supplier<IForgeRegistry<AbilityComponentKey<? extends AbilityComponent<?>>>> ABILITY_COMPONENTS = () -> RegistryManager.ACTIVE.getRegistry(WyRegistry.Keys.ABILITY_COMPONENTS);
   public static final Supplier<IForgeRegistry<JollyRogerElement>> JOLLY_ROGER_ELEMENTS = () -> RegistryManager.ACTIVE.getRegistry(WyRegistry.Keys.JOLLY_ROGER_ELEMENTS);
   public static final Supplier<IForgeRegistry<ChallengeCore<?>>> CHALLENGES = () -> RegistryManager.ACTIVE.getRegistry(WyRegistry.Keys.CHALLENGES);
   public static final Supplier<IForgeRegistry<MorphInfo>> MORPHS = () -> RegistryManager.ACTIVE.getRegistry(WyRegistry.Keys.MORPHS);
   public static final Supplier<IForgeRegistry<ParticleEffect<?>>> PARTICLE_EFFECTS = () -> RegistryManager.ACTIVE.getRegistry(WyRegistry.Keys.PARTICLE_EFFECTS);
   public static final Supplier<IForgeRegistry<QuestId<?>>> QUESTS = () -> RegistryManager.ACTIVE.getRegistry(WyRegistry.Keys.QUESTS);
   public static final Supplier<IForgeRegistry<Faction>> FACTIONS = () -> RegistryManager.ACTIVE.getRegistry(WyRegistry.Keys.FACTIONS);
   public static final Supplier<IForgeRegistry<FightingStyle>> FIGHTING_STYLES = () -> RegistryManager.ACTIVE.getRegistry(WyRegistry.Keys.FIGHTING_STYLES);
   public static final Supplier<IForgeRegistry<Race>> RACES = () -> RegistryManager.ACTIVE.getRegistry(WyRegistry.Keys.RACES);
   public static final Supplier<IForgeRegistry<PartEntityType<? extends PartEntity<? extends Entity>, ? extends Entity>>> PART_ENTITY_TYPES = () -> RegistryManager.ACTIVE.getRegistry(WyRegistry.Keys.PART_ENTITY_TYPES);

   public static class Keys {
      public static final ResourceKey<Registry<AbilityCore<? extends IAbility>>> ABILITIES = key("abilities");
      public static final ResourceKey<Registry<AbilityComponentKey<? extends AbilityComponent<?>>>> ABILITY_COMPONENTS = key("ability_components");
      public static final ResourceKey<Registry<JollyRogerElement>> JOLLY_ROGER_ELEMENTS = key("jolly_roger_elements");
      public static final ResourceKey<Registry<ChallengeCore<?>>> CHALLENGES = key("challenges");
      public static final ResourceKey<Registry<MorphInfo>> MORPHS = key("morphs");
      public static final ResourceKey<Registry<ParticleEffect<?>>> PARTICLE_EFFECTS = key("particle_effects");
      public static final ResourceKey<Registry<QuestId<?>>> QUESTS = key("quests");
      public static final ResourceKey<Registry<Faction>> FACTIONS = key("factions");
      public static final ResourceKey<Registry<FightingStyle>> FIGHTING_STYLES = key("fighting_styles");
      public static final ResourceKey<Registry<Race>> RACES = key("races");
      public static final ResourceKey<Registry<PartEntityType<? extends PartEntity<? extends Entity>, ? extends Entity>>> PART_ENTITY_TYPES = key("part_entity_types");

      private static <T> ResourceKey<Registry<T>> key(String name) {
         return ResourceKey.m_135788_(ResourceLocation.fromNamespaceAndPath("mineminenomi", name));
      }
   }
}
