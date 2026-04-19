package xyz.pixelatedw.mineminenomi.abilities.ryubrachiosaurus;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import xyz.pixelatedw.mineminenomi.api.abilities.zoan.ZoanAbility;

public class BrachiosaurusGuardPointAbility extends ZoanAbility {

    public BrachiosaurusGuardPointAbility() {
        super(ResourceLocation.fromNamespaceAndPath("mineminenomi", "ryu_ryu_no_mi_model_2"));
    }

    @Override
    public ResourceLocation getMorphModelId() {
        return ResourceLocation.fromNamespaceAndPath("mineminenomi", "brachiosaurus_guard");
    }

    @Override
    public double getScaleModifier() { return 2; }

    @Override
    public double getHealthModifier() { return 60; }

    @Override
    public double getDamageModifier() { return 5; }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.brachiosaurusguardpoint");
    }
}
