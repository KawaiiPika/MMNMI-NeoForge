package xyz.pixelatedw.mineminenomi.api.abilities.race;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

public class MizuTaihoAbility extends Ability {
    private static final int CHARGE_TICKS = 30; // 1.5 seconds

    public MizuTaihoAbility() {
        super(ResourceLocation.fromNamespaceAndPath("mineminenomi", "fishman_karate"));
    }

    @Override
    protected void startUsing(LivingEntity entity) {
        if (!entity.level().isClientSide) {
             entity.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(), 
                net.minecraft.sounds.SoundEvents.GENERIC_SPLASH, 
                net.minecraft.sounds.SoundSource.PLAYERS, 1.5F, 1.0F);
        }
    }

    @Override
    protected void onTick(LivingEntity entity, long duration) {
        if (duration < CHARGE_TICKS) {
            if (entity.level().isClientSide && duration % 3 == 0) {
                 for(int i = 0; i < 10; i++) {
                     entity.level().addParticle(net.minecraft.core.particles.ParticleTypes.SPLASH, 
                        entity.getX() + (entity.getRandom().nextDouble() - 0.5) * 2, 
                        entity.getY() + 1 + entity.getRandom().nextDouble(), 
                        entity.getZ() + (entity.getRandom().nextDouble() - 0.5) * 2, 
                        0, 0, 0);
                 }
            }
        } else if (duration == CHARGE_TICKS) {
            if (!entity.level().isClientSide) {
                xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.fishmankarate.MizuTaihoProjectile projectile = new xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.fishmankarate.MizuTaihoProjectile(entity.level(), entity);
                projectile.setPos(entity.getX(), entity.getEyeY(), entity.getZ());
                projectile.shootFromRotation(entity, entity.getXRot(), entity.getYRot(), 0.0F, 1.5F, 1.0F);
                entity.level().addFreshEntity(projectile);
                
                entity.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(), 
                    net.minecraft.sounds.SoundEvents.PLAYER_SPLASH_HIGH_SPEED, 
                    net.minecraft.sounds.SoundSource.PLAYERS, 2.0F, 0.8F);
                
                this.startCooldown(entity, 340);
            }
            this.stop(entity);
        }
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.mizu_taiho");
    }
}
