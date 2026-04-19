package xyz.pixelatedw.mineminenomi.api.events.entity;

import java.util.Optional;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.player.PlayerEvent;
import xyz.pixelatedw.mineminenomi.data.entity.stats.EntityStatsCapability;
import xyz.pixelatedw.mineminenomi.data.entity.stats.IEntityStats;

public class SetPlayerDetailsEvent extends PlayerEvent {
   private Optional<IEntityStats> props;

   public SetPlayerDetailsEvent(Player player) {
      super(player);
      this.props = EntityStatsCapability.get(player);
   }

   public Optional<IEntityStats> getEntityStats() {
      return this.props;
   }
}
