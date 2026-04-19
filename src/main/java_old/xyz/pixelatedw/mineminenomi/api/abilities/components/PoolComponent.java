package xyz.pixelatedw.mineminenomi.api.abilities.components;

import com.google.common.collect.Sets;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityPool;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityCapability;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.packets.server.ability.component.SSetPoolInUsePacket;

public class PoolComponent extends AbilityComponent<IAbility> {
   private final Set<AbilityPool> pools;
   private boolean poolInUse;
   private float ticksLocked;
   private Predicate<IAbility> predicate;

   public PoolComponent(IAbility ability, AbilityPool pool, AbilityPool... pools) {
      super((AbilityComponentKey)ModAbilityComponents.POOL.get(), ability);
      this.pools = Sets.newHashSet(new AbilityPool[]{pool});
      Collections.addAll(this.pools, pools);
   }

   public void postInit(IAbility ability) {
      ability.getComponent((AbilityComponentKey)ModAbilityComponents.SLOT_DECORATION.get()).ifPresent((component) -> component.addPreRenderEvent(50, (entity, minecraft, stack, buffer, rect, x, y, partialTicks) -> {
            if (this.isPoolInUse()) {
               component.setCurrentValue(0.0F);
               component.setMaxValue(1.0F);
               component.setSlotColor(0.0F, 0.0F, 0.0F);
               component.setIconColor(0.4F, 0.4F, 0.4F);
            }

         }));
      this.pools.forEach((pool) -> pool.addAbilityCore(ability.getCore()));
      this.predicate = (abl) -> {
         if (abl.equals(ability)) {
            return false;
         } else {
            Optional<PoolComponent> poolComponent = abl.<PoolComponent>getComponent((AbilityComponentKey)ModAbilityComponents.POOL.get());
            return poolComponent.isPresent() && this.hasAtLeastOneSamePool((PoolComponent)poolComponent.get());
         }
      };
   }

   protected void doTick(LivingEntity entity) {
      if (!this.getAbility().hasComponent((AbilityComponentKey)ModAbilityComponents.DISABLE.get()) || !((DisableComponent)this.getAbility().getComponent((AbilityComponentKey)ModAbilityComponents.DISABLE.get()).get()).isDisabled()) {
         ;
      }
   }

   public Set<AbilityPool> getPools() {
      return this.pools;
   }

   public void removeFromPool(AbilityPool pool) {
      this.pools.remove(pool);
   }

   public boolean containsPool(AbilityPool pool) {
      for(AbilityPool ablpool : this.pools) {
         if (ablpool.equals(pool)) {
            return true;
         }
      }

      return false;
   }

   public boolean hasAtLeastOneSamePool(PoolComponent otherComponent) {
      for(AbilityPool p1 : this.pools) {
         for(AbilityPool p2 : otherComponent.getPools()) {
            if (p1.equals(p2)) {
               return true;
            }
         }
      }

      return false;
   }

   public Set<IAbility> getAbilitiesInPool(LivingEntity entity) {
      return (Set)AbilityCapability.get(entity).map((props) -> props.getEquippedAbilities(this.predicate)).orElse(Set.of());
   }

   public boolean isPoolInUse() {
      return this.poolInUse;
   }

   public void setAbilityFromPoolInUse(LivingEntity entity, boolean flag) {
      this.poolInUse = flag;
      if (!flag) {
         this.getAbility().getComponent((AbilityComponentKey)ModAbilityComponents.SLOT_DECORATION.get()).ifPresent((c) -> c.resetDecoration());
      }

      if (!entity.m_9236_().f_46443_) {
         ModNetwork.sendToAllTrackingAndSelf(new SSetPoolInUsePacket(entity, this.getAbility(), flag, 0), entity);
      }

   }

   public void startPoolInUse(LivingEntity entity) {
      this.ensureIsRegistered();
      this.getAbilitiesInPool(entity).forEach((otherAbility) -> otherAbility.getComponent((AbilityComponentKey)ModAbilityComponents.POOL.get()).ifPresent((c) -> c.setAbilityFromPoolInUse(entity, true)));
   }

   public void stopPoolInUse(LivingEntity entity) {
      this.getAbilitiesInPool(entity).forEach((otherAbility) -> otherAbility.getComponent((AbilityComponentKey)ModAbilityComponents.POOL.get()).ifPresent((c) -> c.setAbilityFromPoolInUse(entity, false)));
   }
}
