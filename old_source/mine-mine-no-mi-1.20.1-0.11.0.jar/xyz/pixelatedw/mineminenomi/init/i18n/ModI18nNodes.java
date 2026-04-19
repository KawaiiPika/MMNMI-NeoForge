package xyz.pixelatedw.mineminenomi.init.i18n;

import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class ModI18nNodes {
   public static final String DORIKI_CHECK;
   public static final String MARKSMANSHIP_POINTS_CHECK;
   public static final String WEAPON_MASTERY_POINTS_CHECK;
   public static final String TECHNOLOGY_POINTS_CHECK;
   public static final String MARTIAL_ARTS_POINTS_CHECK;
   public static final String RACE_CHECK;
   public static final String FACTION_CHECK;
   public static final String STYLE_CHECK;
   public static final String NODE_CHECK;

   public static void init() {
   }

   static {
      DORIKI_CHECK = ModRegistry.registerName(ModRegistry.I18nCategory.ABILITY_NODE, "doriki_check", "Doriki: %d");
      MARKSMANSHIP_POINTS_CHECK = ModRegistry.registerName(ModRegistry.I18nCategory.ABILITY_NODE, "marksmanship_points_check", "Marksmanship Points: %d");
      WEAPON_MASTERY_POINTS_CHECK = ModRegistry.registerName(ModRegistry.I18nCategory.ABILITY_NODE, "weapon_mastery_points_check", "Weapon Mastery Points: %d");
      TECHNOLOGY_POINTS_CHECK = ModRegistry.registerName(ModRegistry.I18nCategory.ABILITY_NODE, "technology_points_check", "Technology Points: %d");
      MARTIAL_ARTS_POINTS_CHECK = ModRegistry.registerName(ModRegistry.I18nCategory.ABILITY_NODE, "martial_arts_points_check", "Martial Arts Points: %d");
      RACE_CHECK = ModRegistry.registerName(ModRegistry.I18nCategory.ABILITY_NODE, "race_check", "Required Race: %s");
      FACTION_CHECK = ModRegistry.registerName(ModRegistry.I18nCategory.ABILITY_NODE, "faction_check", "Required Faction: %s");
      STYLE_CHECK = ModRegistry.registerName(ModRegistry.I18nCategory.ABILITY_NODE, "style_check", "Required Fighting Style: %s");
      NODE_CHECK = ModRegistry.registerName(ModRegistry.I18nCategory.ABILITY_NODE, "node_check", "Required Node: %s");
   }
}
