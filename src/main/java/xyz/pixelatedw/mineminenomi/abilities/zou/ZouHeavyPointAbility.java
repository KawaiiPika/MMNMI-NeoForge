package xyz.pixelatedw.mineminenomi.abilities.zou;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import xyz.pixelatedw.mineminenomi.api.abilities.zoan.ZoanAbility;

public class ZouHeavyPointAbility extends ZoanAbility {

    public ZouHeavyPointAbility() {
        super(ResourceLocation.fromNamespaceAndPath("mineminenomi", "zou_zou_no_mi"));
    }

    @Override
    public ResourceLocation getMorphModelId() {
        return ResourceLocation.fromNamespaceAndPath("mineminenomi", "zou_heavy");
    }

    @Override
    public double getScaleModifier() {
        return 0.5;
    }

    @Override
    public double getHealthModifier() {
        return 10.0;
    }

    @Override
    public double getDamageModifier() {
        return 7.0;
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.zou_heavy_point");
    }
}
