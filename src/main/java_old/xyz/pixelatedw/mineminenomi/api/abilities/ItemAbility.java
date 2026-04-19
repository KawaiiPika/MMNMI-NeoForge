package xyz.pixelatedw.mineminenomi.api.abilities;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ItemSpawnComponent;

public abstract class ItemAbility extends Ability {
   protected final ContinuousComponent continuousComponent = (new ContinuousComponent(this, true)).addStartEvent(200, this::startContinuityEvent).addEndEvent(200, this::endContinuityEvent);
   protected final ItemSpawnComponent itemSpawnComponent = (new ItemSpawnComponent(this)).addMissingItemEvent(200, this::missingItemEvent);

   public ItemAbility(AbilityCore<? extends ItemAbility> core) {
      super(core);
      this.addComponents(new AbilityComponent[]{this.continuousComponent, this.itemSpawnComponent});
      this.addUseEvent(200, this::onUseEvent);
   }

   private void onUseEvent(LivingEntity entity, IAbility ability) {
      this.continuousComponent.triggerContinuity(entity);
   }

   private void startContinuityEvent(LivingEntity entity, IAbility ability) {
      this.itemSpawnComponent.spawnItem(entity, this.createItemStack(entity));
   }

   private void endContinuityEvent(LivingEntity entity, IAbility ability) {
      this.itemSpawnComponent.despawnItems(entity);
   }

   private void missingItemEvent(LivingEntity entity, IAbility ability) {
      this.continuousComponent.stopContinuity(entity);
   }

   public abstract ItemStack createItemStack(LivingEntity var1);
}
