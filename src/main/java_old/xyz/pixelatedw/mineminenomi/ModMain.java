package xyz.pixelatedw.mineminenomi;

import com.google.common.collect.ImmutableMultimap;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.logging.LogUtils;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.server.packs.repository.Pack.Position;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.client.event.RegisterShadersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.event.AddPackFindersEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.config.ModConfig.Type;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.NewRegistryEvent;
import net.minecraftforge.registries.RegistryBuilder;
import net.minecraftforge.registries.ForgeRegistries.Keys;
import net.minecraftforge.resource.PathPackResources;
import org.slf4j.Logger;
import xyz.pixelatedw.mineminenomi.api.WyDebug;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.WyRegistry;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.damagesources.IDamageSourceHandler;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceElement;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.helpers.DamageSourceHelper;
import xyz.pixelatedw.mineminenomi.api.util.PausableTimer;
import xyz.pixelatedw.mineminenomi.api.util.TPSDelta;
import xyz.pixelatedw.mineminenomi.config.CommonConfig;
import xyz.pixelatedw.mineminenomi.config.ServerConfig;
import xyz.pixelatedw.mineminenomi.data.entity.ability.IAbilityData;
import xyz.pixelatedw.mineminenomi.data.entity.animation.IAnimationData;
import xyz.pixelatedw.mineminenomi.data.entity.cameralock.ICameraLock;
import xyz.pixelatedw.mineminenomi.data.entity.carry.ICarryData;
import xyz.pixelatedw.mineminenomi.data.entity.challenge.IChallengeData;
import xyz.pixelatedw.mineminenomi.data.entity.combat.ICombatData;
import xyz.pixelatedw.mineminenomi.data.entity.devilfruit.IDevilFruit;
import xyz.pixelatedw.mineminenomi.data.entity.gcd.IGCDData;
import xyz.pixelatedw.mineminenomi.data.entity.haki.IHakiData;
import xyz.pixelatedw.mineminenomi.data.entity.hisolookout.IHisoLookoutData;
import xyz.pixelatedw.mineminenomi.data.entity.kairosekicoating.IKairosekiCoating;
import xyz.pixelatedw.mineminenomi.data.entity.nbtgoals.INBTGoals;
import xyz.pixelatedw.mineminenomi.data.entity.projectileextra.IProjectileExtras;
import xyz.pixelatedw.mineminenomi.data.entity.quest.IQuestData;
import xyz.pixelatedw.mineminenomi.data.entity.stats.IEntityStats;
import xyz.pixelatedw.mineminenomi.datagen.ModAdvancementsProvider;
import xyz.pixelatedw.mineminenomi.datagen.ModLootTableProvider;
import xyz.pixelatedw.mineminenomi.datagen.ModRecipesProvider;
import xyz.pixelatedw.mineminenomi.datagen.tags.ModBiomeTagsProvider;
import xyz.pixelatedw.mineminenomi.datagen.tags.ModBlockTagsProvider;
import xyz.pixelatedw.mineminenomi.datagen.tags.ModDamageTypeTagsProvider;
import xyz.pixelatedw.mineminenomi.datagen.tags.ModEntityTagsProvider;
import xyz.pixelatedw.mineminenomi.datagen.tags.ModItemTagsProvider;
import xyz.pixelatedw.mineminenomi.handlers.ModEventHandler;
import xyz.pixelatedw.mineminenomi.init.ModAbilities;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;
import xyz.pixelatedw.mineminenomi.init.ModAdvancements;
import xyz.pixelatedw.mineminenomi.init.ModAnimations;
import xyz.pixelatedw.mineminenomi.init.ModArmors;
import xyz.pixelatedw.mineminenomi.init.ModAttributes;
import xyz.pixelatedw.mineminenomi.init.ModBiomeModifiers;
import xyz.pixelatedw.mineminenomi.init.ModBlockEntities;
import xyz.pixelatedw.mineminenomi.init.ModBlocks;
import xyz.pixelatedw.mineminenomi.init.ModChallenges;
import xyz.pixelatedw.mineminenomi.init.ModCommandArgumentTypes;
import xyz.pixelatedw.mineminenomi.init.ModConfiguredFeatures;
import xyz.pixelatedw.mineminenomi.init.ModContainers;
import xyz.pixelatedw.mineminenomi.init.ModCreativeTabs;
import xyz.pixelatedw.mineminenomi.init.ModDamageTypes;
import xyz.pixelatedw.mineminenomi.init.ModDimensions;
import xyz.pixelatedw.mineminenomi.init.ModDispenseBehaviors;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModEnchantments;
import xyz.pixelatedw.mineminenomi.init.ModEntities;
import xyz.pixelatedw.mineminenomi.init.ModFactions;
import xyz.pixelatedw.mineminenomi.init.ModFeatures;
import xyz.pixelatedw.mineminenomi.init.ModFightingStyles;
import xyz.pixelatedw.mineminenomi.init.ModFruits;
import xyz.pixelatedw.mineminenomi.init.ModItems;
import xyz.pixelatedw.mineminenomi.init.ModJollyRogers;
import xyz.pixelatedw.mineminenomi.init.ModLootTypes;
import xyz.pixelatedw.mineminenomi.init.ModMemoryModuleTypes;
import xyz.pixelatedw.mineminenomi.init.ModMobs;
import xyz.pixelatedw.mineminenomi.init.ModModelOverrides;
import xyz.pixelatedw.mineminenomi.init.ModMorphs;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModParticleTypes;
import xyz.pixelatedw.mineminenomi.init.ModPermissions;
import xyz.pixelatedw.mineminenomi.init.ModPlacedFeatures;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;
import xyz.pixelatedw.mineminenomi.init.ModQuests;
import xyz.pixelatedw.mineminenomi.init.ModRaces;
import xyz.pixelatedw.mineminenomi.init.ModRecipes;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.ModRenderTypes;
import xyz.pixelatedw.mineminenomi.init.ModRenderers;
import xyz.pixelatedw.mineminenomi.init.ModSounds;
import xyz.pixelatedw.mineminenomi.init.ModStructures;
import xyz.pixelatedw.mineminenomi.init.ModTags;
import xyz.pixelatedw.mineminenomi.init.ModValues;
import xyz.pixelatedw.mineminenomi.init.ModWeapons;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18n;
import xyz.pixelatedw.mineminenomi.integrations.curios.CuriosIntegration;
import xyz.pixelatedw.mineminenomi.mixins.IRangedAttributeMixin;
import xyz.pixelatedw.mineminenomi.ui.screens.WhiteWalkieStorageScreen;

@Mod("mineminenomi")
@EventBusSubscriber(
   value = {Dist.CLIENT},
   modid = "mineminenomi",
   bus = Bus.MOD
)
public class ModMain {
   public static final PausableTimer PAUSABLE_TIMER = new PausableTimer();
   public static final String PROJECT_ID = "mineminenomi";
   public static final String PROJECT_VERSION = "0.11.0";
   public static final String PROJECT_NAME = "Mine Mine no Mi";
   public static final Logger LOGGER = LogUtils.getLogger();

   public ModMain(FMLJavaModLoadingContext ctx) {
      Locale.setDefault(Locale.ENGLISH);
      PAUSABLE_TIMER.start();
      IEventBus modEventBus = ctx.getModEventBus();
      ModEventHandler.init();
      modEventBus.addListener(this::modCommonSetup);
      modEventBus.addListener(this::modLoadComplete);
      modEventBus.addListener(this::registerCapabilities);
      modEventBus.addListener(this::registerNewRegistries);
      modEventBus.addListener(this::registerGatherData);
      DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
            modEventBus.addListener(ModParticleTypes.Client::registerParticleFactories);
            modEventBus.addListener(this::modClientSetup);
            modEventBus.addListener(this::registerShaders);
            modEventBus.addListener(this::registerColorHandlers);
            modEventBus.addListener(this::registerClientPackFinder);
            ClientProxy.init(ctx);
         });
      MinecraftForge.EVENT_BUS.addListener(ModPermissions::registerPermissions);
      ModRegistry.init(modEventBus);
      ModItems.init();
      ModFruits.init();
      ModArmors.init();
      ModWeapons.init();
      ModTags.init();
      ModBlocks.init();
      ModContainers.init();
      ModBlockEntities.init();
      ModAbilityComponents.init();
      ModAbilities.init();
      ModMorphs.init();
      ModMobs.init();
      ModProjectiles.init();
      ModEntities.init();
      ModFactions.init();
      ModRaces.init();
      ModFightingStyles.init();
      ModChallenges.init();
      ModQuests.init();
      ModFeatures.init();
      ModDimensions.init();
      ModStructures.init();
      ModEnchantments.init();
      ModAttributes.init();
      ModEffects.init();
      ModCreativeTabs.init();
      ModJollyRogers.init();
      ModLootTypes.init();
      ModMemoryModuleTypes.init();
      ModParticleEffects.init();
      ModParticleTypes.init();
      ModSounds.init();
      ModCommandArgumentTypes.init();
      ModAdvancements.register();
      ModRecipes.init();
      ModDamageTypes.init();
      ctx.registerConfig(Type.COMMON, CommonConfig.SPEC);
      ctx.registerConfig(Type.SERVER, ServerConfig.SPEC);
      TPSDelta.INSTANCE.init();
   }

   private void registerCapabilities(RegisterCapabilitiesEvent event) {
      event.register(IAbilityData.class);
      event.register(IAnimationData.class);
      event.register(ICarryData.class);
      event.register(IChallengeData.class);
      event.register(ICombatData.class);
      event.register(IDevilFruit.class);
      event.register(IGCDData.class);
      event.register(IHakiData.class);
      event.register(IQuestData.class);
      event.register(IEntityStats.class);
      event.register(IKairosekiCoating.class);
      event.register(INBTGoals.class);
      event.register(IHisoLookoutData.class);
      event.register(ICameraLock.class);
      event.register(IProjectileExtras.class);
   }

   private void registerNewRegistries(NewRegistryEvent event) {
      event.create((new RegistryBuilder()).setName(WyRegistry.Keys.ABILITIES.m_135782_()));
      event.create((new RegistryBuilder()).setName(WyRegistry.Keys.ABILITY_COMPONENTS.m_135782_()));
      event.create((new RegistryBuilder()).setName(WyRegistry.Keys.JOLLY_ROGER_ELEMENTS.m_135782_()));
      event.create((new RegistryBuilder()).setName(WyRegistry.Keys.CHALLENGES.m_135782_()));
      event.create((new RegistryBuilder()).setName(WyRegistry.Keys.MORPHS.m_135782_()));
      event.create((new RegistryBuilder()).setName(WyRegistry.Keys.PARTICLE_EFFECTS.m_135782_()));
      event.create((new RegistryBuilder()).setName(WyRegistry.Keys.QUESTS.m_135782_()));
      event.create((new RegistryBuilder()).setName(WyRegistry.Keys.FACTIONS.m_135782_()));
      event.create((new RegistryBuilder()).setName(WyRegistry.Keys.FIGHTING_STYLES.m_135782_()));
      event.create((new RegistryBuilder()).setName(WyRegistry.Keys.RACES.m_135782_()));
      event.create((new RegistryBuilder()).setName(WyRegistry.Keys.PART_ENTITY_TYPES.m_135782_()));
   }

   private void registerGatherData(GatherDataEvent event) {
      DataGenerator generator = event.getGenerator();
      PackOutput output = generator.getPackOutput();
      CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
      RegistrySetBuilder registrySet = (new RegistrySetBuilder()).m_254916_(Registries.f_268580_, ModDamageTypes::bootstrap).m_254916_(Registries.f_256911_, ModConfiguredFeatures::bootstrap).m_254916_(Registries.f_256988_, ModPlacedFeatures::bootstrap).m_254916_(Keys.BIOME_MODIFIERS, ModBiomeModifiers::bootstrap);
      ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
      generator.addProvider(event.includeServer(), new DatapackBuiltinEntriesProvider(output, lookupProvider, registrySet, Set.of("mineminenomi")));
      ModBlockTagsProvider blockTags = new ModBlockTagsProvider(output, lookupProvider, existingFileHelper);
      generator.addProvider(event.includeServer(), new ModDamageTypeTagsProvider(output, lookupProvider, existingFileHelper));
      generator.addProvider(event.includeServer(), blockTags);
      generator.addProvider(event.includeServer(), new ModItemTagsProvider(output, lookupProvider, blockTags.m_274426_(), existingFileHelper));
      generator.addProvider(event.includeServer(), new ModEntityTagsProvider(output, lookupProvider, existingFileHelper));
      generator.addProvider(event.includeServer(), new ModBiomeTagsProvider(output, lookupProvider, existingFileHelper));
      generator.addProvider(event.includeServer(), new ModAdvancementsProvider(output, lookupProvider, existingFileHelper));
      generator.addProvider(event.includeServer(), new ModRecipesProvider(output));
      generator.addProvider(event.includeServer(), new ModLootTableProvider(output));
   }

   private void modCommonSetup(FMLCommonSetupEvent event) {
      ModNetwork.init();
      ModDispenseBehaviors.init();
      ImmutableMultimap.Builder<AbilityCategory, AbilityCore<?>> mapBuilder = ImmutableMultimap.builder();

      for(AbilityCore<?> core : ((IForgeRegistry)WyRegistry.ABILITIES.get()).getValues()) {
         mapBuilder.put(core.getCategory(), core);
      }

      ModValues.abilityCategoryMap = mapBuilder.build();
      if (hasCuriosInstalled()) {
         CuriosIntegration.registerCurioItems();
      }

   }

   private void modLoadComplete(FMLLoadCompleteEvent event) {
      ((IRangedAttributeMixin)Attributes.f_22276_).setMaxValue((double)3000.0F);
      DamageSourceHelper.registerEntityDamageValue(EntityType.f_20467_, new IDamageSourceHandler.NuDamageValue((AbilityCore)null, SourceElement.WATER, SourceHakiNature.IMBUING, new SourceType[]{SourceType.PROJECTILE}));
   }

   private void modClientSetup(FMLClientSetupEvent event) {
      ModI18n.init();
      ModRenderers.init();
      ModModelOverrides.register();
      if (WyDebug.isDebug()) {
         WyHelper.generateJSONLangs();
      }

      event.enqueueWork(() -> {
         ModAnimations.init();
         MenuScreens.m_96206_((MenuType)ModContainers.WHITE_WALKIE_STORAGE.get(), (container, inv, title) -> new WhiteWalkieStorageScreen(container, inv));
         if (hasCuriosInstalled()) {
            CuriosIntegration.registerCurioRenderers();
         }

      });
   }

   private void registerShaders(RegisterShadersEvent event) {
      try {
         event.registerShader(new ShaderInstance(event.getResourceProvider(), ResourceLocation.fromNamespaceAndPath("mineminenomi", "rendertype_entity_translucent_color_no_texture"), DefaultVertexFormat.f_85812_), (shaderInstance) -> ModRenderTypes.transparentColorShaderInstance = shaderInstance);
         event.registerShader(new ShaderInstance(event.getResourceProvider(), ResourceLocation.fromNamespaceAndPath("mineminenomi", "rendertype_entity_aura_haki"), DefaultVertexFormat.f_85818_), (shaderInstance) -> ModRenderTypes.auraHakiShaderInstance = shaderInstance);
      } catch (IOException e) {
         e.printStackTrace();
      }

   }

   private void registerColorHandlers(RegisterColorHandlersEvent.Item event) {
      ModItems.Client.registerColorHandlers(event);
   }

   private void registerClientPackFinder(AddPackFindersEvent event) {
      Pack dubPack = this.registerResourcePack("dub-pack", Component.m_237113_("Dub Pack"));
      event.addRepositorySource((consumer) -> consumer.accept(dubPack));
   }

   private Pack registerResourcePack(String packName, Component title) {
      Path packPath = ModList.get().getModFileById("mineminenomi").getFile().findResource(new String[]{"packs/" + packName});
      return Pack.m_245429_(packName, title, false, (path) -> new PathPackResources(path, false, packPath), PackType.CLIENT_RESOURCES, Position.TOP, PackSource.f_10528_);
   }

   public static boolean hasCuriosInstalled() {
      return ModList.get().isLoaded("curios");
   }

   public static boolean hasClothConfigInstalled() {
      return ModList.get().isLoaded("cloth_config");
   }
}
