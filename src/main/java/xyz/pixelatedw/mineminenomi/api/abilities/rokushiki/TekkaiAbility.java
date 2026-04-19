package xyz.pixelatedw.mineminenomi.api.abilities.rokushiki;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

public class TekkaiAbility extends Ability {
    private static final int DURATION = 100; // 5 seconds

    @Override
    protected void startUsing(LivingEntity entity) {
        if (!entity.level().isClientSide) {
             entity.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(), 
                xyz.pixelatedw.mineminenomi.init.ModSounds.TEKKAI_SFX.get(), 
                net.minecraft.sounds.SoundSource.PLAYERS, 1.0F, 1.0F);
        }
    }

    @Override
    protected void onTick(LivingEntity entity, long duration) {
        if (!entity.level().isClientSide) {
            entity.addEffect(new net.minecraft.world.effect.MobEffectInstance(net.minecraft.world.effect.MobEffects.DAMAGE_RESISTANCE, 5, 4, false, false));
            entity.addEffect(new net.minecraft.world.effect.MobEffectInstance(net.minecraft.world.effect.MobEffects.MOVEMENT_SLOWDOWN, 5, 9, false, false));
        } else {
             if (duration % 4 == 0) {
                entity.level().addParticle(net.minecraft.core.particles.ParticleTypes.CRIT, 
                    entity.getX() + (entity.getRandom().nextDouble() - 0.5), 
                    entity.getY() + 1, 
                    entity.getZ() + (entity.getRandom().nextDouble() - 0.5), 
                    0, 0, 0);
            }
        }

        if (duration >= DURATION) {
            this.startCooldown(entity, 200);
            this.stop(entity);
        }
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.tekkai");
    }
}
