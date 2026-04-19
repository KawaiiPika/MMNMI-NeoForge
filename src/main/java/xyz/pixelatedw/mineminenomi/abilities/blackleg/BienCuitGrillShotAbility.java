package xyz.pixelatedw.mineminenomi.abilities.blackleg;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.util.Result;

public class BienCuitGrillShotAbility extends Ability {

    public BienCuitGrillShotAbility() {}

    @Override
    public Result canUse(LivingEntity entity) {
        xyz.pixelatedw.mineminenomi.data.entity.PlayerStats stats = xyz.pixelatedw.mineminenomi.data.entity.PlayerStats.get(entity);
        if (stats == null || !stats.isAbilityActive("mineminenomi:diable_jambe")) {
            return Result.fail(Component.literal("You must activate Diable Jambe first!"));
        }
        return super.canUse(entity);
    }

    @Override
    protected void startUsing(LivingEntity entity) {
        Vec3 look = entity.getLookAngle();
        entity.setDeltaMovement(new Vec3(look.x * 2.0, 0.2, look.z * 2.0));
        entity.hurtMarked = true;
        
        if (!entity.level().isClientSide) {
            entity.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(), 
                net.minecraft.sounds.SoundEvents.FIRECHARGE_USE, 
                net.minecraft.sounds.SoundSource.PLAYERS, 1.0f, 1.0f);
        }
    }

    @Override
    public void onTick(LivingEntity entity, long duration) {
        if (duration > 10) {
            this.stop(entity);
            this.startCooldown(entity, 400);
            return;
        }

        AABB area = entity.getBoundingBox().inflate(1.5);
        for (LivingEntity target : entity.level().getEntitiesOfClass(LivingEntity.class, area)) {
            if (target != entity) {
                target.hurt(entity.damageSources().mobAttack(entity), 30.0f);
                target.setRemainingFireTicks(100); // 5 seconds
                Vec3 push = target.position().subtract(entity.position()).normalize().scale(1.0);
                target.setDeltaMovement(new Vec3(push.x, 0.2, push.z));
                target.hurtMarked = true;
            }
        }
        
        if (entity.level() instanceof net.minecraft.server.level.ServerLevel serverLevel) {
            serverLevel.sendParticles(net.minecraft.core.particles.ParticleTypes.FLAME, 
                entity.getX(), entity.getY() + 1.0, entity.getZ(), 
                5, 0.5, 0.5, 0.5, 0.1);
        }
    }

    @Override
    public Component getDisplayName() {
        return Component.literal("Bien Cuit: Grill Shot");
    }
}
