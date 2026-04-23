package xyz.pixelatedw.mineminenomi.client.gui.screens;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import xyz.pixelatedw.mineminenomi.data.entity.PlayerStats;

public class PlayerStatsScreen extends Screen {

    public PlayerStatsScreen() {
        super(Component.literal("Player Stats"));
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(graphics, mouseX, mouseY, partialTick);
        super.render(graphics, mouseX, mouseY, partialTick);
        graphics.drawCenteredString(this.font, this.title, this.width / 2, 20, 0xFFFFFF);
        
        var player = this.minecraft.player;
        if (player != null) {
            PlayerStats stats = PlayerStats.get(player);
            if (stats != null) {
                int y = 50;
                graphics.drawString(this.font, "Doriki: " + (int)stats.getDoriki(), this.width / 2 - 50, y, 0xFFFFFF);
                y += 15;
                graphics.drawString(this.font, "Belly: " + stats.getBelly(), this.width / 2 - 50, y, 0xFFFFFF);
                y += 15;
                graphics.drawString(this.font, "Bounty: " + stats.getBounty(), this.width / 2 - 50, y, 0xFFFFFF);
                y += 15;
                graphics.drawString(this.font, "Style: " + stats.getFightingStyle().map(rl -> rl.getPath()).orElse("None"), this.width / 2 - 50, y, 0xFFFFFF);
                
                y += 30;
                graphics.drawString(this.font, "Busoshoku Haki Exp: " + (int)stats.getBusoshokuHakiExp(), this.width / 2 - 50, y, 0xFFFFFF);
                y += 15;
                graphics.drawString(this.font, "Kenbunshoku Haki Exp: " + (int)stats.getKenbunshokuHakiExp(), this.width / 2 - 50, y, 0xFFFFFF);

                y += 30;
                graphics.drawString(this.font, "Training Points:", this.width / 2 - 50, y, 0xAAAAAA);
                for (var type : xyz.pixelatedw.mineminenomi.api.enums.TrainingPointType.values()) {
                    y += 15;
                    graphics.drawString(this.font, "  " + type.name().toLowerCase() + ": " + stats.getTrainingPoints(type), this.width / 2 - 50, y, 0xFFFFFF);
                }
            }
        }
    }
}
