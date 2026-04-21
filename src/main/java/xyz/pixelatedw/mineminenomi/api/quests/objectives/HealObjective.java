package xyz.pixelatedw.mineminenomi.api.quests.objectives;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

public class HealObjective extends Objective {

    public HealObjective(String title, float maxProgress) {
        super(null, Component.literal(title));
        this.setMaxProgress(maxProgress);
    }

    public void onHeal(Player player, float amount) {
        this.alterProgress(player, amount);
    }
}
