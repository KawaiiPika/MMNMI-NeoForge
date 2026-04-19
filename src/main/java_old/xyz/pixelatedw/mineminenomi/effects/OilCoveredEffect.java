package xyz.pixelatedw.mineminenomi.effects;

import java.awt.Color;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectCategory;
import xyz.pixelatedw.mineminenomi.api.effects.ITextureOverlayEffect;
import xyz.pixelatedw.mineminenomi.api.effects.ITransmisibleByTouchEffect;
import xyz.pixelatedw.mineminenomi.init.ModResources;

public class OilCoveredEffect extends BaseEffect implements ITextureOverlayEffect, ITransmisibleByTouchEffect {
   private static final Color OVERLAY_COLOR = new Color(0.0F, 0.0F, 0.0F, 1.0F);

   public OilCoveredEffect() {
      super(MobEffectCategory.NEUTRAL, 0);
   }

   public boolean shouldUpdateClient() {
      return true;
   }

   public Color getTextureOverlayColor() {
      return OVERLAY_COLOR;
   }

   public ResourceLocation getBodyTexture(int duration, int amplifier) {
      return ModResources.FROSTBITE_3;
   }

   public boolean isTransmisibleByTouch() {
      return true;
   }
}
