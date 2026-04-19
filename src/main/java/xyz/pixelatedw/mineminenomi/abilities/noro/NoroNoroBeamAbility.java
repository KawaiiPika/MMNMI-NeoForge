package xyz.pixelatedw.mineminenomi.abilities.noro;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.entities.projectiles.NoroNoroBeamEntity;

/** Noro Noro Beam — Photons that slow down everything they touch. */
public class NoroNoroBeamAbility extends Ability {
    private static final ResourceLocation FRUIT = ResourceLocation.fromNamespaceAndPath("mineminenomi", "noro_noro_no_mi");
    
    public NoroNoroBeamAbility() { 
        super(FRUIT); 
    }

    @Override
    protected void startUsing(LivingEntity entity) {
        if (!entity.level().isClientSide) {
            NoroNoroBeamEntity projectile = new NoroNoroBeamEntity(entity.level(), entity);
            Vec3 look = entity.getLookAngle();
            projectile.shoot(look.x, look.y, look.z, 2.0F, 1.0F);
            entity.level().addFreshEntity(projectile);
            
            xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper.sendAbilityMessage(entity, Component.translatable("ability.mineminenomi.noro_noro_beam.on"));
        }
    }

    @Override
    public Component getDisplayName() { 
        return Component.translatable("ability.mineminenomi.noro_noro_beam"); 
    }
}
