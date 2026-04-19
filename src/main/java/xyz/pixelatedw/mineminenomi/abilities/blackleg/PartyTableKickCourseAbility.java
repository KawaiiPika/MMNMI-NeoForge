package xyz.pixelatedw.mineminenomi.abilities.blackleg;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

public class PartyTableKickCourseAbility extends Ability {

    private static final long DURATION = 20; // 1 second

    public PartyTableKickCourseAbility() {}

    @Override
    protected void startUsing(LivingEntity entity) {
        if (!entity.level().isClientSide) {
            entity.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(), 
                net.minecraft.sounds.SoundEvents.PLAYER_ATTACK_SWEEP, 
                net.minecraft.sounds.SoundSource.PLAYERS, 1.5f, 0.8f);
        }
    }

    @Override
    public void onTick(LivingEntity entity, long duration) {
        if (duration > DURATION) {
            this.stop(entity);
            this.startCooldown(entity, 160);
            return;
        }

        boolean diableJambeActive = false;
        xyz.pixelatedw.mineminenomi.data.entity.PlayerStats stats = xyz.pixelatedw.mineminenomi.data.entity.PlayerStats.get(entity);
        if (stats != null) {
            diableJambeActive = stats.isAbilityActive("mineminenomi:diable_jambe");
        }

        AABB area = entity.getBoundingBox().inflate(4.0);
        for (LivingEntity target : entity.level().getEntitiesOfClass(LivingEntity.class, area)) {
            if (target != entity) {
                float damage = diableJambeActive ? 10.0f : 6.0f;
                target.hurt(entity.damageSources().mobAttack(entity), damage);
                Vec3 dir = target.position().subtract(entity.position()).normalize().scale(0.5);
                target.setDeltaMovement(new Vec3(dir.x, 0.4, dir.z));
                target.hurtMarked = true;
                
                if (diableJambeActive) {
                    target.setRemainingFireTicks(40);
                }
            }
        }
        
        if (entity.level() instanceof net.minecraft.server.level.ServerLevel serverLevel) {
            serverLevel.sendParticles(net.minecraft.core.particles.ParticleTypes.SWEEP_ATTACK, 
                entity.getX(), entity.getY() + 0.1, entity.getZ(), 
                3, 1.0, 0, 1.0, 0);
        }
    }

    @Override
    public Component getDisplayName() {
        return Component.literal("Party-Table Kick Course");
    }
}
