package xyz.pixelatedw.mineminenomi.effects;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectCategory;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.effects.IScreenShaderEffect;

public class ImpactFrameEffect extends BaseEffect implements IScreenShaderEffect {
   private static final ResourceLocation SHADER = ResourceLocation.fromNamespaceAndPath("mineminenomi", "shaders/post/impact_frame.json");

   public ImpactFrameEffect() {
      super(MobEffectCategory.NEUTRAL, WyHelper.hexToRGB("#000000").getRGB());
   }

   public boolean isRemoveable() {
      return false;
   }

   public ResourceLocation getScreenShader() {
      return SHADER;
   }
}
