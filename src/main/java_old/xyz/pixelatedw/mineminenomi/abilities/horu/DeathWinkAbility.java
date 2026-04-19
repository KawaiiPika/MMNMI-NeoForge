package xyz.pixelatedw.mineminenomi.abilities.horu;

import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityExplosion;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ExplosionComponent;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.ModSounds;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;
import xyz.pixelatedw.mineminenomi.particles.effects.horu.WinkExplosionParticleEffect;

public class DeathWinkAbility extends Ability {
   private static final int COOLDOWN = 120;
   public static final RegistryObject<AbilityCore<DeathWinkAbility>> INSTANCE = ModRegistry.registerAbility("death_wink", "Death Wink", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("The user winks really hard creating a shockwave; %s boosts it's power.", new Object[]{ModEffects.GANMEN_SEICHO_HORMONE.get()}));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, DeathWinkAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(120.0F)).setSourceHakiNature(SourceHakiNature.SPECIAL).build("mineminenomi");
   });
   private ExplosionComponent explosionComponent = new ExplosionComponent(this);

   public DeathWinkAbility(AbilityCore<DeathWinkAbility> core) {
      super(core);
      this.addComponents(new AbilityComponent[]{this.explosionComponent});
      this.addUseEvent(this::useEvent);
   }

   private void useEvent(LivingEntity entity, IAbility ability) {
      entity.m_9236_().m_5594_((Player)null, entity.m_20183_(), (SoundEvent)ModSounds.DEATH_WINK_SFX.get(), SoundSource.PLAYERS, 2.0F, 1.0F);
      EntityHitResult trace = WyHelper.rayTraceEntities(entity, (double)1.0F);
      WyHelper.spawnParticleEffect((ParticleEffect)ModParticleEffects.HORU_WINK.get(), entity, trace.m_82450_().m_7096_(), entity.m_20186_(), trace.m_82450_().m_7094_());
      int power = entity.m_21023_((MobEffect)ModEffects.GANMEN_SEICHO_HORMONE.get()) ? 3 : 2;
      boolean createExplosion = true;
      HitResult mop = WyHelper.rayTraceBlocksAndEntities(entity, (double)(8 * power));
      double x = mop.m_82450_().f_82479_;
      double y = mop.m_82450_().f_82480_;
      double z = mop.m_82450_().f_82481_;
      if (mop instanceof EntityHitResult hitResult) {
         Entity var15 = hitResult.m_82443_();
         if (var15 instanceof NuProjectileEntity projectile) {
            if (projectile.getDamage() < (float)(power * 5)) {
               createExplosion = false;
               AbilityHelper.setDeltaMovement(projectile, -projectile.m_20184_().f_82479_, projectile.m_20184_().f_82480_, -projectile.m_20184_().f_82479_);
            }
         }
      }

      if (createExplosion) {
         AbilityExplosion explosion = this.explosionComponent.createExplosion(entity, entity, x, y, z, (float)(1 + power));
         explosion.setStaticDamage((float)(power * 10));
         explosion.setExplosionSound(false);
         explosion.setSmokeParticles((ParticleEffect)ModParticleEffects.WINK_EXPLOSION.get(), WinkExplosionParticleEffect.EXPLOSION2);
         explosion.m_46061_();
         double distance = Math.sqrt(entity.m_20275_(x, y, z));
         if (distance < (double)0.5F) {
            AbilityHelper.setDeltaMovement(entity, WyHelper.randomWithRange(-1, 1) * (double)0.5F * (double)power, (double)1.5F * (double)power, WyHelper.randomWithRange(-1, 1) * (double)0.5F * (double)power);
         }
      }

      this.cooldownComponent.startCooldown(entity, 120.0F);
   }
}
