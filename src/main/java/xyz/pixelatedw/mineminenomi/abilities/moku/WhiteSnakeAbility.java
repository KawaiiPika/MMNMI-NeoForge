package xyz.pixelatedw.mineminenomi.abilities.moku;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.entities.projectiles.WhiteSnakeEntity;

/** White Snake — launches a massive rope of smoke that pulls enemies in. */
public class WhiteSnakeAbility extends Ability {
    private static final ResourceLocation FRUIT = ResourceLocation.fromNamespaceAndPath("mineminenomi", "moku_moku_no_mi");

    public WhiteSnakeAbility() {
        super(FRUIT);
    }

    @Override
    protected void startUsing(LivingEntity entity) {
<<<<<<< HEAD
        Vec3 look = entity.getLookAngle();
        for (var target : entity.level().getEntities(entity, entity.getBoundingBox().inflate(4.0).move(look.scale(8.0)))) {
            if (target instanceof LivingEntity living) {
                Vec3 pull = entity.position().subtract(living.position()).normalize().scale(2.5);
                living.setDeltaMovement(pull.x, pull.y + 0.3, pull.z);
                living.hurt(entity.damageSources().mobAttack(entity), 5.0F);
                living.addEffect(new net.minecraft.world.effect.MobEffectInstance(
                    net.minecraft.world.effect.MobEffects.MOVEMENT_SLOWDOWN, 60, 2));
                living.hurtMarked = true;
            }
=======
        if (!entity.level().isClientSide) {
            Vec3 look = entity.getLookAngle();
            WhiteSnakeEntity projectile = new WhiteSnakeEntity(entity.level(), entity, look.scale(0.5));
            projectile.setPos(entity.getX(), entity.getEyeY(), entity.getZ());
            entity.level().addFreshEntity(projectile);

            entity.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(),
                xyz.pixelatedw.mineminenomi.init.ModSounds.MOKU_SFX.get(),
                net.minecraft.sounds.SoundSource.PLAYERS, 1.5F, 1.0F);

            this.startCooldown(entity, 150);
>>>>>>> 1ee21eb0 (WIP: Pre-merge stash)
        }
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.white_snake");
    }
}
