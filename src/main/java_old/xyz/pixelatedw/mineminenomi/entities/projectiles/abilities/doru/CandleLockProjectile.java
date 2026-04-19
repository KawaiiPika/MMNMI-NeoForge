package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.doru;

import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.EntityHitResult;
import xyz.pixelatedw.mineminenomi.abilities.doru.CandleChampionAbility;
import xyz.pixelatedw.mineminenomi.abilities.doru.CandleLockAbility;
import xyz.pixelatedw.mineminenomi.abilities.doru.DoruHelper;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityCapability;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class CandleLockProjectile extends NuProjectileEntity {
   private boolean isChampionActive = false;

   public CandleLockProjectile(EntityType<? extends CandleLockProjectile> type, Level world) {
      super(type, world);
   }

   public CandleLockProjectile(Level world, LivingEntity owner, CandleLockAbility ability) {
      super((EntityType)ModProjectiles.CANDLE_LOCK.get(), world, owner, ability);
      this.setDamage(8.0F);
      this.setMaxLife(20);
      this.setPassThroughEntities();
      super.setPhysical();
      this.setEntityCollisionSize((double)3.0F);
      this.isChampionActive = (Boolean)AbilityCapability.get(owner).map((props) -> (CandleChampionAbility)props.getEquippedAbility((AbilityCore)CandleChampionAbility.INSTANCE.get())).map((abl) -> abl.isContinuous()).orElse(false);
      if (this.isChampionActive) {
         this.setEntityCollisionSize((double)5.0F, (double)3.0F, (double)5.0F);
      }

      this.addEntityHitEvent(100, this::entityHitEvent);
      this.addTickEvent(100, this::tickEvent);
   }

   private void entityHitEvent(EntityHitResult result) {
      if (this.getOwner() != null) {
         Entity var3 = result.m_82443_();
         if (var3 instanceof LivingEntity) {
            LivingEntity target = (LivingEntity)var3;
            int time = 200;
            int amp = 1;
            if (DoruHelper.hasColorPalette(this.getOwner())) {
               amp = 2;
            }

            if (this.isChampionActive) {
               time = 300;
            }

            target.m_7292_(new MobEffectInstance((MobEffect)ModEffects.CANDLE_LOCK.get(), time, amp));
         }

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
         WyHelper.spawnParticleEffect((ParticleEffect)ModParticleEffects.CANDLE_LOCK.get(), this, (double)pos.m_123341_(), (double)pos.m_123342_(), (double)pos.m_123343_());
      }
   }
}
