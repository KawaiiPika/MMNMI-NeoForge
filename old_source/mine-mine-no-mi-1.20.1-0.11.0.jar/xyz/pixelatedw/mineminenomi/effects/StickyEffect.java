package xyz.pixelatedw.mineminenomi.effects;

import java.awt.Color;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityExplosion;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.effects.IBindBodyEffect;
import xyz.pixelatedw.mineminenomi.api.effects.IColorOverlayEffect;
import xyz.pixelatedw.mineminenomi.init.ModAttributes;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.particles.effects.CommonExplosionParticleEffect;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class StickyEffect extends BaseEffect implements IColorOverlayEffect, IBindBodyEffect {
   private static final Color OVERLAY_COLOR = new Color(0.62F, 0.78F, 0.0F, 0.65F);

   public StickyEffect() {
      super(MobEffectCategory.HARMFUL, WyHelper.hexToRGB("#58db54").getRGB());
      this.m_19472_(Attributes.f_22278_, "9276cd71-345c-4420-ae9a-3c8f725908a3", (double)1.0F, Operation.ADDITION);
      this.m_19472_(Attributes.f_22279_, "b13ba7ec-7103-4160-a5ff-139534a44691", -0.2, Operation.MULTIPLY_TOTAL);
      this.m_19472_((Attribute)ModAttributes.JUMP_HEIGHT.get(), "fb997caf-43b6-4dc2-b5da-b504ab41545a", (double)-255.0F, Operation.ADDITION);
      this.m_19472_(Attributes.f_22283_, "c2731993-56d2-4c67-a6fe-cdd144bd9ff8", (double)-4.0F, Operation.ADDITION);
      this.m_19472_(Attributes.f_22281_, "5208c5bf-44d0-4111-9787-f63db22f180c", (double)-6.0F, Operation.ADDITION);
   }

   public boolean shouldUpdateClient() {
      return true;
   }

   public boolean m_6584_(int duration, int amplifier) {
      return true;
   }

   public void m_6742_(LivingEntity entity, int amplifier) {
      if (entity.m_6060_() && entity.f_19797_ > 0) {
         entity.m_20095_();
         AbilityExplosion explosion = new AbilityExplosion(entity, (IAbility)null, entity.m_20185_(), entity.m_20186_(), entity.m_20189_(), 6.0F);
         explosion.setExplosionSound(true);
         explosion.setDamageOwner(true);
         explosion.setDestroyBlocks(true);
         explosion.setFireAfterExplosion(true);
         explosion.setSmokeParticles((ParticleEffect)ModParticleEffects.COMMON_EXPLOSION.get(), CommonExplosionParticleEffect.EXPLOSION6);
         explosion.setDamageEntities(true);
         explosion.setStaticDamage(100.0F);
         explosion.m_46061_();
         entity.m_21195_(this);
      }

   }

   public Color getBodyOverlayColor(int duration, int amplifier) {
      return OVERLAY_COLOR;
   }

   public boolean isBlockingRotation() {
      return true;
   }

   public boolean isLingering() {
      return true;
   }
}
