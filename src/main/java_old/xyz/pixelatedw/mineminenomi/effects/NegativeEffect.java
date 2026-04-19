package xyz.pixelatedw.mineminenomi.effects;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.effects.IScreenShaderEffect;

public class NegativeEffect extends BaseEffect implements IScreenShaderEffect {
   private static final ResourceLocation SHADER = ResourceLocation.parse("shaders/post/desaturate.json");

   public NegativeEffect() {
      super(MobEffectCategory.HARMFUL, WyHelper.hexToRGB("#b6a8cb").getRGB());
      this.m_19472_(Attributes.f_22279_, "11147784-f615-47da-a28c-20c721cf5e9f", (double)-1.0F, Operation.MULTIPLY_TOTAL);
      this.m_19472_(Attributes.f_22283_, "d23c3332-a0bf-4dde-80e1-1a6b936caf41", (double)-1.0F, Operation.MULTIPLY_TOTAL);
      this.m_19472_(Attributes.f_22281_, "d23c3332-a0bf-4dde-80e1-1a6b936caf41", (double)-5.0F, Operation.MULTIPLY_TOTAL);
   }

   public ResourceLocation getScreenShader() {
      return SHADER;
   }

   public boolean isLingering() {
      return true;
   }
}
