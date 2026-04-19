package xyz.pixelatedw.mineminenomi.entities.projectiles;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import xyz.pixelatedw.mineminenomi.api.NuWorld;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.api.protection.DefaultProtectionRules;
import xyz.pixelatedw.mineminenomi.blocks.SkyBlockBlock;
import xyz.pixelatedw.mineminenomi.init.ModBlocks;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;

public class MilkyDialProjectile extends NuProjectileEntity {
   public MilkyDialProjectile(EntityType<? extends MilkyDialProjectile> type, Level world) {
      super(type, world);
   }

   public MilkyDialProjectile(Level world, LivingEntity player) {
      super((EntityType)ModProjectiles.MILKY_DIAL_PROJECTILE.get(), world, player);
      this.setMaxLife(40);
      this.setPhysical();
      this.setPassThroughBlocks();
      this.addTickEvent(100, this::tickEvent);
   }

   private void tickEvent() {
      for(BlockPos blockpos : BlockPos.m_121940_(this.m_20183_().m_7918_(-1, -1, -1), this.m_20183_().m_7918_(1, 0, 1))) {
         BlockState state = (BlockState)((Block)ModBlocks.SKY_BLOCK.get()).m_49966_().m_61124_(SkyBlockBlock.TYPE, this.f_19796_.m_188503_(4));
         NuWorld.setBlockState((Entity)this.getOwner(), blockpos, state, 3, DefaultProtectionRules.AIR);
      }

   }
}
