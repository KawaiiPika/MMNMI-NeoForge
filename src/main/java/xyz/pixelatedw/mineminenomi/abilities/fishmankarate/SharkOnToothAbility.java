package xyz.pixelatedw.mineminenomi.abilities.fishmankarate;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.init.ModSounds;

import java.util.List;

public class SharkOnToothAbility extends Ability {

    private static final long COOLDOWN = 300L;
    private static final long HOLD_TIME = 15L;
    private static final float RANGE = 1.5F;
    private static final float DAMAGE = 40.0F;

    private long continuityStartTime = 0;
    private boolean hitTarget = false;

    @Override
    protected void startUsing(LivingEntity entity) {
        if (!this.isUsing(entity)) {
            this.continuityStartTime = entity.level().getGameTime();
            this.hitTarget = false;
            entity.level().playSound(null, entity.blockPosition(), ModSounds.DASH_ABILITY_SWOOSH_SFX.get(), SoundSource.PLAYERS, 2.0F, 1.0F);
        }
    }

    @Override
    public void onTick(LivingEntity entity, long duration) {
        long currentDuration = entity.level().getGameTime() - this.continuityStartTime;

        if (currentDuration > HOLD_TIME || entity.isDeadOrDying()) {
            this.stop(entity);
            return;
        }

        Vec3 look = entity.getLookAngle();
        Vec3 speed = new Vec3(look.x * 2.3, 0.0, look.z * 2.3);
        entity.move(MoverType.SELF, speed);

        if (!this.hitTarget && entity.level() instanceof ServerLevel serverLevel) {
            AABB area = entity.getBoundingBox().inflate(RANGE);
            List<LivingEntity> targets = serverLevel.getEntitiesOfClass(LivingEntity.class, area);
            targets.remove(entity);

            for (LivingEntity target : targets) {
                if (target.hurt(entity.damageSources().mobAttack(entity), DAMAGE)) {
                    this.hitTarget = true;
                    break;
                }
            }
        }
    }

    @Override
    protected void stopUsing(LivingEntity entity) {
        this.startCooldown(entity, COOLDOWN);
    }

    @Override
    public Component getDisplayName() {
        return Component.literal("Shark on Tooth");
    }
}
