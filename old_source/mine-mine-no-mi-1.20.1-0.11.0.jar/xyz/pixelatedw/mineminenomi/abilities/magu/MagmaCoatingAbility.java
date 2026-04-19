package xyz.pixelatedw.mineminenomi.abilities.magu;

import java.awt.Color;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
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
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceElement;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.ModResources;

public class MagmaCoatingAbility extends PunchAbility {
   private static final float THRESHOLD = 600.0F;
   private static final float COOLDOWN = 200.0F;
   public static final RegistryObject<AbilityCore<MagmaCoatingAbility>> INSTANCE = ModRegistry.registerAbility("magma_coating", "Magma Coating", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("The user coats their arm with magma", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, MagmaCoatingAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(200.0F), ContinuousComponent.getTooltip(600.0F), ChangeStatsComponent.getTooltip()).setSourceHakiNature(SourceHakiNature.SPECIAL).setSourceType(SourceType.FIST).setSourceElement(SourceElement.MAGMA).build("mineminenomi");
   });
   private final SkinOverlayComponent skinOverlayComponent;
   private static final AbilityOverlay OVERLAY;

   public MagmaCoatingAbility(AbilityCore<MagmaCoatingAbility> core) {
      super(core);
      this.skinOverlayComponent = new SkinOverlayComponent(this, OVERLAY, new AbilityOverlay[0]);
      this.continuousComponent.addStartEvent(this::onContinuityStart).addEndEvent(this::onContinuityEnd);
      this.addComponents(new AbilityComponent[]{this.skinOverlayComponent});
      this.clearUseChecks();
   }

   private void onContinuityStart(LivingEntity entity, IAbility ability) {
      this.skinOverlayComponent.showAll(entity);
   }

   private void onContinuityEnd(LivingEntity entity, IAbility ability) {
      this.skinOverlayComponent.hideAll(entity);
   }

   public float getPunchDamage() {
      return 25.0F;
   }

   public float getPunchCooldown() {
      return 200.0F;
   }

   public boolean onHitEffect(LivingEntity entity, LivingEntity target, DamageSource source) {
      AbilityHelper.setSecondsOnFireBy(target, 5, entity);
      return true;
   }

   public float getPunchHoldTime() {
      return 600.0F;
   }

   public int getUseLimit() {
      return -1;
   }

   static {
      OVERLAY = (new AbilityOverlay.Builder()).setOverlayPart(AbilityOverlay.OverlayPart.LIMB).setTexture(ModResources.DOKU_COATING).setColor(new Color(160, 0, 0)).build();
   }
}
