package xyz.pixelatedw.mineminenomi.abilities.hana;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.init.ModEffects;

/** Clutch — Sprouting hands to snap the opponent's back. */
public class ClutchAbility extends Ability {
    private static final ResourceLocation FRUIT = ResourceLocation.fromNamespaceAndPath("mineminenomi", "hana_hana_no_mi");
    
    public ClutchAbility() { 
        super(FRUIT); 
    }

    @Override
    protected void startUsing(LivingEntity entity) {
        if (!entity.level().isClientSide) {
            Vec3 look = entity.getLookAngle();
            for (var target : entity.level().getEntities(entity, entity.getBoundingBox().inflate(1.5).move(look.scale(10.0)))) {
                if (target instanceof LivingEntity living) {
                    living.hurt(entity.damageSources().mobAttack(entity), 12.0F);
                    living.addEffect(new MobEffectInstance(ModEffects.MOVEMENT_BLOCKED, 40, 0));
                    
                    // Blossom effect
                    if (living.level() instanceof net.minecraft.server.level.ServerLevel serverLevel) {
                        serverLevel.sendParticles(ParticleTypes.CHERRY_LEAVES, living.getX(), living.getY() + 1, living.getZ(), 20, 0.5, 0.5, 0.5, 0.05);
                    }
                }
            }
            
            xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper.sendAbilityMessage(entity, Component.translatable("ability.mineminenomi.clutch.on"));
        }
    }

    @Override
    public Component getDisplayName() { 
        return Component.translatable("ability.mineminenomi.clutch"); 
    }
}
