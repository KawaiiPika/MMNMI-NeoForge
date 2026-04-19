package xyz.pixelatedw.mineminenomi.abilities.ushibison;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

public class FiddleBanffAbility extends Ability {

    public FiddleBanffAbility() {
        super(ResourceLocation.fromNamespaceAndPath("mineminenomi", "ushi_ushi_no_mi_model_1"));
    }

    @Override
    protected void startUsing(LivingEntity entity) {
        // TODO: implement
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.fiddlebanff");
    }
}
