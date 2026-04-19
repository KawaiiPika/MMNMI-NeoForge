package xyz.pixelatedw.mineminenomi.abilities.brawler;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

public class JishinHoAbility extends Ability {

    public JishinHoAbility() {}

    @Override
    protected void startUsing(LivingEntity entity) {
        if (!entity.level().isClientSide) {
            AABB area = entity.getBoundingBox().inflate(5.0);
            for (LivingEntity target : entity.level().getEntitiesOfClass(LivingEntity.class, area)) {
                if (target != entity && target.onGround()) {
                    target.hurt(entity.damageSources().mobAttack(entity), 10.0f);
                    Vec3 knockback = target.position().subtract(entity.position()).normalize().scale(1.0);
                    target.setDeltaMovement(knockback.x, 0.6, knockback.z);
                }
            }
            entity.sendSystemMessage(Component.literal("Jishin Ho!"));
        }
        
        this.startCooldown(entity, 160);
        this.stop(entity);
    }

    @Override
    public Component getDisplayName() {
        return Component.literal("Jishin Ho");
    }
}
