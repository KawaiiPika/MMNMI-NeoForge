package xyz.pixelatedw.mineminenomi.abilities.hie;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import java.util.List;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

public class IceBlockPheasantAbility extends Ability {

    public IceBlockPheasantAbility() {
        super(ResourceLocation.fromNamespaceAndPath("mineminenomi", "hie_hie_no_mi"));
    }

    @Override
    protected void startUsing(LivingEntity entity) {
        if (!entity.level().isClientSide) {
            Vec3 look = entity.getLookAngle();
            Vec3 startPos = entity.getEyePosition();
            
            AABB totalAABB = new AABB(startPos, startPos.add(look.scale(20.0))).inflate(4.0);
            List<LivingEntity> targets = entity.level().getEntitiesOfClass(LivingEntity.class, totalAABB, (t) -> t != entity && t.isAlive());

            for (LivingEntity livingTarget : targets) {
                for (double i = 0; i < 20.0; i += 1.0) {
                    Vec3 waveCenter = startPos.add(look.scale(i));
                    if (livingTarget.distanceToSqr(waveCenter) < 12.0) {
                        livingTarget.hurt(entity.damageSources().indirectMagic(entity, entity), 18.0F);
                        livingTarget.addEffect(new net.minecraft.world.effect.MobEffectInstance(net.minecraft.world.effect.MobEffects.MOVEMENT_SLOWDOWN, 120, 3));
                        livingTarget.setDeltaMovement(look.scale(1.5).add(0, 0.2, 0));
                        livingTarget.hurtMarked = true;
                        break;
                    }
                }
            }
            
            entity.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(), 
                net.minecraft.sounds.SoundEvents.PHANTOM_FLAP, 
                net.minecraft.sounds.SoundSource.PLAYERS, 2.0F, 0.5F);
            
            this.startCooldown(entity, 500);
        } else {
             Vec3 look = entity.getLookAngle();
             for (int i = 0; i < 40; i++) {
                 double d = i * 0.5;
                 for (int j = 0; j < 3; j++) {
                     entity.level().addParticle(net.minecraft.core.particles.ParticleTypes.SNOWFLAKE, 
                        entity.getX() + look.x * d + (entity.getRandom().nextDouble() - 0.5) * 2, 
                        entity.getY() + 1 + look.y * d + (entity.getRandom().nextDouble() - 0.5) * 2, 
                        entity.getZ() + look.z * d + (entity.getRandom().nextDouble() - 0.5) * 2, 
                        0, 0, 0);
                 }
             }
        }
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.ice_block_pheasant");
    }
}
