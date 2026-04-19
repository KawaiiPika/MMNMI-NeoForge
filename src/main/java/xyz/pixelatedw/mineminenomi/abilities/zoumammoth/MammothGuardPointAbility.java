package xyz.pixelatedw.mineminenomi.abilities.zoumammoth;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import xyz.pixelatedw.mineminenomi.api.abilities.zoan.ZoanAbility;

public class MammothGuardPointAbility extends ZoanAbility {

    public MammothGuardPointAbility() {
        super(ResourceLocation.fromNamespaceAndPath("mineminenomi", "zou_zou_no_mi_model_1"));
    }

    @Override
    public ResourceLocation getMorphModelId() {
        return ResourceLocation.fromNamespaceAndPath("mineminenomi", "mammoth_guard");
    }

    @Override
    public double getScaleModifier() { return 1.8; }

    @Override
    public double getHealthModifier() { return 50; }

    @Override
    public double getDamageModifier() { return 6; }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.mammothguardpoint");
    }
}
