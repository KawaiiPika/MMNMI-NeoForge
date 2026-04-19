package xyz.pixelatedw.mineminenomi.abilities.gomu;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.entities.projectiles.GomuGomuNoBazookaEntity;

/** Gomu Gomu no Bazooka — Double handed rubber attack with high knockback. */
public class GomuGomuNoBazookaAbility extends Ability {
    private static final ResourceLocation FRUIT = ResourceLocation.fromNamespaceAndPath("mineminenomi", "gomu_gomu_no_mi");
    
    public GomuGomuNoBazookaAbility() { 
        super(FRUIT); 
    }

    @Override
    protected void startUsing(LivingEntity entity) {
        if (!entity.level().isClientSide) {
            GomuGomuNoBazookaEntity projectile = new GomuGomuNoBazookaEntity(entity.level(), entity);
            Vec3 look = entity.getLookAngle();
            projectile.shoot(look.x, look.y, look.z, 2.0F, 1.0F);
            entity.level().addFreshEntity(projectile);
            
            entity.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(), 
                xyz.pixelatedw.mineminenomi.init.ModSounds.GOMU_SFX.get(), 
                net.minecraft.sounds.SoundSource.PLAYERS, 2.5F, 0.8F);
                
            this.startCooldown(entity, 60);
        }
    }

    @Override
    public Component getDisplayName() { 
        return Component.translatable("ability.mineminenomi.gomu_gomu_no_bazooka"); 
    }
}
