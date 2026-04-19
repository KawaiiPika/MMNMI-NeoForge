package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.sniper;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.entities.SpikeEntity;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;

public class TetsuBoshiProjectile extends NuProjectileEntity {
   public TetsuBoshiProjectile(EntityType<? extends TetsuBoshiProjectile> type, Level world) {
      super(type, world);
   }

   public TetsuBoshiProjectile(Level world, LivingEntity player, IAbility ability) {
      super((EntityType)ModProjectiles.TETSU_BOSHI.get(), world, player, ability);
      this.setDamage(4.0F);
      this.addBlockHitEvent(100, this::onBlockImpactEvent);
   }

   private void onBlockImpactEvent(BlockHitResult result) {
      for(int i = 0; i < 10; ++i) {
         double offsetX = WyHelper.randomWithRange(-2, 2) + WyHelper.randomDouble();
         double offsetZ = WyHelper.randomWithRange(-2, 2) + WyHelper.randomDouble();
         SpikeEntity spike = new SpikeEntity(this.m_9236_());
         spike.m_7678_((double)result.m_82425_().m_123341_() + offsetX, (double)(result.m_82425_().m_123342_() + 1), (double)result.m_82425_().m_123343_() + offsetZ, 180.0F, 0.0F);
         this.m_9236_().m_7967_(spike);
      }

   }
}
