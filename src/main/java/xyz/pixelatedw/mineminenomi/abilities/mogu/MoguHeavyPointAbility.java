package xyz.pixelatedw.mineminenomi.abilities.mogu;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import xyz.pixelatedw.mineminenomi.api.abilities.zoan.ZoanAbility;

public class MoguHeavyPointAbility extends ZoanAbility {

    public MoguHeavyPointAbility() {
        super(ResourceLocation.fromNamespaceAndPath("mineminenomi", "mogu_mogu_no_mi"));
    }

    @Override
    public ResourceLocation getMorphModelId() {
        return ResourceLocation.fromNamespaceAndPath("mineminenomi", "mogu_heavy");
    }

    @Override
    public double getScaleModifier() { return 0.2; }

    @Override
    public double getHealthModifier() { return 10; }

    @Override
    public double getDamageModifier() { return 5; }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.moguheavypoint");
    }
}
