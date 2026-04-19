package xyz.pixelatedw.mineminenomi.abilities.mero;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.entities.projectiles.PistolKissEntity;

/** Pistol Kiss — Heart-shaped projectile that inflicts infatuation. */
public class PistolKissAbility extends Ability {
    private static final ResourceLocation FRUIT = ResourceLocation.fromNamespaceAndPath("mineminenomi", "mero_mero_no_mi");
    
    public PistolKissAbility() { 
        super(FRUIT); 
    }

    @Override
    protected void startUsing(LivingEntity entity) {
        if (!entity.level().isClientSide) {
            PistolKissEntity projectile = new PistolKissEntity(entity.level(), entity);
            Vec3 look = entity.getLookAngle();
            projectile.shoot(look.x, look.y, look.z, 2.5F, 1.0F);
            entity.level().addFreshEntity(projectile);
            
            xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper.sendAbilityMessage(entity, Component.translatable("ability.mineminenomi.pistol_kiss.on"));
        }
    }

    @Override
    public Component getDisplayName() { 
        return Component.translatable("ability.mineminenomi.pistol_kiss"); 
    }
}
