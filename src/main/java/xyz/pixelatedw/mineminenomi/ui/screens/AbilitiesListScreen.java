package xyz.pixelatedw.mineminenomi.ui.screens;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.PacketDistributor;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.data.entity.PlayerStats;
import xyz.pixelatedw.mineminenomi.init.ModAbilities;
import xyz.pixelatedw.mineminenomi.init.ModResources;
import xyz.pixelatedw.mineminenomi.networking.packets.CEquipAbilityPacket;
import xyz.pixelatedw.mineminenomi.networking.packets.CRemoveAbilityPacket;
import xyz.pixelatedw.mineminenomi.ui.panel.AbilitiesListScrollPanel;
import xyz.pixelatedw.mineminenomi.ui.widget.AbilitySlotButton;

import java.util.ArrayList;
import java.util.List;

public class AbilitiesListScreen extends Screen {
    private AbilitiesListScrollPanel scrollPanel;
    private final List<AbilitySlotButton> slotButtons = new ArrayList<>();
    private final List<xyz.pixelatedw.mineminenomi.ui.widget.BookSignSideButton> categoryButtons = new ArrayList<>();
    public int slotSelected = -1;
    private Ability draggedAbility;
    private int draggedFromSlot = -1;

    public AbilitiesListScreen() {
        super(Component.translatable("gui.mineminenomi.abilities"));
    }

    @Override
    protected void init() {
        int posX = this.width;
        int posY = this.height;

        this.scrollPanel = new AbilitiesListScrollPanel(this, posX / 2 - 102, posY / 2 - 92, 210, 130);
        addWidget(this.scrollPanel);

        // Category tabs
        PlayerStats stats = PlayerStats.get(Minecraft.getInstance().player);
        
        // Dynamic Category Tabs
        int leftTabY = posY / 2 - 100;
        int rightTabY = posY / 2 - 100;
        boolean firstCategorySet = false;
        for (int i = 0; i < 6; i++) {
            final int categoryIdx = i;
            List<xyz.pixelatedw.mineminenomi.api.abilities.Ability> categoryAbilities = this.scrollPanel.getAbilitiesForCategory(i, stats);
            
            // Skip empty categories, except Devil Fruit if they have one (even if it's currently empty of abilities, it acts as a placeholder)
            if (categoryAbilities.isEmpty() && (i != 0 || !stats.hasDevilFruit())) continue;

            if (!firstCategorySet) {
                this.scrollPanel.setCategory(categoryIdx);
                firstCategorySet = true;
            }

            boolean isOnRight = i >= 4;
            int btnX = isOnRight ? posX / 2 + 105 : posX / 2 - 165;
            int currentTabY = isOnRight ? rightTabY : leftTabY;

            xyz.pixelatedw.mineminenomi.ui.widget.BookSignSideButton btn = new xyz.pixelatedw.mineminenomi.ui.widget.BookSignSideButton(btnX, currentTabY, 60, 50, Component.empty(), (b) -> { this.scrollPanel.setCategory(categoryIdx); });
            btn.setTextureWidth(60);
            if (isOnRight) btn.setFlipped(true);
            
            ResourceLocation icon = null;
            if (i == 0 && stats.hasDevilFruit()) {
                ResourceLocation df = stats.getDevilFruit().get();
                icon = ResourceLocation.fromNamespaceAndPath(df.getNamespace(), "textures/items/" + df.getPath() + ".png");
            } else if (!categoryAbilities.isEmpty()) {
                icon = categoryAbilities.get(0).getTexture();
            }
            
            if (icon != null) {
                int iconX = isOnRight ? posX / 2 + 112 : posX / 2 - 136;
                btn.setIcon(icon, iconX, currentTabY + 12, 1.45F);
            }
            categoryButtons.add(btn);
            this.addRenderableWidget(btn);
            
            if (isOnRight) rightTabY += 35;
            else leftTabY += 35;
        }

        // Create 8 ability slots (representative of one bar)
        for (int i = 0; i < 8; i++) {
            final int index = i;
            AbilitySlotButton btn = new AbilitySlotButton(null, posX / 2 - 100 + i * 25, posY - 31, 22, 21, (net.minecraft.client.player.AbstractClientPlayer)Minecraft.getInstance().player, (b) -> {
                onSlotClicked(index);
            });
            btn.setSlotIndex(index);
            slotButtons.add(btn);
            addRenderableWidget(btn);
        }
        
        updateSlots();
    }

    private void onSlotClicked(int index) {
        if (this.slotSelected == index) {
            this.slotSelected = -1;
        } else {
            this.slotSelected = index;
        }
        
        for (int i = 0; i < slotButtons.size(); i++) {
            slotButtons.get(i).setIsPressed(i == slotSelected);
        }
    }

    public void onAbilitySelected(Ability ability) {
        if (slotSelected >= 0) {
            ResourceLocation id = ModAbilities.REGISTRY.getKey(ability);
            if (id != null) {
                PlayerStats stats = PlayerStats.get(Minecraft.getInstance().player);
                int actualSlot = slotSelected + stats.getCombatBarSet() * 8;
                PacketDistributor.sendToServer(new CEquipAbilityPacket(actualSlot, id.toString()));
                updateSlots();
            }
        }
    }

    public void updateSlots() {
        PlayerStats stats = PlayerStats.get(Minecraft.getInstance().player);
        if (stats == null) return;

        int offset = stats.getCombatBarSet() * 8;
        for (int i = 0; i < 8; i++) {
            String abilityId = stats.getEquippedAbility(i + offset);
            Ability ability = null;
            if (!abilityId.isEmpty()) {
                ability = ModAbilities.REGISTRY.get(ResourceLocation.parse(abilityId));
            }
            slotButtons.get(i).setAbility(ability);
        }
    }

    @Override
    public void renderBackground(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        // Do nothing to prevent default blur/gradient overlay
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        graphics.fill(0, 0, this.width, this.height, 0x80000000); // Semi-transparent black background
        
        int posX = this.width;
        int posY = this.height;
        
        // 1. Boards (Background)
        graphics.blit(ModResources.BOARD, (posX - 250) / 2, (posY - 230) / 2, 0, 0, 256, 256);
        graphics.blit(ModResources.BOARD, (posX - 250) / 2, posY - 60, 0, 0, 256, 256);
        
        // 2. Tabs (Middle Layer) - super.render calls BookSignSideButton.renderWidget which is at Z=50
        super.render(graphics, mouseX, mouseY, partialTicks);

        // 3. Paper (Foreground for Tabs)
        graphics.pose().pushPose();
        graphics.pose().translate(0, 0, 60.0F);
        graphics.blit(ModResources.BLANK_NEW, (posX - 250) / 2, (posY - 230) / 2, 0, 0, 256, 256);
        graphics.blit(ModResources.BLANK_NEW, (posX - 250) / 2, posY - 60, 0, 0, 256, 256);
        graphics.pose().popPose();
        
        // 4. Content (Top Layer)
        if (this.scrollPanel != null) {
            graphics.pose().pushPose();
            graphics.pose().translate(0, 0, 100.0F);
            this.scrollPanel.render(graphics, mouseX, mouseY, partialTicks);
            graphics.pose().popPose();
        }

        // Removed paper backgrounds here

        graphics.pose().pushPose();
        graphics.pose().translate(0, 0, 100.0F);

        for (AbilitySlotButton btn : slotButtons) {
            btn.render(graphics, mouseX, mouseY, partialTicks);
        }
        
        if (this.draggedAbility != null) {
            xyz.pixelatedw.mineminenomi.api.helpers.RendererHelper.drawIcon(this.draggedAbility.getTexture(), graphics.pose(), (float)(mouseX - 8), (float)(mouseY - 8), 500.0F, 16.0F, 16.0F, 1.0F, 1.0F, 1.0F, 1.0F);
        }

        String barId = "1";
        xyz.pixelatedw.mineminenomi.api.helpers.RendererHelper.drawStringWithBorder(this.font, graphics, Component.literal(barId), (posX + 8) / 2 - (this.font.width(barId) + 104), posY - 24, java.awt.Color.WHITE.getRGB());
        graphics.drawCenteredString(this.font, this.title, posX / 2, (posY - 230) / 2 - 24, 0xFFFFFF);

        graphics.pose().popPose();
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        // Check if we clicked on a slot to start dragging from it
        for (AbilitySlotButton slot : slotButtons) {
            if (slot.isHovered() && slot.getAbility() != null) {
                this.draggedAbility = slot.getAbility();
                this.draggedFromSlot = slot.getSlotIndex();
                return true;
            }
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        if (this.draggedAbility != null) {
            boolean droppedInSlot = false;
            for (AbilitySlotButton slot : slotButtons) {
                if (slot.isHovered()) {
                    ResourceLocation id = ModAbilities.REGISTRY.getKey(this.draggedAbility);
                    if (id != null) {
                        PlayerStats stats = PlayerStats.get(Minecraft.getInstance().player);
                        int actualSlot = slot.getSlotIndex() + stats.getCombatBarSet() * 8;
                        PacketDistributor.sendToServer(new CEquipAbilityPacket(actualSlot, id.toString()));
                        droppedInSlot = true;
                    }
                    break;
                }
            }

            // If we dragged from a slot and dropped it outside, remove it
            if (!droppedInSlot && this.draggedFromSlot != -1) {
                PlayerStats stats = PlayerStats.get(Minecraft.getInstance().player);
                int actualSlot = this.draggedFromSlot + stats.getCombatBarSet() * 8;
                PacketDistributor.sendToServer(new CRemoveAbilityPacket(actualSlot));
            }

            this.draggedAbility = null;
            this.draggedFromSlot = -1;
            updateSlots();
            return true;
        }
        return super.mouseReleased(mouseX, mouseY, button);
    }

    public void setDraggedAbility(Ability ability) {
        this.draggedAbility = ability;
        this.draggedFromSlot = -1;
    }

    public static void renderAbilityTooltip(GuiGraphics graphics, int x, int y, Ability ability) {
        if (ability == null) return;
        graphics.renderTooltip(Minecraft.getInstance().font, ability.getDisplayName(), x, y);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
