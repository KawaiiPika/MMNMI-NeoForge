package xyz.pixelatedw.mineminenomi.api.events.stats;

import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.player.PlayerEvent;
import xyz.pixelatedw.mineminenomi.api.enums.StatChangeSource;

public abstract class StatEvent extends PlayerEvent {
   private StatChangeSource source;

   public StatEvent(Player entity, StatChangeSource source) {
      super(entity);
      this.source = source;
   }

   public StatChangeSource getSource() {
      return this.source;
   }
}
