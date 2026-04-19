package xyz.pixelatedw.mineminenomi.items;

import com.google.common.collect.Sets;
import java.awt.Color;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.DFEncyclopediaEntry;
import xyz.pixelatedw.mineminenomi.api.helpers.ItemsHelper;
import xyz.pixelatedw.mineminenomi.config.ServerConfig;
import xyz.pixelatedw.mineminenomi.handlers.world.RandomizedFruitsHandler;
import xyz.pixelatedw.mineminenomi.init.ModAdvancements;
import xyz.pixelatedw.mineminenomi.init.ModItems;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.init.ModValues;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18n;
import xyz.pixelatedw.mineminenomi.packets.server.ui.SOpenEncyclopediaScreenPacket;

public class DFEncyclopediaItem extends Item {
   private static final Component CONFIG_ERROR;

   public DFEncyclopediaItem() {
      super((new Item.Properties()).m_41487_(1));
   }

   public InteractionResultHolder<ItemStack> m_7203_(Level world, Player player, InteractionHand hand) {
      ItemStack itemStack = player.m_21120_(hand);
      if (world.f_46443_) {
         return InteractionResultHolder.m_19098_(itemStack);
      } else if (!ServerConfig.getRandomizedFruits()) {
         player.m_5661_(ModI18n.SYSTEM_MESSAGE_RANDOMIZED_FRUITS, true);
         return InteractionResultHolder.m_19100_(itemStack);
      } else {
         ModNetwork.sendTo(new SOpenEncyclopediaScreenPacket(itemStack), player);
         return InteractionResultHolder.m_19090_(itemStack);
      }
   }

   public void m_7373_(ItemStack itemStack, @Nullable Level world, List<Component> list, TooltipFlag par4) {
      if (!ServerConfig.getRandomizedFruits() && !RandomizedFruitsHandler.Client.HAS_RANDOMIZED_FRUIT) {
         list.add(CONFIG_ERROR);
      } else {
         int count = itemStack.m_41698_("unlocked").m_128440_();
         list.add(Component.m_237110_(ModI18n.ITEM_DF_ENCYCLOPEDIA_ENTRIES, new Object[]{count}).m_130940_(ChatFormatting.GRAY));
         float completeness = getCompletion(itemStack);
         if (completeness > 0.0F) {
            String var10001 = ModI18n.ITEM_DF_ENCYCLOPEDIA_COMPLETION;
            Object[] var10002 = new Object[1];
            Object[] var10006 = new Object[]{completeness * 100.0F};
            var10002[0] = String.format("%.2f", var10006) + "%";
            list.add(Component.m_237110_(var10001, var10002).m_130940_(ChatFormatting.GRAY));
         }
      }

   }

   public static void addFruitClues(ItemStack itemStack, ResourceLocation fruit, DFEncyclopediaEntry clue) {
      if (!itemStack.m_41619_() && itemStack.m_41720_() == ModItems.DEVIL_FRUIT_ENCYCLOPEDIA.get()) {
         CompoundTag nbt = new CompoundTag();
         if (clue.getShape().isPresent()) {
            nbt.m_128405_("baseShape", (Integer)clue.getShape().get());
         }

         if (clue.getBaseColor().isPresent()) {
            nbt.m_128405_("baseColor", ((Color)clue.getBaseColor().get()).getRGB());
         }

         if (clue.getStemColor().isPresent()) {
            nbt.m_128405_("stemColor", ((Color)clue.getStemColor().get()).getRGB());
         }

         itemStack.m_41698_("unlocked").m_128365_(fruit.toString(), nbt);
      }
   }

   public static Set<DFEncyclopediaEntry> getEntries(ItemStack itemStack) {
      if (!itemStack.m_41619_() && itemStack.m_41720_() == ModItems.DEVIL_FRUIT_ENCYCLOPEDIA.get() && itemStack.m_41782_() && itemStack.m_41783_().m_128441_("unlocked")) {
         Set<DFEncyclopediaEntry> entries = new HashSet();
         CompoundTag nbt = itemStack.m_41698_("unlocked");

         for(AkumaNoMiItem fruit : ModValues.DEVIL_FRUITS) {
            String key = fruit.getRegistryKey().toString();
            if (nbt.m_128441_(key)) {
               CompoundTag fruitNbt = nbt.m_128469_(key);
               Optional<Integer> shape = fruitNbt.m_128441_("baseShape") ? Optional.of(fruitNbt.m_128451_("baseShape")) : Optional.empty();
               Optional<Color> baseColor = fruitNbt.m_128441_("baseColor") ? Optional.of(new Color(fruitNbt.m_128451_("baseColor"))) : Optional.empty();
               Optional<Color> stemColor = fruitNbt.m_128441_("stemColor") ? Optional.of(new Color(fruitNbt.m_128451_("stemColor"))) : Optional.empty();
               DFEncyclopediaEntry entry = DFEncyclopediaEntry.of(shape, baseColor, stemColor);
               entry.setDevilFruit(fruit);
               entries.add(entry);
            }
         }

         return entries;
      } else {
         return Sets.newHashSet();
      }
   }

   public static Optional<DFEncyclopediaEntry> getEntry(ItemStack itemStack, AkumaNoMiItem fruit) {
      if (!itemStack.m_41619_() && itemStack.m_41720_() == ModItems.DEVIL_FRUIT_ENCYCLOPEDIA.get() && itemStack.m_41782_() && itemStack.m_41783_().m_128441_("unlocked")) {
         CompoundTag nbt = itemStack.m_41698_("unlocked");
         String key = fruit.getRegistryKey().toString();
         if (nbt.m_128441_(key)) {
            CompoundTag fruitNbt = nbt.m_128469_(key);
            Optional<Integer> shape = fruitNbt.m_128441_("baseShape") ? Optional.of(fruitNbt.m_128451_("baseShape")) : Optional.empty();
            Optional<Color> baseColor = fruitNbt.m_128441_("baseColor") ? Optional.of(new Color(fruitNbt.m_128451_("baseColor"))) : Optional.empty();
            Optional<Color> stemColor = fruitNbt.m_128441_("stemColor") ? Optional.of(new Color(fruitNbt.m_128451_("stemColor"))) : Optional.empty();
            DFEncyclopediaEntry entry = DFEncyclopediaEntry.of(shape, baseColor, stemColor);
            entry.setDevilFruit(fruit);
            return Optional.of(entry);
         } else {
            return Optional.empty();
         }
      } else {
         return Optional.empty();
      }
   }

   public static float getCompletion(ItemStack itemStack) {
      if (!itemStack.m_41619_() && itemStack.m_41720_() == ModItems.DEVIL_FRUIT_ENCYCLOPEDIA.get() && itemStack.m_41782_() && itemStack.m_41783_().m_128441_("unlocked")) {
         int completed = 0;
         CompoundTag unlockedElements = itemStack.m_41737_("unlocked");

         for(String key : unlockedElements.m_128431_()) {
            CompoundTag nbt = unlockedElements.m_128469_(key);
            DFEncyclopediaEntry entry = DFEncyclopediaEntry.of(nbt);
            completed += entry.getCompletion();
         }

         return (float)completed / (float)(ModValues.DEVIL_FRUITS.size() * 3);
      } else {
         return -1.0F;
      }
   }

   public static boolean updateEncyclopediae(Player player, ResourceLocation fruit, DFEncyclopediaEntry entry) {
      if (player instanceof ServerPlayer serverPlayer) {
         List<ItemStack> slots = ItemsHelper.getAllInventoryItems(player);
         slots = (List)slots.stream().filter((stack) -> stack.m_41720_() == ModItems.DEVIL_FRUIT_ENCYCLOPEDIA.get()).collect(Collectors.toList());
         if (slots.size() <= 0) {
            return false;
         } else {
            slots.forEach((stack) -> {
               addFruitClues(stack, fruit, entry);
               ModAdvancements.ENCYCLOPEDIA_COMPLETION.trigger(serverPlayer, stack);
            });
            return true;
         }
      } else {
         return false;
      }
   }

   static {
      CONFIG_ERROR = ModI18n.SYSTEM_MESSAGE_RANDOMIZED_FRUITS.m_6881_().m_130940_(ChatFormatting.RED);
   }
}
