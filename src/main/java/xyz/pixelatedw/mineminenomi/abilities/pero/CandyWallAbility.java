package xyz.pixelatedw.mineminenomi.abilities.pero;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

public class CandyWallAbility extends Ability {
    public CandyWallAbility() {
        super(ResourceLocation.fromNamespaceAndPath("mineminenomi", "pero_pero_no_mi"));
    }

    @Override
    protected void startUsing(LivingEntity entity) {
        // Stub implementation
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.candy_wall");
    }
}
