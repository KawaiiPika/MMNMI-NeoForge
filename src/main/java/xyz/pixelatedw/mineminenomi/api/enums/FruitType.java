package xyz.pixelatedw.mineminenomi.api.enums;

import net.minecraft.ChatFormatting;

public enum FruitType {
    PARAMECIA("Paramecia", ChatFormatting.BLUE),
    LOGIA("Logia", ChatFormatting.GOLD),
    ZOAN("Zoan", ChatFormatting.GREEN),
    ANCIENT_ZOAN("Ancient Zoan", ChatFormatting.DARK_GREEN),
    MYTHICAL_ZOAN("Mythical Zoan", ChatFormatting.DARK_PURPLE);

    private final String name;
    private final ChatFormatting color;

    FruitType(String name, ChatFormatting color) {
        this.name = name;
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public ChatFormatting getColor() {
        return color;
    }
}
