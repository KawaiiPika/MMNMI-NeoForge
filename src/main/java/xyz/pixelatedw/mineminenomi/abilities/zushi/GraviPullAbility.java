package xyz.pixelatedw.mineminenomi.abilities.zushi;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;

public class GraviPullAbility extends Ability {
    private static final ResourceLocation FRUIT = ResourceLocation.fromNamespaceAndPath("mineminenomi", "zushi_zushi_no_mi");

    public GraviPullAbility() {
        super(FRUIT);
    }

    @Override
    protected void startUsing(LivingEntity entity) {
        if (entity.level() instanceof net.minecraft.server.level.ServerLevel serverLevel) {
            for(net.minecraft.server.level.ServerPlayer p : serverLevel.players()) serverLevel.sendParticles(p, new xyz.pixelatedw.mineminenomi.particles.SimpleParticleData(xyz.pixelatedw.mineminenomi.init.ModParticleTypes.GRAVI_PULL_1.get()), true, entity.getX(), entity.getY(), entity.getZ(), 1, 0.0D, 0.0D, 0.0D, 0.0D);
        }
    }

    @Override
    protected void onTick(LivingEntity entity, long duration) {
        if (duration >= 60) {
            if (entity.level() instanceof net.minecraft.server.level.ServerLevel serverLevel) {
                for(net.minecraft.server.level.ServerPlayer p : serverLevel.players()) serverLevel.sendParticles(p, new xyz.pixelatedw.mineminenomi.particles.SimpleParticleData(xyz.pixelatedw.mineminenomi.init.ModParticleTypes.GRAVI_PULL_2.get()), true, entity.getX(), entity.getY(), entity.getZ(), 1, 0.0D, 0.0D, 0.0D, 0.0D);
            }

            for(LivingEntity target : entity.level().getEntitiesOfClass(LivingEntity.class, entity.getBoundingBox().inflate(16.0D), e -> e != entity)) {
                double offsetX = entity.getX() - target.getX();
                double offsetZ = entity.getZ() - target.getZ();
                AbilityHelper.setDeltaMovement(target, offsetX / 2.0D, (entity.getY() - target.getY()) / 4.0D, offsetZ / 2.0D);
            }

            this.stop(entity);
        }
    }

    @Override
    protected void stopUsing(LivingEntity entity) {
        this.startCooldown(entity, 340);
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.gravi_pull");
    }
}
