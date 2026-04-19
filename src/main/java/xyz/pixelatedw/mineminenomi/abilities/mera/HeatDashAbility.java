package xyz.pixelatedw.mineminenomi.abilities.mera;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;

public class HeatDashAbility extends Ability {

    public HeatDashAbility() {
        super(ResourceLocation.fromNamespaceAndPath("mineminenomi", "mera_mera_no_mi"));
    }

    @Override
    protected void startUsing(LivingEntity entity) {
        if (!entity.level().isClientSide) {
             entity.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(), 
                xyz.pixelatedw.mineminenomi.init.ModSounds.MERA_SFX.get(), 
                net.minecraft.sounds.SoundSource.PLAYERS, 1.5F, 1.0F);
        }
    }

    @Override
    protected void onTick(LivingEntity entity, long duration) {
        if (!entity.level().isClientSide) {
            Vec3 look = entity.getLookAngle();
            double speed = 2.5;
            AbilityHelper.setDeltaMovement(entity, look.x * speed, look.y * 0.5 + 0.1, look.z * speed);
            entity.hurtMarked = true;

            for (LivingEntity target : entity.level().getEntitiesOfClass(LivingEntity.class, entity.getBoundingBox().inflate(1.5))) {
                if (target != entity) {
                    target.setRemainingFireTicks(60);
                    target.hurt(entity.damageSources().onFire(), 2.0F);
                }
            }
        } else {
             for (int i = 0; i < 5; i++) {
                entity.level().addParticle(net.minecraft.core.particles.ParticleTypes.FLAME, 
                    entity.getX() + (entity.getRandom().nextDouble() - 0.5), 
                    entity.getY() + 0.5, 
                    entity.getZ() + (entity.getRandom().nextDouble() - 0.5), 
                    0, 0, 0);
            }
        }

        if (duration >= 15 || (duration > 5 && entity.onGround())) {
            this.stop(entity);
            this.startCooldown(entity, 200);
        }
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.heat_dash");
    }
}
