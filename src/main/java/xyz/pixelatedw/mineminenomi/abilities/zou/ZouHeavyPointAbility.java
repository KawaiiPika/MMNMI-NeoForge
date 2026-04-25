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
        return 0.5; // Increases size by 50% (total 1.5x)
    }

    @Override
    public double getHealthModifier() {
        return 20.0; // Adds 10 hearts
    }

    @Override
    public double getDamageModifier() {
        return 6.0; // Adds 6 damage
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.zou_heavy_point");
    }
}
