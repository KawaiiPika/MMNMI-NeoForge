package xyz.pixelatedw.mineminenomi.handlers.world;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.registries.ForgeRegistries;
import xyz.pixelatedw.mineminenomi.api.DFEncyclopediaEntry;
import xyz.pixelatedw.mineminenomi.api.helpers.ItemsHelper;
import xyz.pixelatedw.mineminenomi.config.ServerConfig;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.init.ModValues;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18n;
import xyz.pixelatedw.mineminenomi.items.AkumaNoMiItem;
import xyz.pixelatedw.mineminenomi.items.DFEncyclopediaItem;
import xyz.pixelatedw.mineminenomi.packets.server.randfruit.SSetFruitSeedsPacket;
import xyz.pixelatedw.mineminenomi.packets.server.randfruit.SSetRandomizedFruitsPacket;

public class RandomizedFruitsHandler {
   public static class Client {
      public static boolean HAS_RANDOMIZED_FRUIT = false;
      public static boolean DIRTY = false;
      public static HashMap<Integer, Long> FRUIT_SEEDS = new HashMap();

      public static void handleHiddenFruitName(Player player, ItemStack itemStack, List<Component> tooltip) {
         if (HAS_RANDOMIZED_FRUIT && !itemStack.m_41619_()) {
            Item var4 = itemStack.m_41720_();
            if (var4 instanceof AkumaNoMiItem) {
               AkumaNoMiItem fruitItem = (AkumaNoMiItem)var4;
               boolean isFound = itemStack.m_41782_() && itemStack.m_41783_().m_128471_("deciphered");
               tooltip.clear();
               if (isFound) {
                  Component realFuitName = fruitItem.getShiftedFruit().getDevilFruitName().m_6881_().m_130948_(Style.f_131099_.m_131140_(ChatFormatting.GOLD));
                  tooltip.add(realFuitName);
               } else {
                  for(ItemStack stack : ItemsHelper.getInventoryItems(player)) {
                     if (!stack.m_41619_() && stack.m_41720_() instanceof DFEncyclopediaItem) {
                        Optional<DFEncyclopediaEntry> entry = DFEncyclopediaItem.getEntry(stack, fruitItem.getShiftedFruit());
                        if (entry.isPresent() && ((DFEncyclopediaEntry)entry.get()).isComplete()) {
                           itemStack.m_41784_().m_128379_("deciphered", true);
                        }
                     }
                  }

                  tooltip.add(ModI18n.ITEM_GENERIC_FRUIT);
               }
            }
         }

      }
   }

   public static class Common {
      public static long SEED;

      public static void setRandomizationSeed(Player player) {
         ModNetwork.sendTo(new SSetRandomizedFruitsPacket(ServerConfig.getRandomizedFruits()), player);
         if (ServerConfig.getRandomizedFruits()) {
            HashMap<Integer, Long> seeds = new HashMap();
            Random rand = new Random(((ServerLevel)player.m_9236_()).m_7328_());
            long worldSeed = rand.nextLong();

            for(AkumaNoMiItem fruit : ModValues.DEVIL_FRUITS) {
               ResourceLocation key = ForgeRegistries.ITEMS.getKey(fruit);
               if (key != null) {
                  int hash = key.hashCode();
                  long seed = worldSeed + (long)hash;
                  seeds.put(hash, seed);
               }
            }

            SEED = worldSeed;
            ModNetwork.sendTo(new SSetFruitSeedsPacket(seeds, worldSeed), player);
         }

      }

      public static void handleClueUsage(Player player, ItemStack stack) {
         if (!stack.m_41619_() && stack.m_41720_() == Items.f_42516_ && stack.m_41782_() && !stack.m_41783_().m_128456_() && !player.m_9236_().f_46443_) {
            if (stack.m_41737_("fruitClues") != null) {
               if (!ServerConfig.getRandomizedFruits()) {
                  player.m_5661_(ModI18n.SYSTEM_MESSAGE_RANDOMIZED_FRUITS, false);
               } else {
                  CompoundTag nbt = stack.m_41737_("fruitClues");
                  ResourceLocation key = ResourceLocation.parse(nbt.m_128461_("fruitKey"));
                  DFEncyclopediaEntry entry = DFEncyclopediaEntry.of(nbt);
                  boolean flag = DFEncyclopediaItem.updateEncyclopediae(player, key, entry);
                  if (flag) {
                     stack.m_41774_(1);
                     Item item = (Item)ForgeRegistries.ITEMS.getValue(key);
                     player.m_5661_(Component.m_237110_(ModI18n.ITEM_MESSAGE_GAINED_FRUIT_CLUE, new Object[]{item.m_41466_().getString()}), false);
                  }

               }
            }
         }
      }
   }
}
