package xyz.pixelatedw.mineminenomi.api.abilities.components;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class BonusManager {
   private UUID id;
   private Map<UUID, BonusValue> bonuses = new HashMap();

   public BonusManager(UUID id) {
      this.id = id;
   }

   public float applyBonus(float value) {
      for(BonusValue bonus : this.bonuses.values()) {
         switch (bonus.getType()) {
            case ADD:
               value += bonus.getValue();
               break;
            case MUL:
               value *= bonus.getValue();
         }
      }

      value = Math.max(0.0F, value);
      return value;
   }

   public boolean hasBonus(UUID id) {
      return this.bonuses.containsKey(id);
   }

   public void addBonus(BonusValue value) {
      this.bonuses.put(value.getUUID(), value);
   }

   public void addBonus(UUID id, String name, BonusOperation type, float value) {
      this.bonuses.put(id, new BonusValue(id, name, type, value));
   }

   public void removeBonus(UUID id) {
      this.bonuses.remove(id);
   }

   public void clearBonuses() {
      this.bonuses.clear();
   }

   public UUID getId() {
      return this.id;
   }

   public void setBonusMap(Map<UUID, BonusValue> map) {
      this.bonuses = map;
   }

   public Set<Map.Entry<UUID, BonusValue>> getBonuses() {
      return this.bonuses.entrySet();
   }

   public static class BonusValue {
      private UUID id;
      private BonusOperation type;
      private String name;
      private float value;

      public BonusValue(UUID id, String name, BonusOperation type, float value) {
         this.id = id;
         this.name = name;
         this.type = type;
         this.value = value;
      }

      public UUID getUUID() {
         return this.id;
      }

      public String getName() {
         return this.name;
      }

      public BonusOperation getType() {
         return this.type;
      }

      public float getValue() {
         return this.value;
      }

      public String toString() {
         String var10000 = this.name;
         return var10000 + " | " + this.getType().name() + " " + this.getValue();
      }
   }
}
