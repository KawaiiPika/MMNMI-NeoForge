package xyz.pixelatedw.mineminenomi.abilities.ushigiraffe;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import xyz.pixelatedw.mineminenomi.api.abilities.zoan.ZoanAbility;

public class GiraffeWalkPointAbility extends ZoanAbility {

    public GiraffeWalkPointAbility() {
        super(ResourceLocation.fromNamespaceAndPath("mineminenomi", "ushi_ushi_no_mi_model_2"));
    }

    @Override
    public ResourceLocation getMorphModelId() {
        return ResourceLocation.fromNamespaceAndPath("mineminenomi", "giraffe_walk");
    }

    @Override
    public double getScaleModifier() { return 1.2; }

    @Override
    public double getHealthModifier() { return 20; }

    @Override
    public double getDamageModifier() { return 4; }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.giraffewalkpoint");
    }
}
