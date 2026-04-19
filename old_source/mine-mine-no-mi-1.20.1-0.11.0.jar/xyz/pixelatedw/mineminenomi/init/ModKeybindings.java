package xyz.pixelatedw.mineminenomi.init;

import com.mojang.blaze3d.platform.InputConstants.Type;
import java.util.ArrayList;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18nKeybinds;

@EventBusSubscriber(
   modid = "mineminenomi",
   value = {Dist.CLIENT},
   bus = Bus.MOD
)
public class ModKeybindings {
   public static final KeyMapping TEST;
   public static final KeyMapping OPEN_PLAYER_MENU;
   public static final KeyMapping OPEN_ABILITIES_MENU;
   public static final KeyMapping OPEN_CHALLENGES_MENU;
   public static final KeyMapping OPEN_QUESTS_MENU;
   public static final KeyMapping OPEN_CREW_MENU;
   public static final KeyMapping OPEN_ABILITY_TREE_MENU;
   public static final KeyMapping VEHICLE_ALT_MODE;
   public static final KeyMapping COMBAT_MODE;
   public static final KeyMapping NEXT_COMBAT_BAR;
   public static final KeyMapping PREV_COMBAT_BAR;
   public static final KeyMapping LAST_COMBAT_BAR;
   public static final KeyMapping ABILITY_ALT_MODE;
   public static final ArrayList<KeyMapping> COMBAT_BAR_KEYS;
   public static final KeyMapping[] COMBAT_BAR_SHORTCUTS;
   public static final int[] PREVIOUS_INVENTORY_KEYBINDS;
   public static int serverMaxBars;

   @SubscribeEvent
   public static void registerKeyMappings(RegisterKeyMappingsEvent event) {
      event.register(TEST);
      event.register(OPEN_PLAYER_MENU);
      event.register(OPEN_ABILITIES_MENU);
      event.register(OPEN_CHALLENGES_MENU);
      event.register(OPEN_QUESTS_MENU);
      event.register(OPEN_CREW_MENU);
      event.register(OPEN_ABILITY_TREE_MENU);
      event.register(VEHICLE_ALT_MODE);
      event.register(COMBAT_MODE);
      event.register(NEXT_COMBAT_BAR);
      event.register(PREV_COMBAT_BAR);
      event.register(LAST_COMBAT_BAR);
      event.register(ABILITY_ALT_MODE);

      for(KeyMapping key : COMBAT_BAR_KEYS) {
         event.register(key);
      }

      for(KeyMapping key : COMBAT_BAR_SHORTCUTS) {
         event.register(key);
      }

   }

   static {
      TEST = new KeyMapping("Test Key", KeyConflictContext.IN_GAME, Type.KEYSYM, 80, ModI18nKeybinds.CATEGORY_GENERAL);
      OPEN_PLAYER_MENU = new KeyMapping(ModI18nKeybinds.KEY_PLAYER, KeyConflictContext.UNIVERSAL, Type.KEYSYM, 82, ModI18nKeybinds.CATEGORY_GENERAL);
      OPEN_ABILITIES_MENU = new KeyMapping(ModI18nKeybinds.KEY_OPEN_ABILITIES, KeyConflictContext.UNIVERSAL, Type.KEYSYM, -1, ModI18nKeybinds.CATEGORY_GENERAL);
      OPEN_CHALLENGES_MENU = new KeyMapping(ModI18nKeybinds.KEY_OPEN_CHALLENGES, KeyConflictContext.UNIVERSAL, Type.KEYSYM, -1, ModI18nKeybinds.CATEGORY_GENERAL);
      OPEN_QUESTS_MENU = new KeyMapping(ModI18nKeybinds.KEY_OPEN_QUESTS, KeyConflictContext.UNIVERSAL, Type.KEYSYM, -1, ModI18nKeybinds.CATEGORY_GENERAL);
      OPEN_CREW_MENU = new KeyMapping(ModI18nKeybinds.KEY_OPEN_CREW, KeyConflictContext.UNIVERSAL, Type.KEYSYM, -1, ModI18nKeybinds.CATEGORY_GENERAL);
      OPEN_ABILITY_TREE_MENU = new KeyMapping(ModI18nKeybinds.KEY_OPEN_ABILITY_TREE, KeyConflictContext.UNIVERSAL, Type.KEYSYM, -1, ModI18nKeybinds.CATEGORY_GENERAL);
      VEHICLE_ALT_MODE = new KeyMapping(ModI18nKeybinds.KEY_VEHICLE_ALT_MODE, KeyConflictContext.IN_GAME, Type.KEYSYM, 96, ModI18nKeybinds.CATEGORY_GENERAL);
      COMBAT_MODE = new KeyMapping(ModI18nKeybinds.KEY_COMBATMODE, KeyConflictContext.IN_GAME, Type.KEYSYM, 342, ModI18nKeybinds.CATEGORY_COMBATBAR);
      NEXT_COMBAT_BAR = new KeyMapping(ModI18nKeybinds.KEY_NEXT_COMBAT_BAR, KeyConflictContext.IN_GAME, Type.KEYSYM, 93, ModI18nKeybinds.CATEGORY_COMBATBAR);
      PREV_COMBAT_BAR = new KeyMapping(ModI18nKeybinds.KEY_PREV_COMBAT_BAR, KeyConflictContext.IN_GAME, Type.KEYSYM, 91, ModI18nKeybinds.CATEGORY_COMBATBAR);
      LAST_COMBAT_BAR = new KeyMapping(ModI18nKeybinds.KEY_LAST_COMBAT_BAR, KeyConflictContext.IN_GAME, Type.KEYSYM, -1, ModI18nKeybinds.CATEGORY_COMBATBAR);
      ABILITY_ALT_MODE = new KeyMapping(ModI18nKeybinds.KEY_CHANGE_ABILITY_MODE, KeyConflictContext.IN_GAME, Type.KEYSYM, 96, ModI18nKeybinds.CATEGORY_COMBATBAR);
      COMBAT_BAR_KEYS = new ArrayList();
      COMBAT_BAR_SHORTCUTS = new KeyMapping[10];
      PREVIOUS_INVENTORY_KEYBINDS = new int[]{-1, -1, -1, -1, -1, -1, -1, -1, -1, -1};
      serverMaxBars = 2;
      COMBAT_BAR_KEYS.add(new KeyMapping(ModI18nKeybinds.KEY_BAR1_COMBATSLOT1, KeyConflictContext.IN_GAME, Type.KEYSYM, 49, ModI18nKeybinds.CATEGORY_COMBATBAR));
      COMBAT_BAR_KEYS.add(new KeyMapping(ModI18nKeybinds.KEY_BAR1_COMBATSLOT2, KeyConflictContext.IN_GAME, Type.KEYSYM, 50, ModI18nKeybinds.CATEGORY_COMBATBAR));
      COMBAT_BAR_KEYS.add(new KeyMapping(ModI18nKeybinds.KEY_BAR1_COMBATSLOT3, KeyConflictContext.IN_GAME, Type.KEYSYM, 51, ModI18nKeybinds.CATEGORY_COMBATBAR));
      COMBAT_BAR_KEYS.add(new KeyMapping(ModI18nKeybinds.KEY_BAR1_COMBATSLOT4, KeyConflictContext.IN_GAME, Type.KEYSYM, 52, ModI18nKeybinds.CATEGORY_COMBATBAR));
      COMBAT_BAR_KEYS.add(new KeyMapping(ModI18nKeybinds.KEY_BAR1_COMBATSLOT5, KeyConflictContext.IN_GAME, Type.KEYSYM, 53, ModI18nKeybinds.CATEGORY_COMBATBAR));
      COMBAT_BAR_KEYS.add(new KeyMapping(ModI18nKeybinds.KEY_BAR1_COMBATSLOT6, KeyConflictContext.IN_GAME, Type.KEYSYM, 54, ModI18nKeybinds.CATEGORY_COMBATBAR));
      COMBAT_BAR_KEYS.add(new KeyMapping(ModI18nKeybinds.KEY_BAR1_COMBATSLOT7, KeyConflictContext.IN_GAME, Type.KEYSYM, 55, ModI18nKeybinds.CATEGORY_COMBATBAR));
      COMBAT_BAR_KEYS.add(new KeyMapping(ModI18nKeybinds.KEY_BAR1_COMBATSLOT8, KeyConflictContext.IN_GAME, Type.KEYSYM, 56, ModI18nKeybinds.CATEGORY_COMBATBAR));
      COMBAT_BAR_KEYS.add(new KeyMapping(ModI18nKeybinds.KEY_BAR2_COMBATSLOT1, KeyConflictContext.IN_GAME, Type.KEYSYM, -1, ModI18nKeybinds.CATEGORY_COMBATBAR));
      COMBAT_BAR_KEYS.add(new KeyMapping(ModI18nKeybinds.KEY_BAR2_COMBATSLOT2, KeyConflictContext.IN_GAME, Type.KEYSYM, -1, ModI18nKeybinds.CATEGORY_COMBATBAR));
      COMBAT_BAR_KEYS.add(new KeyMapping(ModI18nKeybinds.KEY_BAR2_COMBATSLOT3, KeyConflictContext.IN_GAME, Type.KEYSYM, -1, ModI18nKeybinds.CATEGORY_COMBATBAR));
      COMBAT_BAR_KEYS.add(new KeyMapping(ModI18nKeybinds.KEY_BAR2_COMBATSLOT4, KeyConflictContext.IN_GAME, Type.KEYSYM, -1, ModI18nKeybinds.CATEGORY_COMBATBAR));
      COMBAT_BAR_KEYS.add(new KeyMapping(ModI18nKeybinds.KEY_BAR2_COMBATSLOT5, KeyConflictContext.IN_GAME, Type.KEYSYM, -1, ModI18nKeybinds.CATEGORY_COMBATBAR));
      COMBAT_BAR_KEYS.add(new KeyMapping(ModI18nKeybinds.KEY_BAR2_COMBATSLOT6, KeyConflictContext.IN_GAME, Type.KEYSYM, -1, ModI18nKeybinds.CATEGORY_COMBATBAR));
      COMBAT_BAR_KEYS.add(new KeyMapping(ModI18nKeybinds.KEY_BAR2_COMBATSLOT7, KeyConflictContext.IN_GAME, Type.KEYSYM, -1, ModI18nKeybinds.CATEGORY_COMBATBAR));
      COMBAT_BAR_KEYS.add(new KeyMapping(ModI18nKeybinds.KEY_BAR2_COMBATSLOT8, KeyConflictContext.IN_GAME, Type.KEYSYM, -1, ModI18nKeybinds.CATEGORY_COMBATBAR));
      COMBAT_BAR_KEYS.add(new KeyMapping(ModI18nKeybinds.KEY_BAR3_COMBATSLOT1, KeyConflictContext.IN_GAME, Type.KEYSYM, -1, ModI18nKeybinds.CATEGORY_COMBATBAR));
      COMBAT_BAR_KEYS.add(new KeyMapping(ModI18nKeybinds.KEY_BAR3_COMBATSLOT2, KeyConflictContext.IN_GAME, Type.KEYSYM, -1, ModI18nKeybinds.CATEGORY_COMBATBAR));
      COMBAT_BAR_KEYS.add(new KeyMapping(ModI18nKeybinds.KEY_BAR3_COMBATSLOT3, KeyConflictContext.IN_GAME, Type.KEYSYM, -1, ModI18nKeybinds.CATEGORY_COMBATBAR));
      COMBAT_BAR_KEYS.add(new KeyMapping(ModI18nKeybinds.KEY_BAR3_COMBATSLOT4, KeyConflictContext.IN_GAME, Type.KEYSYM, -1, ModI18nKeybinds.CATEGORY_COMBATBAR));
      COMBAT_BAR_KEYS.add(new KeyMapping(ModI18nKeybinds.KEY_BAR3_COMBATSLOT5, KeyConflictContext.IN_GAME, Type.KEYSYM, -1, ModI18nKeybinds.CATEGORY_COMBATBAR));
      COMBAT_BAR_KEYS.add(new KeyMapping(ModI18nKeybinds.KEY_BAR3_COMBATSLOT6, KeyConflictContext.IN_GAME, Type.KEYSYM, -1, ModI18nKeybinds.CATEGORY_COMBATBAR));
      COMBAT_BAR_KEYS.add(new KeyMapping(ModI18nKeybinds.KEY_BAR3_COMBATSLOT7, KeyConflictContext.IN_GAME, Type.KEYSYM, -1, ModI18nKeybinds.CATEGORY_COMBATBAR));
      COMBAT_BAR_KEYS.add(new KeyMapping(ModI18nKeybinds.KEY_BAR3_COMBATSLOT8, KeyConflictContext.IN_GAME, Type.KEYSYM, -1, ModI18nKeybinds.CATEGORY_COMBATBAR));
      COMBAT_BAR_SHORTCUTS[0] = new KeyMapping(ModI18nKeybinds.KEY_BAR_SHORTCUT1, KeyConflictContext.IN_GAME, Type.KEYSYM, -1, ModI18nKeybinds.CATEGORY_COMBATBAR);
      COMBAT_BAR_SHORTCUTS[1] = new KeyMapping(ModI18nKeybinds.KEY_BAR_SHORTCUT2, KeyConflictContext.IN_GAME, Type.KEYSYM, -1, ModI18nKeybinds.CATEGORY_COMBATBAR);
      COMBAT_BAR_SHORTCUTS[2] = new KeyMapping(ModI18nKeybinds.KEY_BAR_SHORTCUT3, KeyConflictContext.IN_GAME, Type.KEYSYM, -1, ModI18nKeybinds.CATEGORY_COMBATBAR);
      COMBAT_BAR_SHORTCUTS[3] = new KeyMapping(ModI18nKeybinds.KEY_BAR_SHORTCUT4, KeyConflictContext.IN_GAME, Type.KEYSYM, -1, ModI18nKeybinds.CATEGORY_COMBATBAR);
      COMBAT_BAR_SHORTCUTS[4] = new KeyMapping(ModI18nKeybinds.KEY_BAR_SHORTCUT5, KeyConflictContext.IN_GAME, Type.KEYSYM, -1, ModI18nKeybinds.CATEGORY_COMBATBAR);
      COMBAT_BAR_SHORTCUTS[5] = new KeyMapping(ModI18nKeybinds.KEY_BAR_SHORTCUT6, KeyConflictContext.IN_GAME, Type.KEYSYM, -1, ModI18nKeybinds.CATEGORY_COMBATBAR);
      COMBAT_BAR_SHORTCUTS[6] = new KeyMapping(ModI18nKeybinds.KEY_BAR_SHORTCUT7, KeyConflictContext.IN_GAME, Type.KEYSYM, -1, ModI18nKeybinds.CATEGORY_COMBATBAR);
      COMBAT_BAR_SHORTCUTS[7] = new KeyMapping(ModI18nKeybinds.KEY_BAR_SHORTCUT8, KeyConflictContext.IN_GAME, Type.KEYSYM, -1, ModI18nKeybinds.CATEGORY_COMBATBAR);
      COMBAT_BAR_SHORTCUTS[8] = new KeyMapping(ModI18nKeybinds.KEY_BAR_SHORTCUT9, KeyConflictContext.IN_GAME, Type.KEYSYM, -1, ModI18nKeybinds.CATEGORY_COMBATBAR);
      COMBAT_BAR_SHORTCUTS[9] = new KeyMapping(ModI18nKeybinds.KEY_BAR_SHORTCUT10, KeyConflictContext.IN_GAME, Type.KEYSYM, -1, ModI18nKeybinds.CATEGORY_COMBATBAR);
   }
}
