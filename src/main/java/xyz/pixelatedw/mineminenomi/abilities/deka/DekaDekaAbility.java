package xyz.pixelatedw.mineminenomi.abilities.deka;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import xyz.pixelatedw.mineminenomi.api.abilities.zoan.ZoanAbility;

public class DekaDekaAbility extends ZoanAbility {

    public DekaDekaAbility() {
        super(ResourceLocation.fromNamespaceAndPath("mineminenomi", "deka_deka_no_mi"));
    }

    @Override
    public ResourceLocation getMorphModelId() {
        return ResourceLocation.fromNamespaceAndPath("mineminenomi", "deka_deka");
    }

    @Override
    public double getScaleModifier() { return 5.0; }

    @Override
    public double getHealthModifier() { return 50; }

    @Override
    public double getDamageModifier() { return 20; }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.deka_deka");
    }
}
