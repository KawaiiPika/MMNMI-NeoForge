package xyz.pixelatedw.mineminenomi.effects;

import java.util.List;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.damagesources.TargetPredicate;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.TargetHelper;

public class CycloneTempoEffect extends BaseEffect {
   public CycloneTempoEffect() {
      super(MobEffectCategory.BENEFICIAL, WyHelper.hexToRGB("#000000").getRGB());
      this.m_19485_().clear();
      this.m_19472_(Attributes.f_22278_, "7d355019-7ef9-4beb-bcba-8b2608a73380", (double)1.0F, Operation.ADDITION);
   }

   public boolean m_6584_(int duration, int amplifier) {
      return duration % 10 == 0;
   }

   public void m_6742_(LivingEntity entity, int amplifier) {
      List<LivingEntity> list = TargetHelper.<LivingEntity>getEntitiesInArea(entity.m_9236_(), entity, (double)6.0F, TargetPredicate.DEFAULT_AREA_CHECK, LivingEntity.class);
      Vec3 center = entity.m_20182_();

      for(LivingEntity target : list) {
         Vec3 speed = target.m_20182_().m_82546_(center).m_82541_().m_82542_((double)2.0F, (double)1.0F, (double)2.0F);
         AbilityHelper.setDeltaMovement(target, speed.f_82479_, 0.4, speed.f_82481_);
      }

   }
}
