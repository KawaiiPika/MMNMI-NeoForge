package xyz.pixelatedw.mineminenomi.abilities.hasshoken;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;


public class KiryuKirikugiAbility extends Ability {

    private static final long COOLDOWN = 700L;
    private static final float DAMAGE = 60.0F;

    private boolean isAwaitingHit = false;

    @Override
    protected void startUsing(LivingEntity entity) {
        if (!this.isUsing(entity)) {
            this.isAwaitingHit = true;
            // Provide a visual buff indicator here
        } else {
            this.stop(entity);
        }
    }

    public void onSwing(LivingEntity entity) {
        if (this.isUsing(entity) && this.isAwaitingHit) {
            if (entity.level() instanceof ServerLevel serverLevel) {
                net.minecraft.world.entity.projectile.Arrow projectile = new net.minecraft.world.entity.projectile.Arrow(serverLevel, entity, new net.minecraft.world.item.ItemStack(net.minecraft.world.item.Items.ARROW), null);
                // projectile.shootFromRotation(entity, entity.getXRot(), entity.getYRot(), 0.0F, 3.0F, 0.0F);
                projectile.shootFromRotation(entity, entity.getXRot(), entity.getYRot(), 0.0F, 3.0F, 0.0F); serverLevel.addFreshEntity(projectile);
            }

            this.isAwaitingHit = false;
            this.stop(entity);
            this.startCooldown(entity, COOLDOWN);
        }
    }

    public float getDamage() {
        return DAMAGE;
    }

    @Override
    public Component getDisplayName() {
        return Component.literal("Kiryu Kirikugi");
    }
}
