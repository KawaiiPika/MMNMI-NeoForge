package xyz.pixelatedw.mineminenomi.abilities.wara;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

public class WarabideSwordAbility extends Ability {
    public WarabideSwordAbility() {
        super(ResourceLocation.fromNamespaceAndPath("mineminenomi", "wara_wara_no_mi"));
    }

    @Override
    protected void startUsing(LivingEntity entity) {
        // Stub implementation
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.warabide_sword");
    }
}
