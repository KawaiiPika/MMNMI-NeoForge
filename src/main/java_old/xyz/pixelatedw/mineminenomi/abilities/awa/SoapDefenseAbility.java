package xyz.pixelatedw.mineminenomi.abilities.awa;

import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.GuardAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class SoapDefenseAbility extends GuardAbility {
   private static final float HOLD_TIME = 200.0F;
   private static final float MIN_COOLDOWN = 50.0F;
   private static final float MAX_COOLDOWN = 250.0F;
   private static final GuardAbility.GuardValue GUARD_VALUE;
   public static final RegistryObject<AbilityCore<SoapDefenseAbility>> INSTANCE;

   public SoapDefenseAbility(AbilityCore<SoapDefenseAbility> core) {
      super(core);
      this.continuousComponent.addStartEvent(100, this::onContinuityStart).addTickEvent(100, this::onContinuityTick).addEndEvent(100, this::onContinuityEnd);
   }

   private void onContinuityStart(LivingEntity entity, IAbility ability) {
      this.blockMovement(entity);
   }

   private void onContinuityTick(LivingEntity entity, IAbility ability) {
      if (entity.m_20069_()) {
         this.continuousComponent.stopContinuity(entity);
      }

   }

   private void onContinuityEnd(LivingEntity entity, IAbility ability) {
      this.removeMovementBlock(entity);
      this.cooldownComponent.startCooldown(entity, 50.0F + this.continuousComponent.getContinueTime());
   }

   public float getHoldTime() {
      return 200.0F;
   }

   public void onGuard(LivingEntity entity, DamageSource source, float originalDamage, float newDamage) {
   }

   public GuardAbility.GuardValue getGuard(LivingEntity entity) {
      return GUARD_VALUE;
   }

   static {
      GUARD_VALUE = GuardAbility.GuardValue.percentage(0.2F, GuardAbility.GuardBreakKind.TOTAL, 200.0F);
      INSTANCE = ModRegistry.registerAbility("soap_defense", "Soap Defense", (id, name) -> {
         Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Protect yourself using a giant concentrated soap shield", (Object)null));
         return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, SoapDefenseAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(50.0F, 250.0F), ContinuousComponent.getTooltip(200.0F)).build("mineminenomi");
      });
   }
}
