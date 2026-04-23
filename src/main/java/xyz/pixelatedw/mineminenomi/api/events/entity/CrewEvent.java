package xyz.pixelatedw.mineminenomi.api.events.entity;

import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.Event;
import net.neoforged.bus.api.ICancellableEvent;
import xyz.pixelatedw.mineminenomi.api.crew.Crew;

public class CrewEvent extends Event {

    private final Player player;
    private final Crew crew;

    public CrewEvent(Player player, Crew crew) {
        this.player = player;
        this.crew = crew;
    }

    public Player getPlayer() {
        return this.player;
    }

    public Crew getCrew() {
        return this.crew;
    }

    public static class Create extends CrewEvent implements ICancellableEvent {
        public Create(Player player, Crew crew) {
            super(player, crew);
        }
    }

    public static class Join extends CrewEvent implements ICancellableEvent {
        public Join(Player player, Crew crew) {
            super(player, crew);
        }
    }

    public static class Leave extends CrewEvent implements ICancellableEvent {
        public Leave(Player player, Crew crew) {
            super(player, crew);
        }
    }

    public static class Kick extends CrewEvent implements ICancellableEvent {
        public Kick(Player player, Crew crew) {
            super(player, crew);
        }
    }
}
