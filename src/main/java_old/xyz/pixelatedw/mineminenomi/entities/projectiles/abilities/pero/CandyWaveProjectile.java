package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.pero;

import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.EntityHitResult;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.blockgen.BlockGenerators;
import xyz.pixelatedw.mineminenomi.api.blockgen.SimpleBlockPlacer;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.api.protection.DefaultProtectionRules;
import xyz.pixelatedw.mineminenomi.init.ModBlocks;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class CandyWaveProjectile extends NuProjectileEntity {
   private final SimpleBlockPlacer placer;

   public CandyWaveProjectile(EntityType<? extends CandyWaveProjectile> type, Level world) {
      super(type, world);
      this.placer = (new SimpleBlockPlacer()).setBlock(((Block)ModBlocks.CANDY.get()).m_49966_()).setRule(DefaultProtectionRules.CORE);
   }

   public CandyWaveProjectile(Level world, LivingEntity player, IAbility ability) {
      super((EntityType)ModProjectiles.CANDY_WAVE.get(), world, player, ability);
      this.setDamage(24.0F);
      this.setMaxLife(12);
      this.setPassThroughEntities();
      this.setPassThroughBlocks();
      this.placer = (new SimpleBlockPlacer()).setBlock(((Block)ModBlocks.CANDY.get()).m_49966_()).setRule(DefaultProtectionRules.CORE);
      this.addEntityHitEvent(100, this::onEntityHit);
      this.addTickEvent(100, this::onTickEvent);
   }

   private void onEntityHit(EntityHitResult result) {
      Entity var3 = result.m_82443_();
      if (var3 instanceof LivingEntity target) {
         target.m_7292_(new MobEffectInstance((MobEffect)ModEffects.CANDY_STUCK.get(), 100, 0, false, false, false));
      }

   }

   private void onTickEvent() {
      BlockPos pos = null;

      for(int j = 1; pos == null; ++j) {
         BlockState state = this.m_9236_().m_8055_(this.m_20183_().m_6625_(j));
         if (state.m_60815_()) {
            pos = this.m_20183_().m_6625_(j);
            break;
         }

         if (j > 3) {
            break;
         }
      }

      if (pos != null) {
         int size = 2 + 4 * (this.getMaxLife() - this.getLife()) / this.getMaxLife();
         this.placer.setSize(size);
         this.placer.generate(this.m_9236_(), pos, BlockGenerators.SPHERE);
         WyHelper.spawnParticleEffect((ParticleEffect)ModParticleEffects.CANDY_WAVE.get(), this, (double)pos.m_123341_(), (double)pos.m_123342_(), (double)pos.m_123343_());
      }
   }
}
