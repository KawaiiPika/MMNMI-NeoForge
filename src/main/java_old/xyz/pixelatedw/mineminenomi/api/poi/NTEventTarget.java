package xyz.pixelatedw.mineminenomi.api.poi;

import java.util.Optional;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.data.world.NPCWorldData;

public class NTEventTarget extends POIEventTarget {
   private TrackedNPC tracked;

   public NTEventTarget() {
   }

   public NTEventTarget(ServerLevel world, Vec3 pos, long openTime, TrackedNPC tracked) {
      super(world, pos, openTime);
      this.setTracked(tracked);
   }

   private void setTracked(TrackedNPC tracked) {
      this.tracked = tracked;
      this.setTriggerAction((world, poi) -> this.tracked.spawnTrackedMob(world, poi.getPosition()));
   }

   public CompoundTag save() {
      CompoundTag tag = super.save();
      tag.m_128356_("trackedId", this.tracked.getId());
      return tag;
   }

   public void load(CompoundTag tag) {
      super.load(tag);
      long id = tag.m_128454_("trackedId");
      Optional<TrackedNPC> tracked = NPCWorldData.get().getTrackedMob(id);
      if (tracked.isPresent()) {
         this.setTracked((TrackedNPC)tracked.get());
      }

   }

   public TrackedNPC getTrackedNPC() {
      return this.tracked;
   }
}
