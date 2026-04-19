package xyz.pixelatedw.mineminenomi.abilities.ryupteranodon;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

public class TankyudonAbility extends Ability {

    public TankyudonAbility() {
        super(ResourceLocation.fromNamespaceAndPath("mineminenomi", "ryu_ryu_no_mi_model_3"));
    }

    @Override
    protected void startUsing(LivingEntity entity) {
        // TODO: implement
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.tankyudon");
    }
}
