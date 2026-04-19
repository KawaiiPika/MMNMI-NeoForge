package xyz.pixelatedw.mineminenomi.abilities.moku;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.entities.projectiles.WhiteOutEntity;

/** White Out — Large smoke cloud that traps enemies. */
public class WhiteOutAbility extends Ability {
    private static final ResourceLocation FRUIT = ResourceLocation.fromNamespaceAndPath("mineminenomi", "moku_moku_no_mi");
    
    public WhiteOutAbility() { 
        super(FRUIT); 
    }

    @Override
    protected void startUsing(LivingEntity entity) {
        if (!entity.level().isClientSide) {
            WhiteOutEntity projectile = new WhiteOutEntity(entity.level(), entity);
            Vec3 look = entity.getLookAngle();
            projectile.shoot(look.x, look.y, look.z, 2.0F, 0.1F);
            entity.level().addFreshEntity(projectile);
            
            entity.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(), 
                xyz.pixelatedw.mineminenomi.init.ModSounds.MOKU_SFX.get(), 
                net.minecraft.sounds.SoundSource.PLAYERS, 1.5F, 1.0F);
            
            this.startCooldown(entity, 200);
        }
        
        if (entity.level().isClientSide) {
             for(int i = 0; i < 10; i++) {
                 entity.level().addParticle(net.minecraft.core.particles.ParticleTypes.CAMPFIRE_COSY_SMOKE, 
                    entity.getX(), entity.getY() + 1, entity.getZ(), 
                    (entity.getRandom().nextDouble() - 0.5) * 0.2, 0.1, (entity.getRandom().nextDouble() - 0.5) * 0.2);
             }
        }
    }

    @Override
    public Component getDisplayName() { 
        return Component.translatable("ability.mineminenomi.white_out"); 
    }
}
