package xyz.pixelatedw.mineminenomi.api.effects;

import java.awt.Color;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import xyz.pixelatedw.mineminenomi.init.ModRenderTypes;

public interface ITextureOverlayEffect {
   Color DEFAULT_OVERLAY_COLOR = new Color(1.0F, 1.0F, 1.0F, 1.0F);

   ResourceLocation getBodyTexture(int var1, int var2);

   default ResourceLocation getViewTexture(int duration, int amplifier) {
      return this.getBodyTexture(duration, amplifier);
   }

   default Color getTextureOverlayColor() {
      return DEFAULT_OVERLAY_COLOR;
   }

   @OnlyIn(Dist.CLIENT)
   default RenderType getRenderType() {
      return ModRenderTypes.TRANSPARENT_COLOR;
   }
}
