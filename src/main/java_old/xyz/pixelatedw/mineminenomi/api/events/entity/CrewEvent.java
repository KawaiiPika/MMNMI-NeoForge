package xyz.pixelatedw.mineminenomi.api.events.entity;

import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.Cancelable;
import xyz.pixelatedw.mineminenomi.api.crew.Crew;

public class CrewEvent extends PlayerEvent {
   private Crew crew;

   public CrewEvent(Player player, Crew crew) {
      super(player);
      this.crew = crew;
   }

   public Crew getCrew() {
      return this.crew;
   }

   @Cancelable
   public static class Create extends CrewEvent {
      public Create(Player player, Crew crew) {
         super(player, crew);
      }
   }

   @Cancelable
   public static class Join extends CrewEvent {
      public Join(Player player, Crew crew) {
         super(player, crew);
      }
   }

   @Cancelable
   public static class Leave extends CrewEvent {
      public Leave(Player player, Crew crew) {
         super(player, crew);
      }
   }

   @Cancelable
   public static class Kick extends CrewEvent {
      public Kick(Player player, Crew crew) {
         super(player, crew);
      }
   }
}
