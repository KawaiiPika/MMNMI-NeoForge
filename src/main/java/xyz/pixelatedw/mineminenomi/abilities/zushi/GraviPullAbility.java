package xyz.pixelatedw.mineminenomi.abilities.zushi;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

public class GraviPullAbility extends Ability {

    public GraviPullAbility() {
        super(ResourceLocation.fromNamespaceAndPath("mineminenomi", "zushi_zushi_no_mi"));
    }

    @Override
    protected void startUsing(LivingEntity entity) {
        // Handled in onTick
    }

    @Override
    public void onTick(LivingEntity entity, long duration) {
        if (duration == 1) {
            if (!entity.level().isClientSide) {
                // TODO: Spawn ModParticleEffects.GRAVI_PULL_1 using Level#sendParticles
            }
        }

        if (duration < 60) {
            // Charging phase
        } else if (duration == 60) {
            if (!entity.level().isClientSide) {
                // TODO: Spawn ModParticleEffects.GRAVI_PULL_2 using Level#sendParticles
                var aabb = entity.getBoundingBox().inflate(16.0);
                for (LivingEntity target : entity.level().getEntitiesOfClass(LivingEntity.class, aabb, e -> e != entity)) {
                    double offsetX = entity.getX() - target.getX();
                    double offsetZ = entity.getZ() - target.getZ();
                    target.push(offsetX / 2.0, (entity.getY() - target.getY()) / 4.0, offsetZ / 2.0);
                }
            }
            this.stop(entity);
            this.startCooldown(entity, 340);
        }
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.gravi_pull");
    }
}
