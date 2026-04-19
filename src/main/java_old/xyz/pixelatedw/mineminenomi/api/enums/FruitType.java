package xyz.pixelatedw.mineminenomi.api.enums;

import net.minecraft.ChatFormatting;

public enum FruitType {
   PARAMECIA(ChatFormatting.RED, "Paramecia"),
   LOGIA(ChatFormatting.YELLOW, "Logia"),
   ZOAN(ChatFormatting.GREEN, "Zoan"),
   MYTHICAL_ZOAN(ChatFormatting.AQUA, "Mythical Zoan"),
   ANCIENT_ZOAN(ChatFormatting.DARK_GREEN, "Ancient Zoan"),
   ARTIFICIAL_LOGIA(ChatFormatting.GOLD, "Artificial Logia"),
   ARTIFICIAL_PARAMECIA(ChatFormatting.GOLD, "Artificial Paramecia"),
   ARTIFICIAL_ZOAN(ChatFormatting.GOLD, "Artificial Zoan");

   private ChatFormatting color;
   private String name;

   private FruitType(ChatFormatting color, String name) {
      this.color = color;
      this.name = name;
   }

   public ChatFormatting getColor() {
      return this.color;
   }

   public String getName() {
      return this.name;
   }

   // $FF: synthetic method
   private static FruitType[] $values() {
      return new FruitType[]{PARAMECIA, LOGIA, ZOAN, MYTHICAL_ZOAN, ANCIENT_ZOAN, ARTIFICIAL_LOGIA, ARTIFICIAL_PARAMECIA, ARTIFICIAL_ZOAN};
   }
}
