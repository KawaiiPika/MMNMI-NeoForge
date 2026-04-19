package xyz.pixelatedw.mineminenomi.client.gui.screens;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.data.entity.PlayerStats;
import xyz.pixelatedw.mineminenomi.init.ModAbilities;
import xyz.pixelatedw.mineminenomi.networking.packets.CEquipAbilityPacket;

public class AbilitiesScreen extends Screen {

    public AbilitiesScreen() {
        super(Component.literal("Abilities"));
    }

    @Override
    protected void init() {
        super.init();
        
        int y = 40;
        var abilities = ModAbilities.REGISTRY;
        
        for (var entry : abilities.entrySet()) {
            Ability ability = entry.getValue();
            ResourceLocation id = entry.getKey().location();
            
            this.addRenderableWidget(Button.builder(ability.getDisplayName(), (btn) -> {
                // For simplicity, equip to the first empty slot in current bar
                int slot = findFirstEmptySlot();
                if (slot != -1) {
                    net.neoforged.neoforge.network.PacketDistributor.sendToServer(new CEquipAbilityPacket(slot, id.toString()));
                }
            }).bounds(this.width / 2 - 100, y, 200, 20).build());
            
            y += 25;
        }
    }

    private int findFirstEmptySlot() {
        var player = this.minecraft.player;
        if (player == null) return -1;
        PlayerStats stats = PlayerStats.get(player);
        if (stats == null) return -1;
        
        int barStart = stats.getCombatBarSet() * 8;
        for (int i = 0; i < 8; i++) {
            if (stats.getEquippedAbility(barStart + i).isEmpty()) {
                return barStart + i;
            }
        }
        return -1;
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(graphics, mouseX, mouseY, partialTick);
        super.render(graphics, mouseX, mouseY, partialTick);
        graphics.drawCenteredString(this.font, this.title, this.width / 2, 20, 0xFFFFFF);
    }
}
