package xyz.pixelatedw.mineminenomi.abilities.bane;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.data.entity.PlayerStats;
import xyz.pixelatedw.mineminenomi.init.ModSounds;

public class SpringHopperAbility extends Ability {

    public SpringHopperAbility() {
        super(ResourceLocation.fromNamespaceAndPath("mineminenomi", "bane_bane_no_mi"));
    }

    @Override
    protected void startUsing(LivingEntity entity) {
        // Continuous usage handled in onTick
    }

    @Override
    protected void stopUsing(LivingEntity entity) {
        PlayerStats stats = PlayerStats.get(entity);
        if (stats != null) {
            stats.setAbilityState("spring_hopper_jump_power", 0);
            stats.setAbilityState("spring_hopper_can_increase", 0);
            stats.setAbilityState("spring_hopper_falling", 0);
        }
        this.startCooldown(entity, 200);
    }

    @Override
    public void onTick(LivingEntity entity, long duration) {
        PlayerStats stats = PlayerStats.get(entity);
        if (stats == null) return;

        int jumpPower = stats.getAbilityState("spring_hopper_jump_power");
        boolean canIncreaseJumpPower = stats.getAbilityState("spring_hopper_can_increase") == 1;
        boolean startedFalling = stats.getAbilityState("spring_hopper_falling") == 1;
        boolean updated = false;

        if (entity.horizontalCollision && jumpPower > 2) {
            Vec3 speed = entity.getLookAngle().multiply(-2.0, -2.0, -2.0);
            entity.push(speed.x, speed.y, speed.z);
            if (jumpPower < 9) {
                jumpPower++;
                updated = true;
            }
            if (!entity.level().isClientSide && entity instanceof Player player) {
                entity.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(), ModSounds.SPRING_SFX.get(), SoundSource.PLAYERS, 1.0F, Mth.clamp(entity.getRandom().nextFloat() + 0.3F, 0.5F, 1.5F));
            }
        }

        if (entity.onGround()) {
            if (!startedFalling) {
                startedFalling = true;
                updated = true;
            }

            // Heuristic for jumping: upward movement when on ground means jump started
            boolean isJumping = entity.getDeltaMovement().y > 0.0;

            if (isJumping) {
                if (jumpPower > 3) {
                    Vec3 speed = entity.getLookAngle().multiply(0.25 + jumpPower * 0.25, 1.0, 0.25 + jumpPower * 0.25);
                    entity.push(speed.x, 0, speed.z);
                }
                if (jumpPower < 9 && canIncreaseJumpPower) {
                    jumpPower++;
                    updated = true;
                }

                if (entity instanceof Player player) {
                    player.setIgnoreFallDamageFromCurrentImpulse(true);
                    if (!entity.level().isClientSide) {
                        entity.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(), ModSounds.SPRING_SFX.get(), SoundSource.PLAYERS, 0.3F, Mth.clamp(entity.getRandom().nextFloat() + 0.3F, 0.8F, 1.5F));
                    }
                }

                canIncreaseJumpPower = true;
                updated = true;
            } else {
                jumpPower = 0;
                updated = true;
            }
        } else {
            if (entity.verticalCollision && jumpPower > 0) {
                jumpPower--;
                updated = true;
            }

            if (entity.getDeltaMovement().y < 0.0) {
                if (startedFalling) {
                    canIncreaseJumpPower = AbilityHelper.getDifferenceToFloor(entity) > jumpPower;
                    startedFalling = false;
                    updated = true;
                }

                if (jumpPower > 3) {
                    entity.setDeltaMovement(entity.getDeltaMovement().scale(1.15));
                }
            }
        }

        if (updated) {
            stats.setAbilityState("spring_hopper_jump_power", jumpPower);
            stats.setAbilityState("spring_hopper_can_increase", canIncreaseJumpPower ? 1 : 0);
            stats.setAbilityState("spring_hopper_falling", startedFalling ? 1 : 0);
            stats.sync(entity);
        }
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.spring_hopper");
    }
}
