package xyz.pixelatedw.mineminenomi.api.abilities.rokushiki;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;

public class SoruAbility extends Ability {

    @Override
    protected void startUsing(LivingEntity entity) {
        if (!entity.level().isClientSide) {
            Vec3 look = entity.getLookAngle();
            double speed = 2.8;
            
            AbilityHelper.setDeltaMovement(entity, look.x * speed, look.y * speed + 0.1, look.z * speed);
            entity.hurtMarked = true;

            entity.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(), 
                xyz.pixelatedw.mineminenomi.init.ModSounds.SORU_SFX.get(), 
                net.minecraft.sounds.SoundSource.PLAYERS, 1.0F, 1.0F);
            
            this.startCooldown(entity, 50);
        } else {
             for (int i = 0; i < 15; i++) {
                entity.level().addParticle(net.minecraft.core.particles.ParticleTypes.POOF, 
                    entity.getX() + (entity.getRandom().nextDouble() - 0.5) * 2, 
                    entity.getY() + 0.5, 
                    entity.getZ() + (entity.getRandom().nextDouble() - 0.5) * 2, 
                    0, 0, 0);
            }
        }
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.soru");
    }
}
