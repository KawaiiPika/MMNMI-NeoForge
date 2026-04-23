package xyz.pixelatedw.mineminenomi.abilities.bomu;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

public class KickBombAbility extends Ability {

    public KickBombAbility() {
        super(ResourceLocation.fromNamespaceAndPath("mineminenomi", "bomu_bomu_no_mi"));
    }

    @Override
    protected void startUsing(LivingEntity entity) {
        if (!entity.level().isClientSide) {
            entity.level().explode(entity, entity.getX(), entity.getY(), entity.getZ(), 7.0F, net.minecraft.world.level.Level.ExplosionInteraction.MOB);
            this.startCooldown(entity, 400);
        }
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.kick_bomb");
    }
}
