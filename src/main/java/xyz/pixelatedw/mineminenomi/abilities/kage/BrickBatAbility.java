package xyz.pixelatedw.mineminenomi.abilities.kage;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.entities.projectiles.BrickBatEntity;

public class BrickBatAbility extends Ability {
    private static final ResourceLocation FRUIT = ResourceLocation.fromNamespaceAndPath("mineminenomi", "kage_kage_no_mi");
    
    public BrickBatAbility() { 
        super(FRUIT); 
    }

    @Override
    protected void startUsing(LivingEntity entity) {
        if (!entity.level().isClientSide) {
            for (int i = 0; i < 5; i++) {
                BrickBatEntity projectile = new BrickBatEntity(entity.level(), entity);
                projectile.shootFromRotation(entity, entity.getXRot() + (entity.getRandom().nextFloat() - 0.5f) * 20, entity.getYRot() + (entity.getRandom().nextFloat() - 0.5f) * 20, 0.0F, 1.5F, 1.0F);
                entity.level().addFreshEntity(projectile);
            }
            
            entity.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(),
                net.minecraft.sounds.SoundEvents.BAT_TAKEOFF,
                net.minecraft.sounds.SoundSource.PLAYERS, 1.5F, 1.0F);

            this.startCooldown(entity, 120);
        }
    }

    @Override
    public Component getDisplayName() { 
        return Component.translatable("ability.mineminenomi.brick_bat"); 
    }
}
