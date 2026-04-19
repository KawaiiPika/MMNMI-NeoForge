package xyz.pixelatedw.mineminenomi.abilities.kame;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import xyz.pixelatedw.mineminenomi.api.abilities.zoan.ZoanAbility;

public class KameWalkPointAbility extends ZoanAbility {

    public KameWalkPointAbility() {
        super(ResourceLocation.fromNamespaceAndPath("mineminenomi", "kame_kame_no_mi"));
    }

    @Override
    public ResourceLocation getMorphModelId() {
        return ResourceLocation.fromNamespaceAndPath("mineminenomi", "kame_guard");
    }

    @Override
    public double getScaleModifier() { return 0; }

    @Override
    public double getHealthModifier() { return 8; }

    @Override
    public double getDamageModifier() { return 2; }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.kamewalkpoint");
    }
}
