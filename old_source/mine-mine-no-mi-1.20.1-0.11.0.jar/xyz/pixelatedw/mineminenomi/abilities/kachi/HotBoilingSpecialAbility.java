package xyz.pixelatedw.mineminenomi.abilities.kachi;

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
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.ModResources;

public class HotBoilingSpecialAbility extends PunchAbility {
   private static final int COOLDOWN = 100;
   public static final RegistryObject<AbilityCore<HotBoilingSpecialAbility>> INSTANCE = ModRegistry.registerAbility("hot_boiling_special", "Hot Boiling Special", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Hardens and heats up the fist of the user.", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, HotBoilingSpecialAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(100.0F), ContinuousComponent.getTooltip(), ChangeStatsComponent.getTooltip()).setSourceHakiNature(SourceHakiNature.HARDENING).setSourceType(SourceType.FIST).build("mineminenomi");
   });
   private static final AbilityOverlay OVERLAY;
   private final SkinOverlayComponent skinOverlayComponent;

   public HotBoilingSpecialAbility(AbilityCore<HotBoilingSpecialAbility> core) {
      super(core);
      this.skinOverlayComponent = new SkinOverlayComponent(this, OVERLAY, new AbilityOverlay[0]);
      this.addComponents(new AbilityComponent[]{this.skinOverlayComponent});
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
      return 20.0F;
   }

   public boolean onHitEffect(LivingEntity entity, LivingEntity target, DamageSource source) {
      AbilityHelper.setSecondsOnFireBy(target, 20, entity);
      return true;
   }

   public int getUseLimit() {
      return 1;
   }

   public float getPunchCooldown() {
      return 100.0F;
   }

   static {
      OVERLAY = (new AbilityOverlay.Builder()).setOverlayPart(AbilityOverlay.OverlayPart.LIMB).setTexture(ModResources.HOT_BOILING_SPECIAL_ARM).setColor("#FFFFFFAA").build();
   }
}
