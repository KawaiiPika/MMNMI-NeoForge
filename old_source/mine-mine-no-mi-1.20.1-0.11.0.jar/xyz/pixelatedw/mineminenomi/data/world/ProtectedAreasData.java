package xyz.pixelatedw.mineminenomi.data.world;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.saveddata.SavedData;
import org.jetbrains.annotations.Nullable;
import xyz.pixelatedw.mineminenomi.api.protection.ProtectedArea;

public class ProtectedAreasData extends SavedData {
   private static final String IDENTIFIER = "mineminenomi-protected-areas";
   private HashMap<String, ProtectedArea> abilityProtections = new HashMap();
   private final ServerLevel level;

   private ProtectedAreasData(ServerLevel level) {
      this.level = level;
   }

   public static ProtectedAreasData get(ServerLevel level) {
      Function<CompoundTag, ProtectedAreasData> load = (tag) -> load(tag, level);
      Supplier<ProtectedAreasData> constructor = () -> new ProtectedAreasData(level);
      return (ProtectedAreasData)level.m_8895_().m_164861_(load, constructor, "mineminenomi-protected-areas");
   }

   public static ProtectedAreasData load(CompoundTag nbt, ServerLevel level) {
      ProtectedAreasData data = new ProtectedAreasData(level);
      ListTag protectedAreas = nbt.m_128437_("protectedAreas", 10);
      data.abilityProtections.clear();

      for(int i = 0; i < protectedAreas.size(); ++i) {
         CompoundTag entryNBT = protectedAreas.m_128728_(i);
         ProtectedArea area = ProtectedArea.from(entryNBT);
         data.abilityProtections.put(area.getLabel(), area);
      }

      return data;
   }

   public CompoundTag m_7176_(CompoundTag nbt) {
      ListTag protectedAreas = new ListTag();
      if (this.abilityProtections.size() > 0) {
         for(Map.Entry<String, ProtectedArea> entry : this.abilityProtections.entrySet()) {
            CompoundTag entryNBT = ((ProtectedArea)entry.getValue()).save();
            protectedAreas.add(entryNBT);
         }
      }

      nbt.m_128365_("protectedAreas", protectedAreas);
      return nbt;
   }

   public void tick() {
      this.level.m_46473_().m_6180_("world restoration");

      for(ProtectedArea area : this.getAllRestrictions().values()) {
         area.restoreBlocks(this.level);
      }

      this.level.m_46473_().m_7238_();
   }

   public boolean isInsideRestrictedArea(int posX, int posY, int posZ) {
      if (this.abilityProtections.size() <= 0) {
         return false;
      } else {
         for(ProtectedArea area : this.abilityProtections.values()) {
            if (area.isInside(this.level, posX, posY, posZ)) {
               return true;
            }
         }

         return false;
      }
   }

   public @Nullable ProtectedArea getProtectedArea(String label) {
      return (ProtectedArea)this.abilityProtections.get(label);
   }

   public Optional<ProtectedArea> getProtectedArea(int posX, int posY, int posZ) {
      return this.abilityProtections.values().stream().filter((area) -> area.isInside(this.level, posX, posY, posZ)).sorted((a1, a2) -> a1.getSize() > a2.getSize() ? 1 : -1).findFirst();
   }

   public void addRestrictedArea(ProtectedArea area) {
      this.abilityProtections.put(area.getLabel(), area);
      this.m_77762_();
   }

   public void addRestrictedArea(BlockPos center, int size, String label) {
      ProtectedArea area = new ProtectedArea(center, size, label);
      this.abilityProtections.put(area.getLabel(), area);
      this.m_77762_();
   }

   public void resizeRestrictedArea(String label, int size) {
      ((ProtectedArea)this.abilityProtections.get(label)).setSize(size);
      this.m_77762_();
   }

   public void removeRestrictedArea(String label) {
      this.abilityProtections.remove(label);
      this.m_77762_();
   }

   public void removeRestrictedArea(int midX, int midY, int midZ) {
      for(ProtectedArea area : this.abilityProtections.values()) {
         if (midX == area.getCenter().m_123341_() && midY == area.getCenter().m_123342_() && midZ == area.getCenter().m_123343_()) {
            this.abilityProtections.remove(area.getLabel());
            this.m_77762_();
            return;
         }
      }

   }

   public Map<String, ProtectedArea> getAllRestrictions() {
      return Collections.unmodifiableMap(this.abilityProtections);
   }
}
