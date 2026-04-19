package xyz.pixelatedw.mineminenomi.api.abilities;

import java.util.List;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

public interface IDescriptiveAbility {
   void appendDescription(Player var1, List<Component> var2);
}
