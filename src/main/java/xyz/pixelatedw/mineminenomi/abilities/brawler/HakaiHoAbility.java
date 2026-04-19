package xyz.pixelatedw.mineminenomi.abilities.brawler;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

public class HakaiHoAbility extends Ability {

    public HakaiHoAbility() {}

    @Override
    protected void startUsing(LivingEntity entity) {
        if (!entity.level().isClientSide) {
            Vec3 look = entity.getLookAngle();
            for (int i = 1; i <= 6; i++) {
                Vec3 punchPos = entity.getEyePosition().add(look.scale(i));
                AABB area = new AABB(punchPos.subtract(1.5, 1.5, 1.5), punchPos.add(1.5, 1.5, 1.5));
                
                for (LivingEntity target : entity.level().getEntitiesOfClass(LivingEntity.class, area)) {
                    if (target != entity) {
                        target.hurt(entity.damageSources().mobAttack(entity), 12.0f);
                        Vec3 knockback = look.scale(2.0);
                        target.setDeltaMovement(knockback.x, 0.5, knockback.z);
                    }
                }
            }
            entity.sendSystemMessage(Component.literal("Hakai Ho!"));
        }
        
        this.startCooldown(entity, 140);
        this.stop(entity);
    }

    @Override
    public Component getDisplayName() {
        return Component.literal("Hakai Ho");
    }
}
