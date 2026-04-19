package xyz.pixelatedw.mineminenomi.abilities.toriphoenix;

import javax.annotation.Nullable;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.registries.RegistryObject;
import xyz.pixelatedw.mineminenomi.abilities.PropelledFlightAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityType;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityTooltipsHelper;
import xyz.pixelatedw.mineminenomi.api.util.Result;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityCapability;
import xyz.pixelatedw.mineminenomi.data.entity.ability.IAbilityData;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModMorphs;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class PhoenixFlightAbility extends PropelledFlightAbility {
   public static final RegistryObject<AbilityCore<PhoenixFlightAbility>> INSTANCE = ModRegistry.registerAbility("phoenix_flight", "Phoenix Flight", (id, name) -> (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, AbilityType.PASSIVE, PhoenixFlightAbility::new)).addDescriptionLine(AbilityDescriptionLine.NEW_LINE, AbilityTooltipsHelper.getRequiredMorphTooltip(ModMorphs.PHOENIX_FLY)).build("mineminenomi"));
   private PhoenixAssaultPointAbility assaultPointAbility;
   private PhoenixFlyPointAbility flyPointAbility;

   public PhoenixFlightAbility(AbilityCore<PhoenixFlightAbility> core) {
      super(core);
      this.addCanUseCheck(this::canFly);
      this.addDuringPassiveEvent(this::onDuringPassive);
   }

   private void onDuringPassive(LivingEntity entity) {
   }

   public float getMaxSpeed(LivingEntity entity) {
      float maxSpeed = 0.0F;
      IAbilityData abilityDataProps = (IAbilityData)AbilityCapability.get(entity).orElse((Object)null);
      if (abilityDataProps == null) {
         return 0.0F;
      } else {
         PhoenixAssaultPointAbility phoenixAssaultPointAbility = this.getAssaultPoint(entity);
         PhoenixFlyPointAbility phoenixFlyPointAbility = this.getFlyPoint(entity);
         if (phoenixAssaultPointAbility != null && phoenixAssaultPointAbility.isContinuous()) {
            maxSpeed = 0.4F;
         } else if (phoenixFlyPointAbility != null && phoenixFlyPointAbility.isContinuous()) {
            maxSpeed = 0.7F;
         }

         MobEffectInstance fatigueEffectInstance = entity.m_21124_((MobEffect)ModEffects.FATIGUE_EFFECT.get());
         if (fatigueEffectInstance != null) {
            maxSpeed /= 1.0F + Math.min((float)fatigueEffectInstance.m_19564_(), 3.0F);
         }

         return maxSpeed;
      }
   }

   protected float getAcceleration(LivingEntity entity) {
      IAbilityData abilityDataProps = (IAbilityData)AbilityCapability.get(entity).orElse((Object)null);
      if (abilityDataProps == null) {
         return 0.0F;
      } else {
         PhoenixAssaultPointAbility phoenixAssaultPointAbility = this.getAssaultPoint(entity);
         PhoenixFlyPointAbility phoenixFlyPointAbility = this.getFlyPoint(entity);
         if (phoenixAssaultPointAbility != null && phoenixAssaultPointAbility.isContinuous()) {
            return 0.0035F;
         } else {
            return phoenixFlyPointAbility != null && phoenixFlyPointAbility.isContinuous() ? 0.007F : 0.0F;
         }
      }
   }

   protected int getHeightDifference(LivingEntity entity) {
      return 128;
   }

   public float getSpeed(LivingEntity entity) {
      FujiazamiAbility fujiazamiAbility = (FujiazamiAbility)AbilityCapability.get(entity).map((props) -> (FujiazamiAbility)props.getEquippedAbility((AbilityCore)FujiazamiAbility.INSTANCE.get())).orElse((Object)null);
      if (fujiazamiAbility != null && fujiazamiAbility.isContinuous()) {
         return 0.0F;
      } else {
         MobEffectInstance fatigueEffectInstance = entity.m_21124_((MobEffect)ModEffects.FATIGUE_EFFECT.get());
         return fatigueEffectInstance != null ? this.speed * 1.0F - Math.min((float)fatigueEffectInstance.m_19564_(), 3.0F) : this.speed;
      }
   }

   private Result canFly(LivingEntity entity, IAbility ability) {
      IAbilityData abilityDataProps = (IAbilityData)AbilityCapability.get(entity).orElse((Object)null);
      if (abilityDataProps == null) {
         return Result.fail((Component)null);
      } else {
         PhoenixAssaultPointAbility phoenixAssaultPointAbility = (PhoenixAssaultPointAbility)abilityDataProps.getEquippedAbility((AbilityCore)PhoenixAssaultPointAbility.INSTANCE.get());
         PhoenixFlyPointAbility phoenixFlyPointAbility = (PhoenixFlyPointAbility)abilityDataProps.getEquippedAbility((AbilityCore)PhoenixFlyPointAbility.INSTANCE.get());
         TenseiNoSoenAbility tenseiNoSoenAbility = (TenseiNoSoenAbility)abilityDataProps.getEquippedAbility((AbilityCore)TenseiNoSoenAbility.INSTANCE.get());
         boolean isTenseiNoSoenActive = tenseiNoSoenAbility != null && tenseiNoSoenAbility.isContinuous();
         return (phoenixAssaultPointAbility == null || !phoenixAssaultPointAbility.isContinuous()) && (phoenixFlyPointAbility == null || !phoenixFlyPointAbility.isContinuous() || isTenseiNoSoenActive) ? Result.fail((Component)null) : Result.success();
      }
   }

   @Nullable
   public PhoenixAssaultPointAbility getAssaultPoint(LivingEntity entity) {
      if (this.assaultPointAbility != null) {
         return this.assaultPointAbility;
      } else {
         IAbilityData props = (IAbilityData)AbilityCapability.get(entity).orElse((Object)null);
         if (props == null) {
            return null;
         } else {
            this.assaultPointAbility = (PhoenixAssaultPointAbility)props.getEquippedAbility((AbilityCore)PhoenixAssaultPointAbility.INSTANCE.get());
            return this.assaultPointAbility;
         }
      }
   }

   @Nullable
   public PhoenixFlyPointAbility getFlyPoint(LivingEntity entity) {
      if (this.flyPointAbility != null) {
         return this.flyPointAbility;
      } else {
         IAbilityData props = (IAbilityData)AbilityCapability.get(entity).orElse((Object)null);
         if (props == null) {
            return null;
         } else {
            this.flyPointAbility = (PhoenixFlyPointAbility)props.getEquippedAbility((AbilityCore)PhoenixFlyPointAbility.INSTANCE.get());
            return this.flyPointAbility;
         }
      }
   }
}
