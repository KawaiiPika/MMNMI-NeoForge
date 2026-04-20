package xyz.pixelatedw.mineminenomi.abilities.magu;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.entities.projectiles.DaiFunkaEntity;

public class DaiFunkaAbility extends Ability {

    private static final int CHARGE_TICKS = 40; // 2 seconds

    public DaiFunkaAbility() {
        super(ResourceLocation.fromNamespaceAndPath("mineminenomi", "magu_magu_no_mi"));
    }

    @Override
    protected void startUsing(LivingEntity entity) {
        if (!entity.level().isClientSide) {
             entity.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(), 
                xyz.pixelatedw.mineminenomi.init.ModSounds.MAGU_SFX.get(), 
                net.minecraft.sounds.SoundSource.PLAYERS, 2.0F, 1.0F);
        }
    }

    @Override
    protected void onTick(LivingEntity entity, long duration) {
        if (duration < CHARGE_TICKS) {
            if (duration % 5 == 0) {
                entity.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(), 
                    net.minecraft.sounds.SoundEvents.LAVA_EXTINGUISH, 
                    net.minecraft.sounds.SoundSource.PLAYERS, 1.0F, 0.5F);
            }
            if (entity.level().isClientSide) {
                for (int i = 0; i < 5; i++) {
                    entity.level().addParticle(net.minecraft.core.particles.ParticleTypes.LARGE_SMOKE, 
                        entity.getX() + (entity.getRandom().nextDouble() - 0.5) * 2, 
                        entity.getY() + 1 + entity.getRandom().nextDouble(), 
                        entity.getZ() + (entity.getRandom().nextDouble() - 0.5) * 2, 
                        0, 0.05, 0);
                    entity.level().addParticle(net.minecraft.core.particles.ParticleTypes.FLAME, 
                        entity.getX() + (entity.getRandom().nextDouble() - 0.5), 
                        entity.getY() + 1, 
                        entity.getZ() + (entity.getRandom().nextDouble() - 0.5), 
                        0, 0.1, 0);
                }
            }
        } else if (duration == CHARGE_TICKS) {
            if (!entity.level().isClientSide) {
                DaiFunkaEntity daiFunka = new DaiFunkaEntity(entity.level(), entity);
                daiFunka.shootFromRotation(entity, entity.getXRot(), entity.getYRot(), 0.0F, 2.0F, 0.1F);
                entity.level().addFreshEntity(daiFunka);
                
                entity.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(), 
                    net.minecraft.sounds.SoundEvents.GENERIC_EXPLODE.value(), 
                    net.minecraft.sounds.SoundSource.PLAYERS, 2.0F, 0.8F);
                
                this.startCooldown(entity, 200);
            }
            this.stop(entity);
        }
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.dai_funka");
    }
}
