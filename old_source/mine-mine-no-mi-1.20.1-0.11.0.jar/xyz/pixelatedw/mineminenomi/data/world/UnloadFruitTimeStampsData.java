package xyz.pixelatedw.mineminenomi.data.world;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraftforge.server.ServerLifecycleHooks;

public class UnloadFruitTimeStampsData extends SavedData {
   private static final String IDENTIFIER = "mineminenomi-unloaded-chunk-times";
   private Map<String, Long> unloadTimes = new HashMap();

   public static UnloadFruitTimeStampsData get() {
      MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
      ServerLevel serverWorld = server.m_129783_();
      return (UnloadFruitTimeStampsData)serverWorld.m_8895_().m_164861_(UnloadFruitTimeStampsData::load, UnloadFruitTimeStampsData::new, "mineminenomi-unloaded-chunk-times");
   }

   public void addUnloadTime(ResourceLocation resourceKey, long time) {
      this.unloadTimes.put(resourceKey.toString(), time);
      this.m_77762_();
   }

   public void removeUnloadTime(ResourceLocation resourceKey) {
      this.unloadTimes.remove(resourceKey.toString());
      this.m_77762_();
   }

   public long getUnloadTime(ResourceLocation resourceLocation) {
      return (Long)this.unloadTimes.get(resourceLocation.toString());
   }

   public Map<String, Long> getUnloadTimes() {
      return this.unloadTimes;
   }

   public CompoundTag m_7176_(CompoundTag tag) {
      CompoundTag timesTag = new CompoundTag();
      Map var10000 = this.unloadTimes;
      Objects.requireNonNull(timesTag);
      var10000.forEach(timesTag::m_128356_);
      tag.m_128365_("unloadTimeMap", timesTag);
      return tag;
   }

   public static UnloadFruitTimeStampsData load(CompoundTag nbt) {
      UnloadFruitTimeStampsData data = new UnloadFruitTimeStampsData();
      CompoundTag timesTag = nbt.m_128469_("unloadTimeMap");
      timesTag.m_128431_().forEach((key) -> data.unloadTimes.put(key, timesTag.m_128454_(key)));
      return data;
   }
}
