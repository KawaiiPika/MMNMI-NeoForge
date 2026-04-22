package xyz.pixelatedw.mineminenomi.client.gui.screens;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.ChatFormatting;
import net.minecraft.resources.ResourceLocation;
import xyz.pixelatedw.mineminenomi.api.helpers.RendererHelper;
import xyz.pixelatedw.mineminenomi.data.entity.PlayerStats;
import xyz.pixelatedw.mineminenomi.init.ModFruits;
import xyz.pixelatedw.mineminenomi.init.ModResources;
import xyz.pixelatedw.mineminenomi.items.AkumaNoMiItem;
import xyz.pixelatedw.mineminenomi.ui.widget.PlankButton;

public class PlayerStatsScreen extends Screen {

    private static final ResourceLocation CURRENCIES = ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/gui/currencies.png");

    public PlayerStatsScreen() {
        super(Component.literal("Player Stats"));
    }

    @Override
    protected void init() {
        super.init();

        int posX = this.width / 2 - 190;
        int posY = this.height - this.height / 4 - 10;

        this.addRenderableWidget(new PlankButton(posX, posY, 80, 26, Component.translatable("gui.mineminenomi.abilities"), (btn) -> {
            this.minecraft.setScreen(new xyz.pixelatedw.mineminenomi.ui.screens.AbilitiesListScreen());
        }));
        this.addRenderableWidget(new PlankButton(posX + 60, posY, 80, 26, Component.translatable("gui.mineminenomi.ability_tree"), (btn) -> {
            this.minecraft.setScreen(new xyz.pixelatedw.mineminenomi.client.gui.screens.AbilityTreeScreen());
        }));
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(graphics, mouseX, mouseY, partialTick);
        int posX = (this.width - 256) / 2;
        int posY = (this.height - 256) / 2;
        
        var player = this.minecraft.player;
        if (player != null) {
            PlayerStats stats = PlayerStats.get(player);
            if (stats != null) {
                // Determine if cyborg to show Cola
                if (stats.getRace().map(rl -> rl.getPath()).orElse("").contains("cyborg")) {
                     graphics.drawString(this.font, Component.literal(ChatFormatting.BOLD + "Cola: " + ChatFormatting.RESET + stats.getBasic().cola()), posX - 50, posY + 30, -1);
                }

                graphics.drawString(this.font, Component.literal(ChatFormatting.BOLD + "Doriki: " + ChatFormatting.RESET + Math.round(stats.getDoriki())), posX - 50, posY + 50, -1);
                
                String factionActual = stats.getFaction().map(rl -> rl.getPath()).orElse("None");
                graphics.drawString(this.font, Component.literal(ChatFormatting.BOLD + "Faction: " + ChatFormatting.RESET + factionActual), posX - 50, posY + 70, -1);

                String raceActual = stats.getRace().map(rl -> rl.getPath()).orElse("None");
                graphics.drawString(this.font, Component.literal(ChatFormatting.BOLD + "Race: " + ChatFormatting.RESET + raceActual), posX - 50, posY + 90, -1);

                String styleActual = stats.getFightingStyle().map(rl -> rl.getPath()).orElse("None");
                graphics.drawString(this.font, Component.literal(ChatFormatting.BOLD + "Style: " + ChatFormatting.RESET + styleActual), posX - 50, posY + 110, -1);

                if (stats.getBelly() > 0L) {
                    graphics.drawString(this.font, Component.literal("" + stats.getBelly()), posX + 215, posY + 52, -1);
                    graphics.blit(CURRENCIES, posX + 190, posY + 40, 0, 32, 32, 64);
                }

                if (stats.getExtol() > 0L) {
                    graphics.drawString(this.font, Component.literal("" + stats.getExtol()), posX + 215, posY + 82, -1);
                    graphics.blit(CURRENCIES, posX + 190, posY + 69, 34, 32, 64, 86);
                }

                if (stats.getDevilFruit().isPresent()) {
                    var fruitLoc = stats.getDevilFruit().get();
                    var fruitItem = net.minecraft.core.registries.BuiltInRegistries.ITEM.get(fruitLoc);
                    if (fruitItem instanceof AkumaNoMiItem akumaItem) {
                        ItemStack mainFruit = new ItemStack(akumaItem);
                        boolean hasYamiSecondary = !fruitLoc.equals(ModFruits.YAMI_YAMI_NO_MI.getId()) && stats.getCombat().hasYamiPower();

                        String mainFruitName = akumaItem.getName(mainFruit).getString();
                        int color = -1;
                        if (stats.getCombat().hasAwakenedFruit()) {
                            color = 15509033;
                        }

                        StringBuilder sb = new StringBuilder();
                        sb.append(mainFruitName);
                        if (hasYamiSecondary) {
                            var yamiItem = ModFruits.YAMI_YAMI_NO_MI.get();
                            sb.append(" + ").append(yamiItem.getName(new ItemStack(yamiItem)).getString());
                            graphics.renderItem(new ItemStack(yamiItem), posX - 56, posY + 147);
                        }

                        graphics.drawString(this.font, Component.literal(ChatFormatting.BOLD + sb.toString()), posX - 28, posY + 154, color);
                        graphics.renderItem(mainFruit, posX - 50, posY + 150);
                    }
                }
            }
        }
        super.render(graphics, mouseX, mouseY, partialTick);
    }
}
