package xyz.pixelatedw.mineminenomi.abilities.doru;

import java.util.function.Predicate;
import java.util.function.Supplier;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
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
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AttributeHelper;
import xyz.pixelatedw.mineminenomi.api.morph.MorphInfo;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityCapability;
import xyz.pixelatedw.mineminenomi.init.ModAttributes;
import xyz.pixelatedw.mineminenomi.init.ModMorphs;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class CandleChampionAbility extends MorphAbility {
   private static final float HOLD_TIME = 1200.0F;
   private static final float MIN_COOLDOWN = 40.0F;
   private static final float MAX_COOLDOWN = 1240.0F;
   public static final RegistryObject<AbilityCore<CandleChampionAbility>> INSTANCE = ModRegistry.registerAbility("candle_champion", "Candle Champion", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("The user covers themselves in a thick wax coating, creating a battle suit protecting the user and increasing their offensive capabilities.", (Object)null), ImmutablePair.of("While active transforms §aDoru Doru Arts: Mori§r into §aChamp Fight§r", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, CandleChampionAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(40.0F, 1240.0F), ContinuousComponent.getTooltip(1200.0F)).build("mineminenomi");
   });
   private static final AbilityAttributeModifier STRENGTH_MODIFIER;
   private static final AbilityAttributeModifier ATTACK_SPEED_MODIFIER;
   private static final AbilityAttributeModifier REACH_MODIFIER;
   private static final AbilityAttributeModifier ARMOR_MODIFIER;
   private static final AbilityAttributeModifier KNOCKBACK_RESISTANCE;
   private static final AbilityAttributeModifier FALL_RESISTANCE_MODIFIER;

   public CandleChampionAbility(AbilityCore<CandleChampionAbility> core) {
      super(core);
      this.continuousComponent.addStartEvent(100, this::startContinuityEvent);
      this.continuousComponent.addTickEvent(100, this::tickContinuityEvent);
      this.continuousComponent.addEndEvent(100, this::endContinuityEvent);
      Predicate<LivingEntity> isContinuityActive = (entity) -> this.continuousComponent.isContinuous();
      this.statsComponent.addAttributeModifier((Supplier)ModAttributes.PUNCH_DAMAGE, STRENGTH_MODIFIER, isContinuityActive);
      this.statsComponent.addAttributeModifier(Attributes.f_22283_, ATTACK_SPEED_MODIFIER, isContinuityActive);
      this.statsComponent.addAttributeModifier(Attributes.f_22284_, ARMOR_MODIFIER, isContinuityActive);
      this.statsComponent.addAttributeModifier((Supplier)ForgeMod.ENTITY_REACH, REACH_MODIFIER, isContinuityActive);
      this.statsComponent.addAttributeModifier((Supplier)ForgeMod.BLOCK_REACH, REACH_MODIFIER, isContinuityActive);
      this.statsComponent.addAttributeModifier(Attributes.f_22278_, KNOCKBACK_RESISTANCE, isContinuityActive);
      this.statsComponent.addAttributeModifier((Supplier)ModAttributes.FALL_RESISTANCE, FALL_RESISTANCE_MODIFIER, isContinuityActive);
   }

   private void startContinuityEvent(LivingEntity entity, IAbility ability) {
      DoruDoruArtsMoriAbility doruArtsMori = (DoruDoruArtsMoriAbility)AbilityCapability.get(entity).map((props) -> (DoruDoruArtsMoriAbility)props.getEquippedAbility((AbilityCore)DoruDoruArtsMoriAbility.INSTANCE.get())).orElse((Object)null);
      if (doruArtsMori != null) {
         doruArtsMori.changeToChampFight(entity);
      }

   }

   private void tickContinuityEvent(LivingEntity entity, IAbility ability) {
      if (entity.m_6060_()) {
         this.continuousComponent.increaseContinuityTime(5.0F);
      }

   }

   private void endContinuityEvent(LivingEntity entity, IAbility ability) {
      DoruDoruArtsMoriAbility doruArtsMori = (DoruDoruArtsMoriAbility)AbilityCapability.get(entity).map((props) -> (DoruDoruArtsMoriAbility)props.getEquippedAbility((AbilityCore)DoruDoruArtsMoriAbility.INSTANCE.get())).orElse((Object)null);
      if (doruArtsMori != null) {
         doruArtsMori.changeToDefault(entity);
      }

      float cooldown = 40.0F + this.continuousComponent.getContinueTime();
      this.cooldownComponent.startCooldown(entity, cooldown);
   }

   public float getContinuityHoldTime() {
      return 1200.0F;
   }

   public MorphInfo getTransformation() {
      return (MorphInfo)ModMorphs.CANDLE_CHAMPION.get();
   }

   static {
      STRENGTH_MODIFIER = new AbilityAttributeModifier(AttributeHelper.MORPH_STRENGTH_UUID, INSTANCE, "Candle Champion Attack Damage Modifier", (double)4.0F, Operation.ADDITION);
      ATTACK_SPEED_MODIFIER = new AbilityAttributeModifier(AttributeHelper.MORPH_ATTACK_SPEED_UUID, INSTANCE, "Candle Champion Attack Speed Modifier", (double)0.1F, Operation.ADDITION);
      REACH_MODIFIER = new AbilityAttributeModifier(AttributeHelper.MORPH_ATTACK_REACH_UUID, INSTANCE, "Candle Champion Reach Modifier", 0.3, Operation.ADDITION);
      ARMOR_MODIFIER = new AbilityAttributeModifier(AttributeHelper.MORPH_ARMOR_UUID, INSTANCE, "Candle Champion Armor Modifier", (double)15.0F, Operation.ADDITION);
      KNOCKBACK_RESISTANCE = new AbilityAttributeModifier(AttributeHelper.MORPH_KNOCKBACK_RESISTANCE_UUID, INSTANCE, "Candle Champion Knockback Resistance Modifier", (double)2.0F, Operation.ADDITION);
      FALL_RESISTANCE_MODIFIER = new AbilityAttributeModifier(AttributeHelper.MORPH_FALL_RESISTANCE_UUID, INSTANCE, "Candle Champion Fall Resistance Modifier", (double)5.0F, Operation.ADDITION);
   }
}
