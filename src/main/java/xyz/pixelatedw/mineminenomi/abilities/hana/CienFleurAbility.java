package xyz.pixelatedw.mineminenomi.abilities.hana;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.init.ModEffects;

/**
 * Cien Fleur — Robin sprouts 100 hands in an AoE, grabbing and damaging enemies.
 * Implemented as a wide grab-and-squeeze attack.
 */
public class CienFleurAbility extends Ability {
    private static final ResourceLocation FRUIT = ResourceLocation.fromNamespaceAndPath("mineminenomi", "hana_hana_no_mi");
    
    public CienFleurAbility() { 
        super(FRUIT); 
    }

    @Override
    protected void startUsing(LivingEntity entity) {
        if (!entity.level().isClientSide) {
            for (var target : entity.level().getEntitiesOfClass(LivingEntity.class, entity.getBoundingBox().inflate(8.0), e -> e != entity)) {
                if (target instanceof LivingEntity living) {
                    living.hurt(entity.damageSources().mobAttack(entity), 9.0F);
                    living.addEffect(new MobEffectInstance(ModEffects.MOVEMENT_BLOCKED, 80, 0));
                    living.hurtMarked = true;
                    
                    if (living.level() instanceof net.minecraft.server.level.ServerLevel serverLevel) {
                        serverLevel.sendParticles(ParticleTypes.CHERRY_LEAVES, living.getX(), living.getY() + 1, living.getZ(), 10, 0.3, 0.3, 0.3, 0.02);
                    }
                }
            }
            
            xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper.sendAbilityMessage(entity, Component.translatable("ability.mineminenomi.cien_fleur.on"));
        }
    }

    @Override
    public Component getDisplayName() { 
        return Component.translatable("ability.mineminenomi.cien_fleur"); 
    }
}
