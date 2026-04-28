package xyz.pixelatedw.mineminenomi.abilities.moku;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.entities.projectiles.WhiteSnakeEntity;

public class WhiteSnakeAbility extends Ability {

    @Override
    public void start(LivingEntity entity) {
        super.start(entity);
        if (!entity.level().isClientSide) {
            WhiteSnakeEntity proj = new WhiteSnakeEntity(entity.level(), entity);
            Vec3 look = entity.getLookAngle();
            proj.shoot(look.x, look.y, look.z, 1.5F, 0);
            entity.level().addFreshEntity(proj);
        }
    }

    @Override
    public String getId() {
        return "white_snake";
    }

    @Override
    public String getDisplayName() {
        return "White Snake";
    }
}
