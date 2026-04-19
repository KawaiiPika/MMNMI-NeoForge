package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.moku;

import java.util.List;
import java.util.Optional;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.MobsHelper;
import xyz.pixelatedw.mineminenomi.init.ModEntityPredicates;
import xyz.pixelatedw.mineminenomi.init.ModParticleTypes;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;
import xyz.pixelatedw.mineminenomi.particles.data.SimpleParticleData;

public class WhiteSnakeProjectile extends NuProjectileEntity {
   private Optional<LivingEntity> target = Optional.empty();

   public WhiteSnakeProjectile(EntityType<? extends WhiteSnakeProjectile> type, Level world) {
      super(type, world);
   }

   public WhiteSnakeProjectile(Level world, LivingEntity player, IAbility ability) {
      super((EntityType)ModProjectiles.WHITE_SNAKE.get(), world, player, ability);
      this.setDamage(25.0F);
      this.setMaxLife(40);
      this.addEntityHitEvent(100, this::onEntityHit);
      this.addTickEvent(100, this::onTickEvent);
   }

   private void onEntityHit(EntityHitResult result) {
      Entity var3 = result.m_82443_();
      if (var3 instanceof LivingEntity target) {
         target.m_7292_(new MobEffectInstance(MobEffects.f_19614_, 300, 0));
      }

   }

   private void onTickEvent() {
      if (this.getOwner() != null) {
         if (this.target.isPresent() && ((LivingEntity)this.target.get()).m_6084_() && !(((LivingEntity)this.target.get()).m_20182_().m_82557_(this.m_20182_()) > (double)15.0F)) {
            Vec3 dist = this.m_20182_().m_82546_(((LivingEntity)this.target.get()).m_20182_()).m_82520_((double)0.0F, (double)-1.0F, (double)0.0F);
            double speedReduction = (double)12.0F;
            double speed = 0.6;
            double xSpeed = Math.min(speed, -dist.f_82479_ / speedReduction);
            double ySpeed = Math.min(speed, -dist.f_82480_ / speedReduction);
            double zSpeed = Math.min(speed, -dist.f_82481_ / speedReduction);
            AbilityHelper.setDeltaMovement(this, xSpeed, ySpeed, zSpeed);
         } else {
            List<LivingEntity> list = WyHelper.<LivingEntity>getNearbyLiving(this.m_20182_(), this.m_9236_(), (double)5.0F, ModEntityPredicates.getEnemyFactions(this.getOwner()));
            list.remove(this.getOwner());
            list.sort(MobsHelper.ENTITY_THREAT);
            if (list.size() > 0) {
               this.target = list.stream().findAny();
            }
         }

         if (!this.m_9236_().f_46443_) {
            for(int i = 0; i < 40; ++i) {
               double offsetX = WyHelper.randomDouble() / (double)2.0F;
               double offsetY = WyHelper.randomDouble() / (double)2.0F;
               double offsetZ = WyHelper.randomDouble() / (double)2.0F;
               SimpleParticleData data = new SimpleParticleData((ParticleType)ModParticleTypes.MOKU2.get());
               data.setLife(10);
               data.setSize((float)(WyHelper.randomDouble() * (double)4.0F));
               data.setColor(0.8F, 0.8F, 0.8F);
               WyHelper.spawnParticles(data, (ServerLevel)this.m_9236_(), this.m_20185_() + offsetX, this.m_20186_() + offsetY, this.m_20189_() + offsetZ);
            }
         }

      }
   }
}
