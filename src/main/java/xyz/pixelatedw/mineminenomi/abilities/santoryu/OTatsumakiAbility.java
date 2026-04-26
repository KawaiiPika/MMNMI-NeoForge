package xyz.pixelatedw.mineminenomi.abilities.santoryu;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityUseConditions;
import xyz.pixelatedw.mineminenomi.api.util.Result;
import xyz.pixelatedw.mineminenomi.data.entity.PlayerStats;
import xyz.pixelatedw.mineminenomi.init.ModSounds;

import java.util.List;

public class OTatsumakiAbility extends Ability {

    private static final float DAMAGE = 30.0F;
    private static final float RANGE = 5.5F;
    private static final int DURATION = 60; // 3 seconds
    private static final int COOLDOWN = 240;

    @Override
    public Result canUse(LivingEntity entity) {
        Result result = super.canUse(entity);
        if (result.isFail()) return result;
        return AbilityUseConditions.requiresSword(entity);
    }

    @Override
    protected void startUsing(LivingEntity entity) {
        if (!entity.level().isClientSide) {
            entity.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(), ModSounds.SPIN.get(), SoundSource.PLAYERS, 2.0F, 0.75F);
        }
    }

    @Override
    public void tick(LivingEntity entity) {
        super.tick(entity);
        if (isUsing(entity)) {
            long duration = getDuration(entity);
            
            if (duration >= DURATION) {
                stopUsing(entity);
                startCooldown(entity, (long)COOLDOWN);
                return;
            }

            // Damage every 15 ticks
            if (duration % 15 == 0) {
                List<LivingEntity> targets = xyz.pixelatedw.mineminenomi.api.helpers.TargetHelper.getEntitiesInArea(entity.level(), entity, RANGE, LivingEntity.class);
                
                for (LivingEntity target : targets) {
                    if (!entity.level().isClientSide) {
                        target.hurt(entity.damageSources().mobAttack(entity), DAMAGE);
                        ((ServerLevel)entity.level()).sendParticles(ParticleTypes.SWEEP_ATTACK,  target.getX(), target.getY() + target.getBbHeight()/2, target.getZ(), 1, 0, 0, 0, 0);
                    }
                }
            }

            if (!entity.level().isClientSide) {
                if (duration % 5 == 0) {
                    entity.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(), ModSounds.SPIN.get(), SoundSource.PLAYERS, 2.0F, 0.75F);
                }
                ((ServerLevel)entity.level()).sendParticles(ParticleTypes.CLOUD,  entity.getX(), entity.getY() + 1.0, entity.getZ(), 1, 0, 0, 0, 0);
            }
        }
    }

    @Override
    public Component getDisplayName() {
        return Component.literal("O Tatsumaki");
    }
}
