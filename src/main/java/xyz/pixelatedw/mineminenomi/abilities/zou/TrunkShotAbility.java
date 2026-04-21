package xyz.pixelatedw.mineminenomi.abilities.zou;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

public class TrunkShotAbility extends Ability {

    private static final int COOLDOWN = 160;
    private static final float RANGE = 15.0F;

    public TrunkShotAbility() {
        super(ResourceLocation.fromNamespaceAndPath("mineminenomi", "zou_zou_no_mi"));
    }

    @Override
    protected void startUsing(LivingEntity entity) {
        if (!entity.level().isClientSide) {
             entity.level().getEntitiesOfClass(LivingEntity.class, entity.getBoundingBox().inflate(RANGE).move(entity.getLookAngle().scale(3))).forEach(target -> {
                 if (target != entity) {
                     if (target.hurt(entity.damageSources().mobAttack(entity), 12.0F)) {
                         target.knockback(1.5F, entity.getX() - target.getX(), entity.getZ() - target.getZ());
                     }
                 }
             });

             entity.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(),
                net.minecraft.sounds.SoundEvents.PLAYER_ATTACK_KNOCKBACK,
                net.minecraft.sounds.SoundSource.PLAYERS, 1.0F, 1.0F);

             this.startCooldown(entity, COOLDOWN);
             this.stop(entity);
        }
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.trunk_shot");
    }
}
