package xyz.pixelatedw.mineminenomi.abilities.sai;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

public class HornDashAbility extends Ability {

    public HornDashAbility() {
        super(ResourceLocation.fromNamespaceAndPath("mineminenomi", "sai_sai_no_mi"));
    }

    @Override
    protected void startUsing(LivingEntity entity) {
        // TODO: implement
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.horndash");
    }
}
