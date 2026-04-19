package xyz.pixelatedw.mineminenomi.api.quests.objectives;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;

public interface ICureEffectObjective {
   boolean checkEffect(Player var1, MobEffectInstance var2);
}
