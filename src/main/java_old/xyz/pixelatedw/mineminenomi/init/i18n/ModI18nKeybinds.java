package xyz.pixelatedw.mineminenomi.init.i18n;

import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class ModI18nKeybinds {
   public static final String CATEGORY_GENERAL;
   public static final String CATEGORY_COMBATBAR;
   public static final String KEY_PLAYER;
   public static final String KEY_COMBATMODE;
   public static final String KEY_NEXT_COMBAT_BAR;
   public static final String KEY_PREV_COMBAT_BAR;
   public static final String KEY_LAST_COMBAT_BAR;
   public static final String KEY_CHANGE_ABILITY_MODE;
   public static final String KEY_VEHICLE_ALT_MODE;
   public static final String KEY_OPEN_ABILITIES;
   public static final String KEY_OPEN_CHALLENGES;
   public static final String KEY_OPEN_QUESTS;
   public static final String KEY_OPEN_CREW;
   public static final String KEY_OPEN_ABILITY_TREE;
   public static final String KEY_BAR1_COMBATSLOT1;
   public static final String KEY_BAR1_COMBATSLOT2;
   public static final String KEY_BAR1_COMBATSLOT3;
   public static final String KEY_BAR1_COMBATSLOT4;
   public static final String KEY_BAR1_COMBATSLOT5;
   public static final String KEY_BAR1_COMBATSLOT6;
   public static final String KEY_BAR1_COMBATSLOT7;
   public static final String KEY_BAR1_COMBATSLOT8;
   public static final String KEY_BAR2_COMBATSLOT1;
   public static final String KEY_BAR2_COMBATSLOT2;
   public static final String KEY_BAR2_COMBATSLOT3;
   public static final String KEY_BAR2_COMBATSLOT4;
   public static final String KEY_BAR2_COMBATSLOT5;
   public static final String KEY_BAR2_COMBATSLOT6;
   public static final String KEY_BAR2_COMBATSLOT7;
   public static final String KEY_BAR2_COMBATSLOT8;
   public static final String KEY_BAR3_COMBATSLOT1;
   public static final String KEY_BAR3_COMBATSLOT2;
   public static final String KEY_BAR3_COMBATSLOT3;
   public static final String KEY_BAR3_COMBATSLOT4;
   public static final String KEY_BAR3_COMBATSLOT5;
   public static final String KEY_BAR3_COMBATSLOT6;
   public static final String KEY_BAR3_COMBATSLOT7;
   public static final String KEY_BAR3_COMBATSLOT8;
   public static final String KEY_BAR_SHORTCUT1;
   public static final String KEY_BAR_SHORTCUT2;
   public static final String KEY_BAR_SHORTCUT3;
   public static final String KEY_BAR_SHORTCUT4;
   public static final String KEY_BAR_SHORTCUT5;
   public static final String KEY_BAR_SHORTCUT6;
   public static final String KEY_BAR_SHORTCUT7;
   public static final String KEY_BAR_SHORTCUT8;
   public static final String KEY_BAR_SHORTCUT9;
   public static final String KEY_BAR_SHORTCUT10;

   public static void init() {
   }

   static {
      CATEGORY_GENERAL = ModRegistry.registerName(ModRegistry.I18nCategory.KEYBIND_CATEGORY, "generic", "Mine Mine no Mi - Keys");
      CATEGORY_COMBATBAR = ModRegistry.registerName(ModRegistry.I18nCategory.KEYBIND_CATEGORY, "combat_bar", "Mine Mine no Mi - Combat Bar");
      KEY_PLAYER = ModRegistry.registerName(ModRegistry.I18nCategory.KEYBIND, "player_stats", "Player Stats");
      KEY_COMBATMODE = ModRegistry.registerName(ModRegistry.I18nCategory.KEYBIND, "change_combat_mode", "Combat Mode");
      KEY_NEXT_COMBAT_BAR = ModRegistry.registerName(ModRegistry.I18nCategory.KEYBIND, "next_combat_bar", "Next Combat Bar");
      KEY_PREV_COMBAT_BAR = ModRegistry.registerName(ModRegistry.I18nCategory.KEYBIND, "previous_combat_bar", "Previous Combat Bar");
      KEY_LAST_COMBAT_BAR = ModRegistry.registerName(ModRegistry.I18nCategory.KEYBIND, "last_combat_bar", "Last Combat Bar");
      KEY_CHANGE_ABILITY_MODE = ModRegistry.registerName(ModRegistry.I18nCategory.KEYBIND, "change_ability_mode", "Change Ability Mode");
      KEY_VEHICLE_ALT_MODE = ModRegistry.registerName(ModRegistry.I18nCategory.KEYBIND, "change_vehicle_mode", "Toggle Vehicle Mode");
      KEY_OPEN_ABILITIES = ModRegistry.registerName(ModRegistry.I18nCategory.KEYBIND, "open_abilities_menu", "Open Abilities Menu");
      KEY_OPEN_CHALLENGES = ModRegistry.registerName(ModRegistry.I18nCategory.KEYBIND, "open_challenges_menu", "Open Challenges Menu");
      KEY_OPEN_QUESTS = ModRegistry.registerName(ModRegistry.I18nCategory.KEYBIND, "open_quests_menu", "Open Quests Menu");
      KEY_OPEN_CREW = ModRegistry.registerName(ModRegistry.I18nCategory.KEYBIND, "open_crew_menu", "Open Crew Menu");
      KEY_OPEN_ABILITY_TREE = ModRegistry.registerName(ModRegistry.I18nCategory.KEYBIND, "open_ability_tree_menu", "Open Ability Tree Menu");
      KEY_BAR1_COMBATSLOT1 = ModRegistry.registerName(ModRegistry.I18nCategory.KEYBIND, "combat_bar.1.slot.1", "Bar 1 - Ability 1");
      KEY_BAR1_COMBATSLOT2 = ModRegistry.registerName(ModRegistry.I18nCategory.KEYBIND, "combat_bar.1.slot.2", "Bar 1 - Ability 2");
      KEY_BAR1_COMBATSLOT3 = ModRegistry.registerName(ModRegistry.I18nCategory.KEYBIND, "combat_bar.1.slot.3", "Bar 1 - Ability 3");
      KEY_BAR1_COMBATSLOT4 = ModRegistry.registerName(ModRegistry.I18nCategory.KEYBIND, "combat_bar.1.slot.4", "Bar 1 - Ability 4");
      KEY_BAR1_COMBATSLOT5 = ModRegistry.registerName(ModRegistry.I18nCategory.KEYBIND, "combat_bar.1.slot.5", "Bar 1 - Ability 5");
      KEY_BAR1_COMBATSLOT6 = ModRegistry.registerName(ModRegistry.I18nCategory.KEYBIND, "combat_bar.1.slot.6", "Bar 1 - Ability 6");
      KEY_BAR1_COMBATSLOT7 = ModRegistry.registerName(ModRegistry.I18nCategory.KEYBIND, "combat_bar.1.slot.7", "Bar 1 - Ability 7");
      KEY_BAR1_COMBATSLOT8 = ModRegistry.registerName(ModRegistry.I18nCategory.KEYBIND, "combat_bar.1.slot.8", "Bar 1 - Ability 8");
      KEY_BAR2_COMBATSLOT1 = ModRegistry.registerName(ModRegistry.I18nCategory.KEYBIND, "combat_bar.2.slot.1", "Bar 2 - Ability 1");
      KEY_BAR2_COMBATSLOT2 = ModRegistry.registerName(ModRegistry.I18nCategory.KEYBIND, "combat_bar.2.slot.2", "Bar 2 - Ability 2");
      KEY_BAR2_COMBATSLOT3 = ModRegistry.registerName(ModRegistry.I18nCategory.KEYBIND, "combat_bar.2.slot.3", "Bar 2 - Ability 3");
      KEY_BAR2_COMBATSLOT4 = ModRegistry.registerName(ModRegistry.I18nCategory.KEYBIND, "combat_bar.2.slot.4", "Bar 2 - Ability 4");
      KEY_BAR2_COMBATSLOT5 = ModRegistry.registerName(ModRegistry.I18nCategory.KEYBIND, "combat_bar.2.slot.5", "Bar 2 - Ability 5");
      KEY_BAR2_COMBATSLOT6 = ModRegistry.registerName(ModRegistry.I18nCategory.KEYBIND, "combat_bar.2.slot.6", "Bar 2 - Ability 6");
      KEY_BAR2_COMBATSLOT7 = ModRegistry.registerName(ModRegistry.I18nCategory.KEYBIND, "combat_bar.2.slot.7", "Bar 2 - Ability 7");
      KEY_BAR2_COMBATSLOT8 = ModRegistry.registerName(ModRegistry.I18nCategory.KEYBIND, "combat_bar.2.slot.8", "Bar 2 - Ability 8");
      KEY_BAR3_COMBATSLOT1 = ModRegistry.registerName(ModRegistry.I18nCategory.KEYBIND, "combat_bar.3.slot.1", "Bar 3 - Ability 1");
      KEY_BAR3_COMBATSLOT2 = ModRegistry.registerName(ModRegistry.I18nCategory.KEYBIND, "combat_bar.3.slot.2", "Bar 3 - Ability 2");
      KEY_BAR3_COMBATSLOT3 = ModRegistry.registerName(ModRegistry.I18nCategory.KEYBIND, "combat_bar.3.slot.3", "Bar 3 - Ability 3");
      KEY_BAR3_COMBATSLOT4 = ModRegistry.registerName(ModRegistry.I18nCategory.KEYBIND, "combat_bar.3.slot.4", "Bar 3 - Ability 4");
      KEY_BAR3_COMBATSLOT5 = ModRegistry.registerName(ModRegistry.I18nCategory.KEYBIND, "combat_bar.3.slot.5", "Bar 3 - Ability 5");
      KEY_BAR3_COMBATSLOT6 = ModRegistry.registerName(ModRegistry.I18nCategory.KEYBIND, "combat_bar.3.slot.6", "Bar 3 - Ability 6");
      KEY_BAR3_COMBATSLOT7 = ModRegistry.registerName(ModRegistry.I18nCategory.KEYBIND, "combat_bar.3.slot.7", "Bar 3 - Ability 7");
      KEY_BAR3_COMBATSLOT8 = ModRegistry.registerName(ModRegistry.I18nCategory.KEYBIND, "combat_bar.3.slot.8", "Bar 3 - Ability 8");
      KEY_BAR_SHORTCUT1 = ModRegistry.registerName(ModRegistry.I18nCategory.KEYBIND, "combat_bar_shortcut.1", "Move to Combat Bar Set 1");
      KEY_BAR_SHORTCUT2 = ModRegistry.registerName(ModRegistry.I18nCategory.KEYBIND, "combat_bar_shortcut.2", "Move to Combat Bar Set 2");
      KEY_BAR_SHORTCUT3 = ModRegistry.registerName(ModRegistry.I18nCategory.KEYBIND, "combat_bar_shortcut.3", "Move to Combat Bar Set 3");
      KEY_BAR_SHORTCUT4 = ModRegistry.registerName(ModRegistry.I18nCategory.KEYBIND, "combat_bar_shortcut.4", "Move to Combat Bar Set 4");
      KEY_BAR_SHORTCUT5 = ModRegistry.registerName(ModRegistry.I18nCategory.KEYBIND, "combat_bar_shortcut.5", "Move to Combat Bar Set 5");
      KEY_BAR_SHORTCUT6 = ModRegistry.registerName(ModRegistry.I18nCategory.KEYBIND, "combat_bar_shortcut.6", "Move to Combat Bar Set 6");
      KEY_BAR_SHORTCUT7 = ModRegistry.registerName(ModRegistry.I18nCategory.KEYBIND, "combat_bar_shortcut.7", "Move to Combat Bar Set 7");
      KEY_BAR_SHORTCUT8 = ModRegistry.registerName(ModRegistry.I18nCategory.KEYBIND, "combat_bar_shortcut.8", "Move to Combat Bar Set 8");
      KEY_BAR_SHORTCUT9 = ModRegistry.registerName(ModRegistry.I18nCategory.KEYBIND, "combat_bar_shortcut.9", "Move to Combat Bar Set 9");
      KEY_BAR_SHORTCUT10 = ModRegistry.registerName(ModRegistry.I18nCategory.KEYBIND, "combat_bar_shortcut.10", "Move to Combat Bar Set 10");
   }
}
