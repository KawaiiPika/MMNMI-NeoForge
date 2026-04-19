package xyz.pixelatedw.mineminenomi.entities.mobs.animals;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.entities.mobs.OPEntity;
import xyz.pixelatedw.mineminenomi.init.ModAttributes;

public class BlugoriEntity extends AbstractGorillaEntity {
   public BlugoriEntity(EntityType<? extends BlugoriEntity> type, Level world) {
      super(type, world);
      if (world != null && !world.f_46443_) {
         this.m_21051_((Attribute)ModAttributes.FALL_RESISTANCE.get()).m_22100_((double)2.0F);
         this.m_21051_((Attribute)ModAttributes.PUNCH_DAMAGE.get()).m_22100_((double)7.0F);
         this.m_21051_((Attribute)ModAttributes.TOUGHNESS.get()).m_22100_((double)2.0F);
      }

   }

   public static AttributeSupplier.Builder createAttributes() {
      return OPEntity.createAttributes().m_22268_(Attributes.f_22279_, 0.3).m_22268_(Attributes.f_22278_, (double)0.2F).m_22268_(Attributes.f_22276_, (double)60.0F).m_22268_(Attributes.f_22281_, (double)5.0F).m_22268_(Attributes.f_22277_, (double)70.0F);
   }
}
