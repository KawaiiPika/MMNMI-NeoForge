package xyz.pixelatedw.mineminenomi.entities.mobs.abilities;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.entities.mobs.OPEntity;
import xyz.pixelatedw.mineminenomi.init.ModMobs;

public class MirageCloneEntity extends CloneEntity {
   public MirageCloneEntity(EntityType<? extends MirageCloneEntity> type, Level world) {
      super(type, world);
   }

   public MirageCloneEntity(Level world, LivingEntity owner) {
      super((EntityType)ModMobs.MIRAGE_CLONE.get(), world, owner);
   }

   public static AttributeSupplier.Builder createAttributes() {
      return OPEntity.createAttributes().m_22268_(Attributes.f_22277_, (double)35.0F).m_22268_(Attributes.f_22279_, (double)0.25F).m_22268_(Attributes.f_22276_, (double)5.0F).m_22268_(Attributes.f_22278_, (double)10.0F).m_22268_(Attributes.f_22284_, (double)2.0F);
   }

   public void m_142687_(Entity.RemovalReason reason) {
      if (!this.m_9236_().f_46443_) {
         for(int i = 0; i < 10; ++i) {
            double offsetX = WyHelper.randomDouble();
            double offsetY = WyHelper.randomDouble();
            double offsetZ = WyHelper.randomDouble();
            if (i % 2 == 0) {
               ((ServerLevel)this.m_20193_()).m_8767_(ParticleTypes.f_123796_, this.m_20185_() + offsetX, this.m_20186_() + (double)1.5F + offsetY, this.m_20189_() + offsetZ, 1, (double)0.0F, (double)0.0F, (double)0.0F, 0.05);
            } else {
               ((ServerLevel)this.m_20193_()).m_8767_(ParticleTypes.f_123759_, this.m_20185_() + offsetX, this.m_20186_() + (double)1.5F + offsetY, this.m_20189_() + offsetZ, 1, (double)0.0F, (double)0.0F, (double)0.0F, 0.05);
            }
         }
      }

      super.m_142687_(reason);
   }
}
