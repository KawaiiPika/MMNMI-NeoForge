package xyz.pixelatedw.mineminenomi.api.abilities.rokushiki;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

public class RankyakuAbility extends Ability {

    @Override
    protected void startUsing(LivingEntity entity) {
        if (!entity.level().isClientSide) {
             entity.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(), 
                xyz.pixelatedw.mineminenomi.init.ModSounds.RANKYAKU_SFX.get(), 
                net.minecraft.sounds.SoundSource.PLAYERS, 1.0F, 1.2F);
             
             this.startCooldown(entity, 60);
        }
        
        Vec3 look = entity.getLookAngle();
        Vec3 startPos = entity.getEyePosition();
        
        for (double i = 0; i < 25.0; i += 0.5) {
            double x = startPos.x + look.x * i;
            double y = startPos.y + look.y * i;
            double z = startPos.z + look.z * i;
            
            if (entity.level().isClientSide) {
                entity.level().addParticle(net.minecraft.core.particles.ParticleTypes.SWEEP_ATTACK, x, y, z, 0, 0, 0);
                entity.level().addParticle(net.minecraft.core.particles.ParticleTypes.CLOUD, x, y, z, 0, 0.05, 0);
            } else {
                for (Entity target : entity.level().getEntitiesOfClass(LivingEntity.class, new net.minecraft.world.phys.AABB(x - 0.5, y - 0.5, z - 0.5, x + 0.5, y + 0.5, z + 0.5), e -> e != entity)) {
                    if (target instanceof LivingEntity livingTarget) {
                        livingTarget.hurt(entity.damageSources().mobAttack(entity), 10.0F);
                        livingTarget.setDeltaMovement(look.scale(1.5).add(0, 0.2, 0));
                        livingTarget.hurtMarked = true;
                        return;
                    }
                }
                
                if (!entity.level().isEmptyBlock(new net.minecraft.core.BlockPos((int) x, (int) y, (int) z))) {
                    break;
                }
            }
        }
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.rankyaku");
    }
}
