package xyz.pixelatedw.mineminenomi.api.poi;

import javax.annotation.Nullable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.phys.Vec3;

public class POIEventTarget {
   private ServerLevel level;
   private Vec3 pos;
   private long startTime;
   private long openTime;
   private EventTriggerAction triggerAction;

   public POIEventTarget() {
   }

   public POIEventTarget(ServerLevel level, Vec3 pos, long openTime) {
      this.level = level;
      this.pos = pos;
      this.startTime = level.m_46467_();
      this.openTime = openTime;
   }

   public ServerLevel getLevel() {
      return this.level;
   }

   public Vec3 getPosition() {
      return this.pos;
   }

   public long getStartTime() {
      return this.startTime;
   }

   public boolean shouldTrigger(ServerPlayer player) {
      return this.getTriggerAction() != null && player.m_20238_(this.getPosition()) < (double)10000.0F;
   }

   @Nullable
   public EventTriggerAction getTriggerAction() {
      return this.triggerAction;
   }

   public void setTriggerAction(EventTriggerAction event) {
      this.triggerAction = event;
   }

   public boolean hasExpired(ServerLevel world) {
      return world.m_46467_() > this.startTime + this.openTime;
   }

   public void tick() {
   }

   public CompoundTag save() {
      CompoundTag tag = new CompoundTag();
      tag.m_128347_("x", this.pos.f_82479_);
      tag.m_128347_("y", this.pos.f_82480_);
      tag.m_128347_("z", this.pos.f_82481_);
      tag.m_128356_("startTime", this.startTime);
      tag.m_128356_("openTime", this.openTime);
      return tag;
   }

   public void load(CompoundTag tag) {
      double x = tag.m_128459_("x");
      double y = tag.m_128459_("y");
      double z = tag.m_128459_("z");
      Vec3 pos = new Vec3(x, y, z);
      this.pos = pos;
      this.startTime = tag.m_128454_("startTime");
      this.openTime = tag.m_128454_("openTime");
   }

   @FunctionalInterface
   public interface EventTriggerAction {
      void trigger(ServerLevel var1, POIEventTarget var2);
   }
}
