package xyz.pixelatedw.mineminenomi.config;

import xyz.pixelatedw.mineminenomi.api.config.options.BooleanOption;
import xyz.pixelatedw.mineminenomi.api.config.options.EnumOption;
import xyz.pixelatedw.mineminenomi.api.config.options.IntegerOption;
import xyz.pixelatedw.mineminenomi.ui.screens.AbilitiesListScreen;

public class UIConfig {
   public static final BooleanOption DISPLAY_IN_SECONDS = new BooleanOption(false, "Display in Seconds", "Displays the cooldown, charge up time and any other number displayed in the ability icon as seconds instead of ticks\nDefault: false");
   public static final IntegerOption ABILITY_BARS_ON_SCREEN = new IntegerOption(1, 1, 3, "Ability Bars on Screen", "How many ability bars can be displayed on the user's screen, this is limited by the server's amount of ability bars a user can have, it cannot exceed that\nDefault: 1");
   public static final BooleanOption SHOW_KEYBIND = new BooleanOption(true, "Show Keybind", "Displays the keybind associated with the slot as text in the bottom-left part of it\nDefault: true");
   public static final BooleanOption MERGE_ABILITY_BONUSES = new BooleanOption(false, "Merge Ability Bonuses", "Merge the bonuses shown in the ability tooltip with the original value\nFor example a default damage of 2 with a bonus of 2 would be displayed as 4, instead of 2 (+2)\nDefault: true");
   public static final BooleanOption SIMPLE_DISPLAYS = new BooleanOption(false, "Simple Displays", "Simplifies some UI elements down to numbers instead of visuals, can provide help for people with color blindness for example.\nDefault: false");
   public static final BooleanOption USE_HEARTS_BAR = new BooleanOption(true, "Use Mod Hearts UI", "Use the mod's hearts bar showing only 1 line of hearts with the HP number on the right\nDefault: true");
   public static final BooleanOption HIDE_ABILITY_STATS = new BooleanOption(false, "Hide Ability Stats", "Hides all the ability stats by default, reveals then while holding the SHIFT key.\nDefault: false");
   public static final EnumOption<AbilitiesListScreen.Compactness> ABILITY_LIST_COMPACTNESS;
   public static final EnumOption<AbilitiesListScreen.Selection> ABILITY_LIST_SELECTION;
   public static final BooleanOption ABILITY_LIST_SHOW_TOOLTIPS;
   public static final BooleanOption ABILITY_LIST_SHOW_HELP;
   public static final EnumOption<ClientConfig.SlotNumberVisuals> SLOT_NUMBER_DISPLAY;

   static {
      ABILITY_LIST_COMPACTNESS = new EnumOption<AbilitiesListScreen.Compactness>(AbilitiesListScreen.Compactness.SPATIOUS, AbilitiesListScreen.Compactness.values(), "Ability List Compactness", "How close the abilities are from each other in the selection list.\nDefault: SPATIOUS");
      ABILITY_LIST_SELECTION = new EnumOption<AbilitiesListScreen.Selection>(AbilitiesListScreen.Selection.DRAG_AND_DROP, AbilitiesListScreen.Selection.values(), "Ability List Selection Mode", "Which selection mode to use.\nDefault: DRAG_AND_DROP");
      ABILITY_LIST_SHOW_TOOLTIPS = new BooleanOption(true, "Ability List Show Tooltips", "Shows the ability tooltips when hovering them\nDefault: true");
      ABILITY_LIST_SHOW_HELP = new BooleanOption(true, "Ability List Show Help", "Shows the help button\nDefault: true");
      SLOT_NUMBER_DISPLAY = new EnumOption<ClientConfig.SlotNumberVisuals>(ClientConfig.SlotNumberVisuals.SECONDS, ClientConfig.SlotNumberVisuals.values(), "Slot Number Display", "Changes how slow numbers are displayed (such as for cooldown or charging)\nDefault: TICKS");
   }
}
