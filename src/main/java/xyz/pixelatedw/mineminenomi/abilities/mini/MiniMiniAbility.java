package xyz.pixelatedw.mineminenomi.abilities.mini;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import xyz.pixelatedw.mineminenomi.api.abilities.zoan.ZoanAbility;

public class MiniMiniAbility extends ZoanAbility {

    public MiniMiniAbility() {
        super(ResourceLocation.fromNamespaceAndPath("mineminenomi", "mini_mini_no_mi"));
    }

    @Override
    public ResourceLocation getMorphModelId() {
        return ResourceLocation.fromNamespaceAndPath("mineminenomi", "mini_mini");
    }

    @Override
    public double getScaleModifier() { return 0.2; }

    @Override
    public double getHealthModifier() { return 0; }

    @Override
    public double getDamageModifier() { return 0; }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.mini_mini");
    }
}
