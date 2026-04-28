package xyz.pixelatedw.mineminenomi.abilities.zushi;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.core.BlockPos;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.data.entity.PlayerStats;

public class JigokuTabiAbility extends Ability {

    public JigokuTabiAbility() {
        super(ResourceLocation.fromNamespaceAndPath("mineminenomi", "zushi_zushi_no_mi"));
    }

    @Override
    protected void startUsing(LivingEntity entity) {
        PlayerStats stats = PlayerStats.get(entity);
        if (stats != null) {
            stats.setAbilityState("jigoku_force", 4);
        }
    }

    @Override
    protected void stopUsing(LivingEntity entity) {
        this.startCooldown(entity, 340);
    }

    @Override
    public void onTick(LivingEntity entity, long duration) {
        if (duration > 120) {
            this.stop(entity);
            return;
        }

        PlayerStats stats = PlayerStats.get(entity);
        if (stats == null) return;
        int force = stats.getAbilityState("jigoku_force");
        if (force == 0) force = 4;

        if (duration % 60 == 0 && force < 6) {
            force++;
            stats.setAbilityState("jigoku_force", force);
        }

        if (!entity.level().isClientSide) {
            var aabb = entity.getBoundingBox().inflate(24.0);
            for (LivingEntity target : entity.level().getEntitiesOfClass(LivingEntity.class, aabb, e -> e != entity)) {
                target.setDeltaMovement(target.getDeltaMovement().x, target.getDeltaMovement().y - 4.0, target.getDeltaMovement().z);

                if (duration % 20 == 0) {
                    target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 25, 5, false, false));
                    target.hurt(entity.damageSources().mobAttack(entity), force * 2.0F);

                    // Simple crater logic
                    BlockPos center = target.blockPosition();
                    int radius = force;
                    for (int dx = -radius; dx <= radius; dx++) {
                        for (int dy = -2; dy <= 1; dy++) {
                            for (int dz = -radius; dz <= radius; dz++) {
                                if (dx*dx + dy*dy + dz*dz <= radius*radius) {
                                    BlockPos p = center.offset(dx, dy, dz);
                                    if (entity.level().getBlockState(p).isCollisionShapeFullBlock(entity.level(), p) && entity.level().getBlockState(p).getDestroySpeed(entity.level(), p) >= 0) {
                                        entity.level().setBlock(p, Blocks.AIR.defaultBlockState(), 3);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.jigoku_tabi");
    }
}
