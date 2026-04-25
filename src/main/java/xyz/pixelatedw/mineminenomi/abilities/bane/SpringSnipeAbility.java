package xyz.pixelatedw.mineminenomi.abilities.bane;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

public class SpringSnipeAbility extends Ability {

    public SpringSnipeAbility() {
        super(ResourceLocation.fromNamespaceAndPath("mineminenomi", "bane_bane_no_mi"));
    }

    @Override
    protected void startUsing(LivingEntity entity) {
        if (!entity.level().isClientSide) {
            Vec3 speed = entity.getLookAngle().scale(6.0);
            entity.setDeltaMovement(speed);

            for (var target : entity.level().getEntities(entity, entity.getBoundingBox().inflate(1.6))) {
                if (target instanceof LivingEntity livingTarget) {
                    livingTarget.hurt(entity.damageSources().mobAttack(entity), 50.0F);
                }
            }

            this.startCooldown(entity, 280);
        }
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.spring_snipe");
    }
}
