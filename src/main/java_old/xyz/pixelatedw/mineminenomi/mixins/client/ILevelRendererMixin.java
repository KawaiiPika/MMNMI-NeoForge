package xyz.pixelatedw.mineminenomi.mixins.client;

import com.mojang.blaze3d.pipeline.RenderTarget;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.RenderBuffers;
import net.minecraft.client.renderer.ViewArea;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin({LevelRenderer.class})
public interface ILevelRendererMixin {
   @Accessor("entityTarget")
   void setEntityTarget(RenderTarget var1);

   @Accessor("viewArea")
   void setViewFrustum(ViewArea var1);

   @Accessor("viewArea")
   ViewArea getViewFrustum();

   @Accessor("renderBuffers")
   RenderBuffers getRenderTypeTextures();
}
