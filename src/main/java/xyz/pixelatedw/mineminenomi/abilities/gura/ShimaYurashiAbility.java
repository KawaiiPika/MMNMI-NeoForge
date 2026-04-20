package xyz.pixelatedw.mineminenomi.abilities.gura;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

/** Shima Yurashi — Island Shaking. */
public class ShimaYurashiAbility extends Ability {
    private static final ResourceLocation FRUIT = ResourceLocation.fromNamespaceAndPath("mineminenomi", "gura_gura_no_mi");
    
    public ShimaYurashiAbility() { 
        super(FRUIT); 
    }

    private static final int CHARGE_TICKS = 80; // 4 seconds

    @Override
    protected void startUsing(LivingEntity entity) {
        if (!entity.level().isClientSide) {
            entity.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(), 
                xyz.pixelatedw.mineminenomi.init.ModSounds.GURA_SFX.get(), 
                net.minecraft.sounds.SoundSource.PLAYERS, 2.0F, 0.5F);
        }
    }

    @Override
    protected void onTick(LivingEntity entity, long duration) {
        if (duration < CHARGE_TICKS) {
            if (entity.level().isClientSide && duration % 5 == 0) {
                 for(int i = 0; i < 10; i++) {
                     entity.level().addParticle(ParticleTypes.EXPLOSION, 
                        entity.getX() + (entity.getRandom().nextDouble() - 0.5) * 10, 
                        entity.getY(), 
                        entity.getZ() + (entity.getRandom().nextDouble() - 0.5) * 10, 
                        0, 0.1, 0);
                 }
            }
        } else if (duration == CHARGE_TICKS) {
            if (!entity.level().isClientSide) {
                for (var target : entity.level().getEntities(entity, entity.getBoundingBox().inflate(35.0))) {
                    if (target instanceof LivingEntity living) {
                        living.hurt(entity.damageSources().explosion(entity, entity), 40.0F);
                        living.setDeltaMovement(0, 3.5, 0);
                        living.hurtMarked = true;
                    }
                }
                
                // Massive AOE particles
                for (int i = 0; i < 100; i++) {
                    double rx = (entity.getRandom().nextDouble() - 0.5) * 70;
                    double rz = (entity.getRandom().nextDouble() - 0.5) * 70;
                    entity.level().addParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE, 
                        entity.getX() + rx, entity.getY(), entity.getZ() + rz, 
                        0, 0.8, 0);
                }
                
                this.startCooldown(entity, 1200);
            }
            this.stop(entity);
        }
    }

    @Override
    public Component getDisplayName() { 
        return Component.translatable("ability.mineminenomi.shimayurashi"); 
    }
}
