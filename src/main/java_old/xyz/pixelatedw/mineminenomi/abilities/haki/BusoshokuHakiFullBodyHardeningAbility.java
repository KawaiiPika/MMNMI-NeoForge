package xyz.pixelatedw.mineminenomi.abilities.haki;

import java.util.UUID;
import java.util.function.Predicate;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityAttributeModifier;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityOverlay;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityPool;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChangeStatsComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.HitTriggerComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.PoolComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.SkinOverlayComponent;
import xyz.pixelatedw.mineminenomi.api.damagesources.IDamageSourceHandler;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityUseConditions;
import xyz.pixelatedw.mineminenomi.config.ServerConfig;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityCapability;
import xyz.pixelatedw.mineminenomi.data.entity.ability.IAbilityData;
import xyz.pixelatedw.mineminenomi.data.entity.haki.HakiCapability;
import xyz.pixelatedw.mineminenomi.data.entity.haki.IHakiData;
import xyz.pixelatedw.mineminenomi.data.entity.stats.EntityStatsCapability;
import xyz.pixelatedw.mineminenomi.data.entity.stats.IEntityStats;
import xyz.pixelatedw.mineminenomi.init.ModAbilityPools;
import xyz.pixelatedw.mineminenomi.init.ModAttributes;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.ModResources;
import xyz.pixelatedw.mineminenomi.init.ModSounds;

public class BusoshokuHakiFullBodyHardeningAbility extends Ability {
   public static final RegistryObject<AbilityCore<BusoshokuHakiFullBodyHardeningAbility>> INSTANCE = ModRegistry.registerAbility("busoshoku_haki_full_body_hardening", "Busoshoku Haki: Full Body Hardening", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Covers the whole body of the user user in a layer of Armament haki, used for a balance between offense and defense.", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.HAKI, BusoshokuHakiFullBodyHardeningAbility::new)).addDescriptionLine(desc).setSourceType(SourceType.FIST).setUnlockCheck(BusoshokuHakiFullBodyHardeningAbility::canUnlock).build("mineminenomi");
   });
   public static final AbilityOverlay OVERLAY;
   private Predicate<LivingEntity> emptyHandPredicate;
   private static final UUID ARMOR_UUID;
   private static final UUID ARMOR_THOUGNESS_UUID;
   private static final UUID TOUGHNESS_UUID;
   private final ContinuousComponent continuousComponent = (new ContinuousComponent(this, true)).addStartEvent(this::continuousStartEvent).addEndEvent(this::continuousStopEvent).addTickEvent(this::continuousTickEvent);
   private final ChangeStatsComponent statsComponent = new ChangeStatsComponent(this);
   private final HitTriggerComponent hitTriggerComponent = (new HitTriggerComponent(this)).addTryHitEvent(100, this::tryHitEvent).addOnHitEvent(100, this::onHitEvent);
   private final SkinOverlayComponent skinOverlayComponent;
   private final PoolComponent poolComponent;

   public BusoshokuHakiFullBodyHardeningAbility(AbilityCore<BusoshokuHakiFullBodyHardeningAbility> core) {
      super(core);
      this.skinOverlayComponent = new SkinOverlayComponent(this, OVERLAY, new AbilityOverlay[0]);
      this.poolComponent = new PoolComponent(this, ModAbilityPools.BODY_BUSOSHOKU_HAKI, new AbilityPool[0]);
      this.setOGCD();
      this.addComponents(new AbilityComponent[]{this.continuousComponent, this.statsComponent, this.hitTriggerComponent, this.skinOverlayComponent, this.poolComponent});
      this.addCanUseCheck(AbilityUseConditions::canEnableHaki);
      this.addUseEvent(this::onUseEvent);
   }

   private void onUseEvent(LivingEntity entity, IAbility ability) {
      if (this.emptyHandPredicate == null) {
         this.emptyHandPredicate = (e) -> this.continuousComponent.isContinuous() && e.m_21205_().m_41619_();
      }

      IHakiData hakiProps = (IHakiData)HakiCapability.get(entity).orElse((Object)null);
      if (hakiProps != null) {
         double defense = (double)(hakiProps.getBusoshokuHakiExp() / 12.5F);
         this.statsComponent.addAttributeModifier(Attributes.f_22284_, this.getArmorMod(defense), (e) -> this.continuousComponent.isContinuous());
         this.statsComponent.addAttributeModifier(Attributes.f_22285_, this.getArmorThougnessMod(defense / (double)4.0F), (e) -> this.continuousComponent.isContinuous());
         this.statsComponent.addAttributeModifier((Attribute)ModAttributes.TOUGHNESS.get(), this.getToughnessMod((double)(8.0F * (hakiProps.getBusoshokuHakiExp() / (float)ServerConfig.getHakiExpLimit()))), (e) -> this.continuousComponent.isContinuous());
         this.continuousComponent.triggerContinuity(entity);
      }
   }

   private void continuousStartEvent(LivingEntity entity, IAbility ability) {
      entity.m_9236_().m_5594_((Player)null, entity.m_20183_(), (SoundEvent)ModSounds.BUSOSHOKU_HAKI_SFX.get(), SoundSource.PLAYERS, 2.0F, 0.8F);
      this.skinOverlayComponent.showAll(entity);
   }

   private void continuousStopEvent(LivingEntity entity, IAbility ability) {
      this.skinOverlayComponent.hideAll(entity);
   }

   private void continuousTickEvent(LivingEntity entity, IAbility ability) {
      boolean isOnMaxOveruse = HakiHelper.checkForHakiOveruse(entity, 0);
      if (isOnMaxOveruse) {
         this.continuousComponent.stopContinuity(entity);
      }

   }

   private HitTriggerComponent.HitResult tryHitEvent(LivingEntity entity, LivingEntity target, DamageSource source, IAbility ability) {
      return !this.continuousComponent.isContinuous() ? HitTriggerComponent.HitResult.PASS : HitTriggerComponent.HitResult.HIT;
   }

   private boolean onHitEvent(LivingEntity entity, LivingEntity target, DamageSource source, IAbility ability) {
      IDamageSourceHandler sourceHandler = IDamageSourceHandler.getHandler(source);
      sourceHandler.bypassLogia();
      return true;
   }

   private AbilityAttributeModifier getArmorMod(double amount) {
      return new AbilityAttributeModifier(ARMOR_UUID, (AbilityCore)INSTANCE.get(), "Full Body Haki Armor Modifier", amount, Operation.ADDITION);
   }

   private AbilityAttributeModifier getArmorThougnessMod(double amount) {
      return new AbilityAttributeModifier(ARMOR_THOUGNESS_UUID, (AbilityCore)INSTANCE.get(), "Full Body Haki Armor Toughness Modifier", amount, Operation.ADDITION);
   }

   private AbilityAttributeModifier getToughnessMod(double amount) {
      return new AbilityAttributeModifier(TOUGHNESS_UUID, (AbilityCore)INSTANCE.get(), "Full Body Haki Toughness Modifier", amount, Operation.ADDITION);
   }

   private static boolean canUnlock(LivingEntity user) {
      IAbilityData abilityProps = (IAbilityData)AbilityCapability.get(user).orElse((Object)null);
      IHakiData hakiProps = (IHakiData)HakiCapability.get(user).orElse((Object)null);
      IEntityStats statsProps = (IEntityStats)EntityStatsCapability.get(user).orElse((Object)null);
      if (abilityProps != null && hakiProps != null && statsProps != null) {
         boolean hasHardeningUnlocked = abilityProps.hasUnlockedAbility((AbilityCore)BusoshokuHakiHardeningAbility.INSTANCE.get());
         return hasHardeningUnlocked && statsProps.getDoriki() > (double)5000.0F && (double)hakiProps.getBusoshokuHakiExp() > HakiHelper.getBusoshokuFullBodyExpNeeded(user);
      } else {
         return false;
      }
   }

   static {
      OVERLAY = (new AbilityOverlay.Builder()).setTexture(ModResources.BUSOSHOKU_HAKI_ARM).setColor(WyHelper.hexToRGB("#FFFFFFAA")).build();
      ARMOR_UUID = UUID.fromString("0457f786-0a5a-4e83-9ea6-f924c259a798");
      ARMOR_THOUGNESS_UUID = UUID.fromString("0457f786-0a5a-4e83-9ea6-f924c259a798");
      TOUGHNESS_UUID = UUID.fromString("9121ac66-fb1c-48a7-a636-0cdc3f01d96e");
   }
}
