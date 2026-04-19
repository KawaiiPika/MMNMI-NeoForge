package xyz.pixelatedw.mineminenomi.entities.ai.goals.dugong;

import java.util.UUID;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import xyz.pixelatedw.mineminenomi.api.entities.ai.TickedGoal;
import xyz.pixelatedw.mineminenomi.api.util.Interval;
import xyz.pixelatedw.mineminenomi.entities.mobs.animals.AbstractDugongEntity;

public class DugongRageGoal extends TickedGoal<AbstractDugongEntity> {
   private static final AttributeModifier RAGE_MODIFIER;
   private Interval canUseInterval = new Interval(10);

   public DugongRageGoal(AbstractDugongEntity entity) {
      super(entity);
   }

   public boolean m_8036_() {
      if (!this.canUseInterval.canTick()) {
         return false;
      } else if (((AbstractDugongEntity)this.entity).getRageTarget() == null) {
         return false;
      } else if (((AbstractDugongEntity)this.entity).m_21830_(((AbstractDugongEntity)this.entity).getRageTarget())) {
         ((AbstractDugongEntity)this.entity).setEnraged((LivingEntity)null);
         return false;
      } else {
         return true;
      }
   }

   public boolean m_8045_() {
      return ((AbstractDugongEntity)this.entity).getRageTarget() != null && ((AbstractDugongEntity)this.entity).getRageTarget().m_6084_();
   }

   public void m_8056_() {
      super.m_8056_();

      for(int i = 0; i < 5; ++i) {
         double offsetX = ((AbstractDugongEntity)this.entity).m_217043_().m_188583_() * 0.2;
         double offsetY = ((AbstractDugongEntity)this.entity).m_217043_().m_188583_() * 0.2;
         double offsetZ = ((AbstractDugongEntity)this.entity).m_217043_().m_188583_() * 0.2;
         ((ServerLevel)((AbstractDugongEntity)this.entity).m_20193_()).m_8767_(ParticleTypes.f_123792_, ((AbstractDugongEntity)this.entity).m_20185_() + offsetX, ((AbstractDugongEntity)this.entity).m_20186_() + (double)1.0F + offsetY, ((AbstractDugongEntity)this.entity).m_20189_() + offsetZ, 1, (double)0.0F, (double)0.0F, (double)0.0F, (double)0.0F);
      }

      ((AbstractDugongEntity)this.entity).m_6710_(((AbstractDugongEntity)this.entity).getRageTarget());
      ((AbstractDugongEntity)this.entity).setResting(false);
      ((AbstractDugongEntity)this.entity).stopTraining();
      AttributeInstance attr = ((AbstractDugongEntity)this.entity).m_21051_(Attributes.f_22281_);
      if (attr != null) {
         attr.m_22130_(RAGE_MODIFIER);
         attr.m_22118_(RAGE_MODIFIER);
      }

   }

   public void m_8041_() {
      super.m_8041_();
      ((AbstractDugongEntity)this.entity).setEnraged((LivingEntity)null);
      ((AbstractDugongEntity)this.entity).m_6710_((LivingEntity)null);
      AttributeInstance attr = ((AbstractDugongEntity)this.entity).m_21051_(Attributes.f_22281_);
      if (attr != null) {
         attr.m_22130_(RAGE_MODIFIER);
      }

   }

   static {
      RAGE_MODIFIER = new AttributeModifier(UUID.fromString("4b03a4b4-1eb5-464a-8312-0f9079044462"), "Rage Mode Multiplier", (double)10.0F, Operation.ADDITION);
   }
}
