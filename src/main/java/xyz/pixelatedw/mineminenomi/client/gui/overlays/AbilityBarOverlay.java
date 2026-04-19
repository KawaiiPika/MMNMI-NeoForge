package xyz.pixelatedw.mineminenomi.client.gui.overlays;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.LayeredDraw;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.data.entity.PlayerStats;
import xyz.pixelatedw.mineminenomi.init.ModAbilities;
import xyz.pixelatedw.mineminenomi.init.ModResources;

public class AbilityBarOverlay implements LayeredDraw.Layer {

    public static final AbilityBarOverlay INSTANCE = new AbilityBarOverlay();

    @Override
    public void render(GuiGraphics graphics, net.minecraft.client.DeltaTracker deltaTracker) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.options.hideGui || mc.player == null) return;

        PlayerStats stats = PlayerStats.get(mc.player);
        if (stats == null || !stats.isInCombatMode()) return;

        int screenWidth = mc.getWindow().getGuiScaledWidth();
        int screenHeight = mc.getWindow().getGuiScaledHeight();

        int posX = screenWidth / 2;
        int posY = screenHeight;

        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();

        // Render Background Slots
        for (int i = 0; i < 8; i++) {
            int x = posX - 100 + i * 25;
            
            // Draw Vanilla-like slot background
            xyz.pixelatedw.mineminenomi.api.helpers.RendererHelper.drawAbilitySlot(graphics, x, posY - 23, 23, 23);
            
            // Draw slot number at bottom right
            String slotNum = String.valueOf(i + 1);
            int strWidth = mc.font.width(slotNum);
            xyz.pixelatedw.mineminenomi.api.helpers.RendererHelper.drawStringWithBorder(mc.font, graphics, Component.literal(slotNum), x + 23 - strWidth - 1, posY - 9, 0xFFFFFF);
            
            // Draw selection highlight if selected
            if (i == stats.getSelectedAbilitySlot()) {
                xyz.pixelatedw.mineminenomi.api.helpers.RendererHelper.drawSelectionHighlight(graphics, x, posY - 23, 23, 23);
            }

            String abilityId = stats.getEquippedAbility(i + stats.getCombatBarSet() * 8);
            if (!abilityId.isEmpty()) {
                Ability ability = ModAbilities.REGISTRY.get(ResourceLocation.parse(abilityId));
                if (ability != null) {
                    // Render ability icon (16x16) using RendererHelper
                    xyz.pixelatedw.mineminenomi.api.helpers.RendererHelper.drawIcon(ability.getTexture(), graphics.pose(), (float)(x + 4), (float)(posY - 19), 400.0F, 16.0F, 16.0F, 1.0F, 1.0F, 1.0F, 1.0F, false);
                    
                    // Render blue active highlight (border only, darker blue)
                    if (stats.isAbilityActive(abilityId)) {
                        RenderSystem.setShaderColor(0.2f, 0.2f, 1.0f, 1.0f);
                        xyz.pixelatedw.mineminenomi.api.helpers.RendererHelper.drawSelectionHighlight(graphics, x, posY - 23, 23, 23);
                        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
                    }
                    
                    // Render cooldown visual timer
                    if (stats.getCombat().abilityCooldowns() != null && stats.getCombat().abilityCooldowns().containsKey(abilityId)) {
                        long expiryTicks = stats.getCombat().abilityCooldowns().get(abilityId);
                        long currentTicks = mc.level.getGameTime();
                        if (expiryTicks > currentTicks) {
                            long maxTicks = stats.getCombat().abilityMaxCooldowns().getOrDefault(abilityId, 1L);
                            double ratio = (double) (expiryTicks - currentTicks) / maxTicks;
                            
                            graphics.pose().pushPose();
                            graphics.pose().translate(0, 0, 450); // Render above the icon (z=400)
                            
                            // Yellow overlay from widgets.png UV (24, 0)
                            int overlayHeight = (int) Math.max(0, 23 - (ratio * 23.0));
                            graphics.blit(ModResources.WIDGETS, x, posY - 23, 24, 0, 23, overlayHeight);
                            
                            // Draw text timer in X.x seconds format
                            double remainingSeconds = (expiryTicks - currentTicks) / 20.0;
                            String timeStr = String.format("%.1f", remainingSeconds);
                            int strW = mc.font.width(timeStr);
                            
                            xyz.pixelatedw.mineminenomi.api.helpers.RendererHelper.drawStringWithBorder(mc.font, graphics, Component.literal(timeStr), x + 11 - strW / 2, posY - 16, 0xFFFFFF);
                            graphics.pose().popPose();
                        }
                    }
                }
            }
        }

        // Render Doriki & Bounty
        String dorikiText = "Doriki: " + (int)stats.getDoriki();
        graphics.drawString(mc.font, dorikiText, 10, 10, 0xFFFFFF);

        String bountyText = "Bounty: " + stats.getBounty();
        graphics.drawString(mc.font, bountyText, 10, 25, 0xFFFFFF);

        // Render Haki Info
        graphics.drawString(mc.font, "Busoshoku Haki: " + (int)stats.getBusoshokuHakiExp(), 10, 40, 0x555555);
        graphics.drawString(mc.font, "Kenbunshoku Haki: " + (int)stats.getKenbunshokuHakiExp(), 10, 55, 0xFFFFFF);

        // Render Stamina Bar
        int barWidth = 100;
        int barHeight = 5;
        int barX = 10;
        int barY = 70;
        
        // Background
        graphics.fill(barX, barY, barX + barWidth, barY + barHeight, 0x55000000);
        
        // Progress
        int progress = (int) (stats.getStamina() / stats.getMaxStamina() * barWidth);
        graphics.fill(barX, barY, barX + progress, barY + barHeight, 0xFFFFAA00);
        
        graphics.drawString(mc.font, "Stamina: " + (int)stats.getStamina(), 10, 78, 0xFFFFFF);

        RenderSystem.disableBlend();
    }
}
