package xyz.pixelatedw.mineminenomi.api.quests.objectives;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public interface IKillEntityObjective {
    boolean checkKill(Player player, LivingEntity victim, DamageSource source);
}
