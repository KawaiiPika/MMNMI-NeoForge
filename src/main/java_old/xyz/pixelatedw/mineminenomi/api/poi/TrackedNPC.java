package xyz.pixelatedw.mineminenomi.api.poi;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryObject;
import org.apache.logging.log4j.LogManager;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.WyRegistry;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Faction;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.FightingStyle;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Race;
import xyz.pixelatedw.mineminenomi.api.helpers.AttributeHelper;
import xyz.pixelatedw.mineminenomi.config.ServerConfig;
import xyz.pixelatedw.mineminenomi.data.entity.haki.HakiCapability;
import xyz.pixelatedw.mineminenomi.data.entity.haki.IHakiData;
import xyz.pixelatedw.mineminenomi.data.entity.stats.EntityStatsCapability;
import xyz.pixelatedw.mineminenomi.data.entity.stats.IEntityStats;
import xyz.pixelatedw.mineminenomi.entities.mobs.NotoriousEntity;
import xyz.pixelatedw.mineminenomi.init.ModFactions;
import xyz.pixelatedw.mineminenomi.init.ModFightingStyles;
import xyz.pixelatedw.mineminenomi.init.ModMobs;
import xyz.pixelatedw.mineminenomi.init.ModRaces;

public class TrackedNPC {
   private static final double MIN_DORIKI = (double)1000.0F;
   private static final float MIN_OBS_HAKI = 10.0F;
   private static final float MIN_BUSO_HAKI = 10.0F;
   private static final long MIN_BOUNTY = 100000L;
   private static final float MAX_TIME = 1.5552E8F;
   private static final List<RegistryObject<FightingStyle>> STYLES;
   private static final List<RegistryObject<Race>> RACES;
   private UUID uuid;
   private Random random;
   private long seed;
   private long id;
   private Vec3 pos;
   private long lastGameUpdate;
   private long lastNewsUpdate;
   private Faction faction;
   private Race race;
   private Race subRace;
   private FightingStyle style;
   private double doriki;
   private long bounty;
   private float kenbunshokuExp;
   private float busoshokuExp;
   private NewsEntry newsEntry;

   private TrackedNPC() {
      this.pos = Vec3.f_82478_;
   }

   public TrackedNPC(ServerLevel world, long id, Faction faction, long seed) {
      this.pos = Vec3.f_82478_;
      this.setSeed(seed);
      this.id = id;
      this.faction = faction;
      this.style = (FightingStyle)((RegistryObject)STYLES.get(this.random.nextInt(STYLES.size()))).get();
      this.race = (Race)((RegistryObject)RACES.get(this.random.nextInt(RACES.size()))).get();
      if (this.race.equals(ModRaces.MINK.get())) {
         int size = ((Race)ModRaces.MINK.get()).getSubRaces().size();
         this.subRace = (Race)((RegistryObject)((Race)ModRaces.MINK.get()).getSubRaces().get(this.random.nextInt(size))).get();
      }

      this.lastGameUpdate = world.m_46467_();
      this.doriki = (double)1000.0F;
      this.kenbunshokuExp = 10.0F;
      this.busoshokuExp = 10.0F;
      if (this.faction.equals(ModFactions.PIRATE.get())) {
         this.bounty = 100000L;
      }

   }

   public static TrackedNPC from(CompoundTag tag) {
      TrackedNPC npc = new TrackedNPC();
      npc.setSeed(tag.m_128454_("seed"));
      npc.id = tag.m_128454_("id");
      npc.uuid = tag.m_128342_("uuid");
      npc.lastGameUpdate = tag.m_128454_("lastGameUpdate");
      npc.lastNewsUpdate = tag.m_128454_("lastNewsUpdate");
      ResourceLocation factionId = ResourceLocation.parse(tag.m_128461_("faction"));
      Faction faction = (Faction)((IForgeRegistry)WyRegistry.FACTIONS.get()).getValue(factionId);
      Objects.requireNonNull(faction, "Faction of a TrackedNPC cannot be null!");
      npc.faction = faction;
      if (npc.faction.equals(ModFactions.PIRATE.get())) {
         npc.bounty = tag.m_128454_("bounty");
      }

      ResourceLocation raceId = ResourceLocation.parse(tag.m_128461_("race"));
      Race race = (Race)((IForgeRegistry)WyRegistry.RACES.get()).getValue(raceId);
      Objects.requireNonNull(race, "Race of a TrackedNPC cannot be null!");
      npc.race = race;
      ResourceLocation subRaceId = ResourceLocation.parse(tag.m_128461_("subRace"));
      Race subRace = (Race)((IForgeRegistry)WyRegistry.RACES.get()).getValue(subRaceId);
      npc.subRace = subRace;
      ResourceLocation styleId = ResourceLocation.parse(tag.m_128461_("style"));
      FightingStyle style = (FightingStyle)((IForgeRegistry)WyRegistry.FIGHTING_STYLES.get()).getValue(styleId);
      Objects.requireNonNull(style, "Fighting Style of a TrackedNPC cannot be null!");
      npc.style = style;
      npc.doriki = tag.m_128459_("doriki");
      npc.kenbunshokuExp = tag.m_128457_("kenbunshokuExp");
      npc.busoshokuExp = tag.m_128457_("busoshokuExp");
      return npc;
   }

   public long getId() {
      return this.id;
   }

   public UUID getUUID() {
      return this.uuid;
   }

   public long getSeed() {
      return this.seed;
   }

   public void setSeed(long seed) {
      this.seed = seed;
      if (this.random == null) {
         this.random = new Random(seed);
      } else {
         this.random.setSeed(seed);
      }

      this.uuid = new UUID(this.random.nextLong(), this.random.nextLong());
   }

   public double getDoriki() {
      return this.doriki;
   }

   public long getBounty() {
      return this.bounty;
   }

   public float getObservationHaki() {
      return this.kenbunshokuExp;
   }

   public float getBusoshokuHaki() {
      return this.busoshokuExp;
   }

   public Random getRandom() {
      return this.random;
   }

   public Faction getFaction() {
      return this.faction;
   }

   public Race getRace() {
      return this.race;
   }

   @Nullable
   public Race getSubRace() {
      return this.subRace;
   }

   public FightingStyle getStyle() {
      return this.style;
   }

   public NewsEntry getNewsEntry(Level world) {
      if (this.newsEntry == null) {
         this.updateNewsEntry(world);
      }

      return this.newsEntry;
   }

   public void updateNewsEntry(Level world) {
      this.newsEntry = NewsEntry.createNewsFor(this, world);
      this.lastNewsUpdate = world.m_46467_();
   }

   public void recalculateDifficulty(ServerLevel world) {
      float timePassed = (float)world.m_46467_() / 1.5552E8F;
      int amount = 0;
      double totalDoriki = (double)0.0F;
      float totalHaki = 0.0F;

      for(ServerPlayer player : world.m_6907_()) {
         ++amount;
         totalDoriki += (Double)EntityStatsCapability.get(player).map(IEntityStats::getDoriki).orElse((double)0.0F);
         Optional<IHakiData> hakiData = HakiCapability.get(player);
         totalHaki += (Float)hakiData.map(IHakiData::getBusoshokuHakiExp).orElse(0.0F);
         totalHaki += (Float)hakiData.map(IHakiData::getKenbunshokuHakiExp).orElse(0.0F);
      }

      double totalPossibleDoriki = (double)(ServerConfig.getDorikiLimit() * amount);
      float totalPossibleHaki = (float)(ServerConfig.getHakiExpLimit() * 2 * amount);
      double dorikiBonus = Mth.m_14008_(totalDoriki / totalPossibleDoriki, (double)0.0F, (double)0.5F);
      double hakiBonus = Mth.m_14008_((double)(totalHaki / totalPossibleHaki), (double)0.0F, (double)0.5F);
      double currentDifficulty = dorikiBonus * 0.3 + hakiBonus * 0.3 + (double)timePassed * 0.4;
      if (Double.isNaN(this.doriki)) {
         this.doriki = (double)1000.0F;
      }

      double newDoriki = (double)((float)(this.doriki * ((double)1.0F + dorikiBonus)));
      if (newDoriki < this.doriki) {
         newDoriki = this.doriki;
      }

      this.doriki = Mth.m_14008_(newDoriki, (double)1000.0F, (double)ServerConfig.getDorikiLimit());
      if (Float.isNaN(this.kenbunshokuExp)) {
         this.kenbunshokuExp = 10.0F;
      }

      float newObsHaki = (float)((double)this.kenbunshokuExp * ((double)1.0F + hakiBonus));
      if (newObsHaki < this.kenbunshokuExp) {
         newObsHaki = this.kenbunshokuExp;
      }

      this.kenbunshokuExp = Mth.m_14036_(newObsHaki, 10.0F, (float)ServerConfig.getHakiExpLimit());
      if (Float.isNaN(this.busoshokuExp)) {
         this.busoshokuExp = 10.0F;
      }

      float newBusoHaki = (float)((double)this.busoshokuExp * ((double)1.0F + hakiBonus));
      if (newBusoHaki < this.busoshokuExp) {
         newBusoHaki = this.busoshokuExp;
      }

      this.busoshokuExp = Mth.m_14036_(newBusoHaki, 10.0F, (float)ServerConfig.getHakiExpLimit());
      long newBounty = 100000L;
      if (this.faction.equals(ModFactions.PIRATE.get())) {
         newBounty = (long)((double)newBounty * ((double)1.0F + currentDifficulty));
         newBounty += (long)this.random.nextInt(10000);
      }

      this.bounty += newBounty;
      this.bounty = WyHelper.clamp(this.bounty, 10000L, 100000000000L);
      this.lastGameUpdate = world.m_46467_();
   }

   @Nullable
   public NotoriousEntity createTrackedMob(Level world) {
      NotoriousEntity entity = null;
      if (this.getFaction().equals(ModFactions.PIRATE.get())) {
         entity = new NotoriousEntity((EntityType)ModMobs.PIRATE_NOTORIOUS_CAPTAIN.get(), world, NotoriousEntity::initPirate, this);
      } else if (this.getFaction().equals(ModFactions.MARINE.get())) {
         entity = new NotoriousEntity((EntityType)ModMobs.MARINE_VICE_ADMIRAL.get(), world, NotoriousEntity::initMarine, this);
      }

      if (entity == null) {
         LogManager.getLogger(this).warn("Null entity found when creating a Notorious entity, this will most likely break things!");
      }

      return entity;
   }

   @Nullable
   public NotoriousEntity spawnTrackedMob(ServerLevel world, Vec3 pos) {
      NotoriousEntity entity = this.createTrackedMob(world);
      if (entity == null) {
         return null;
      } else {
         int y = world.m_6924_(Types.MOTION_BLOCKING, (int)pos.m_7096_(), (int)pos.m_7094_());
         entity.m_6034_(pos.m_7096_(), (double)(y + 1), pos.m_7094_());
         IEntityStats statsData = (IEntityStats)EntityStatsCapability.get(entity).orElse((Object)null);
         IHakiData hakiData = (IHakiData)HakiCapability.get(entity).orElse((Object)null);
         if (statsData != null && hakiData != null) {
            this.doriki = Mth.m_14008_(this.doriki, (double)1000.0F, (double)ServerConfig.getDorikiLimit());
            this.bounty = WyHelper.clamp(this.bounty, 10000L, 100000000000L);
            statsData.setDoriki(this.doriki);
            statsData.setFaction(this.faction);
            if (this.faction.equals(ModFactions.PIRATE.get())) {
               statsData.setBounty(this.bounty);
            }

            statsData.setRace(this.race);
            statsData.setSubRace(this.subRace);
            statsData.setFightingStyle(this.style);
            hakiData.setKenbunshokuHakiExp(this.kenbunshokuExp);
            hakiData.setBusoshokuHakiExp(this.busoshokuExp);
            double health = (double)200.0F + this.doriki / (double)20.0F;
            health = Mth.m_14008_(health, (double)200.0F, (double)ServerConfig.getDorikiLimit() / (double)20.0F);
            entity.m_21051_(Attributes.f_22276_).m_22100_(health);
            AttributeHelper.updateToughnessAttribute(entity);
            entity.m_5634_(entity.m_21233_());
            world.m_7967_(entity);
            return entity;
         } else {
            return null;
         }
      }
   }

   public CompoundTag save() {
      CompoundTag tag = new CompoundTag();
      tag.m_128356_("seed", this.seed);
      tag.m_128362_("uuid", this.uuid);
      tag.m_128356_("id", this.id);
      tag.m_128356_("lastGameUpdate", this.lastGameUpdate);
      tag.m_128356_("lastNewsUpdate", this.lastNewsUpdate);
      tag.m_128359_("faction", this.faction.getRegistryName().toString());
      tag.m_128359_("race", this.race.getRegistryName().toString());
      tag.m_128359_("style", this.style.getRegistryName().toString());
      tag.m_128347_("doriki", this.doriki);
      tag.m_128356_("bounty", this.bounty);
      tag.m_128350_("kenbunshokuExp", this.kenbunshokuExp);
      tag.m_128350_("busoshokuExp", this.busoshokuExp);
      return tag;
   }

   public boolean equals(Object other) {
      if (other instanceof TrackedNPC otherNPC) {
         return this.id == otherNPC.id ? true : super.equals(other);
      } else {
         return false;
      }
   }

   static {
      STYLES = Lists.newArrayList(new RegistryObject[]{ModFightingStyles.SWORDSMAN, ModFightingStyles.BRAWLER, ModFightingStyles.BLACK_LEG, ModFightingStyles.SNIPER});
      RACES = Lists.newArrayList(new RegistryObject[]{ModRaces.HUMAN, ModRaces.FISHMAN, ModRaces.MINK});
   }
}
