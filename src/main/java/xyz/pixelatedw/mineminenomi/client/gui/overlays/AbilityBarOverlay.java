package xyz.pixelatedw.mineminenomi.client.gui.overlays;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.LayeredDraw;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.client.KeyMapping;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.data.entity.PlayerStats;
import xyz.pixelatedw.mineminenomi.init.ModAbilities;
import xyz.pixelatedw.mineminenomi.init.ModResources;
import xyz.pixelatedw.mineminenomi.init.ModKeybindings;
import java.awt.Color;

public class AbilityBarOverlay implements LayeredDraw.Layer {

    public static final AbilityBarOverlay INSTANCE = new AbilityBarOverlay();

    private static int colorTicks = 1000;
    private static int iconSum = 0;
    private static int iconMode = 0;

    @Override
    public void render(GuiGraphics graphics, net.minecraft.client.DeltaTracker deltaTracker) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.options.hideGui || mc.player == null) return;

        PlayerStats stats = PlayerStats.get(mc.player);
        if (stats == null || !stats.isInCombatMode()) return;

        colorTicks = (colorTicks + 1) % 500;
        int screenWidth = mc.getWindow().getGuiScaledWidth();
        int screenHeight = mc.getWindow().getGuiScaledHeight();

        int posX = screenWidth / 2;
        int posY = screenHeight;

        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();

        // Draw the indicator for the current active bar index
        int activeBarIndex = stats.getCombatBarSet() + 1;
        String barIdStr = String.valueOf(activeBarIndex);
        xyz.pixelatedw.mineminenomi.api.helpers.RendererHelper.drawStringWithBorder(
                mc.font, graphics, Component.literal(barIdStr), posX - 120, posY - 14, 0xFFFFFF);

        // Render Background Slots
        for (int i = 0; i < 8; i++) {
            int slotX = posX - 100 + i * 25;
            int slotY = posY - 23;
            
            // Draw Vanilla-like slot background
            xyz.pixelatedw.mineminenomi.api.helpers.RendererHelper.drawAbilitySlot(graphics, slotX, slotY, 23, 23);
            
            // Draw slot number at bottom right
            String slotNum = String.valueOf(i + 1);
            int strWidth = mc.font.width(slotNum);
            xyz.pixelatedw.mineminenomi.api.helpers.RendererHelper.drawStringWithBorder(mc.font, graphics, Component.literal(slotNum), slotX + 23 - strWidth - 1, posY - 9, 0xFFFFFF);
            
            // Draw selection highlight if selected
            if (i == stats.getSelectedAbilitySlot()) {
                xyz.pixelatedw.mineminenomi.api.helpers.RendererHelper.drawSelectionHighlight(graphics, slotX, slotY, 23, 23);
            }

            int abilityIndex = i + stats.getCombatBarSet() * 8;
            String abilityIdStr = stats.getEquippedAbility(abilityIndex);
            if (!abilityIdStr.isEmpty()) {
                Ability ability = ModAbilities.REGISTRY.get(ResourceLocation.parse(abilityIdStr));
                if (ability != null) {
                    // Render ability icon (16x16) using RendererHelper
                    xyz.pixelatedw.mineminenomi.api.helpers.RendererHelper.drawIcon(ability.getTexture(), graphics.pose(), (float)(slotX + 4), (float)(slotY + 4), 400.0F, 16.0F, 16.0F, 1.0F, 1.0F, 1.0F, 1.0F, false);
                    
                    // Render blue active highlight (border only, darker blue)
                    if (stats.isAbilityActive(abilityIdStr)) {
                        RenderSystem.setShaderColor(0.2f, 0.2f, 1.0f, 1.0f);
                        xyz.pixelatedw.mineminenomi.api.helpers.RendererHelper.drawSelectionHighlight(graphics, slotX, slotY, 23, 23);
                        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
                    }
                    
                    // Render cooldown visual timer
                    if (stats.getCombat().abilityCooldowns() != null && stats.getCombat().abilityCooldowns().containsKey(abilityIdStr)) {
                        long expiryTicks = stats.getCombat().abilityCooldowns().get(abilityIdStr);
                        long currentTicks = mc.level.getGameTime();
                        if (expiryTicks > currentTicks) {
                            long maxTicks = stats.getCombat().abilityMaxCooldowns().getOrDefault(abilityIdStr, 1L);
                            double ratio = (double) (expiryTicks - currentTicks) / maxTicks;
                            
                            graphics.pose().pushPose();
                            graphics.pose().translate(0, 0, 450); // Render above the icon (z=400)
                            
                            // Yellow overlay from widgets.png UV (24, 0)
                            int overlayHeight = (int) Math.max(0, 23 - (ratio * 23.0));
                            graphics.blit(ModResources.WIDGETS, slotX, slotY, 24, 0, 23, overlayHeight);
                            
                            // Draw text timer in X.x seconds format
                            double remainingSeconds = (expiryTicks - currentTicks) / 20.0;
                            String timeStr = String.format("%.1f", remainingSeconds);
                            int strW = mc.font.width(timeStr);
                            
                            xyz.pixelatedw.mineminenomi.api.helpers.RendererHelper.drawStringWithBorder(mc.font, graphics, Component.literal(timeStr), slotX + 11 - strW / 2, slotY + 7, 0xFFFFFF);
                            graphics.pose().popPose();
                        }
                    }
                }
            }

            // Draw keybind text
            renderHotbarKeybind(graphics, mc.font, slotX, slotY, i);
        }

        renderCombatIndicator(mc.player, stats, graphics, posX, posY, 0);

        RenderSystem.disableBlend();
    }

    private void renderHotbarKeybind(GuiGraphics graphics, net.minecraft.client.gui.Font font, int slotX, int slotY, int slotId) {
        KeyMapping kb = ModKeybindings.ABILITY_SLOTS[slotId];
        StringBuilder sb = new StringBuilder();
        
        if (kb.isUnbound()) {
            sb.append(colorTicks >= 250 ? "§4" : "§c");
            sb.append("⚠");
        } else {
            switch (kb.getKeyModifier()) {
                case ALT -> sb.append("a");
                case CONTROL -> sb.append("c");
                case SHIFT -> sb.append("s");
            }
            sb.append(kb.getKey().getDisplayName().getString());
        }
        
        graphics.pose().pushPose();
        graphics.pose().translate((float)slotX + 2, (float)slotY - 1, 0.0F);
        graphics.pose().scale(0.66F, 0.66F, 0.66F);
        xyz.pixelatedw.mineminenomi.api.helpers.RendererHelper.drawStringWithBorder(font, graphics, Component.literal(sb.toString()), 0, 0, 0xFFFFFF);
        graphics.pose().popPose();
    }

    private void renderCombatIndicator(net.minecraft.client.player.LocalPlayer player, PlayerStats stats, GuiGraphics graphics, int posX, int posY, int offset) {
        iconSum = 1; // Always at least 1 since we're in combat mode
        if (stats.isRogue()) {
            iconSum += 2;
        }

        if (iconSum == 3) {
            if (iconMode == 0) {
                iconMode = iconSum % 2;
            }

            if (player.tickCount % 40 == 0) {
                if (iconMode == 1) {
                    iconMode = 2;
                } else if (iconMode == 2) {
                    iconMode = 1;
                }
            }
        } else {
            iconMode = iconSum % 3;
        }

        if (iconMode > 0) {
            ResourceLocation icon = switch (iconMode) {
                case 1 -> ModResources.PIRATE_ICON;
                case 2 -> ModResources.WARNING_ICON;
                default -> null;
            };

            if (icon != null) {
                graphics.pose().pushPose();
                graphics.pose().translate((float)(posX - 128), (float)(posY - 170 - offset), 2.0F);
                graphics.pose().translate(128.0F, 128.0F, 0.0F);
                graphics.pose().scale(0.15F, 0.15F, 1.0F);
                graphics.pose().translate(-128.0F, -128.0F, 0.0F);
                RenderSystem.setShaderColor(1.0F, 0.0F, 0.0F, 1.0F);
                graphics.blit(icon, 0, 0, 0, 0, 256, 256);
                RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
                graphics.pose().popPose();
            }
        }
    }
}
