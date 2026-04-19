package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.sniper;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.entities.clouds.KemuriBoshiCloudEntity;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;

public class KemuriBoshiProjectile extends NuProjectileEntity {
   public KemuriBoshiProjectile(EntityType<? extends KemuriBoshiProjectile> type, Level world) {
      super(type, world);
   }

   public KemuriBoshiProjectile(Level world, LivingEntity player, IAbility ability) {
      super((EntityType)ModProjectiles.KEMURI_BOSHI.get(), world, player, ability);
      this.setDamage(3.0F);
      this.addBlockHitEvent(100, this::onBlockImpactEvent);
   }

   private void onBlockImpactEvent(BlockHitResult result) {
      if (this.getOwner() != null) {
         KemuriBoshiCloudEntity smokeCloud = new KemuriBoshiCloudEntity(this.m_9236_(), this.getOwner());
         smokeCloud.setLife(100);
         smokeCloud.m_7678_((double)result.m_82425_().m_123341_(), (double)(result.m_82425_().m_123342_() + 1), (double)result.m_82425_().m_123343_(), 0.0F, 0.0F);
         AbilityHelper.setDeltaMovement(smokeCloud, (double)0.0F, (double)0.0F, (double)0.0F);
         this.m_9236_().m_7967_(smokeCloud);
      }
   }
}
