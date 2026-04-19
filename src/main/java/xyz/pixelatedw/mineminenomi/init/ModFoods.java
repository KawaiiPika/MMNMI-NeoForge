package xyz.pixelatedw.mineminenomi.init;

import net.minecraft.world.food.FoodProperties;

public class ModFoods {
    public static final FoodProperties COLA = new FoodProperties.Builder().nutrition(1).saturationModifier(0.0F).alwaysEdible().build();
    public static final FoodProperties SEA_KING_MEAT = new FoodProperties.Builder().nutrition(4).saturationModifier(0.6F).build();
    public static final FoodProperties COOKED_SEA_KING_MEAT = new FoodProperties.Builder().nutrition(8).saturationModifier(1.2F).build();
    public static final FoodProperties ALCOHOL = new FoodProperties.Builder().nutrition(1).saturationModifier(2.0F).alwaysEdible().build();
    public static final FoodProperties TANGERINE = new FoodProperties.Builder().nutrition(4).saturationModifier(0.3F).build();
}
