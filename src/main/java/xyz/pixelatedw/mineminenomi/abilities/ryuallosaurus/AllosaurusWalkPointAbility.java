package xyz.pixelatedw.mineminenomi.abilities.ryuallosaurus;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import xyz.pixelatedw.mineminenomi.api.abilities.zoan.ZoanAbility;

public class AllosaurusWalkPointAbility extends ZoanAbility {

    public AllosaurusWalkPointAbility() {
        super(ResourceLocation.fromNamespaceAndPath("mineminenomi", "ryu_ryu_no_mi_model_1"));
    }

    @Override
    public ResourceLocation getMorphModelId() {
        return ResourceLocation.fromNamespaceAndPath("mineminenomi", "allosaurus_walk");
    }

    @Override
    public double getScaleModifier() { return 1.5; }

    @Override
    public double getHealthModifier() { return 30; }

    @Override
    public double getDamageModifier() { return 8; }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.allosauruswalkpoint");
    }
}
