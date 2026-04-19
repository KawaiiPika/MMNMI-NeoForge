package xyz.pixelatedw.mineminenomi.abilities.ushigiraffe;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

public class BiganAbility extends Ability {

    public BiganAbility() {
        super(ResourceLocation.fromNamespaceAndPath("mineminenomi", "ushi_ushi_no_mi_model_2"));
    }

    @Override
    protected void startUsing(LivingEntity entity) {
        // TODO: implement
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.bigan");
    }
}
