package xyz.pixelatedw.mineminenomi.abilities.haki;

import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityPool;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.HitTriggerComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.PoolComponent;
import xyz.pixelatedw.mineminenomi.api.damagesources.IDamageSourceHandler;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityUseConditions;
import xyz.pixelatedw.mineminenomi.api.helpers.ItemsHelper;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityCapability;
import xyz.pixelatedw.mineminenomi.data.entity.ability.IAbilityData;
import xyz.pixelatedw.mineminenomi.data.entity.haki.HakiCapability;
import xyz.pixelatedw.mineminenomi.data.entity.haki.IHakiData;
import xyz.pixelatedw.mineminenomi.data.entity.stats.EntityStatsCapability;
import xyz.pixelatedw.mineminenomi.data.entity.stats.IEntityStats;
import xyz.pixelatedw.mineminenomi.init.ModAbilityPools;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.ModSounds;
import xyz.pixelatedw.mineminenomi.init.ModWeapons;

public class BusoshokuHakiImbuingAbility extends Ability {
   public static final RegistryObject<AbilityCore<BusoshokuHakiImbuingAbility>> INSTANCE = ModRegistry.registerAbility("busoshoku_haki_imbuing", "Busoshoku Haki: Imbuing", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Covers the weapon of the user in Armament haki, making their weapon attacks stronger and being able to damage Logia users.", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.HAKI, BusoshokuHakiImbuingAbility::new)).addDescriptionLine(desc).setSourceType(SourceType.SLASH).setUnlockCheck(BusoshokuHakiImbuingAbility::canUnlock).build("mineminenomi");
   });
   private final ContinuousComponent continuousComponent = (new ContinuousComponent(this, true)).addStartEvent(this::onContinuityStart).addTickEvent(this::onContinuityTick).addEndEvent(this::onContinuityEnd);
   private final HitTriggerComponent hitTriggerComponent = (new HitTriggerComponent(this)).addTryHitEvent(100, this::tryHitEvent).addOnHitEvent(100, this::onHitEvent);
   private final PoolComponent poolComponent;

   public BusoshokuHakiImbuingAbility(AbilityCore<BusoshokuHakiImbuingAbility> core) {
      super(core);
      this.poolComponent = new PoolComponent(this, ModAbilityPools.IMBUING_BUSOSHOKU_HAKI, new AbilityPool[0]);
      this.setOGCD();
      this.addComponents(new AbilityComponent[]{this.continuousComponent, this.hitTriggerComponent, this.poolComponent});
      this.addCanUseCheck(AbilityUseConditions::canEnableHaki);
      this.addUseEvent(this::onUseEvent);
   }

   private void onUseEvent(LivingEntity entity, IAbility ability) {
      if (entity.m_21205_().m_41720_() != ModWeapons.ENMA.get() || !this.continuousComponent.isContinuous()) {
         this.continuousComponent.triggerContinuity(entity);
      }
   }

   private void onContinuityStart(LivingEntity entity, IAbility ability) {
      entity.m_9236_().m_5594_((Player)null, entity.m_20183_(), (SoundEvent)ModSounds.BUSOSHOKU_HAKI_SFX.get(), SoundSource.PLAYERS, 2.0F, 1.0F);
   }

   private void onContinuityTick(LivingEntity entity, IAbility ability) {
      boolean isOnMaxOveruse = HakiHelper.checkForHakiOveruse(entity, 1);
      if (isOnMaxOveruse) {
         this.continuousComponent.stopContinuity(entity);
      }

   }

   private void onContinuityEnd(LivingEntity entity, IAbility ability) {
      for(ItemStack stack : ItemsHelper.getAllInventoryItems(entity)) {
         if (!stack.m_41619_() && ItemsHelper.isImbuable(stack) && stack.m_41784_().m_128471_("imbuingHakiActive")) {
            stack.m_41784_().m_128473_("imbuingHakiActive");
         }
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

   private static boolean canUnlock(LivingEntity user) {
      IAbilityData abilityProps = (IAbilityData)AbilityCapability.get(user).orElse((Object)null);
      IHakiData hakiProps = (IHakiData)HakiCapability.get(user).orElse((Object)null);
      IEntityStats statsProps = (IEntityStats)EntityStatsCapability.get(user).orElse((Object)null);
      if (abilityProps != null && hakiProps != null && statsProps != null) {
         boolean hasHardeningUnlocked = abilityProps.hasUnlockedAbility((AbilityCore)BusoshokuHakiHardeningAbility.INSTANCE.get());
         return statsProps.getDoriki() > (double)4000.0F && (double)hakiProps.getBusoshokuHakiExp() > HakiHelper.getBusoshokuHardeningExpNeeded(user) || hasHardeningUnlocked;
      } else {
         return false;
      }
   }
}
