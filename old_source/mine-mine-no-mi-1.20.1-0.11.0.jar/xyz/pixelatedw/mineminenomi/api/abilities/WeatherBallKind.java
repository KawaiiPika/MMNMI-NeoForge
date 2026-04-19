package xyz.pixelatedw.mineminenomi.api.abilities;

import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.Nullable;

public enum WeatherBallKind {
   HEAT((byte)1),
   COOL((byte)2),
   THUNDER((byte)3);

   private byte kind;

   private WeatherBallKind(byte kind) {
      this.kind = kind;
   }

   public byte getKind() {
      return this.kind;
   }

   public static @Nullable WeatherBallKind from(byte b0) {
      switch (b0) {
         case 1 -> {
            return HEAT;
         }
         case 2 -> {
            return COOL;
         }
         case 3 -> {
            return THUNDER;
         }
         default -> {
            return null;
         }
      }
   }

   public String toString() {
      return Component.m_237115_("ability.mineminenomi." + this.name().toLowerCase() + "_ball").getString();
   }

   // $FF: synthetic method
   private static WeatherBallKind[] $values() {
      return new WeatherBallKind[]{HEAT, COOL, THUNDER};
   }
}
