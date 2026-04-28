package xyz.pixelatedw.mineminenomi.abilities.zushi;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import java.util.List;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;

public class GraviPullAbility extends Ability {
    private static final int COOLDOWN = 340;
    private static final int CHARGE_TIME = 60;
    private static final int RANGE = 16;

    public GraviPullAbility() {
        super(ResourceLocation.fromNamespaceAndPath("mineminenomi", "zushi_zushi_no_mi"));
    }

    @Override
    protected void startUsing(LivingEntity entity) {
    }

    @Override
    protected void onTick(LivingEntity entity, long duration) {
        if (!entity.level().isClientSide && duration >= CHARGE_TIME) {
            List<LivingEntity> targets = entity.level().getEntitiesOfClass(LivingEntity.class, entity.getBoundingBox().inflate(RANGE), target -> target != entity && target.isAlive());
            for (LivingEntity target : targets) {
                if (target.distanceToSqr(entity) <= RANGE * RANGE) {
                    double offsetX = entity.getX() - target.getX();
                    double offsetZ = entity.getZ() - target.getZ();
                    AbilityHelper.setDeltaMovement(target, offsetX / 2.0D, (entity.getY() - target.getY()) / 4.0D, offsetZ / 2.0D);
                }
            }

            this.stop(entity);
            this.startCooldown(entity, COOLDOWN);
        }
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.gravi_pull");
    }
}
