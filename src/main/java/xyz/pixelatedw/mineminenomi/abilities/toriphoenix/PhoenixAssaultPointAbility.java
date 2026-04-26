package xyz.pixelatedw.mineminenomi.abilities.toriphoenix;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import xyz.pixelatedw.mineminenomi.api.abilities.zoan.ZoanAbility;

public class PhoenixAssaultPointAbility extends ZoanAbility {

    public PhoenixAssaultPointAbility() {
        super(ResourceLocation.fromNamespaceAndPath("mineminenomi", "tori_tori_no_mi_model_1"));
    }

    @Override
    public ResourceLocation getMorphModelId() {
        return ResourceLocation.fromNamespaceAndPath("mineminenomi", "phoenix2");
    }

    @Override
    public double getScaleModifier() { return 0.2; }

    @Override
    public double getHealthModifier() { return 10; }

    @Override
    public double getDamageModifier() { return 6; }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.phoenixassaultpoint");
    }
}
