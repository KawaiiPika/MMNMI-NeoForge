package xyz.pixelatedw.mineminenomi.abilities.ryuallosaurus;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import xyz.pixelatedw.mineminenomi.api.abilities.zoan.ZoanAbility;

public class AllosaurusHeavyPointAbility extends ZoanAbility {

    public AllosaurusHeavyPointAbility() {
        super(ResourceLocation.fromNamespaceAndPath("mineminenomi", "ryu_ryu_no_mi_model_1"));
    }

    @Override
    public ResourceLocation getMorphModelId() {
        return ResourceLocation.fromNamespaceAndPath("mineminenomi", "allosaurus_walk");
    }

    @Override
    public double getScaleModifier() { return 0.8; }

    @Override
    public double getHealthModifier() { return 20; }

    @Override
    public double getDamageModifier() { return 12; }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.allosaurusheavypoint");
    }
}
