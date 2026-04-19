package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.yami;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.BlockHitResult;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.blockgen.BlockGenerators;
import xyz.pixelatedw.mineminenomi.api.blockgen.SimpleBlockPlacer;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.api.protection.DefaultProtectionRules;
import xyz.pixelatedw.mineminenomi.init.ModBlocks;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class DarkMatterProjectile extends NuProjectileEntity {
   private SimpleBlockPlacer placer;

   public DarkMatterProjectile(EntityType<? extends DarkMatterProjectile> type, Level world) {
      super(type, world);
   }

   public DarkMatterProjectile(Level world, LivingEntity player, IAbility ability) {
      super((EntityType)ModProjectiles.DARK_MATTER.get(), world, player, ability);
      this.setDamage(15.0F);
      this.setMaxLife(20);
      this.placer = (new SimpleBlockPlacer()).setBlock(((Block)ModBlocks.DARKNESS.get()).m_49966_()).setRule(DefaultProtectionRules.CORE_FOLIAGE_ORE_LIQUID_AIR).setSize(3);
      this.addBlockHitEvent(100, this::onBlockImpactEvent);
   }

   private void onBlockImpactEvent(BlockHitResult result) {
      this.placer.generate(this.m_9236_(), result.m_82425_(), BlockGenerators.SPHERE);
      WyHelper.spawnParticleEffect((ParticleEffect)ModParticleEffects.DARK_MATTER.get(), this, (double)result.m_82425_().m_123341_(), (double)result.m_82425_().m_123342_(), (double)result.m_82425_().m_123343_());
   }
}
