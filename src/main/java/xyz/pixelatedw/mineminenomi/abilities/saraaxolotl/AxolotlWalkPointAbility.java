package xyz.pixelatedw.mineminenomi.abilities.saraaxolotl;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import xyz.pixelatedw.mineminenomi.api.abilities.zoan.ZoanAbility;

public class AxolotlWalkPointAbility extends ZoanAbility {

    public AxolotlWalkPointAbility() {
        super(ResourceLocation.fromNamespaceAndPath("mineminenomi", "sara_sara_no_mi"));
    }

    @Override
    public ResourceLocation getMorphModelId() {
        return ResourceLocation.fromNamespaceAndPath("mineminenomi", "axolotl_walk");
    }

    @Override
    public double getScaleModifier() { return 0.3; }

    @Override
    public double getHealthModifier() { return 10; }

    @Override
    public double getDamageModifier() { return 2; }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.axolotlwalkpoint");
    }
}
