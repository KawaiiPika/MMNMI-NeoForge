package xyz.pixelatedw.mineminenomi.effects;

import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import xyz.pixelatedw.mineminenomi.api.WyHelper;

public class ChiyupopoEffect extends BaseEffect {
   private float oldHealth = 0.0F;

   public ChiyupopoEffect() {
      super(MobEffectCategory.BENEFICIAL, WyHelper.hexToRGB("#FFC0CB").getRGB());
   }

   public void m_6742_(LivingEntity pLivingEntity, int pAmplifier) {
      if (pLivingEntity.m_21223_() < pLivingEntity.m_21233_()) {
         pLivingEntity.m_5634_(1.0F);
      }

   }

   public boolean m_6584_(int pDuration, int pAmplifier) {
      int k = 50 >> pAmplifier;
      if (k > 0) {
         return pDuration % k == 0;
      } else {
         return true;
      }
   }

   public void m_6385_(LivingEntity pLivingEntity, AttributeMap pAttributeMap, int pAmplifier) {
      super.m_6385_(pLivingEntity, pAttributeMap, pAmplifier);
      this.oldHealth = pLivingEntity.m_21223_();
   }

   public void m_6386_(LivingEntity pLivingEntity, AttributeMap pAttributeMap, int pAmplifier) {
      super.m_6386_(pLivingEntity, pAttributeMap, pAmplifier);
      pLivingEntity.m_21153_(this.oldHealth);
   }
}
