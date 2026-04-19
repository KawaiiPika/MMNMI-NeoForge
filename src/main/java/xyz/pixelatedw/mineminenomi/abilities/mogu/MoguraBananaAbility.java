package xyz.pixelatedw.mineminenomi.abilities.mogu;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

public class MoguraBananaAbility extends Ability {

    public MoguraBananaAbility() {
        super(ResourceLocation.fromNamespaceAndPath("mineminenomi", "mogu_mogu_no_mi"));
    }

    @Override
    protected void startUsing(LivingEntity entity) {
        // TODO: implement
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.mogurabanana");
    }
}
