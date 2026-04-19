package xyz.pixelatedw.mineminenomi.abilities.gomu;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.RegistryObject;
import xyz.pixelatedw.mineminenomi.abilities.PropelledFlightAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityType;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.util.Result;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityCapability;
import xyz.pixelatedw.mineminenomi.data.entity.ability.IAbilityData;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class GearFourthFlightAbility extends PropelledFlightAbility {
   public static final RegistryObject<AbilityCore<GearFourthFlightAbility>> INSTANCE = ModRegistry.registerAbility("gear_fourth_flight", "Gear Fourth Flight", (id, name) -> (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, AbilityType.PASSIVE, GearFourthFlightAbility::new)).setHidden().build("mineminenomi"));

   public GearFourthFlightAbility(AbilityCore<GearFourthFlightAbility> core) {
      super(core);
      this.addCanUseCheck(this::canFly);
      this.addDuringPassiveEvent(this::onDuringPassive);
   }

   private void onDuringPassive(LivingEntity entity) {
   }

   public float getMaxSpeed(LivingEntity entity) {
      return 1.1F;
   }

   protected float getAcceleration(LivingEntity entity) {
      return 0.01F;
   }

   protected int getHeightDifference(LivingEntity entity) {
      return 40;
   }

   protected Vec3 getMovementVector(LivingEntity entity) {
      int d1 = entity.m_20096_() ? 1 : 0;
      Vec3 lookVector = entity.m_20154_();
      double x = lookVector.f_82479_ * (double)super.speed * ((double)1.0F - (double)d1) * (double)entity.f_20902_;
      double y = (double)d1 * 0.6 + lookVector.f_82480_ * (double)super.speed * ((double)1.0F - (double)d1) * (double)entity.f_20902_ + Math.cos((double)entity.f_19797_ / ((double)4.0F - (double)super.speed * (double)1.25F)) / (double)5.0F;
      double z = lookVector.f_82481_ * (double)super.speed * ((double)1.0F - (double)d1) * (double)entity.f_20902_;
      return new Vec3(x, y, z);
   }

   private Result canFly(LivingEntity entity, IAbility ability) {
      IAbilityData props = (IAbilityData)AbilityCapability.get(entity).orElse((Object)null);
      if (props == null) {
         return Result.fail((Component)null);
      } else {
         GearFourthAbility gearFourthAbility = (GearFourthAbility)props.getEquippedAbility((AbilityCore)GearFourthAbility.INSTANCE.get());
         boolean isGearFourthActive = gearFourthAbility != null && gearFourthAbility.isContinuous();
         return isGearFourthActive ? Result.success() : Result.fail((Component)null);
      }
   }

   public boolean canRenderGauge(Player entity) {
      IAbilityData props = (IAbilityData)AbilityCapability.get(entity).orElse((Object)null);
      if (props == null) {
         return false;
      } else {
         GearFourthAbility gearFourthAbility = (GearFourthAbility)props.getEquippedAbility((AbilityCore)GearFourthAbility.INSTANCE.get());
         boolean isGearFourthActive = gearFourthAbility != null && gearFourthAbility.isContinuous();
         return isGearFourthActive;
      }
   }
}
