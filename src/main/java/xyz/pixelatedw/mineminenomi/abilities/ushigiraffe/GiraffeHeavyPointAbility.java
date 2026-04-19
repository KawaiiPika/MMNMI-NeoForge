package xyz.pixelatedw.mineminenomi.abilities.ushigiraffe;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import xyz.pixelatedw.mineminenomi.api.abilities.zoan.ZoanAbility;

public class GiraffeHeavyPointAbility extends ZoanAbility {

    public GiraffeHeavyPointAbility() {
        super(ResourceLocation.fromNamespaceAndPath("mineminenomi", "ushi_ushi_no_mi_model_2"));
    }

    @Override
    public ResourceLocation getMorphModelId() {
        return ResourceLocation.fromNamespaceAndPath("mineminenomi", "giraffe_heavy");
    }

    @Override
    public double getScaleModifier() { return 0.8; }

    @Override
    public double getHealthModifier() { return 15; }

    @Override
    public double getDamageModifier() { return 6; }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.giraffeheavypoint");
    }
}
