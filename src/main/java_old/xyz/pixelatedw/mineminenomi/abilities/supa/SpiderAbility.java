package xyz.pixelatedw.mineminenomi.abilities.supa;

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
import xyz.pixelatedw.mineminenomi.api.abilities.components.AnimationComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.SkinOverlayComponent;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.init.ModAnimations;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class SpiderAbility extends GuardAbility {
   private static final float HOLD_TIME = 300.0F;
   private static final float MIN_COOLDOWN = 30.0F;
   private static final float MAX_COOLDOWN = 330.0F;
   private static final GuardAbility.GuardValue GUARD_VALUE;
   private static final AbilityOverlay OVERLAY;
   public static final RegistryObject<AbilityCore<SpiderAbility>> INSTANCE;
   public final SkinOverlayComponent skinOverlayComponent;
   public final AnimationComponent animationComponent;

   public SpiderAbility(AbilityCore<SpiderAbility> core) {
      super(core);
      this.skinOverlayComponent = new SkinOverlayComponent(this, OVERLAY, new AbilityOverlay[0]);
      this.animationComponent = new AnimationComponent(this);
      this.addComponents(new AbilityComponent[]{this.skinOverlayComponent, this.animationComponent});
      this.continuousComponent.addStartEvent(this::startContinuityEvent).addEndEvent(this::endContinuityEvent);
   }

   private void startContinuityEvent(LivingEntity entity, IAbility ability) {
      this.animationComponent.start(entity, ModAnimations.CROSSED_ARMS);
      this.skinOverlayComponent.showAll(entity);
      this.blockMovement(entity);
   }

   private void endContinuityEvent(LivingEntity entity, IAbility ability) {
      this.removeMovementBlock(entity);
      float cooldown = 30.0F + this.continuousComponent.getContinueTime();
      this.animationComponent.stop(entity);
      this.cooldownComponent.startCooldown(entity, cooldown);
      this.skinOverlayComponent.hideAll(entity);
   }

   public void onGuard(LivingEntity entity, DamageSource source, float originalDamage, float newDamage) {
   }

   public GuardAbility.GuardValue getGuard(LivingEntity entity) {
      return GUARD_VALUE;
   }

   static {
      GUARD_VALUE = GuardAbility.GuardValue.percentage(0.3F, GuardAbility.GuardBreakKind.PER_HIT, 100.0F);
      OVERLAY = (new AbilityOverlay.Builder()).setColor(new Color(100, 100, 100, 70)).build();
      INSTANCE = ModRegistry.registerAbility("spider", "Spider", (id, name) -> {
         Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Hardens the user's body to protect themselves, but they're unable to move", (Object)null));
         return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, SpiderAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(30.0F, 330.0F), ContinuousComponent.getTooltip(300.0F)).build("mineminenomi");
      });
   }
}
