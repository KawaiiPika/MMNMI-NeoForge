package xyz.pixelatedw.mineminenomi.abilities.hana;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityCapability;
import xyz.pixelatedw.mineminenomi.data.entity.ability.IAbilityData;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class MilFleurAbility extends Ability {
   private static final int HOLD_TIME = 600;
   private static final int MIN_COOLDOWN = 100;
   private static final float MAX_COOLDOWN = 400.0F;
   public static final RegistryObject<AbilityCore<MilFleurAbility>> INSTANCE = ModRegistry.registerAbility("mil_fleur", "Mil Fleur", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("While active all the other abilities of this fruit will transform, either allowing for area of effects or bigger and better versions of themselves.", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, MilFleurAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(100.0F, 400.0F), ContinuousComponent.getTooltip(600.0F)).build("mineminenomi");
   });
   private final ContinuousComponent continuousComponent = (new ContinuousComponent(this, true)).addStartEvent(this::startContinuityEvent).addEndEvent(this::endContinuityEvent);

   public MilFleurAbility(AbilityCore<MilFleurAbility> ability) {
      super(ability);
      this.addComponents(new AbilityComponent[]{this.continuousComponent});
      this.addUseEvent(this::useEvent);
   }

   private void useEvent(LivingEntity entity, IAbility ability) {
      this.continuousComponent.triggerContinuity(entity, 600.0F);
   }

   private void startContinuityEvent(LivingEntity entity, IAbility ability) {
      IAbilityData props = (IAbilityData)AbilityCapability.get(entity).orElse((Object)null);
      if (props != null) {
         DosFleurClutchAbility dosFleurClutch = (DosFleurClutchAbility)props.getEquippedAbility((AbilityCore)DosFleurClutchAbility.INSTANCE.get());
         if (dosFleurClutch != null) {
            dosFleurClutch.switchMilMode(entity);
         }

         SeisFleurSlapAbility seisFleurSlap = (SeisFleurSlapAbility)props.getEquippedAbility((AbilityCore)SeisFleurSlapAbility.INSTANCE.get());
         if (seisFleurSlap != null) {
            seisFleurSlap.switchMilMode(entity);
         }

         SeisFleurTwistAbility seisFleurTwist = (SeisFleurTwistAbility)props.getEquippedAbility((AbilityCore)SeisFleurTwistAbility.INSTANCE.get());
         if (seisFleurTwist != null) {
            seisFleurTwist.switchMilMode(entity);
         }

      }
   }

   private void endContinuityEvent(LivingEntity entity, IAbility ability) {
      IAbilityData props = (IAbilityData)AbilityCapability.get(entity).orElse((Object)null);
      if (props != null) {
         DosFleurClutchAbility dosFleur = (DosFleurClutchAbility)props.getEquippedAbility((AbilityCore)DosFleurClutchAbility.INSTANCE.get());
         if (dosFleur != null) {
            dosFleur.switchNormalMode(entity);
         }

         SeisFleurSlapAbility seisFleurSlap = (SeisFleurSlapAbility)props.getEquippedAbility((AbilityCore)SeisFleurSlapAbility.INSTANCE.get());
         if (seisFleurSlap != null) {
            seisFleurSlap.switchNormalMode(entity);
         }

         SeisFleurTwistAbility seisFleurTwist = (SeisFleurTwistAbility)props.getEquippedAbility((AbilityCore)SeisFleurTwistAbility.INSTANCE.get());
         if (seisFleurTwist != null) {
            seisFleurTwist.switchNormalMode(entity);
         }

         float cooldown = Math.max(100.0F, 400.0F);
         this.cooldownComponent.startCooldown(entity, cooldown);
      }
   }

   public static enum Mode {
      NORMAL,
      MIL_FLEUR;

      // $FF: synthetic method
      private static Mode[] $values() {
         return new Mode[]{NORMAL, MIL_FLEUR};
      }
   }
}
