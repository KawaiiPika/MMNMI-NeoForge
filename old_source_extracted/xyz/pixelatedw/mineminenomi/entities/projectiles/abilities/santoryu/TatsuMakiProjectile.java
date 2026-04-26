package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.santoryu;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;

public class TatsuMakiProjectile extends NuProjectileEntity {
   public TatsuMakiProjectile(EntityType<? extends TatsuMakiProjectile> type, Level world) {
      super(type, world);
   }

   public TatsuMakiProjectile(Level world, LivingEntity player, IAbility ability, SourceType type) {
      super((EntityType)ModProjectiles.TATSU_MAKI.get(), world, player, ability, ability.getCore().getSourceElement(), ability.getCore().getSourceHakiNature(), type);
      this.setDamage(30.0F);
      this.setMaxLife(40);
      this.addEntityHitEvent(100, this::entityHitEvent);
   }

   private void entityHitEvent(EntityHitResult result) {
      Entity target = result.m_82443_();
      if (!super.m_9236_().m_5776_()) {
         WyHelper.spawnParticles(ParticleTypes.f_123766_, (ServerLevel)super.m_9236_(), target.m_20185_(), target.m_20186_() + (double)target.m_20192_(), target.m_20189_());
      }

   }
}
