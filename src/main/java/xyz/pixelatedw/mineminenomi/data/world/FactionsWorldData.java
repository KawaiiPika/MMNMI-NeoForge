package xyz.pixelatedw.mineminenomi.data.world;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.SavedData;
import net.neoforged.neoforge.server.ServerLifecycleHooks;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.level.saveddata.SavedData;
import org.jetbrains.annotations.Nullable;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.crew.Crew;
import xyz.pixelatedw.mineminenomi.api.crew.JollyRoger;
import xyz.pixelatedw.mineminenomi.api.crew.JollyRogerElement;
import xyz.pixelatedw.mineminenomi.init.ModJollyRogers;

public class FactionsWorldData extends SavedData {
   private static final String IDENTIFIER = "mineminenomi-factions";
   private HashMap<String, Crew> pirateCrews = new HashMap();
   private HashMap<UUID, Long> issuedBounties = new HashMap();

   private static final SavedData.Factory<FactionsWorldData> FACTORY = new SavedData.Factory<>(FactionsWorldData::new, FactionsWorldData::load, null);

   public static FactionsWorldData get() {
      MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
      if (server == null) {
         return null;
      } else {
         ServerLevel serverWorld = server.overworld();
         return serverWorld.getDataStorage().computeIfAbsent(FACTORY, IDENTIFIER);
      }
   }

   public static FactionsWorldData load(CompoundTag nbt, HolderLookup.Provider provider) {
      FactionsWorldData data = new FactionsWorldData();
      CompoundTag bounties = nbt.getCompound("issuedBounties");
      data.issuedBounties.clear();
      bounties.getAllKeys().forEach((x) -> data.issuedBounties.put(UUID.fromString(x), bounties.getLong(x)));
      ListTag crews = nbt.getList("crews", 10);
      data.pirateCrews.clear();

      for(int i = 0; i < crews.size(); ++i) {
         CompoundTag crewNBT = crews.getCompound(i);
         Crew crew = Crew.from(crewNBT);
         data.pirateCrews.put(WyHelper.getResourceName(crew.getName()), crew);
      }

      return data;
   }

   @Override
   public CompoundTag save(CompoundTag nbt, HolderLookup.Provider provider) {
      CompoundTag bounties = new CompoundTag();
      if (this.issuedBounties.size() > 0) {
         this.issuedBounties.forEach((key, value) -> bounties.putLong(key.toString(), value));
      }

      nbt.put("issuedBounties", bounties);
      ListTag crews = new ListTag();

      for(Crew crew : this.pirateCrews.values()) {
         crews.add(crew.write());
      }

      nbt.put("crews", crews);
      return nbt;
   }

   public void tick(Level level) {
      level.getProfiler().push("crews");
      this.getCrews().forEach((crew) -> {
         crew.tick();
         if (crew.isEmpty()) {
            this.removeCrew(crew);
         }

      });
      level.getProfiler().pop();
   }

   public List<Crew> getCrews() {
      return new ArrayList(this.pirateCrews.values());
   }

   public @Nullable Crew getCrewWithMember(UUID memId) {
      for(Crew crew : this.pirateCrews.values()) {
         for(Crew.Member member : crew.getMembers()) {
            if (member.getUUID().equals(memId)) {
               return crew;
            }
         }
      }

      return null;
   }

   public @Nullable Crew getCrewByName(String name) {
      for(Crew crew : this.pirateCrews.values()) {
         if (crew.getName().equals(name)) {
            return crew;
         }
      }

      return null;
   }

   public @Nullable Crew getCrewWithCaptain(UUID capId) {
      return this.pirateCrews.values().stream().filter((crew) -> crew.getCaptain() != null && crew.getCaptain().getUUID().equals(capId)).findFirst().orElse(null);
   }

   public void removeCrew(Crew crew) {
      String key = WyHelper.getResourceName(crew.getName());
      if (this.pirateCrews.containsKey(key)) {
         this.pirateCrews.remove(key);
      }

      this.setDirty();
   }

   public void addCrew(Crew crew) {
      String key = WyHelper.getResourceName(crew.getName());
      if (!this.pirateCrews.containsKey(key)) {
         this.pirateCrews.put(key, crew);
      }

      this.setDirty();
   }

   public void removeCrewMember(Level level, Crew crew, UUID uuid) {
      crew.removeMember(level, uuid);
      this.setDirty();
   }

   public void addCrewMember(Crew crew, LivingEntity entity) {
      this.addCrewMember(crew, entity, true);
   }

   public void addTemporaryCrewMember(Crew crew, LivingEntity entity) {
      this.addCrewMember(crew, entity, false);
   }

   public void addCrewMember(Crew crew, LivingEntity entity, boolean saveMember) {
      String key = WyHelper.getResourceName(crew.getName());
      if (!this.pirateCrews.containsKey(key)) {
         this.pirateCrews.put(key, crew);
      } else {
         crew = (Crew)this.pirateCrews.get(key);
      }

      crew.addMember(entity, saveMember);
      this.setDirty();
   }

   public void updateCrewJollyRoger(ServerPlayer player, Crew crew, JollyRoger jollyRoger) {
      if (jollyRoger.getBase() != null && !jollyRoger.getBase().canUse(player, crew)) {
         jollyRoger.setBase(ModJollyRogers.BASE_SKULL.get());
      }

      for(int i = 0; i < jollyRoger.getBackgrounds().length; ++i) {
         JollyRogerElement element = jollyRoger.getBackgrounds()[i];
         if (element != null && !element.canUse(player, crew)) {
            jollyRoger.setBackground(i, (JollyRogerElement)null);
         }
      }

      for(int i = 0; i < jollyRoger.getDetails().length; ++i) {
         JollyRogerElement element = jollyRoger.getDetails()[i];
         if (element != null && !element.canUse(player, crew)) {
            jollyRoger.setDetail(i, element);
         }
      }

      crew.setJollyRoger(jollyRoger);
      this.setDirty();
   }

   public HashMap<UUID, Long> getAllBounties() {
      return this.issuedBounties;
   }

   public Object[] getRandomBounty() {
      int count = this.getAllBounties().size();
      if (count <= 0) {
         return null;
      } else {
         Object[] keys = this.getAllBounties().keySet().toArray();
         Object key = keys[(new Random()).nextInt(count)];
         long bounty = (Long)this.getAllBounties().get(key);
         return new Object[]{key, bounty};
      }
   }

   public long getBounty(UUID uuid) {
      return this.issuedBounties.containsKey(uuid) ? (Long)this.issuedBounties.get(uuid) : 0L;
   }

   public boolean hasIssuedBounty(LivingEntity entity) {
      return this.issuedBounties.containsKey(entity.getUUID());
   }

   public void removeBounty(UUID uuid) {
      if (this.issuedBounties.containsKey(uuid)) {
         this.issuedBounties.remove(uuid);
      }

   }

   public void issueBounty(UUID uuid, long bounty) {
      if (this.issuedBounties.containsKey(uuid)) {
         this.issuedBounties.remove(uuid);
         this.issuedBounties.put(uuid, bounty);
      } else {
         this.issuedBounties.put(uuid, bounty);
      }

      this.setDirty();
   }
}
