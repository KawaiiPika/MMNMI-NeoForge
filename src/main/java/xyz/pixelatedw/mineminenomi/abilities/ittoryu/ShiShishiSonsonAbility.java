package xyz.pixelatedw.mineminenomi.abilities.ittoryu;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityUseConditions;
import xyz.pixelatedw.mineminenomi.api.util.Result;
import xyz.pixelatedw.mineminenomi.data.entity.PlayerStats;
import xyz.pixelatedw.mineminenomi.init.ModSounds;

import java.util.List;

public class ShiShishiSonsonAbility extends Ability {

    private static final float COOLDOWN = 180.0F;
    private static final int CHARGE_TIME = 20;
    private static final float DAMAGE = 30.0F;
    private static final float MAX_TELEPORT_DISTANCE = 30.0F;

    @Override
    public Result canUse(LivingEntity entity) {
        Result result = super.canUse(entity);
        if (result.isFail()) return result;
        return AbilityUseConditions.requiresSword(entity);
    }

    @Override
    protected void startUsing(LivingEntity entity) {
        // Start charge
    }

    @Override
    public void tick(LivingEntity entity) {
        super.tick(entity);
        if (isUsing(entity)) {
            long duration = getDuration(entity);
            if (duration >= CHARGE_TIME) {
                if (!entity.level().isClientSide) {
                    performDash(entity);
                }
                stopUsing(entity);
            } else {
                // Immobilize during charge
                entity.setDeltaMovement(0, entity.getDeltaMovement().y, 0);
            }
        }
    }

    private void performDash(LivingEntity entity) {
        net.minecraft.world.phys.HitResult hit = WyHelper.rayTraceBlockSafe(entity, MAX_TELEPORT_DISTANCE);
        BlockPos targetPos = null;
        if (hit instanceof net.minecraft.world.phys.BlockHitResult blockHit) {
            targetPos = blockHit.getBlockPos();
        } else {
            Vec3 look = entity.getLookAngle();
            Vec3 target = entity.position().add(look.scale(MAX_TELEPORT_DISTANCE));
            targetPos = BlockPos.containing(target);
        }
        Vec3 startPos = entity.position();

        float actualTeleportDistance = MAX_TELEPORT_DISTANCE;

        List<LivingEntity> targets = xyz.pixelatedw.mineminenomi.api.helpers.TargetHelper.getEntitiesInLine(entity, actualTeleportDistance, 2.0, LivingEntity.class);

        for (LivingEntity target : targets) {
            if (!entity.level().isClientSide) {
                target.hurt(entity.damageSources().mobAttack(entity), DAMAGE);
                ((ServerLevel)entity.level()).sendParticles(ParticleTypes.SWEEP_ATTACK,  target.getX(), target.getY() + target.getBbHeight()/2, target.getZ(), 1, 0, 0, 0, 0);
            }
        }

        entity.setDeltaMovement(0, 0, 0);
        entity.teleportTo(targetPos.getX() + 0.5, targetPos.getY() + 1.0, targetPos.getZ() + 0.5);
        entity.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(), ModSounds.DASH_ABILITY_SWOOSH_SFX.get(), SoundSource.PLAYERS, 2.0F, 1.0F);

        startCooldown(entity, (long)COOLDOWN);
    }

    @Override
    public Component getDisplayName() {
        return Component.literal("Shi Shishi Sonson");
    }
}
