package xyz.pixelatedw.mineminenomi.abilities.goro;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.registries.RegistryObject;
import xyz.pixelatedw.mineminenomi.abilities.PropelledFlightAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityType;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.util.Result;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityCapability;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class VoltAmaruFlightAbility extends PropelledFlightAbility {
   public static final RegistryObject<AbilityCore<VoltAmaruFlightAbility>> INSTANCE = ModRegistry.registerAbility("volt_amaru_flight", "Volt Amaru Flight", (id, name) -> (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, AbilityType.PASSIVE, VoltAmaruFlightAbility::new)).build("mineminenomi"));

   public VoltAmaruFlightAbility(AbilityCore<VoltAmaruFlightAbility> core) {
      super(core);
      this.addCanUseCheck(this::canFly);
      this.addDuringPassiveEvent(this::onDuringPassive);
   }

   private void onDuringPassive(LivingEntity entity) {
      if (entity instanceof Player player) {
         if (!super.isRecovering) {
            double difference = AbilityHelper.getDifferenceToFloor(entity);
            if (difference < (double)5.0F && player.m_150110_().f_35935_) {
               AbilityHelper.setDeltaMovement(entity, entity.m_20184_().m_82520_((double)0.0F, (double)1.0F, (double)0.0F).m_82542_((double)1.0F, (double)0.25F, (double)1.0F));
            }
         }

      }
   }

   public float getMaxSpeed(LivingEntity entity) {
      return entity.m_20142_() ? 2.1F : 1.1F;
   }

   protected float getAcceleration(LivingEntity entity) {
      return 0.015F;
   }

   protected int getHeightDifference(LivingEntity entity) {
      return 36;
   }

   private Result canFly(LivingEntity entity, IAbility ability) {
      boolean isActive = (Boolean)AbilityCapability.get(entity).map((props) -> (VoltAmaruAbility)props.getEquippedAbility((AbilityCore)VoltAmaruAbility.INSTANCE.get())).map((abl) -> abl.isContinuous()).orElse(false);
      return isActive ? Result.success() : Result.fail((Component)null);
   }
}
