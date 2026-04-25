package xyz.pixelatedw.mineminenomi.client.events;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.InputEvent;
import net.neoforged.neoforge.client.event.RenderGuiLayerEvent;
import xyz.pixelatedw.mineminenomi.ModMain;
import xyz.pixelatedw.mineminenomi.init.ModKeybindings;
import xyz.pixelatedw.mineminenomi.networking.packets.CToggleCombatModePacket;
import xyz.pixelatedw.mineminenomi.networking.packets.CExecuteAbilityPacket;
import xyz.pixelatedw.mineminenomi.networking.packets.CSelectAbilitySlotPacket;
import xyz.pixelatedw.mineminenomi.data.entity.PlayerStats;

@EventBusSubscriber(modid = ModMain.PROJECT_ID, value = Dist.CLIENT)
public class InputEvents {

    @SubscribeEvent
    public static void onKeyInput(InputEvent.Key event) {
        if (ModKeybindings.COMBAT_MODE.consumeClick()) {
            net.neoforged.neoforge.network.PacketDistributor.sendToServer(new CToggleCombatModePacket());
        }

        var mc = net.minecraft.client.Minecraft.getInstance();

        if (ModKeybindings.STATS_MENU.consumeClick()) {
            mc.setScreen(new xyz.pixelatedw.mineminenomi.client.gui.screens.PlayerStatsScreen());
        }
        
        if (mc.screen == null) {
            if (ModKeybindings.CHARACTER_CREATOR_MENU.consumeClick()) {
                mc.setScreen(new xyz.pixelatedw.mineminenomi.ui.screens.CharacterCreatorScreen(false, true));
            }
            if (ModKeybindings.ABILITIES_MENU.consumeClick()) {
                mc.setScreen(new xyz.pixelatedw.mineminenomi.ui.screens.AbilitiesListScreen());
            }
        }

        var player = mc.player;
        if (player != null) {
            var stats = PlayerStats.get(player);
            if (stats != null && stats.isInCombatMode()) {
                for (int i = 0; i < ModKeybindings.ABILITY_SLOTS.length; i++) {
                    if (ModKeybindings.ABILITY_SLOTS[i].consumeClick()) {
                        net.neoforged.neoforge.network.PacketDistributor.sendToServer(new CExecuteAbilityPacket(i));
                    }
                }

                if (ModKeybindings.NEXT_COMBAT_BAR.consumeClick()) {
                    int next = (stats.getCombatBarSet() + 1) % xyz.pixelatedw.mineminenomi.config.ServerConfig.getAbilityBars();
                    stats.setCombatBarSet(next);
                    net.neoforged.neoforge.network.PacketDistributor.sendToServer(new xyz.pixelatedw.mineminenomi.networking.packets.CChangeCombatBarPacket(next));
                }
                if (ModKeybindings.PREV_COMBAT_BAR.consumeClick()) {
                    int prev = stats.getCombatBarSet() - 1;
                    if (prev < 0) prev = xyz.pixelatedw.mineminenomi.config.ServerConfig.getAbilityBars() - 1;
                    stats.setCombatBarSet(prev);
                    net.neoforged.neoforge.network.PacketDistributor.sendToServer(new xyz.pixelatedw.mineminenomi.networking.packets.CChangeCombatBarPacket(prev));
                }
            }
        }
    }

    @SubscribeEvent
    public static void onMouseScroll(InputEvent.MouseScrollingEvent event) {
        var player = net.minecraft.client.Minecraft.getInstance().player;
        if (player != null) {
            var stats = PlayerStats.get(player);
            if (stats != null && stats.isInCombatMode()) {
                double scrollDelta = event.getScrollDeltaY();
                if (scrollDelta != 0) {
                    int currentSlot = stats.getSelectedAbilitySlot();
                    int newSlot = (currentSlot - (int) Math.signum(scrollDelta)) % 8;
                    if (newSlot < 0) newSlot = 7;

                    stats.setSelectedAbilitySlot(newSlot);
                    net.neoforged.neoforge.network.PacketDistributor.sendToServer(new CSelectAbilitySlotPacket(newSlot));
                    event.setCanceled(true);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onRenderGuiLayer(RenderGuiLayerEvent.Pre event) {
        if (event.getName().equals(net.minecraft.resources.ResourceLocation.withDefaultNamespace("hotbar")) ||
            event.getName().equals(net.minecraft.resources.ResourceLocation.withDefaultNamespace("experience_bar"))) {
            var player = net.minecraft.client.Minecraft.getInstance().player;
            if (player != null) {
                var stats = PlayerStats.get(player);
                if (stats != null && stats.isInCombatMode()) {
                    event.setCanceled(true);
                }
            }
        }
    }
}
