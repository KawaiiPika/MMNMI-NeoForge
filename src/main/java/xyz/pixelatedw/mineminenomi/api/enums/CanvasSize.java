package xyz.pixelatedw.mineminenomi.api.enums;

import net.minecraft.util.StringRepresentable;

public enum CanvasSize implements StringRepresentable {
   SMALL("small"),
   MEDIUM("medium"),
   LARGE("large"),
   HUGE("huge");

   private final String name;

   private CanvasSize(String name) {
      this.name = name;
   }

   public String getSerializedName() {
      return this.name;
   }

   public boolean isMaximumSize() {
      return this.ordinal() >= values().length - 1;
   }

   // $FF: synthetic method
   private static CanvasSize[] $values() {
      return new CanvasSize[]{SMALL, MEDIUM, LARGE, HUGE};
   }
}
