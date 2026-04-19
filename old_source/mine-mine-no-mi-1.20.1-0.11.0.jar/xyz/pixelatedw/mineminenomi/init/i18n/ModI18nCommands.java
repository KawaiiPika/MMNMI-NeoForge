package xyz.pixelatedw.mineminenomi.init.i18n;

import net.minecraft.network.chat.Component;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class ModI18nCommands {
   public static final Component ISSUEBOUNTY_REQUIREMENTS;
   public static final Component ISSUEBOUNTY_ONLY_UP;
   public static final Component ISSUEBOUNTY_NOT_ENOUGH_BELLY;
   public static final Component ISSUEBOUNTY_TARGET_REQUIREMENTS;
   public static final String ISSUEBOUNTY_SUCCESS;
   public static final Component POUCH_INVENTORY_FULL;
   public static final Component CHECK_FRUIT_OFPW_ONLY;
   public static final Component CHECK_FRUIT_ERROR_EXPORTING;
   public static final String DAMAGE_MULTIPLIER_SET;
   public static final Component CREW_NO_CREW_FOUND;
   public static final Component CREW_DELETE_ALL_CONFIRM;
   public static final Component CREW_DELETED_ALL_CREWS;
   public static final Component CREW_DELETED_CREW;
   public static final Component CREW_CREATED_CREW;
   public static final Component CREW_PLAYER_NO_PIRATE;
   public static final Component CREW_PLAYER_ADDED_TO_CREW;
   public static final Component CREW_PLAYER_REMOVED_FROM_CREW;
   public static final Component CREW_CAPTAIN_CHANGE;
   public static final Component CREW_ALREADY_IN_CREW;
   public static final Component CREW_NOT_IN_CREW;
   public static final Component CREW_NAME_ALREADY_EXISTS;

   public static void init() {
   }

   static {
      ISSUEBOUNTY_REQUIREMENTS = Component.m_237115_(ModRegistry.registerName(ModRegistry.I18nCategory.COMMAND, "issuebounty.requirements", "Only Marines with above 4000 doriki can use this command!"));
      ISSUEBOUNTY_ONLY_UP = Component.m_237115_(ModRegistry.registerName(ModRegistry.I18nCategory.COMMAND, "issuebounty.only_up", "Bounties can only be increased!"));
      ISSUEBOUNTY_NOT_ENOUGH_BELLY = Component.m_237115_(ModRegistry.registerName(ModRegistry.I18nCategory.COMMAND, "issuebounty.not_enough_belly", "You don't have enough belly"));
      ISSUEBOUNTY_TARGET_REQUIREMENTS = Component.m_237115_(ModRegistry.registerName(ModRegistry.I18nCategory.COMMAND, "issuebounty.target_requirements", "Bounties can only be issued for Pirates, Bandits or Revolutionaries!"));
      ISSUEBOUNTY_SUCCESS = ModRegistry.registerName(ModRegistry.I18nCategory.COMMAND, "issuebounty.success", "A bounty of %s has been issued for %s");
      POUCH_INVENTORY_FULL = Component.m_237115_(ModRegistry.registerName(ModRegistry.I18nCategory.COMMAND, "pouch.inventory_full", "Your inventory is full!"));
      CHECK_FRUIT_OFPW_ONLY = Component.m_237115_(ModRegistry.registerName(ModRegistry.I18nCategory.COMMAND, "check_fruit.ofpw_only", "This command can only be used when the One Fruit per World config option is enabled."));
      CHECK_FRUIT_ERROR_EXPORTING = Component.m_237115_(ModRegistry.registerName(ModRegistry.I18nCategory.COMMAND, "check_fruit.error_exporting", "Error occured while trying to export the one fruit report, send a log to a dev."));
      DAMAGE_MULTIPLIER_SET = ModRegistry.registerName(ModRegistry.I18nCategory.COMMAND, "damagem.set", "Damage Multiplier set to %s for %s.");
      CREW_NO_CREW_FOUND = Component.m_237115_(ModRegistry.registerName(ModRegistry.I18nCategory.COMMAND, "crew.no_crew_found", "No crews found!"));
      CREW_DELETE_ALL_CONFIRM = Component.m_237115_(ModRegistry.registerName(ModRegistry.I18nCategory.COMMAND, "crew.confirm_delete", "This will delete all crews on your server! If you are absolutely sure you want to do this insert \\\"true\\\" after the command!"));
      CREW_DELETED_ALL_CREWS = Component.m_237115_(ModRegistry.registerName(ModRegistry.I18nCategory.COMMAND, "crew.removed_all", "Successfully deleted all crews!"));
      CREW_DELETED_CREW = Component.m_237115_(ModRegistry.registerName(ModRegistry.I18nCategory.COMMAND, "crew.deleted", "Successfully deleted crew!"));
      CREW_CREATED_CREW = Component.m_237115_(ModRegistry.registerName(ModRegistry.I18nCategory.COMMAND, "crew.created", "Successfully created crew!"));
      CREW_PLAYER_NO_PIRATE = Component.m_237115_(ModRegistry.registerName(ModRegistry.I18nCategory.COMMAND, "crew.player_no_pirate", "Player is not a Pirate!"));
      CREW_PLAYER_ADDED_TO_CREW = Component.m_237115_(ModRegistry.registerName(ModRegistry.I18nCategory.COMMAND, "crew.player_added_to_crew", "Player successfully added to crew!"));
      CREW_PLAYER_REMOVED_FROM_CREW = Component.m_237115_(ModRegistry.registerName(ModRegistry.I18nCategory.COMMAND, "crew.player_removed_from_crew", "Player successfully removed from crew!"));
      CREW_CAPTAIN_CHANGE = Component.m_237115_(ModRegistry.registerName(ModRegistry.I18nCategory.COMMAND, "crew.captain_change", "Successfully changed captain of crew!"));
      CREW_ALREADY_IN_CREW = Component.m_237115_(ModRegistry.registerName(ModRegistry.I18nCategory.COMMAND, "crew.player_in_crew", "Player already in a crew!"));
      CREW_NOT_IN_CREW = Component.m_237115_(ModRegistry.registerName(ModRegistry.I18nCategory.COMMAND, "crew.not_in_crew", "Player not in that crew!"));
      CREW_NAME_ALREADY_EXISTS = Component.m_237115_(ModRegistry.registerName(ModRegistry.I18nCategory.COMMAND, "crew.already_exists", "Crew with that name already exists!"));
   }
}
