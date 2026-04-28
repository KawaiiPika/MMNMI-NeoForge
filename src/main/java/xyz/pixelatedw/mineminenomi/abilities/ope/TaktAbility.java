package xyz.pixelatedw.mineminenomi.abilities.ope;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

public class TaktAbility extends Ability {

    private static final int MAX_DURATION = 60;
    private java.util.List<LivingEntity> grabbed = new java.util.ArrayList<>();

    public TaktAbility() {
        super(ResourceLocation.fromNamespaceAndPath("mineminenomi", "ope_ope_no_mi"));
    }

    @Override
    protected void startUsing(LivingEntity entity) {
        if (!RoomAbility.isEntityInRoom(entity)) {
            if (entity instanceof net.minecraft.world.entity.player.Player player) {
                player.displayClientMessage(Component.translatable("ability.mineminenomi.message.only_in_room"), true);
            }
            return;
        }

        if (!entity.level().isClientSide) {
            grabbed.clear();
            net.minecraft.world.phys.HitResult mop = xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper.pickEntity(entity, 15.0);
            if (mop != null && mop.getType() == net.minecraft.world.phys.HitResult.Type.ENTITY) {
                net.minecraft.world.entity.Entity target = ((net.minecraft.world.phys.EntityHitResult) mop).getEntity();
                if (target instanceof LivingEntity living && RoomAbility.isEntityInRoom(living)) {
                    grabbed.add(living);
                }
            } else {
                // AoE grab nearby
                net.minecraft.world.phys.Vec3 hitPos = entity.pick(15.0, 0, false).getLocation();
                entity.level().getEntitiesOfClass(LivingEntity.class, new net.minecraft.world.phys.AABB(hitPos, hitPos).inflate(3.0)).forEach(target -> {
                    if (target != entity && RoomAbility.isEntityInRoom(target)) {
                        grabbed.add(target);
                    }
                });
            }

            if (grabbed.isEmpty()) {
                this.stop(entity);
            }
        }
    }

    @Override
    protected void onTick(LivingEntity entity, long duration) {
        if (!RoomAbility.isEntityInRoom(entity) || grabbed.isEmpty() || duration >= MAX_DURATION) {
            this.stop(entity);
            this.startCooldown(entity, 240);
            return;
        }

        if (!entity.level().isClientSide) {
            Vec3 lookVec = entity.getLookAngle();
            Vec3 pos = entity.position().add(0, entity.getEyeHeight() / 2, 0).add(lookVec.scale(8.0));

            grabbed.removeIf(target -> !target.isAlive() || !RoomAbility.isEntityInRoom(target));

            for (LivingEntity target : grabbed) {
                Vec3 movement = pos.subtract(target.position()).scale(0.5); // Move towards target position smoothly
                target.setDeltaMovement(movement);
                target.resetFallDistance();
                target.hurtMarked = true;
            }
        }
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.takt");
    }
}
