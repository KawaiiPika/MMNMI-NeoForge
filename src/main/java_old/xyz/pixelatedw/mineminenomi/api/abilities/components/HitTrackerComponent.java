package xyz.pixelatedw.mineminenomi.api.abilities.components;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;

public class HitTrackerComponent extends AbilityComponent<IAbility> {
   private Set<Entity> hits = new HashSet();
   private Set<UUID> hitsUUIDs = new HashSet();

   public HitTrackerComponent(IAbility ability) {
      super((AbilityComponentKey)ModAbilityComponents.HIT_TRACKER.get(), ability);
   }

   public void clearHits() {
      this.hits.clear();
      this.hitsUUIDs.clear();
   }

   public boolean canHit(Entity target) {
      this.ensureIsRegistered();
      if (this.hits.contains(target)) {
         return false;
      } else {
         this.hits.add(target);
         this.hitsUUIDs.add(target.m_20148_());
         return true;
      }
   }

   public Set<Entity> getHits() {
      return this.hits;
   }

   public Set<UUID> getHitsUUIDs() {
      return this.hitsUUIDs;
   }

   protected void doTick(LivingEntity entity) {
   }
}
