package xyz.pixelatedw.mineminenomi.abilities.bara;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.data.entity.PlayerStats;

public class BaraBaraCarAbility extends Ability {
    private static final ResourceLocation FRUIT = ResourceLocation.fromNamespaceAndPath("mineminenomi", "bara_bara_no_mi");

    public BaraBaraCarAbility() {
        super(FRUIT);
    }

    @Override
    public boolean isUsing(LivingEntity entity) {
        PlayerStats stats = PlayerStats.get(entity);
        return stats != null && stats.isAbilityActive(this.getId().toString());
    }

    @Override
    protected void startUsing(LivingEntity entity) {
        PlayerStats stats = PlayerStats.get(entity);
        if (stats != null) {
            stats.setAbilityActive(this.getId().toString(), true);
            stats.sync(entity);
            entity.addEffect(new net.minecraft.world.effect.MobEffectInstance(net.minecraft.world.effect.MobEffects.MOVEMENT_SPEED, 99999, 4, false, false, false));
        }
    }

    @Override
    protected void stopUsing(LivingEntity entity) {
        PlayerStats stats = PlayerStats.get(entity);
        if (stats != null) {
            stats.setAbilityActive(this.getId().toString(), false);
            stats.sync(entity);
            entity.removeEffect(net.minecraft.world.effect.MobEffects.MOVEMENT_SPEED);
        }
        this.startCooldown(entity, 300);
    }

    @Override
    protected void onTick(LivingEntity entity, long duration) {
        if (!entity.level().isClientSide) {
            Vec3 look = entity.getLookAngle();
            entity.level().getEntitiesOfClass(LivingEntity.class, entity.getBoundingBox().inflate(1.5)).forEach(target -> {
                if (target != entity) {
                    target.hurt(entity.damageSources().mobAttack(entity), 5.0F);
                    target.setDeltaMovement(look.scale(1.5).add(0, 0.4, 0));
                }
            });
        }
    }

    private ResourceLocation getId() {
        return ResourceLocation.fromNamespaceAndPath("mineminenomi", "bara_bara_car");
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.bara_bara_car");
    }
}
