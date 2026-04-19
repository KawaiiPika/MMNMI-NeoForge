package xyz.pixelatedw.mineminenomi.items;

import com.google.common.collect.ImmutableSet;
import java.util.List;
import java.util.Set;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import xyz.pixelatedw.mineminenomi.api.helpers.DevilFruitHelper;
import xyz.pixelatedw.mineminenomi.data.world.OneFruitWorldData;
import xyz.pixelatedw.mineminenomi.init.ModItems;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18n;

public class AkumaNoMiBoxItem extends Item {
   public static final Pair<Integer, ResourceLocation> TIER_1_FRUITS = ImmutablePair.of(1, ResourceLocation.fromNamespaceAndPath("mineminenomi", "dfboxes/wooden_box"));
   public static final Pair<Integer, ResourceLocation> TIER_2_FRUITS = ImmutablePair.of(2, ResourceLocation.fromNamespaceAndPath("mineminenomi", "dfboxes/iron_box"));
   public static final Pair<Integer, ResourceLocation> TIER_3_FRUITS = ImmutablePair.of(3, ResourceLocation.fromNamespaceAndPath("mineminenomi", "dfboxes/golden_box"));
   private static final Set<Item> KEY_SET;
   private Pair<Integer, ResourceLocation> tier;

   public AkumaNoMiBoxItem(Pair<Integer, ResourceLocation> tier) {
      super((new Item.Properties()).m_41487_(1));
      this.tier = tier;
   }

   public int getKeySlot(Player player) {
      if (!player.m_150109_().m_18949_(KEY_SET)) {
         player.m_213846_(ModI18n.ITEM_MESSAGE_NEED_KEY);
         return -1;
      } else {
         for(int i = 0; i < player.m_150109_().m_6643_(); ++i) {
            ItemStack stack = player.m_150109_().m_8020_(i);
            if (stack != null && !stack.m_41619_() && stack.m_41720_() == ModItems.KEY.get()) {
               return i;
            }
         }

         return -1;
      }
   }

   public InteractionResultHolder<ItemStack> m_7203_(Level world, Player player, InteractionHand hand) {
      ItemStack itemStack = player.m_21120_(hand);
      if (world.f_46443_) {
         return InteractionResultHolder.m_19090_(itemStack);
      } else {
         int keySlot = this.getKeySlot(player);
         if (keySlot < 0) {
            return InteractionResultHolder.m_19100_(itemStack);
         } else {
            player.m_150109_().m_7407_(keySlot, 1);
            player.m_150109_().m_36057_(itemStack);
            OneFruitWorldData worldData = OneFruitWorldData.get();
            LootTable lootTable = player.m_9236_().m_7654_().m_278653_().m_278676_((ResourceLocation)this.tier.getValue());
            LootParams lootParams = (new LootParams.Builder((ServerLevel)player.m_9236_())).m_287239_(player.m_36336_()).m_287286_(LootContextParams.f_81455_, player).m_287235_(LootContextParamSets.f_81410_);
            List<ItemStack> stacks = lootTable.m_287195_(lootParams);
            ItemStack boxItemStack = ItemStack.f_41583_;

            for(ItemStack stack : stacks) {
               if (stack != null && stack.m_41720_() instanceof AkumaNoMiItem && worldData.isFruitAvailable((AkumaNoMiItem)stack.m_41720_())) {
                  boxItemStack = stack;
                  break;
               }
            }

            if (boxItemStack == null) {
               player.m_150109_().m_36057_(itemStack);
               return InteractionResultHolder.m_19090_(itemStack);
            } else if (!(boxItemStack.m_41720_() instanceof AkumaNoMiItem)) {
               player.m_150109_().m_36054_(boxItemStack);
               return InteractionResultHolder.m_19090_(itemStack);
            } else if (DevilFruitHelper.hasDFLimitInInventory(player)) {
               player.m_36176_(boxItemStack, true);
               return InteractionResultHolder.m_19090_(itemStack);
            } else {
               AkumaNoMiItem fruit = (AkumaNoMiItem)boxItemStack.m_41720_();
               player.m_150109_().m_36054_(boxItemStack);
               worldData.inventoryOneFruit(fruit, player, "Obtained from " + itemStack.m_41611_().getString());
               return InteractionResultHolder.m_19090_(itemStack);
            }
         }
      }
   }

   public int getTierLevel() {
      return (Integer)this.tier.getKey();
   }

   static {
      KEY_SET = ImmutableSet.of((Item)ModItems.KEY.get());
   }
}
