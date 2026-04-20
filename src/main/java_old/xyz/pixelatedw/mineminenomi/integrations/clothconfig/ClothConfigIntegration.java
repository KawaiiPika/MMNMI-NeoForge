package xyz.pixelatedw.mineminenomi.integrations.clothconfig;

import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;
import java.util.function.BiFunction;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import me.shedaniel.clothconfig2.gui.entries.BooleanListEntry;
import me.shedaniel.clothconfig2.gui.entries.ColorEntry;
import me.shedaniel.clothconfig2.gui.entries.DoubleListEntry;
import me.shedaniel.clothconfig2.gui.entries.DropdownBoxEntry;
import me.shedaniel.clothconfig2.gui.entries.EnumListEntry;
import me.shedaniel.clothconfig2.gui.entries.IntegerListEntry;
import me.shedaniel.clothconfig2.gui.entries.IntegerListListEntry;
import me.shedaniel.clothconfig2.gui.entries.IntegerSliderEntry;
import me.shedaniel.clothconfig2.gui.entries.LongListEntry;
import me.shedaniel.clothconfig2.gui.entries.StringListListEntry;
import me.shedaniel.clothconfig2.impl.builders.BooleanToggleBuilder;
import me.shedaniel.clothconfig2.impl.builders.DoubleFieldBuilder;
import me.shedaniel.clothconfig2.impl.builders.IntFieldBuilder;
import me.shedaniel.clothconfig2.impl.builders.IntSliderBuilder;
import me.shedaniel.clothconfig2.impl.builders.LongFieldBuilder;
import me.shedaniel.clothconfig2.impl.builders.SubCategoryBuilder;
import me.shedaniel.clothconfig2.impl.builders.DropdownMenuBuilder.CellCreatorBuilder;
import me.shedaniel.clothconfig2.impl.builders.DropdownMenuBuilder.TopCellElementBuilder;
import me.shedaniel.math.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.config.options.BooleanOption;
import xyz.pixelatedw.mineminenomi.api.config.options.ColorOption;
import xyz.pixelatedw.mineminenomi.api.config.options.DoubleOption;
import xyz.pixelatedw.mineminenomi.api.config.options.EnumOption;
import xyz.pixelatedw.mineminenomi.api.config.options.IntegerListOption;
import xyz.pixelatedw.mineminenomi.api.config.options.IntegerOption;
import xyz.pixelatedw.mineminenomi.api.config.options.LongOption;
import xyz.pixelatedw.mineminenomi.api.config.options.StringListOption;
import xyz.pixelatedw.mineminenomi.config.AbilitiesConfig;
import xyz.pixelatedw.mineminenomi.config.ChallengesConfig;
import xyz.pixelatedw.mineminenomi.config.FactionsConfig;
import xyz.pixelatedw.mineminenomi.config.GeneralConfig;
import xyz.pixelatedw.mineminenomi.config.SystemConfig;
import xyz.pixelatedw.mineminenomi.config.UIConfig;
import xyz.pixelatedw.mineminenomi.config.WorldEventsConfig;
import xyz.pixelatedw.mineminenomi.config.WorldFeaturesConfig;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18nConfig;

public class ClothConfigIntegration {
   private static final BiFunction<Minecraft, Screen, Screen> CONFIG_FACTORY = (client, parent) -> {
      ConfigBuilder builder = ConfigBuilder.create();
      int bgTextureId = 1 + (new Random()).nextInt(3);
      builder.setTitle(Component.m_237115_(ModI18nConfig.CONFIG_TITLE)).setDefaultBackgroundTexture(ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/blocks/poneglyph" + bgTextureId + ".png"));
      ConfigEntryBuilder entryBuilder = builder.entryBuilder();
      if (client.f_91073_ != null) {
         ConfigCategory general = builder.getOrCreateCategory(Component.m_237115_(ModI18nConfig.GENERAL_CATEGORY));
         general.addEntry(addBooleanToggle(entryBuilder, GeneralConfig.EXTRA_HEARTS));
         general.addEntry(addIntSlider(entryBuilder, GeneralConfig.HEALTH_GAIN_FREQUENCY));
         general.addEntry(addBooleanToggle(entryBuilder, GeneralConfig.RACE_RANDOMIZER));
         general.addEntry(addBooleanToggle(entryBuilder, GeneralConfig.ALLOW_SUB_RACE_SELECT));
         general.addEntry(addBooleanToggle(entryBuilder, GeneralConfig.PUBLIC_REMOVEDF));
         general.addEntry(addBooleanToggle(entryBuilder, GeneralConfig.PUBLIC_CHECK_FRUITS));
         general.addEntry(addIntField(entryBuilder, GeneralConfig.DORIKI_LIMIT));
         general.addEntry(addIntField(entryBuilder, GeneralConfig.HAKI_EXP_LIMIT));
         general.addEntry(addBooleanToggle(entryBuilder, GeneralConfig.DESTROY_SPAWNER));
         general.addEntry(addBooleanToggle(entryBuilder, GeneralConfig.DESTROY_WATER));
         SubCategoryBuilder npcs = entryBuilder.startSubCategory(Component.m_237115_(ModI18nConfig.NPCS_CATEGORY)).setExpanded(false);
         npcs.add(addBooleanToggle(entryBuilder, GeneralConfig.MOB_REWARDS));
         npcs.add(addBooleanToggle(entryBuilder, GeneralConfig.MINIMUM_DORIKI_PER_KILL));
         npcs.add(addDoubleField(entryBuilder, GeneralConfig.DORIKI_REWARD_MULTIPLIER));
         npcs.add(addDoubleField(entryBuilder, GeneralConfig.BELLY_REWARD_MULTIPLIER));
         npcs.add(addDoubleField(entryBuilder, GeneralConfig.BOUNTY_REWARD_MULTIPLIER));
         npcs.add(addDoubleField(entryBuilder, GeneralConfig.HAKI_EXP_MULTIPLIER));
         npcs.add(addDoubleField(entryBuilder, GeneralConfig.LOYALTY_MULTIPLIER));
         npcs.add(addBooleanToggle(entryBuilder, GeneralConfig.DESPAWN_WITH_NAMETAG));
         npcs.add(addBooleanToggle(entryBuilder, GeneralConfig.NATIVE_HAKI));
         general.addEntry(npcs.build());
         SubCategoryBuilder keepStats = entryBuilder.startSubCategory(Component.m_237113_("Keep Stats")).setTooltip(new Component[]{Component.m_237115_(ModI18nConfig.KEEP_STATS_SUB_CATEGORY_TOOLTIP)}).setExpanded(false);
         keepStats.add(addBooleanToggle(entryBuilder, GeneralConfig.RACE_KEEP));
         keepStats.add(addBooleanToggle(entryBuilder, GeneralConfig.FACTION_KEEP));
         keepStats.add(addBooleanToggle(entryBuilder, GeneralConfig.FIGHTING_STYLE_KEEP));
         keepStats.add(addBooleanToggle(entryBuilder, GeneralConfig.DEVIL_FRUIT_KEEP));
         keepStats.add(addIntSlider(entryBuilder, GeneralConfig.DORIKI_KEEP_PERCENTAGE));
         keepStats.add(addIntSlider(entryBuilder, GeneralConfig.BOUNTY_KEEP_PERCENTAGE));
         keepStats.add(addIntSlider(entryBuilder, GeneralConfig.BELLY_KEEP_PERCENTAGE));
         keepStats.add(addIntSlider(entryBuilder, GeneralConfig.HAKI_EXP_KEEP_PERCENTAGE));
         keepStats.add(addIntSlider(entryBuilder, GeneralConfig.LOYALTY_KEEP_PERCENTAGE));
         general.addEntry(keepStats.build());
         SubCategoryBuilder quests = entryBuilder.startSubCategory(Component.m_237113_("Quests & Trials")).setExpanded(false);
         quests.add(addBooleanToggle(entryBuilder, GeneralConfig.ENABLE_TRIALS));
         quests.add(addBooleanToggle(entryBuilder, GeneralConfig.ENABLE_STYLES_PROGRESSION));
         general.addEntry(quests.build());
         ConfigCategory abilities = builder.getOrCreateCategory(Component.m_237115_(ModI18nConfig.ABILITIES_CATEGORY));
         abilities.addEntry(addBooleanToggle(entryBuilder, AbilitiesConfig.ABILITY_GRIEFING));
         abilities.addEntry(addBooleanToggle(entryBuilder, AbilitiesConfig.ABILITY_FRAUD_CHECKS));
         abilities.addEntry(addIntSlider(entryBuilder, AbilitiesConfig.ABILITY_BARS));
         abilities.addEntry(addBooleanToggle(entryBuilder, AbilitiesConfig.YAMI_POWER));
         abilities.addEntry(addBooleanToggle(entryBuilder, AbilitiesConfig.ENABLE_AWAKENINGS));
         abilities.addEntry(addBooleanToggle(entryBuilder, AbilitiesConfig.WATER_CHECKS));
         abilities.addEntry(addBooleanToggle(entryBuilder, AbilitiesConfig.SHARED_COOLDOWNS));
         abilities.addEntry(addBooleanToggle(entryBuilder, AbilitiesConfig.REMOVE_Y_RESTRICTION));
         abilities.addEntry(addBooleanToggle(entryBuilder, AbilitiesConfig.RANDOMIZED_FRUITS));
         abilities.addEntry(addBooleanToggle(entryBuilder, AbilitiesConfig.COMBAT_STATE_UPDATE_CHAT_MESSAGGE));
         abilities.addEntry(addDoubleField(entryBuilder, AbilitiesConfig.DEVIL_FRUIT_DROP_FROM_LEAVES));
         abilities.addEntry(addBooleanToggle(entryBuilder, AbilitiesConfig.LOGIA_INVULNERABILITY));
         abilities.addEntry(addBooleanToggle(entryBuilder, AbilitiesConfig.LOGIA_RETURN_EFFECT));
         abilities.addEntry(addEnumSelector(entryBuilder, AbilitiesConfig.LOGIA_PROJECTILE_HIT_LOGIC));
         abilities.addEntry(addStringList(entryBuilder, AbilitiesConfig.BANNED_ABILITIES));
         SubCategoryBuilder haki = entryBuilder.startSubCategory(Component.m_237115_(ModI18nConfig.HAKI_CATEGORY)).setExpanded(true);
         haki.add(addEnumSelector(entryBuilder, AbilitiesConfig.HAOSHOKU_HAKI_UNLOCK_LOGIC));
         haki.add(addEnumSelector(entryBuilder, AbilitiesConfig.HAOSHOKU_HAKI_COLORING_LOGIC));
         haki.add(addColorField(entryBuilder, AbilitiesConfig.HAKI_COLOR));
         abilities.addEntry(haki.build());
         SubCategoryBuilder onefruit = entryBuilder.startSubCategory(Component.m_237115_(ModI18nConfig.ONE_FRUIT_CATEGORY)).setExpanded(false);
         onefruit.add(addEnumSelector(entryBuilder, AbilitiesConfig.ONE_FRUIT_PER_WORLD));
         onefruit.add(addBooleanToggle(entryBuilder, AbilitiesConfig.UNABLE_TO_PICKUP_DF));
         onefruit.add(addIntSlider(entryBuilder, AbilitiesConfig.FRUITS_LIMIT_INVENTORY));
         onefruit.add(addIntSlider(entryBuilder, AbilitiesConfig.DAYS_FOR_INACTIVITY));
         onefruit.add(addEnumSelector(entryBuilder, AbilitiesConfig.ONE_FRUIT_PER_WORLD_CHUNK_DELETION));
         abilities.addEntry(onefruit.build());
         SubCategoryBuilder ablprot = entryBuilder.startSubCategory(Component.m_237115_(ModI18nConfig.ABL_PROTECTION_CATEGORY)).setExpanded(false);
         ablprot.add(addStringList(entryBuilder, AbilitiesConfig.GLOBAL_PROTECTION_WHITELIST));
         ablprot.add(addLongField(entryBuilder, AbilitiesConfig.GLOBAL_PROTECTION_RESTORATION_GRACE));
         abilities.addEntry(ablprot.build());
         SubCategoryBuilder dfrespawns = entryBuilder.startSubCategory(Component.m_237115_(ModI18nConfig.DF_RESPAWNS_CATEGORY)).setExpanded(false);
         dfrespawns.add(addIntSlider(entryBuilder, AbilitiesConfig.DROPPED_APPLES_RESPAWN_CHANCE));
         dfrespawns.add(addIntSlider(entryBuilder, AbilitiesConfig.ENTITY_INVENTORY_APPLES_RESPAWN_CHANCE));
         dfrespawns.add(addIntSlider(entryBuilder, AbilitiesConfig.CHESTS_APPLES_RESPAWN_CHANCE));
         abilities.addEntry(dfrespawns.build());
         ConfigCategory factions = builder.getOrCreateCategory(Component.m_237115_(ModI18nConfig.FACTION_CATEGORY));
         factions.addEntry(addBooleanToggle(entryBuilder, FactionsConfig.DISABLE_FRIENDLY_FIRE));
         SubCategoryBuilder bounty = entryBuilder.startSubCategory(Component.m_237115_(ModI18nConfig.BOUNTY_SUB_CATEGORY)).setExpanded(false);
         bounty.add(addIntSlider(entryBuilder, FactionsConfig.TIME_BETWEEN_PACKAGE_DROPS));
         factions.addEntry(bounty.build());
         SubCategoryBuilder crew = entryBuilder.startSubCategory(Component.m_237115_(ModI18nConfig.CREW_SUB_CATEGORY)).setExpanded(false);
         crew.add(addIntField(entryBuilder, FactionsConfig.CREW_BOUNTY_REQUIREMENT));
         crew.add(addBooleanToggle(entryBuilder, FactionsConfig.WORLD_MESSAGE_ON_CREW_CREATE));
         factions.addEntry(crew.build());
         ConfigCategory challenges = builder.getOrCreateCategory(Component.m_237115_(ModI18nConfig.CHALLENGES_CATEGORY));
         challenges.addEntry(addBooleanToggle(entryBuilder, ChallengesConfig.ENABLE_CHALLENGES));
         challenges.addEntry(addBooleanToggle(entryBuilder, ChallengesConfig.CHALLENGE_CACHING));
         challenges.addEntry(addBooleanToggle(entryBuilder, ChallengesConfig.RETURN_TO_SAFETY));
         challenges.addEntry(addIntSlider(entryBuilder, ChallengesConfig.ARENA_CONSTRUCTION_SPEED));
         challenges.addEntry(addIntList(entryBuilder, ChallengesConfig.DORIKI_REWARD_POOL));
         challenges.addEntry(addIntList(entryBuilder, ChallengesConfig.BELLY_REWARD_POOL));
         challenges.addEntry(addIntList(entryBuilder, ChallengesConfig.HAKI_REWARD_POOL));
         ConfigCategory worldEvents = builder.getOrCreateCategory(Component.m_237115_(ModI18nConfig.WORLD_EVENTS_CATEGORY));
         worldEvents.addEntry(addBooleanToggle(entryBuilder, WorldEventsConfig.SPAWN_WORLD_HUMANOIDS));
         worldEvents.addEntry(addBooleanToggle(entryBuilder, WorldEventsConfig.SPAWN_WORLD_ANIMALS));
         SubCategoryBuilder traders = entryBuilder.startSubCategory(Component.m_237115_(ModI18nConfig.TRADERS_SUB_CATEGORY)).setExpanded(false);
         traders.add(addIntSlider(entryBuilder, WorldEventsConfig.TIME_BETWEEN_TRADER_SPAWNS));
         traders.add(addIntSlider(entryBuilder, WorldEventsConfig.SPAWN_CHANCE_TRADER));
         worldEvents.addEntry(traders.build());
         SubCategoryBuilder trainers = entryBuilder.startSubCategory(Component.m_237115_(ModI18nConfig.TRAINERS_SUB_CATEGORY)).setExpanded(false);
         trainers.add(addIntSlider(entryBuilder, WorldEventsConfig.TIME_BETWEEN_TRAINER_SPAWNS));
         trainers.add(addIntSlider(entryBuilder, WorldEventsConfig.SPAWN_CHANCE_TRAINER));
         worldEvents.addEntry(trainers.build());
         SubCategoryBuilder ambushes = entryBuilder.startSubCategory(Component.m_237115_(ModI18nConfig.AMBUSHES_SUB_CATEGORY)).setExpanded(false);
         ambushes.add(addIntSlider(entryBuilder, WorldEventsConfig.TIME_BETWEEN_AMBUSH_SPAWNS));
         ambushes.add(addIntSlider(entryBuilder, WorldEventsConfig.SPAWN_CHANCE_AMBUSH));
         worldEvents.addEntry(ambushes.build());
         SubCategoryBuilder spawnChances = entryBuilder.startSubCategory(Component.m_237115_(ModI18nConfig.SPAWN_CHANCES_SUB_CATEGORY)).setExpanded(false);
         spawnChances.add(addDoubleField(entryBuilder, WorldEventsConfig.GRUNT_SPAWN_CHANCE));
         spawnChances.add(addDoubleField(entryBuilder, WorldEventsConfig.BRUTE_SPAWN_CHANCE));
         spawnChances.add(addDoubleField(entryBuilder, WorldEventsConfig.SNIPER_SPAWN_CHANCE));
         spawnChances.add(addDoubleField(entryBuilder, WorldEventsConfig.CAPTAIN_SPAWN_CHANCE));
         spawnChances.add(addDoubleField(entryBuilder, WorldEventsConfig.PACIFISTA_SPAWN_CHANCE));
         worldEvents.addEntry(spawnChances.build());
         ConfigCategory worldFeatures = builder.getOrCreateCategory(Component.m_237115_(ModI18nConfig.STRUCTURES_CATEGORY));
         worldFeatures.addEntry(addBooleanToggle(entryBuilder, WorldFeaturesConfig.SPAWN_BIOMES));
         worldFeatures.addEntry(addIntSlider(entryBuilder, WorldFeaturesConfig.SPAWN_CHANCE_TRAINING_STRUCTURES));
         worldFeatures.addEntry(addIntSlider(entryBuilder, WorldFeaturesConfig.SPAWN_CHANCE_SKY_ISLANDS));
         worldFeatures.addEntry(addIntSlider(entryBuilder, WorldFeaturesConfig.SPAWN_CHANCE_GHOST_SHIPS));
         worldFeatures.addEntry(addIntSlider(entryBuilder, WorldFeaturesConfig.SPAWN_CHANCE_SMALL_SHIPS));
         worldFeatures.addEntry(addIntSlider(entryBuilder, WorldFeaturesConfig.SPAWN_CHANCE_MEDIUM_SHIPS));
         worldFeatures.addEntry(addIntSlider(entryBuilder, WorldFeaturesConfig.SPAWN_CHANCE_LARGE_SHIPS));
         worldFeatures.addEntry(addIntSlider(entryBuilder, WorldFeaturesConfig.SPAWN_CHANCE_CAMPS));
         worldFeatures.addEntry(addIntSlider(entryBuilder, WorldFeaturesConfig.SPAWN_CHANCE_SMALL_BASES));
         worldFeatures.addEntry(addIntSlider(entryBuilder, WorldFeaturesConfig.SPAWN_CHANCE_LARGE_BASES));
         worldFeatures.addEntry(addIntSlider(entryBuilder, WorldFeaturesConfig.SPAWN_CHANCE_WATCH_TOWERS));
         worldFeatures.addEntry(addIntSlider(entryBuilder, WorldFeaturesConfig.SPAWN_CHANCE_PONEGLYPHS));
         worldFeatures.addEntry(addIntSlider(entryBuilder, WorldFeaturesConfig.KAIROSEKI_SPAWN_COUNT));
         ConfigCategory ui = builder.getOrCreateCategory(Component.m_237115_(ModI18nConfig.UI_CATEGORY));
         ui.addEntry(addIntSlider(entryBuilder, UIConfig.ABILITY_BARS_ON_SCREEN));
         ui.addEntry(addBooleanToggle(entryBuilder, UIConfig.SHOW_KEYBIND));
         ui.addEntry(addBooleanToggle(entryBuilder, UIConfig.MERGE_ABILITY_BONUSES));
         ui.addEntry(addBooleanToggle(entryBuilder, UIConfig.HIDE_ABILITY_STATS));
         ui.addEntry(addBooleanToggle(entryBuilder, UIConfig.SIMPLE_DISPLAYS));
         ui.addEntry(addBooleanToggle(entryBuilder, UIConfig.USE_HEARTS_BAR));
         ui.addEntry(addEnumSelector(entryBuilder, UIConfig.SLOT_NUMBER_DISPLAY));
         ui.addEntry(addEnumSelector(entryBuilder, UIConfig.ABILITY_LIST_COMPACTNESS));
         ui.addEntry(addEnumSelector(entryBuilder, UIConfig.ABILITY_LIST_SELECTION));
         ui.addEntry(addBooleanToggle(entryBuilder, UIConfig.ABILITY_LIST_SHOW_TOOLTIPS));
         ui.addEntry(addBooleanToggle(entryBuilder, UIConfig.ABILITY_LIST_SHOW_HELP));
      }

      ConfigCategory system = builder.getOrCreateCategory(Component.m_237115_(ModI18nConfig.SYSTEM_CATEGORY));
      system.addEntry(addBooleanToggle(entryBuilder, SystemConfig.MASTER_COMMAND));
      system.addEntry(addBooleanToggle(entryBuilder, SystemConfig.UPDATE_MESSAGE));
      system.addEntry(addBooleanToggle(entryBuilder, SystemConfig.MOD_SPLASH_TEXT));
      system.addEntry(addBooleanToggle(entryBuilder, SystemConfig.BLUE_GORO));
      system.addEntry(addBooleanToggle(entryBuilder, SystemConfig.EXPERIMENTAL_TIMERS));
      builder.setParentScreen(parent);
      return builder.build();
   };

   public static void registerModConfig(FMLJavaModLoadingContext ctx) {
      ctx.registerExtensionPoint(ConfigScreenHandler.ConfigScreenFactory.class, () -> new ConfigScreenHandler.ConfigScreenFactory(CONFIG_FACTORY));
   }

   private static ColorEntry addColorField(ConfigEntryBuilder entryBuilder, ColorOption option) {
      int color = WyHelper.hexToRGB((String)option.getValue().get()).getRGB();
      return entryBuilder.startColorField(option.getTitleComponent(), Color.ofOpaque(color)).setDefaultValue(16711680).setTooltip(option.getDescriptionComponent()).setSaveConsumer((o) -> option.saveColor(Color.ofOpaque(o))).build();
   }

   private static StringListListEntry addStringList(ConfigEntryBuilder entryBuilder, StringListOption option) {
      return entryBuilder.startStrList(option.getTitleComponent(), (List)option.getValue().get()).setDefaultValue(option.getDefault()).setTooltip(option.getDescriptionComponent()).setSaveConsumer((o) -> option.getValue().set(o)).build();
   }

   private static IntegerListListEntry addIntList(ConfigEntryBuilder entryBuilder, IntegerListOption option) {
      return entryBuilder.startIntList(option.getTitleComponent(), (List)option.getValue().get()).setDefaultValue(option.getDefault()).setTooltip(option.getDescriptionComponent()).setSaveConsumer((o) -> option.getValue().set(o)).build();
   }

   private static DropdownBoxEntry addItemDropdown(ConfigEntryBuilder entryBuilder) {
      DropdownBoxEntry.SelectionTopCellElement topCell = TopCellElementBuilder.ofItemObject(Items.f_42410_);
      return entryBuilder.startDropdownMenu(Component.m_237113_("Title"), topCell, CellCreatorBuilder.ofItemObject()).setDefaultValue((Supplier)null).setSelections((Iterable)ForgeRegistries.ITEMS.getValues().stream().sorted(Comparator.comparing(Item::toString)).collect(Collectors.toCollection(LinkedHashSet::new))).setSaveConsumer((item) -> System.out.println("save this " + String.valueOf(item))).build();
   }

   private static <T extends Enum<T>> EnumListEntry<T> addEnumSelector(ConfigEntryBuilder entryBuilder, EnumOption<T> option) {
      return entryBuilder.startEnumSelector(option.getTitleComponent(), option.getEnumClass(), (Enum)option.getValue().get()).setTooltip(option.getDescriptionComponent()).setSaveConsumer((o) -> option.getValue().set(o)).setDefaultValue((Enum)option.getDefault()).build();
   }

   private static BooleanListEntry addBooleanToggle(ConfigEntryBuilder entryBuilder, BooleanOption option) {
      return ((BooleanToggleBuilder)entryBuilder.startBooleanToggle(option.getTitleComponent(), (Boolean)option.getValue().get()).setTooltip(option.getDescriptionComponent()).setSaveConsumer((o) -> option.getValue().set(o)).setDefaultValue((Boolean)option.getDefault())).build();
   }

   private static DoubleListEntry addDoubleField(ConfigEntryBuilder entryBuilder, DoubleOption option) {
      return ((DoubleFieldBuilder)((DoubleFieldBuilder)((DoubleFieldBuilder)entryBuilder.startDoubleField(option.getTitleComponent(), (Double)option.getValue().get()).setTooltip(option.getDescriptionComponent()).setSaveConsumer((o) -> option.getValue().set(o)).setMin((Double)option.getMin())).setMax((Double)option.getMax())).setDefaultValue((Double)option.getDefault())).build();
   }

   private static IntegerListEntry addIntField(ConfigEntryBuilder entryBuilder, IntegerOption option) {
      return ((IntFieldBuilder)((IntFieldBuilder)((IntFieldBuilder)entryBuilder.startIntField(option.getTitleComponent(), (Integer)option.getValue().get()).setTooltip(option.getDescriptionComponent()).setSaveConsumer((o) -> option.getValue().set(o)).setMin((Integer)option.getMin())).setMax((Integer)option.getMax())).setDefaultValue((Integer)option.getDefault())).build();
   }

   private static IntegerSliderEntry addIntSlider(ConfigEntryBuilder entryBuilder, IntegerOption option) {
      return ((IntSliderBuilder)((IntSliderBuilder)((IntSliderBuilder)entryBuilder.startIntSlider(option.getTitleComponent(), (Integer)option.getValue().get(), (Integer)option.getMin(), (Integer)option.getMax()).setTooltip(option.getDescriptionComponent()).setSaveConsumer((o) -> option.getValue().set(o)).setMin((Integer)option.getMin())).setMax((Integer)option.getMax())).setDefaultValue((Integer)option.getDefault())).build();
   }

   private static LongListEntry addLongField(ConfigEntryBuilder entryBuilder, LongOption option) {
      return ((LongFieldBuilder)((LongFieldBuilder)((LongFieldBuilder)entryBuilder.startLongField(option.getTitleComponent(), (Long)option.getValue().get()).setTooltip(option.getDescriptionComponent()).setSaveConsumer((o) -> option.getValue().set(o)).setMin((Long)option.getMin())).setMax((Long)option.getMax())).setDefaultValue((Long)option.getDefault())).build();
   }
}
