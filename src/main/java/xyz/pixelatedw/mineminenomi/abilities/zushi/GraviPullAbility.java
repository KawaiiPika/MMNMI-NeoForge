package xyz.pixelatedw.mineminenomi.abilities.zushi;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.world.phys.AABB;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

import java.util.List;

public class GraviPullAbility extends Ability {

    public GraviPullAbility() {
        super(ResourceLocation.fromNamespaceAndPath("mineminenomi", "zushi_zushi_no_mi"));
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.gravi_pull");
    }

    @Override
    protected void startUsing(LivingEntity entity) {
        if (!entity.level().isClientSide()) {
            // Simulated End Charge Event directly for now
            AABB aabb = entity.getBoundingBox().inflate(16.0D);
            List<LivingEntity> targets = entity.level().getEntitiesOfClass(LivingEntity.class, aabb, e -> e != entity && e.isAlive());

            for (LivingEntity target : targets) {
                double offsetX = entity.getX() - target.getX();
                double offsetZ = entity.getZ() - target.getZ();
                double offsetY = entity.getY() - target.getY();

                target.setDeltaMovement(offsetX / 2.0D, offsetY / 4.0D, offsetZ / 2.0D);
            }

            startCooldown(entity, 340);
        }
    }
}
