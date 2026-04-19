package xyz.pixelatedw.mineminenomi.abilities.doku;

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
import xyz.pixelatedw.mineminenomi.api.abilities.GuardAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.SkinOverlayComponent;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.ModResources;

public class DokuFuguAbility extends GuardAbility {
   private static final int COOLDOWN = 600;
   private static final int HOLD_TIME = 1200;
   private static final AbilityOverlay OVERLAY;
   private static final GuardAbility.GuardValue GUARD_VALUE;
   public static final RegistryObject<AbilityCore<DokuFuguAbility>> INSTANCE;
   private final SkinOverlayComponent skinOverlayComponent;

   public DokuFuguAbility(AbilityCore<DokuFuguAbility> core) {
      super(core);
      this.skinOverlayComponent = new SkinOverlayComponent(this, OVERLAY, new AbilityOverlay[0]);
      this.addComponents(new AbilityComponent[]{this.skinOverlayComponent});
      this.addCanUseCheck((entity, ability) -> DokuHelper.requiresVenomDemon(entity, ability).inverse());
      this.addContinueUseCheck((entity, ability) -> DokuHelper.requiresVenomDemon(entity, ability).inverse());
      this.continuousComponent.addStartEvent(100, this::startContinuityEvent).addEndEvent(100, this::endContinuityEvent);
   }

   private void startContinuityEvent(LivingEntity entity, IAbility ability) {
      this.skinOverlayComponent.showAll(entity);
   }

   private void endContinuityEvent(LivingEntity entity, IAbility ability) {
      this.skinOverlayComponent.hideAll(entity);
      this.cooldownComponent.startCooldown(entity, 600.0F);
   }

   public void onGuard(LivingEntity entity, DamageSource source, float originalDamage, float newDamage) {
   }

   public float getHoldTime() {
      return 1200.0F;
   }

   public boolean isParallel() {
      return true;
   }

   public GuardAbility.GuardValue getGuard(LivingEntity entity) {
      return GUARD_VALUE;
   }

   static {
      OVERLAY = (new AbilityOverlay.Builder()).setTexture(ModResources.DOKU_COATING).setColor(new Color(1.0F, 1.0F, 1.0F, 0.6F)).build();
      GUARD_VALUE = GuardAbility.GuardValue.percentage(0.1F, GuardAbility.GuardBreakKind.TOTAL, 100.0F);
      INSTANCE = ModRegistry.registerAbility("doku_fugu", "Doku Fugu", (id, name) -> {
         Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("The user covers themselves in poison creating a thin protective layer to damage. Cannot be used while %s is active.", new Object[]{VenomDemonAbility.INSTANCE}));
         return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, DokuFuguAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(600.0F), ContinuousComponent.getTooltip(1200.0F)).build("mineminenomi");
      });
   }
}
