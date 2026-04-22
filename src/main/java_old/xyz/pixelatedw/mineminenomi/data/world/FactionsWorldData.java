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
import net.minecraftforge.server.ServerLifecycleHooks;
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

   public static FactionsWorldData get() {
      MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
      if (server == null) {
         return null;
      } else {
         ServerLevel serverWorld = server.m_129783_();
         FactionsWorldData worldData = (FactionsWorldData)serverWorld.m_8895_().m_164861_(FactionsWorldData::load, FactionsWorldData::new, "mineminenomi-factions");
         return worldData;
      }
   }

   public static FactionsWorldData load(CompoundTag nbt) {
      FactionsWorldData data = new FactionsWorldData();
      CompoundTag bounties = nbt.m_128469_("issuedBounties");
      data.issuedBounties.clear();
      bounties.m_128431_().stream().forEach((x) -> data.issuedBounties.put(UUID.fromString(x), bounties.m_128454_(x)));
      ListTag crews = nbt.m_128437_("crews", 10);
      data.pirateCrews.clear();

      for(int i = 0; i < crews.size(); ++i) {
         CompoundTag crewNBT = crews.m_128728_(i);
         Crew crew = Crew.from(crewNBT);
         data.pirateCrews.put(WyHelper.getResourceName(crew.getName()), crew);
      }

      return data;
   }

   public CompoundTag m_7176_(CompoundTag nbt) {
      CompoundTag bounties = new CompoundTag();
      if (this.issuedBounties.size() > 0) {
         this.issuedBounties.entrySet().stream().forEach((x) -> bounties.m_128356_(((UUID)x.getKey()).toString(), (Long)x.getValue()));
      }

      nbt.m_128365_("issuedBounties", bounties);
      ListTag crews = new ListTag();

      for(Crew crew : this.pirateCrews.values()) {
         crews.add(crew.write());
      }

      nbt.m_128365_("crews", crews);
      return nbt;
   }

   public void tick(Level level) {
      level.m_46473_().m_6180_("crews");
      this.getCrews().forEach((crew) -> {
         crew.tick();
         if (crew.isEmpty()) {
            this.removeCrew(crew);
         }

      });
      level.m_46473_().m_7238_();
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
      return (Crew)this.pirateCrews.values().stream().filter((crew) -> crew.getCaptain() != null && crew.getCaptain().getUUID().equals(capId)).findFirst().orElse((Object)null);
   }

   public void removeCrew(Crew crew) {
      String key = WyHelper.getResourceName(crew.getName());
      if (this.pirateCrews.containsKey(key)) {
         this.pirateCrews.remove(key);
      }

      this.m_77762_();
   }

   public void addCrew(Crew crew) {
      String key = WyHelper.getResourceName(crew.getName());
      if (!this.pirateCrews.containsKey(key)) {
         this.pirateCrews.put(key, crew);
      }

      this.m_77762_();
   }

   public void removeCrewMember(Level level, Crew crew, UUID uuid) {
      crew.removeMember(level, uuid);
      this.m_77762_();
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
      this.m_77762_();
   }

   public void updateCrewJollyRoger(ServerPlayer player, Crew crew, JollyRoger jollyRoger) {
      if (jollyRoger.getBase() != null && !jollyRoger.getBase().canUse(player, crew)) {
         jollyRoger.setBase((JollyRogerElement)ModJollyRogers.BASE_SKULL.get());
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
      this.m_77762_();
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
      return this.issuedBounties.containsKey(entity.m_20148_());
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

      this.m_77762_();
   }
}
