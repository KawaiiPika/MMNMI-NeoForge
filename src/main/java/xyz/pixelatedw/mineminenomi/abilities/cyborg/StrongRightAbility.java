package xyz.pixelatedw.mineminenomi.abilities.cyborg;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.cyborg.StrongRightProjectile;
import xyz.pixelatedw.mineminenomi.init.ModEffects;

public class StrongRightAbility extends Ability {
    private StrongRightProjectile projectile;
    private boolean isShot = false;

    @Override
    protected void startUsing(LivingEntity entity) {
        if (!entity.hasEffect(net.minecraft.world.effect.MobEffects.WEAKNESS)) {
            if (entity.level() instanceof ServerLevel serverLevel) {
                this.projectile = new StrongRightProjectile(serverLevel, entity);
                this.projectile.shootFromRotation(entity, entity.getXRot(), entity.getYRot(), 0.0F, 3.0F, 0.0F);
                serverLevel.addFreshEntity(this.projectile);
                this.isShot = true;
                entity.addEffect(new MobEffectInstance(net.minecraft.world.effect.MobEffects.WEAKNESS, 200, 0, false, false));
                this.startCooldown(entity, 100L);
            }
        }
    }
    @Override
    public void onTick(LivingEntity entity, long duration) {
        if (this.isShot && this.projectile != null && !this.projectile.isAlive()) {
            this.projectile = null;
            entity.removeEffect(net.minecraft.world.effect.MobEffects.WEAKNESS);
            this.isShot = false;
            this.stop(entity);
        }
    }
    @Override
    public Component getDisplayName() { return Component.literal("Strong Right"); }
}
