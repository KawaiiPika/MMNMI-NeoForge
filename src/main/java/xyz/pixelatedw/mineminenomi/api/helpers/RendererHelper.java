package xyz.pixelatedw.mineminenomi.api.helpers;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat.Mode;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import org.joml.Matrix4f;
import org.joml.Matrix3f;
import net.minecraft.client.renderer.texture.OverlayTexture;

public class RendererHelper {

    public static void drawStringWithBorder(Font font, GuiGraphics graphics, Component text, int posX, int posY, int color) {
        drawStringWithBorder(font, graphics, text.getVisualOrderText(), posX, posY, color);
    }

    public static void drawStringWithBorder(Font font, GuiGraphics graphics, FormattedCharSequence text, int posX, int posY, int color) {
        drawStringWithBorder(font, graphics, text, posX, posY, color, 0xFF000000);
    }

    public static void drawStringWithBorder(Font font, GuiGraphics graphics, FormattedCharSequence text, int posX, int posY, int color, int outlineColor) {
        if (text != null) {
            // Draw 1px outline
            graphics.drawString(font, text, posX + 1, posY, outlineColor, false);
            graphics.drawString(font, text, posX - 1, posY, outlineColor, false);
            graphics.drawString(font, text, posX, posY + 1, outlineColor, false);
            graphics.drawString(font, text, posX, posY - 1, outlineColor, false);
            
            // Draw main text
            graphics.drawString(font, text, posX, posY, color, false);
        }
    }

    public static void drawStringWithBorder(Font font, PoseStack matrixStack, MultiBufferSource buffer, FormattedCharSequence text, int posX, int posY, int color, int outlineColor) {
        if (text != null) {
            font.drawInBatch(text, (float) posX + 1, (float) posY, outlineColor, false, matrixStack.last().pose(), buffer, Font.DisplayMode.NORMAL, 0, 15728880);
            font.drawInBatch(text, (float) posX - 1, (float) posY, outlineColor, false, matrixStack.last().pose(), buffer, Font.DisplayMode.NORMAL, 0, 15728880);
            font.drawInBatch(text, (float) posX, (float) posY + 1, outlineColor, false, matrixStack.last().pose(), buffer, Font.DisplayMode.NORMAL, 0, 15728880);
            font.drawInBatch(text, (float) posX, (float) posY - 1, outlineColor, false, matrixStack.last().pose(), buffer, Font.DisplayMode.NORMAL, 0, 15728880);
            font.drawInBatch(text, (float) posX, (float) posY, color, false, matrixStack.last().pose(), buffer, Font.DisplayMode.NORMAL, 0, 15728880);
        }
    }

    public static void drawIcon(ResourceLocation icon, PoseStack pose, float x, float y, float z, float u, float v, float red, float green, float blue, float alpha) {
        drawIcon(icon, pose, x, y, z, u, v, red, green, blue, alpha, false);
    }

    public static void drawIcon(ResourceLocation icon, PoseStack pose, float x, float y, float z, float u, float v, float red, float green, float blue, float alpha, boolean flip) {
        if (icon == null) return;
        
        Matrix4f matrix = pose.last().pose();
        float uvDir = flip ? -1.0F : 1.0F;
        
        RenderSystem.enableBlend();
        RenderSystem.setShaderTexture(0, icon);
        RenderSystem.setShader(GameRenderer::getPositionTexColorShader);
        
        Tesselator tessellator = Tesselator.getInstance();
        BufferBuilder bufferbuilder = tessellator.begin(Mode.QUADS, DefaultVertexFormat.POSITION_TEX_COLOR);
        
        bufferbuilder.addVertex(matrix, x, y + v, z).setColor(red, green, blue, alpha).setUv(0.0F, uvDir);
        bufferbuilder.addVertex(matrix, x + u, y + v, z).setColor(red, green, blue, alpha).setUv(uvDir, uvDir);
        bufferbuilder.addVertex(matrix, x + u, y, z).setColor(red, green, blue, alpha).setUv(uvDir, 0.0F);
        bufferbuilder.addVertex(matrix, x, y, z).setColor(red, green, blue, alpha).setUv(0.0F, 0.0F);
        
        BufferUploader.drawWithShader(bufferbuilder.buildOrThrow());
        RenderSystem.disableBlend();
    }

    public static void drawQuad(PoseStack matrixStack, VertexConsumer buffer, float red, float green, float blue, float alpha, float x, float y, float width, float height, float u1, float u2, float v1, float v2, int lightmap) {
        PoseStack.Pose entry = matrixStack.last();
        Matrix4f matrix4f = entry.pose();
        Matrix3f matrix3f = entry.normal();
        drawVertex(matrix4f, matrix3f, buffer, red, green, blue, alpha, x, y + height, u1, v2, lightmap);
        drawVertex(matrix4f, matrix3f, buffer, red, green, blue, alpha, x + width, y + height, u2, v2, lightmap);
        drawVertex(matrix4f, matrix3f, buffer, red, green, blue, alpha, x + width, y, u2, v1, lightmap);
        drawVertex(matrix4f, matrix3f, buffer, red, green, blue, alpha, x, y, u1, v1, lightmap);
    }

    public static void drawVertex(Matrix4f matrixPos, Matrix3f matrixNormal, VertexConsumer buffer, float red, float green, float blue, float alpha, float x, float y, float texU, float texV, int lightmap) {
        buffer.addVertex(matrixPos, x, y, 0.0F)
              .setColor(red, green, blue, alpha)
              .setUv(texU, texV)
              .setOverlay(OverlayTexture.NO_OVERLAY)
              .setLight(lightmap)
              .setNormal(0.0F, 1.0F, 0.0F);
    }

    public static void drawAbilitySlot(GuiGraphics graphics, int x, int y, int width, int height) {
        // The Vanilla-like slot container from the mod's widgets
        graphics.blit(xyz.pixelatedw.mineminenomi.init.ModResources.WIDGETS, x, y, 0, 0, width, height);
    }

    public static void drawSelectionHighlight(GuiGraphics graphics, int x, int y, int width, int height) {
        // The Vanilla-like selection highlight
        graphics.blitSprite(ResourceLocation.withDefaultNamespace("hud/hotbar_selection"), x - 1, y - 1, width + 2, height + 2);
    }
}
