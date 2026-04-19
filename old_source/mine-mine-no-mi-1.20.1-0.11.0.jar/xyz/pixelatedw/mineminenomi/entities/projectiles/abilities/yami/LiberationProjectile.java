package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.yami;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import xyz.pixelatedw.mineminenomi.api.NuWorld;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.api.protection.DefaultProtectionRules;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;

public class LiberationProjectile extends NuProjectileEntity {
   private BlockState block;

   public LiberationProjectile(EntityType<? extends LiberationProjectile> type, Level world) {
      super(type, world);
   }

   public LiberationProjectile(Level world, LivingEntity player, BlockState block, IAbility ability) {
      super((EntityType)ModProjectiles.LIBERATION.get(), world, player, ability);
      this.block = block;
      this.setDamage(5.0F);
      this.addBlockHitEvent(100, this::onBlockImpactEvent);
   }

   private void onBlockImpactEvent(BlockHitResult result) {
      NuWorld.setBlockState((Level)this.m_9236_(), result.m_82425_().m_7918_(0, 1, 0), this.block, 3, DefaultProtectionRules.AIR);
   }
}
