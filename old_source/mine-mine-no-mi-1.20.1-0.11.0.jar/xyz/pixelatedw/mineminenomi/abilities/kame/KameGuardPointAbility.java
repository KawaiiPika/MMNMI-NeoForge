package xyz.pixelatedw.mineminenomi.abilities.kame;

import net.minecraft.network.chat.Component;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.level.block.state.BlockState;
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
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChangeStatsComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DamageTakenComponent;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AttributeHelper;
import xyz.pixelatedw.mineminenomi.api.morph.MorphInfo;
import xyz.pixelatedw.mineminenomi.init.ModAttributes;
import xyz.pixelatedw.mineminenomi.init.ModMorphs;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class KameGuardPointAbility extends MorphAbility {
   public static final RegistryObject<AbilityCore<KameGuardPointAbility>> INSTANCE = ModRegistry.registerAbility("kame_guard_point", "Kame Guard Point", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Transforms the user into a turtle, which focuses on defense.", (Object)null), ImmutablePair.of("Sneaking allows you to retract into your shell.", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, KameGuardPointAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(10.0F), ContinuousComponent.getTooltip(), ChangeStatsComponent.getTooltip()).build("mineminenomi");
   });
   private static final AbilityAttributeModifier HEALTH_MODIFIER;
   private static final AbilityAttributeModifier ARMOR_MODIFIER;
   private static final AbilityAttributeModifier ATTACK_SPEED_MODIFIER;
   private static final AbilityAttributeModifier KNOCKBACK_RESISTANCE_MODIFIER;
   private static final AbilityAttributeModifier JUMP_BOOST_MODIFIER;
   private static final AbilityAttributeModifier GROUND_SPEED_MODIFIER;
   private static final AbilityAttributeModifier ICE_SPEED_MODIFIER;
   private static final AbilityAttributeModifier SWIM_SPEED_MODIFIER;
   private static final AbilityAttributeModifier FRICTION_MODIFIER;
   protected final DamageTakenComponent damageTakenComponent = (new DamageTakenComponent(this)).addOnHurtEvent(100, this::onHurtEvent);
   private boolean hasIceBuff = false;

   public KameGuardPointAbility(AbilityCore<KameGuardPointAbility> core) {
      super(core);
      this.addComponents(new AbilityComponent[]{this.damageTakenComponent});
      this.statsComponent.addAttributeModifier(Attributes.f_22284_, ARMOR_MODIFIER);
      this.statsComponent.addAttributeModifier(Attributes.f_22276_, HEALTH_MODIFIER);
      this.statsComponent.addAttributeModifier(Attributes.f_22283_, ATTACK_SPEED_MODIFIER);
      this.statsComponent.addAttributeModifier(Attributes.f_22278_, KNOCKBACK_RESISTANCE_MODIFIER);
      this.statsComponent.addAttributeModifier(ModAttributes.JUMP_HEIGHT, JUMP_BOOST_MODIFIER);
      this.statsComponent.addAttributeModifier(ForgeMod.SWIM_SPEED, SWIM_SPEED_MODIFIER);
      this.statsComponent.addAttributeModifier((Attribute)ModAttributes.FRICTION.get(), FRICTION_MODIFIER, this::slideOnIce);
      this.continuousComponent.addTickEvent(100, this::duringContinuityEvent);
   }

   private float onHurtEvent(LivingEntity entity, IAbility ability, DamageSource damageSource, float damage) {
      if (this.continuousComponent.isContinuous() && !entity.m_9236_().f_46443_ && entity.m_6047_()) {
         damage *= 0.1F;
      }

      return damage;
   }

   private void duringContinuityEvent(LivingEntity entity, IAbility ability) {
      boolean isDirty = false;
      if (!this.hasIceBuff && this.slideOnIce(entity)) {
         this.statsComponent.addAttributeModifier(Attributes.f_22279_, ICE_SPEED_MODIFIER);
         this.hasIceBuff = true;
         isDirty = true;
      } else if (this.hasIceBuff && !this.slideOnIce(entity)) {
         this.statsComponent.addAttributeModifier(Attributes.f_22279_, GROUND_SPEED_MODIFIER);
         this.hasIceBuff = false;
         isDirty = true;
      }

      if (isDirty) {
         this.statsComponent.applyModifiers(entity);
      }

   }

   public boolean slideOnIce(LivingEntity entity) {
      if (entity.m_6047_()) {
         return false;
      } else {
         BlockState state = entity.m_9236_().m_8055_(entity.m_20183_().m_7495_());
         return entity.m_20096_() && state.m_204336_(BlockTags.f_13047_);
      }
   }

   public MorphInfo getTransformation() {
      return (MorphInfo)ModMorphs.KAME_GUARD.get();
   }

   static {
      HEALTH_MODIFIER = new AbilityAttributeModifier(AttributeHelper.MORPH_HEALTH_UUID, INSTANCE, "Kame Guard Point Modifier", (double)10.0F, Operation.ADDITION);
      ARMOR_MODIFIER = new AbilityAttributeModifier(AttributeHelper.MORPH_ARMOR_UUID, INSTANCE, "Kame Guard Point Modifier", (double)20.0F, Operation.ADDITION);
      ATTACK_SPEED_MODIFIER = new AbilityAttributeModifier(AttributeHelper.MORPH_ATTACK_SPEED_UUID, INSTANCE, "Kame Guard Point Modifier", (double)-0.15F, Operation.ADDITION);
      KNOCKBACK_RESISTANCE_MODIFIER = new AbilityAttributeModifier(AttributeHelper.MORPH_KNOCKBACK_RESISTANCE_UUID, INSTANCE, "Kame Guard Point Knockback Resistance Modifier", (double)2.0F, Operation.ADDITION);
      JUMP_BOOST_MODIFIER = new AbilityAttributeModifier(AttributeHelper.MORPH_JUMP_BOOST_UUID, INSTANCE, "Kame Guard Point Jump Modifier", (double)-10.0F, Operation.ADDITION);
      GROUND_SPEED_MODIFIER = new AbilityAttributeModifier(AttributeHelper.MORPH_MOVEMENT_SPEED_UUID, INSTANCE, "Kame Guard Point Modifier", -0.85, Operation.MULTIPLY_BASE);
      ICE_SPEED_MODIFIER = new AbilityAttributeModifier(AttributeHelper.MORPH_MOVEMENT_SPEED_UUID, INSTANCE, "Kame Guard Point Modifier", (double)-0.25F, Operation.MULTIPLY_BASE);
      SWIM_SPEED_MODIFIER = new AbilityAttributeModifier(AttributeHelper.MORPH_SWIM_SPEED_UUID, INSTANCE, "Kame Guard Point Water Speed Modifier", (double)1.0F, Operation.ADDITION);
      FRICTION_MODIFIER = new AbilityAttributeModifier(AttributeHelper.MORPH_FRICTION_UUID, INSTANCE, "Kame Guard Friction Modifier", (double)1.9F, Operation.ADDITION);
   }
}
