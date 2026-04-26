package xyz.pixelatedw.mineminenomi.abilities.ope;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

public class InjectionShotAbility extends Ability {

    private static final int CHARGE_TIME = 20;
    private static final int DASH_TIME = 20;

    public InjectionShotAbility() {
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
    }

    @Override
    protected void onTick(LivingEntity entity, long duration) {
        if (!RoomAbility.isEntityInRoom(entity)) {
            this.stop(entity);
            return;
        }

        if (duration < CHARGE_TIME) {
            entity.addEffect(new net.minecraft.world.effect.MobEffectInstance(net.minecraft.world.effect.MobEffects.MOVEMENT_SLOWDOWN, 2, 0, false, false, false));
        } else if (duration == CHARGE_TIME) {
            Vec3 look = entity.getLookAngle();
            entity.setDeltaMovement(look.x * 3.0, 0.35, look.z * 3.0);
            entity.hurtMarked = true;
        } else if (duration > CHARGE_TIME && duration <= CHARGE_TIME + DASH_TIME) {
            if (!entity.level().isClientSide) {
                entity.level().getEntitiesOfClass(LivingEntity.class, entity.getBoundingBox().inflate(1.6)).forEach(target -> {
                    if (target != entity && RoomAbility.isEntityInRoom(target)) {
                        if (target.hurt(entity.damageSources().mobAttack(entity), 40.0F)) {
                            target.addEffect(new net.minecraft.world.effect.MobEffectInstance(net.minecraft.world.effect.MobEffects.CONFUSION, 60, 0));
                        }
                    }
                });
            }
        } else {
            this.stop(entity);
            this.startCooldown(entity, 200);
        }
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.injection_shot");
    }
}
