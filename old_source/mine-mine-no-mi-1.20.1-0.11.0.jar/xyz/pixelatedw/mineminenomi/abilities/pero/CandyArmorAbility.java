package xyz.pixelatedw.mineminenomi.abilities.pero;

import java.util.UUID;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityAttributeModifier;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityOverlay;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChangeStatsComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.SkinOverlayComponent;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.init.ModAttributes;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.ModResources;

public class CandyArmorAbility extends Ability {
   private static final AbilityOverlay OVERLAY;
   private static final int HOLD_TIME = 300;
   private static final int MIN_COOLDOWN = 40;
   private static final float MAX_COOLDOWN = 200.0F;
   public static final RegistryObject<AbilityCore<CandyArmorAbility>> INSTANCE;
   private static final AbilityAttributeModifier MINING_SPEED_MODIFIER;
   private static final AbilityAttributeModifier MOVEMENT_SPEED_MODIFIER;
   private static final AbilityAttributeModifier ARMOR_MODIFIER;
   private static final AbilityAttributeModifier ARMOR_TOUGHNESS_MODIFIER;
   private final ContinuousComponent continuousComponent = (new ContinuousComponent(this)).addStartEvent(this::startContinuityEvent).addEndEvent(this::endContinuityEvent);
   private final ChangeStatsComponent changeStatsComponent = new ChangeStatsComponent(this);
   private final SkinOverlayComponent skinOverlayComponent;

   public CandyArmorAbility(AbilityCore<CandyArmorAbility> core) {
      super(core);
      this.skinOverlayComponent = new SkinOverlayComponent(this, OVERLAY, new AbilityOverlay[0]);
      this.addComponents(new AbilityComponent[]{this.continuousComponent, this.changeStatsComponent, this.skinOverlayComponent});
      this.changeStatsComponent.addAttributeModifier(ModAttributes.MINING_SPEED, MINING_SPEED_MODIFIER);
      this.changeStatsComponent.addAttributeModifier(Attributes.f_22279_, MOVEMENT_SPEED_MODIFIER);
      this.changeStatsComponent.addAttributeModifier(Attributes.f_22284_, ARMOR_MODIFIER);
      this.changeStatsComponent.addAttributeModifier(Attributes.f_22285_, ARMOR_TOUGHNESS_MODIFIER);
      this.addUseEvent(this::useEvent);
   }

   private void useEvent(LivingEntity entity, IAbility ability) {
      this.continuousComponent.triggerContinuity(entity, 300.0F);
   }

   private void startContinuityEvent(LivingEntity entity, IAbility ability) {
      this.changeStatsComponent.applyModifiers(entity);
      this.skinOverlayComponent.showAll(entity);
   }

   private void endContinuityEvent(LivingEntity entity, IAbility ability) {
      this.changeStatsComponent.removeModifiers(entity);
      this.skinOverlayComponent.hideAll(entity);
      float cooldown = Math.max(40.0F, this.continuousComponent.getContinueTime() / 1.5F);
      this.cooldownComponent.startCooldown(entity, cooldown);
   }

   static {
      OVERLAY = (new AbilityOverlay.Builder()).setTexture(ModResources.CANDY_ARMOR).setColor(WyHelper.hexToRGB("#FFFFFF99")).build();
      INSTANCE = ModRegistry.registerAbility("candy_armor", "Candy Armor", (id, name) -> {
         Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Coats the user with a hard candy armor, which boosts defense, but reduces mobility.", (Object)null));
         return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, CandyArmorAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(40.0F, 200.0F), ContinuousComponent.getTooltip(300.0F), ChangeStatsComponent.getTooltip()).build("mineminenomi");
      });
      MINING_SPEED_MODIFIER = new AbilityAttributeModifier(UUID.fromString("bde32ad3-5bf4-4b3b-8bf1-4947afbee0ad"), INSTANCE, "Candy Armor Mining Speed Modifier", (double)-0.05F, Operation.MULTIPLY_TOTAL);
      MOVEMENT_SPEED_MODIFIER = new AbilityAttributeModifier(UUID.fromString("df1e3c16-eaf5-4a65-8664-4ac073f14ed5"), INSTANCE, "Candy Armor Movement Speed Modifier", (double)-0.05F, Operation.MULTIPLY_TOTAL);
      ARMOR_MODIFIER = new AbilityAttributeModifier(UUID.fromString("35c7d00c-2876-4ec5-801c-914677539ef5"), INSTANCE, "Candy Armor Armor Modifier", (double)18.0F, Operation.ADDITION);
      ARMOR_TOUGHNESS_MODIFIER = new AbilityAttributeModifier(UUID.fromString("bfc760c0-698d-4362-8bab-42b86ad008b9"), INSTANCE, "Candy Armor Toughness Modifier", (double)10.0F, Operation.ADDITION);
   }
}
