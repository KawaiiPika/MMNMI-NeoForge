package xyz.pixelatedw.mineminenomi.abilities.magu;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

public class MagmaCoatingAbility extends Ability {

    private static final int MAX_DURATION = 120;

    public MagmaCoatingAbility() {
        super(ResourceLocation.fromNamespaceAndPath("mineminenomi", "magu_magu_no_mi"));
    }

    @Override
    protected void startUsing(LivingEntity entity) {
        // We will just do a simple duration for now, no overlay since skin coating is complicated and requires packet sync
    }

    @Override
    protected void onTick(LivingEntity entity, long duration) {
        if (duration >= MAX_DURATION) {
            this.stop(entity);
            this.startCooldown(entity, 300);
        }
        if (entity.level().isClientSide) {
             entity.level().addParticle(net.minecraft.core.particles.ParticleTypes.LAVA,
                entity.getX() + (entity.getRandom().nextDouble() - 0.5), 
                entity.getY() + entity.getRandom().nextDouble() * 2, 
                entity.getZ() + (entity.getRandom().nextDouble() - 0.5), 
                0, 0, 0);
        }
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.magma_coating");
    }
}
