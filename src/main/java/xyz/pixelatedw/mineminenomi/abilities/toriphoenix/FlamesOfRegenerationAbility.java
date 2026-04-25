package xyz.pixelatedw.mineminenomi.abilities.toriphoenix;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

public class FlamesOfRegenerationAbility extends Ability {

    public FlamesOfRegenerationAbility() {
        super(ResourceLocation.fromNamespaceAndPath("mineminenomi", "tori_tori_no_mi_model_1"));
    }

    @Override
    protected void startUsing(LivingEntity entity) {
        if (!entity.level().isClientSide) {
            entity.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(), 
                net.minecraft.sounds.SoundEvents.FIRECHARGE_USE, 
                net.minecraft.sounds.SoundSource.PLAYERS, 1.0F, 1.5F);
        }
    }

    @Override
    protected void onTick(LivingEntity entity, long duration) {
        if (!entity.level().isClientSide) {
            if (duration % 20 == 0) { // Every 1 second
                entity.heal(1.0F);
            }
        } else if (duration % 5 == 0) {
            entity.level().addParticle(net.minecraft.core.particles.ParticleTypes.SOUL_FIRE_FLAME,
                entity.getX() + (entity.getRandom().nextDouble() - 0.5) * 1.2,
                entity.getY() + (entity.getRandom().nextDouble() * 2),
                entity.getZ() + (entity.getRandom().nextDouble() - 0.5) * 1.2,
                0, 0.05, 0);
        }
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.flamesofregeneration");
    }
}
