package xyz.pixelatedw.mineminenomi.abilities.ittoryu;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.data.entity.PlayerStats;

import java.util.List;

public class HiryuKaenAbility extends Ability {

    private static final float DAMAGE = 20.0F;
    private static final float RANGE = 4.5F;
    private static final int COOLDOWN = 240;

    @Override
    protected void startUsing(LivingEntity entity) {
        // Jump high into the air
        Vec3 look = entity.getLookAngle();
        entity.setDeltaMovement(look.x * 2.0, 1.5, look.z * 2.0);
    }

    @Override
    public void tick(LivingEntity entity) {
        super.tick(entity);
        if (isUsing(entity)) {
            // Check for targets while in air/landing
            List<LivingEntity> targets = WyHelper.getNearbyEntities(entity.position(), entity.level(), RANGE, 2.0, RANGE, 
                    e -> e != entity && e instanceof LivingEntity, LivingEntity.class);
            
            boolean hitAnything = false;
            for (LivingEntity target : targets) {
                target.hurt(entity.damageSources().mobAttack(entity), DAMAGE);
                target.setRemainingFireTicks(80); // 4 seconds of fire
                if (!entity.level().isClientSide) {
                    WyHelper.spawnParticles(ParticleTypes.FLAME, (ServerLevel)entity.level(), target.getX(), target.getY() + target.getBbHeight()/2, target.getZ());
                }
                hitAnything = true;
            }

            if (hitAnything || entity.onGround()) {
                stopUsing(entity);
                PlayerStats stats = PlayerStats.get(entity);
                if (stats != null && getAbilityId() != null) {
                    stats.setAbilityCooldown(getAbilityId().toString(), COOLDOWN, entity.level().getGameTime());
                }
            }
        }
    }

    @Override
    public Component getDisplayName() {
        return Component.literal("Hiryu: Kaen");
    }
}
