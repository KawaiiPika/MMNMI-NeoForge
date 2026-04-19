package xyz.pixelatedw.mineminenomi.api.protection;

import com.google.common.base.Strings;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.util.BlockSnapshot;
import org.apache.commons.lang3.RandomStringUtils;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.config.ServerConfig;

public class ProtectedArea {
   private BlockPos centerPos;
   private int size;
   private String label = "";
   private boolean allowBlockDestruction = false;
   private boolean allowEntityDamage = false;
   private boolean allowPlayerDamage = false;
   private boolean allowAbilities = false;
   private boolean allowBlockRestoration = false;
   private boolean allowStatLoss = true;
   private boolean allowDeath = true;
   private boolean allowMobSpawns = true;
   private int restoreInterval = 20;
   private int restoreAmount = 15;
   private int restoreDistance = 10;
   private int unconsciousTime = 40;
   private int restoreTick;
   private Map<BlockPos, RestorationEntry> restoreBlocks = new HashMap();

   private ProtectedArea() {
   }

   public ProtectedArea(BlockPos center, int size, String label) {
      this.centerPos = center;
      this.setLabel(label);
      this.size = size;
   }

   public static ProtectedArea from(CompoundTag nbt) {
      ProtectedArea area = new ProtectedArea();
      area.load(nbt);
      area.setLabel(area.getLabel());
      return area;
   }

   public void setBlockDestruction(boolean flag) {
      this.allowBlockDestruction = flag;
   }

   public boolean canDestroyBlocks() {
      return this.allowBlockDestruction;
   }

   public void setEntityDamage(boolean flag) {
      this.allowEntityDamage = flag;
   }

   public boolean canHurtEntities() {
      return this.allowEntityDamage;
   }

   public void setPlayerDamage(boolean flag) {
      this.allowPlayerDamage = flag;
   }

   public boolean canHurtPlayers() {
      return this.allowPlayerDamage;
   }

   public void setBlockRestoration(boolean flag) {
      this.allowBlockRestoration = flag;
   }

   public boolean canRestoreBlocks() {
      return this.allowBlockRestoration;
   }

   public void setStatLoss(boolean flag) {
      this.allowStatLoss = flag;
   }

   public boolean canLoseStats() {
      return this.allowStatLoss;
   }

   public void setDeath(boolean flag) {
      this.allowDeath = flag;
   }

   public boolean canDie() {
      return this.allowDeath;
   }

   public void setMobSpawns(boolean flag) {
      this.allowMobSpawns = flag;
   }

   public boolean canMobsSpawn() {
      return this.allowMobSpawns;
   }

   public void setAbilitiesUsage(boolean flag) {
      this.allowAbilities = flag;
   }

   public boolean canAbilitiesBeUsed() {
      return this.allowAbilities;
   }

   public boolean canUseAbility(AbilityCore<?> core) {
      if (!this.allowAbilities) {
         boolean isWhitelisted = ServerConfig.isAbilityProtectionWhitelisted(core);
         return isWhitelisted;
      } else {
         return true;
      }
   }

   public void setRestoreInterval(int interval) {
      this.restoreInterval = interval;
   }

   public int getRestoreInterval() {
      return this.restoreInterval;
   }

   public void setRestoreAmount(int amount) {
      this.restoreAmount = amount;
   }

   public int getRestoreAmount() {
      return this.restoreAmount;
   }

   public void setRestoreDistance(int amount) {
      this.restoreDistance = amount;
   }

   public int getRestoreDistance() {
      return this.restoreDistance;
   }

   public void setUnconsciousTime(int time) {
      this.unconsciousTime = time;
   }

   public int getUnconsciousTime() {
      return this.unconsciousTime;
   }

   public void queueForRestoration(Level world, BlockPos pos) {
      this.queueForRestoration(pos, new RestorationEntry(world, pos));
   }

   public void queueForRestoration(BlockPos pos, RestorationEntry entry) {
      this.restoreBlocks.putIfAbsent(pos, entry);
   }

   public void queueForRestoration(Map<BlockPos, RestorationEntry> map) {
      map.entrySet().removeIf((e) -> this.restoreBlocks.containsKey(e.getKey()));
      this.restoreBlocks.putAll(map);
   }

   public Map<BlockPos, RestorationEntry> getRestorationMap() {
      return this.restoreBlocks;
   }

   public void restoreBlocks(Level level) {
      if (this.getRestorationMap().size() > 0 && this.canRestoreBlocks()) {
         if (this.getRestoreInterval() <= 0 || this.restoreTick-- <= 0) {
            Iterator<Map.Entry<BlockPos, RestorationEntry>> iter = this.getRestorationMap().entrySet().iterator();
            int restoreLeft = this.restoreAmount;

            while(restoreLeft > 0 && iter.hasNext()) {
               Map.Entry<BlockPos, RestorationEntry> entry = (Map.Entry)iter.next();
               if (level.m_46467_() >= ((RestorationEntry)entry.getValue()).getTimestamp() + ServerConfig.getGlobalProtectionGraceTime()) {
                  BlockPos pos = (BlockPos)entry.getKey();
                  if (this.restoreDistance > 0) {
                     boolean hasPlayerNear = false;

                     for(Player player : level.m_6907_()) {
                        if (player.m_20183_().m_123314_(pos, (double)this.restoreDistance)) {
                           hasPlayerNear = true;
                           break;
                        }
                     }

                     if (hasPlayerNear) {
                        continue;
                     }
                  }

                  BlockState replaced = ((RestorationEntry)entry.getValue()).getSnapshot().getReplacedBlock();
                  level.m_7731_(pos, replaced, 3);
                  CompoundTag nbt = ((RestorationEntry)entry.getValue()).getSnapshot().getTag();
                  BlockEntity te = null;
                  if (nbt != null) {
                     te = level.m_7702_(pos);
                     if (te != null) {
                        te.m_142466_(nbt);
                        te.m_6596_();
                     }
                  }

                  iter.remove();
                  --restoreLeft;
               }
            }

         }
      }
   }

   public void setLabel(String label) {
      this.label = this.checkValidLabel(label);
   }

   public String getLabel() {
      return WyHelper.getResourceName(this.label);
   }

   public BlockPos getCenter() {
      return this.centerPos;
   }

   public int getSize() {
      return this.size;
   }

   public void setSize(int size) {
      this.size = size;
   }

   public boolean isInside(Level world, int posX, int posY, int posZ) {
      int size = this.getSize() + 1;
      return posX < this.getCenter().m_123341_() + size && posX > this.getCenter().m_123341_() - size && posY < this.getCenter().m_123342_() + size && posY > this.getCenter().m_123342_() - size && posZ < this.getCenter().m_123343_() + size && posZ > this.getCenter().m_123343_() - size;
   }

   private String checkValidLabel(String label) {
      return Strings.isNullOrEmpty(label) ? RandomStringUtils.randomAlphabetic(5) : WyHelper.getResourceName(label);
   }

   public CompoundTag save() {
      CompoundTag nbt = new CompoundTag();
      nbt.m_128359_("label", this.label);
      nbt.m_128405_("x", this.centerPos.m_123341_());
      nbt.m_128405_("y", this.centerPos.m_123342_());
      nbt.m_128405_("z", this.centerPos.m_123343_());
      nbt.m_128405_("size", this.size);
      nbt.m_128379_("allowBlockDestruction", this.allowBlockDestruction);
      nbt.m_128379_("allowEntityDamage", this.allowEntityDamage);
      nbt.m_128379_("allowPlayerDamage", this.allowPlayerDamage);
      nbt.m_128379_("allowBlockRestoration", this.allowBlockRestoration);
      nbt.m_128379_("allowAbilities", this.allowAbilities);
      nbt.m_128379_("allowStatLoss", this.allowStatLoss);
      nbt.m_128379_("allowDeath", this.allowDeath);
      nbt.m_128379_("allowMobSpawns", this.allowMobSpawns);
      nbt.m_128405_("restoreInterval", this.restoreInterval);
      nbt.m_128405_("restoreAmount", this.restoreAmount);
      nbt.m_128405_("restoreDistance", this.restoreDistance);
      nbt.m_128405_("unconsciousTime", this.unconsciousTime);
      return nbt;
   }

   public void load(CompoundTag nbt) {
      this.label = nbt.m_128461_("label");
      int x = nbt.m_128451_("x");
      int y = nbt.m_128451_("y");
      int z = nbt.m_128451_("z");
      this.centerPos = new BlockPos(x, y, z);
      this.size = nbt.m_128451_("size");
      this.allowBlockDestruction = nbt.m_128471_("allowBlockDestruction");
      this.allowEntityDamage = nbt.m_128471_("allowEntityDamage");
      this.allowPlayerDamage = nbt.m_128471_("allowPlayerDamage");
      this.allowBlockRestoration = nbt.m_128471_("allowBlockRestoration");
      this.allowAbilities = nbt.m_128471_("allowAbilities");
      this.allowStatLoss = nbt.m_128471_("allowStatLoss");
      this.allowDeath = nbt.m_128471_("allowDeath");
      this.allowMobSpawns = nbt.m_128471_("allowMobSpawns");
      this.restoreInterval = nbt.m_128451_("restoreInterval");
      this.restoreAmount = nbt.m_128451_("restoreAmount");
      this.restoreDistance = nbt.m_128451_("restoreDistance");
      this.unconsciousTime = nbt.m_128451_("unconsciousTime");
   }

   public static class RestorationEntry {
      private long timestamp;
      private BlockSnapshot snapshot;

      public RestorationEntry(Level level, BlockPos pos) {
         this(level.m_46467_(), BlockSnapshot.create(level.m_46472_(), level, pos, 2));
      }

      public RestorationEntry(long timestamp, BlockSnapshot snapshot) {
         this.timestamp = timestamp;
         this.snapshot = snapshot;
      }

      public long getTimestamp() {
         return this.timestamp;
      }

      public BlockSnapshot getSnapshot() {
         return this.snapshot;
      }
   }
}
