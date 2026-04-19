package xyz.pixelatedw.mineminenomi.api.entities;

import com.mojang.authlib.GameProfile;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.IForgeRegistry;
import org.apache.logging.log4j.LogManager;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.WyRegistry;
import xyz.pixelatedw.mineminenomi.api.crew.Crew;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Faction;
import xyz.pixelatedw.mineminenomi.data.entity.stats.EntityStatsCapability;
import xyz.pixelatedw.mineminenomi.data.world.EventsWorldData;
import xyz.pixelatedw.mineminenomi.data.world.FactionsWorldData;
import xyz.pixelatedw.mineminenomi.entities.mobs.OPEntity;

public class WantedPosterData {
   private static final String[] WANTED_POSTER_BACKGROUNDS = new String[]{"forest1", "forest2", "jungle1", "jungle2", "hills1", "hills2", "hills3", "plains1", "plains2", "plains3", "taiga1", "taiga2"};
   private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#,##0");
   private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
   private UUID ownerUuid;
   private String ownerName;
   private Optional<GameProfile> ownerProfile = Optional.empty();
   private Optional<ResourceLocation> skinTexture = Optional.empty();
   private Optional<Crew> ownerCrew = Optional.empty();
   private Optional<Vec3> trackedPosition = Optional.empty();
   private Optional<Faction> faction = Optional.empty();
   private long bounty;
   private String bountyString;
   private String background;
   private String date;
   private Random random;
   private long seed;
   private boolean isExpired;

   private WantedPosterData() {
   }

   public WantedPosterData(LivingEntity entity, long bounty) {
      this.seed = (long)entity.hashCode() + bounty;
      this.random = new Random(this.seed);
      this.ownerUuid = entity.m_20148_();
      this.ownerName = entity.m_7755_().getString();
      if (entity instanceof Player player) {
         this.ownerProfile = Optional.ofNullable(player.m_36316_());
      } else if (entity instanceof OPEntity opEntity) {
         ResourceLocation texture = opEntity.getCurrentTexture();
         if (texture == null) {
            this.skinTexture = Optional.ofNullable(opEntity.getCurrentTexture());
         } else {
            this.skinTexture = Optional.ofNullable(texture);
         }
      }

      Crew crew = FactionsWorldData.get().getCrewWithMember(this.ownerUuid);
      this.ownerCrew = Optional.ofNullable(crew);
      this.faction = (Optional)EntityStatsCapability.get(entity).map((props) -> props.getFaction()).orElse(Optional.empty());
      this.bounty = bounty;
      this.bountyString = DECIMAL_FORMAT.format(this.bounty);
      int randomBg = (int)WyHelper.randomWithRange((Random)this.random, 0, WANTED_POSTER_BACKGROUNDS.length - 1);
      this.background = WANTED_POSTER_BACKGROUNDS[randomBg];
      String dateString = DATE_FORMAT.format(new Date());
      this.date = dateString;
   }

   public static WantedPosterData empty() {
      return new WantedPosterData();
   }

   public static WantedPosterData from(CompoundTag nbt) {
      WantedPosterData wantedPoster = new WantedPosterData();
      if (nbt.m_128456_()) {
         LogManager.getLogger(WantedPosterData.class).warn("Wanted Poster Data created without any actual data!");
         return wantedPoster;
      } else {
         wantedPoster.ownerUuid = nbt.m_128342_("UUID");
         wantedPoster.ownerName = nbt.m_128461_("Name");
         if (nbt.m_128425_("Owner", 10)) {
            wantedPoster.ownerProfile = Optional.ofNullable(NbtUtils.m_129228_(nbt.m_128469_("Owner")));
         }

         if (nbt.m_128441_("Texture")) {
            wantedPoster.skinTexture = Optional.ofNullable(ResourceLocation.parse(nbt.m_128461_("Texture")));
         }

         if (nbt.m_128425_("TrackedPosition", 10)) {
            CompoundTag trackedPos = nbt.m_128469_("TrackedPosition");
            double x = trackedPos.m_128459_("X");
            double y = trackedPos.m_128459_("Y");
            double z = trackedPos.m_128459_("Z");
            wantedPoster.trackedPosition = Optional.ofNullable(new Vec3(x, y, z));
         }

         if (nbt.m_128425_("Crew", 10)) {
            wantedPoster.ownerCrew = Optional.ofNullable(Crew.from(nbt.m_128469_("Crew")));
         }

         wantedPoster.bounty = nbt.m_128454_("Bounty");
         wantedPoster.bountyString = DECIMAL_FORMAT.format(wantedPoster.bounty);
         wantedPoster.isExpired = nbt.m_128471_("IsExpired");
         if (nbt.m_128441_("Faction")) {
            ResourceLocation id = ResourceLocation.parse(nbt.m_128461_("Faction"));
            Faction faction = (Faction)((IForgeRegistry)WyRegistry.FACTIONS.get()).getValue(id);
            wantedPoster.faction = Optional.ofNullable(faction);
         }

         wantedPoster.background = nbt.m_128461_("Background");
         wantedPoster.date = nbt.m_128461_("Date");
         wantedPoster.seed = nbt.m_128454_("Seed");
         wantedPoster.random = new Random(wantedPoster.seed);
         return wantedPoster;
      }
   }

   public UUID getOwnerUuid() {
      return this.ownerUuid;
   }

   public String getOwnerName() {
      return this.ownerName;
   }

   public Optional<GameProfile> getOwnerProfile() {
      return this.ownerProfile;
   }

   public Optional<Crew> getOwnerCrew() {
      return this.ownerCrew;
   }

   public Optional<ResourceLocation> getOwnerTexture() {
      return this.skinTexture;
   }

   public Optional<Faction> getFaction() {
      return this.faction;
   }

   public long getBounty() {
      return this.bounty;
   }

   public String getBountyString() {
      return this.bountyString;
   }

   public String getBackground() {
      return this.background;
   }

   public String getDate() {
      return this.date;
   }

   public boolean isExpired() {
      return this.isExpired;
   }

   public void checkIfExpired() {
      FactionsWorldData worldData = FactionsWorldData.get();
      if (worldData == null) {
         LogManager.getLogger(this).warn("Checking for expiration on client side! Something has gone wrong.");
      } else {
         if (this.skinTexture.isPresent()) {
            EventsWorldData eventsWorldData = EventsWorldData.get();
            if (eventsWorldData != null) {
               boolean hasEvent = eventsWorldData.hasNTEventFor(this.ownerUuid);
               if (!hasEvent) {
                  this.isExpired = true;
                  return;
               }
            }
         }

         this.isExpired = worldData.getBounty(this.ownerUuid) != this.bounty;
      }
   }

   public void setTrackedPosition(Vec3 pos) {
      this.trackedPosition = Optional.ofNullable(pos);
   }

   public Optional<Vec3> getTrackedPosition() {
      return this.trackedPosition;
   }

   public CompoundTag write() {
      CompoundTag data = new CompoundTag();
      if (this.ownerUuid == null) {
         return data;
      } else {
         data.m_128362_("UUID", this.ownerUuid);
         data.m_128359_("Name", this.ownerName);
         data.m_128356_("Bounty", this.bounty);
         data.m_128379_("IsExpired", this.isExpired);
         this.faction.ifPresent((faction) -> data.m_128359_("Faction", faction.getRegistryName().toString()));
         data.m_128359_("Background", this.background);
         data.m_128359_("Date", this.date);
         if (this.ownerProfile.isPresent()) {
            CompoundTag gameProfileNBT = new CompoundTag();
            NbtUtils.m_129230_(gameProfileNBT, (GameProfile)this.ownerProfile.get());
            data.m_128365_("Owner", gameProfileNBT);
         }

         if (this.skinTexture.isPresent()) {
            data.m_128359_("Texture", ((ResourceLocation)this.skinTexture.get()).toString());
         }

         if (this.trackedPosition.isPresent()) {
            CompoundTag trackedPos = new CompoundTag();
            trackedPos.m_128347_("X", ((Vec3)this.trackedPosition.get()).f_82479_);
            trackedPos.m_128347_("Y", ((Vec3)this.trackedPosition.get()).f_82480_);
            trackedPos.m_128347_("Z", ((Vec3)this.trackedPosition.get()).f_82481_);
            data.m_128365_("TrackedPosition", trackedPos);
         }

         if (this.ownerCrew.isPresent()) {
            data.m_128365_("Crew", ((Crew)this.ownerCrew.get()).write());
         }

         data.m_128356_("Seed", this.seed);
         return data;
      }
   }
}
