package xyz.pixelatedw.mineminenomi.abilities.bara;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.entities.projectiles.BaraBaraHoEntity;

/** Bara Bara Ho — Flying fist attack. */
public class BaraBaraHoAbility extends Ability {
    private static final ResourceLocation FRUIT = ResourceLocation.fromNamespaceAndPath("mineminenomi", "bara_bara_no_mi");
    
    public BaraBaraHoAbility() { 
        super(FRUIT); 
    }

    @Override
    protected void startUsing(LivingEntity entity) {
        if (!entity.level().isClientSide) {
            BaraBaraHoEntity projectile = new BaraBaraHoEntity(entity.level(), entity);
            Vec3 look = entity.getLookAngle();
            projectile.shoot(look.x, look.y, look.z, 2.0F, 1.0F);
            entity.level().addFreshEntity(projectile);
            
            xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper.sendAbilityMessage(entity, Component.translatable("ability.mineminenomi.bara_bara_ho.on"));
        }
    }

    @Override
    public Component getDisplayName() { 
        return Component.translatable("ability.mineminenomi.bara_bara_ho"); 
    }
}
