package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.sniper;

import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import xyz.pixelatedw.mineminenomi.api.NuWorld;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.api.protection.DefaultProtectionRules;
import xyz.pixelatedw.mineminenomi.init.ModBlocks;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;

public class TokuyoAburaBoshiProjectile extends NuProjectileEntity {
   public TokuyoAburaBoshiProjectile(EntityType<? extends TokuyoAburaBoshiProjectile> type, Level world) {
      super(type, world);
   }

   public TokuyoAburaBoshiProjectile(Level world, LivingEntity player, IAbility ability) {
      super((EntityType)ModProjectiles.TOKUYO_ABURA_BOSHI.get(), world, player, ability);
      this.setDamage(4.0F);
      this.addEntityHitEvent(100, this::onEntityImpactEvent);
      this.addBlockHitEvent(100, this::onBlockImpactEvent);
   }

   private void onEntityImpactEvent(EntityHitResult result) {
      Entity var3 = result.m_82443_();
      if (var3 instanceof LivingEntity living) {
         living.m_7292_(new MobEffectInstance((MobEffect)ModEffects.OIL_COVERED.get(), 300, 0));
      }

      this.defaultOnHitBlock(result);
   }

   private void onBlockImpactEvent(BlockHitResult result) {
      BlockPos.MutableBlockPos mutpos = new BlockPos.MutableBlockPos();

      for(int heightSpread = -3; heightSpread < 3; ++heightSpread) {
         for(int i = 0; i < 20; ++i) {
            double offsetX = WyHelper.randomWithRange(-3, 3);
            double offsetZ = WyHelper.randomWithRange(-3, 3);
            mutpos.m_122169_((double)result.m_82425_().m_123341_() + offsetX, (double)(result.m_82425_().m_123342_() + heightSpread), (double)result.m_82425_().m_123343_() + offsetZ);
            BlockPos belowPos = mutpos.m_7495_();
            if (this.m_9236_().m_8055_(belowPos).m_60734_() != ModBlocks.OIL_SPILL.get()) {
               NuWorld.setBlockState((Entity)this.getOwner(), mutpos.m_7949_(), ((Block)ModBlocks.OIL_SPILL.get()).m_49966_(), 3, DefaultProtectionRules.AIR_FOLIAGE);
            }
         }
      }

   }
}
