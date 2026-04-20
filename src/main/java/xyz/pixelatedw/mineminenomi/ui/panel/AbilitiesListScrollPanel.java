package xyz.pixelatedw.mineminenomi.ui.panel;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.helpers.RendererHelper;
import xyz.pixelatedw.mineminenomi.data.entity.PlayerStats;
import xyz.pixelatedw.mineminenomi.init.ModAbilities;
import xyz.pixelatedw.mineminenomi.ui.screens.AbilitiesListScreen;

import java.util.ArrayList;
import java.util.List;

public class AbilitiesListScrollPanel extends AbstractWidget {
    private final AbilitiesListScreen parent;
    private final List<Ability> abilities = new ArrayList<>();
    private double scrollAmount = 0;
    private final int itemHeight = 25;

    private int currentCategory = 0;

    public AbilitiesListScrollPanel(AbilitiesListScreen parent, int x, int y, int width, int height) {
        super(x, y, width, height, Component.empty());
        this.parent = parent;
        refreshAbilities();
    }
    
    public void setCategory(int category) {
        this.currentCategory = category;
        this.scrollAmount = 0;
        refreshAbilities();
    }

    public void refreshAbilities() {
        this.abilities.clear();
        PlayerStats stats = PlayerStats.get(Minecraft.getInstance().player);
        if (stats == null) return;
        
        this.abilities.addAll(getAbilitiesForCategory(currentCategory, stats));
    }

    public List<Ability> getAbilitiesForCategory(int category, PlayerStats stats) {
        List<Ability> list = new ArrayList<>();
        ModAbilities.REGISTRY.forEach(ability -> {
            if (isAbilityInCategory(ability, category, stats)) {
                list.add(ability);
            }
        });
        
        // Sorting logic
        list.sort((a1, a2) -> {
            String n1 = a1.getClass().getSimpleName().toLowerCase();
            String n2 = a2.getClass().getSimpleName().toLowerCase();
            
            if (category == 1) { // Passives
                int p1 = n1.contains("punch") || n1.contains("emptyhands") ? 0 : 1;
                int p2 = n2.contains("punch") || n2.contains("emptyhands") ? 0 : 1;
                if (p1 != p2) return Integer.compare(p1, p2);
            } else if (category == 2) { // Haki
                int p1 = n1.contains("haoshoku") ? 0 : (n1.contains("busoshoku") ? 1 : (n1.contains("kenbunshoku") ? 2 : 3));
                int p2 = n2.contains("haoshoku") ? 0 : (n2.contains("busoshoku") ? 1 : (n2.contains("kenbunshoku") ? 2 : 3));
                if (p1 != p2) return Integer.compare(p1, p2);
            } else if (category == 3) { // Style
                int p1 = n1.contains("command") ? 0 : 1;
                int p2 = n2.contains("command") ? 0 : 1;
                if (p1 != p2) return Integer.compare(p1, p2);
            }
            
            return n1.compareTo(n2);
        });
        
        return list;
    }

    private boolean isAbilityInCategory(Ability ability, int category, PlayerStats stats) {
        ResourceLocation requiredFruit = ability.getRequiredFruit();
        String name = ability.getClass().getSimpleName().toLowerCase();
        
        if (category == 0) { // Devil Fruit
            return !ability.isPassive() && requiredFruit != null && stats.getDevilFruit().isPresent() && stats.getDevilFruit().get().equals(requiredFruit);
        } else if (category == 1) { // Passives
            if (ability.isPassive()) {
                if (requiredFruit != null) {
                    return stats.getDevilFruit().isPresent() && stats.getDevilFruit().get().equals(requiredFruit);
                }
                return true;
            }
            return requiredFruit == null && (name.contains("passive") || name.contains("punch") || name.contains("knockdown") || name.contains("emptyhands"));
        } else if (category == 2) { // Haki
            return name.contains("haki");
        } else if (category == 3) { // Style / Rokushiki
            return name.contains("geppo") || name.contains("soru") || name.contains("rankyaku") || name.contains("shigan") || name.contains("rokushiki") || name.contains("tekkai") || name.contains("kamie") || name.contains("rokuogan");
        } else if (category == 4) { // Race
            // This is a placeholder, usually race abilities have specific names or markers
            return name.contains("fishman") || name.contains("karate") || name.contains("mink") || name.contains("electro") || name.contains("cyborg");
        } else if (category == 5) { // Misc
            return !isAbilityInCategory(ability, 0, stats) && !isAbilityInCategory(ability, 1, stats) && !isAbilityInCategory(ability, 2, stats) && !isAbilityInCategory(ability, 3, stats) && !isAbilityInCategory(ability, 4, stats);
        }
        return false;
    }

    @Override
    protected void renderWidget(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        graphics.enableScissor(this.getX(), this.getY(), this.getX() + this.width, this.getY() + this.height);
        
        int currentY = this.getY() - (int)scrollAmount;
        for (Ability ability : abilities) {
            if (currentY + itemHeight > this.getY() && currentY < this.getY() + this.height) {
                renderEntry(graphics, this.getX(), currentY, mouseX, mouseY, ability);
            }
            currentY += itemHeight;
        }
        
        graphics.disableScissor();

        // Draw Scroll Bar
        if (getMaxScroll() > 0) {
            int scrollBarX = this.getX() + this.width - 6;
            int scrollBarY = this.getY();
            int scrollBarHeight = this.height;
            
            // Background
            graphics.fill(scrollBarX, scrollBarY, scrollBarX + 5, scrollBarY + scrollBarHeight, 0xFF000000);
            graphics.fill(scrollBarX + 1, scrollBarY, scrollBarX + 4, scrollBarY + scrollBarHeight, 0xFFCCCCCC);
            
            // Thumb
            int thumbHeight = (int) ((float) height / (abilities.size() * itemHeight) * height);
            thumbHeight = Math.max(10, thumbHeight);
            int thumbY = scrollBarY + (int) (scrollAmount / getMaxScroll() * (scrollBarHeight - thumbHeight));
            graphics.fill(scrollBarX + 1, thumbY, scrollBarX + 4, thumbY + thumbHeight, 0xFF333333);
        }
    }

    private void renderEntry(GuiGraphics graphics, int x, int y, int mouseX, int mouseY, Ability ability) {
        boolean isHovered = mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + itemHeight;
        int color = isHovered ? 10014975 : 0xFFFFFF;

        // Draw Vanilla-like slot background
        RendererHelper.drawAbilitySlot(graphics, x + 5, y + 1, 23, 23);

        // Draw icon
        ResourceLocation icon = ability.getTexture();
        if (icon != null) {
            RendererHelper.drawIcon(icon, graphics.pose(), x + 9, y + 5, 400.0F, 16.0F, 16.0F, 1.0F, 1.0F, 1.0F, 1.0F);
        }

        // Draw name
        RendererHelper.drawStringWithBorder(Minecraft.getInstance().font, graphics, ability.getDisplayName(), x + 45, y + 8, color);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (!this.isHoveredOrFocused()) return false;
        
        double relativeY = mouseY - this.getY() + scrollAmount;
        int index = (int) (relativeY / itemHeight);
        
        if (index >= 0 && index < abilities.size()) {
            Ability ability = abilities.get(index);
            parent.setDraggedAbility(ability);
            return true;
        }
        
        return false;
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double scrollX, double scrollY) {
        this.scrollAmount = Math.max(0, Math.min(scrollAmount - scrollY * 10, getMaxScroll()));
        return true;
    }

    private int getMaxScroll() {
        return Math.max(0, (abilities.size() * itemHeight) - height);
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput narrationElementOutput) {}
}
