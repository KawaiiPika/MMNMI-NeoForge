package xyz.pixelatedw.mineminenomi.items.weapons;

import java.util.Optional;
import java.util.function.Supplier;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import xyz.pixelatedw.mineminenomi.abilities.doru.DoruDoruArtsPickaxeAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponentKey;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ItemSpawnComponent;
import xyz.pixelatedw.mineminenomi.api.items.IMultiChannelColorItem;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityCapability;
import xyz.pixelatedw.mineminenomi.data.entity.ability.IAbilityData;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;
import xyz.pixelatedw.mineminenomi.init.ModBlocks;
import xyz.pixelatedw.mineminenomi.init.ModWeapons;

public class DoruPickaxeItem extends PickaxeItem implements IMultiChannelColorItem {
   private final Supplier<AbilityCore<DoruDoruArtsPickaxeAbility>> coreSupplier;

   public DoruPickaxeItem(Tier tier, int damage, float speed) {
      super(tier, damage, speed, new Item.Properties());
      this.coreSupplier = DoruDoruArtsPickaxeAbility.INSTANCE;
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

   public float m_8102_(ItemStack stack, BlockState state) {
      return stack.m_41720_() == ModWeapons.DORU_PICKAXE.get() && state.m_60734_() == ModBlocks.WAX.get() ? 999.0F : super.m_8102_(stack, state);
   }

   public boolean m_8120_(ItemStack stack) {
      return false;
   }

   public int getDefaultLayerColor(int layer) {
      return 0;
   }

   public boolean canBeDyed() {
      return true;
   }

   public int getMaxLayers() {
      return 1;
   }
}
