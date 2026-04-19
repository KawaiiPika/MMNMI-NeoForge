package xyz.pixelatedw.mineminenomi.api.util;

import java.util.ArrayList;
import java.util.List;

public class Signal<T> {
   private List<Listener<T>> listeners = new ArrayList();

   public void add(Listener<T> listener) {
      this.listeners.add(listener);
   }

   public void remove(Listener<T> listener) {
      this.listeners.remove(listener);
   }

   public void clearListeners() {
      this.listeners.clear();
   }

   public void dispatch(T object) {
      for(Listener<T> listener : this.listeners) {
         listener.receive(this, object);
      }

   }
}
