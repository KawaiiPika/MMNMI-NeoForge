package xyz.pixelatedw.mineminenomi.effects;

import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraftforge.common.ForgeMod;
import xyz.pixelatedw.mineminenomi.init.ModAttributes;

public class DFCurseEffect extends BaseEffect {
   public DFCurseEffect() {
      super(MobEffectCategory.HARMFUL, 0);
      this.m_19472_(Attributes.f_22279_, "7107DE5E-7CE8-4030-940E-514C1F160890", (double)-0.7F, Operation.MULTIPLY_TOTAL);
      this.m_19472_((Attribute)ForgeMod.SWIM_SPEED.get(), "5a6ce15a-28c7-4d4b-91c5-cd98a061605b", (double)-0.7F, Operation.MULTIPLY_TOTAL);
      this.m_19472_(Attributes.f_22283_, "55FCED67-E92A-486E-9800-B47F202C4386", (double)-0.5F, Operation.MULTIPLY_TOTAL);
      this.m_19472_(Attributes.f_22281_, "22653B89-116E-49DC-9B6B-9971489B5BE5", (double)-4.0F, Operation.ADDITION);
      this.m_19472_((Attribute)ModAttributes.MINING_SPEED.get(), "e8207b5d-8635-40fc-8674-d7db8aae0b72", (double)-0.5F, Operation.ADDITION);
   }

   public boolean shouldUpdateClient() {
      return true;
   }
}
