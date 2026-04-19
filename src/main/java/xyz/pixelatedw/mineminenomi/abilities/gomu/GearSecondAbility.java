package xyz.pixelatedw.mineminenomi.abilities.gomu;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

/** Gear Second — blood-pump acceleration, grants Speed + Strength for a duration. */
public class GearSecondAbility extends Ability {
    private static final ResourceLocation FRUIT = ResourceLocation.fromNamespaceAndPath("mineminenomi", "gomu_gomu_no_mi");
    public GearSecondAbility() { super(FRUIT); }

    @Override
    protected void startUsing(LivingEntity entity) {
        if (!entity.level().isClientSide) {
             entity.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(), 
                xyz.pixelatedw.mineminenomi.init.ModSounds.GEAR_SECOND_SFX.get(), 
                net.minecraft.sounds.SoundSource.PLAYERS, 1.5F, 1.0F);
        }
    }

    @Override
    protected void onTick(LivingEntity entity, long duration) {
        if (!entity.level().isClientSide) {
            entity.addEffect(new net.minecraft.world.effect.MobEffectInstance(net.minecraft.world.effect.MobEffects.MOVEMENT_SPEED, 10, 2, false, false));
            entity.addEffect(new net.minecraft.world.effect.MobEffectInstance(net.minecraft.world.effect.MobEffects.DAMAGE_BOOST, 10, 1, false, false));
            entity.addEffect(new net.minecraft.world.effect.MobEffectInstance(net.minecraft.world.effect.MobEffects.DIG_SPEED, 10, 1, false, false));
        } else {
             if (duration % 5 == 0) {
                 for (int i = 0; i < 3; i++) {
                     entity.level().addParticle(net.minecraft.core.particles.ParticleTypes.LARGE_SMOKE, 
                        entity.getX() + (entity.getRandom().nextDouble() - 0.5) * 0.5, 
                        entity.getY() + entity.getRandom().nextDouble() * 2, 
                        entity.getZ() + (entity.getRandom().nextDouble() - 0.5) * 0.5, 
                        0, 0.05, 0);
                 }
             }
        }
        
        // Auto-stop after 1 minute or manually (handled by UI)
        if (duration > 1200) {
            this.stop(entity);
            this.startCooldown(entity, 600);
        }
    }

    @Override
    public void stop(LivingEntity entity) {
        super.stop(entity);
        if (!entity.level().isClientSide) {
             this.startCooldown(entity, 200); // Base cooldown for manual stop
        }
    }

    @Override
    public Component getDisplayName() { return Component.translatable("ability.mineminenomi.gear_second"); }
}
