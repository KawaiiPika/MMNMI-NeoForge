package xyz.pixelatedw.mineminenomi.abilities.doa;

import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityCapability;

public class DoaHelper {
   public static boolean isInsideDoor(LivingEntity entity) {
      boolean isInsideDoor = (Boolean)AbilityCapability.get(entity).map((props) -> (AirDoorAbility)props.getEquippedAbility((AbilityCore)AirDoorAbility.INSTANCE.get())).map((props) -> props.isContinuous()).orElse(false);
      return isInsideDoor;
   }
}
