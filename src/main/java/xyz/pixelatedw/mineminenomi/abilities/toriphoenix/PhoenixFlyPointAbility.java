package xyz.pixelatedw.mineminenomi.abilities.toriphoenix;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import xyz.pixelatedw.mineminenomi.api.abilities.zoan.ZoanAbility;

public class PhoenixFlyPointAbility extends ZoanAbility {

    public PhoenixFlyPointAbility() {
        super(ResourceLocation.fromNamespaceAndPath("mineminenomi", "tori_tori_no_mi_model_1"));
    }

    @Override
    public ResourceLocation getMorphModelId() {
        return ResourceLocation.fromNamespaceAndPath("mineminenomi", "phoenix_fly");
    }

    @Override
    public double getScaleModifier() { return 0.3; }

    @Override
    public double getHealthModifier() { return 15; }

    @Override
    public double getDamageModifier() { return 4; }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.phoenixflypoint");
    }
}
