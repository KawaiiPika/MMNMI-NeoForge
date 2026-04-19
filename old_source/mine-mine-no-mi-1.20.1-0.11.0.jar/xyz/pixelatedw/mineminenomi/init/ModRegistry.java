package xyz.pixelatedw.mineminenomi.init;

import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.serialization.Codec;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;
import net.minecraft.commands.synchronization.ArgumentTypeInfo;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.EntityType.Builder;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.structure.placement.StructurePlacement;
import net.minecraft.world.level.levelgen.structure.placement.StructurePlacementType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.storage.loot.Serializer;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.entity.PartEntity;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.network.IContainerFactory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import xyz.pixelatedw.mineminenomi.api.AttributeRegistryEntry;
import xyz.pixelatedw.mineminenomi.api.WyDebug;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.WyRegistry;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityRegistryEntry;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponentKey;
import xyz.pixelatedw.mineminenomi.api.challenges.Challenge;
import xyz.pixelatedw.mineminenomi.api.challenges.ChallengeCore;
import xyz.pixelatedw.mineminenomi.api.crew.JollyRogerElement;
import xyz.pixelatedw.mineminenomi.api.crew.JollyRogerElementRegistryEntry;
import xyz.pixelatedw.mineminenomi.api.entities.PartEntityType;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Faction;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.FightingStyle;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Race;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityGroups;
import xyz.pixelatedw.mineminenomi.api.morph.MorphInfo;
import xyz.pixelatedw.mineminenomi.api.quests.Quest;
import xyz.pixelatedw.mineminenomi.api.quests.QuestId;
import xyz.pixelatedw.mineminenomi.items.AkumaNoMiItem;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class ModRegistry {
   private static HashMap<String, String> langMap = new HashMap();
   private static List<RegistryObject<ForgeSpawnEggItem>> modSpawnEggs = new ArrayList();
   private static final DeferredRegister<Item> ITEMS;
   private static final DeferredRegister<Block> BLOCKS;
   private static final DeferredRegister<MenuType<?>> CONTAINER_TYPES;
   private static final DeferredRegister<AbilityCore<? extends IAbility>> ABILITIES;
   private static final DeferredRegister<AbilityComponentKey<? extends AbilityComponent<?>>> ABILITY_COMPONENTS;
   private static final DeferredRegister<MobEffect> EFFECTS;
   private static final DeferredRegister<Enchantment> ENCHANTMENTS;
   private static final DeferredRegister<EntityType<?>> ENTITY_TYPES;
   private static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES;
   private static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES;
   private static final DeferredRegister<QuestId<?>> QUESTS;
   private static final DeferredRegister<Feature<?>> FEATURES;
   private static final DeferredRegister<SoundEvent> SOUNDS;
   private static final DeferredRegister<Attribute> ATTRIBUTES;
   private static final DeferredRegister<ChallengeCore<?>> CHALLENGES;
   private static final DeferredRegister<ParticleEffect<?>> PARTICLE_EFFECTS;
   private static final DeferredRegister<ArgumentTypeInfo<?, ?>> COMMAND_ARGUMENT_TYPES;
   private static final DeferredRegister<JollyRogerElement> JOLLY_ROGER_ELEMENTS;
   private static final DeferredRegister<MorphInfo> MORPHS;
   private static final DeferredRegister<CreativeModeTab> CREATIVE_TABS;
   private static final DeferredRegister<LootItemFunctionType> LOOT_FUNCTIONS;
   private static final DeferredRegister<LootItemConditionType> LOOT_CONDITIONS;
   private static final DeferredRegister<MemoryModuleType<?>> MEMORY_MODULE_TYPES;
   private static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS;
   private static final DeferredRegister<Faction> FACTIONS;
   private static final DeferredRegister<FightingStyle> FIGHTING_STYLES;
   private static final DeferredRegister<Race> RACES;
   private static final DeferredRegister<PartEntityType<? extends PartEntity<? extends Entity>, ? extends Entity>> PART_ENTITY_TYPES;
   private static final DeferredRegister<Codec<? extends ChunkGenerator>> CHUNK_GENERATORS;
   private static final DeferredRegister<DimensionType> DIMENSION_TYPE;
   private static final DeferredRegister<StructurePlacementType<?>> STRUCTURE_PLACEMENTS;
   private static final DeferredRegister<StructureProcessorType<?>> STRUCTURE_PROCESSOR;

   public static HashMap<String, String> getLangMap() {
      return langMap;
   }

   public static List<RegistryObject<ForgeSpawnEggItem>> getModSpawnEggs() {
      return modSpawnEggs;
   }

   public static void init(IEventBus eventBus) {
      ITEMS.register(eventBus);
      BLOCKS.register(eventBus);
      CONTAINER_TYPES.register(eventBus);
      ABILITIES.register(eventBus);
      ABILITY_COMPONENTS.register(eventBus);
      EFFECTS.register(eventBus);
      ENCHANTMENTS.register(eventBus);
      ENTITY_TYPES.register(eventBus);
      BLOCK_ENTITIES.register(eventBus);
      PARTICLE_TYPES.register(eventBus);
      QUESTS.register(eventBus);
      FEATURES.register(eventBus);
      SOUNDS.register(eventBus);
      ATTRIBUTES.register(eventBus);
      CHALLENGES.register(eventBus);
      PARTICLE_EFFECTS.register(eventBus);
      COMMAND_ARGUMENT_TYPES.register(eventBus);
      JOLLY_ROGER_ELEMENTS.register(eventBus);
      MORPHS.register(eventBus);
      CREATIVE_TABS.register(eventBus);
      LOOT_FUNCTIONS.register(eventBus);
      LOOT_CONDITIONS.register(eventBus);
      MEMORY_MODULE_TYPES.register(eventBus);
      RECIPE_SERIALIZERS.register(eventBus);
      FACTIONS.register(eventBus);
      FIGHTING_STYLES.register(eventBus);
      RACES.register(eventBus);
      PART_ENTITY_TYPES.register(eventBus);
      CHUNK_GENERATORS.register(eventBus);
      STRUCTURE_PLACEMENTS.register(eventBus);
      STRUCTURE_PROCESSOR.register(eventBus);
   }

   /** @deprecated */
   @Deprecated
   public static String registerName(String key, String localizedName) {
      getLangMap().put(key, localizedName);
      return key;
   }

   public static String registerName(I18nCategory category, String key, String localizedName) {
      key = category.id + ".mineminenomi." + key;
      if (WyDebug.isDebug() && getLangMap().containsKey(key)) {
         WyDebug.error(key + " key has already been registered!");
      }

      getLangMap().put(key, localizedName);
      return key;
   }

   public static String registerAbilityName(String key, String localizedName) {
      return registerName(ModRegistry.I18nCategory.ABILITY, key, localizedName);
   }

   public static <A extends ArgumentType<?>, T extends ArgumentTypeInfo.Template<A>, I extends ArgumentTypeInfo<A, T>> RegistryObject<I> registerCommandArgumentType(String id, Supplier<I> info) {
      RegistryObject<I> reg = COMMAND_ARGUMENT_TYPES.register(id, info);
      return reg;
   }

   public static RegistryObject<LootItemFunctionType> registerLootFunction(String id, Serializer<? extends LootItemFunction> serializer) {
      return LOOT_FUNCTIONS.register(id, () -> new LootItemFunctionType(serializer));
   }

   public static RegistryObject<LootItemConditionType> registerLootCondition(String id, Serializer<? extends LootItemCondition> serializer) {
      return LOOT_CONDITIONS.register(id, () -> new LootItemConditionType(serializer));
   }

   public static <T> RegistryObject<MemoryModuleType<T>> registerMemoryModule(String name, Codec<T> codec) {
      return MEMORY_MODULE_TYPES.register(name, () -> new MemoryModuleType(Optional.of(codec)));
   }

   public static <T> RegistryObject<MemoryModuleType<T>> registerMemoryModule(String name) {
      return MEMORY_MODULE_TYPES.register(name, () -> new MemoryModuleType(Optional.empty()));
   }

   public static <S extends RecipeSerializer<T>, T extends Recipe<?>> RegistryObject<S> registerRecipeSerializer(String name, Supplier<S> serializer) {
      return RECIPE_SERIALIZERS.register(name, serializer);
   }

   public static <T extends CreativeModeTab> RegistryObject<T> registerCreativeTab(String localizedName, Supplier<T> attr) {
      String resourceName = WyHelper.getResourceName(localizedName);
      registerName(ModRegistry.I18nCategory.CREATIVE_TAB, resourceName, localizedName);
      return CREATIVE_TABS.register(resourceName, attr);
   }

   public static <T extends ParticleEffect<?>> RegistryObject<T> registerParticleEffect(String localizedName, Supplier<T> effect) {
      String resourceName = WyHelper.getResourceName(localizedName);
      return PARTICLE_EFFECTS.register("particle_effect.mineminenomi." + resourceName, effect);
   }

   public static RegistryObject<Attribute> registerAttribute(String localizedName, AttributeRegistryEntry attr) {
      String resourceName = WyHelper.getResourceName(localizedName);
      String id = registerName(ModRegistry.I18nCategory.ATTRIBUTE, "name." + resourceName, localizedName);
      RegistryObject<Attribute> reg = ATTRIBUTES.register(resourceName, () -> attr.get(id));
      return reg;
   }

   public static <T extends MorphInfo> RegistryObject<T> registerMorph(String resourceName, Supplier<T> morph) {
      RegistryObject<T> reg = MORPHS.register(resourceName, morph);
      return reg;
   }

   public static RegistryObject<JollyRogerElement> registerJollyRogerElement(String id, String name, JollyRogerElement.LayerType layer, JollyRogerElementRegistryEntry entry) {
      String var10000;
      switch (layer) {
         case BASE -> var10000 = "bases";
         case BACKGROUND -> var10000 = "backgrounds";
         case DETAIL -> var10000 = "details";
         default -> throw new IncompatibleClassChangeError();
      }

      String layerName = var10000;
      Component localizationName = Component.m_237115_(registerName("jolly_roger.mineminenomi." + layerName + "." + id, name));
      String newId = layerName + "_" + id;
      return JOLLY_ROGER_ELEMENTS.register(newId, () -> {
         ResourceLocation texture = ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/jolly_rogers/" + layerName + "/" + id + ".png");
         JollyRogerElement elem = entry.get(layer).setTexture(texture).setLocalizedName(localizationName);
         return elem;
      });
   }

   public static RegistryObject<JollyRogerElement> registerJollyRogerElement(JollyRogerElement element) {
      String resourceName = WyHelper.getResourceName(element.getTexture().toString().replace("mineminenomi", ""));
      return JOLLY_ROGER_ELEMENTS.register(resourceName, () -> element);
   }

   public static <T extends ParticleType<?>> RegistryObject<T> registerParticleType(String localizedName, Supplier<T> type) {
      String resourceName = WyHelper.getResourceName(localizedName);
      return PARTICLE_TYPES.register(resourceName, type);
   }

   public static <T extends Feature<?>> RegistryObject<T> registerFeature(String localizedName, Supplier<T> feature) {
      String resourceName = WyHelper.getResourceName(localizedName);
      return FEATURES.register(resourceName, feature);
   }

   public static <T extends MobEffect> RegistryObject<T> registerEffect(String localizedName, Supplier<T> effect) {
      String resourceName = WyHelper.getResourceName(localizedName);
      return registerEffect(localizedName, resourceName, effect);
   }

   public static <T extends MobEffect> RegistryObject<T> registerEffect(String localizedName, String resourceKey, Supplier<T> effect) {
      String resourceName = WyHelper.getResourceName(resourceKey);
      getLangMap().put("effect.mineminenomi." + resourceName, localizedName);
      RegistryObject<T> reg = EFFECTS.register(resourceName, effect);
      return reg;
   }

   public static <T extends Enchantment> RegistryObject<T> registerEnchantment(String localizedName, Supplier<T> enchantment) {
      String resourceName = WyHelper.getResourceName(localizedName);
      getLangMap().put("enchantment.mineminenomi." + resourceName, localizedName);
      RegistryObject<T> reg = ENCHANTMENTS.register(resourceName, enchantment);
      return reg;
   }

   public static <T extends Quest> RegistryObject<QuestId<T>> registerQuest(String id, String title, Supplier<QuestId<T>> quest) {
      getLangMap().put("quest.mineminenomi." + id, title);
      RegistryObject<QuestId<T>> reg = QUESTS.register(id, quest);
      return reg;
   }

   public static Component registerObjectiveTitle(String questId, int objectiveId, String title, Object... args) {
      String id = registerName("quest.mineminenomi." + questId + ".objective." + objectiveId, title);
      return Component.m_237110_(id, args);
   }

   public static <T extends IAbility> RegistryObject<AbilityCore<T>> registerAbility(String id, String localizedName, AbilityRegistryEntry<AbilityCore<T>> core) {
      registerName(ModRegistry.I18nCategory.ABILITY, id, localizedName);
      RegistryObject<AbilityCore<T>> reg = ABILITIES.register(id, () -> core.get(id, localizedName));
      return reg;
   }

   public static <C extends AbilityComponent<?>> RegistryObject<AbilityComponentKey<C>> registerAbilityComponent(String id) {
      RegistryObject<AbilityComponentKey<C>> reg = ABILITY_COMPONENTS.register(id, () -> AbilityComponentKey.key(ResourceLocation.fromNamespaceAndPath("mineminenomi", id)));
      return reg;
   }

   public static RegistryObject<SoundEvent> registerSound(String localizedName) {
      String resourceName = WyHelper.getResourceName(localizedName);
      getLangMap().put("mineminenomi.subtitle." + resourceName, localizedName);
      RegistryObject<SoundEvent> reg = SOUNDS.register(resourceName, () -> SoundEvent.m_262824_(ResourceLocation.fromNamespaceAndPath("mineminenomi", resourceName)));
      return reg;
   }

   public static <T extends AkumaNoMiItem> RegistryObject<T> registerFruitItem(String localizedName, Supplier<T> item) {
      String resourceName = WyHelper.getResourceName(localizedName);
      registerName(ModRegistry.I18nCategory.ITEM, resourceName, localizedName);
      AbilityGroups.addFruitGroup(ResourceLocation.fromNamespaceAndPath("mineminenomi", resourceName));
      RegistryObject<T> reg = ITEMS.register(resourceName, item);
      return reg;
   }

   public static <T extends Item> RegistryObject<T> registerItem(String localizedName, Supplier<T> item) {
      String resourceName = WyHelper.getResourceName(localizedName);
      return registerItem(resourceName, localizedName, item);
   }

   public static <T extends Item> RegistryObject<T> registerItem(String id, String localizedName, Supplier<T> item) {
      registerName(ModRegistry.I18nCategory.ITEM, id, localizedName);
      RegistryObject<T> reg = ITEMS.register(id, item);
      return reg;
   }

   public static RegistryObject<ForgeSpawnEggItem> registerSpawnEggItem(String localizedEntityName, Supplier<ForgeSpawnEggItem> supp) {
      String entityResName = WyHelper.getResourceName(localizedEntityName);
      String resourceName = entityResName + "_spawn_egg";
      String localizedName = "Spawn " + localizedEntityName;
      getLangMap().put("item.mineminenomi." + resourceName, localizedName);
      RegistryObject<ForgeSpawnEggItem> reg = ITEMS.register(resourceName, supp);
      getModSpawnEggs().add(reg);
      return reg;
   }

   public static <T extends Block> RegistryObject<T> registerBlock(String localizedName, Supplier<T> block) {
      return registerBlock(localizedName, block, new Item.Properties());
   }

   public static <T extends Block> RegistryObject<T> registerBlock(String localizedName, Supplier<T> block, Item.Properties props) {
      String resourceName = WyHelper.getResourceName(localizedName);
      getLangMap().put("block.mineminenomi." + resourceName, localizedName);
      RegistryObject<T> reg = BLOCKS.register(resourceName, block);
      registerItem(localizedName, () -> new BlockItem((Block)reg.get(), props));
      return reg;
   }

   public static <T extends Block> RegistryObject<T> registerBlock(String localizedName, Supplier<T> block, Function<T, BlockItem> blockItemFunc) {
      String resourceName = WyHelper.getResourceName(localizedName);
      getLangMap().put("block.mineminenomi." + resourceName, localizedName);
      RegistryObject<T> reg = BLOCKS.register(resourceName, block);
      registerItem(localizedName, () -> (BlockItem)blockItemFunc.apply((Block)reg.get()));
      return reg;
   }

   public static <C extends AbstractContainerMenu> RegistryObject<MenuType<C>> registerContainer(String localizedName, IContainerFactory<C> containerFactory) {
      String resourceName = WyHelper.getResourceName(localizedName);
      RegistryObject<MenuType<C>> reg = CONTAINER_TYPES.register(resourceName, () -> IForgeMenuType.create(containerFactory));
      return reg;
   }

   public static <T extends BlockEntity> RegistryObject<BlockEntityType<T>> registerTileEntity(String localizedName, Supplier<BlockEntityType<T>> factory) {
      String resourceName = WyHelper.getResourceName(localizedName);
      RegistryObject<BlockEntityType<T>> reg = BLOCK_ENTITIES.register(resourceName, factory);
      return reg;
   }

   public static <T extends Entity> EntityType.Builder<T> createEntityType(EntityType.EntityFactory<T> factory, MobCategory classification) {
      EntityType.Builder<T> builder = Builder.m_20704_(factory, classification);
      builder.setTrackingRange(64).setShouldReceiveVelocityUpdates(true).setUpdateInterval(5).m_20699_(0.5F, 0.5F);
      return builder;
   }

   public static <T extends Mob> EntityType.Builder<T> createMobType(EntityType.EntityFactory<T> factory, MobCategory classification) {
      EntityType.Builder<T> builder = Builder.m_20704_(factory, classification);
      builder.setTrackingRange(64).setShouldReceiveVelocityUpdates(true).setUpdateInterval(1).m_20699_(0.6F, 1.8F);
      return builder;
   }

   public static <T extends Entity> EntityType.Builder<T> createProjectileType(EntityType.EntityFactory<T> factory) {
      EntityType.Builder<T> builder = Builder.m_20704_(factory, MobCategory.MISC);
      builder.setTrackingRange(64).setShouldReceiveVelocityUpdates(true).setUpdateInterval(1).m_20699_(0.75F, 0.75F);
      return builder;
   }

   public static <T extends EntityType<? extends Entity>> RegistryObject<T> registerEntityType(String localizedName, Supplier<T> supp) {
      String resourceName = WyHelper.getResourceName(localizedName);
      return registerEntityType(localizedName, resourceName, supp);
   }

   public static <T extends EntityType<? extends Entity>> RegistryObject<T> registerEntityType(String localizedName, String id, Supplier<T> supp) {
      getLangMap().put("entity.mineminenomi." + id, localizedName);
      return ENTITY_TYPES.register(id, supp);
   }

   public static <T extends Challenge> RegistryObject<ChallengeCore<T>> registerChallenge(ChallengeCore<T> challenge) {
      RegistryObject<ChallengeCore<T>> reg = CHALLENGES.register(challenge.getId(), () -> challenge);
      return reg;
   }

   public static <I extends Faction> RegistryObject<I> registerFaction(String id, String localizedName, Supplier<I> faction) {
      registerName(ModRegistry.I18nCategory.FACTION, id, localizedName);
      return FACTIONS.register(id, faction);
   }

   public static <I extends FightingStyle> RegistryObject<I> registerStyle(String id, String localizedName, Supplier<I> style) {
      registerName(ModRegistry.I18nCategory.STYLE, id, localizedName);
      return FIGHTING_STYLES.register(id, style);
   }

   public static <I extends Race> RegistryObject<I> registerRace(String id, String localizedName, Supplier<I> race) {
      registerName(ModRegistry.I18nCategory.RACE, id, localizedName);
      return RACES.register(id, race);
   }

   public static <P extends PartEntity<T>, T extends Entity> RegistryObject<PartEntityType<P, T>> registerPartEntityType(String id, PartEntityType.PartEntityFactory<P, T> partFactory) {
      return PART_ENTITY_TYPES.register(id, () -> new PartEntityType(partFactory));
   }

   public static <C extends Codec<? extends ChunkGenerator>> RegistryObject<C> registerChunkGenerator(String id, Supplier<C> chunkGen) {
      return CHUNK_GENERATORS.register(id, chunkGen);
   }

   public static <C extends StructurePlacementType<? extends StructurePlacement>> RegistryObject<C> registerStructurePlacement(String id, Supplier<C> placement) {
      return STRUCTURE_PLACEMENTS.register(id, placement);
   }

   public static <C extends StructureProcessorType<? extends StructureProcessor>> RegistryObject<C> registerStructureProcessor(String id, Supplier<C> placement) {
      return STRUCTURE_PROCESSOR.register(id, placement);
   }

   static {
      ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, "mineminenomi");
      BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, "mineminenomi");
      CONTAINER_TYPES = DeferredRegister.create(ForgeRegistries.MENU_TYPES, "mineminenomi");
      ABILITIES = DeferredRegister.create(WyRegistry.Keys.ABILITIES, "mineminenomi");
      ABILITY_COMPONENTS = DeferredRegister.create(WyRegistry.Keys.ABILITY_COMPONENTS, "mineminenomi");
      EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, "mineminenomi");
      ENCHANTMENTS = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, "mineminenomi");
      ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, "mineminenomi");
      BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, "mineminenomi");
      PARTICLE_TYPES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, "mineminenomi");
      QUESTS = DeferredRegister.create(WyRegistry.Keys.QUESTS, "mineminenomi");
      FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, "mineminenomi");
      SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, "mineminenomi");
      ATTRIBUTES = DeferredRegister.create(ForgeRegistries.ATTRIBUTES, "mineminenomi");
      CHALLENGES = DeferredRegister.create(WyRegistry.Keys.CHALLENGES, "mineminenomi");
      PARTICLE_EFFECTS = DeferredRegister.create(WyRegistry.Keys.PARTICLE_EFFECTS, "mineminenomi");
      COMMAND_ARGUMENT_TYPES = DeferredRegister.create(Registries.f_256982_, "mineminenomi");
      JOLLY_ROGER_ELEMENTS = DeferredRegister.create(WyRegistry.Keys.JOLLY_ROGER_ELEMENTS, "mineminenomi");
      MORPHS = DeferredRegister.create(WyRegistry.Keys.MORPHS, "mineminenomi");
      CREATIVE_TABS = DeferredRegister.create(Registries.f_279569_, "mineminenomi");
      LOOT_FUNCTIONS = DeferredRegister.create(Registries.f_257015_, "mineminenomi");
      LOOT_CONDITIONS = DeferredRegister.create(Registries.f_256976_, "mineminenomi");
      MEMORY_MODULE_TYPES = DeferredRegister.create(ForgeRegistries.MEMORY_MODULE_TYPES, "mineminenomi");
      RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, "mineminenomi");
      FACTIONS = DeferredRegister.create(WyRegistry.Keys.FACTIONS, "mineminenomi");
      FIGHTING_STYLES = DeferredRegister.create(WyRegistry.Keys.FIGHTING_STYLES, "mineminenomi");
      RACES = DeferredRegister.create(WyRegistry.Keys.RACES, "mineminenomi");
      PART_ENTITY_TYPES = DeferredRegister.create(WyRegistry.Keys.PART_ENTITY_TYPES, "mineminenomi");
      CHUNK_GENERATORS = DeferredRegister.create(Registries.f_256783_, "mineminenomi");
      DIMENSION_TYPE = DeferredRegister.create(Registries.f_256787_, "mineminenomi");
      STRUCTURE_PLACEMENTS = DeferredRegister.create(Registries.f_256888_, "mineminenomi");
      STRUCTURE_PROCESSOR = DeferredRegister.create(Registries.f_256983_, "mineminenomi");
   }

   public static enum I18nCategory {
      GUI("gui"),
      ABILITY("ability"),
      ABILITY_NODE("ability_node"),
      ITEM("item"),
      BLOCK("block"),
      CONTAINER("container"),
      ENTITY("entity"),
      FACTION("faction"),
      RACE("race"),
      STYLE("style"),
      QUEST("quest"),
      CREATIVE_TAB("itemGroup"),
      PARTICLE_EFFECT("particle_effect"),
      ATTRIBUTE("attribute"),
      EFFECT("effect"),
      ENCHANTMENT("enchantment"),
      CHALLENGE("challenge"),
      ADVANCEMENT("advancement"),
      KEYBIND("key"),
      KEYBIND_CATEGORY("key.categories"),
      MORPH("morph"),
      CREW("crew"),
      FACTION_RANK("faction_rank"),
      COMMAND("command"),
      TUTORIAL("tutorial");

      private String id;

      private I18nCategory(String id) {
         this.id = id;
      }

      public String getId() {
         return this.id;
      }

      // $FF: synthetic method
      private static I18nCategory[] $values() {
         return new I18nCategory[]{GUI, ABILITY, ABILITY_NODE, ITEM, BLOCK, CONTAINER, ENTITY, FACTION, RACE, STYLE, QUEST, CREATIVE_TAB, PARTICLE_EFFECT, ATTRIBUTE, EFFECT, ENCHANTMENT, CHALLENGE, ADVANCEMENT, KEYBIND, KEYBIND_CATEGORY, MORPH, CREW, FACTION_RANK, COMMAND, TUTORIAL};
      }
   }
}
