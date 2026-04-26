package xyz.pixelatedw.mineminenomi.abilities.mero;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

public class PerfumeFemurAbility extends Ability {
    public PerfumeFemurAbility() {
        super(ResourceLocation.fromNamespaceAndPath("mineminenomi", "mero_mero_no_mi"));
    }

    @Override
    protected void startUsing(LivingEntity entity) {
        // Stub implementation
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.perfume_femur");
    }
}
