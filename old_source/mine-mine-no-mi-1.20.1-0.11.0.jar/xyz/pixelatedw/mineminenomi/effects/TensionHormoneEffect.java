package xyz.pixelatedw.mineminenomi.effects;

import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.init.ModAttributes;

public class TensionHormoneEffect extends BaseEffect {
   public TensionHormoneEffect() {
      super(MobEffectCategory.BENEFICIAL, WyHelper.hexToRGB("#db545c").getRGB());
      this.m_19472_(Attributes.f_22281_, "c24353cb-2efd-491f-a3f3-55a853d707ef", (double)0.5F, Operation.MULTIPLY_TOTAL);
      this.m_19472_(Attributes.f_22279_, "1abe0056-96d4-4cf8-962c-cbf7a54f28f3", (double)0.5F, Operation.MULTIPLY_TOTAL);
      this.m_19472_((Attribute)ModAttributes.JUMP_HEIGHT.get(), "dd83d35f-5190-4840-a582-f538300ce0af", (double)0.5F, Operation.MULTIPLY_TOTAL);
      this.m_19472_(Attributes.f_22278_, "7d355019-7ef9-4beb-bcba-8b2608a73380", (double)10.0F, Operation.ADDITION);
   }

   public void stopEffect(LivingEntity entity) {
      entity.m_7292_(new MobEffectInstance(MobEffects.f_19604_, 400, 1));
   }

   public boolean isLingering() {
      return true;
   }
}
