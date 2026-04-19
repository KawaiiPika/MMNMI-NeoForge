package xyz.pixelatedw.mineminenomi.abilities.haki;

import java.awt.Color;
import java.util.UUID;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityAttributeModifier;
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
import xyz.pixelatedw.mineminenomi.config.ServerConfig;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityCapability;
import xyz.pixelatedw.mineminenomi.data.entity.haki.HakiCapability;
import xyz.pixelatedw.mineminenomi.data.entity.haki.IHakiData;
import xyz.pixelatedw.mineminenomi.data.entity.stats.EntityStatsCapability;
import xyz.pixelatedw.mineminenomi.entities.LightningDischargeEntity;
import xyz.pixelatedw.mineminenomi.init.ModAbilityPools;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.ModSounds;

public class HaoshokuHakiInfusionAbility extends PunchAbility {
   public static final RegistryObject<AbilityCore<HaoshokuHakiInfusionAbility>> INSTANCE = ModRegistry.registerAbility("haoshoku_haki_infusion", "Haoshoku Haki: Infusion", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Allows the user to infuse their haoshoku haki into whatever weapon they're holding.", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.HAKI, HaoshokuHakiInfusionAbility::new)).addDescriptionLine(desc).setUnlockCheck(HaoshokuHakiInfusionAbility::canUnlock).setSourceType(SourceType.FIST).build("mineminenomi");
   });
   private static final AbilityOverlay OVERLAY;
   private static final UUID STRENGTH_UUID;
   private final PoolComponent poolComponent;
   private final SkinOverlayComponent skinOverlayComponent;

   public HaoshokuHakiInfusionAbility(AbilityCore<HaoshokuHakiInfusionAbility> core) {
      super(core);
      this.poolComponent = new PoolComponent(this, ModAbilityPools.ADVANCED_HAOSHOKU_HAKI, new AbilityPool[0]);
      this.skinOverlayComponent = new SkinOverlayComponent(this, OVERLAY, new AbilityOverlay[0]);
      this.setOGCD();
      this.continuousComponent.addStartEvent(100, this::onContinuityStart).addTickEvent(100, this::onContinuityTick).addEndEvent(100, this::onContinuityEnd);
      this.addComponents(new AbilityComponent[]{this.poolComponent, this.skinOverlayComponent});
      this.clearUseChecks();
      this.addCanUseCheck(AbilityUseConditions::canEnableHaki);
   }

   private void onContinuityStart(LivingEntity entity, IAbility ability) {
      Color clientRGB = WyHelper.intToRGB(HakiHelper.getHaoshokuColour(entity), 50);
      AbilityOverlay overlay = (new AbilityOverlay.Builder()).setOverlayPart(AbilityOverlay.OverlayPart.LIMB).setColor(clientRGB).build();
      this.skinOverlayComponent.show(entity, overlay);
   }

   private void onContinuityTick(LivingEntity entity, IAbility ability) {
      if (!entity.m_9236_().f_46443_) {
         boolean isOnMaxOveruse = HakiHelper.checkForHakiOveruse(entity, 3);
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
      if (entity.m_217043_().m_188503_(10) < 1) {
         entity.m_9236_().m_5594_((Player)null, entity.m_20183_(), (SoundEvent)ModSounds.HAKI_RELEASE_SFX.get(), SoundSource.PLAYERS, 2.0F, 0.7F);
      }

      Color clientRGB = WyHelper.intToRGB(HakiHelper.getHaoshokuColour(entity), 50);
      LightningDischargeEntity discharge = new LightningDischargeEntity(target, target.m_20185_(), target.m_20186_() + (double)1.5F, target.m_20189_(), target.m_146908_(), target.m_146909_());
      discharge.setAliveTicks(15);
      discharge.setLightningLength(6.0F);
      discharge.setColor(new Color(0, 0, 0, 100));
      discharge.setOutlineColor(clientRGB);
      discharge.setDetails(4);
      discharge.setDensity(4);
      discharge.setSize(1.0F);
      discharge.setSkipSegments(1);
      entity.m_9236_().m_7967_(discharge);
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

   public static double getDamageBoost(LivingEntity entity, float originalAmount) {
      double doriki = (Double)EntityStatsCapability.get(entity).map((props) -> props.getDoriki()).orElse((double)0.0F);
      float busoHaki = (Float)HakiCapability.get(entity).map((props) -> props.getBusoshokuHakiExp()).orElse(0.0F);
      double dorikiMultiplier = doriki / (double)ServerConfig.getDorikiLimit();
      float hakiMultiplier = busoHaki / (float)ServerConfig.getHakiExpLimit();
      return (double)5.0F + (double)originalAmount * (double)0.5F * (0.1 * dorikiMultiplier + 0.9 * (double)hakiMultiplier);
   }

   private AbilityAttributeModifier getEntryAttackDamage(double amount) {
      return new AbilityAttributeModifier(STRENGTH_UUID, (AbilityCore)INSTANCE.get(), "Haoshoku Haki Infusion Attack Damage Modifier", amount, Operation.ADDITION);
   }

   private static boolean canUnlock(LivingEntity user) {
      IHakiData props = (IHakiData)HakiCapability.get(user).orElse((Object)null);
      if (props == null) {
         return false;
      } else {
         boolean hasHakiAbilities = AbilityCapability.hasUnlockedAbility(user, (AbilityCore)HaoshokuHakiAbility.INSTANCE.get());
         return props.getTotalHakiExp() >= props.getMaxHakiExp() * 0.85F && hasHakiAbilities;
      }
   }

   static {
      OVERLAY = (new AbilityOverlay.Builder()).setOverlayPart(AbilityOverlay.OverlayPart.LIMB).setColor(new Color(255, 0, 0, 50)).build();
      STRENGTH_UUID = UUID.fromString("46383f90-63d5-4cfb-8df7-f93db7d5b84b");
   }
}
