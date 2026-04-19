package xyz.pixelatedw.mineminenomi.abilities.kage;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.entities.projectiles.BrickBatEntity;

/** Brick Bat — Shadow bats projectile. */
public class BrickBatAbility extends Ability {
    private static final ResourceLocation FRUIT = ResourceLocation.fromNamespaceAndPath("mineminenomi", "kage_kage_no_mi");
    
    public BrickBatAbility() { 
        super(FRUIT); 
    }

    @Override
    protected void startUsing(LivingEntity entity) {
        if (!entity.level().isClientSide) {
            for (int i = 0; i < 3; i++) {
                BrickBatEntity projectile = new BrickBatEntity(entity.level(), entity);
                Vec3 look = entity.getLookAngle();
                // Randomize slightly
                projectile.shoot(look.x + (entity.getRandom().nextFloat() - 0.5) * 0.2, 
                                 look.y + (entity.getRandom().nextFloat() - 0.5) * 0.2, 
                                 look.z + (entity.getRandom().nextFloat() - 0.5) * 0.2, 1.5F, 1.0F);
                entity.level().addFreshEntity(projectile);
            }
            
            if (!entity.level().isClientSide) {
                xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper.sendAbilityMessage(entity, Component.translatable("ability.mineminenomi.brick_bat.on"));
            }
        }
    }

    @Override
    public Component getDisplayName() { 
        return Component.translatable("ability.mineminenomi.brick_bat"); 
    }
}
