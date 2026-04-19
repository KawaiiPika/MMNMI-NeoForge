package xyz.pixelatedw.mineminenomi.quests.objectives;

import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import xyz.pixelatedw.mineminenomi.api.quests.Quest;
import xyz.pixelatedw.mineminenomi.api.quests.objectives.IKillEntityObjective;
import xyz.pixelatedw.mineminenomi.api.quests.objectives.Objective;

public class KillEntityObjective extends Objective implements IKillEntityObjective {
    private IKillCheck check;

    public KillEntityObjective(Quest parent, Component titleId, int count, IKillCheck check) {
        super(parent, titleId);
        this.setMaxProgress((float) count);
        this.check = check;
    }

    @Override
    public boolean checkKill(Player player, LivingEntity victim, DamageSource source) {
        return this.check.test(player, victim, source);
    }

    @FunctionalInterface
    public interface IKillCheck {
        boolean test(Player player, LivingEntity victim, DamageSource source);

        default IKillCheck and(IKillCheck other) {
            return (player, victim, source) -> this.test(player, victim, source) && other.test(player, victim, source);
        }
    }
}
