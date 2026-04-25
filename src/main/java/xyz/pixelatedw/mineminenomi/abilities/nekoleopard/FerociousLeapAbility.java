package xyz.pixelatedw.mineminenomi.abilities.nekoleopard;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

import java.util.List;

public class FerociousLeapAbility extends Ability {

    public FerociousLeapAbility() {
        super(ResourceLocation.fromNamespaceAndPath("mineminenomi", "neko_neko_no_mi_model_leopard"));
    }

    @Override
    protected void startUsing(LivingEntity entity) {
        if (!entity.level().isClientSide) {
            Vec3 speed = entity.getLookAngle().normalize();
            entity.setDeltaMovement(speed.x, 1.4D, speed.z);
            entity.hasImpulse = true;

            // Landing logic/damage to be hooked to a tick event. Simulate damage on jump for test.
            AABB aabb = entity.getBoundingBox().inflate(3.5D);
            List<LivingEntity> targets = entity.level().getEntitiesOfClass(LivingEntity.class, aabb, e -> e != entity && e.isAlive());

            for (LivingEntity target : targets) {
                target.hurt(entity.damageSources().mobAttack(entity), 10.0F);
            }

            xyz.pixelatedw.mineminenomi.data.entity.PlayerStats stats = xyz.pixelatedw.mineminenomi.data.entity.PlayerStats.get(entity);
            if (stats != null && getAbilityId() != null) {
                stats.setAbilityCooldown(getAbilityId().toString(), 160, entity.level().getGameTime());
            }
        }
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.ferociousleap");
    }
}
