package xyz.pixelatedw.mineminenomi.entities.mobs.animals;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.entities.mobs.OPEntity;
import xyz.pixelatedw.mineminenomi.init.ModAttributes;

public class BlagoriEntity extends AbstractGorillaEntity {
   public BlagoriEntity(EntityType<? extends BlagoriEntity> type, Level world) {
      super(type, world);
      if (world != null && !world.f_46443_) {
         this.m_21051_((Attribute)ModAttributes.FALL_RESISTANCE.get()).m_22100_((double)2.0F);
         this.m_21051_((Attribute)ModAttributes.PUNCH_DAMAGE.get()).m_22100_((double)9.0F);
         this.m_21051_((Attribute)ModAttributes.TOUGHNESS.get()).m_22100_((double)4.0F);
      }

   }

   public static AttributeSupplier.Builder createAttributes() {
      return OPEntity.createAttributes().m_22268_(Attributes.f_22279_, 0.3).m_22268_(Attributes.f_22278_, (double)0.4F).m_22268_(Attributes.f_22276_, (double)80.0F).m_22268_(Attributes.f_22281_, (double)7.0F).m_22268_(Attributes.f_22277_, (double)80.0F);
   }
}
