package xyz.pixelatedw.mineminenomi.abilities.santoryu;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.data.entity.PlayerStats;

import java.util.List;

public class OTatsumakiAbility extends Ability {

    private static final float DAMAGE = 30.0F;
    private static final float RANGE = 5.5F;
    private static final int DURATION = 60; // 3 seconds
    private static final int COOLDOWN = 240;

    @Override
    protected void startUsing(LivingEntity entity) {
        // Start spinning effect (could add sound/animation here)
    }

    @Override
    public void tick(LivingEntity entity) {
        super.tick(entity);
        if (isUsing(entity)) {
            long duration = getDuration(entity);
            
            if (duration >= DURATION) {
                stopUsing(entity);
                PlayerStats stats = PlayerStats.get(entity);
                if (stats != null && getAbilityId() != null) {
                    stats.setAbilityCooldown(getAbilityId().toString(), COOLDOWN, entity.level().getGameTime());
                }
                return;
            }

            // Damage every 10 ticks
            if (duration % 10 == 0) {
                List<LivingEntity> targets = WyHelper.getNearbyEntities(entity.position(), entity.level(), RANGE, 3.0, RANGE, 
                        e -> e != entity && e instanceof LivingEntity, LivingEntity.class);
                
                for (LivingEntity target : targets) {
                    target.hurt(entity.damageSources().mobAttack(entity), DAMAGE / 3.0F); // Distributed damage
                    if (!entity.level().isClientSide) {
                        WyHelper.spawnParticles(ParticleTypes.CLOUD, (ServerLevel)entity.level(), target.getX(), target.getY() + target.getBbHeight()/2, target.getZ());
                    }
                }
                
                if (!entity.level().isClientSide) {
                    WyHelper.spawnParticles(ParticleTypes.SWEEP_ATTACK, (ServerLevel)entity.level(), entity.getX(), entity.getY() + 1.0, entity.getZ());
                }
            }
        }
    }

    @Override
    public Component getDisplayName() {
        return Component.literal("O Tatsumaki");
    }
}
