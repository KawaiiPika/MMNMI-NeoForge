package xyz.pixelatedw.mineminenomi.api.abilities.components;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.helpers.ItemsHelper;
import xyz.pixelatedw.mineminenomi.api.util.PriorityEventPool;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;

public class ItemSpawnComponent extends AbilityComponent<IAbility> {
   public static final String SPAWN_TAG = "spawnedByAbility";
   private List<ItemStack> trackedStacks = new ArrayList();
   private boolean isActive;
   private final PriorityEventPool<IMissingItemEvent> missingItemEvents = new PriorityEventPool<IMissingItemEvent>();

   public ItemSpawnComponent(IAbility ability) {
      super((AbilityComponentKey)ModAbilityComponents.ITEM_SPAWN.get(), ability);
      this.setTickRate(5);
   }

   public ItemSpawnComponent addMissingItemEvent(int priority, IMissingItemEvent event) {
      this.missingItemEvents.addEvent(priority, event);
      return this;
   }

   public void doTick(LivingEntity entity) {
      if (!entity.m_9236_().f_46443_ && this.isActive && this.missingItemEvents.countEventsInPool() > 0L && !this.trackedStacks.isEmpty()) {
         for(ItemStack stack : this.trackedStacks) {
            if (stack.m_41619_()) {
               this.missingItemEvents.dispatch((event) -> event.missingItem(entity, this.getAbility()));
               return;
            }
         }
      }

   }

   private List<ItemStack> getTrackedItems() {
      return this.trackedStacks;
   }

   public void spawnItem(LivingEntity entity, ItemStack stack) {
      this.spawnItem(entity, stack, EquipmentSlot.MAINHAND, false);
   }

   public void spawnItem(LivingEntity entity, ItemStack stack, @Nullable EquipmentSlot slot, boolean forceSlot) {
      this.ensureIsRegistered();
      this.isActive = true;
      ItemStack itemStack = entity.m_6844_(slot);
      if (itemStack.m_41619_()) {
         entity.m_8061_(slot, stack);
         this.trackedStacks.add(stack);
      } else {
         if (entity instanceof Player) {
            Player player = (Player)entity;
            player.m_36356_(stack);
            this.trackedStacks.add(stack);
         }

      }
   }

   public void despawnItems(LivingEntity entity) {
      this.isActive = false;
      if (this.trackedStacks.size() > 0) {
         for(ItemStack stack : this.trackedStacks) {
            ItemsHelper.removeItemStackFromInventory(entity, stack);
         }
      }

      this.trackedStacks.clear();
   }

   public boolean isActive() {
      return this.isActive;
   }

   @FunctionalInterface
   public interface IMissingItemEvent {
      void missingItem(LivingEntity var1, IAbility var2);
   }
}
