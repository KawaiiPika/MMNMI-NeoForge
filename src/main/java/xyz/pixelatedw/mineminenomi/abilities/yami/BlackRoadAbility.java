package xyz.pixelatedw.mineminenomi.abilities.yami;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.yami.BlackRoadProjectile;
import xyz.pixelatedw.mineminenomi.init.ModParticleTypes;

public class BlackRoadAbility extends Ability {
    private static final int COOLDOWN = 400;
    private static final int MAX_CHARGE_TICKS = 60;
    private static final int RANGE = 24;

    public BlackRoadAbility() {
        super(ResourceLocation.fromNamespaceAndPath("mineminenomi", "yami_yami_no_mi"));
    }

    @Override
    protected void startUsing(LivingEntity entity) {}

    @Override
    protected void onTick(LivingEntity entity, long duration) {
        if (duration <= MAX_CHARGE_TICKS) {
            if (entity.level().isClientSide && duration % 2 == 0) {
                entity.level().addParticle(net.minecraft.core.particles.ParticleTypes.SQUID_INK,
                        entity.getX(), entity.getY() + 1, entity.getZ(), 0, 0, 0);
            }
        }

        if (duration == MAX_CHARGE_TICKS) {
            if (!entity.level().isClientSide) {
                this.shootProjectile(entity, 1.0f);
            }
            this.startCooldown(entity, COOLDOWN);
            this.stop(entity);
        }
    }

    private void shootProjectile(LivingEntity entity, float chargePercentage) {
        int range = (int) (RANGE * chargePercentage);
        BlackRoadProjectile proj = new BlackRoadProjectile(entity.level(), entity);
        proj.setLife(range);
        proj.shootFromRotation(entity, entity.getXRot(), entity.getYRot(), 0.0F, 1.1F, 1.0F);
        entity.level().addFreshEntity(proj);
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.black_road");
    }
}
