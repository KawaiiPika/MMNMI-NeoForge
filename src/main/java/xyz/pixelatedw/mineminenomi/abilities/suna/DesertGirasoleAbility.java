package xyz.pixelatedw.mineminenomi.abilities.suna;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.Blocks;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

public class DesertGirasoleAbility extends Ability {
    private static final ResourceLocation FRUIT = ResourceLocation.fromNamespaceAndPath("mineminenomi", "suna_suna_no_mi");

    public DesertGirasoleAbility() {
        super(FRUIT);
    }

    @Override
    protected void startUsing(LivingEntity entity) {
        if (!entity.level().isClientSide) {
            int radius = 4;
            BlockPos center = entity.blockPosition().below();
            BlockPos.betweenClosedStream(center.offset(-radius, -1, -radius), center.offset(radius, 0, radius))
                .forEach(pos -> {
                    if (pos.distSqr(center) <= radius * radius) {
                        net.minecraft.world.level.block.state.BlockState state = entity.level().getBlockState(pos);
                        if (state.isCollisionShapeFullBlock(entity.level(), pos) && !state.is(Blocks.BEDROCK)) {
                            entity.level().setBlockAndUpdate(pos, Blocks.SAND.defaultBlockState());
                        }
                    }
                });

            // Sucks entities inward
            entity.level().getEntitiesOfClass(LivingEntity.class, entity.getBoundingBox().inflate(radius)).forEach(target -> {
                if (target != entity) {
                    net.minecraft.world.phys.Vec3 pull = center.getCenter().subtract(target.position()).normalize().scale(0.8);
                    target.setDeltaMovement(target.getDeltaMovement().add(pull));
                    target.hurt(entity.damageSources().inWall(), 3.0F); // Suffocation damage
                }
            });

            this.startCooldown(entity, 300);
        }
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.desert_girasole");
    }
}
