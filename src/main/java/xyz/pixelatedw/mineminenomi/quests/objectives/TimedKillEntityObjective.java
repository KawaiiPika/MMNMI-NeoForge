package xyz.pixelatedw.mineminenomi.quests.objectives;

import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import xyz.pixelatedw.mineminenomi.api.quests.Quest;

public class TimedKillEntityObjective extends KillEntityObjective {
    private long firstKill;
    private int ticks;

    public TimedKillEntityObjective(Quest parent, Component titleId, int count, int seconds) {
        this(parent, titleId, count, seconds, (player, target, source) -> true);
    }

    public TimedKillEntityObjective(Quest parent, Component titleId, int count, int seconds, KillEntityObjective.IKillCheck check) {
        super(parent, titleId, count, check);
        this.firstKill = 0L;
        this.ticks = seconds * 20;
    }

    @Override
    public boolean checkKill(Player player, LivingEntity target, DamageSource source) {
        long currentTime = player.level().getGameTime();
        if (this.firstKill != 0L && currentTime > this.firstKill + (long) this.ticks) {
            this.setProgress(player, 0.0F, false);
            this.firstKill = 0L;
        }

        if (this.firstKill == 0L) {
            this.firstKill = currentTime;
        }

        if (currentTime - (long) this.ticks <= this.firstKill) {
            return super.checkKill(player, target, source);
        }
        
        return false;
    }
}
