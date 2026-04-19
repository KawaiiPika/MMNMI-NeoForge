package xyz.pixelatedw.mineminenomi.handlers.world;

import com.mojang.datafixers.util.Pair;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.registries.ForgeRegistries;
import xyz.pixelatedw.mineminenomi.api.OneFruitEntry;
import xyz.pixelatedw.mineminenomi.api.WyDebug;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.helpers.DevilFruitHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.ItemsHelper;
import xyz.pixelatedw.mineminenomi.api.morph.MorphInfo;
import xyz.pixelatedw.mineminenomi.config.ServerConfig;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityCapability;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityDataBase;
import xyz.pixelatedw.mineminenomi.data.entity.ability.IAbilityData;
import xyz.pixelatedw.mineminenomi.data.entity.devilfruit.DevilFruitBase;
import xyz.pixelatedw.mineminenomi.data.entity.devilfruit.DevilFruitCapability;
import xyz.pixelatedw.mineminenomi.data.entity.devilfruit.IDevilFruit;
import xyz.pixelatedw.mineminenomi.data.world.OneFruitWorldData;
import xyz.pixelatedw.mineminenomi.data.world.UnloadFruitTimeStampsData;
import xyz.pixelatedw.mineminenomi.entities.AkumaNoMiEntity;
import xyz.pixelatedw.mineminenomi.init.ModFruits;
import xyz.pixelatedw.mineminenomi.init.ModMorphs;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.init.ModValues;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18n;
import xyz.pixelatedw.mineminenomi.items.AkumaNoMiItem;
import xyz.pixelatedw.mineminenomi.packets.server.SSyncDevilFruitPacket;

public class OneFruitPerWorldHandler {
   private static final Pair<ResourceLocation, ResourceLocation> OFFHAND_TEXTURE;

   public static void breakLeaves(Player player, Level level, BlockPos pos, boolean simpleLogic) {
      boolean hasShears = player.m_21120_(player.m_7655_()).m_41720_() == Items.f_42574_;
      if (ServerConfig.getDevilFruitDropsFromLeavesChance() > (double)0.0F && !hasShears) {
         double chance = level.m_213780_().m_188500_() * (double)100.0F;
         if (chance < ServerConfig.getDevilFruitDropsFromLeavesChance()) {
            AkumaNoMiItem df = (AkumaNoMiItem)ModValues.DEVIL_FRUITS.get((int)WyHelper.randomWithRange(0, ModValues.DEVIL_FRUITS.size() - 1));
            df = DevilFruitHelper.findAvailableOneFruit(level, df);
            if (df != null) {
               BlockPos blockPos = new BlockPos(pos.m_123341_(), pos.m_123342_(), pos.m_123343_());
               boolean flag = !simpleLogic || OneFruitWorldData.get().dropOneFruit(df, player, player.m_9236_().m_46472_().m_135782_(), blockPos, "Dropped from leaves. Sheared by " + player.m_5446_().getString());
               if (flag) {
                  ItemStack stack = df.m_7968_();
                  ItemEntity item = new ItemEntity(level, (double)pos.m_123341_(), (double)pos.m_123342_(), (double)pos.m_123343_(), stack);
                  level.m_7967_(item);
               }
            }
         }
      }

   }

   public static boolean cancelMouseClick(Screen screen) {
      if (screen instanceof AbstractContainerScreen containerScreen) {
         ItemStack cap = containerScreen.m_6262_().m_142621_();
         if (!(cap.m_41720_() instanceof AkumaNoMiItem)) {
            return false;
         }

         Slot slot = containerScreen.getSlotUnderMouse();
         if (slot == null) {
            return false;
         }

         if (slot.m_7543_() != null && slot.m_7543_().equals(OFFHAND_TEXTURE)) {
            return true;
         }

         if (screen instanceof CreativeModeInventoryScreen || screen instanceof InventoryScreen) {
            return false;
         }

         int total = containerScreen.m_6262_().f_38839_.size();
         int current = slot.f_40219_;
         int realSlotIndex = total - current;
         if (realSlotIndex > 35) {
            return true;
         }
      }

      return false;
   }

   public static boolean cancelMouseReleaseEvent(Screen screen) {
      if (screen instanceof AbstractContainerScreen containerScreen) {
         ItemStack cap = containerScreen.m_6262_().m_142621_();
         if (!(cap.m_41720_() instanceof AkumaNoMiItem)) {
            return false;
         }

         Slot slot = containerScreen.getSlotUnderMouse();
         if (slot == null) {
            return false;
         }

         if (slot.m_7543_() != null && slot.m_7543_().equals(OFFHAND_TEXTURE)) {
            return true;
         }

         if (screen instanceof CreativeModeInventoryScreen || screen instanceof InventoryScreen) {
            return false;
         }

         int total = containerScreen.m_6262_().f_38839_.size();
         int current = slot.f_40219_;
         int realSlotIndex = total - current;
         if (realSlotIndex > 35) {
            return true;
         }
      }

      return false;
   }

   public static void containerClose(InventoryMenu inv, Player player) {
      int containerSlots = inv.f_38839_.size() - player.m_150109_().f_35974_.size();

      for(int i = 0; i < containerSlots; ++i) {
         Slot slot = (Slot)inv.f_38839_.get(i);
         if (slot.m_6657_() && slot.m_7993_().m_41720_() instanceof AkumaNoMiItem) {
            player.m_36176_(slot.m_7993_().m_41777_(), true);
            slot.m_6201_(1);
         }
      }

      dropFruitsFromNearbyContainers(player);
   }

   public static void playerDeath(ServerPlayer player) {
      IDevilFruit fruitProps = (IDevilFruit)DevilFruitCapability.get(player).orElse(new DevilFruitBase(player));
      OneFruitWorldData worldData = OneFruitWorldData.get();
      boolean fruitRespawned = DevilFruitHelper.respawnDevilFruit(player, fruitProps);
      if (!fruitRespawned) {
         if ((!fruitProps.hasDevilFruit(ModFruits.YOMI_YOMI_NO_MI) || ((MorphInfo)ModMorphs.YOMI_SKELETON.get()).isActive(player)) && DevilFruitHelper.canDevilFruitRespawn() && fruitProps.getDevilFruit().isPresent()) {
            worldData.lostOneFruit((ResourceLocation)fruitProps.getDevilFruit().get(), player, "User's death");
         }

         if (fruitProps.hasYamiPower() && DevilFruitHelper.canDevilFruitRespawn()) {
            ResourceLocation key = ForgeRegistries.ITEMS.getKey((Item)ModFruits.YAMI_YAMI_NO_MI.get());
            worldData.lostOneFruit(key, player, "User died");
         }

         ArrayList<ItemStack> slots = new ArrayList();
         slots.addAll(player.m_150109_().f_35974_);
         slots.addAll(player.m_150109_().f_35976_);

         for(ItemStack invStack : slots) {
            if (invStack != null) {
               Item var8 = invStack.m_41720_();
               if (var8 instanceof AkumaNoMiItem) {
                  AkumaNoMiItem item = (AkumaNoMiItem)var8;
                  ResourceLocation key = ForgeRegistries.ITEMS.getKey(invStack.m_41720_());
                  if (worldData.isFruitInUse(key)) {
                     invStack.m_41774_(invStack.m_41613_());
                  } else if (!player.m_9236_().m_46469_().m_46207_(GameRules.f_46133_)) {
                     BlockPos blockPos = new BlockPos(player.m_146903_(), player.m_146904_(), player.m_146907_());
                     worldData.dropOneFruit(item, player, player.m_9236_().m_46472_().m_135782_(), blockPos, (String)null);
                  }
               }
            }
         }
      }

   }

   public static void playerLogin(ServerPlayer player) {
      OneFruitWorldData worldData = OneFruitWorldData.get();
      IDevilFruit fruitData = (IDevilFruit)DevilFruitCapability.get(player).orElse(new DevilFruitBase(player));
      IAbilityData abilityData = (IAbilityData)AbilityCapability.get(player).orElse(new AbilityDataBase(player));
      if (ServerConfig.getDaysForInactivity() > 0) {
         for(OneFruitEntry entry : worldData.getOneFruitEntries()) {
            Date date = entry.getLastUpdate();
            long diff = WyHelper.getDaysSince(date);
            if (diff >= (long)ServerConfig.getDaysForInactivity()) {
               worldData.lostOneFruit(entry.getKey(), (LivingEntity)null, "Inactivity for " + diff + " days");
            }

            boolean somebodyElseHasFruit = entry.getOwner().isPresent() && !((UUID)entry.getOwner().get()).equals(player.m_20148_());
            boolean nobodyHasFruit = !entry.getOwner().isPresent() && entry.getStatus() == OneFruitEntry.Status.LOST;
            if ((somebodyElseHasFruit || nobodyHasFruit) && fruitData.getDevilFruit().isPresent() && entry.getKey().equals(fruitData.getDevilFruit().get())) {
               fruitData.removeDevilFruit();
               abilityData.clearUnlockedAbilities(AbilityCategory.DEVIL_FRUITS.isCorePartofCategory());
               abilityData.clearPassiveAbilities(AbilityCategory.DEVIL_FRUITS.isAbilityPartofCategory());
               abilityData.clearEquippedAbilities(AbilityCategory.DEVIL_FRUITS.isAbilityPartofCategory());
               ModNetwork.sendTo(new SSyncDevilFruitPacket(player, fruitData), player);
               player.m_213846_(Component.m_237115_(ModI18n.SYSTEM_MESSAGE_OFPW_INACTIVITY.getString()));
            }
         }
      }

      checkPlayerInventory(player);
   }

   public static void playerLogout(Player player) {
      IDevilFruit dfProps = (IDevilFruit)DevilFruitCapability.get(player).orElse(new DevilFruitBase(player));
      OneFruitWorldData worldData = OneFruitWorldData.get();
      List<ResourceLocation> fruits = new ArrayList();
      if (dfProps.hasAnyDevilFruit()) {
         fruits.add((ResourceLocation)dfProps.getDevilFruit().get());
      }

      if (dfProps.hasYamiPower()) {
         fruits.add(ForgeRegistries.ITEMS.getKey((Item)ModFruits.YAMI_YAMI_NO_MI.get()));
      }

      Inventory inv = player.m_150109_();

      for(ItemStack stack : inv.f_35974_) {
         if (stack.m_41720_() instanceof AkumaNoMiItem) {
            fruits.add(ForgeRegistries.ITEMS.getKey(stack.m_41720_()));
         }
      }

      if (!fruits.isEmpty()) {
         for(ResourceLocation fruit : fruits) {
            worldData.forceUpdateOneFruit(fruit);
         }
      }

   }

   public static boolean cancelPickUpExtended(Player player, ItemStack stack) {
      if (stack.m_41720_() == Items.f_42265_ && stack.m_41782_()) {
         ListTag items = stack.m_41784_().m_128469_("BlockEntityTag").m_128437_("Items", 10);

         for(int i = 0; i < items.size(); ++i) {
            CompoundTag itemNBT = items.m_128728_(i);
            String itemId = itemNBT.m_128461_("id");
            Item item = (Item)ForgeRegistries.ITEMS.getValue(ResourceLocation.m_135820_(itemId));
            if (item != null && item instanceof AkumaNoMiItem) {
               items.remove(i);
            }
         }
      } else {
         Item items = stack.m_41720_();
         if (items instanceof AkumaNoMiItem) {
            AkumaNoMiItem devilFruit = (AkumaNoMiItem)items;
            IDevilFruit devilFruitProps = (IDevilFruit)DevilFruitCapability.get(player).orElse(new DevilFruitBase(player));
            int fruitsFound = ItemsHelper.getAllInventoryItems(player).stream().filter((itemStack) -> itemStack.m_41720_() instanceof AkumaNoMiItem).toList().size();
            boolean canYamiPickupFruit = devilFruitProps.getDevilFruit().equals("yami_yami") && ServerConfig.isYamiPowerEnabled() && fruitsFound == 0;
            if (devilFruitProps.hasAnyDevilFruit() && ServerConfig.getUnableToPickDFAsUser() && !canYamiPickupFruit) {
               return true;
            }

            if (DevilFruitHelper.hasDFLimitInInventory(player)) {
               return true;
            }

            OneFruitWorldData worldProps = OneFruitWorldData.get();
            return !worldProps.inventoryOneFruit(devilFruit, player, "Picked up from ground");
         }

         checkPlayerInventory(player);
      }

      return false;
   }

   public static boolean cancelPickUpSimple(Player player, ItemStack stack) {
      Item var3 = stack.m_41720_();
      if (var3 instanceof AkumaNoMiItem devilFruit) {
         OneFruitWorldData worldProps = OneFruitWorldData.get();
         checkPlayerInventory(player);
         return !worldProps.inventoryOneFruit(devilFruit, player, "Picked up from ground");
      } else {
         return false;
      }
   }

   public static boolean cancelDrop(AkumaNoMiEntity entity) {
      Entity owner = entity.m_19749_();
      Player player = null;
      if (owner instanceof Player pl) {
         player = pl;
      }

      OneFruitWorldData worldProps = OneFruitWorldData.get();
      BlockPos blockPos = new BlockPos(entity.m_146903_(), entity.m_146904_(), entity.m_146907_());
      AkumaNoMiItem var10001 = (AkumaNoMiItem)entity.m_32055_().m_41720_();
      ResourceLocation var10003 = entity.m_9236_().m_46472_().m_135782_();
      String var10005 = player == null ? "" : "by " + player.m_5446_().getString();
      return !worldProps.dropOneFruit(var10001, player, var10003, blockPos, "Fruit got dropped " + var10005, true);
   }

   public static void expire(AkumaNoMiItem devilFruit) {
      WyDebug.debug(String.valueOf(devilFruit.getRegistryKey()) + " has expired");
      OneFruitWorldData worldData = OneFruitWorldData.get();
      worldData.lostOneFruit(ForgeRegistries.ITEMS.getKey(devilFruit), (LivingEntity)null, "Item Entity expired on ground");
   }

   public static void unloadInstant(AkumaNoMiItem devilFruit) {
      OneFruitWorldData worldData = OneFruitWorldData.get();
      WyDebug.debug("Instant removed " + String.valueOf(devilFruit.getRegistryKey()));
      worldData.lostOneFruit(devilFruit.getRegistryKey(), (LivingEntity)null, "Unloaded by Chunk");
   }

   public static void unloadRealistic(AkumaNoMiItem devilFruit) {
      UnloadFruitTimeStampsData worldData = UnloadFruitTimeStampsData.get();
      WyDebug.debug("Set unload time for fruit " + String.valueOf(devilFruit.getRegistryKey()));
      worldData.addUnloadTime(devilFruit.getRegistryKey(), Instant.now().plus(Duration.ofMinutes(1L)).toEpochMilli());
   }

   public static boolean cleanup(AkumaNoMiEntity fruitEntity) {
      OneFruitWorldData worldData = OneFruitWorldData.get();
      AkumaNoMiItem devilFruit = (AkumaNoMiItem)fruitEntity.m_32055_().m_41720_();
      OneFruitEntry fruitEntry = worldData.getOneFruitEntry(devilFruit.getRegistryKey());
      if (fruitEntry != null && fruitEntry.getStatus() == OneFruitEntry.Status.LOST) {
         WyDebug.debug(String.valueOf(devilFruit.getRegistryKey()) + " destroyed");
         return true;
      } else {
         return false;
      }
   }

   public static void tick() {
      UnloadFruitTimeStampsData unloadTimesData = UnloadFruitTimeStampsData.get();
      List<ResourceLocation> toRemove = new ArrayList();
      unloadTimesData.getUnloadTimes().forEach((fruit, time) -> {
         ResourceLocation fruitResource = ResourceLocation.m_135820_(fruit);
         if (fruitResource != null) {
            if (Instant.now().toEpochMilli() > time) {
               OneFruitWorldData oneFruitWorldData = OneFruitWorldData.get();
               WyDebug.debug("Unloaded fruit " + String.valueOf(fruitResource));
               toRemove.add(fruitResource);
               oneFruitWorldData.lostOneFruit(fruitResource, (LivingEntity)null, "Unloaded by Chunk");
            }

         }
      });
      Objects.requireNonNull(unloadTimesData);
      toRemove.forEach(unloadTimesData::removeUnloadTime);
   }

   public static void dropFruitsFromNearbyContainers(Player player) {
      for(BlockPos pos : WyHelper.getNearbyTileEntities(player, 40)) {
         BlockEntity te = player.m_9236_().m_7702_(pos);
         if (te instanceof Container container) {
            for(int i = 0; i < container.m_6643_(); ++i) {
               ItemStack stack = container.m_8020_(i);
               if (stack != null && stack.m_41720_() instanceof AkumaNoMiItem) {
                  player.m_36176_(stack.m_41777_(), false);
                  stack.m_41774_(stack.m_41613_());
               }
            }
         }
      }

   }

   public static void checkPlayerInventory(Player player) {
      OneFruitWorldData worldProps = OneFruitWorldData.get();
      IDevilFruit fruitData = (IDevilFruit)DevilFruitCapability.get(player).orElse(new DevilFruitBase(player));
      ArrayList<ItemStack> slots = new ArrayList();
      slots.addAll(player.m_150109_().f_35974_);
      slots.addAll(player.m_150109_().f_35976_);
      int fruitsFound = 0;

      for(ItemStack stack : slots) {
         if (stack != null && stack.m_41720_() instanceof AkumaNoMiItem) {
            ResourceLocation key = ForgeRegistries.ITEMS.getKey(stack.m_41720_());
            ++fruitsFound;
            if (worldProps.isFruitInUse(key)) {
               stack.m_41774_(stack.m_41613_());
            } else if (worldProps.isFruitDuped(key, player.m_20148_())) {
               stack.m_41774_(stack.m_41613_());
            } else if (fruitData.hasAnyDevilFruit() && ServerConfig.getUnableToPickDFAsUser()) {
               if (!fruitData.hasDevilFruit((AkumaNoMiItem)ModFruits.YAMI_YAMI_NO_MI.get()) || !ServerConfig.isYamiPowerEnabled() || fruitsFound != 1) {
                  if (!worldProps.isFruitDuped(key, player.m_20148_())) {
                     worldProps.lostOneFruit(key, (LivingEntity)null, "Cannot pick up extra fruits");
                  }

                  stack.m_41774_(stack.m_41613_());
               }
            } else if (fruitsFound > ServerConfig.getInventoryLimitForFruits()) {
               if (!worldProps.isFruitDuped(key, player.m_20148_())) {
                  worldProps.lostOneFruit(key, (LivingEntity)null, "Cannot pick up more than " + ServerConfig.getInventoryLimitForFruits() + " fruits");
               }

               stack.m_41774_(stack.m_41613_());
            }
         }
      }

   }

   static {
      OFFHAND_TEXTURE = Pair.of(InventoryMenu.f_39692_, InventoryMenu.f_39697_);
   }
}
