package xyz.pixelatedw.mineminenomi.abilities.ushibison;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import xyz.pixelatedw.mineminenomi.api.abilities.zoan.ZoanAbility;

public class BisonHeavyPointAbility extends ZoanAbility {

    public BisonHeavyPointAbility() {
        super(ResourceLocation.fromNamespaceAndPath("mineminenomi", "ushi_ushi_no_mi_model_1"));
    }

    @Override
    public ResourceLocation getMorphModelId() {
        return ResourceLocation.fromNamespaceAndPath("mineminenomi", "bison_heavy");
    }

    @Override
    public double getScaleModifier() { return 0.4; }

    @Override
    public double getHealthModifier() { return 25; }

    @Override
    public double getDamageModifier() { return 8; }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.bisonheavypoint");
    }
}
