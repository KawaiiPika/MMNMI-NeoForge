package xyz.pixelatedw.mineminenomi.abilities.zushi;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.AABB;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Blocks;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.List;

public class JigokuTabiAbility extends Ability {

    private int force = 4;
    private long lastDamageTick = 0;

    public JigokuTabiAbility() {
        super(ResourceLocation.fromNamespaceAndPath("mineminenomi", "zushi_zushi_no_mi"));
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.jigoku_tabi");
    }

    @Override
    protected void startUsing(LivingEntity entity) {
        this.force = 4;
        this.lastDamageTick = entity.level().getGameTime();
    }

    @Override
    protected void onTick(LivingEntity entity, long duration) {
        if (!entity.level().isClientSide() && entity.level() instanceof ServerLevel serverLevel) {
            long gameTime = serverLevel.getGameTime();
            AABB aabb = entity.getBoundingBox().inflate(24.0D);
            List<LivingEntity> targets = serverLevel.getEntitiesOfClass(LivingEntity.class, aabb, e -> e != entity && e.isAlive());

            DamageSource source = entity.damageSources().mobAttack(entity);

            for (LivingEntity target : targets) {
                target.setDeltaMovement(0.0D, target.getDeltaMovement().y - 4.0D, 0.0D);

                if (gameTime - this.lastDamageTick >= 20) {
                    target.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 25, 5, false, false));
                    target.hurt(source, this.force * 2.0F);

                    int r = this.force;
                    BlockPos center = target.blockPosition();
                    serverLevel.getServer().execute(() -> {
                        for (int x = -r; x <= r; x++) {
                            for (int y = -r; y <= r; y++) {
                                for (int z = -r; z <= r; z++) {
                                    if (x*x + y*y + z*z <= r*r) {
                                        BlockPos targetPos = center.offset(x, y, z);
                                        if (!serverLevel.getBlockState(targetPos).isAir() && serverLevel.getBlockState(targetPos).getDestroySpeed(serverLevel, targetPos) >= 0) {
                                            serverLevel.setBlock(targetPos, Blocks.AIR.defaultBlockState(), 3);
                                        }
                                    }
                                }
                            }
                        }
                    });
                }
            }

            if (gameTime - this.lastDamageTick >= 20) {
                this.lastDamageTick = gameTime;
            }

            if (duration % 60 == 0) {
                this.force++;
            }

            if (duration >= 120) {
                this.stop(entity);
            }
        }
    }

    @Override
    public void stop(LivingEntity entity) {
        super.stop(entity);
        startCooldown(entity, 100 + getDuration(entity) * 2);
    }
}
