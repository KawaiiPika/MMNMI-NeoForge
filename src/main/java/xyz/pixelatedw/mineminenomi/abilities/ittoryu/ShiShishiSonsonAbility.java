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
import xyz.pixelatedw.mineminenomi.data.entity.PlayerStats;
import xyz.pixelatedw.mineminenomi.init.ModSounds;

import java.util.List;

public class ShiShishiSonsonAbility extends Ability {

    private static final int CHARGE_TIME = 20;
    private static final float DAMAGE = 10.0F;
    private static final float RANGE = 10.0F;

    @Override
    protected void startUsing(LivingEntity entity) {
        // Charging phase logic (could be more complex with animations)
    }

    @Override
    public void tick(LivingEntity entity) {
        super.tick(entity);
        if (isUsing(entity)) {
            long duration = getDuration(entity);
            if (duration >= CHARGE_TIME) {
                performDash(entity);
                stopUsing(entity);
            } else {
                // Immobilize during charge
                entity.setDeltaMovement(0, entity.getDeltaMovement().y, 0);
            }
        }
    }

    private void performDash(LivingEntity entity) {
        BlockPos targetPos = WyHelper.rayTraceBlockSafe(entity, RANGE).getBlockPos();
        Vec3 startPos = entity.position();
        
        // Damage entities in line
        List<LivingEntity> targets = WyHelper.getNearbyEntities(entity.position(), entity.level(), RANGE, 2.0, RANGE, 
                e -> e != entity && e instanceof LivingEntity, LivingEntity.class);
        
        for (LivingEntity target : targets) {
            // Simplified line check: if the target is roughly between start and end
            target.hurt(entity.damageSources().mobAttack(entity), DAMAGE);
            if (!entity.level().isClientSide) {
                WyHelper.spawnParticles(ParticleTypes.SWEEP_ATTACK, (ServerLevel)entity.level(), target.getX(), target.getY() + target.getBbHeight()/2, target.getZ());
            }
        }

        // Teleport
        entity.teleportTo(targetPos.getX() + 0.5, targetPos.getY() + 1.0, targetPos.getZ() + 0.5);
        entity.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(), ModSounds.DASH_ABILITY_SWOOSH_SFX.get(), SoundSource.PLAYERS, 1.0F, 1.0F);
        
        // Cooldown
        PlayerStats stats = PlayerStats.get(entity);
        if (stats != null && getAbilityId() != null) {
            stats.setAbilityCooldown(getAbilityId().toString(), 140, entity.level().getGameTime());
        }
    }

    @Override
    public Component getDisplayName() {
        return Component.literal("Shi Shishi Sonson");
    }
}
