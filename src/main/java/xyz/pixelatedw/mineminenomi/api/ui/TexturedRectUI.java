package xyz.pixelatedw.mineminenomi.api.ui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat.Mode;
import java.util.Objects;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import org.joml.Matrix4f;

public class TexturedRectUI {
    private final ResourceLocation texture;
    private float zLevel = 0.0F;
    private float u = 0.0F;
    private float v = 0.0F;
    private float width = 256.0F;
    private float height = 256.0F;
    private float uScale = 1.0F;
    private float vScale = 1.0F;
    private float red = 1.0F;
    private float green = 1.0F;
    private float blue = 1.0F;
    private float alpha = 1.0F;
    private boolean flipX = false;
    private boolean flipY = false;

    public TexturedRectUI(ResourceLocation texture) {
        Objects.requireNonNull(texture, "Texture must never be null!");
        this.texture = texture;
    }

    public void draw(PoseStack matrixStack, float x, float y) {
        float uAtalsScale = (this.flipX ? -1.0F : 1.0F) / 256.0F;
        float vAtlasScale = (this.flipY ? -1.0F : 1.0F) / 256.0F;
        float width2 = this.width * this.uScale;
        float height2 = this.height * this.vScale;
        
        RenderSystem.setShaderTexture(0, this.texture);
        RenderSystem.setShader(GameRenderer::getPositionTexColorShader);
        
        Tesselator tesselator = Tesselator.getInstance();
        BufferBuilder wr = tesselator.begin(Mode.QUADS, DefaultVertexFormat.POSITION_TEX_COLOR);
        
        Matrix4f matrix = matrixStack.last().pose();
        wr.addVertex(matrix, x, y + height2, this.zLevel).setColor(this.red, this.green, this.blue, this.alpha).setUv(this.u * uAtalsScale, (this.v + this.height) * vAtlasScale);
        wr.addVertex(matrix, x + width2, y + height2, this.zLevel).setColor(this.red, this.green, this.blue, this.alpha).setUv((this.u + this.width) * uAtalsScale, (this.v + this.height) * vAtlasScale);
        wr.addVertex(matrix, x + width2, y, this.zLevel).setColor(this.red, this.green, this.blue, this.alpha).setUv((this.u + this.width) * uAtalsScale, this.v * vAtlasScale);
        wr.addVertex(matrix, x, y, this.zLevel).setColor(this.red, this.green, this.blue, this.alpha).setUv(this.u * uAtalsScale, this.v * vAtlasScale);
        
        BufferUploader.drawWithShader(wr.buildOrThrow());
    }

    public TexturedRectUI setUV(float u, float v) {
        this.u = u;
        this.v = v;
        return this;
    }

    public TexturedRectUI setSize(float width, float height) {
        this.width = width;
        this.height = height;
        return this;
    }

    public TexturedRectUI setZLevel(float zLevel) {
        this.zLevel = zLevel;
        return this;
    }

    public TexturedRectUI setColor(float red, float green, float blue, float alpha) {
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.alpha = alpha;
        return this;
    }

    public TexturedRectUI setScale(float scale) {
        this.uScale = scale;
        this.vScale = scale;
        return this;
    }

    public TexturedRectUI setFlipX(boolean flag) {
        this.flipX = flag;
        return this;
    }

    public TexturedRectUI setFlipY(boolean flag) {
        this.flipY = flag;
        return this;
    }
}
