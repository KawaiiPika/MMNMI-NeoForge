package xyz.pixelatedw.mineminenomi.abilities.toriphoenix;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.TargetHelper;
import xyz.pixelatedw.mineminenomi.init.ModParticleTypes;
import java.util.List;
import net.minecraft.core.particles.ParticleOptions;
import xyz.pixelatedw.mineminenomi.api.util.Result;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityUseConditions;

public class TenseiNoSoenAbility extends Ability {

    private static final int CHARGE_TIME = 60;
    private static final int COOLDOWN = 600;
    private static final int RANGE = 30;
    private static final float DAMAGE = 50.0F;

    public TenseiNoSoenAbility() {
        super(ResourceLocation.fromNamespaceAndPath("mineminenomi", "tori_tori_no_mi_model_1"));
    }

    @Override
    public Result canUse(LivingEntity entity) {
        Result baseResult = super.canUse(entity);
        if (baseResult.isFail()) return baseResult;
        return AbilityUseConditions.requiresInAir(entity);
    }

    @Override
    protected void startUsing(LivingEntity entity) {
    }

    @Override
    protected void onTick(LivingEntity entity, long duration) {
        boolean isPlummeting = duration >= CHARGE_TIME;
        if (!isPlummeting) {
            if (entity.level().isClientSide && duration % 2 == 0) {
                spawnChargeParticles(entity);
            }
        } else {
            if (entity.onGround() || AbilityHelper.getDifferenceToFloor(entity) < 2.0) {
                if (entity.level().isClientSide) {
                    spawnSlamParticles(entity);
                } else {
                    List<LivingEntity> targets = TargetHelper.getEntitiesInArea(entity.level(), entity, RANGE, LivingEntity.class);
                    for (LivingEntity target : targets) {
                        target.hurt(entity.damageSources().mobAttack(entity), DAMAGE);
                    }
                    this.startCooldown(entity, COOLDOWN);
                }
                this.stop(entity);
            } else {
                entity.setDeltaMovement(entity.getDeltaMovement().x, -10.0, entity.getDeltaMovement().z);
                entity.fallDistance = 0.0F;
            }
        }
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.tenseinosoen");
    }

    private void spawnChargeParticles(LivingEntity entity) {
        for (int i = 0; i < 5; i++) {
            entity.level().addParticle((ParticleOptions) (Object) ModParticleTypes.BLUE_FLAME.get(), entity.getX() + (entity.getRandom().nextDouble() - 0.5) * 2, entity.getY() + entity.getRandom().nextDouble() * 2, entity.getZ() + (entity.getRandom().nextDouble() - 0.5) * 2, 0, 0.02, 0);
        }
    }

    private void spawnSlamParticles(LivingEntity entity) {
        for (int i = 0; i < 50; i++) {
            double angle = 2 * Math.PI * entity.getRandom().nextDouble();
            double radius = entity.getRandom().nextDouble() * 3;
            double x = radius * Math.cos(angle);
            double z = radius * Math.sin(angle);
            entity.level().addParticle((ParticleOptions) (Object) ModParticleTypes.BLUE_FLAME.get(), entity.getX() + x, entity.getY() + 0.5, entity.getZ() + z, x / 4.0, 0.1, z / 4.0);
        }
    }
}
