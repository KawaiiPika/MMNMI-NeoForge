package xyz.pixelatedw.mineminenomi.abilities.sai;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import xyz.pixelatedw.mineminenomi.api.abilities.zoan.ZoanAbility;

public class SaiWalkPointAbility extends ZoanAbility {

    public SaiWalkPointAbility() {
        super(ResourceLocation.fromNamespaceAndPath("mineminenomi", "sai_sai_no_mi"));
    }

    @Override
    public ResourceLocation getMorphModelId() {
        return ResourceLocation.fromNamespaceAndPath("mineminenomi", "sai_walk");
    }

    @Override
    public double getScaleModifier() { return 0.4; }

    @Override
    public double getHealthModifier() { return 15; }

    @Override
    public double getDamageModifier() { return 4; }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.saiwalkpoint");
    }
}
