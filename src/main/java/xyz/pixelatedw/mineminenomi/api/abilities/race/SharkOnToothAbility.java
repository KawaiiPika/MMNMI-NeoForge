package xyz.pixelatedw.mineminenomi.api.abilities.race;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

import java.util.List;

public class SharkOnToothAbility extends Ability {

    private static final int DASH_TICKS = 15;
    private boolean hitTarget = false;

    public SharkOnToothAbility() {
        super(ResourceLocation.fromNamespaceAndPath("mineminenomi", "fishman_karate"));
    }

    @Override
    protected void startUsing(LivingEntity entity) {
        this.hitTarget = false;
        entity.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(), 
            net.minecraft.sounds.SoundEvents.PLAYER_SPLASH_HIGH_SPEED, 
            net.minecraft.sounds.SoundSource.PLAYERS, 1.0F, 1.0F);
    }

    @Override
    protected void onTick(LivingEntity entity, long duration) {
        if (entity.isAlive()) {
            Vec3 look = entity.getLookAngle();
            Vec3 speed = new Vec3(look.x * 2.3, 0, look.z * 2.3);
            entity.move(MoverType.SELF, speed);
            
            if (!this.hitTarget && !entity.level().isClientSide) {
                List<LivingEntity> targets = entity.level().getEntitiesOfClass(LivingEntity.class, entity.getBoundingBox().inflate(1.5), e -> e != entity && e.isAlive());
                if (!targets.isEmpty()) {
                    LivingEntity target = targets.get(0);
                    target.hurt(entity.damageSources().mobAttack(entity), 40.0F);
                    this.hitTarget = true;
                }
            }
        }

        if (duration >= DASH_TICKS) {
            this.startCooldown(entity, 300);
            this.stop(entity);
        }
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.shark_on_tooth");
    }
}
