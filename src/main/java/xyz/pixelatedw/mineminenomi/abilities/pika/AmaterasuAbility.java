package xyz.pixelatedw.mineminenomi.abilities.pika;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.data.entity.PlayerStats;
import xyz.pixelatedw.mineminenomi.entities.projectiles.AmaterasuEntity;

public class AmaterasuAbility extends Ability {

    private static final int CHARGE_TICKS = 60; // 3 seconds

    public AmaterasuAbility() {
        super(ResourceLocation.fromNamespaceAndPath("mineminenomi", "pika_pika_no_mi"));
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
            entity.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(), 
                net.minecraft.sounds.SoundEvents.BEACON_ACTIVATE, 
                net.minecraft.sounds.SoundSource.PLAYERS, 2.0F, 1.0F);
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
        if (duration < CHARGE_TICKS) {
            if (entity.level().isClientSide) {
                for (int i = 0; i < 3; i++) {
                    entity.level().addParticle(net.minecraft.core.particles.ParticleTypes.END_ROD, 
                        entity.getX() + (entity.getRandom().nextDouble() - 0.5), 
                        entity.getEyeY() + (entity.getRandom().nextDouble() - 0.5), 
                        entity.getZ() + (entity.getRandom().nextDouble() - 0.5), 
                        0, 0, 0);
                }
            }
        } else if (duration == CHARGE_TICKS) {
            if (!entity.level().isClientSide) {
                Vec3 look = entity.getLookAngle();
                AmaterasuEntity amaterasu = new AmaterasuEntity(entity.level(), entity, look.scale(0.1));
                amaterasu.setPos(entity.getX(), entity.getEyeY(), entity.getZ());
                entity.level().addFreshEntity(amaterasu);
                
                entity.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(), 
                    net.minecraft.sounds.SoundEvents.BEACON_DEACTIVATE, 
                    net.minecraft.sounds.SoundSource.PLAYERS, 2.0F, 1.5F);
            }
            stopUsing(entity);
            this.startCooldown(entity, 300);
        }
    }

    private ResourceLocation getId() {
        return ResourceLocation.fromNamespaceAndPath("mineminenomi", "amaterasu");
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.amaterasu");
    }
}
