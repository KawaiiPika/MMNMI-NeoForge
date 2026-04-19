package xyz.pixelatedw.mineminenomi.effects;

import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraftforge.common.ForgeMod;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.init.ModAttributes;

public class CarryingEffect extends BaseEffect {
   public CarryingEffect() {
      super(MobEffectCategory.NEUTRAL, WyHelper.hexToRGB("#000000").getRGB());
      this.m_19472_(Attributes.f_22279_, "37056f15-9d27-4687-9346-c0bd4e97e878", (double)-0.15F, Operation.MULTIPLY_TOTAL);
      this.m_19472_((Attribute)ForgeMod.SWIM_SPEED.get(), "85c3342e-da01-4db1-a9ff-b6963e6b009a", (double)-0.15F, Operation.MULTIPLY_TOTAL);
      this.m_19472_((Attribute)ModAttributes.JUMP_HEIGHT.get(), "5b64a909-6c13-4c64-8bde-9facf4c4e12c", (double)-0.15F, Operation.MULTIPLY_TOTAL);
      this.m_19472_(Attributes.f_22283_, "c59f0bc1-0b13-4db5-80e2-9cf75a9a64ee", (double)-0.15F, Operation.MULTIPLY_TOTAL);
      this.m_19472_((Attribute)ModAttributes.MINING_SPEED.get(), "0d0ce5e1-cb49-42de-a747-f67c8f473327", (double)-0.15F, Operation.MULTIPLY_TOTAL);
   }
}
