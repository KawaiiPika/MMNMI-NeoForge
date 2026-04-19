package xyz.pixelatedw.mineminenomi.abilities.haki;

import java.awt.Color;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
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
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.ModResources;
import xyz.pixelatedw.mineminenomi.init.ModSounds;

public class BusoshokuHakiHardeningAbility extends PunchAbility {
   public static final RegistryObject<AbilityCore<BusoshokuHakiHardeningAbility>> INSTANCE = ModRegistry.registerAbility("busoshoku_haki_hardening", "Busoshoku Haki: Hardening", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Covers the fist of the user in Armament haki, making their physical attacks more powerful and being able to damage Logia users.", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.HAKI, BusoshokuHakiHardeningAbility::new)).addDescriptionLine(desc).setSourceType(SourceType.FIST).setUnlockCheck(BusoshokuHakiHardeningAbility::canUnlock).build("mineminenomi");
   });
   private static final AbilityOverlay OVERLAY;
   private final PoolComponent poolComponent;
   private final SkinOverlayComponent skinOverlayComponent;

   public BusoshokuHakiHardeningAbility(AbilityCore<BusoshokuHakiHardeningAbility> core) {
      super(core);
      this.poolComponent = new PoolComponent(this, ModAbilityPools.BODY_BUSOSHOKU_HAKI, new AbilityPool[0]);
      this.skinOverlayComponent = new SkinOverlayComponent(this, OVERLAY, new AbilityOverlay[0]);
      this.setOGCD();
      this.continuousComponent.addStartEvent(100, this::onContinuityStart).addTickEvent(100, this::onContinuityTick).addEndEvent(100, this::onContinuityEnd);
      this.addComponents(new AbilityComponent[]{this.poolComponent, this.skinOverlayComponent});
      this.addCanUseCheck(AbilityUseConditions::canEnableHaki);
   }

   private void onContinuityStart(LivingEntity entity, IAbility ability) {
      this.skinOverlayComponent.showAll(entity);
      entity.m_9236_().m_5594_((Player)null, entity.m_20183_(), (SoundEvent)ModSounds.BUSOSHOKU_HAKI_SFX.get(), SoundSource.PLAYERS, 2.0F, 1.0F);
   }

   private void onContinuityTick(LivingEntity entity, IAbility ability) {
      Level level = entity.m_9236_();
      if (level != null && !level.f_46443_) {
         boolean isOnMaxOveruse = HakiHelper.checkForHakiOveruse(entity, 0);
         if (isOnMaxOveruse) {
            super.continuousComponent.stopContinuity(entity);
         }

      }
   }

   private void onContinuityEnd(LivingEntity entity, IAbility ability) {
      this.skinOverlayComponent.hideAll(entity);
   }

   public boolean onHitEffect(LivingEntity entity, LivingEntity target, DamageSource source) {
      IDamageSourceHandler sourceHandler = IDamageSourceHandler.getHandler(source);
      sourceHandler.bypassLogia();
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

   private static boolean canUnlock(LivingEntity user) {
      IAbilityData abilityProps = (IAbilityData)AbilityCapability.get(user).orElse((Object)null);
      IHakiData hakiProps = (IHakiData)HakiCapability.get(user).orElse((Object)null);
      IEntityStats statsProps = (IEntityStats)EntityStatsCapability.get(user).orElse((Object)null);
      if (abilityProps != null && hakiProps != null && statsProps != null) {
         boolean hasImbuingUnlocked = abilityProps.hasUnlockedAbility((AbilityCore)BusoshokuHakiImbuingAbility.INSTANCE.get());
         return statsProps.getDoriki() > (double)4000.0F && (double)hakiProps.getBusoshokuHakiExp() > HakiHelper.getBusoshokuHardeningExpNeeded(user) || hasImbuingUnlocked;
      } else {
         return false;
      }
   }

   static {
      OVERLAY = (new AbilityOverlay.Builder()).setTexture(ModResources.BUSOSHOKU_HAKI_ARM).setOverlayPart(AbilityOverlay.OverlayPart.LIMB).setColor(new Color(255, 255, 255, 191)).build();
   }
}
