package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.rokushiki;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityExplosion;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ExplosionComponent;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;

public class RokuoganProjectile extends NuProjectileEntity {
   public RokuoganProjectile(EntityType<? extends RokuoganProjectile> type, Level world) {
      super(type, world);
   }

   public RokuoganProjectile(Level world, LivingEntity entity, IAbility ability) {
      super((EntityType)ModProjectiles.ROKUOGAN.get(), world, entity, ability);
      this.setMaxLife(5);
      this.setDamage(0.0F);
      this.setPassThroughBlocks();
      this.setPassThroughEntities();
      this.addBlockHitEvent(100, this::blockHitEvent);
      this.addTickEvent(100, this::tickEvent);
   }

   private void blockHitEvent(BlockHitResult result) {
      ExplosionComponent.createExplosion((IAbility)((IAbility)this.getParent().orElse((Object)null)), (comp) -> {
         AbilityExplosion explosion = comp.createExplosion(this, this.getOwner(), this.m_20185_(), this.m_20186_(), this.m_20189_(), 7.0F);
         explosion.setExplosionSound(true);
         explosion.setDamageOwner(false);
         explosion.setDestroyBlocks(false);
         explosion.setDamageEntities(false);
         explosion.setFireAfterExplosion(false);
         explosion.setDamageEntities(false);
         explosion.m_46061_();
      });
   }

   private void tickEvent() {
      if (!this.m_9236_().f_46443_) {
         for(int i = 0; i < 20; ++i) {
            double offsetX = WyHelper.randomDouble() / (double)1.25F;
            double offsetY = WyHelper.randomDouble() / (double)1.25F;
            double offsetZ = WyHelper.randomDouble() / (double)1.25F;
            ((ServerLevel)this.m_9236_()).m_8767_(ParticleTypes.f_123759_, this.m_20185_() + offsetX, this.m_20186_() + offsetY, this.m_20189_() + offsetZ, 1, (double)0.0F, (double)0.0F, (double)0.0F, -0.1);
            ((ServerLevel)this.m_9236_()).m_8767_(ParticleTypes.f_123812_, this.m_20185_() + offsetX, this.m_20186_() + offsetY, this.m_20189_() + offsetZ, 1, (double)0.0F, (double)0.0F, (double)0.0F, 0.1);
         }
      }

   }
}
