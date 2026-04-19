package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.gomu;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.entities.NuHorizontalLightningEntity;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class GomuGomuNoJetPistolProjectile extends NuHorizontalLightningEntity {
   public GomuGomuNoJetPistolProjectile(EntityType<? extends GomuGomuNoJetPistolProjectile> type, Level world) {
      super(type, world);
   }

   public GomuGomuNoJetPistolProjectile(Level world, LivingEntity entity, IAbility ability) {
      super((EntityType)ModProjectiles.GOMU_GOMU_NO_JET_PISTOL.get(), entity, 80.0F, 16.0F, ability);
      this.setRetracting();
      this.setSize(0.025F);
      this.setOneTimeHit();
      this.setDepth(1);
      this.setPassThroughEntities(false);
      this.setDamage(10.0F);
      this.setFist();
      this.addTickEvent(100, this::onTickEvent);
   }

   private void onTickEvent() {
      if (this.f_19797_ % 2 == 0) {
         WyHelper.spawnParticleEffect((ParticleEffect)ModParticleEffects.GEAR_SECOND.get(), this, this.m_20185_(), this.m_20186_(), this.m_20189_());
      }

   }
}
