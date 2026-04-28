package xyz.pixelatedw.mineminenomi.abilities.magu;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.data.entity.PlayerStats;

public class MagmaCoatingAbility extends Ability {

    public MagmaCoatingAbility() {
        super(ResourceLocation.fromNamespaceAndPath("mineminenomi", "magu_magu_no_mi"));
    }

    @Override
    public boolean isUsing(LivingEntity entity) {
        PlayerStats stats = PlayerStats.get(entity);
        return stats != null && stats.isAbilityActive(this.getId().toString());
    }

    @Override
    protected void startUsing(LivingEntity entity) {
        PlayerStats stats = PlayerStats.get(entity);
        if (stats != null) {
            stats.setAbilityActive(this.getId().toString(), true);
            stats.sync(entity);
        }
    }

    @Override
    protected void stopUsing(LivingEntity entity) {
        PlayerStats stats = PlayerStats.get(entity);
        if (stats != null) {
            stats.setAbilityActive(this.getId().toString(), false);
            stats.sync(entity);
        }
    }

    @Override
    protected void onTick(LivingEntity entity, long duration) {
        if (entity.level().isClientSide && duration % 5 == 0) {
            entity.level().addParticle(net.minecraft.core.particles.ParticleTypes.LAVA,
                entity.getX() + (entity.getRandom().nextDouble() - 0.5), 
                entity.getY() + entity.getRandom().nextDouble() * 2, 
                entity.getZ() + (entity.getRandom().nextDouble() - 0.5), 
                0, 0, 0);
        }

        // Drain stamina if active
        PlayerStats stats = PlayerStats.get(entity);
        if (stats != null) {
            stats.setStamina(stats.getStamina() - 0.1);
            if (stats.getStamina() <= 0) {
                stopUsing(entity);
            }
        }
    }

    private ResourceLocation getId() {
        return ResourceLocation.fromNamespaceAndPath("mineminenomi", "magma_coating");
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.magma_coating");
    }
}
