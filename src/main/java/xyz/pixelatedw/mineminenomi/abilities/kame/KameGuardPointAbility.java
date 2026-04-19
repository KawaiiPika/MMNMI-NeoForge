package xyz.pixelatedw.mineminenomi.abilities.kame;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import xyz.pixelatedw.mineminenomi.api.abilities.zoan.ZoanAbility;

public class KameGuardPointAbility extends ZoanAbility {

    public KameGuardPointAbility() {
        super(ResourceLocation.fromNamespaceAndPath("mineminenomi", "kame_kame_no_mi"));
    }

    @Override
    public ResourceLocation getMorphModelId() {
        return ResourceLocation.fromNamespaceAndPath("mineminenomi", "kame_guard");
    }

    @Override
    public double getScaleModifier() { return 0; }

    @Override
    public double getHealthModifier() { return 15; }

    @Override
    public double getDamageModifier() { return 1; }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.kameguardpoint");
    }
}
