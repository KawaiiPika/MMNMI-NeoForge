package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.yami;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
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

public class BlackRoadProjectile extends NuProjectileEntity {
   private SimpleBlockPlacer placer;

   public BlackRoadProjectile(EntityType<? extends BlackRoadProjectile> type, Level world) {
      super(type, world);
   }

   public BlackRoadProjectile(Level world, LivingEntity player, IAbility ability) {
      super((EntityType)ModProjectiles.BLACK_ROAD.get(), world, player, ability);
      this.setMaxLife(20);
      this.setDamage(10.0F);
      this.setPassThroughEntities();
      this.setPassThroughBlocks();
      this.placer = (new SimpleBlockPlacer()).setBlock(((Block)ModBlocks.DARKNESS.get()).m_49966_()).setRule(DefaultProtectionRules.CORE);
      this.addTickEvent(100, this::onTickEvent);
   }

   private void onTickEvent() {
      BlockPos pos = null;

      for(int j = 1; pos == null; ++j) {
         BlockState state = this.m_9236_().m_8055_(this.m_20183_().m_6625_(j));
         if (!state.m_60795_()) {
            pos = this.m_20183_().m_6625_(j);
            break;
         }

         if (j > 2) {
            break;
         }
      }

      if (pos != null) {
         int size = Math.round(2.0F + 4.0F * (1.0F - (float)this.getLife() / (float)this.getMaxLife()));
         this.placer.setSize(size);
         this.placer.generate(this.m_9236_(), pos, BlockGenerators.SPHERE);
         WyHelper.spawnParticleEffect((ParticleEffect)ModParticleEffects.DARK_MATTER_CHARGING.get(), this, (double)pos.m_123341_(), (double)pos.m_123342_(), (double)pos.m_123343_());
      }
   }
}
