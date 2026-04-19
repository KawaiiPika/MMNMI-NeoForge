package xyz.pixelatedw.mineminenomi.abilities.ushibison;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import xyz.pixelatedw.mineminenomi.api.abilities.zoan.ZoanAbility;

public class BisonWalkPointAbility extends ZoanAbility {

    public BisonWalkPointAbility() {
        super(ResourceLocation.fromNamespaceAndPath("mineminenomi", "ushi_ushi_no_mi_model_1"));
    }

    @Override
    public ResourceLocation getMorphModelId() {
        return ResourceLocation.fromNamespaceAndPath("mineminenomi", "bison_walk");
    }

    @Override
    public double getScaleModifier() { return 0.5; }

    @Override
    public double getHealthModifier() { return 20; }

    @Override
    public double getDamageModifier() { return 5; }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.bisonwalkpoint");
    }
}
