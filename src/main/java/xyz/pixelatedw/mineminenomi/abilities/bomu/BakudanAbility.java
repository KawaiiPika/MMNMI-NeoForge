package xyz.pixelatedw.mineminenomi.abilities.bomu;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.resources.ResourceLocation;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

public class BakudanAbility extends Ability {

    public BakudanAbility() {
        super(ResourceLocation.fromNamespaceAndPath("mineminenomi", "bomu_bomu_no_mi"));
    }

    @Override
    protected void startUsing(LivingEntity entity) {
        // Implementation for bomb throwing will be added later
        entity.level().explode(entity, entity.getX(), entity.getY(), entity.getZ(), 2.0F, false, net.minecraft.world.level.Level.ExplosionInteraction.NONE);
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.bakudan");
    }
}
