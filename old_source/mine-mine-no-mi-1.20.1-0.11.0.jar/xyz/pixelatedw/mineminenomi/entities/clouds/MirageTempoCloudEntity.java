package xyz.pixelatedw.mineminenomi.entities.clouds;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.entities.CloudEntity;
import xyz.pixelatedw.mineminenomi.init.ModEntities;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class MirageTempoCloudEntity extends CloudEntity {
   public MirageTempoCloudEntity(EntityType<? extends MirageTempoCloudEntity> type, Level world) {
      super(type, world);
   }

   public MirageTempoCloudEntity(Level world, LivingEntity owner) {
      super((EntityType)ModEntities.MIRAGE_TEMPO_CLOUD.get(), world, owner);
   }

   public void m_8119_() {
      super.m_8119_();
      if (!this.m_9236_().f_46443_ && this.f_19797_ % 2 == 0) {
         WyHelper.spawnParticleEffect((ParticleEffect)ModParticleEffects.KEMURI_BOSHI.get(), this, this.m_20185_(), this.m_20186_(), this.m_20189_());
      }

   }
}
