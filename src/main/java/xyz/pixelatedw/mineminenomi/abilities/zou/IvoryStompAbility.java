package xyz.pixelatedw.mineminenomi.abilities.zou;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;

public class IvoryStompAbility extends Ability {

    private static final int COOLDOWN = 160;
    private static final float DAMAGE = 20.0F;

    public IvoryStompAbility() {
        super(ResourceLocation.fromNamespaceAndPath("mineminenomi", "zou_zou_no_mi"));
    }

    @Override
    protected void startUsing(LivingEntity entity) {
        if (!entity.level().isClientSide) {
             entity.level().getEntitiesOfClass(LivingEntity.class, entity.getBoundingBox().inflate(3.0).move(entity.getLookAngle())).forEach(target -> {
                 if (target != entity) {
                     if (target.hurt(entity.damageSources().mobAttack(entity), DAMAGE)) {
                         Vec3 speed = entity.getLookAngle().multiply(2.5, 1.0, 2.5);
                         AbilityHelper.setDeltaMovement(target, speed.x, entity.getDeltaMovement().y + 0.1, speed.z);
                     }
                 }
             });

             entity.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(),
                net.minecraft.sounds.SoundEvents.PLAYER_ATTACK_STRONG,
                net.minecraft.sounds.SoundSource.PLAYERS, 1.0F, 1.0F);

             this.startCooldown(entity, COOLDOWN);
             this.stop(entity);
        }
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.ivory_stomp");
    }
}
