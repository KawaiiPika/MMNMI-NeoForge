package xyz.pixelatedw.mineminenomi.abilities.nikyu;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.entities.projectiles.PadHoEntity;

/** Pad Ho — Repulsion shockwave projectile. */
public class PadHoAbility extends Ability {
    private static final ResourceLocation FRUIT = ResourceLocation.fromNamespaceAndPath("mineminenomi", "nikyu_nikyu_no_mi");
    
    public PadHoAbility() { 
        super(FRUIT); 
    }

    @Override
    protected void startUsing(LivingEntity entity) {
        if (!entity.level().isClientSide) {
            PadHoEntity projectile = new PadHoEntity(entity.level(), entity);
            Vec3 look = entity.getLookAngle();
            projectile.shoot(look.x, look.y, look.z, 2.5F, 1.0F);
            entity.level().addFreshEntity(projectile);
            
            AbilityHelper.sendAbilityMessage(entity, Component.translatable("ability.mineminenomi.pad_ho.on"));
        }
    }

    @Override
    public Component getDisplayName() { 
        return Component.translatable("ability.mineminenomi.pad_ho"); 
    }
}
