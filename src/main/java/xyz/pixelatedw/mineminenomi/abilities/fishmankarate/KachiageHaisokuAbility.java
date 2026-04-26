package xyz.pixelatedw.mineminenomi.abilities.fishmankarate;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

public class KachiageHaisokuAbility extends Ability {

    private static final long COOLDOWN = 160L;
    private static final float DAMAGE = 20.0F;
    private static final float WATER_DAMAGE_MUL = 2.0F;

    private boolean isAwaitingHit = false;

    @Override
    protected void startUsing(LivingEntity entity) {
        if (!this.isUsing(entity)) {
            this.isAwaitingHit = true;
        } else {
            this.stop(entity);
        }
    }

    public void onSwing(LivingEntity entity) {
        if (this.isUsing(entity) && this.isAwaitingHit) {
            this.isAwaitingHit = false;
            this.stop(entity);
            this.startCooldown(entity, COOLDOWN);
        }
    }

    public float getDamage(LivingEntity entity) {
        float damage = DAMAGE;
        if (FishmanKarateHelper.isInWater(entity)) {
            damage *= WATER_DAMAGE_MUL;
        }
        return damage;
    }

    @Override
    public Component getDisplayName() {
        return Component.literal("Kachiage Haisoku");
    }
}
