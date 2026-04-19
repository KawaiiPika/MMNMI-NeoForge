package xyz.pixelatedw.mineminenomi.abilities.bara;

import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityAttributeModifier;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityPool;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.MorphAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AnimationComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.PoolComponent;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AttributeHelper;
import xyz.pixelatedw.mineminenomi.api.morph.MorphInfo;
import xyz.pixelatedw.mineminenomi.init.ModAbilityPools;
import xyz.pixelatedw.mineminenomi.init.ModAnimations;
import xyz.pixelatedw.mineminenomi.init.ModMorphs;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class BaraBaraCarAbility extends MorphAbility {
   private static final int HOLD_TIME = 1200;
   private static final int MIN_COOLDOWN = 100;
   private static final int MAX_COOLDOWN = 1200;
   private static final float MAX_SPEED = 1.0F;
   private static final float SLIDE_POWER = 1.3F;
   public static final RegistryObject<AbilityCore<BaraBaraCarAbility>> INSTANCE = ModRegistry.registerAbility("bara_bara_car", "Bara Bara Car", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Turns the user's body into a car.", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, BaraBaraCarAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(100.0F, 1200.0F), ContinuousComponent.getTooltip(1200.0F)).build("mineminenomi");
   });
   private static final AbilityAttributeModifier SPEED_MODIFIER;
   private static final AbilityAttributeModifier STEP_HEIGHT;
   private final PoolComponent poolComponent;
   private final AnimationComponent animationComponent;

   public BaraBaraCarAbility(AbilityCore<BaraBaraCarAbility> core) {
      super(core);
      this.poolComponent = new PoolComponent(this, ModAbilityPools.BARA_ABILITY, new AbilityPool[0]);
      this.animationComponent = new AnimationComponent(this);
      this.addComponents(new AbilityComponent[]{this.poolComponent, this.animationComponent});
      this.continuousComponent.addStartEvent(200, this::startContinuityEvent);
      this.continuousComponent.addTickEvent(this::duringContinuityEvent);
      this.continuousComponent.addEndEvent(200, this::endContinuityEvent);
      this.statsComponent.addAttributeModifier(Attributes.f_22279_, SPEED_MODIFIER);
      this.statsComponent.addAttributeModifier(ForgeMod.STEP_HEIGHT_ADDITION, STEP_HEIGHT);
      this.addCanUseCheck(BaraHelper::hasLimbs);
   }

   public MorphInfo getTransformation() {
      return (MorphInfo)ModMorphs.BARA_CAR.get();
   }

   private void startContinuityEvent(LivingEntity entity, IAbility ability) {
      this.animationComponent.start(entity, ModAnimations.BARA_CAR);
   }

   private void duringContinuityEvent(LivingEntity entity, IAbility ability) {
      if (entity.m_20096_() && entity.m_20142_() && (Math.abs(entity.m_20184_().m_7096_()) < 0.2 || Math.abs(entity.m_20184_().m_7094_()) < 0.2)) {
         double x = Mth.m_14008_(entity.m_20184_().m_7096_() * (double)1.3F, (double)-1.0F, (double)1.0F);
         double z = Mth.m_14008_(entity.m_20184_().m_7094_() * (double)1.3F, (double)-1.0F, (double)1.0F);
         AbilityHelper.setDeltaMovement(entity, x, entity.m_20184_().m_7098_(), z);
      }

   }

   private void endContinuityEvent(LivingEntity entity, IAbility ability) {
      this.animationComponent.stop(entity);
   }

   public float getContinuityHoldTime() {
      return WyHelper.secondsToTicks(60.0F);
   }

   public float getCooldownTicks() {
      return Math.max(100.0F, super.continuousComponent.getContinueTime());
   }

   static {
      SPEED_MODIFIER = new AbilityAttributeModifier(AttributeHelper.MORPH_MOVEMENT_SPEED_UUID, INSTANCE, "Bara Bara Car Speed Modifier", 1.2, Operation.MULTIPLY_BASE);
      STEP_HEIGHT = new AbilityAttributeModifier(AttributeHelper.MORPH_STEP_HEIGHT_UUID, INSTANCE, "Bara Bara Car Step Height Modifier", (double)1.0F, Operation.ADDITION);
   }
}
