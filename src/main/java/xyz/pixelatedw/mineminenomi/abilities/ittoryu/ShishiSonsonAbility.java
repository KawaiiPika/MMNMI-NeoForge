package xyz.pixelatedw.mineminenomi.abilities.ittoryu;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.data.entity.PlayerStats;

import java.util.List;

public class ShishiSonsonAbility extends Ability {

    private static final float COOLDOWN = 140.0F;
    private static final float DAMAGE = 10.0F;
    private static final float RANGE = 10.0F;

    @Override
    protected void startUsing(LivingEntity entity) {
        if (!entity.level().isClientSide) {
            Vec3 look = entity.getLookAngle();
            Vec3 targetPos = entity.position().add(look.scale(RANGE));

            // Simplified dash: just move the entity
            entity.setDeltaMovement(look.scale(1.5));

            // Damage entities along the path
            List<LivingEntity> targets = entity.level().getEntitiesOfClass(LivingEntity.class, entity.getBoundingBox().inflate(RANGE, 2, RANGE), e -> e != entity);
            for (LivingEntity target : targets) {
                target.hurt(entity.damageSources().mobAttack(entity), DAMAGE);
            }

            PlayerStats stats = PlayerStats.get(entity);
            if (stats != null && getAbilityId() != null) {
                stats.setAbilityCooldown(getAbilityId().toString(), (int) COOLDOWN, entity.level().getGameTime());
            }
        }
    }

    @Override
    public Component getDisplayName() {
        return Component.literal("Shishi Sonson");
    }
}
