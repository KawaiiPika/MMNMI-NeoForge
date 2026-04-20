package xyz.pixelatedw.mineminenomi.abilities.toriphoenix;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityUseConditions;
import xyz.pixelatedw.mineminenomi.api.util.Result;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.toriphoenix.PhoenixGoenProjectile;

public class PhoenixGoenAbility extends Ability {

    private static final int COOLDOWN = 160;
    private static final int TRIGGERS = 5;
    private static final int INTERVAL = 4;
    private static final int ACTIVE_WINDOW = TRIGGERS * INTERVAL;

    public PhoenixGoenAbility() {
        super(ResourceLocation.fromNamespaceAndPath("mineminenomi", "tori_tori_no_mi_model_1"));
    }

    @Override
    public Result canUse(LivingEntity entity) {
        Result superResult = super.canUse(entity);
        if (superResult.isFail()) {
            return superResult;
        }
        return AbilityUseConditions.requiresInAir(entity);
    }

    @Override
    protected void startUsing(LivingEntity entity) {
        // Start ticks
    }

    @Override
    protected void onTick(LivingEntity entity, long duration) {
        if (duration < ACTIVE_WINDOW) {
            if (!entity.level().isClientSide && duration % INTERVAL == 0) {
                Vec3 lookVec = entity.getLookAngle();
                PhoenixGoenProjectile proj = new PhoenixGoenProjectile(entity.level(), entity, lookVec);
                proj.shootFromRotation(entity, entity.getXRot(), entity.getYRot(), 0.0F, 2.5F, 5.0F);
                entity.level().addFreshEntity(proj);
            }
        } else {
            this.startCooldown(entity, COOLDOWN);
            this.stop(entity);
        }
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.phoenixgoen");
    }
}
