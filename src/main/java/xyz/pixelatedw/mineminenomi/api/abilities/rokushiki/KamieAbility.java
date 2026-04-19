package xyz.pixelatedw.mineminenomi.api.abilities.rokushiki;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

public class KamieAbility extends Ability {

    @Override
    protected void startUsing(LivingEntity entity) {
        if (!entity.level().isClientSide) {
            Vec3 look = entity.getLookAngle();
            Vec3 side = new Vec3(-look.z, 0.1, look.x).normalize().scale(1.8);
            if (entity.getRandom().nextBoolean()) side = side.scale(-1);
            
            entity.setDeltaMovement(entity.getDeltaMovement().add(side));
            entity.hurtMarked = true;
            
            entity.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(), 
                xyz.pixelatedw.mineminenomi.init.ModSounds.KAMIE_SFX.get(), 
                net.minecraft.sounds.SoundSource.PLAYERS, 1.0F, 1.0F);
            
            this.startCooldown(entity, 80);
        } else {
             for (int i = 0; i < 8; i++) {
                entity.level().addParticle(net.minecraft.core.particles.ParticleTypes.POOF, 
                    entity.getX(), entity.getY() + 1, entity.getZ(), 
                    (entity.getRandom().nextDouble() - 0.5) * 0.5, 0.1, (entity.getRandom().nextDouble() - 0.5) * 0.5);
            }
        }
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.kamie");
    }
}
