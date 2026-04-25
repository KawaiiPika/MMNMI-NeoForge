package xyz.pixelatedw.mineminenomi.abilities.jiki;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

public class PunkGibsonAbility extends Ability {

    public PunkGibsonAbility() {
        super(ResourceLocation.fromNamespaceAndPath("mineminenomi", "jiki_jiki_no_mi"));
    }

    @Override
    protected void startUsing(LivingEntity entity) {
        if (!entity.level().isClientSide) {
            Vec3 look = entity.getLookAngle();
            for (var target : entity.level().getEntities(entity, entity.getBoundingBox().inflate(3.0).move(look.scale(3.0)))) {
                if (target instanceof LivingEntity livingTarget) {
                    livingTarget.hurt(entity.damageSources().mobAttack(entity), 25.0F);
                    Vec3 knockback = look.scale(1.5);
                    livingTarget.setDeltaMovement(knockback.x, 0.5, knockback.z);
                }
            }
            this.startCooldown(entity, 600);
        }
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.punk_gibson");
    }
}
