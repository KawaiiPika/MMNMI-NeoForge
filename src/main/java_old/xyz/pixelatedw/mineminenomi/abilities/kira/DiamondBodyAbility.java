package xyz.pixelatedw.mineminenomi.abilities.kira;

import java.awt.Color;
import java.util.UUID;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
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

public class DiamondBodyAbility extends Ability {
   private static final int HOLD_TIME = 2400;
   private static final int MIN_COOLDOWN = 40;
   private static final int MAX_COOLDOWN = 840;
   private static final AbilityOverlay OVERLAY;
   public static final RegistryObject<AbilityCore<DiamondBodyAbility>> INSTANCE;
   private static final AbilityAttributeModifier ARMOR_MODIFIER;
   private static final AbilityAttributeModifier ARMOR_TOUGHNESS_MODIFIER;
   private static final AbilityAttributeModifier ATTACK_MODIFIER;
   private static final AbilityAttributeModifier TOUGHNESS_MODIFIER;
   private final ContinuousComponent continuousComponent = (new ContinuousComponent(this, true)).addStartEvent(this::startContinuityEvent).addTickEvent(this::duringContinuityEvent).addEndEvent(this::endContinuityEvent);
   private final SkinOverlayComponent skinOverlayComponent;
   private final ChangeStatsComponent changeStatsComponent;

   public DiamondBodyAbility(AbilityCore<DiamondBodyAbility> core) {
      super(core);
      this.skinOverlayComponent = new SkinOverlayComponent(this, OVERLAY, new AbilityOverlay[0]);
      this.changeStatsComponent = new ChangeStatsComponent(this);
      this.addComponents(new AbilityComponent[]{this.continuousComponent, this.skinOverlayComponent, this.changeStatsComponent});
      this.changeStatsComponent.addAttributeModifier(Attributes.f_22284_, ARMOR_MODIFIER);
      this.changeStatsComponent.addAttributeModifier(Attributes.f_22285_, ARMOR_TOUGHNESS_MODIFIER);
      this.changeStatsComponent.addAttributeModifier(ModAttributes.PUNCH_DAMAGE, ATTACK_MODIFIER);
      this.changeStatsComponent.addAttributeModifier(ModAttributes.TOUGHNESS, TOUGHNESS_MODIFIER);
      this.addUseEvent(this::useEvent);
   }

   private void useEvent(LivingEntity entity, IAbility ability) {
      this.continuousComponent.triggerContinuity(entity, 2400.0F);
   }

   private void startContinuityEvent(LivingEntity entity, IAbility ability) {
      this.changeStatsComponent.applyModifiers(entity);
      this.skinOverlayComponent.showAll(entity);
   }

   private void duringContinuityEvent(LivingEntity entity, IAbility ability) {
   }

   private void endContinuityEvent(LivingEntity entity, IAbility ability) {
      this.changeStatsComponent.removeModifiers(entity);
      this.skinOverlayComponent.hideAll(entity);
      float cooldown = 40.0F + this.continuousComponent.getContinueTime() / 3.0F;
      this.cooldownComponent.startCooldown(entity, cooldown);
   }

   static {
      OVERLAY = (new AbilityOverlay.Builder()).setOverlayPart(AbilityOverlay.OverlayPart.BODY).setTexture(ModResources.DIAMOND_BODY).setColor(new Color(255, 255, 255, 165)).build();
      INSTANCE = ModRegistry.registerAbility("diamond_body", "Diamond Body", (id, name) -> {
         Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Allows the user's body to become diamond, gaining high strength and defence.", (Object)null));
         return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, DiamondBodyAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(40.0F, 840.0F), ContinuousComponent.getTooltip(2400.0F), ChangeStatsComponent.getTooltip()).build("mineminenomi");
      });
      ARMOR_MODIFIER = new AbilityAttributeModifier(UUID.fromString("547a5eaa-a969-4328-9364-a40638876d54"), INSTANCE, "Diamond Body Armor Modifier", (double)20.0F, Operation.ADDITION);
      ARMOR_TOUGHNESS_MODIFIER = new AbilityAttributeModifier(UUID.fromString("764f6317-2d9f-4c54-8906-0201f1521212"), INSTANCE, "Diamond Body Armor Toughness Modifier", (double)12.0F, Operation.ADDITION);
      ATTACK_MODIFIER = new AbilityAttributeModifier(UUID.fromString("f139d3ce-ac49-42d6-bb47-e93a6b89e44b"), INSTANCE, "Diamond Body Attack Modifier", (double)6.0F, Operation.ADDITION);
      TOUGHNESS_MODIFIER = new AbilityAttributeModifier(UUID.fromString("7db0de61-b5a2-40d9-ab0e-42d6afb5bece"), INSTANCE, "Diamond Body Toughness Modifier", (double)8.0F, Operation.ADDITION);
   }
}
