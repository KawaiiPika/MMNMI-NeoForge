package xyz.pixelatedw.mineminenomi.data.world;

import com.google.common.collect.Iterators;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.function.Consumer;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BiomeTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.properties.StructureMode;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockIgnoreProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.server.ServerLifecycleHooks;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Faction;
import xyz.pixelatedw.mineminenomi.api.helpers.MobsHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.StructuresHelper;
import xyz.pixelatedw.mineminenomi.api.poi.CountdownEventTarget;
import xyz.pixelatedw.mineminenomi.api.poi.NTEventTarget;
import xyz.pixelatedw.mineminenomi.api.poi.POIEventTarget;
import xyz.pixelatedw.mineminenomi.api.poi.TrackedNPC;
import xyz.pixelatedw.mineminenomi.config.ServerConfig;
import xyz.pixelatedw.mineminenomi.data.entity.stats.EntityStatsCapability;
import xyz.pixelatedw.mineminenomi.data.entity.stats.IEntityStats;
import xyz.pixelatedw.mineminenomi.entities.mobs.CaptainEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.GruntEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.NotoriousEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.PacifistaEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.worldgov.CelestialDragonEntity;
import xyz.pixelatedw.mineminenomi.init.ModFactions;
import xyz.pixelatedw.mineminenomi.init.ModLootTables;
import xyz.pixelatedw.mineminenomi.init.ModMobs;
import xyz.pixelatedw.mineminenomi.init.ModSounds;

public class EventsWorldData extends SavedData {
   private static final String IDENTIFIER = "mineminenomi-events";
   private Set<NTEventTarget> notoriousTarget = new HashSet();
   private Set<POIEventTarget> caravans = new HashSet();
   private Set<CountdownEventTarget> busterCalls = new HashSet();
   private Set<POIEventTarget> celestialVisits = new HashSet();
   private int tick = 0;

   public static EventsWorldData get() {
      MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
      ServerLevel serverLevel = server.m_129783_();
      EventsWorldData worldData = (EventsWorldData)serverLevel.m_8895_().m_164861_(EventsWorldData::load, EventsWorldData::new, "mineminenomi-events");
      return worldData;
   }

   public static EventsWorldData load(CompoundTag nbt) {
      EventsWorldData data = new EventsWorldData();
      ListTag notoriousTargets = nbt.m_128437_("notoriousTargets", 10);

      for(int i = 0; i < notoriousTargets.size(); ++i) {
         CompoundTag entryNBT = notoriousTargets.m_128728_(i);
         if (!entryNBT.m_128456_()) {
            NTEventTarget poi = new NTEventTarget();
            poi.load(entryNBT);
            data.notoriousTarget.add(poi);
         }
      }

      ListTag caravans = nbt.m_128437_("caravans", 10);

      for(int i = 0; i < caravans.size(); ++i) {
         CompoundTag entryNBT = caravans.m_128728_(i);
         if (!entryNBT.m_128456_()) {
            POIEventTarget poi = new POIEventTarget();
            data.setupCaravan(poi);
            poi.load(entryNBT);
            data.caravans.add(poi);
         }
      }

      ListTag busterCalls = nbt.m_128437_("busterCalls", 10);

      for(int i = 0; i < busterCalls.size(); ++i) {
         CompoundTag entryNBT = busterCalls.m_128728_(i);
         if (!entryNBT.m_128456_()) {
            CountdownEventTarget poi = new CountdownEventTarget();
            data.setupSpecialBusterCall(poi, poi.getTargetId());
            poi.load(entryNBT);
            data.busterCalls.add(poi);
         }
      }

      ListTag visits = nbt.m_128437_("celestialVisits", 10);

      for(int i = 0; i < visits.size(); ++i) {
         CompoundTag entryNBT = visits.m_128728_(i);
         if (!entryNBT.m_128456_()) {
            POIEventTarget poi = new POIEventTarget();
            data.setupCelestialVisit(poi);
            poi.load(entryNBT);
            data.celestialVisits.add(poi);
         }
      }

      return data;
   }

   public CompoundTag m_7176_(CompoundTag nbt) {
      ListTag notoriousTargets = new ListTag();

      for(POIEventTarget poi : this.notoriousTarget) {
         notoriousTargets.add(poi.save());
      }

      nbt.m_128365_("notoriousTargets", notoriousTargets);
      ListTag caravans = new ListTag();

      for(POIEventTarget poi : this.caravans) {
         caravans.add(poi.save());
      }

      nbt.m_128365_("caravans", caravans);
      ListTag busterCalls = new ListTag();

      for(CountdownEventTarget poi : this.busterCalls) {
         busterCalls.add(poi.save());
      }

      nbt.m_128365_("busterCalls", busterCalls);
      ListTag visits = new ListTag();

      for(POIEventTarget poi : this.celestialVisits) {
         visits.add(poi.save());
      }

      nbt.m_128365_("celestialVisits", visits);
      return nbt;
   }

   public void tick(ServerLevel world) {
      ++this.tick;
      this.tickEvents(world);
      this.startNewEvents(world);
   }

   public void startNewEvents(ServerLevel world) {
      if (this.tick % 300 == 0) {
         List<ServerPlayer> players = world.m_6907_();
         Collections.shuffle(players);
         if (ServerConfig.canSpawnCelestialVisits()) {
            int newVisits = 0;
            boolean canSpawnCelestialVisit = world.f_46441_.m_188503_(3) == 0;
            if (canSpawnCelestialVisit && this.celestialVisits.size() < 10) {
               for(ServerPlayer player : players) {
                  if (newVisits > 1) {
                     break;
                  }

                  Optional<Vec3> opt = this.findEventPositionAround(world, player.m_20182_(), 100, 1000, false);
                  if (opt.isPresent() && this.canSpawnCVPoint((Vec3)opt.get()) && this.addCelestialVisit(world, (Vec3)opt.get())) {
                     ++newVisits;
                  }
               }
            }
         }

         if (ServerConfig.canSpawnCaravans()) {
            int newCaravans = 0;
            if (this.caravans.size() < 25) {
               for(ServerPlayer player : players) {
                  if (newCaravans > 3) {
                     break;
                  }

                  IEntityStats props = (IEntityStats)EntityStatsCapability.get(player).orElse((Object)null);
                  if (props != null && !props.isMarine() && !props.isBountyHunter()) {
                     Optional<Vec3> opt = this.findEventPositionAround(world, player.m_20182_(), 1000, 5000, false);
                     if (opt.isPresent() && this.addCaravan(world, (Vec3)opt.get(), 72000L)) {
                        ++newCaravans;
                     }
                  }
               }
            }
         }

         if (ServerConfig.canSpawnNotoriousTargets()) {
            NPCWorldData npcWorldData = NPCWorldData.get();
            int newTargets = 0;
            if (this.notoriousTarget.size() < 5) {
               for(ServerPlayer player : players) {
                  if (newTargets > 2) {
                     break;
                  }

                  Optional<Vec3> opt = this.findEventPositionAround(world, player.m_20182_(), 1000, 5000, false);
                  if (opt.isPresent()) {
                     boolean isMarine = world.m_213780_().m_188499_();
                     Optional<TrackedNPC> npc = npcWorldData.getRandomTrackedMob(isMarine ? (Faction)ModFactions.MARINE.get() : (Faction)ModFactions.PIRATE.get());
                     if (npc.isPresent() && !this.hasNTEventFor((TrackedNPC)npc.get())) {
                        this.addNotoriousTarget(world, (Vec3)opt.get(), 72000L, (TrackedNPC)npc.get());
                        ++newTargets;
                     }
                  }
               }
            }
         }
      }

   }

   public void tickEvents(ServerLevel world) {
      boolean hasUpdates = false;
      Iterator<NTEventTarget> ntIter = this.notoriousTarget.iterator();

      while(ntIter.hasNext()) {
         NTEventTarget poi = (NTEventTarget)ntIter.next();
         if (poi.getTrackedNPC() == null) {
            ntIter.remove();
            hasUpdates = true;
         }
      }

      Iterator<POIEventTarget> eventsIter = Iterators.concat(new Iterator[]{this.busterCalls.iterator()});
      if (ServerConfig.canSpawnNotoriousTargets()) {
         eventsIter = Iterators.concat(eventsIter, this.notoriousTarget.iterator());
      }

      if (ServerConfig.canSpawnCelestialVisits()) {
         eventsIter = Iterators.concat(eventsIter, this.celestialVisits.iterator());
      }

      if (ServerConfig.canSpawnCaravans()) {
         eventsIter = Iterators.concat(eventsIter, this.caravans.iterator());
      }

      while(eventsIter.hasNext()) {
         POIEventTarget poi = (POIEventTarget)eventsIter.next();
         if (poi.hasExpired(world)) {
            eventsIter.remove();
            hasUpdates = true;
         } else {
            for(ServerPlayer player : world.m_6907_()) {
               if (poi.shouldTrigger(player)) {
                  poi.getTriggerAction().trigger(world, poi);
                  eventsIter.remove();
                  hasUpdates = true;
                  return;
               }
            }

            poi.tick();
         }
      }

      if (hasUpdates) {
         this.m_77762_();
      }

   }

   public Optional<Vec3> findEventPositionAround(ServerLevel world, Vec3 pos, int offsetXZ, int bonusOffsetXZ, boolean allowOceans) {
      Optional<Vec3> pos2 = Optional.empty();

      for(int j = 0; j < 5; ++j) {
         int extraX = offsetXZ + world.m_213780_().m_188503_(bonusOffsetXZ);
         if (world.m_213780_().m_188499_()) {
            extraX *= -1;
         }

         int extraZ = offsetXZ + world.m_213780_().m_188503_(bonusOffsetXZ);
         if (world.m_213780_().m_188499_()) {
            extraZ *= -1;
         }

         Vec3 vecPos = pos.m_82520_((double)extraX, (double)1.0F, (double)extraZ);
         BlockPos check = new BlockPos((int)vecPos.f_82479_, (int)vecPos.f_82480_, (int)vecPos.f_82481_);
         if (allowOceans || !world.m_204166_(check).m_203656_(BiomeTags.f_207603_)) {
            pos2 = Optional.of(vecPos);
            break;
         }
      }

      return pos2;
   }

   public boolean addCelestialVisit(ServerLevel world, Vec3 pos) {
      POIEventTarget poi = new POIEventTarget(world, pos, 72000L);
      this.setupCelestialVisit(poi);
      this.celestialVisits.add(poi);
      this.m_77762_();
      return true;
   }

   private void setupCelestialVisit(POIEventTarget poi) {
      poi.setTriggerAction((world, event) -> {
         int y = world.m_6924_(Types.MOTION_BLOCKING_NO_LEAVES, (int)event.getPosition().m_7096_(), (int)event.getPosition().m_7094_());
         BlockPos blockPos = new BlockPos((int)event.getPosition().m_7096_(), y, (int)event.getPosition().m_7094_());
         EntityType<CelestialDragonEntity> dragonEntity = (EntityType)ModMobs.CELESTIAL_DRAGON.get();
         EntityType<GruntEntity> gruntEntity = (EntityType)ModMobs.MARINE_GRUNT.get();
         dragonEntity.m_262455_(world, (CompoundTag)null, (Consumer)null, blockPos, MobSpawnType.EVENT, false, false);

         for(int i = 0; i < 6; ++i) {
            gruntEntity.m_262455_(world, (CompoundTag)null, (Consumer)null, blockPos, MobSpawnType.EVENT, false, false);
         }

      });
   }

   private boolean canSpawnCVPoint(Vec3 pos) {
      for(POIEventTarget poi : this.celestialVisits) {
         if (Math.abs(poi.getPosition().m_82557_(pos)) < (double)22500.0F) {
            return false;
         }
      }

      return true;
   }

   public Set<POIEventTarget> getCelestialVisitsPOIs() {
      return new LinkedHashSet(this.celestialVisits);
   }

   public void addSpecialBusterCall(ServerLevel world, LivingEntity target) {
      CountdownEventTarget poi = new CountdownEventTarget(world, target, 72000L, 10);
      this.setupSpecialBusterCall(poi, target.m_20148_());
      this.busterCalls.add(poi);
      this.m_77762_();
   }

   private void setupSpecialBusterCall(CountdownEventTarget poi, UUID targetId) {
      poi.setTriggerAction((world, event) -> {
         Vec3 targetVec = event.getPosition();
         BlockPos targetPos = new BlockPos((int)targetVec.m_7096_(), (int)targetVec.m_7098_(), (int)targetVec.m_7094_());
         boolean canSpawnInBiome = !world.m_204166_(targetPos).m_203656_(BiomeTags.f_207603_);
         if (canSpawnInBiome) {
            Optional<TrackedNPC> viceAdmiralEntity = NPCWorldData.get().getRandomTrackedMob((Faction)ModFactions.MARINE.get());
            EntityType<CaptainEntity> captainEntity = (EntityType)ModMobs.MARINE_CAPTAIN.get();
            EntityType<PacifistaEntity> pacifistaEntity = (EntityType)ModMobs.PACIFISTA.get();
            EntityType<GruntEntity> gruntEntity = (EntityType)ModMobs.MARINE_GRUNT.get();
            boolean hasPacifistas = world.f_46441_.m_188503_(3) == 0;
            LivingEntity target = (LivingEntity)poi.getTarget(world).orElse((Object)null);
            BlockPos spawnPos = WyHelper.findOnGroundSpawnLocation(world, captainEntity, targetPos, 10);
            if (spawnPos == null) {
               int y = world.m_6924_(Types.MOTION_BLOCKING_NO_LEAVES, (int)event.getPosition().m_7096_(), (int)event.getPosition().m_7094_());
               spawnPos = new BlockPos((int)event.getPosition().m_7096_(), y, (int)event.getPosition().m_7094_());
            }

            if (viceAdmiralEntity.isPresent()) {
               Vec3 spawnVec = new Vec3((double)spawnPos.m_123341_(), (double)spawnPos.m_123342_(), (double)spawnPos.m_123343_());
               NotoriousEntity viceAdmiral = ((TrackedNPC)viceAdmiralEntity.get()).spawnTrackedMob(world, spawnVec);
               if (viceAdmiral != null) {
                  viceAdmiral.m_6710_(target);
               }
            }

            for(int i = 0; i < 4; ++i) {
               spawnPos = WyHelper.findOnGroundSpawnLocation(world, gruntEntity, targetPos, 20);
               if (spawnPos != null) {
                  if (hasPacifistas) {
                     PacifistaEntity pacifista = (PacifistaEntity)pacifistaEntity.m_20592_(world, (ItemStack)null, (Player)null, spawnPos, MobSpawnType.EVENT, false, false);
                     if (pacifista != null) {
                        pacifista.m_6710_(target);
                     }
                  } else {
                     CaptainEntity captain = (CaptainEntity)captainEntity.m_20592_(world, (ItemStack)null, (Player)null, spawnPos, MobSpawnType.EVENT, false, false);
                     if (captain != null) {
                        captain.m_6710_(target);
                     }
                  }
               }
            }

            for(int i = 0; i < 20; ++i) {
               spawnPos = WyHelper.findOnGroundSpawnLocation(world, gruntEntity, targetPos, 20);
               if (spawnPos != null) {
                  GruntEntity grunt = (GruntEntity)gruntEntity.m_20592_(world, (ItemStack)null, (Player)null, spawnPos, MobSpawnType.EVENT, false, false);
                  if (grunt != null) {
                     MobsHelper.removeFearGoals(grunt);
                     grunt.m_6710_(target);
                  }
               }
            }

            world.m_5594_((Player)null, targetPos, SoundEvents.f_11699_, SoundSource.HOSTILE, 1.0F, 1.0F);
            world.m_5594_((Player)null, targetPos, (SoundEvent)ModSounds.GENERIC_EXPLOSION.get(), SoundSource.HOSTILE, 1.0F, 1.0F);
         }
      });
   }

   public boolean addCaravan(ServerLevel world, Vec3 pos, long openTime) {
      BlockPos blockPos = new BlockPos((int)pos.m_7096_(), (int)pos.m_7098_(), (int)pos.m_7094_());
      if (!world.m_204166_(blockPos).m_203656_(BiomeTags.f_207591_) && !world.m_204166_(blockPos).m_203656_(BiomeTags.f_207611_)) {
         return false;
      } else {
         for(POIEventTarget poi : this.caravans) {
            if (poi.getPosition().m_82557_(pos) < (double)2500.0F) {
               return false;
            }
         }

         POIEventTarget poi = new POIEventTarget(world, pos, openTime);
         this.setupCaravan(poi);
         this.caravans.add(poi);
         this.m_77762_();
         return true;
      }
   }

   private void setupCaravan(POIEventTarget poi) {
      poi.setTriggerAction((world, event) -> {
         int y = world.m_6924_(Types.MOTION_BLOCKING_NO_LEAVES, (int)event.getPosition().m_7096_(), (int)event.getPosition().m_7094_());
         BlockPos blockPos = new BlockPos((int)event.getPosition().m_7096_(), y, (int)event.getPosition().m_7094_());
         BlockPos enemiesSpawn = blockPos;
         int grunts = 2 + world.m_213780_().m_188503_(5);
         boolean hasViceAdmiral = world.m_213780_().m_188503_(3) == 0;
         boolean hasPacifista = false;
         if (hasViceAdmiral) {
            grunts += 5;
            if (world.m_213780_().m_188503_(3) == 0) {
               hasPacifista = true;
            }
         }

         Optional<TrackedNPC> trackedNpc = NPCWorldData.get().getRandomTrackedMob((Faction)ModFactions.MARINE.get());
         if (!trackedNpc.isPresent()) {
            hasViceAdmiral = false;
         }

         StructurePlaceSettings placement = (new StructurePlaceSettings()).m_74377_(Mirror.NONE).m_74379_(Rotation.NONE).m_74392_(false);
         placement.m_74394_().m_74383_(BlockIgnoreProcessor.f_74048_).m_230324_(RandomSource.m_216335_(Util.m_137550_()));
         Optional<StructureTemplate> template = StructuresHelper.getStructure(world, ResourceLocation.fromNamespaceAndPath("mineminenomi", "unaligned/caravan"));
         if (template.isPresent()) {
            BlockPos templatePos = blockPos.m_7495_();
            ((StructureTemplate)template.get()).m_230328_(world, templatePos, templatePos, placement, RandomSource.m_216335_(Util.m_137550_()), 2);

            for(StructureTemplate.StructureBlockInfo blockInfo : ((StructureTemplate)template.get()).m_74603_(templatePos, placement, Blocks.f_50677_)) {
               if (blockInfo.f_74677_() != null) {
                  BlockPos pos = blockInfo.f_74675_();
                  StructureMode structuremode = StructureMode.valueOf(blockInfo.f_74677_().m_128461_("mode"));
                  if (structuremode == StructureMode.DATA) {
                     String function = blockInfo.f_74677_().m_128461_("metadata");
                     if (function.equals("caravan_loot")) {
                        StructuresHelper.spawnLoot(world, pos, hasViceAdmiral ? ModLootTables.CARAVAN_HARD_CHEST : ModLootTables.CARAVAN_EASY_CHEST);
                     } else if (function.equals("caravan_spawn")) {
                        enemiesSpawn = pos;
                     }
                  }
               }
            }
         }

         for(int i = 0; i < grunts; ++i) {
            GruntEntity entity = (GruntEntity)((EntityType)ModMobs.MARINE_GRUNT.get()).m_20592_(world, (ItemStack)null, (Player)null, enemiesSpawn, MobSpawnType.EVENT, false, false);
            if (entity != null) {
               MobsHelper.removeFearGoals(entity);
            }
         }

         if (hasViceAdmiral) {
            NotoriousEntity entity = ((TrackedNPC)trackedNpc.get()).createTrackedMob(world);
            entity.m_6034_((double)enemiesSpawn.m_123341_(), (double)enemiesSpawn.m_123342_(), (double)enemiesSpawn.m_123343_());
            world.m_7967_(entity);
         } else {
            CaptainEntity entity = (CaptainEntity)((EntityType)ModMobs.MARINE_CAPTAIN.get()).m_20592_(world, (ItemStack)null, (Player)null, enemiesSpawn, MobSpawnType.EVENT, false, false);
            if (entity != null) {
               MobsHelper.removeFearGoals(entity);
            }
         }

         if (hasPacifista) {
            PacifistaEntity entity = (PacifistaEntity)((EntityType)ModMobs.PACIFISTA.get()).m_20592_(world, (ItemStack)null, (Player)null, enemiesSpawn, MobSpawnType.EVENT, false, false);
            if (entity != null) {
               MobsHelper.removeFearGoals(entity);
            }
         }

         for(int i = 0; i < 3; ++i) {
            EntityType.f_20492_.m_20592_(world, (ItemStack)null, (Player)null, enemiesSpawn, MobSpawnType.EVENT, false, false);
         }

      });
   }

   public Set<POIEventTarget> getCaravanPOIs() {
      return new LinkedHashSet(this.caravans);
   }

   public void addNotoriousTarget(ServerLevel world, Vec3 pos, long openTime, TrackedNPC tracked) {
      NPCWorldData.get().updateTrackedMob(world, tracked);
      NTEventTarget poi = new NTEventTarget(world, pos, openTime, tracked);
      this.notoriousTarget.add(poi);
      this.m_77762_();
   }

   public Set<NTEventTarget> getNotoriousTargets() {
      return new LinkedHashSet(this.notoriousTarget);
   }

   public boolean hasNTEventAt(Vec3 pos) {
      return this.notoriousTarget.stream().anyMatch((event) -> event.getPosition().equals(pos));
   }

   public boolean hasNTEventFor(TrackedNPC npc) {
      return this.notoriousTarget.stream().anyMatch((event) -> event.getTrackedNPC().equals(npc));
   }

   public boolean hasNTEventFor(UUID id) {
      return this.notoriousTarget.stream().filter((event) -> event.getTrackedNPC() != null).anyMatch((event) -> event.getTrackedNPC().getUUID().equals(id));
   }

   public void wipeAllEvents() {
      this.notoriousTarget.clear();
      this.caravans.clear();
      this.busterCalls.clear();
      this.celestialVisits.clear();
   }
}
