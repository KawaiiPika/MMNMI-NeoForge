package xyz.pixelatedw.mineminenomi.ui.widget;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.helpers.RendererHelper;
import xyz.pixelatedw.mineminenomi.api.ui.TexturedRectUI;
import xyz.pixelatedw.mineminenomi.init.ModResources;

public class AbilitySlotButton extends Button {
    private static final Component EMPTY = Component.empty();
    private final AbstractClientPlayer player;
    private float red = 1.0F;
    private float green = 1.0F;
    private float blue = 1.0F;
    private float[] hoverColor = new float[]{0.0F, 0.5F, 1.0F};
    private float[] pressedColor = new float[]{0.0F, 0.25F, 1.0F};
    private int slotIndex;
    private Ability ability;
    private boolean isPressed;
    private TexturedRectUI rect;

    public AbilitySlotButton(Ability ability, int posX, int posY, int width, int height, AbstractClientPlayer player, Button.OnPress onPress) {
        super(posX, posY, width, height, EMPTY, onPress, DEFAULT_NARRATION);
        this.rect = new TexturedRectUI(ModResources.WIDGETS);
        this.player = player;
        this.ability = ability;
    }

    @Override
    protected void renderWidget(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        graphics.pose().pushPose();
        graphics.pose().translate(0.0F, 0.0F, 250.0F);
        
        if (this.visible) {
            this.isHovered = mouseX >= this.getX() && mouseY >= this.getY() && mouseX < this.getX() + this.width && mouseY < this.getY() + this.height;
            RenderSystem.enableBlend();
            
            // Draw Vanilla-like slot background
            RendererHelper.drawAbilitySlot(graphics, this.getX(), this.getY(), 23, 23);
            
            if (this.isHovered) {
                RendererHelper.drawSelectionHighlight(graphics, this.getX(), this.getY(), 23, 23);
            }
            RenderSystem.disableBlend();
            
            if (this.ability != null) {
                ResourceLocation res = this.ability.getTexture();
                RendererHelper.drawIcon(res, graphics.pose(), (float)(this.getX() + 4), (float)(this.getY() + 4), 400.0F, 16.0F, 16.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            }
        }

        graphics.pose().popPose();
    }

    public void setIsPressed(boolean flag) {
        this.isPressed = flag;
    }

    public void setAbility(Ability ability) {
        this.ability = ability;
    }

    public Ability getAbility() {
        return this.ability;
    }

    public void setSlotIndex(int index) {
        this.slotIndex = index;
    }

    public int getSlotIndex() {
        return this.slotIndex;
    }
}
