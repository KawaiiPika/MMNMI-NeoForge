package xyz.pixelatedw.mineminenomi.abilities.haki;

import java.awt.Color;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityExplosion;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityOverlay;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityPool;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.PunchAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.PoolComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.SkinOverlayComponent;
import xyz.pixelatedw.mineminenomi.api.damagesources.IDamageSourceHandler;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityUseConditions;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityCapability;
import xyz.pixelatedw.mineminenomi.data.entity.ability.IAbilityData;
import xyz.pixelatedw.mineminenomi.data.entity.haki.HakiCapability;
import xyz.pixelatedw.mineminenomi.data.entity.haki.IHakiData;
import xyz.pixelatedw.mineminenomi.data.entity.stats.EntityStatsCapability;
import xyz.pixelatedw.mineminenomi.data.entity.stats.IEntityStats;
import xyz.pixelatedw.mineminenomi.init.ModAbilityPools;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.ModSounds;
import xyz.pixelatedw.mineminenomi.particles.effects.CommonExplosionParticleEffect;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class BusoshokuHakiInternalDestructionAbility extends PunchAbility {
   public static final RegistryObject<AbilityCore<BusoshokuHakiInternalDestructionAbility>> INSTANCE = ModRegistry.registerAbility("busoshoku_haki_internal_destruction", "Busoshoku Haki: Internal Destruction", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Allows the user to emit their haki through the target's body dealing great internal damage.", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.HAKI, BusoshokuHakiInternalDestructionAbility::new)).addDescriptionLine(desc).setSourceType(SourceType.FIST, SourceType.INTERNAL).setUnlockCheck(BusoshokuHakiInternalDestructionAbility::canUnlock).build("mineminenomi");
   });
   private static final AbilityOverlay OVERLAY;
   private final PoolComponent poolComponent;
   private final SkinOverlayComponent skinOverlayComponent;

   public BusoshokuHakiInternalDestructionAbility(AbilityCore<BusoshokuHakiInternalDestructionAbility> core) {
      super(core);
      this.poolComponent = new PoolComponent(this, ModAbilityPools.ADVANCED_BUSOSHOKU_HAKI, new AbilityPool[0]);
      this.skinOverlayComponent = new SkinOverlayComponent(this, OVERLAY, new AbilityOverlay[0]);
      this.setOGCD();
      this.continuousComponent.addStartEvent(100, this::onContinuityStart).addTickEvent(100, this::onContinuityTick).addEndEvent(100, this::onContinuityEnd);
      this.addComponents(new AbilityComponent[]{this.poolComponent, this.skinOverlayComponent});
      this.clearUseChecks();
      this.addCanUseCheck(AbilityUseConditions::canEnableHaki);
   }

   private void onContinuityStart(LivingEntity entity, IAbility ability) {
      this.statsComponent.removeModifiers(entity);
      this.statsComponent.clearAttributeModifiers();
      this.statsComponent.addAttributeModifier(ForgeMod.ENTITY_REACH, HakiHelper.getAdvancedBusoshokuHakiAttackReachBonus((AbilityCore)INSTANCE.get(), (double)BusoshokuHakiEmissionAbility.getReachBonus(entity)));
      this.statsComponent.applyModifiers(entity);
      this.skinOverlayComponent.showAll(entity);
      entity.m_9236_().m_5594_((Player)null, entity.m_20183_(), (SoundEvent)ModSounds.BUSOSHOKU_HAKI_SFX.get(), SoundSource.PLAYERS, 2.0F, 1.0F);
   }

   private void onContinuityTick(LivingEntity entity, IAbility ability) {
      Level level = entity.m_9236_();
      if (level != null && !level.f_46443_) {
         boolean isOnMaxOveruse = HakiHelper.checkForHakiOveruse(entity, 2);
         if (isOnMaxOveruse) {
            super.continuousComponent.stopContinuity(entity);
         }

      }
   }

   private void onContinuityEnd(LivingEntity entity, IAbility ability) {
      this.skinOverlayComponent.hideAll(entity);
   }

   private static boolean canUnlock(LivingEntity user) {
      IAbilityData abilityProps = (IAbilityData)AbilityCapability.get(user).orElse((Object)null);
      IHakiData hakiProps = (IHakiData)HakiCapability.get(user).orElse((Object)null);
      IEntityStats statsProps = (IEntityStats)EntityStatsCapability.get(user).orElse((Object)null);
      if (abilityProps != null && hakiProps != null && statsProps != null) {
         boolean hasEmissionUnlocked = abilityProps.hasUnlockedAbility((AbilityCore)BusoshokuHakiEmissionAbility.INSTANCE.get());
         boolean hasBusoExp = (double)hakiProps.getBusoshokuHakiExp() > HakiHelper.getBusoshokuInternalDestructionExpNeeded(user);
         return hasEmissionUnlocked && statsProps.getDoriki() > (double)8000.0F && hasBusoExp;
      } else {
         return false;
      }
   }

   public boolean onHitEffect(LivingEntity entity, LivingEntity target, DamageSource source) {
      IDamageSourceHandler sourceHandler = IDamageSourceHandler.getHandler(source);
      sourceHandler.bypassLogia();
      sourceHandler.addGlobalPiercing(1.0F);
      WyHelper.spawnParticleEffect((ParticleEffect)ModParticleEffects.INTERNAL_DESTRUCTION_BURST.get(), entity, target.m_20185_(), target.m_20186_() + (double)entity.m_20192_(), target.m_20189_());
      AbilityExplosion explosion = new AbilityExplosion(entity, this, target.m_20185_(), target.m_20186_(), target.m_20189_(), 1.0F);
      explosion.setDamageEntities(false);
      explosion.setDestroyBlocks(false);
      explosion.disableExplosionKnockback();
      explosion.setFireAfterExplosion(false);
      explosion.setExplosionSound(false);
      explosion.setSmokeParticles((ParticleEffect)ModParticleEffects.COMMON_EXPLOSION.get(), CommonExplosionParticleEffect.EXPLOSION1);
      explosion.m_46061_();
      return true;
   }

   public int getUseLimit() {
      return -1;
   }

   public boolean isParallel() {
      return true;
   }

   public float getPunchCooldown() {
      return 0.0F;
   }

   static {
      OVERLAY = (new AbilityOverlay.Builder()).setOverlayPart(AbilityOverlay.OverlayPart.LIMB).setColor(new Color(226, 129, 25, 70)).build();
   }
}
