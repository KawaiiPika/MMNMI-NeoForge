package xyz.pixelatedw.mineminenomi.config;

import xyz.pixelatedw.mineminenomi.api.config.options.BooleanOption;

public class SystemConfig {
   public static final BooleanOption MASTER_COMMAND = new BooleanOption(false, "Master Command", "Merges all the commands added by this mod under a generic /mmnm command, used for compatibility reasons in case some other mod adds similarly named commands\nDefault: false");
   public static final BooleanOption ENABLE_PERMISSIONS = new BooleanOption(false, "Enable Permissions", "Allows the usage of bukkit style permissions for certain mod features. Will override the default checks based on vanilla's 1-4 levels of permissions but might also bypass some mod specific requirements.\nDefault: false");
   public static final BooleanOption UPDATE_MESSAGE = new BooleanOption(true, "Update Message", "Allows the game to show a text message when the installed mod is outdated\nDefault: true");
   public static final BooleanOption MOD_SPLASH_TEXT = new BooleanOption(true, "Mod Splash Text", "Allows the game to show mod specific splash texts on the main menu\nDefault: true");
   public static final BooleanOption EXPERIMENTAL_TIMERS = new BooleanOption(true, "Experimental Timers", "Replaces some of the timers in the mod with real life based timers, could slightly improve the accuracy of some, however on large servers with big TPS spikes it could also have the opposite effect\nDefault: true");
   public static final BooleanOption BLUE_GORO = new BooleanOption(false, "Blue Goro", "Turns Goro Goro no Mi, its abilities and special effects blue\nRestarting the game is recommended after changing this option\nDefault: false");
}
