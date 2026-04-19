package xyz.pixelatedw.mineminenomi.abilities.mero;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.entities.projectiles.MeroMeroMellowEntity;

/** Mero Mero Mellow — Petrification beam. */
public class MeroMeroMellowAbility extends Ability {
    private static final ResourceLocation FRUIT = ResourceLocation.fromNamespaceAndPath("mineminenomi", "mero_mero_no_mi");
    
    public MeroMeroMellowAbility() { 
        super(FRUIT); 
    }

    @Override
    protected void startUsing(LivingEntity entity) {
        if (!entity.level().isClientSide) {
            MeroMeroMellowEntity projectile = new MeroMeroMellowEntity(entity.level(), entity);
            Vec3 look = entity.getLookAngle();
            projectile.shoot(look.x, look.y, look.z, 2.0F, 1.0F);
            entity.level().addFreshEntity(projectile);
            
            AbilityHelper.sendAbilityMessage(entity, Component.translatable("ability.mineminenomi.mero_mero_mellow.on"));
        }
    }

    @Override
    public Component getDisplayName() { 
        return Component.translatable("ability.mineminenomi.mero_mero_mellow"); 
    }
}
