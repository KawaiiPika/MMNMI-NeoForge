package xyz.pixelatedw.mineminenomi.abilities.bomu;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

public class BreezeBreathBombAbility extends Ability {
    public BreezeBreathBombAbility() {
        super(ResourceLocation.fromNamespaceAndPath("mineminenomi", "bomu_bomu_no_mi"));
    }

    @Override
    protected void startUsing(LivingEntity entity) {
        // Stub implementation
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.breeze_breath_bomb");
    }
}
