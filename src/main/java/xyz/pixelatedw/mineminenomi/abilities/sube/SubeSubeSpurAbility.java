package xyz.pixelatedw.mineminenomi.abilities.sube;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

/** Sube Sube Spur — Sliding movement. */
public class SubeSubeSpurAbility extends Ability {
    private static final ResourceLocation FRUIT = ResourceLocation.fromNamespaceAndPath("mineminenomi", "sube_sube_no_mi");
    
    public SubeSubeSpurAbility() { 
        super(FRUIT); 
    }

    @Override
    protected void startUsing(LivingEntity entity) {
        xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper.sendAbilityMessage(entity, Component.translatable("ability.mineminenomi.sube_sube_spur.on"));
    }

    @Override
    protected void onTick(LivingEntity entity, long duration) {
        if (entity.onGround()) {
            Vec3 look = entity.getLookAngle();
            Vec3 movement = new Vec3(look.x * 0.4, 0, look.z * 0.4);
            entity.setDeltaMovement(entity.getDeltaMovement().add(movement));
            
            if (entity.level().isClientSide) {
                entity.level().addParticle(net.minecraft.core.particles.ParticleTypes.CLOUD, 
                    entity.getX(), entity.getY(), entity.getZ(), 0, 0.1, 0);
            }
        }
    }

    @Override
    public Component getDisplayName() { 
        return Component.translatable("ability.mineminenomi.sube_sube_spur"); 
    }
}
