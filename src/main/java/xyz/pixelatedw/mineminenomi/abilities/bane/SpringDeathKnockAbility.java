package xyz.pixelatedw.mineminenomi.abilities.bane;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.InteractionHand;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.bane.SpringDeathKnockProjectile;

public class SpringDeathKnockAbility extends Ability {

    public SpringDeathKnockAbility() {
        super(ResourceLocation.fromNamespaceAndPath("mineminenomi", "bane_bane_no_mi"));
    }

    @Override
    protected void startUsing(LivingEntity entity) {
        if (!entity.level().isClientSide) {
            SpringDeathKnockProjectile proj = new SpringDeathKnockProjectile(entity.level(), entity);
            proj.shoot(entity.getLookAngle().x, entity.getLookAngle().y, entity.getLookAngle().z, 3.0F, 1.0F);
            entity.level().addFreshEntity(proj);
            entity.swing(InteractionHand.MAIN_HAND, true);
            this.startCooldown(entity, 80);
        }
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.spring_death_knock");
    }
}
