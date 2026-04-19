package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.suna;

import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.EntityHitResult;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.blockgen.BlockGenerators;
import xyz.pixelatedw.mineminenomi.api.blockgen.SimpleBlockPlacer;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.api.protection.DefaultProtectionRules;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class DesertSpadaProjectile extends NuProjectileEntity {
   private SimpleBlockPlacer placer;

   public DesertSpadaProjectile(EntityType<? extends DesertSpadaProjectile> type, Level world) {
      super(type, world);
   }

   public DesertSpadaProjectile(Level world, LivingEntity player, IAbility ability) {
      super((EntityType)ModProjectiles.DESERT_SPADA.get(), world, player, ability);
      this.setDamage(30.0F);
      this.setMaxLife(45);
      this.setPassThroughEntities();
      this.setPassThroughBlocks();
      this.setEntityCollisionSize((double)2.0F);
      this.placer = (new SimpleBlockPlacer()).setBlock(Blocks.f_50016_.m_49966_()).setRule(DefaultProtectionRules.CORE).setSize(2);
      this.addEntityHitEvent(100, this::entityHitEvent);
      this.addTickEvent(100, this::tickEvent);
   }

   private void entityHitEvent(EntityHitResult result) {
      Entity var3 = result.m_82443_();
      if (var3 instanceof LivingEntity target) {
         target.m_7292_(new MobEffectInstance((MobEffect)ModEffects.DEHYDRATION.get(), 200, 1));
      }

   }

   private void tickEvent() {
      BlockPos pos = null;
      int j = 1;

      while(true) {
         BlockState state = this.m_9236_().m_8055_(this.m_20183_().m_6625_(j));
         if (!state.m_60795_()) {
            pos = this.m_20183_().m_6625_(j);
            break;
         }

         if (j > 5) {
            break;
         }

         ++j;
      }

      if (pos != null) {
         this.placer.generate(this.m_9236_(), pos.m_7495_(), BlockGenerators.SPHERE);
         WyHelper.spawnParticleEffect((ParticleEffect)ModParticleEffects.DESERT_SPADA.get(), this, (double)pos.m_123341_(), (double)pos.m_123342_(), (double)pos.m_123343_());
      }
   }
}
