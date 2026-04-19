package xyz.pixelatedw.mineminenomi.init;

import net.minecraft.client.KeyMapping;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.settings.KeyConflictContext;
import org.lwjgl.glfw.GLFW;
import xyz.pixelatedw.mineminenomi.ModMain;

@EventBusSubscriber(modid = ModMain.PROJECT_ID, value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD)
public class ModKeybindings {

    public static final String CATEGORY_GENERAL = "key.categories.mineminenomi.general";
    public static final String CATEGORY_COMBATBAR = "key.categories.mineminenomi.combatbar";

    public static final KeyMapping COMBAT_MODE = new KeyMapping("key.mineminenomi.combat_mode", KeyConflictContext.IN_GAME, com.mojang.blaze3d.platform.InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_LEFT_ALT, CATEGORY_COMBATBAR);

    public static final KeyMapping ABILITIES_MENU = new KeyMapping("key.mineminenomi.abilities_menu", KeyConflictContext.IN_GAME, com.mojang.blaze3d.platform.InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_O, CATEGORY_GENERAL);
    public static final KeyMapping STATS_MENU = new KeyMapping("key.mineminenomi.stats_menu", KeyConflictContext.IN_GAME, com.mojang.blaze3d.platform.InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_P, CATEGORY_GENERAL);
    public static final KeyMapping CHARACTER_CREATOR_MENU = new KeyMapping("key.mineminenomi.character_creator_menu", KeyConflictContext.IN_GAME, com.mojang.blaze3d.platform.InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_K, CATEGORY_GENERAL);
    public static final KeyMapping NEXT_COMBAT_BAR = new KeyMapping("key.mineminenomi.next_combat_bar", KeyConflictContext.IN_GAME, com.mojang.blaze3d.platform.InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_RIGHT, CATEGORY_COMBATBAR);
    public static final KeyMapping PREV_COMBAT_BAR = new KeyMapping("key.mineminenomi.prev_combat_bar", KeyConflictContext.IN_GAME, com.mojang.blaze3d.platform.InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_LEFT, CATEGORY_COMBATBAR);

    public static final KeyMapping[] ABILITY_SLOTS = new KeyMapping[] {
            new KeyMapping("key.mineminenomi.ability_slot_1", KeyConflictContext.IN_GAME, com.mojang.blaze3d.platform.InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_1, CATEGORY_COMBATBAR),
            new KeyMapping("key.mineminenomi.ability_slot_2", KeyConflictContext.IN_GAME, com.mojang.blaze3d.platform.InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_2, CATEGORY_COMBATBAR),
            new KeyMapping("key.mineminenomi.ability_slot_3", KeyConflictContext.IN_GAME, com.mojang.blaze3d.platform.InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_3, CATEGORY_COMBATBAR),
            new KeyMapping("key.mineminenomi.ability_slot_4", KeyConflictContext.IN_GAME, com.mojang.blaze3d.platform.InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_4, CATEGORY_COMBATBAR),
            new KeyMapping("key.mineminenomi.ability_slot_5", KeyConflictContext.IN_GAME, com.mojang.blaze3d.platform.InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_5, CATEGORY_COMBATBAR),
            new KeyMapping("key.mineminenomi.ability_slot_6", KeyConflictContext.IN_GAME, com.mojang.blaze3d.platform.InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_6, CATEGORY_COMBATBAR),
            new KeyMapping("key.mineminenomi.ability_slot_7", KeyConflictContext.IN_GAME, com.mojang.blaze3d.platform.InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_7, CATEGORY_COMBATBAR),
            new KeyMapping("key.mineminenomi.ability_slot_8", KeyConflictContext.IN_GAME, com.mojang.blaze3d.platform.InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_8, CATEGORY_COMBATBAR)
    };

    @SubscribeEvent
    public static void registerKeyMappings(RegisterKeyMappingsEvent event) {
        event.register(COMBAT_MODE);
        event.register(ABILITIES_MENU);
        event.register(STATS_MENU);
        event.register(CHARACTER_CREATOR_MENU);
        event.register(NEXT_COMBAT_BAR);
        event.register(PREV_COMBAT_BAR);
        for (KeyMapping key : ABILITY_SLOTS) {
            event.register(key);
        }
    }
}
