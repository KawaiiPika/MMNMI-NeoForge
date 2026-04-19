package xyz.pixelatedw.mineminenomi.abilities.gomu;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.registries.RegistryObject;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilitySet;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponentKey;
import xyz.pixelatedw.mineminenomi.api.helpers.MentionHelper;
import xyz.pixelatedw.mineminenomi.api.util.Result;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityCapability;
import xyz.pixelatedw.mineminenomi.data.entity.ability.IAbilityData;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18nAbilities;

public class GomuHelper {
   private static final AbilitySet GEARS;
   public static final AbilityDescriptionLine.IDescriptionLine SECOND_GEAR_REQ;
   public static final AbilityDescriptionLine.IDescriptionLine THIRD_GEAR_REQ;
   public static final AbilityDescriptionLine.IDescriptionLine FOURTH_GEAR_REQ;
   public static final AbilityDescriptionLine.IDescriptionLine FIFTH_GEAR_REQ;

   public static Result requiresGearFifth(LivingEntity entity, IAbility ability) {
      boolean hasGearFifthActive = (Boolean)AbilityCapability.get(entity).map((props) -> (GearFifthAbility)props.getEquippedAbility((AbilityCore)GearFifthAbility.INSTANCE.get())).map((abl) -> abl.isContinuous()).orElse(false);
      return hasGearFifthActive ? Result.success() : Result.fail((Component)null);
   }

   public static Ability.ICanUseEvent canUseGearCheck(AbilityCore<?> gear) {
      return (entity, ability) -> {
         IAbilityData props = (IAbilityData)AbilityCapability.get(entity).orElse((Object)null);
         if (props == null) {
            return Result.fail((Component)null);
         } else {
            boolean hasOtherGearActive = false;

            for(RegistryObject<? extends AbilityCore<?>> otherGear : GEARS) {
               if (!gear.equals(otherGear.get())) {
                  IAbility gearAbility = props.getEquippedAbility((AbilityCore)otherGear.get());
                  if (gearAbility != null) {
                     boolean isActive = (Boolean)gearAbility.getComponent((AbilityComponentKey)ModAbilityComponents.CONTINUOUS.get()).map((comp) -> comp.isContinuous()).orElse(false);
                     if (isActive) {
                        hasOtherGearActive = true;
                        break;
                     }
                  }
               }
            }

            return hasOtherGearActive ? Result.fail(ModI18nAbilities.MESSAGE_GEAR_ACTIVE) : Result.success();
         }
      };
   }

   static {
      GEARS = new AbilitySet(new RegistryObject[]{GearSecondAbility.INSTANCE, GearThirdAbility.INSTANCE, GearFourthAbility.INSTANCE});
      SECOND_GEAR_REQ = AbilityDescriptionLine.IDescriptionLine.of(Component.m_237110_(ModI18nAbilities.DEPENDENCY_SINGLE_ACTIVE, new Object[]{MentionHelper.tryParseAndMention(GearSecondAbility.INSTANCE)}));
      THIRD_GEAR_REQ = AbilityDescriptionLine.IDescriptionLine.of(Component.m_237110_(ModI18nAbilities.DEPENDENCY_SINGLE_ACTIVE, new Object[]{MentionHelper.tryParseAndMention(GearThirdAbility.INSTANCE)}));
      FOURTH_GEAR_REQ = AbilityDescriptionLine.IDescriptionLine.of(Component.m_237110_(ModI18nAbilities.DEPENDENCY_SINGLE_ACTIVE, new Object[]{MentionHelper.tryParseAndMention(GearFourthAbility.INSTANCE)}));
      FIFTH_GEAR_REQ = AbilityDescriptionLine.IDescriptionLine.of(Component.m_237110_(ModI18nAbilities.DEPENDENCY_SINGLE_ACTIVE, new Object[]{MentionHelper.tryParseAndMention(GearFifthAbility.INSTANCE)}));
   }

   public static enum Gears {
      NO_GEAR,
      GEAR_2,
      GEAR_3,
      GEAR_4,
      GEAR_5;

      // $FF: synthetic method
      private static Gears[] $values() {
         return new Gears[]{NO_GEAR, GEAR_2, GEAR_3, GEAR_4, GEAR_5};
      }
   }
}
