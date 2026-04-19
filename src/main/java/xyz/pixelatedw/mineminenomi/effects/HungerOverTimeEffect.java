package xyz.pixelatedw.mineminenomi.effects;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class HungerOverTimeEffect extends MobEffect {
    private final float baseHunger;
    private final int freq;

    public HungerOverTimeEffect(float baseHunger, int freq) {
        super(MobEffectCategory.HARMFUL, 0x813D2D);
        this.baseHunger = baseHunger;
        this.freq = freq;
    }

    public boolean shouldApplyEffectTick(int duration, int amplifier) {
        return duration % this.freq == 0;
    }

    @Override
    public boolean applyEffectTick(LivingEntity entity, int amplifier) {
        if (entity instanceof Player player) {
            float hunger = this.baseHunger * (amplifier + 1);
            int foodLevel = (int)(player.getFoodData().getFoodLevel() - hunger);
            player.getFoodData().setFoodLevel(Math.max(foodLevel, 0));
        }
        return true;
    }
}
