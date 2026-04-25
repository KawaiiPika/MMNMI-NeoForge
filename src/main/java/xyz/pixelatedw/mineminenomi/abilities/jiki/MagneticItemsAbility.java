package xyz.pixelatedw.mineminenomi.abilities.jiki;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

public class MagneticItemsAbility extends Ability {

    public MagneticItemsAbility() {
        super(ResourceLocation.fromNamespaceAndPath("mineminenomi", "jiki_jiki_no_mi"));
    }

    @Override
    public boolean isPassive() {
        return true;
    }

    @Override
    protected void startUsing(LivingEntity entity) {}

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.magnetic_items");
    }
}
