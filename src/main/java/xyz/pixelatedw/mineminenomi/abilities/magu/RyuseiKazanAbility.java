package xyz.pixelatedw.mineminenomi.abilities.magu;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.data.entity.PlayerStats;
import xyz.pixelatedw.mineminenomi.entities.projectiles.RyuseiKazanEntity;

public class RyuseiKazanAbility extends Ability {

    private static final int MAX_TICKS = 80; // 4 seconds

    public RyuseiKazanAbility() {
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
        if (duration % 5 == 0) {
            if (!entity.level().isClientSide) {
                for (int i = 0; i < 3; i++) {
                    RyuseiKazanEntity meteor = new RyuseiKazanEntity(entity.level(), entity);
                    float spread = 30.0F;
                    float xRot = -90.0F + (entity.getRandom().nextFloat() - 0.5F) * spread; // Shoot upwards
                    float yRot = entity.getYRot() + (entity.getRandom().nextFloat() - 0.5F) * spread;
                    meteor.shootFromRotation(entity, xRot, yRot, 0.0F, 1.2F, 0.5F);
                    entity.level().addFreshEntity(meteor);
                }
                
                entity.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(), 
                    net.minecraft.sounds.SoundEvents.GENERIC_EXPLODE.value(), 
                    net.minecraft.sounds.SoundSource.PLAYERS, 1.0F, 0.8F);
            }
        }

        if (duration >= MAX_TICKS) {
            stopUsing(entity);
        }
    }

    private ResourceLocation getId() {
        return ResourceLocation.fromNamespaceAndPath("mineminenomi", "ryusei_kazan");
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.ryusei_kazan");
    }
}
