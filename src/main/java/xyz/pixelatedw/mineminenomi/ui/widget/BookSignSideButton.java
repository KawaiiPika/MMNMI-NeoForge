package xyz.pixelatedw.mineminenomi.ui.widget;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import xyz.pixelatedw.mineminenomi.api.helpers.RendererHelper;
import xyz.pixelatedw.mineminenomi.init.ModResources;

public class BookSignSideButton extends Button {
    private boolean isPressed;
    private boolean isFlipped;
    private int textureWidth = 120;
    private ResourceLocation iconTexture;
    private float iconScale = 1.0F;
    private int iconPosX;
    private int iconPosY;

    public BookSignSideButton(int posX, int posY, int width, int height, Component string, Button.OnPress onPress) {
        super(posX, posY, width, height, string, onPress, DEFAULT_NARRATION);
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

        Minecraft minecraft = Minecraft.getInstance();
        Font fontrenderer = minecraft.font;
        RenderSystem.setShaderColor(red, green, blue, this.alpha);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.enableDepthTest();
        
        graphics.blit(ModResources.BUTTON, this.getX(), this.getY(), this.width, this.height, this.isFlipped ? this.textureWidth : 0, 8.0F, this.textureWidth, 50, 120, 50);
        
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        
        if (this.iconTexture != null) {
            graphics.pose().pushPose();
            RenderSystem.enableBlend();
            graphics.pose().translate((float)this.iconPosX, (float)this.iconPosY, 151.0F);
            graphics.pose().scale(this.iconScale, this.iconScale, this.iconScale);
            RendererHelper.drawIcon(this.iconTexture, graphics.pose(), 0.0F, 0.0F, 1.0F, 16.0F, 16.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            graphics.pose().popPose();
        }

        int color = this.getFGColor() | Mth.floor(this.alpha * 255.0F) << 24;
        int texLen = fontrenderer.width(this.getMessage()) / 2;
        RendererHelper.drawStringWithBorder(fontrenderer, graphics, this.getMessage(), this.getX() + this.width / 2 - texLen, this.getY() + (this.height - 8) / 2, color);
        
        graphics.pose().popPose();
        RenderSystem.disableDepthTest();
    }

    public void setIsPressed(boolean isPressed) {
        this.isPressed = isPressed;
    }

    public void setFlipped(boolean flipped) {
        this.isFlipped = flipped;
    }

    public void setTextureWidth(int width) {
        this.textureWidth = width;
    }

    public void setIcon(ResourceLocation texture, int iconPosX, int iconPosY, float scale) {
        this.iconTexture = texture;
        this.iconPosX = iconPosX;
        this.iconPosY = iconPosY;
        this.iconScale = scale;
    }
}
