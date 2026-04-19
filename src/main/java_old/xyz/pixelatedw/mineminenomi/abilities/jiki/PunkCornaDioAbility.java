package xyz.pixelatedw.mineminenomi.abilities.jiki;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityAttributeModifier;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChangeStatsComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DealDamageComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.HitTrackerComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.MorphComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceElement;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.ItemsHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.MentionHelper;
import xyz.pixelatedw.mineminenomi.api.morph.MorphInfo;
import xyz.pixelatedw.mineminenomi.init.ModAttributes;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModMorphs;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class PunkCornaDioAbility extends Ability {
   private static final int REQUIRED_IRON = 160;
   private static final int CHARGE_TIME = 60;
   private static final int HOLD_TIME = 100;
   private static final int COOLDOWN = 200;
   private static final int RANGE = 6;
   private static final float DAMAGE = 100.0F;
   public static final RegistryObject<AbilityCore<PunkCornaDioAbility>> INSTANCE = ModRegistry.registerAbility("punk_corna_dio", "Punk Corna Dio", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Uses %s magnetic items from the inventory to create a bull which can then be used to smash into enemies dealing huge damage, knocking them away and potentially stunning them.", new Object[]{MentionHelper.mentionText(160)}));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, PunkCornaDioAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(200.0F), ChargeComponent.getTooltip(60.0F), DealDamageComponent.getTooltip(100.0F)).setSourceHakiNature(SourceHakiNature.IMBUING).setSourceElement(SourceElement.METAL).setSourceType(SourceType.BLUNT).build("mineminenomi");
   });
   private static final AbilityAttributeModifier TOUGHNESS_MODIFIER;
   private static final AbilityAttributeModifier STEP_HEIGHT_MODIFIER;
   private final ChargeComponent chargeComponent = (new ChargeComponent(this)).addStartEvent(this::startChargeEvent).addTickEvent(this::duringChargeEvent).addEndEvent(this::endChargeEvent);
   private final ContinuousComponent continuousComponent = (new ContinuousComponent(this)).addTickEvent(this::duringContinuityEvent).addEndEvent(this::endContinuityEvent);
   private final HitTrackerComponent hitTrackerComponent = new HitTrackerComponent(this);
   private final RangeComponent rangeComponent = new RangeComponent(this);
   private final DealDamageComponent dealDamageComponent = new DealDamageComponent(this);
   private final MorphComponent morphComponent = new MorphComponent(this);
   private final ChangeStatsComponent changeStatsComponent = new ChangeStatsComponent(this);
   private List<ItemStack> magneticItems = new ArrayList();

   public PunkCornaDioAbility(AbilityCore<PunkCornaDioAbility> core) {
      super(core);
      this.addComponents(new AbilityComponent[]{this.chargeComponent, this.continuousComponent, this.hitTrackerComponent, this.rangeComponent, this.dealDamageComponent, this.morphComponent, this.changeStatsComponent});
      this.changeStatsComponent.addAttributeModifier(ModAttributes.TOUGHNESS, TOUGHNESS_MODIFIER);
      this.changeStatsComponent.addAttributeModifier(ForgeMod.STEP_HEIGHT_ADDITION, STEP_HEIGHT_MODIFIER);
      this.addCanUseCheck(JikiHelper.getMetalicItemsCheck(160));
      this.addUseEvent(this::useEvent);
   }

   private void useEvent(LivingEntity entity, IAbility ability) {
      if (!this.continuousComponent.isContinuous()) {
         this.chargeComponent.startCharging(entity, 60.0F);
      }
   }

   private void startChargeEvent(LivingEntity entity, IAbility ability) {
      List<ItemStack> inventory = ItemsHelper.getAllInventoryItems(entity);
      this.magneticItems = JikiHelper.getMagneticItemsNeeded(inventory, 160.0F);
      this.morphComponent.startMorph(entity, (MorphInfo)ModMorphs.PUNK_CORNA_DIO.get());
      this.changeStatsComponent.applyModifiers(entity);
      this.hitTrackerComponent.clearHits();
   }

   private void duringChargeEvent(LivingEntity entity, IAbility ability) {
      entity.m_7292_(new MobEffectInstance((MobEffect)ModEffects.MOVEMENT_BLOCKED.get(), 2, 2, false, false));
   }

   private void endChargeEvent(LivingEntity entity, IAbility ability) {
      this.continuousComponent.startContinuity(entity, 100.0F);
   }

   private void duringContinuityEvent(LivingEntity entity, IAbility ability) {
      entity.m_7292_(new MobEffectInstance((MobEffect)ModEffects.MOVEMENT_BLOCKED.get(), 2, 2, false, false));
      if (this.magneticItems.size() <= 0) {
         this.continuousComponent.stopContinuity(entity);
      }

      Vec3 lookVec = entity.m_20154_();
      Vec3 speed = lookVec.m_82542_(2.3, (double)0.0F, 2.3);
      entity.m_6478_(MoverType.SELF, speed);
      if (this.continuousComponent.getContinueTime() % 25.0F == 0.0F) {
         this.hitTrackerComponent.clearHits();
      }

      Vec3 knockbackVec = lookVec.m_82542_((double)4.0F, (double)1.0F, (double)4.0F);

      for(LivingEntity target : this.rangeComponent.getTargetsInArea(entity, 6.0F)) {
         if (this.hitTrackerComponent.canHit(target) && this.dealDamageComponent.hurtTarget(entity, target, 100.0F)) {
            if (WyHelper.randomDouble() > (double)0.75F) {
               entity.m_7292_(new MobEffectInstance((MobEffect)ModEffects.DIZZY.get(), 100, 1, false, false));
            }

            AbilityHelper.setDeltaMovement(target, knockbackVec.f_82479_, 0.2, knockbackVec.f_82481_);
         }
      }

   }

   private void endContinuityEvent(LivingEntity entity, IAbility ability) {
      if (this.magneticItems != null && this.magneticItems.size() > 0) {
         ItemStack stack = (ItemStack)this.magneticItems.get(0);
         ItemsHelper.itemBreakParticles(entity, 160, stack);
         entity.m_9236_().m_5594_((Player)null, entity.m_20183_(), SoundEvents.f_12018_, entity.m_5720_(), 0.5F, 1.0F);
         JikiHelper.dropComponentItems(entity.m_9236_(), entity.m_20182_(), this.magneticItems);
      }

      this.morphComponent.stopMorph(entity);
      this.changeStatsComponent.removeModifiers(entity);
      this.cooldownComponent.startCooldown(entity, 200.0F);
   }

   static {
      TOUGHNESS_MODIFIER = new AbilityAttributeModifier(UUID.fromString("aa76fb8e-9123-424c-954a-7e19007491f8"), INSTANCE, "Punk Corna DIO Toughness Modifier", (double)3.0F, Operation.ADDITION);
      STEP_HEIGHT_MODIFIER = new AbilityAttributeModifier(UUID.fromString("d9824f43-df79-4664-a0a6-b4324236834e"), INSTANCE, "Punk Corna DIO Step Height Modifier", (double)3.0F, Operation.ADDITION);
   }
}
