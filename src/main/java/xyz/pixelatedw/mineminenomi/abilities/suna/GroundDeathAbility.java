package xyz.pixelatedw.mineminenomi.abilities.suna;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

/** Ground Death — Crocodile's moisture-draining grab & AoE. Heavily damages target and turns area to sand. */
public class GroundDeathAbility extends Ability {
    private static final ResourceLocation FRUIT = ResourceLocation.fromNamespaceAndPath("mineminenomi", "suna_suna_no_mi");
    public GroundDeathAbility() { super(FRUIT); }

    @Override
    protected void startUsing(LivingEntity entity) {
        if (!entity.level().isClientSide) {
            Vec3 look = entity.getLookAngle();
            for (var target : entity.level().getEntities(entity, entity.getBoundingBox().inflate(2.0).move(look.scale(3.0)))) {
                if (target instanceof LivingEntity living) {
                    // Moisture drain — massive single-target damage
                    living.hurt(entity.damageSources().dryOut(), 40.0F);
                    living.addEffect(new net.minecraft.world.effect.MobEffectInstance(
                        net.minecraft.world.effect.MobEffects.WEAKNESS, 200, 3));
                    living.hurtMarked = true;
                    break;
                }
            }

            // AoE terrain degradation
            int radius = 8;
            BlockPos center = entity.blockPosition();
            BlockPos.betweenClosedStream(center.offset(-radius, -2, -radius), center.offset(radius, 2, radius))
                .forEach(pos -> {
                    if (pos.distSqr(center) <= radius * radius) {
                        net.minecraft.world.level.block.state.BlockState state = entity.level().getBlockState(pos);
                        if (state.is(Blocks.DIRT) || state.is(Blocks.GRASS_BLOCK) || state.is(Blocks.STONE)) {
                            entity.level().setBlockAndUpdate(pos, Blocks.SAND.defaultBlockState());
                        } else if (state.is(Blocks.WATER)) {
                            entity.level().setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
                        }
                    }
                });

            this.startCooldown(entity, 400);
        }
    }

    @Override
    public Component getDisplayName() { return Component.translatable("ability.mineminenomi.ground_death"); }
}
