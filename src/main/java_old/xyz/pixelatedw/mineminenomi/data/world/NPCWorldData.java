package xyz.pixelatedw.mineminenomi.data.world;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraftforge.server.ServerLifecycleHooks;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Faction;
import xyz.pixelatedw.mineminenomi.api.poi.TrackedNPC;
import xyz.pixelatedw.mineminenomi.api.util.Interval;
import xyz.pixelatedw.mineminenomi.init.ModFactions;

public class NPCWorldData extends SavedData {
   private static final String IDENTIFIER = "mineminenomi-npcs";
   private Random random = new Random();
   private Interval updateInterval = Interval.startAtMax(300);
   private Interval updateNewsInterval = Interval.startAtMax(24000);
   private Set<TrackedNPC> tracked = new LinkedHashSet();
   private long trackedId = 0L;

   public static NPCWorldData get() {
      MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
      ServerLevel serverWorld = server.m_129783_();
      NPCWorldData worldData = (NPCWorldData)serverWorld.m_8895_().m_164861_(NPCWorldData::load, NPCWorldData::new, "mineminenomi-npcs");
      return worldData;
   }

   public static NPCWorldData load(CompoundTag nbt) {
      NPCWorldData data = new NPCWorldData();
      data.trackedId = nbt.m_128454_("globalTrackedId");
      ListTag trackedNpcs = nbt.m_128437_("trackedNPCs", 10);

      for(int i = 0; i < trackedNpcs.size(); ++i) {
         CompoundTag entryNBT = trackedNpcs.m_128728_(i);
         if (!entryNBT.m_128456_()) {
            TrackedNPC npc = TrackedNPC.from(entryNBT);
            data.tracked.add(npc);
         }
      }

      return data;
   }

   public CompoundTag m_7176_(CompoundTag nbt) {
      nbt.m_128356_("globalTrackedId", this.trackedId);
      ListTag trackedNpcs = new ListTag();

      for(TrackedNPC npc : this.tracked) {
         trackedNpcs.add(npc.save());
      }

      nbt.m_128365_("trackedNPCs", trackedNpcs);
      return nbt;
   }

   public void setup(ServerLevel world) {
      int alivePirates = (int)this.tracked.stream().filter((t) -> t.getFaction().equals(ModFactions.PIRATE.get())).count();
      int aliveMarines = (int)this.tracked.stream().filter((t) -> t.getFaction().equals(ModFactions.MARINE.get())).count();
      if (alivePirates <= 0) {
         for(int pirates = Math.abs(5 + world.m_213780_().m_188503_(10) - alivePirates); pirates > 0; --pirates) {
            this.addRandomTrackedMob(world, (Faction)ModFactions.PIRATE.get());
         }
      }

      if (aliveMarines <= 0) {
         for(int marines = Math.abs(5 + world.m_213780_().m_188503_(15) - aliveMarines); marines > 0; --marines) {
            this.addRandomTrackedMob(world, (Faction)ModFactions.MARINE.get());
         }
      }

   }

   public void tick(ServerLevel world) {
      int updates = 0;
      if (this.updateInterval.canTick()) {
         for(TrackedNPC tracked : this.tracked) {
            tracked.recalculateDifficulty(world);
            ++updates;
         }
      }

      if (this.updateNewsInterval.canTick()) {
         for(TrackedNPC tracked : this.tracked) {
            tracked.updateNewsEntry(world);
            ++updates;
         }
      }

      if (updates > 0) {
         this.m_77762_();
      }

   }

   public Set<TrackedNPC> getTrackedMobs() {
      return new LinkedHashSet(this.tracked);
   }

   public void updateTrackedMob(ServerLevel world, TrackedNPC npc) {
      npc.recalculateDifficulty(world);
      this.m_77762_();
   }

   private void addRandomTrackedMob(ServerLevel world, Faction faction) {
      long seed = world.m_213780_().m_188505_();
      this.addTrackedMob(world, faction, seed);
   }

   private void addTrackedMob(ServerLevel world, Faction faction, long seed) {
      TrackedNPC tracked = new TrackedNPC(world, (long)(this.trackedId++), faction, seed);
      this.tracked.add(tracked);
      this.m_77762_();
   }

   public void removeTrackedMob(ServerLevel world, TrackedNPC tracked) {
      if (tracked != null) {
         this.tracked.remove(tracked);
         long factionLeft = this.tracked.stream().filter((npc) -> npc.getFaction().equals(tracked.getFaction())).count();
         if (factionLeft < 5L) {
            this.addRandomTrackedMob(world, tracked.getFaction());
         }

         this.m_77762_();
      }
   }

   public Optional<TrackedNPC> getTrackedMob(long id) {
      return this.tracked.stream().filter((t) -> t.getId() == id).findFirst();
   }

   public Optional<TrackedNPC> getTrackedMobBySeed(long seed) {
      return this.tracked.stream().filter((t) -> t.getSeed() == seed).findFirst();
   }

   public Optional<TrackedNPC> getRandomTrackedMob(Faction faction) {
      Optional<TrackedNPC> tracked = ((List)this.tracked.stream().filter((npc) -> npc.getFaction().equals(faction)).collect(WyHelper.toShuffledList())).stream().findAny();
      return tracked;
   }
}
