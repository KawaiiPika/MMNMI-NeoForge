package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.gura;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityExplosion;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ExplosionComponent;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.util.Interval;
import xyz.pixelatedw.mineminenomi.init.ModParticleTypes;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;
import xyz.pixelatedw.mineminenomi.particles.data.SimpleParticleData;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class GekishinProjectile extends NuProjectileEntity {
   private Interval explosionInterval = new Interval(2);

   public GekishinProjectile(EntityType<? extends GekishinProjectile> type, Level world) {
      super(type, world);
   }

   public GekishinProjectile(Level world, LivingEntity player, IAbility ability) {
      super((EntityType)ModProjectiles.GEKISHIN.get(), world, player, ability);
      this.setDamage(70.0F);
      this.setMaxLife(50);
      this.setEntityCollisionSize((double)5.0F);
      this.setPassThroughEntities();
      this.setPassThroughBlocks();
      this.setEntityCollisionSize((double)3.0F);
      this.setArmorPiercing(0.75F);
      this.addTickEvent(100, this::onTickEvent);
   }

   private void onTickEvent() {
      for(int i = 0; i < 32; ++i) {
         if (this.explosionInterval.canTick()) {
            ((ServerLevel)this.m_9236_()).m_8767_(ParticleTypes.f_123813_, this.m_20185_() + WyHelper.randomDouble() * (double)1.5F, this.m_20186_() + WyHelper.randomDouble() * (double)1.5F, this.m_20189_() + WyHelper.randomDouble() * (double)1.5F, 1, (double)0.0F, (double)0.0F, (double)0.0F, (double)0.0F);
         } else {
            double offsetX = WyHelper.randomDouble() * (double)5.0F;
            double offsetY = WyHelper.randomDouble() * (double)5.0F;
            double offsetZ = WyHelper.randomDouble() * (double)5.0F;
            SimpleParticleData data = new SimpleParticleData(this.f_19796_.m_188500_() > (double)0.5F ? (ParticleType)ModParticleTypes.MOKU.get() : (ParticleType)ModParticleTypes.MOKU2.get());
            data.setLife(10);
            data.setSize(10.0F);
            WyHelper.spawnParticles(data, (ServerLevel)this.m_9236_(), this.m_20185_() + offsetX, this.m_20186_() + offsetY, this.m_20189_() + offsetZ);
         }
      }

      ExplosionComponent.createExplosion((IAbility)((IAbility)this.getParent().orElse((Object)null)), (comp) -> {
         int size = 0;
         AbilityExplosion explosion = comp.createExplosion(this.getOwner(), this.m_20185_(), this.m_20186_(), this.m_20189_(), 8.0F);
         explosion.setStaticBlockResistance(1.35F);
         explosion.setProtectOwnerFromFalling(true);
         explosion.setExplosionSound(false);
         explosion.setSmokeParticles((ParticleEffect)null);
         explosion.setStaticDamage(15.0F);
         explosion.addRemovedBlocksToList();
         explosion.m_46061_();

         for(AbilityExplosion.RemovedBlock entry : explosion.getBlownStates()) {
            FallingBlockEntity entity = FallingBlockEntity.m_201971_(this.m_9236_(), entry.pos(), entry.state());
            entity.f_31943_ = false;
            entity.f_31942_ = 1;
            Vec3 dirVec = entity.m_20182_().m_82546_(this.m_20182_()).m_82541_().m_82542_((double)2.0F, (double)2.0F, (double)2.0F);
            AbilityHelper.setDeltaMovement(entity, -dirVec.f_82479_, (double)1.5F + dirVec.f_82480_, -dirVec.f_82481_);
            ++size;
            if (size > 50) {
               break;
            }
         }

      });
   }
}
