package xyz.pixelatedw.mineminenomi.api.events;

import net.minecraftforge.eventbus.api.Event;
import xyz.pixelatedw.mineminenomi.api.crew.Crew;
import xyz.pixelatedw.mineminenomi.api.crew.JollyRoger;

public class JollyRogerEvent extends Event {
   private JollyRoger jollyRoger;
   private Crew crew;

   public JollyRogerEvent(JollyRoger jollyRoger, Crew crew) {
      this.jollyRoger = jollyRoger;
      this.crew = crew;
   }

   public Crew getCrew() {
      return this.crew;
   }

   public JollyRoger getJollyRoger() {
      return this.jollyRoger;
   }

   public static class Update extends JollyRogerEvent {
      public Update(JollyRoger jollyRoger, Crew crew) {
         super(jollyRoger, crew);
      }
   }
}
