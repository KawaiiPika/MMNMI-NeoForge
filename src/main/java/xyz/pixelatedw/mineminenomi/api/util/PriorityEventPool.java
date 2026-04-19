package xyz.pixelatedw.mineminenomi.api.util;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class PriorityEventPool<E> {
    public static final int DEFAULT_PRIORITY = 100;
    private final Multimap<Integer, E> events = HashMultimap.create();
    private boolean isDirty;
    private long lastCount;

    public void addEvent(E event) {
        this.addEvent(100, event);
    }

    public void addEvent(int priority, E event) {
        this.events.put(priority, event);
        this.isDirty = true;
    }

    public void removeEvent(E event) {
        if (this.events.containsValue(event)) {
            int eventKey = -1;

            for (int key : this.events.keySet()) {
                if (eventKey >= 0) {
                    break;
                }

                for (E value : this.events.get(key)) {
                    if (value.equals(event)) {
                        eventKey = key;
                        break;
                    }
                }
            }

            this.events.remove(eventKey, event);
            this.isDirty = true;
        }
    }

    public void clearEvents() {
        this.events.clear();
    }

    public void clearEvents(int priority) {
        this.events.removeAll(priority);
    }

    public Stream<E> getEventsStream() {
        return this.events.entries().stream()
                .sorted((e1, e2) -> e1.getKey() > e2.getKey() ? 1 : -1)
                .map(java.util.Map.Entry::getValue);
    }

    public boolean dispatchCancelable(Predicate<E> predicate) {
        return this.getEventsStream().anyMatch(predicate);
    }

    public void dispatch(Consumer<E> consumer) {
        this.getEventsStream().forEachOrdered(consumer);
    }

    public long countEventsInPool() {
        if (this.isDirty) {
            this.lastCount = this.getEventsStream().count();
            this.isDirty = false;
        }

        return this.lastCount;
    }
}
