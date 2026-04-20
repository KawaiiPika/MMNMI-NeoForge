package xyz.pixelatedw.mineminenomi.config;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;
import net.minecraftforge.common.ForgeConfigSpec;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.config.IConfigEnum;
import xyz.pixelatedw.mineminenomi.ui.screens.AbilitiesListScreen;

public class ClientConfig {
   public static final ForgeConfigSpec SPEC;
   private static Map<String, ForgeConfigSpec.BooleanValue> cooldownVisual;
   private static ForgeConfigSpec.IntValue onFireVisibility;
   private static ForgeConfigSpec.BooleanValue alwaysLegUp;

   private static void setupConfig(ForgeConfigSpec.Builder builder) {
      builder.push("General");
      onFireVisibility = builder.comment("Visibility when on fire while using a fire resistant fruit \nDefault: 20").defineInRange("Fire Visibility", 25, 0, 100);
      AbilitiesConfig.HAKI_COLOR.createValue(builder);
      alwaysLegUp = builder.comment("Always hold the leg up for combat as a Black Leg user\nDefault: true").define("Black Leg Always Up", true);
      builder.push("Cooldown Visuals");
      String[] cooldownVisuals = new String[]{"Text", "Color"};
      cooldownVisual = new HashMap();

      for(String mode : cooldownVisuals) {
         cooldownVisual.put(mode, builder.define(mode, true));
      }

      AbilitiesConfig.COMBAT_STATE_UPDATE_CHAT_MESSAGGE.createValue(builder);
      builder.pop();
      builder.pop();
      builder.push("UI");
      UIConfig.DISPLAY_IN_SECONDS.createValue(builder);
      UIConfig.ABILITY_BARS_ON_SCREEN.createValue(builder);
      UIConfig.SHOW_KEYBIND.createValue(builder);
      UIConfig.MERGE_ABILITY_BONUSES.createValue(builder);
      UIConfig.SIMPLE_DISPLAYS.createValue(builder);
      UIConfig.USE_HEARTS_BAR.createValue(builder);
      UIConfig.SLOT_NUMBER_DISPLAY.createValue(builder);
      UIConfig.HIDE_ABILITY_STATS.createValue(builder);
      builder.push("Ability List");
      UIConfig.ABILITY_LIST_COMPACTNESS.createValue(builder);
      UIConfig.ABILITY_LIST_SELECTION.createValue(builder);
      UIConfig.ABILITY_LIST_SHOW_TOOLTIPS.createValue(builder);
      UIConfig.ABILITY_LIST_SHOW_HELP.createValue(builder);
      builder.pop();
      builder.push("System");
      SystemConfig.UPDATE_MESSAGE.createValue(builder);
      SystemConfig.MOD_SPLASH_TEXT.createValue(builder);
      SystemConfig.BLUE_GORO.createValue(builder);
      builder.pop();
   }

   public static void toggleHelpButton() {
      boolean flag = !(Boolean)UIConfig.ABILITY_LIST_SHOW_HELP.get();
      UIConfig.ABILITY_LIST_SHOW_HELP.getValue().set(flag);
      SPEC.save();
   }

   public static boolean getHelpButtonShown() {
      return (Boolean)UIConfig.ABILITY_LIST_SHOW_HELP.get();
   }

   public static void toggleShowingTooltips() {
      boolean flag = !(Boolean)UIConfig.ABILITY_LIST_SHOW_TOOLTIPS.get();
      UIConfig.ABILITY_LIST_SHOW_TOOLTIPS.getValue().set(flag);
      SPEC.save();
   }

   public static boolean getTooltipsShown() {
      return (Boolean)UIConfig.ABILITY_LIST_SHOW_TOOLTIPS.get();
   }

   public static void setSelectionMode(AbilitiesListScreen.Selection option) {
      UIConfig.ABILITY_LIST_SELECTION.getValue().set(option);
      SPEC.save();
   }

   public static AbilitiesListScreen.Selection getSelectionMode() {
      return (AbilitiesListScreen.Selection)UIConfig.ABILITY_LIST_SELECTION.get();
   }

   public static void setCompactness(AbilitiesListScreen.Compactness option) {
      UIConfig.ABILITY_LIST_COMPACTNESS.getValue().set(option);
      SPEC.save();
   }

   public static AbilitiesListScreen.Compactness getCompactness() {
      return (AbilitiesListScreen.Compactness)UIConfig.ABILITY_LIST_COMPACTNESS.get();
   }

   public static boolean isGoroBlue() {
      return (Boolean)SystemConfig.BLUE_GORO.get();
   }

   public static boolean isDisplayInTicks() {
      return UIConfig.SLOT_NUMBER_DISPLAY.get() == ClientConfig.SlotNumberVisuals.TICKS;
   }

   public static boolean isDisplayInSeconds() {
      return UIConfig.SLOT_NUMBER_DISPLAY.get() == ClientConfig.SlotNumberVisuals.SECONDS;
   }

   public static boolean isDisplayInPercentage() {
      return UIConfig.SLOT_NUMBER_DISPLAY.get() == ClientConfig.SlotNumberVisuals.PERCENTAGE;
   }

   public static boolean hidesAbilityStats() {
      return (Boolean)UIConfig.HIDE_ABILITY_STATS.get();
   }

   public static boolean hasCombatUpdateChatMessageEnabled() {
      return (Boolean)AbilitiesConfig.COMBAT_STATE_UPDATE_CHAT_MESSAGGE.get();
   }

   public static boolean isSimpleDisplaysEnabled() {
      return (Boolean)UIConfig.SIMPLE_DISPLAYS.get();
   }

   public static boolean hasHeartsUI() {
      return !(Boolean)GeneralConfig.EXTRA_HEARTS.get() ? false : (Boolean)UIConfig.USE_HEARTS_BAR.get();
   }

   public static boolean isAbilityBonusesMergeEnable() {
      return (Boolean)UIConfig.MERGE_ABILITY_BONUSES.get();
   }

   public static boolean isModSplashTextEnabled() {
      return (Boolean)SystemConfig.MOD_SPLASH_TEXT.get();
   }

   public static boolean isBlackLegAlwaysUp() {
      return (Boolean)alwaysLegUp.get();
   }

   public static Color getHakiColor() {
      Color color = new Color(16711680);

      try {
         String c = (String)AbilitiesConfig.HAKI_COLOR.get();
         if (c.startsWith("#")) {
            color = WyHelper.hexToRGB(c);
         } else {
            int n = Integer.parseInt(c);
            if (n < 0 || n > 16777215) {
               throw new Exception("Haki Color outside its bounds: " + n + " Can only use numbers between 0 and 16777215");
            }

            color = new Color(n);
         }
      } catch (Exception e) {
         e.printStackTrace();
      }

      return color;
   }

   public static boolean showSlotKeybinds() {
      return (Boolean)UIConfig.SHOW_KEYBIND.get();
   }

   public static int getAbilityBarsOnScreen() {
      return (Integer)UIConfig.ABILITY_BARS_ON_SCREEN.get();
   }

   public static boolean hasTextCooldownVisuals() {
      return (Boolean)((ForgeConfigSpec.BooleanValue)cooldownVisual.get("Text")).get();
   }

   public static boolean hasColorCooldownVisuals() {
      return (Boolean)((ForgeConfigSpec.BooleanValue)cooldownVisual.get("Color")).get();
   }

   public static int getFireVisibility() {
      return (Integer)onFireVisibility.get();
   }

   static {
      ForgeConfigSpec.Builder configBuilder = new ForgeConfigSpec.Builder();
      setupConfig(configBuilder);
      SPEC = configBuilder.build();
   }

   public static enum SlotNumberVisuals implements IConfigEnum {
      TICKS,
      SECONDS,
      PERCENTAGE;

      public <T extends IConfigEnum> T next() {
         return (T)values()[(this.ordinal() + 1) % values().length];
      }

      // $FF: synthetic method
      private static SlotNumberVisuals[] $values() {
         return new SlotNumberVisuals[]{TICKS, SECONDS, PERCENTAGE};
      }
   }
}
