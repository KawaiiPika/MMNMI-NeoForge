package xyz.pixelatedw.mineminenomi.abilities.nekoleopard;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import xyz.pixelatedw.mineminenomi.api.abilities.zoan.ZoanAbility;

public class LeopardWalkPointAbility extends ZoanAbility {

    public LeopardWalkPointAbility() {
        super(ResourceLocation.fromNamespaceAndPath("mineminenomi", "neko_neko_no_mi_model_leopard"));
    }

    @Override
    public ResourceLocation getMorphModelId() {
        return ResourceLocation.fromNamespaceAndPath("mineminenomi", "leopard_walk");
    }

    @Override
    public double getScaleModifier() { return 0.0; }

    @Override
    public double getHealthModifier() { return 0; }

    @Override
    public double getDamageModifier() { return 6; }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.leopardwalkpoint");
    }
}
