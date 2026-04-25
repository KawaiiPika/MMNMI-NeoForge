package xyz.pixelatedw.mineminenomi.abilities.nekoleopard;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import xyz.pixelatedw.mineminenomi.api.abilities.zoan.ZoanAbility;

public class LeopardHeavyPointAbility extends ZoanAbility {

    public LeopardHeavyPointAbility() {
        super(ResourceLocation.fromNamespaceAndPath("mineminenomi", "neko_neko_no_mi_model_leopard"));
    }

    @Override
    public ResourceLocation getMorphModelId() {
        return ResourceLocation.fromNamespaceAndPath("mineminenomi", "leopard_heavy");
    }

    @Override
    public double getScaleModifier() { return 0.5; }

    @Override
    public double getHealthModifier() { return 10; }

    @Override
    public double getDamageModifier() { return 10; }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.leopardheavypoint");
    }
}
