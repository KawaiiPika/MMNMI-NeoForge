package xyz.pixelatedw.mineminenomi.api.abilities.rokushiki;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

public class RokuoganAbility extends Ability {

    private static final int CHARGE_TICKS = 30; // 1.5 seconds

    @Override
    protected void startUsing(LivingEntity entity) {
        if (!entity.level().isClientSide) {
             entity.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(), 
                xyz.pixelatedw.mineminenomi.init.ModSounds.ROKUOGAN_SFX.get(), 
                net.minecraft.sounds.SoundSource.PLAYERS, 1.5F, 1.0F);
        }
    }

    @Override
    protected void onTick(LivingEntity entity, long duration) {
        if (duration < CHARGE_TICKS) {
            if (entity.level().isClientSide && duration % 2 == 0) {
                 entity.level().addParticle(net.minecraft.core.particles.ParticleTypes.CRIT, 
                    entity.getX() + (entity.getRandom().nextDouble() - 0.5), 
                    entity.getY() + 1, 
                    entity.getZ() + (entity.getRandom().nextDouble() - 0.5), 
                    0, 0, 0);
            }
        } else if (duration == CHARGE_TICKS) {
            if (!entity.level().isClientSide) {
                Vec3 look = entity.getLookAngle();
                for (Entity target : entity.level().getEntitiesOfClass(LivingEntity.class, entity.getBoundingBox().inflate(3.5).move(look.scale(2.5)), e -> e != entity)) {
                    if (target instanceof LivingEntity livingTarget) {
                        livingTarget.hurt(entity.damageSources().mobAttack(entity), 25.0F);
                        livingTarget.setDeltaMovement(look.scale(3.5).add(0, 0.4, 0));
                        livingTarget.hurtMarked = true;
                    }
                }
                
                this.startCooldown(entity, 400);
            } else {
                 entity.level().addParticle(net.minecraft.core.particles.ParticleTypes.EXPLOSION_EMITTER, 
                    entity.getX() + entity.getLookAngle().x * 2, 
                    entity.getY() + 1, 
                    entity.getZ() + entity.getLookAngle().z * 2, 
                    0, 0, 0);
            }
            this.stop(entity);
        }
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.rokuogan");
    }
}
