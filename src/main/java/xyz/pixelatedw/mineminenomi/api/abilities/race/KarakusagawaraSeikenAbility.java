package xyz.pixelatedw.mineminenomi.api.abilities.race;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

import java.util.List;

public class KarakusagawaraSeikenAbility extends Ability {

    private static final int CHARGE_TICKS = 40;

    public KarakusagawaraSeikenAbility() {
        super(ResourceLocation.fromNamespaceAndPath("mineminenomi", "fishman_karate"));
    }

    @Override
    protected void startUsing(LivingEntity entity) {}

    @Override
    protected void onTick(LivingEntity entity, long duration) {
        if (entity.level().isClientSide && duration % 5 == 0) {
            for (int i = 0; i < 20; i++) {
                entity.level().addParticle(net.minecraft.core.particles.ParticleTypes.CLOUD, 
                    entity.getX() + (entity.getRandom().nextDouble() - 0.5) * 4, 
                    entity.getY() + 1 + (entity.getRandom().nextDouble() - 0.5) * 2, 
                    entity.getZ() + (entity.getRandom().nextDouble() - 0.5) * 4, 
                    0, 0, 0);
            }
        }

        if (duration >= CHARGE_TICKS) {
            if (!entity.level().isClientSide) {
                List<LivingEntity> targets = entity.level().getEntitiesOfClass(LivingEntity.class, entity.getBoundingBox().inflate(10.0), e -> e != entity && e.isAlive());
                
                for (LivingEntity target : targets) {
                    float damage = 20.0f;
                    if (target.isInWater() || entity.isInWater()) {
                        damage *= 2.5f;
                    }
                    target.hurt(entity.damageSources().magic(), damage);
                    target.addEffect(new net.minecraft.world.effect.MobEffectInstance(net.minecraft.world.effect.MobEffects.MOVEMENT_SLOWDOWN, 200, 1));
                }

                entity.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(), 
                    net.minecraft.sounds.SoundEvents.GENERIC_EXPLODE, 
                    net.minecraft.sounds.SoundSource.PLAYERS, 1.0F, 1.2F);
                
                this.startCooldown(entity, 500);
            }
            this.stop(entity);
        }
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.karakusagawara_seiken");
    }
}
