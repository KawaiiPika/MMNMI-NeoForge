package xyz.pixelatedw.mineminenomi.abilities.saraaxolotl;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

public class AxolotlHealAbility extends Ability {

    public AxolotlHealAbility() {
        super(ResourceLocation.fromNamespaceAndPath("mineminenomi", "sara_sara_no_mi"));
    }

    @Override
    protected void startUsing(LivingEntity entity) {
        // TODO: implement
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.axolotlheal");
    }
}
