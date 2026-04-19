package xyz.pixelatedw.mineminenomi.effects;

import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraftforge.common.ForgeMod;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.effects.IChangeSwingSpeedEffect;
import xyz.pixelatedw.mineminenomi.init.ModAttributes;

public class NoroSlownessEffect extends BaseEffect implements IChangeSwingSpeedEffect {
   public NoroSlownessEffect() {
      super(MobEffectCategory.HARMFUL, WyHelper.hexToRGB("#fa5cb1").getRGB());
      this.m_19472_(Attributes.f_22283_, "8ab1e3cd-9688-402b-8876-73f314e174d2", (double)-0.5F, Operation.MULTIPLY_TOTAL);
      this.m_19472_(Attributes.f_22279_, "bb904ec8-b548-4e1b-82ba-df237ad06f65", (double)-0.5F, Operation.MULTIPLY_TOTAL);
      this.m_19472_((Attribute)ForgeMod.SWIM_SPEED.get(), "e997a027-cb40-4900-a3b5-e7d1f6976a48", (double)-0.5F, Operation.MULTIPLY_TOTAL);
      this.m_19472_((Attribute)ModAttributes.JUMP_HEIGHT.get(), "61346567-335f-4a4d-a4a6-327faec00aa3", (double)-0.5F, Operation.MULTIPLY_TOTAL);
      this.m_19472_((Attribute)ModAttributes.TIME_PROGRESSION.get(), "15494972-058f-4202-8190-a8a6f64a248d", (double)-0.1F, Operation.ADDITION);
   }

   public float swingSpeedModifier(int duration, int amplifier) {
      return (float)(2 + amplifier);
   }

   public boolean isLingering() {
      return true;
   }
}
