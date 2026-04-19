package xyz.pixelatedw.mineminenomi.abilities.toriphoenix;

import java.util.function.Predicate;
import java.util.function.Supplier;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.abilities.PropelledFlightAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityAttributeModifier;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.MorphAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AnimationComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChangeStatsComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AttributeHelper;
import xyz.pixelatedw.mineminenomi.api.morph.MorphInfo;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityCapability;
import xyz.pixelatedw.mineminenomi.data.entity.ability.IAbilityData;
import xyz.pixelatedw.mineminenomi.init.ModAnimations;
import xyz.pixelatedw.mineminenomi.init.ModAttributes;
import xyz.pixelatedw.mineminenomi.init.ModMorphs;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class PhoenixAssaultPointAbility extends MorphAbility {
   public static final RegistryObject<AbilityCore<PhoenixAssaultPointAbility>> INSTANCE = ModRegistry.registerAbility("phoenix_assault_point", "Phoenix Assault Point", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Transforms the user into a half-phoenix hybrid, which focuses on speed and healing.", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, PhoenixAssaultPointAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(10.0F), ContinuousComponent.getTooltip(), ChangeStatsComponent.getTooltip()).build("mineminenomi");
   });
   private final AnimationComponent animationComponent = new AnimationComponent(this);
   private static final AbilityAttributeModifier REGEN_RATE_MODIFIER;
   private static final AbilityAttributeModifier STRENGTH_MODIFIER;
   private static final AbilityAttributeModifier TOUGHNESS_MODIFIER;

   public PhoenixAssaultPointAbility(AbilityCore<PhoenixAssaultPointAbility> core) {
      super(core);
      Predicate<LivingEntity> isMorphed = (entity) -> super.morphComponent.isMorphed();
      this.statsComponent.addAttributeModifier((Supplier)ModAttributes.REGEN_RATE, REGEN_RATE_MODIFIER, isMorphed);
      this.statsComponent.addAttributeModifier((Supplier)ModAttributes.PUNCH_DAMAGE, STRENGTH_MODIFIER, isMorphed);
      this.statsComponent.addAttributeModifier((Supplier)ModAttributes.TOUGHNESS, TOUGHNESS_MODIFIER, isMorphed);
      this.continuousComponent.addStartEvent(this::onContinuityStart).addTickEvent(100, this::onContinuityTick).addEndEvent(this::onContinuityEnd);
      this.addComponents(new AbilityComponent[]{this.animationComponent});
   }

   private void onContinuityStart(LivingEntity entity, IAbility ability) {
   }

   private void onContinuityTick(LivingEntity entity, IAbility ability) {
      boolean isFlying = !entity.m_20096_() && AbilityHelper.getDifferenceToFloor(entity) > (double)1.0F;
      if (entity instanceof Player player) {
         isFlying |= player.m_150110_().f_35935_;
      }

      if (isFlying) {
         if (this.animationComponent.isStopped()) {
            this.animationComponent.start(entity, ModAnimations.PHOENIX_ASSAULT_FLY);
         }
      } else {
         this.animationComponent.stop(entity);
      }

   }

   private void onContinuityEnd(LivingEntity entity, IAbility ability) {
      if (!entity.m_9236_().f_46443_) {
         this.animationComponent.stop(entity);
         IAbilityData abilityDataProps = (IAbilityData)AbilityCapability.get(entity).orElse((Object)null);
         if (abilityDataProps != null) {
            PropelledFlightAbility flightAbility = (PropelledFlightAbility)abilityDataProps.getPassiveAbility((AbilityCore)PhoenixFlightAbility.INSTANCE.get());
            if (flightAbility != null) {
               flightAbility.disableFlight((Player)entity);
            }

         }
      }
   }

   public MorphInfo getTransformation() {
      return (MorphInfo)ModMorphs.PHOENIX_ASSAULT.get();
   }

   static {
      REGEN_RATE_MODIFIER = new AbilityAttributeModifier(AttributeHelper.MORPH_REGEN_RATE_UUID, INSTANCE, "Phoenix Assault Point Health Regeneration Speed Modifier", (double)0.6F, Operation.ADDITION);
      STRENGTH_MODIFIER = new AbilityAttributeModifier(AttributeHelper.MORPH_STRENGTH_UUID, INSTANCE, "Phoenix Assault Point Strength Modifier", (double)3.0F, Operation.ADDITION);
      TOUGHNESS_MODIFIER = new AbilityAttributeModifier(AttributeHelper.MORPH_TOUGHNESS_UUID, INSTANCE, "Phoenix Assault Point Toughness Modifier", (double)1.0F, Operation.ADDITION);
   }
}
