package xyz.pixelatedw.mineminenomi.api.abilities;

import net.minecraftforge.common.IExtensibleEnum;

public enum AbilityUnlock implements IExtensibleEnum {
   NONE,
   PROGRESSION,
   COMMAND;

   public static AbilityUnlock create(String name) {
      throw new IllegalStateException("Enum not extended");
   }

   // $FF: synthetic method
   private static AbilityUnlock[] $values() {
      return new AbilityUnlock[]{NONE, PROGRESSION, COMMAND};
   }
}
