package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.electro;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class ElectricalShowerProjectile extends NuProjectileEntity {
   public ElectricalShowerProjectile(EntityType<? extends ElectricalShowerProjectile> type, Level world) {
      super(type, world);
   }

   public ElectricalShowerProjectile(Level world, LivingEntity entity, IAbility ability) {
      super((EntityType)ModProjectiles.ELECTRICAL_SHOWER.get(), world, entity, ability);
      this.setMaxLife(40);
      this.setPassThroughEntities();
      this.setEntityCollisionSize((double)1.5F, (double)1.5F, (double)1.5F);
      this.addTickEvent(100, this::onTickEvent);
   }

   private void onTickEvent() {
      if (this.f_19797_ >= 0) {
         WyHelper.spawnParticleEffect((ParticleEffect)ModParticleEffects.ELECTRICAL_LUNA.get(), this, this.m_20185_(), this.m_20186_(), this.m_20189_());
      }
   }
}
