package xyz.pixelatedw.mineminenomi.abilities.ryupteranodon;

import javax.annotation.Nullable;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.registries.RegistryObject;
import xyz.pixelatedw.mineminenomi.abilities.PropelledFlightAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityType;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityTooltipsHelper;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityCapability;
import xyz.pixelatedw.mineminenomi.data.entity.ability.IAbilityData;
import xyz.pixelatedw.mineminenomi.init.ModMorphs;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class PteranodonFlightAbility extends PropelledFlightAbility {
   public static final RegistryObject<AbilityCore<PteranodonFlightAbility>> INSTANCE = ModRegistry.registerAbility("pteranodon_flight", "Pteranodon Flight", (id, name) -> (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, AbilityType.PASSIVE, PteranodonFlightAbility::new)).addDescriptionLine(AbilityDescriptionLine.NEW_LINE, AbilityTooltipsHelper.getRequiredMorphTooltip(ModMorphs.PTERA_ASSAULT, ModMorphs.PTERA_FLY)).build("mineminenomi"));
   private PteranodonAssaultPointAbility assaultPointAbility;
   private PteranodonFlyPointAbility flyPointAbility;

   public PteranodonFlightAbility(AbilityCore<PteranodonFlightAbility> core) {
      super(core);
      this.addCanUseCheck(RyuPteraHelper::requiresEitherPoint);
      this.addDuringPassiveEvent(this::onDuringPassive);
   }

   private void onDuringPassive(LivingEntity entity) {
   }

   public float getMaxSpeed(LivingEntity entity) {
      IAbilityData porps = (IAbilityData)AbilityCapability.get(entity).orElse((Object)null);
      if (porps == null) {
         return 0.0F;
      } else {
         PteranodonAssaultPointAbility assaultPoint = this.getAssaultPoint(entity);
         PteranodonFlyPointAbility flyPoint = this.getFlyPoint(entity);
         if (assaultPoint != null && assaultPoint.isContinuous()) {
            return 0.2F;
         } else {
            return flyPoint != null && flyPoint.isContinuous() ? 0.5F : 0.0F;
         }
      }
   }

   protected float getAcceleration(LivingEntity entity) {
      IAbilityData props = (IAbilityData)AbilityCapability.get(entity).orElse((Object)null);
      if (props == null) {
         return 0.0F;
      } else {
         PteranodonAssaultPointAbility assaultPoint = this.getAssaultPoint(entity);
         PteranodonFlyPointAbility flyPoint = this.getFlyPoint(entity);
         if (assaultPoint != null && assaultPoint.isContinuous()) {
            return 0.003F;
         } else {
            return flyPoint != null && flyPoint.isContinuous() ? 0.006F : 0.0F;
         }
      }
   }

   protected int getHeightDifference(LivingEntity entity) {
      return 128;
   }

   public float getSpeed(LivingEntity entity) {
      IAbilityData abilityDataProps = (IAbilityData)AbilityCapability.get(entity).orElse((Object)null);
      if (abilityDataProps == null) {
         return 0.0F;
      } else {
         TempuraudonAbility tempuraudonAbility = (TempuraudonAbility)abilityDataProps.getEquippedAbility((AbilityCore)TempuraudonAbility.INSTANCE.get());
         TankyudonAbility tankyudonAbility = (TankyudonAbility)abilityDataProps.getEquippedAbility((AbilityCore)TankyudonAbility.INSTANCE.get());
         BeakGrabAbility beakGrabAbility = (BeakGrabAbility)abilityDataProps.getEquippedAbility((AbilityCore)BeakGrabAbility.INSTANCE.get());
         if (tempuraudonAbility != null && tempuraudonAbility.isCharging()) {
            return 0.0F;
         } else if ((tankyudonAbility == null || !tankyudonAbility.isContinuous()) && (beakGrabAbility == null || !beakGrabAbility.isContinuous())) {
            return this.speed;
         } else {
            entity.f_20902_ = 0.98F;
            return ++this.speed;
         }
      }
   }

   @Nullable
   public PteranodonAssaultPointAbility getAssaultPoint(LivingEntity entity) {
      if (this.assaultPointAbility != null) {
         return this.assaultPointAbility;
      } else {
         IAbilityData props = (IAbilityData)AbilityCapability.get(entity).orElse((Object)null);
         if (props == null) {
            return null;
         } else {
            this.assaultPointAbility = (PteranodonAssaultPointAbility)props.getEquippedAbility((AbilityCore)PteranodonAssaultPointAbility.INSTANCE.get());
            return this.assaultPointAbility;
         }
      }
   }

   @Nullable
   public PteranodonFlyPointAbility getFlyPoint(LivingEntity entity) {
      if (this.flyPointAbility != null) {
         return this.flyPointAbility;
      } else {
         IAbilityData props = (IAbilityData)AbilityCapability.get(entity).orElse((Object)null);
         if (props == null) {
            return null;
         } else {
            this.flyPointAbility = (PteranodonFlyPointAbility)props.getEquippedAbility((AbilityCore)PteranodonFlyPointAbility.INSTANCE.get());
            return this.flyPointAbility;
         }
      }
   }
}
