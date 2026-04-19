package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.magu;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import xyz.pixelatedw.mineminenomi.abilities.magu.MaguHelper;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.init.ModParticleTypes;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;
import xyz.pixelatedw.mineminenomi.particles.data.SimpleParticleData;

public class DaiFunkaProjectile extends NuProjectileEntity {
   private boolean changeLifeTime = true;

   public DaiFunkaProjectile(EntityType<? extends DaiFunkaProjectile> type, Level world) {
      super(type, world);
   }

   public DaiFunkaProjectile(Level world, LivingEntity player, IAbility ability) {
      super((EntityType)ModProjectiles.DAI_FUNKA.get(), world, player, ability);
      this.setDamage(80.0F);
      this.setMaxLife(35);
      this.setPassThroughEntities();
      this.setArmorPiercing(1.0F);
      this.setEntityCollisionSize((double)7.0F, (double)7.0F, (double)7.0F);
      this.setBlockCollisionSize((double)6.0F, (double)2.0F, (double)6.0F);
      this.addEntityHitEvent(100, this::entityHitEvent);
      this.addBlockHitEvent(100, this::blockHitEvent);
      this.addTickEvent(100, this::tickEvent);
   }

   private void entityHitEvent(EntityHitResult result) {
      Entity var3 = result.m_82443_();
      if (var3 instanceof LivingEntity target) {
         AbilityHelper.setSecondsOnFireBy(target, 15, this.getOwner());
      }

   }

   private void blockHitEvent(BlockHitResult result) {
      MaguHelper.generateLavaPool(this.m_9236_(), result.m_82425_(), 3);
      if (this.changeLifeTime) {
         this.setLife(3);
         this.changeLifeTime = false;
      }

   }

   private void tickEvent() {
      if (!this.m_9236_().f_46443_) {
         for(int i = 0; i < 2; ++i) {
            double offsetX = WyHelper.randomDouble() / (double)2.0F;
            double offsetY = WyHelper.randomDouble() / (double)2.0F;
            double offsetZ = WyHelper.randomDouble() / (double)2.0F;
            SimpleParticleData data = new SimpleParticleData((ParticleType)ModParticleTypes.MERA.get());
            data.setLife(3);
            data.setSize(2.3F);
            WyHelper.spawnParticles(data, (ServerLevel)this.m_9236_(), this.m_20185_() + offsetX, this.m_20186_() + offsetY, this.m_20189_() + offsetZ);
         }

         for(int i = 0; i < 2; ++i) {
            double offsetX = WyHelper.randomDouble() / (double)2.0F;
            double offsetY = WyHelper.randomDouble() / (double)2.0F;
            double offsetZ = WyHelper.randomDouble() / (double)2.0F;
            SimpleParticleData data = new SimpleParticleData((ParticleType)ModParticleTypes.MAGU.get());
            data.setLife(3);
            data.setSize(2.3F);
            WyHelper.spawnParticles(data, (ServerLevel)this.m_9236_(), this.m_20185_() + offsetX, this.m_20186_() + offsetY, this.m_20189_() + offsetZ);
         }
      }

   }
}
