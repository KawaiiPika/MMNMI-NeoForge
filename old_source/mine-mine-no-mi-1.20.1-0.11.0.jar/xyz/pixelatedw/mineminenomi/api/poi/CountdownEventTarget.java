package xyz.pixelatedw.mineminenomi.api.poi;

import java.util.Optional;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class CountdownEventTarget extends POIEventTarget {
   private int countdown;
   private UUID targetId;

   public CountdownEventTarget() {
   }

   public CountdownEventTarget(ServerLevel world, LivingEntity target, long openTime, int ticks) {
      super(world, target.m_20182_(), openTime);
      this.countdown = ticks;
      this.targetId = target.m_20148_();
   }

   public boolean shouldTrigger(ServerPlayer player) {
      return this.countdown <= 0;
   }

   public void tick() {
      if (this.countdown > 0) {
         --this.countdown;
      }

   }

   public CompoundTag save() {
      CompoundTag tag = super.save();
      tag.m_128405_("countdown", this.countdown);
      tag.m_128362_("targetId", this.targetId);
      return tag;
   }

   public void load(CompoundTag tag) {
      super.load(tag);
      this.countdown = tag.m_128451_("countdown");
      this.targetId = tag.m_128342_("targetId");
   }

   public UUID getTargetId() {
      return this.targetId;
   }

   @Nullable
   public Optional<LivingEntity> getTarget(ServerLevel world) {
      Player playerTarget = world.m_46003_(this.targetId);
      if (playerTarget != null) {
         return Optional.ofNullable(playerTarget);
      } else {
         Entity entity = (Entity)world.m_142646_().m_142694_(this.targetId);
         if (entity != null && entity instanceof LivingEntity) {
            LivingEntity livingEntity = (LivingEntity)entity;
            return Optional.ofNullable(livingEntity);
         } else {
            return Optional.empty();
         }
      }
   }
}
