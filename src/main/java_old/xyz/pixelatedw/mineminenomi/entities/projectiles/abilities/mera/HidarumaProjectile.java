package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.mera;

import java.util.List;
import java.util.Optional;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.fluids.FluidType;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.MobsHelper;
import xyz.pixelatedw.mineminenomi.init.ModEntityPredicates;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;

public class HidarumaProjectile extends NuProjectileEntity {
   private Optional<LivingEntity> target;

   public HidarumaProjectile(EntityType<? extends HidarumaProjectile> type, Level world) {
      super(type, world);
   }

   public HidarumaProjectile(Level world, LivingEntity player, IAbility ability) {
      super((EntityType)ModProjectiles.HIDARUMA.get(), world, player, ability);
      this.setDamage(3.5F);
      this.setArmorPiercing(0.75F);
      this.setGravity(0.0F);
      this.setMaxLife(120);
      this.addEntityHitEvent(100, this::entityHitEvent);
      this.addTickEvent(100, this::tickEvent);
   }

   private void entityHitEvent(EntityHitResult result) {
      if (result.m_82443_() != null && result.m_82443_().m_6084_()) {
         AbilityHelper.setSecondsOnFireBy(result.m_82443_(), 3, this.getOwner());
      }

   }

   private void tickEvent() {
      if (this.target != null && this.target.isPresent() && ((LivingEntity)this.target.get()).m_6084_()) {
         Vec3 dist = super.m_20182_().m_82546_(((LivingEntity)this.target.get()).m_20182_()).m_82520_((double)0.0F, (double)-1.0F, (double)0.0F);
         double speedReduction = (double)12.0F;
         double speed = (double)0.5F;
         double xSpeed = Math.min(speed, -dist.f_82479_ / speedReduction);
         double ySpeed = Math.min(speed, -dist.f_82480_ / speedReduction);
         double zSpeed = Math.min(speed, -dist.f_82481_ / speedReduction);
         AbilityHelper.setDeltaMovement(this, xSpeed, ySpeed, zSpeed);
      } else if (this.getOwner() != null) {
         List<LivingEntity> list = WyHelper.<LivingEntity>getNearbyLiving(super.m_20182_(), super.m_9236_(), (double)16.0F, ModEntityPredicates.getEnemyFactions(this.getOwner()));
         list.remove(this.getOwner());
         list.sort(MobsHelper.ENTITY_THREAT);
         if (list.size() > 0) {
            this.target = list.stream().findAny();
         }
      }

      if (super.isEyeInFluidType((FluidType)ForgeMod.WATER_TYPE.get())) {
         super.m_142687_(RemovalReason.KILLED);
         super.m_20193_().m_7106_(ParticleTypes.f_123762_, super.m_20185_(), super.m_20186_() + 1.1, super.m_20189_(), (double)0.0F, (double)0.0F, (double)0.0F);
      }

      if (!super.m_9236_().f_46443_) {
         for(int i = 0; i < 1; ++i) {
            double offsetX = WyHelper.randomDouble() / (double)2.0F;
            double offsetY = WyHelper.randomDouble() / (double)2.0F;
            double offsetZ = WyHelper.randomDouble() / (double)2.0F;
            WyHelper.spawnParticles(ParticleTypes.f_123748_, (ServerLevel)super.m_9236_(), super.m_20185_() + offsetX, super.m_20186_() + offsetY, super.m_20189_() + offsetZ);
         }
      }

   }
}
