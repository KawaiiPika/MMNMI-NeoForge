package xyz.pixelatedw.mineminenomi.abilities.saraaxolotl;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import xyz.pixelatedw.mineminenomi.api.abilities.zoan.ZoanAbility;

public class AxolotlHeavyPointAbility extends ZoanAbility {

    public AxolotlHeavyPointAbility() {
        super(ResourceLocation.fromNamespaceAndPath("mineminenomi", "sara_sara_no_mi"));
    }

    @Override
    public ResourceLocation getMorphModelId() {
        return ResourceLocation.fromNamespaceAndPath("mineminenomi", "axolotl_heavy");
    }

    @Override
    public double getScaleModifier() { return 0.2; }

    @Override
    public double getHealthModifier() { return 15; }

    @Override
    public double getDamageModifier() { return 3; }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.axolotlheavypoint");
    }
}
