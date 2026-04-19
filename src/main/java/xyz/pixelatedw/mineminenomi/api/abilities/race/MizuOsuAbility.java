package xyz.pixelatedw.mineminenomi.api.abilities.race;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

public class MizuOsuAbility extends Ability {

    public MizuOsuAbility() {
        super(ResourceLocation.fromNamespaceAndPath("mineminenomi", "fishman_karate"));
    }

    @Override
    protected void startUsing(LivingEntity entity) {
        if (entity.level().isClientSide) return;

        Vec3 look = entity.getLookAngle();
        Vec3 pos = entity.getEyePosition().add(look.scale(1.5));
        AABB area = new AABB(pos.subtract(1.5, 1.5, 1.5), pos.add(1.5, 1.5, 1.5));

        for (LivingEntity target : entity.level().getEntitiesOfClass(LivingEntity.class, area)) {
            if (target != entity) {
                Vec3 push = target.position().subtract(entity.position()).normalize().scale(2.5);
                target.setDeltaMovement(push.x, 0.4, push.z);
                target.hurtMarked = true;
                
                target.hurt(entity.damageSources().mobAttack(entity), 8.0f);
            }
        }

        if (!entity.level().isClientSide) {
            entity.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(), 
                net.minecraft.sounds.SoundEvents.PLAYER_SPLASH, 
                net.minecraft.sounds.SoundSource.PLAYERS, 1.5F, 1.2F);
            
            // Visual feedback - using vanilla bubble particles
            ((net.minecraft.server.level.ServerLevel)entity.level()).sendParticles(
                net.minecraft.core.particles.ParticleTypes.BUBBLE, 
                pos.x, pos.y, pos.z, 20, 0.5, 0.5, 0.5, 0.1);
        }

        this.startCooldown(entity, 160);
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.mizu_osu");
    }
}
