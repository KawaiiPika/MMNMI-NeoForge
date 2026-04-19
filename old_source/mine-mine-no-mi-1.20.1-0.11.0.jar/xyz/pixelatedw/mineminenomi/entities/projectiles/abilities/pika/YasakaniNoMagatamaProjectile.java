package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.pika;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import org.joml.Vector3f;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityExplosion;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ExplosionComponent;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModParticleTypes;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;
import xyz.pixelatedw.mineminenomi.particles.data.SimpleParticleData;
import xyz.pixelatedw.mineminenomi.particles.effects.CommonExplosionParticleEffect;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class YasakaniNoMagatamaProjectile extends NuProjectileEntity {
   public YasakaniNoMagatamaProjectile(EntityType<? extends YasakaniNoMagatamaProjectile> type, Level world) {
      super(type, world);
   }

   public YasakaniNoMagatamaProjectile(Level world, LivingEntity player, IAbility ability) {
      super((EntityType)ModProjectiles.YASAKANI_NO_MAGATAMA.get(), world, player, ability);
      this.setDamage(4.0F);
      this.setArmorPiercing(1.0F);
      this.addBlockHitEvent(100, this::onBlockImpactEvent);
      this.addTickEvent(100, this::onTickEvent);
   }

   private void onBlockImpactEvent(BlockHitResult result) {
      ExplosionComponent.createExplosion((IAbility)((IAbility)this.getParent().orElse((Object)null)), (comp) -> {
         AbilityExplosion explosion = comp.createExplosion(this, this.getOwner(), (double)result.m_82425_().m_123341_(), (double)result.m_82425_().m_123342_(), (double)result.m_82425_().m_123343_(), 3.0F);
         explosion.setStaticDamage(4.0F);
         explosion.disableExplosionKnockback();
         explosion.setSmokeParticles((ParticleEffect)ModParticleEffects.COMMON_EXPLOSION.get(), CommonExplosionParticleEffect.EXPLOSION4);
         explosion.m_46061_();
      });
   }

   private void onTickEvent() {
      if (!this.m_9236_().f_46443_) {
         SimpleParticleData data = new SimpleParticleData((ParticleType)ModParticleTypes.PIKA.get());
         data.setLife(30);
         data.setSize(2.5F);
         data.setRotation(new Vector3f(0.0F, 1.0F, 0.0F));
         WyHelper.spawnParticles(data, (ServerLevel)this.m_9236_(), this.m_20185_(), this.m_20186_(), this.m_20189_());
      }

   }
}
