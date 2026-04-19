package xyz.pixelatedw.mineminenomi.abilities.kachi;

import java.util.UUID;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityAttributeModifier;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityOverlay;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.PunchAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChangeStatsComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.SkinOverlayComponent;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.ModResources;

public class VulcanizationAbility extends PunchAbility {
   private static final int HOLD_TIME = 200;
   private static final int COOLDOWN = 300;
   public static final RegistryObject<AbilityCore<VulcanizationAbility>> INSTANCE = ModRegistry.registerAbility("vulcanization", "Vulcanization", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Coats the user into a thick stone-like layer that increases the armor of its user.", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, VulcanizationAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(300.0F), ContinuousComponent.getTooltip(200.0F), ChangeStatsComponent.getTooltip()).setSourceHakiNature(SourceHakiNature.HARDENING).setSourceType(SourceType.FIST).build("mineminenomi");
   });
   private static final AbilityOverlay OVERLAY;
   private static final AbilityAttributeModifier ARMOR_MODIFIER;
   private final SkinOverlayComponent skinOverlayComponent;

   public VulcanizationAbility(AbilityCore<VulcanizationAbility> core) {
      super(core);
      this.skinOverlayComponent = new SkinOverlayComponent(this, OVERLAY, new AbilityOverlay[0]);
      this.statsComponent.addAttributeModifier(Attributes.f_22284_, ARMOR_MODIFIER);
      this.addComponents(new AbilityComponent[]{this.skinOverlayComponent});
      this.clearUseChecks();
      this.continuousComponent.addStartEvent(this::startContinuityEvent);
      this.continuousComponent.addEndEvent(this::endContinuityEvent);
   }

   private void startContinuityEvent(LivingEntity entity, IAbility ability) {
      this.skinOverlayComponent.showAll(entity);
   }

   private void endContinuityEvent(LivingEntity entity, IAbility ability) {
      this.skinOverlayComponent.hideAll(entity);
   }

   public float getPunchDamage() {
      return 4.0F;
   }

   public boolean onHitEffect(LivingEntity entity, LivingEntity target, DamageSource source) {
      return true;
   }

   public int getUseLimit() {
      return -1;
   }

   public float getPunchHoldTime() {
      return 200.0F;
   }

   public float getPunchCooldown() {
      return 300.0F;
   }

   static {
      OVERLAY = (new AbilityOverlay.Builder()).setOverlayPart(AbilityOverlay.OverlayPart.BODY).setTexture(ModResources.HOT_BOILING_SPECIAL_ARM).setColor("#FFFFFFAA").build();
      ARMOR_MODIFIER = new AbilityAttributeModifier(UUID.fromString("b0e012c8-1479-414d-8015-9044225572a7"), INSTANCE, "Vulcanization Modifier", (double)7.0F, Operation.ADDITION);
   }
}
