package xyz.pixelatedw.mineminenomi.api.events.entity;

import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.Event.HasResult;
import org.jetbrains.annotations.Nullable;

@HasResult
public class IssueBountyEvent extends PlayerEvent {
   private @Nullable Player issuer;
   private long bounty;

   public IssueBountyEvent(Player target, long bounty, @Nullable Player issuer) {
      super(target);
      this.issuer = issuer;
   }

   public @Nullable Player getIssuer() {
      return this.issuer;
   }

   public long getBounty() {
      return this.bounty;
   }
}
