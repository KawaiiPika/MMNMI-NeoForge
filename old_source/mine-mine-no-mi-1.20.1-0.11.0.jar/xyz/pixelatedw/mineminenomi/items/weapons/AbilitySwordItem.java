package xyz.pixelatedw.mineminenomi.items.weapons;

import java.util.Optional;
import java.util.function.Supplier;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponentKey;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ItemSpawnComponent;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityCapability;
import xyz.pixelatedw.mineminenomi.data.entity.ability.IAbilityData;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;

public class AbilitySwordItem<A extends IAbility> extends ModSwordItem {
   private Supplier<AbilityCore<A>> coreSupplier;

   public AbilitySwordItem(Tier tier, Supplier<AbilityCore<A>> core, int damage) {
      this(tier, core, damage, -2.4F);
   }

   public AbilitySwordItem(Tier tier, Supplier<AbilityCore<A>> core, int damage, float attackSpeed) {
      super(tier, damage, attackSpeed, (new Item.Properties()).setNoRepair().m_41503_(-1));
      this.coreSupplier = null;
      this.coreSupplier = core;
   }

   public void m_6883_(ItemStack itemStack, Level world, Entity entity, int itemSlot, boolean isSelected) {
      super.m_6883_(itemStack, world, entity, itemSlot, isSelected);
      if (entity instanceof Player player) {
         if (!entity.m_9236_().f_46443_ && this.coreSupplier != null) {
            IAbilityData abilityData = (IAbilityData)AbilityCapability.get(player).orElse((Object)null);
            if (abilityData == null) {
               return;
            }

            boolean deleteSword = true;

            for(IAbility ability : abilityData.getEquippedAbilities()) {
               if (ability != null) {
                  Optional<ItemSpawnComponent> itemSpawnComponent = ability.<ItemSpawnComponent>getComponent((AbilityComponentKey)ModAbilityComponents.ITEM_SPAWN.get());
                  boolean hasItemSpawnActive = (Boolean)itemSpawnComponent.map((comp) -> comp.getAbility().getCore().equals(this.coreSupplier.get()) && comp.isActive()).orElse(false);
                  if (hasItemSpawnActive) {
                     deleteSword = false;
                  }
               }
            }

            if (deleteSword) {
               player.m_150109_().m_36057_(itemStack);
            }
         }
      }

   }

   public boolean onEntityItemUpdate(ItemStack itemStack, ItemEntity entityItem) {
      if (entityItem.m_6084_()) {
         entityItem.m_142687_(RemovalReason.DISCARDED);
      }

      return true;
   }

   public boolean m_8120_(ItemStack stack) {
      return false;
   }

   /** @deprecated */
   @Deprecated
   public AbilitySwordItem<A> setFrosbiteTimer(int time) {
      return this;
   }
}
