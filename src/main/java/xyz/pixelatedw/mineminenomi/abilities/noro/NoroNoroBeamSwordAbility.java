package xyz.pixelatedw.mineminenomi.abilities.noro;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

public class NoroNoroBeamSwordAbility extends Ability {
    public NoroNoroBeamSwordAbility() {
        super(ResourceLocation.fromNamespaceAndPath("mineminenomi", "noro_noro_no_mi"));
    }

    @Override
    protected void startUsing(LivingEntity entity) {
        // Stub implementation
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.noro_noro_beam_sword");
    }
}
