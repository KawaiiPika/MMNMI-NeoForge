package xyz.pixelatedw.mineminenomi.abilities.sai;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import xyz.pixelatedw.mineminenomi.api.abilities.zoan.ZoanAbility;

public class SaiHeavyPointAbility extends ZoanAbility {

    public SaiHeavyPointAbility() {
        super(ResourceLocation.fromNamespaceAndPath("mineminenomi", "sai_sai_no_mi"));
    }

    @Override
    public ResourceLocation getMorphModelId() {
        return ResourceLocation.fromNamespaceAndPath("mineminenomi", "sai_heavy");
    }

    @Override
    public double getScaleModifier() { return 0.3; }

    @Override
    public double getHealthModifier() { return 12; }

    @Override
    public double getDamageModifier() { return 7; }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.saiheavypoint");
    }
}
