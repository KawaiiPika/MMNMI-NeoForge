package xyz.pixelatedw.mineminenomi.api;

import com.google.common.base.Strings;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

public class OneFruitEntry {
   private ResourceLocation fruitKey;
   private Optional<UUID> owner = Optional.empty();
   private Status status;
   private String statusMessage = "";
   private Optional<BlockPos> blockPos = Optional.empty();
   private Optional<ResourceLocation> dimension = Optional.empty();
   private Date lastUpdate;
   private List<HistoryEntry> history = new ArrayList();

   private OneFruitEntry() {
   }

   public OneFruitEntry(ResourceLocation key, @Nullable UUID owner, Status status, @Nullable BlockPos blockPos, @Nullable ResourceLocation dimensionId, @Nullable String message) {
      this.fruitKey = key;
      if (!Strings.isNullOrEmpty(message)) {
         this.setStatusMessage(message);
      }

      this.blockPos = Optional.ofNullable(blockPos);
      this.setBlockPos(blockPos);
      this.dimension = Optional.ofNullable(dimensionId);
      this.setDimensionLocation(dimensionId);
      this.update(owner, status, dimensionId, blockPos);
   }

   public static OneFruitEntry from(CompoundTag nbt) {
      OneFruitEntry entry = new OneFruitEntry();
      entry.load(nbt);
      return entry;
   }

   public ResourceLocation getKey() {
      return this.fruitKey;
   }

   public Optional<Item> getItemFromKey() {
      return Optional.ofNullable((Item)ForgeRegistries.ITEMS.getValue(this.fruitKey));
   }

   public Optional<UUID> getOwner() {
      return this.owner;
   }

   public void setStatusMessage(String message) {
      this.statusMessage = message;
   }

   public void setBlockPos(BlockPos pos) {
      this.blockPos = Optional.ofNullable(pos);
   }

   public Optional<BlockPos> getBlockPos() {
      return this.blockPos;
   }

   public void setDimensionLocation(ResourceLocation dimensionLocation) {
      this.dimension = Optional.ofNullable(dimensionLocation);
   }

   public Optional<ResourceLocation> getDimensionLocation() {
      return this.dimension;
   }

   public Status getStatus() {
      return this.status;
   }

   public void update(UUID owner, Status status, ResourceLocation dimensionId, BlockPos blockPos) {
      this.owner = Optional.ofNullable(owner);
      this.status = status;
      this.lastUpdate = new Date();
      this.dimension = Optional.ofNullable(dimensionId);
      this.blockPos = Optional.ofNullable(blockPos);
      this.registerCurrentHistory();
   }

   public void forceLastUpdateNow() {
      this.lastUpdate = new Date();
   }

   public Date getLastUpdate() {
      return this.lastUpdate;
   }

   public void registerCurrentHistory() {
      this.history.add(new HistoryEntry(this.owner, this.status, this.statusMessage, this.dimension, this.blockPos, this.lastUpdate));
      this.statusMessage = "";
   }

   public List<HistoryEntry> getHistory() {
      return this.history;
   }

   public CompoundTag save() {
      CompoundTag nbt = new CompoundTag();
      nbt.m_128359_("fruit", this.fruitKey.toString());
      this.owner.ifPresent((owner) -> nbt.m_128362_("owner", (UUID)this.owner.get()));
      nbt.m_128359_("status", this.status.name());
      nbt.m_128356_("lastUpdate", this.lastUpdate.getTime());
      ListTag history = new ListTag();
      int limit = Math.min(1000, this.history.size());

      for(HistoryEntry entry : this.history.stream().skip((long)(this.history.size() - limit)).limit((long)limit).toList()) {
         CompoundTag entryNBT = entry.save();
         history.add(entryNBT);
      }

      nbt.m_128365_("history", history);
      return nbt;
   }

   public void load(CompoundTag nbt) {
      this.fruitKey = ResourceLocation.parse(nbt.m_128461_("fruit"));
      if (nbt.m_128403_("owner")) {
         this.owner = Optional.of(nbt.m_128342_("owner"));
      }

      this.status = OneFruitEntry.Status.valueOf(nbt.m_128461_("status"));
      this.lastUpdate = new Date(nbt.m_128454_("lastUpdate"));
      ListTag history = nbt.m_128437_("history", 10);
      this.history.clear();

      for(int i = 0; i < history.size(); ++i) {
         CompoundTag historyNBT = history.m_128728_(i);
         this.history.add(OneFruitEntry.HistoryEntry.from(historyNBT));
      }

   }

   public static enum Status {
      DROPPED,
      INVENTORY,
      IN_USE,
      LOST;

      // $FF: synthetic method
      private static Status[] $values() {
         return new Status[]{DROPPED, INVENTORY, IN_USE, LOST};
      }
   }

   public static class HistoryEntry {
      private Optional<UUID> owner = Optional.empty();
      private Status status;
      private String statusMessage = "";
      private Optional<BlockPos> blockPos = Optional.empty();
      private Optional<ResourceLocation> dimensionId = Optional.empty();
      private Date date;

      private HistoryEntry() {
      }

      public HistoryEntry(Optional<UUID> owner, Status status, String statusMessage, Optional<ResourceLocation> dimensionId, Optional<BlockPos> blockPos, Date date) {
         this.owner = owner;
         this.status = status;
         this.statusMessage = statusMessage;
         this.blockPos = blockPos;
         this.dimensionId = dimensionId;
         this.date = date;
      }

      public static HistoryEntry from(CompoundTag nbt) {
         HistoryEntry entry = new HistoryEntry();
         entry.load(nbt);
         return entry;
      }

      public Optional<UUID> getOwner() {
         return this.owner;
      }

      public Status getStatus() {
         return this.status;
      }

      public String getStatusMessage() {
         return this.statusMessage;
      }

      public Optional<BlockPos> getBlockPos() {
         return this.blockPos;
      }

      public Optional<ResourceLocation> getDimensionLocation() {
         return this.dimensionId;
      }

      public Date getDate() {
         return this.date;
      }

      public CompoundTag save() {
         CompoundTag nbt = new CompoundTag();
         this.owner.ifPresent((owner) -> nbt.m_128362_("owner", owner));
         this.blockPos.ifPresent((pos) -> {
            nbt.m_128405_("posX", pos.m_123341_());
            nbt.m_128405_("posY", pos.m_123342_());
            nbt.m_128405_("posZ", pos.m_123343_());
         });
         this.dimensionId.ifPresent((dimensionId) -> nbt.m_128359_("dimension", dimensionId.toString()));
         nbt.m_128359_("status", this.status.name());
         nbt.m_128359_("statusMessage", this.statusMessage);
         nbt.m_128356_("date", this.date.getTime());
         return nbt;
      }

      public void load(CompoundTag nbt) {
         if (nbt.m_128403_("uuid")) {
            this.owner = Optional.of(nbt.m_128342_("uuid"));
         }

         if (nbt.m_128441_("posX") && nbt.m_128441_("posY") && nbt.m_128441_("posZ")) {
            this.blockPos = Optional.of(new BlockPos(nbt.m_128451_("posX"), nbt.m_128451_("posY"), nbt.m_128451_("posZ")));
         }

         if (nbt.m_128441_("dimension")) {
            this.dimensionId = Optional.ofNullable(ResourceLocation.m_135820_(nbt.m_128461_("dimension")));
         }

         this.status = OneFruitEntry.Status.valueOf(nbt.m_128461_("status"));
         this.statusMessage = nbt.m_128461_("statusMessage");
         this.date = new Date(nbt.m_128454_("date"));
      }
   }
}
