package xyz.pixelatedw.mineminenomi.abilities.ryupteranodon;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import xyz.pixelatedw.mineminenomi.api.abilities.zoan.ZoanAbility;

public class PteranodonFlyPointAbility extends ZoanAbility {

    public PteranodonFlyPointAbility() {
        super(ResourceLocation.fromNamespaceAndPath("mineminenomi", "ryu_ryu_no_mi_model_3"));
    }

    @Override
    public ResourceLocation getMorphModelId() {
        return ResourceLocation.fromNamespaceAndPath("mineminenomi", "pteranodon_fly");
    }

    @Override
    public double getScaleModifier() { return 0.5; }

    @Override
    public double getHealthModifier() { return 10; }

    @Override
    public double getDamageModifier() { return 4; }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.pteranodonflypoint");
    }
}
