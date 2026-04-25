package xyz.pixelatedw.mineminenomi.abilities.zou;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import xyz.pixelatedw.mineminenomi.api.abilities.zoan.ZoanAbility;

public class ZouGuardPointAbility extends ZoanAbility {

    public ZouGuardPointAbility() {
        super(ResourceLocation.fromNamespaceAndPath("mineminenomi", "zou_zou_no_mi"));
    }

    @Override
    public ResourceLocation getMorphModelId() {
        return ResourceLocation.fromNamespaceAndPath("mineminenomi", "zou_guard");
    }

    @Override
    public double getScaleModifier() {
        return 1.5; // Increases size by 150% (total 2.5x)
    }

    @Override
    public double getHealthModifier() {
        return 40.0; // Adds 20 hearts
    }

    @Override
    public double getDamageModifier() {
        return 4.0; // Adds 4 damage
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.zou_guard_point");
    }
}
