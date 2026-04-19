package xyz.pixelatedw.mineminenomi.abilities.ryubrachiosaurus;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import xyz.pixelatedw.mineminenomi.api.abilities.zoan.ZoanAbility;

public class BrachiosaurusHeavyPointAbility extends ZoanAbility {

    public BrachiosaurusHeavyPointAbility() {
        super(ResourceLocation.fromNamespaceAndPath("mineminenomi", "ryu_ryu_no_mi_model_2"));
    }

    @Override
    public ResourceLocation getMorphModelId() {
        return ResourceLocation.fromNamespaceAndPath("mineminenomi", "brachiosaurus_heavy");
    }

    @Override
    public double getScaleModifier() { return 1; }

    @Override
    public double getHealthModifier() { return 30; }

    @Override
    public double getDamageModifier() { return 8; }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.brachiosaurusheavypoint");
    }
}
