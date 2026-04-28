package xyz.pixelatedw.mineminenomi.abilities.kilo;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.util.Mth;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.data.entity.PlayerStats;

public class KiloPress10000Ability extends Ability {

    public KiloPress10000Ability() {
        super(ResourceLocation.fromNamespaceAndPath("mineminenomi", "kilo_kilo_no_mi"));
    }

    @Override
    protected void startUsing(LivingEntity entity) {
        if (!entity.onGround() && !entity.level().isClientSide) {
            PlayerStats stats = PlayerStats.get(entity);
            if (stats != null) {
                stats.setAbilityState("kilo_press_y", (int) entity.getY());
            }
            entity.setDeltaMovement(entity.getDeltaMovement().x, -5.0, entity.getDeltaMovement().z);
        } else {
            this.stop(entity); // must be in air
        }
    }

    @Override
    protected void stopUsing(LivingEntity entity) {
        this.startCooldown(entity, 20); // rough cooldown based on formula
    }

    @Override
    public void onTick(LivingEntity entity, long duration) {
        if (duration > 1200) {
            this.stop(entity);
            return;
        }

        if (entity.onGround()) {
            if (!entity.level().isClientSide) {
                PlayerStats stats = PlayerStats.get(entity);
                if (stats != null) {
                    int initialY = stats.getAbilityState("kilo_press_y");
                    if (initialY > 0 && entity.getY() < initialY) {
                        float damage = Mth.clamp(initialY - (float)entity.getY(), 1.0F, 80.0F);
                        var aabb = entity.getBoundingBox().inflate(5.0);
                        for (LivingEntity target : entity.level().getEntitiesOfClass(LivingEntity.class, aabb, e -> e != entity)) {
                            target.hurt(entity.damageSources().mobAttack(entity), damage);
                        }
                        // TODO: spawn particles
                    }
                }
            }
            this.stop(entity);
        }
    }

    @Override
    public boolean checkInvulnerability(LivingEntity entity, DamageSource source) {
        return this.isUsing(entity) && source.is(net.minecraft.tags.DamageTypeTags.IS_FALL);
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.10000_kilo_press");
    }
}
