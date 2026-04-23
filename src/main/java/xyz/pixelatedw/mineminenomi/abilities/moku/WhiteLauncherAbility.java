package xyz.pixelatedw.mineminenomi.abilities.moku;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

public class WhiteLauncherAbility extends Ability {

    private static final int MAX_DURATION = 15;
    private static final float RANGE = 1.6F;
    private static final float DAMAGE = 15.0F;

    public WhiteLauncherAbility() {
        super(ResourceLocation.fromNamespaceAndPath("mineminenomi", "moku_moku_no_mi"));
    }

    @Override
    protected void startUsing(LivingEntity entity) {
        if (!entity.level().isClientSide) {
            entity.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(),
                xyz.pixelatedw.mineminenomi.init.ModSounds.DASH_ABILITY_SWOOSH_SFX.get(),
                net.minecraft.sounds.SoundSource.PLAYERS, 2.0F, 1.0F);
        }
    }

    @Override
    protected void onTick(LivingEntity entity, long duration) {
        if (duration > MAX_DURATION && entity.onGround()) {
            this.stop(entity);
            this.startCooldown(entity, 200);
            return;
        }

        Vec3 look = entity.getLookAngle();
        entity.setDeltaMovement(look.x * 1.5, look.y * 1.5, look.z * 1.5);
        entity.hurtMarked = true;

        if (!entity.level().isClientSide) {
            // Note: In 1.21.1 port without HitTrackerComponent, abilities applying constant damage in an AoE Dash
            // must use setInvulnerableTicks on the target if they don't want to hit repeatedly, or just rely
            // on standard hurt resistance which handles the invincibility frames for us. We'll rely on the
            // standard i-frames provided by LivingEntity.hurt() which naturally prevents multi-hits per tick.
            entity.level().getEntitiesOfClass(LivingEntity.class, entity.getBoundingBox().inflate(RANGE))
                .stream()
                .filter(target -> target != entity)
                .filter(target -> target.distanceToSqr(entity) <= RANGE * RANGE)
                .forEach(target -> {
                    target.hurt(entity.damageSources().mobAttack(entity), DAMAGE);
                });
        } else {
            if (duration % 2 == 0) {
                 for(int i = 0; i < 5; i++) {
                     entity.level().addParticle(net.minecraft.core.particles.ParticleTypes.CAMPFIRE_COSY_SMOKE,
                        entity.getX() + (entity.getRandom().nextDouble() - 0.5),
                        entity.getY() + entity.getRandom().nextDouble() * 2,
                        entity.getZ() + (entity.getRandom().nextDouble() - 0.5),
                        0, 0, 0);
                 }
            }
        }
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.white_launcher");
    }
}
