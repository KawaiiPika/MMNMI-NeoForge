package xyz.pixelatedw.mineminenomi.client.gui.screens;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.data.entity.PlayerStats;
import xyz.pixelatedw.mineminenomi.init.ModAbilities;
import xyz.pixelatedw.mineminenomi.networking.packets.CUnlockAbilityPacket;

public class AbilityTreeScreen extends Screen {

    public AbilityTreeScreen() {
        super(Component.literal("Skill Tree"));
    }

    @Override
    protected void init() {
        super.init();

        int y = 40;
        var abilities = ModAbilities.REGISTRY;
        var player = this.minecraft.player;
        if (player == null) return;
        PlayerStats stats = PlayerStats.get(player);
        if (stats == null) return;

        for (var entry : abilities.entrySet()) {
            Ability ability = entry.getValue();
            ResourceLocation id = entry.getKey().location();

            // Allow unlocking abilities the player doesn't have yet
            if (!stats.getCombat().equippedAbilities().contains(id.toString()) && !stats.getCombat().activeAbilities().contains(id.toString())) {
                this.addRenderableWidget(Button.builder(Component.literal("Unlock " + ability.getDisplayName().getString()), (btn) -> {
                    net.neoforged.neoforge.network.PacketDistributor.sendToServer(new CUnlockAbilityPacket(id));
                    btn.active = false;
                }).bounds(this.width / 2 - 100, y, 200, 20).build());

                y += 25;
            }
        }
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(graphics, mouseX, mouseY, partialTick);
        super.render(graphics, mouseX, mouseY, partialTick);
        graphics.drawCenteredString(this.font, this.title, this.width / 2, 20, 0xFFFFFF);
    }
}
