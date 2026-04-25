package xyz.pixelatedw.mineminenomi.abilities.zoumammoth;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import xyz.pixelatedw.mineminenomi.api.abilities.zoan.ZoanAbility;

public class MammothHeavyPointAbility extends ZoanAbility {

    public MammothHeavyPointAbility() {
        super(ResourceLocation.fromNamespaceAndPath("mineminenomi", "zou_zou_no_mi_model_mammoth"));
    }

    @Override
    public ResourceLocation getMorphModelId() {
        return ResourceLocation.fromNamespaceAndPath("mineminenomi", "mammoth_heavy");
    }

    @Override
    public double getScaleModifier() { return 0.8; }

    @Override
    public double getHealthModifier() { return 15; }

    @Override
    public double getDamageModifier() { return 7; }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.mammothheavypoint");
    }
}
