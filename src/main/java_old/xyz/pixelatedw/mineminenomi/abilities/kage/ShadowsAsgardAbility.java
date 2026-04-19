package xyz.pixelatedw.mineminenomi.abilities.kage;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import java.util.Map;
import java.util.UUID;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityAttributeModifier;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.MorphAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.StackComponent;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.morph.MorphInfo;
import xyz.pixelatedw.mineminenomi.init.ModAttributes;
import xyz.pixelatedw.mineminenomi.init.ModMorphs;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class ShadowsAsgardAbility extends MorphAbility {
   private static final float HOLD_TIME = 1200.0F;
   private static final float MIN_COOLDOWN = 200.0F;
   private static final float MAX_COOLDOWN = 1400.0F;
   public static final int MAX_SHADOWS = 30;
   private static final float MIN_STAT = 0.0F;
   private static final float MAX_ARMOR_STAT = 10.0F;
   private static final float MAX_ATTACK_STAT = 10.0F;
   private static final float MAX_REACH_STAT = 2.0F;
   private static final float MAX_TOUGHNESS_STAT = 5.0F;
   private static final float MAX_STEP_ASSIST_STAT = 1.0F;
   private static final float MAX_KNOCKBACK_RES_STAT = 1.0F;
   private static final float MAX_HEALTH_STAT = 1.0F;
   private static final UUID ARMOR_MODIFIER_UUID = UUID.fromString("c9f0e77d-6cd6-498e-b032-6641daaa1081");
   private static final UUID ATTACK_MODIFIER_UUID = UUID.fromString("ad79a8f6-0e4e-4cfb-9df0-1ce833eee85c");
   private static final UUID REACH_MODIFIER_UUID = UUID.fromString("5d8c2020-c5f7-48c5-8825-b482bc8efff4");
   private static final UUID TOUGHNESS_MODIFIER_UUID = UUID.fromString("80f4aecd-effc-4843-8d53-945de5b0c251");
   private static final UUID STEP_ASSIST_MODIFIER_UUID = UUID.fromString("bf11a9e2-ffb5-4eca-8c01-9068338a838d");
   private static final UUID KNOCKBACK_RES_MODIFIER_UUID = UUID.fromString("c774bb23-72f4-48d3-84e4-e3aff157f1e7");
   private static final UUID MAX_HEALTH_MODIFIER_UUID = UUID.fromString("6f12c985-570c-420d-97d7-f972b0875f98");
   public static final RegistryObject<AbilityCore<ShadowsAsgardAbility>> INSTANCE = ModRegistry.registerAbility("shadows_asgard", "Shadow's Asgard", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("By continuously absorbing Shadows the user's strenght increases as well as their size", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, ShadowsAsgardAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(200.0F, 1400.0F), ContinuousComponent.getTooltip(1200.0F)).build("mineminenomi");
   });
   private final StackComponent stackComponent = new StackComponent(this);
   private int shadows = 0;

   public ShadowsAsgardAbility(AbilityCore<ShadowsAsgardAbility> core) {
      super(core);
      this.addComponents(new AbilityComponent[]{this.stackComponent});
      this.continuousComponent.addStartEvent(120, this::onStartContinuityEvent);
      this.continuousComponent.addTickEvent(120, this::onTickContinuityEvent);
      this.continuousComponent.addEndEvent(120, this::onEndContinuityEvent);
   }

   private void onStartContinuityEvent(LivingEntity entity, IAbility ability) {
      this.shadows = 0;
      this.morphComponent.updateMorphSize(entity);
   }

   private void onTickContinuityEvent(LivingEntity entity, IAbility ability) {
      if (KageHelper.hasEnoughShadows(entity, this, 1).isFail()) {
         this.continuousComponent.stopContinuity(entity);
      } else {
         if (this.continuousComponent.getContinueTime() % 10.0F == 0.0F && this.shadows < 30) {
            this.statsComponent.removeModifiers(entity);
            this.statsComponent.clearAttributeModifiers();

            for(Map.Entry<Attribute, AttributeModifier> entry : this.getAttributes().entries()) {
               this.statsComponent.addAttributeModifier((Attribute)entry.getKey(), (AttributeModifier)entry.getValue());
            }

            this.statsComponent.applyModifiers(entity);
            ++this.shadows;
            this.stackComponent.setStacks(entity, this, this.shadows);
            KageHelper.removeShadows(entity, 1);
            this.morphComponent.updateMorphSize(entity);
         }

      }
   }

   private void onEndContinuityEvent(LivingEntity entity, IAbility ability) {
      this.stackComponent.setStacks(entity, ability, 0);
      this.cooldownComponent.startCooldown(entity, 200.0F + this.continuousComponent.getContinueTime());
   }

   private Multimap<Attribute, AttributeModifier> getAttributes() {
      Multimap<Attribute, AttributeModifier> map = HashMultimap.create();
      map.put(Attributes.f_22284_, new AbilityAttributeModifier(ARMOR_MODIFIER_UUID, INSTANCE, "Shadows Asgard Armor Modifier", this.getArmorValue(), Operation.ADDITION));
      map.put(Attributes.f_22281_, new AbilityAttributeModifier(ATTACK_MODIFIER_UUID, INSTANCE, "Shadows Asgard Attack Modifier", this.getDamageValue(), Operation.ADDITION));
      AbilityAttributeModifier reachAttribute = new AbilityAttributeModifier(REACH_MODIFIER_UUID, INSTANCE, "Shadows Asgard Reach Modifier", this.getReachValue(), Operation.ADDITION);
      map.put((Attribute)ForgeMod.ENTITY_REACH.get(), reachAttribute);
      map.put((Attribute)ForgeMod.BLOCK_REACH.get(), reachAttribute);
      map.put((Attribute)ModAttributes.TOUGHNESS.get(), new AbilityAttributeModifier(TOUGHNESS_MODIFIER_UUID, INSTANCE, "Shadows Asgard Toughness Modifier", this.getToughnessValue(), Operation.ADDITION));
      map.put((Attribute)ForgeMod.STEP_HEIGHT_ADDITION.get(), new AbilityAttributeModifier(STEP_ASSIST_MODIFIER_UUID, INSTANCE, "Shadows Asgard Step Assist Modifier", this.getStepAssistValue(), Operation.ADDITION));
      map.put(Attributes.f_22278_, new AbilityAttributeModifier(KNOCKBACK_RES_MODIFIER_UUID, INSTANCE, "Shadows Asgard Knockback Resistance Modifier", this.getKnockbackResAssistValue(), Operation.ADDITION));
      map.put(Attributes.f_22276_, new AbilityAttributeModifier(MAX_HEALTH_MODIFIER_UUID, INSTANCE, "Shadows Asgard Max Health Modifier", this.getMaxHealthValue(), Operation.ADDITION));
      return map;
   }

   private double getArmorValue() {
      return (double)Math.min((float)(this.shadows + 1) / 30.0F * 10.0F, 10.0F);
   }

   private double getDamageValue() {
      return (double)Math.min((float)(this.shadows + 1) / 30.0F * 10.0F, 10.0F);
   }

   private double getReachValue() {
      return (double)Math.min((float)(this.shadows + 1) / 30.0F * 2.0F, 2.0F);
   }

   private double getToughnessValue() {
      return (double)Math.min((float)(this.shadows + 1) / 30.0F * 5.0F, 5.0F);
   }

   private double getStepAssistValue() {
      return (double)Math.min((float)(this.shadows + 1 + 15) / 30.0F * 1.0F, 1.0F);
   }

   private double getKnockbackResAssistValue() {
      return (double)Math.min((float)(this.shadows + 1 + 15) / 30.0F * 1.0F, 1.0F);
   }

   private double getMaxHealthValue() {
      return (double)Math.min((float)(this.shadows + 1) / 30.0F * 1.0F, 1.0F);
   }

   public void saveAdditional(CompoundTag nbt) {
      nbt.m_128405_("shadows", this.shadows);
   }

   public void loadAdditional(CompoundTag nbt) {
      this.shadows = nbt.m_128451_("shadows");
   }

   public MorphInfo getTransformation() {
      return (MorphInfo)ModMorphs.SHADOWS_ASGARD.get();
   }

   public int getShadows() {
      return this.shadows;
   }

   public float getContinuityHoldTime() {
      return 1200.0F;
   }
}
