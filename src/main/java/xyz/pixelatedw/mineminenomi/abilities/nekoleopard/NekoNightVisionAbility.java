package xyz.pixelatedw.mineminenomi.abilities.nekoleopard;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

public class NekoNightVisionAbility extends Ability {

    public NekoNightVisionAbility() {
        super(ResourceLocation.fromNamespaceAndPath("mineminenomi", "neko_neko_no_mi_model_1"));
    }

    @Override
    protected void startUsing(LivingEntity entity) {
        // TODO: implement
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.nekonightvision");
    }
}
