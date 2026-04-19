package xyz.pixelatedw.mineminenomi.api.enums;

import net.minecraftforge.common.IExtensibleEnum;

public enum TrainingPointType implements IExtensibleEnum {
   MARKSMANSHIP,
   MARTIAL_ARTS,
   TECHNOLOGY,
   WEAPON_MASTERY;

   public static TrainingPointType create(String name) {
      throw new IllegalStateException("Enum not extended");
   }

   // $FF: synthetic method
   private static TrainingPointType[] $values() {
      return new TrainingPointType[]{MARKSMANSHIP, MARTIAL_ARTS, TECHNOLOGY, WEAPON_MASTERY};
   }
}
