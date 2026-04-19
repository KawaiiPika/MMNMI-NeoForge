package xyz.pixelatedw.mineminenomi.data.world;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraftforge.server.ServerLifecycleHooks;
import xyz.pixelatedw.mineminenomi.api.enums.SoulboundMark;

public class ExtendedWorldData extends SavedData {
   private static final String IDENTIFIER = "mineminenomi";
   private HashMap<UUID, SoulboundMark> linkedItemStatus = new HashMap();

   public static ExtendedWorldData get() {
      MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
      ServerLevel serverWorld = server.m_129783_();
      ExtendedWorldData worldData = (ExtendedWorldData)serverWorld.m_8895_().m_164861_(ExtendedWorldData::load, ExtendedWorldData::new, "mineminenomi");
      return worldData;
   }

   public static ExtendedWorldData load(CompoundTag nbt) {
      ExtendedWorldData data = new ExtendedWorldData();
      CompoundTag linkedItemMarksNBT = nbt.m_128469_("linkedItemMarks");
      data.linkedItemStatus.clear();
      linkedItemMarksNBT.m_128431_().stream().forEach((key) -> {
         SoulboundMark markType = SoulboundMark.values()[linkedItemMarksNBT.m_128451_(key)];
         data.linkedItemStatus.put(UUID.fromString(key), markType);
      });
      return data;
   }

   public CompoundTag m_7176_(CompoundTag nbt) {
      CompoundTag linkedItemMarksNBT = new CompoundTag();
      if (this.linkedItemStatus.size() > 0) {
         for(Map.Entry<UUID, SoulboundMark> entry : this.linkedItemStatus.entrySet()) {
            linkedItemMarksNBT.m_128405_(((UUID)entry.getKey()).toString(), ((SoulboundMark)entry.getValue()).ordinal());
         }
      }

      nbt.m_128365_("linkedItemMarks", linkedItemMarksNBT);
      return nbt;
   }

   public void markDead(UUID id) {
      this.linkedItemStatus.put(id, SoulboundMark.DEATH);
      this.m_77762_();
   }

   public void markRestore(UUID id, SoulboundMark mark) {
      this.linkedItemStatus.put(id, mark);
      this.m_77762_();
   }

   public void removeMark(UUID id) {
      this.linkedItemStatus.remove(id);
      this.m_77762_();
   }

   public boolean isOnDeathList(UUID id) {
      return this.linkedItemStatus.get(id) == SoulboundMark.DEATH;
   }

   public boolean isOnRestoreList(UUID id, SoulboundMark mark) {
      return this.linkedItemStatus.get(id) == mark;
   }
}
