package xyz.pixelatedw.mineminenomi.abilities.suna;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.Blocks;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

public class GroundSeccoAbility extends Ability {
    public GroundSeccoAbility() {
        super(ResourceLocation.fromNamespaceAndPath("mineminenomi", "suna_suna_no_mi"));
    }

    @Override
    protected void startUsing(LivingEntity entity) {
        if (!entity.level().isClientSide) {
            int radius = 5;
            BlockPos center = entity.blockPosition();
            BlockPos.betweenClosedStream(center.offset(-radius, -2, -radius), center.offset(radius, 2, radius))
                .forEach(pos -> {
                    if (pos.distSqr(center) <= radius * radius) {
                        net.minecraft.world.level.block.state.BlockState state = entity.level().getBlockState(pos);
                        if (state.is(Blocks.DIRT) || state.is(Blocks.GRASS_BLOCK)) {
                            entity.level().setBlockAndUpdate(pos, Blocks.SAND.defaultBlockState());
                        } else if (state.is(Blocks.WATER)) {
                            entity.level().setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
                        }
                    }
                });

            entity.level().getEntitiesOfClass(LivingEntity.class, entity.getBoundingBox().inflate(radius)).forEach(target -> {
                if (target != entity) {
                    target.addEffect(new net.minecraft.world.effect.MobEffectInstance(net.minecraft.world.effect.MobEffects.WEAKNESS, 200, 1));
                    target.hurt(entity.damageSources().dryOut(), 5.0F);
                }
            });

            this.startCooldown(entity, 300);
        }
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.ground_secco");
    }
}
