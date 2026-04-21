package xyz.pixelatedw.mineminenomi.abilities.yami;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.yami.LiberationProjectile;
import xyz.pixelatedw.mineminenomi.init.ModBlocks;

public class LiberationAbility extends Ability {
    private static final int COOLDOWN = 80;

    public LiberationAbility() {
        super(ResourceLocation.fromNamespaceAndPath("mineminenomi", "yami_yami_no_mi"));
    }

    @Override
    protected void startUsing(LivingEntity entity) {
        if (!entity.level().isClientSide) {
            int batchSize = 10 + entity.getRandom().nextInt(5);
            for(int i = 0; i < batchSize; ++i) {
                LiberationProjectile proj = new LiberationProjectile(entity.level(), entity);
                Vec3 lookVec = entity.getLookAngle();

                if (lookVec.y > 0.7) {
                    proj.shootFromRotation(entity, entity.getXRot(), entity.getYRot(), 0.0F, 3.0F, 6.0F);
                    proj.setLife(300);
                } else {
                    double x = entity.getX() + entity.getRandom().nextDouble() * 6 - 3;
                    double y = entity.getY() + 14.0 + entity.getRandom().nextDouble() * 4;
                    double z = entity.getZ() + entity.getRandom().nextDouble() * 6 - 3;

                    proj.setPos(x, y, z);
                    proj.setDeltaMovement(0, -0.7 - 0.08D, 0);
                }

                entity.level().addFreshEntity(proj);
            }
            this.startCooldown(entity, COOLDOWN);
            this.stop(entity);
        }
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.liberation");
    }
}
