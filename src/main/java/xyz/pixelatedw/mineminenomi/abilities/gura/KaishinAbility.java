package xyz.pixelatedw.mineminenomi.abilities.gura;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

/** Kaishin — Gura sea quake: creates a massive ripple AOE shockwave around the user. */
public class KaishinAbility extends Ability {
    private static final ResourceLocation FRUIT = ResourceLocation.fromNamespaceAndPath("mineminenomi", "gura_gura_no_mi");
    
    public KaishinAbility() { 
        super(FRUIT); 
    }

    @Override
    protected void startUsing(LivingEntity entity) {
        if (!entity.level().isClientSide) {
            for (LivingEntity living : entity.level().getEntitiesOfClass(LivingEntity.class, entity.getBoundingBox().inflate(12.0))) {
                if (living != entity) {
                    Vec3 dir = living.position().subtract(entity.position()).normalize();
                    living.hurt(entity.damageSources().mobAttack(entity), 12.0F);
                    living.setDeltaMovement(dir.scale(4.0).add(0, 0.8, 0));
                    living.hurtMarked = true;
                }
            }
            
            // Particles
            for (int i = 0; i < 360; i += 10) {
                double rad = Math.toRadians(i);
                double x = Math.cos(rad) * 5;
                double z = Math.sin(rad) * 5;
                entity.level().addParticle(ParticleTypes.EXPLOSION, 
                    entity.getX() + x, entity.getY() + 1, entity.getZ() + z, 
                    0, 0, 0);
            }
            
            if (!entity.level().isClientSide) {
                xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper.sendAbilityMessage(entity, Component.translatable("ability.mineminenomi.kaishin.on"));
            }
        }
    }

    @Override
    public Component getDisplayName() { 
        return Component.translatable("ability.mineminenomi.kaishin"); 
    }
}
