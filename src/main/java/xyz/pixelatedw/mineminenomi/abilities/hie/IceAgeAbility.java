package xyz.pixelatedw.mineminenomi.abilities.hie;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

public class IceAgeAbility extends Ability {
    private static final ResourceLocation FRUIT = ResourceLocation.fromNamespaceAndPath("mineminenomi", "hie_hie_no_mi");
    public IceAgeAbility() { super(FRUIT); }

    private static final int CHARGE_TICKS = 60; // 3 seconds

    @Override
    protected void startUsing(LivingEntity entity) {
        if (!entity.level().isClientSide) {
             entity.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(), 
                xyz.pixelatedw.mineminenomi.init.ModSounds.HIE_SFX.get(), 
                net.minecraft.sounds.SoundSource.PLAYERS, 2.0F, 1.0F);
        }
    }

    @Override
    protected void onTick(LivingEntity entity, long duration) {
        if (duration < CHARGE_TICKS) {
            if (entity.level().isClientSide && duration % 5 == 0) {
                 for(int i = 0; i < 15; i++) {
                     entity.level().addParticle(net.minecraft.core.particles.ParticleTypes.SNOWFLAKE, 
                        entity.getX() + (entity.getRandom().nextDouble() - 0.5) * 8, 
                        entity.getY() + 1, 
                        entity.getZ() + (entity.getRandom().nextDouble() - 0.5) * 8, 
                        0, 0.1, 0);
                 }
            }
        } else if (duration == CHARGE_TICKS) {
            if (!entity.level().isClientSide) {
                for (var target : entity.level().getEntities(entity, entity.getBoundingBox().inflate(25.0))) {
                    if (target instanceof LivingEntity living) {
                        living.addEffect(new net.minecraft.world.effect.MobEffectInstance(net.minecraft.world.effect.MobEffects.MOVEMENT_SLOWDOWN, 600, 9));
                        living.addEffect(new net.minecraft.world.effect.MobEffectInstance(net.minecraft.world.effect.MobEffects.WEAKNESS, 600, 2));
                        living.hurt(entity.damageSources().freeze(), 20.0F);
                        living.setTicksFrozen(600);
                    }
                }
                
                // Ice particles
                for (int i = 0; i < 100; i++) {
                    double rx = (entity.getRandom().nextDouble() - 0.5) * 50;
                    double rz = (entity.getRandom().nextDouble() - 0.5) * 50;
                    entity.level().addParticle(net.minecraft.core.particles.ParticleTypes.CLOUD, 
                        entity.getX() + rx, entity.getY(), entity.getZ() + rz, 
                        0, 0.2, 0);
                }
                
                this.startCooldown(entity, 900);
            }
            this.stop(entity);
        }
    }

    @Override
    public Component getDisplayName() { return Component.translatable("ability.mineminenomi.ice_age"); }
}
