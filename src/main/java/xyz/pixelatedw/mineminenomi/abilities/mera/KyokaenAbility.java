package xyz.pixelatedw.mineminenomi.abilities.mera;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

public class KyokaenAbility extends Ability {

    public KyokaenAbility() {
        super(ResourceLocation.fromNamespaceAndPath("mineminenomi", "mera_mera_no_mi"));
    }

    @Override
    protected void startUsing(LivingEntity entity) {
        if (!entity.level().isClientSide) {
             entity.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(), 
                net.minecraft.sounds.SoundEvents.FIRE_AMBIENT, 
                net.minecraft.sounds.SoundSource.PLAYERS, 2.0F, 1.0F);
        }
    }

    @Override
    protected void onTick(LivingEntity entity, long duration) {
        if (!entity.level().isClientSide) {
            Vec3 look = entity.getLookAngle();
            AABB aabb = entity.getBoundingBox().inflate(3.0);
            
            for (LivingEntity target : entity.level().getEntitiesOfClass(LivingEntity.class, aabb)) {
                if (target != entity) {
                    target.hurt(entity.damageSources().onFire(), 3.0F);
                    target.setRemainingFireTicks(60);
                    target.setDeltaMovement(target.getDeltaMovement().add(target.position().subtract(entity.position()).normalize().scale(0.8)));
                    target.hurtMarked = true;
                }
            }
        } else {
             for (int i = 0; i < 15; i++) {
                 double angle = entity.getRandom().nextDouble() * Math.PI * 2;
                 double r = 2.5;
                 entity.level().addParticle(net.minecraft.core.particles.ParticleTypes.FLAME, 
                    entity.getX() + Math.cos(angle) * r, 
                    entity.getY() + entity.getRandom().nextDouble() * 2, 
                    entity.getZ() + Math.sin(angle) * r, 
                    0, 0.1, 0);
             }
        }

        if (duration >= 100) {
            this.stop(entity);
            this.startCooldown(entity, 140);
        }
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.kyokaen");
    }
}
