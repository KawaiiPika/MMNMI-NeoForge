package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.goe;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityExplosion;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ExplosionComponent;
import xyz.pixelatedw.mineminenomi.api.entities.IFlexibleSizeProjectile;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;
import xyz.pixelatedw.mineminenomi.particles.effects.CommonExplosionParticleEffect;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class DragonsRoarProjectile extends NuProjectileEntity implements IFlexibleSizeProjectile {
   private float size = 1.0F;

   public DragonsRoarProjectile(EntityType<? extends DragonsRoarProjectile> type, Level world) {
      super(type, world);
   }

   public DragonsRoarProjectile(Level world, LivingEntity player, float size, Ability ability) {
      super((EntityType)ModProjectiles.DRAGONS_ROAR.get(), world, player, ability);
      this.setDamage(10.0F);
      this.setMaxLife(30);
      this.setPassThroughEntities();
      this.setArmorPiercing(1.0F);
      this.setSize(size);
      this.addBlockHitEvent(100, this::onBlockImpactEvent);
   }

   private void onBlockImpactEvent(BlockHitResult result) {
      ExplosionComponent.createExplosion((IAbility)((IAbility)this.getParent().orElse((Object)null)), (comp) -> {
         AbilityExplosion explosion = comp.createExplosion(this, this.getOwner(), (double)result.m_82425_().m_123341_(), (double)result.m_82425_().m_123342_(), (double)result.m_82425_().m_123343_(), this.getSize() / 2.0F);
         explosion.setStaticBlockResistance(1.35F);
         explosion.setProtectOwnerFromFalling(true);
         explosion.setExplosionSound(true);
         explosion.setSmokeParticles((ParticleEffect)ModParticleEffects.COMMON_EXPLOSION.get(), new CommonExplosionParticleEffect.Details((double)this.getSize()));
         explosion.setStaticDamage(0.0F);
         explosion.m_46061_();
      });
   }

   public void writeSpawnData(FriendlyByteBuf buffer) {
      super.writeSpawnData(buffer);
      buffer.writeFloat(this.size);
   }

   public void readSpawnData(FriendlyByteBuf buffer) {
      super.readSpawnData(buffer);
      this.setSize(buffer.readFloat());
   }

   public void setSize(float size) {
      this.size = Mth.m_14036_(size, 1.0F, 50.0F);
      this.setEntityCollisionSize((double)(size / 2.0F), (double)(size / 2.0F), (double)(size / 2.0F));
   }

   public float getSize() {
      return this.size;
   }
}
