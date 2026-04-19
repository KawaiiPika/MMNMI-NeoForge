package xyz.pixelatedw.mineminenomi.api.quests.objectives;

import net.minecraft.world.entity.player.Player;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;

public interface IUseAbilityObjective {
   boolean checkAbility(Player var1, IAbility var2);
}
