package xyz.pixelatedw.mineminenomi.ui.widget;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import xyz.pixelatedw.mineminenomi.init.ModResources;

public class ArrowButton extends Button {
    private boolean isPressed;
    private boolean isFlipped;
    private int kind;

    public ArrowButton(int posX, int posY, int width, int height, Button.OnPress onPress) {
        super(posX, posY, width, height, Component.empty(), onPress, DEFAULT_NARRATION);
    }

    @Override
    protected void renderWidget(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        RenderSystem.enableDepthTest();
        graphics.pose().pushPose();
        float red = 1.0F;
        float green = 1.0F;
        float blue = 1.0F;
        
        if (this.active && !this.isPressed) {
            if (this.isHovered) {
                graphics.pose().translate(0.0F, 0.5F, 1.0F);
                blue = 0.6F;
                green = 0.6F;
                red = 0.6F;
            }
        } else {
            blue = 0.4F;
            green = 0.4F;
            red = 0.4F;
        }

        RenderSystem.setShaderColor(red, green, blue, this.alpha);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.enableDepthTest();
        graphics.blit(this.getTexture(), this.getX(), this.getY(), 0.0F, 0.0F, this.width, this.height, this.width, this.height);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        graphics.pose().popPose();
        RenderSystem.disableDepthTest();
    }

    public void setIsPressed(boolean isPressed) {
        this.isPressed = isPressed;
    }

    public void setFlipped() {
        this.isFlipped = true;
    }

    public void setWoodTexture() {
        this.kind = 1;
    }

    public ResourceLocation getTexture() {
        switch (this.kind) {
            case 1: return this.isFlipped ? ModResources.WOOD_ARROW_RIGHT : ModResources.WOOD_ARROW_LEFT;
            default: return this.isFlipped ? ModResources.RED_ARROW_RIGHT : ModResources.RED_ARROW_LEFT;
        }
    }
}
