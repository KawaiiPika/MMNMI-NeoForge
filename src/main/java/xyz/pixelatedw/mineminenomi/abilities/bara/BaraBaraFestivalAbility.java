package xyz.pixelatedw.mineminenomi.abilities.bara;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

/** Bara Bara Festival — Body splitting swarm attack. */
public class BaraBaraFestivalAbility extends Ability {
    private static final ResourceLocation FRUIT = ResourceLocation.fromNamespaceAndPath("mineminenomi", "bara_bara_no_mi");
    
    public BaraBaraFestivalAbility() { 
        super(FRUIT); 
    }

    @Override
    protected void startUsing(LivingEntity entity) {
        xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper.sendAbilityMessage(entity, Component.translatable("ability.mineminenomi.bara_bara_festival.on"));
    }

    @Override
    protected void onTick(LivingEntity entity, long duration) {
        if (!entity.level().isClientSide && duration % 20 == 0) {
            for (var target : entity.level().getEntities(entity, entity.getBoundingBox().inflate(5.0))) {
                if (target instanceof LivingEntity living) {
                    living.hurt(entity.damageSources().mobAttack(entity), 4.0F);
                    living.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 40, 1));
                    
                    if (living.level() instanceof net.minecraft.server.level.ServerLevel serverLevel) {
                        serverLevel.sendParticles(ParticleTypes.SWEEP_ATTACK, living.getX(), living.getY() + 1, living.getZ(), 5, 0.2, 0.2, 0.2, 0.05);
                    }
                }
            }
        }
        
        if (entity.level().isClientSide) {
            for (int i = 0; i < 3; i++) {
                entity.level().addParticle(ParticleTypes.CRIT, 
                    entity.getX() + (entity.getRandom().nextDouble() - 0.5) * 4, 
                    entity.getY() + entity.getRandom().nextDouble() * 2, 
                    entity.getZ() + (entity.getRandom().nextDouble() - 0.5) * 4, 
                    0, 0, 0);
            }
        }
    }

    @Override
    public Component getDisplayName() { 
        return Component.translatable("ability.mineminenomi.bara_bara_festival"); 
    }
}
