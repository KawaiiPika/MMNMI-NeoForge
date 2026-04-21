package xyz.pixelatedw.mineminenomi.ui.screens;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import com.mojang.blaze3d.systems.RenderSystem;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.CharacterCreatorSelectionInfo;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Faction;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.FightingStyle;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.ICharacterCreatorEntry;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Race;
import xyz.pixelatedw.mineminenomi.init.ModRegistries;
import xyz.pixelatedw.mineminenomi.init.ModResources;
import xyz.pixelatedw.mineminenomi.ui.widget.AbilitySlotButton;
import xyz.pixelatedw.mineminenomi.ui.widget.ArrowButton;
import xyz.pixelatedw.mineminenomi.ui.widget.BigArrowButton;
import xyz.pixelatedw.mineminenomi.ui.widget.BookSignSideButton;
import xyz.pixelatedw.mineminenomi.ui.widget.SimpleButton;

public class CharacterCreatorScreen extends Screen {
    private AbstractClientPlayer player;
    private ResourceLocation icon;
    private Component label;
    private Component subRaceLabel;
    private List<Ability> topAbilities;
    private List<Ability> bottomAbilities;
    private Faction[] factions;
    private Race[] races;
    private FightingStyle[] styles;
    private Race[] subRaces;
    private Page page;
    private int maxOpt;
    private int renderTick;
    private int subRaceId;
    private boolean hasSubRaces;
    private int[] options;
    private BookSignSideButton factionButton;
    private BookSignSideButton raceButton;
    private BookSignSideButton styleButton;
    private BigArrowButton previousSubRaceButton;
    private BigArrowButton nextSubRaceButton;
    private final List<AbilitySlotButton> abilitySlots;
    private final boolean hasRandomizedRace;
    private final boolean allowSubRaceSelect;

    public CharacterCreatorScreen(boolean hasRandomizedRace, boolean allowMinkRaceSelect) {
        super(Component.empty());
        this.icon = ModResources.RANDOM;
        this.label = Component.translatable("gui.mineminenomi.random");
        this.subRaceLabel = Component.empty();
        this.topAbilities = new ArrayList<>();
        this.bottomAbilities = new ArrayList<>();
        this.page = Page.FACTION;
        this.renderTick = 0;
        this.subRaceId = 0;
        this.hasSubRaces = false;
        this.options = new int[3];
        this.abilitySlots = Lists.newArrayList();
        this.hasRandomizedRace = hasRandomizedRace;
        this.allowSubRaceSelect = allowMinkRaceSelect;
        
        this.factions = ModRegistries.FACTIONS.stream().filter(ICharacterCreatorEntry::isInBook).toArray(Faction[]::new);
        this.races = ModRegistries.RACES.stream().filter(Race::isMainRace).filter(ICharacterCreatorEntry::isInBook).toArray(Race[]::new);
        this.styles = ModRegistries.FIGHTING_STYLES.stream().filter(ICharacterCreatorEntry::isInBook).toArray(FightingStyle[]::new);
    }

    @Override
    public void renderBackground(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        // Do nothing to prevent default blur/gradient overlay
    }

    @Override
    public void render(GuiGraphics graphics, int x, int y, float f) {
        graphics.fill(0, 0, this.width, this.height, 0x80000000); // Semi-transparent black background
        
        RenderSystem.setShaderTexture(0, ModResources.BOOK);
        int centerX = this.width / 2;
        int centerY = this.height / 2;
        
        graphics.pose().pushPose();
        graphics.pose().translate((float)(centerX - 150), (float)(centerY - 120), 0.0F);
        graphics.pose().scale(1.2F, 1.2F, 1.2F);
        graphics.blit(ModResources.BOOK, 0, 0, 0, 0, 256, 256);
        graphics.pose().popPose();
        
        if (!this.allowSubRaceSelect && this.renderTick % 150 == 0) {
            this.increaseSubRace();
        }

        this.drawCategoryIcon(graphics, this.icon, centerX - 15, centerY - 100, 0.7F);
        this.drawCategoryLabel(graphics, this.label, centerX + 75, centerY + 15, 1.5F);
        this.drawCategoryLabel(graphics, this.subRaceLabel, centerX + 75, centerY - 5, 1.1F);

        super.render(graphics, x, y, f);
        this.renderTick++;
    }

    @Override
    protected void init() {
        this.player = (AbstractClientPlayer) this.minecraft.player;
        int centerX = this.width / 2;
        int centerY = this.height / 2;
        
        int buttonX = centerX - 230;
        int buttonY = centerY - 100;
        
        this.factionButton = new BookSignSideButton(buttonX, buttonY, 110, 35, Component.translatable("gui.mineminenomi.faction"), (btn) -> {
            this.page = Page.FACTION;
            this.resetButtonState();
            this.factionButton.setIsPressed(true);
            this.tryToggleSubRaceButtons();
        });
        this.addRenderableWidget(this.factionButton);
        
        buttonY += 45;
        this.raceButton = new BookSignSideButton(buttonX, buttonY, 110, 35, Component.translatable("gui.mineminenomi.race"), (btn) -> {
            this.page = Page.RACE;
            this.resetButtonState();
            this.raceButton.setIsPressed(true);
            this.tryToggleSubRaceButtons();
        });
        if (!this.hasRandomizedRace) {
            this.addRenderableWidget(this.raceButton);
            buttonY += 45;
        }

        this.styleButton = new BookSignSideButton(buttonX, buttonY, 110, 35, Component.translatable("gui.mineminenomi.fighting_style"), (btn) -> {
            this.page = Page.FIGHTING_STYLE;
            this.resetButtonState();
            this.styleButton.setIsPressed(true);
            this.tryToggleSubRaceButtons();
        });
        this.addRenderableWidget(this.styleButton);
        
        int arrowY = centerY + 95;
        ArrowButton prevButton = new ArrowButton(centerX + 35, arrowY, 35, 15, (btn) -> {
            this.decreaseSelectedPage();
            this.tryToggleSubRaceButtons();
        });
        this.addRenderableWidget(prevButton);
        
        ArrowButton nextButton = new ArrowButton(centerX + 115, arrowY, 35, 15, (btn) -> {
            this.increaseSelectedPage();
            this.tryToggleSubRaceButtons();
        });
        nextButton.setFlipped();
        this.addRenderableWidget(nextButton);
        
        this.previousSubRaceButton = new BigArrowButton(centerX + 30, centerY - 30, 20, 100, (btn) -> this.decreaseSubRace());
        this.previousSubRaceButton.visible = false;
        this.addRenderableWidget(this.previousSubRaceButton);
        
        this.nextSubRaceButton = new BigArrowButton(centerX + 140, centerY - 30, 20, 100, (btn) -> this.increaseSubRace());
        this.nextSubRaceButton.setFlipped();
        this.nextSubRaceButton.visible = false;
        this.addRenderableWidget(this.nextSubRaceButton);
        
        SimpleButton finishButton = new SimpleButton(centerX - 35, centerY + 165, 70, 30, Component.translatable("gui.mineminenomi.finish"), (btn) -> this.completeCreation());
        finishButton.setTextOnly();
        finishButton.setFontSize(1.5F);
        this.addRenderableWidget(finishButton);
        
        this.updateSelection();
    }

    private void tryToggleSubRaceButtons() {
        this.previousSubRaceButton.visible = false;
        this.nextSubRaceButton.visible = false;
        if (this.page == Page.RACE && this.hasSubRaces && this.allowSubRaceSelect) {
            this.previousSubRaceButton.visible = true;
            this.nextSubRaceButton.visible = true;
        }
    }

    private void resetButtonState() {
        this.factionButton.setIsPressed(false);
        this.raceButton.setIsPressed(false);
        this.styleButton.setIsPressed(false);
        this.updateSelection();
    }

    private void completeCreation() {
        java.util.Optional<ResourceLocation> faction = this.options[0] > 0 ? java.util.Optional.of(this.factions[this.options[0] - 1].getRegistryName()) : java.util.Optional.empty();
        java.util.Optional<ResourceLocation> race = this.options[1] > 0 ? java.util.Optional.of(this.races[this.options[1] - 1].getRegistryName()) : java.util.Optional.empty();
        java.util.Optional<ResourceLocation> style = this.options[2] > 0 ? java.util.Optional.of(this.styles[this.options[2] - 1].getRegistryName()) : java.util.Optional.empty();
        java.util.Optional<ResourceLocation> subRace = (this.hasSubRaces && this.subRaces != null && this.subRaceId < this.subRaces.length) ? java.util.Optional.of(this.subRaces[this.subRaceId].getRegistryName()) : java.util.Optional.empty();

        net.neoforged.neoforge.network.PacketDistributor.sendToServer(new xyz.pixelatedw.mineminenomi.networking.packets.CFinishCharacterCreatorPacket(faction, race, subRace, style));
        
        this.minecraft.setScreen(null);
    }

    private void updateSelection() {
        this.maxOpt = switch (this.page) {
            case FACTION -> this.factions.length + 1;
            case FIGHTING_STYLE -> this.styles.length + 1;
            case RACE -> this.races.length + 1;
        };

        CharacterCreatorSelectionInfo info = ICharacterCreatorEntry.RANDOM.getSelectionInfo();
        Component label = Component.translatable("gui.mineminenomi.random");
        Component subRaceLabel = Component.empty();
        this.hasSubRaces = false;

        if (this.getSelectedId() > 0) {
            switch (this.page) {
                case FACTION -> {
                    Faction faction = this.factions[this.getSelectedId() - 1];
                    info = faction.getSelectionInfo();
                    label = faction.getLabel();
                }
                case FIGHTING_STYLE -> {
                    FightingStyle style = this.styles[this.getSelectedId() - 1];
                    info = style.getSelectionInfo();
                    label = style.getLabel();
                }
                case RACE -> {
                    Race race = this.races[this.getSelectedId() - 1];
                    if (race.hasSubRaces()) {
                        this.subRaces = race.getSubRaces().stream().map(java.util.function.Supplier::get).filter(ICharacterCreatorEntry::isInBook).toArray(Race[]::new);
                        if (this.subRaces.length > 0) {
                            this.hasSubRaces = true;
                            subRaceLabel = Component.literal(this.subRaces[this.subRaceId].getLabel().getString().replace(race.getLabel().getString(), ""));
                        }
                    }
                    info = race.getSelectionInfo();
                    label = race.getLabel();
                }
            }
        }

        this.icon = info.getIcon();
        this.label = label;
        this.subRaceLabel = subRaceLabel;
        this.topAbilities = info.getTopAbilities().stream().map(s -> (xyz.pixelatedw.mineminenomi.api.abilities.Ability) s.get()).toList();
        this.bottomAbilities = info.getBottomAbilities().stream().map(s -> (xyz.pixelatedw.mineminenomi.api.abilities.Ability) s.get()).toList();
        
        this.createAbilitySlotButtons();
    }

    private void createAbilitySlotButtons() {
        this.abilitySlots.forEach(this::removeWidget);
        this.abilitySlots.clear();
        
        int centerX = this.width / 2;
        int centerY = this.height / 2;
        
        int i = 0;
        int xOffset = 0;
        int yOffset = 0;

        for (Ability ability : this.topAbilities) {
            if (i % 3 == 0) {
                xOffset = 0;
                yOffset += 30;
            } else {
                xOffset += 38;
            }

            AbilitySlotButton slot = new AbilitySlotButton(ability, centerX - 130 + xOffset, centerY - 110 + yOffset, 22, 22, this.player, (btn) -> {});
            this.abilitySlots.add(slot);
            this.addRenderableWidget(slot);
            i++;
        }

        i = 0;
        xOffset = 0;
        yOffset = 0;

        for (Ability ability : this.bottomAbilities) {
            if (i % 3 == 0) {
                xOffset = 0;
                yOffset += 30;
            } else {
                xOffset += 38;
            }

            AbilitySlotButton slot = new AbilitySlotButton(ability, centerX - 130 + xOffset, centerY - 20 + yOffset, 22, 22, this.player, (btn) -> {});
            this.abilitySlots.add(slot);
            this.addRenderableWidget(slot);
            i++;
        }
    }

    private void drawCategoryIcon(GuiGraphics graphics, ResourceLocation icon, int posX, int posY, float scale) {
        graphics.pose().pushPose();
        graphics.pose().translate((float)posX, (float)posY, 2.0F);
        graphics.pose().scale(scale, scale, 1.0F);
        graphics.blit(this.icon, 0, 0, 0, 0, 256, 256);
        graphics.pose().popPose();
    }

    private void drawCategoryLabel(GuiGraphics graphics, Component text, int posX, int posY, float scale) {
        graphics.pose().pushPose();
        graphics.pose().translate((float)posX, (float)posY, 2.0F);
        graphics.pose().scale(scale, scale, 1.0F);
        graphics.drawString(this.font, text, -this.font.width(text) / 2, 0, 0x000000, false);
        graphics.pose().popPose();
    }

    private int getSelectedId() {
        return this.options[this.page.ordinal()];
    }

    public void increaseSelectedPage() {
        if (this.options[this.page.ordinal()] + 1 < this.maxOpt) {
            this.options[this.page.ordinal()]++;
        } else {
            this.options[this.page.ordinal()] = 0;
        }
        this.updateSelection();
    }

    public void decreaseSelectedPage() {
        if (this.options[this.page.ordinal()] - 1 > -1) {
            this.options[this.page.ordinal()]--;
        } else {
            this.options[this.page.ordinal()] = this.maxOpt - 1;
        }
        this.updateSelection();
    }

    public void increaseSubRace() {
        if (this.subRaceId == this.subRaces.length - 1) {
            this.subRaceId = 0;
        } else {
            this.subRaceId++;
        }
        this.updateSubRaceSelection();
    }

    public void decreaseSubRace() {
        if (this.subRaceId == 0) {
            this.subRaceId = this.subRaces.length - 1;
        } else {
            this.subRaceId--;
        }
        this.updateSubRaceSelection();
    }

    private void updateSubRaceSelection() {
        this.icon = this.subRaces[this.subRaceId].getSelectionInfo().getIcon();
        this.subRaceLabel = Component.literal(this.subRaces[this.subRaceId].getLabel().getString().replace(this.label.getString(), ""));
    }

    private enum Page {
        FACTION,
        RACE,
        FIGHTING_STYLE
    }
}
