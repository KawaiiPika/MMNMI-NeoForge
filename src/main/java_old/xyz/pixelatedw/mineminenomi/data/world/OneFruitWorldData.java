package xyz.pixelatedw.mineminenomi.data.world;

import com.google.common.base.Strings;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.server.ServerLifecycleHooks;
import org.jetbrains.annotations.Nullable;
import xyz.pixelatedw.mineminenomi.api.OneFruitEntry;
import xyz.pixelatedw.mineminenomi.api.events.onefruit.DroppedDevilFruitEvent;
import xyz.pixelatedw.mineminenomi.api.events.onefruit.InventoryDevilFruitEvent;
import xyz.pixelatedw.mineminenomi.api.events.onefruit.LostDevilFruitEvent;
import xyz.pixelatedw.mineminenomi.config.ServerConfig;
import xyz.pixelatedw.mineminenomi.items.AkumaNoMiItem;

public class OneFruitWorldData extends SavedData {
   private static final String IDENTIFIER = "mineminenomi-onefruit";
   private List<OneFruitEntry> oneFruitEntries = new ArrayList();

   public static OneFruitWorldData get() {
      MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
      ServerLevel serverWorld = server.m_129783_();
      OneFruitWorldData worldData = (OneFruitWorldData)serverWorld.m_8895_().m_164861_(OneFruitWorldData::load, OneFruitWorldData::new, "mineminenomi-onefruit");
      return worldData;
   }

   public static OneFruitWorldData load(CompoundTag nbt) {
      OneFruitWorldData data = new OneFruitWorldData();
      ListTag oneFruit = nbt.m_128437_("oneFruitList", 10);
      data.oneFruitEntries.clear();

      for(int i = 0; i < oneFruit.size(); ++i) {
         CompoundTag entryNBT = oneFruit.m_128728_(i);
         OneFruitEntry entry = OneFruitEntry.from(entryNBT);
         data.oneFruitEntries.add(entry);
      }

      return data;
   }

   public CompoundTag m_7176_(CompoundTag nbt) {
      ListTag oneFruitList = new ListTag();
      if (this.oneFruitEntries.size() > 0) {
         for(OneFruitEntry entry : this.oneFruitEntries) {
            oneFruitList.add(entry.save());
         }
      }

      nbt.m_128365_("oneFruitList", oneFruitList);
      return nbt;
   }

   public List<OneFruitEntry> getOneFruitEntries() {
      return this.oneFruitEntries;
   }

   public @Nullable OneFruitEntry getOneFruitEntry(ResourceLocation key) {
      Optional<OneFruitEntry> oneFruit = this.oneFruitEntries.stream().filter((entry) -> entry.getKey().equals(key)).findFirst();
      return oneFruit.isPresent() ? (OneFruitEntry)oneFruit.get() : null;
   }

   public boolean isFruitAvailable(AkumaNoMiItem item) {
      return this.isFruitAvailable(item.getRegistryKey());
   }

   public boolean isFruitAvailable(ResourceLocation id) {
      Optional<OneFruitEntry> oneFruitEntry = this.oneFruitEntries.stream().filter((entry) -> entry.getKey().equals(id)).findFirst();
      if (!oneFruitEntry.isPresent()) {
         return true;
      } else {
         return oneFruitEntry.isPresent() && ((OneFruitEntry)oneFruitEntry.get()).getStatus() == OneFruitEntry.Status.LOST;
      }
   }

   public boolean isFruitInWorld(ResourceLocation key) {
      return this.oneFruitEntries.stream().anyMatch((entry) -> entry.getKey().equals(key) && entry.getStatus() != OneFruitEntry.Status.LOST);
   }

   public boolean isFruitInUse(ResourceLocation key) {
      return this.oneFruitEntries.stream().anyMatch((entry) -> entry.getKey().equals(key) && entry.getStatus() == OneFruitEntry.Status.IN_USE);
   }

   public boolean isFruitDuped(ResourceLocation key, UUID uuid) {
      return this.oneFruitEntries.stream().anyMatch((entry) -> {
         boolean keyCheck = entry.getKey().equals(key) && entry.getStatus() != OneFruitEntry.Status.LOST;
         boolean ownerCheck = entry.getOwner().isPresent() && !((UUID)entry.getOwner().get()).equals(uuid);
         return keyCheck && ownerCheck;
      });
   }

   private boolean addOneFruit(ResourceLocation key, @Nullable UUID uuid, OneFruitEntry.Status status) {
      return this.addOneFruit(key, uuid, status, (ResourceLocation)null, (BlockPos)null, (String)null);
   }

   private boolean addOneFruit(ResourceLocation key, @Nullable UUID uuid, OneFruitEntry.Status status, BlockPos blockPos) {
      return this.addOneFruit(key, uuid, status, (ResourceLocation)null, blockPos, (String)null);
   }

   private boolean addOneFruit(ResourceLocation key, @Nullable UUID uuid, OneFruitEntry.Status status, ResourceLocation dimensionId, BlockPos blockPos) {
      return this.addOneFruit(key, uuid, status, dimensionId, blockPos, (String)null);
   }

   private boolean addOneFruit(ResourceLocation key, @Nullable UUID uuid, OneFruitEntry.Status status, @Nullable ResourceLocation dimensionId, @Nullable BlockPos blockPos, @Nullable String message) {
      if (key != null && ServerConfig.hasOneFruitPerWorldSimpleLogic()) {
         if (this.isFruitInWorld(key)) {
            return false;
         } else {
            OneFruitEntry entry = new OneFruitEntry(key, uuid, status, blockPos, dimensionId, message);
            this.oneFruitEntries.add(entry);
            this.m_77762_();
            return true;
         }
      } else {
         return false;
      }
   }

   public boolean updateOneFruit(ResourceLocation key, @Nullable UUID uuid, OneFruitEntry.Status status) {
      return this.updateOneFruit(key, uuid, status, (ResourceLocation)null, (BlockPos)null, (String)null, false);
   }

   public boolean updateOneFruit(ResourceLocation key, @Nullable UUID uuid, OneFruitEntry.Status status, @Nullable String message) {
      return this.updateOneFruit(key, uuid, status, (ResourceLocation)null, (BlockPos)null, message, false);
   }

   public boolean updateOneFruit(ResourceLocation key, @Nullable UUID uuid, OneFruitEntry.Status status, @Nullable BlockPos blockPos, @Nullable String message) {
      return this.updateOneFruit(key, uuid, status, (ResourceLocation)null, blockPos, message, false);
   }

   public boolean updateOneFruit(ResourceLocation key, @Nullable UUID uuid, OneFruitEntry.Status status, @Nullable BlockPos blockPos, @Nullable ResourceLocation dimensionId, String message) {
      return this.updateOneFruit(key, uuid, status, dimensionId, blockPos, message, false);
   }

   public boolean updateOneFruit(ResourceLocation key, @Nullable UUID uuid, OneFruitEntry.Status status, @Nullable ResourceLocation dimensionId, @Nullable BlockPos blockPos, @Nullable String message, boolean force) {
      if (key != null && ServerConfig.hasOneFruitPerWorldSimpleLogic()) {
         Optional<OneFruitEntry> oneFruit = this.oneFruitEntries.stream().filter((entry) -> entry.getKey().equals(key)).findFirst();
         if (!oneFruit.isPresent()) {
            this.addOneFruit(key, uuid, status, dimensionId, blockPos, message);
         } else {
            if (!force) {
               boolean sameOwner = ((OneFruitEntry)oneFruit.get()).getOwner().isPresent() && ((UUID)((OneFruitEntry)oneFruit.get()).getOwner().get()).equals(uuid);
               if (((OneFruitEntry)oneFruit.get()).getStatus() == OneFruitEntry.Status.IN_USE && (status != OneFruitEntry.Status.LOST || !sameOwner)) {
                  return false;
               }

               if (((OneFruitEntry)oneFruit.get()).getStatus() == OneFruitEntry.Status.INVENTORY && status == OneFruitEntry.Status.DROPPED && !sameOwner) {
                  return false;
               }

               if (((OneFruitEntry)oneFruit.get()).getStatus() == OneFruitEntry.Status.INVENTORY && status == OneFruitEntry.Status.INVENTORY && !sameOwner) {
                  return false;
               }

               if (((OneFruitEntry)oneFruit.get()).getStatus() == OneFruitEntry.Status.INVENTORY && status == OneFruitEntry.Status.IN_USE && !sameOwner) {
                  return false;
               }
            }

            if (!Strings.isNullOrEmpty(message)) {
               ((OneFruitEntry)oneFruit.get()).setStatusMessage(message);
            }

            ((OneFruitEntry)oneFruit.get()).update(uuid, status, dimensionId, blockPos);
            this.m_77762_();
         }

         return true;
      } else {
         return false;
      }
   }

   public void lostOneFruit(ResourceLocation key, @Nullable LivingEntity entity, String message) {
      if (key != null && ServerConfig.hasOneFruitPerWorldSimpleLogic()) {
         Optional<OneFruitEntry> oneFruit = this.oneFruitEntries.stream().filter((entry) -> entry.getKey().equals(key)).findFirst();
         if (oneFruit.isPresent()) {
            ((OneFruitEntry)oneFruit.get()).setStatusMessage(message);
            ((OneFruitEntry)oneFruit.get()).update(entity != null ? entity.m_20148_() : null, OneFruitEntry.Status.LOST, (ResourceLocation)null, (BlockPos)null);
            this.m_77762_();
            Item item = (Item)ForgeRegistries.ITEMS.getValue(((OneFruitEntry)oneFruit.get()).getKey());
            LostDevilFruitEvent lostEvent = new LostDevilFruitEvent(entity, item, message);
            MinecraftForge.EVENT_BUS.post(lostEvent);
         }

      }
   }

   public boolean dropOneFruit(AkumaNoMiItem item, @Nullable LivingEntity entity, ResourceLocation dimensionId, BlockPos blockPos, @Nullable String message) {
      return this.dropOneFruit(item, entity, dimensionId, blockPos, message, false);
   }

   public boolean dropOneFruit(AkumaNoMiItem item, @Nullable LivingEntity entity, ResourceLocation dimensionId, BlockPos blockPos, @Nullable String message, boolean force) {
      DroppedDevilFruitEvent droppedEvent = new DroppedDevilFruitEvent(entity, item, dimensionId, blockPos, message);
      MinecraftForge.EVENT_BUS.post(droppedEvent);
      OneFruitWorldData worldData = get();
      return worldData.updateOneFruit(item.getRegistryKey(), entity != null ? entity.m_20148_() : null, OneFruitEntry.Status.DROPPED, dimensionId, blockPos, message, force);
   }

   public boolean inventoryOneFruit(AkumaNoMiItem item, @Nullable LivingEntity entity, @Nullable String message) {
      return this.inventoryOneFruit(item, entity, message, false);
   }

   public boolean inventoryOneFruit(AkumaNoMiItem item, @Nullable LivingEntity entity, @Nullable String message, boolean force) {
      InventoryDevilFruitEvent inventoryEvent = new InventoryDevilFruitEvent(entity, item, message);
      MinecraftForge.EVENT_BUS.post(inventoryEvent);
      OneFruitWorldData worldData = get();
      return worldData.updateOneFruit(item.getRegistryKey(), entity != null ? entity.m_20148_() : null, OneFruitEntry.Status.INVENTORY, (ResourceLocation)null, (BlockPos)null, message, force);
   }

   public void forceUpdateOneFruit(ResourceLocation key) {
      if (key != null && ServerConfig.hasOneFruitPerWorldSimpleLogic()) {
         Optional<OneFruitEntry> oneFruit = this.oneFruitEntries.stream().filter((entry) -> entry.getKey().equals(key)).findFirst();
         if (oneFruit.isPresent()) {
            ((OneFruitEntry)oneFruit.get()).forceLastUpdateNow();
            this.m_77762_();
         }

      }
   }

   public void wipeOneFruitHistory(ResourceLocation key) {
      if (key != null && ServerConfig.hasOneFruitPerWorldSimpleLogic()) {
         Optional<OneFruitEntry> oneFruit = this.oneFruitEntries.stream().filter((entry) -> entry.getKey().equals(key)).findFirst();
         if (oneFruit.isPresent()) {
            ((OneFruitEntry)oneFruit.get()).getHistory().clear();
            this.m_77762_();
         }

      }
   }
}
