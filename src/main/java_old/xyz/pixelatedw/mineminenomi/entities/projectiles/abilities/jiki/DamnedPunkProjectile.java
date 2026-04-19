package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.jiki;

import java.awt.Color;
import java.util.List;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.abilities.jiki.JikiHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityExplosion;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ExplosionComponent;
import xyz.pixelatedw.mineminenomi.api.entities.NuHorizontalLightningEntity;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;
import xyz.pixelatedw.mineminenomi.particles.effects.CommonExplosionParticleEffect;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class DamnedPunkProjectile extends NuHorizontalLightningEntity {
   private List<ItemStack> magneticItems;

   public DamnedPunkProjectile(EntityType<? extends DamnedPunkProjectile> type, Level world) {
      super(type, world);
   }

   public DamnedPunkProjectile(Level world, LivingEntity thrower, List<ItemStack> items, IAbility ability) {
      super((EntityType)ModProjectiles.DAMNED_PUNK.get(), thrower, 64.0F, 7.0F, ability);
      this.magneticItems = items;
      this.setMaxLife(7);
      this.setDamage(60.0F);
      this.setSize(0.1F);
      this.setColor(new Color(11693284));
      this.setAngle(100);
      this.setTargetTimeToReset(6000);
      this.disableExplosionKnockback();
      this.setBranches(1);
      this.setSegments(1);
      this.addBlockHitEvent(100, this::blockHitEvent);
   }

   public void m_142687_(Entity.RemovalReason reason) {
      JikiHelper.dropComponentItems(this.m_9236_(), new Vec3(this.getCurrentX(), this.getCurrentY(), this.getCurrentZ()), this.magneticItems);
      super.m_142687_(reason);
   }

   private void blockHitEvent(BlockHitResult result) {
      ExplosionComponent.createExplosion((IAbility)((IAbility)this.getParent().orElse((Object)null)), (comp) -> {
         AbilityExplosion explosion = comp.createExplosion(this, this.getOwner(), (double)result.m_82425_().m_123341_(), (double)result.m_82425_().m_123342_(), (double)result.m_82425_().m_123343_(), 5.0F);
         explosion.setSmokeParticles((ParticleEffect)ModParticleEffects.COMMON_EXPLOSION.get(), CommonExplosionParticleEffect.EXPLOSION5);
         explosion.m_46061_();
      });
   }
}
