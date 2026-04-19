package xyz.pixelatedw.mineminenomi.quests.objectives;

import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import xyz.pixelatedw.mineminenomi.api.quests.Quest;

public class TimedHitEntityObjective extends HitEntityObjective {
    private long lastHit;
    private int seconds;

    public TimedHitEntityObjective(Quest parent, Component title, int count, int seconds) {
        this(parent, title, count, seconds, DEFAULT_CHECK);
    }

    public TimedHitEntityObjective(Quest parent, Component title, int count, int seconds, HitEntityObjective.ICheckHit check) {
        super(parent, title, count, check);
        this.lastHit = 0L;
        this.seconds = seconds * 20; // Convert to ticks
    }

    @Override
    public boolean checkHit(Player player, LivingEntity target, DamageSource source, float amount) {
        if (this.lastHit == 0L) {
            this.lastHit = player.level().getGameTime();
        }

        long hitTime = player.level().getGameTime();
        if (hitTime - (long) this.seconds <= this.lastHit) {
            this.lastHit = hitTime;
            return super.checkHit(player, target, source, amount);
        } else {
            this.setProgress(player, 0.0F, false);
            this.lastHit = hitTime; // Reset lastHit to current to start new streak
            return true;
        }
    }
}
