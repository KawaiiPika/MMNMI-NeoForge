package xyz.pixelatedw.mineminenomi.data.entity.stats;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.MinecraftForge;
import org.jetbrains.annotations.Nullable;
import xyz.pixelatedw.mineminenomi.abilities.cyborg.ColaBackpackBonusAbility;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Faction;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.FightingStyle;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Race;
import xyz.pixelatedw.mineminenomi.api.enums.Currency;
import xyz.pixelatedw.mineminenomi.api.enums.StatChangeSource;
import xyz.pixelatedw.mineminenomi.api.enums.TrainingPointType;
import xyz.pixelatedw.mineminenomi.api.events.stats.BountyEvent;
import xyz.pixelatedw.mineminenomi.api.events.stats.CurrencyEvent;
import xyz.pixelatedw.mineminenomi.api.events.stats.DorikiEvent;
import xyz.pixelatedw.mineminenomi.api.events.stats.LoyaltyEvent;
import xyz.pixelatedw.mineminenomi.api.factions.IFactionRank;
import xyz.pixelatedw.mineminenomi.api.helpers.AttributeHelper;
import xyz.pixelatedw.mineminenomi.config.ServerConfig;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityCapability;
import xyz.pixelatedw.mineminenomi.data.entity.devilfruit.DevilFruitCapability;
import xyz.pixelatedw.mineminenomi.init.ModAdvancements;
import xyz.pixelatedw.mineminenomi.init.ModFactions;
import xyz.pixelatedw.mineminenomi.init.ModFightingStyles;
import xyz.pixelatedw.mineminenomi.init.ModFruits;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.init.ModRaces;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18n;
import xyz.pixelatedw.mineminenomi.items.AkumaNoMiItem;
import xyz.pixelatedw.mineminenomi.packets.server.ui.SUpdateRogueStateEventPacket;

public class EntityStatsBase implements IEntityStats {
   private LivingEntity owner;
   private double doriki;
   private int cola = 100;
   private int ultraCola = 0;
   private double loyalty = (double)0.0F;
   private int invulnerableTime = 0;
   private Vec3 flyingSpeed;
   private long bounty;
   private long belly;
   private long extol;
   private Optional<Faction> faction;
   private Optional<Race> race;
   private Optional<Race> subRace;
   private Optional<FightingStyle> fightingStyle;
   private boolean hasShadow;
   private boolean hasHeart;
   private boolean inCombatMode;
   private boolean hasStrawDoll;
   private boolean isRogue;
   private double damageMultiplier;
   private int jumpTicks;
   private boolean isJumping;
   private float leftImpulse;
   private float forwardImpulse;
   private boolean hadChiyuEffect;
   private float originalChiyupopoHealth;
   private int freedSlaves;
   private Optional<Integer> skinTint;
   private Deque<ResourceLocation> screenShaders;
   private boolean dyeLayersCheck;
   private final EnumMap<TrainingPointType, Integer> trainingPoints;

   public EntityStatsBase(LivingEntity owner) {
      this.flyingSpeed = Vec3.f_82478_;
      this.bounty = 0L;
      this.belly = 0L;
      this.extol = 0L;
      this.faction = Optional.empty();
      this.race = Optional.empty();
      this.subRace = Optional.empty();
      this.fightingStyle = Optional.empty();
      this.hasShadow = true;
      this.hasHeart = true;
      this.inCombatMode = false;
      this.hasStrawDoll = true;
      this.isRogue = false;
      this.damageMultiplier = (double)1.0F;
      this.jumpTicks = 0;
      this.isJumping = false;
      this.leftImpulse = 0.0F;
      this.forwardImpulse = 0.0F;
      this.hadChiyuEffect = false;
      this.originalChiyupopoHealth = 0.0F;
      this.skinTint = Optional.empty();
      this.screenShaders = new ArrayDeque();
      this.dyeLayersCheck = false;
      this.trainingPoints = new EnumMap(TrainingPointType.class);
      this.owner = owner;
   }

   public double getDoriki() {
      return this.doriki;
   }

   public boolean alterDoriki(double value, StatChangeSource source) {
      LivingEntity pre = this.owner;
      if (pre instanceof Player player) {
         DorikiEvent.Pre pre = new DorikiEvent.Pre(player, value, source);
         if (MinecraftForge.EVENT_BUS.post(pre)) {
            return false;
         }

         value = pre.getDoriki();
      }

      this.doriki = Mth.m_14008_(this.doriki + value, (double)0.0F, (double)ServerConfig.getDorikiLimit());
      pre = this.owner;
      if (pre instanceof Player player) {
         DorikiEvent.Post post = new DorikiEvent.Post(player, value, source);
         MinecraftForge.EVENT_BUS.post(post);
         LivingEntity var7 = this.owner;
         if (var7 instanceof ServerPlayer serverPlayer) {
            ModAdvancements.OBTAIN_DORIKI.trigger(serverPlayer, (int)this.doriki);
         }
      }

      AttributeHelper.updateHPAttribute(this.owner);
      AttributeHelper.updateToughnessAttribute(this.owner);
      return true;
   }

   public void setDoriki(double value) {
      this.doriki = Mth.m_14008_(value, (double)0.0F, (double)ServerConfig.getDorikiLimit());
   }

   public long getBelly() {
      return this.belly;
   }

   public boolean alterBelly(long value, StatChangeSource source) {
      LivingEntity pre = this.owner;
      if (pre instanceof Player player) {
         CurrencyEvent.Pre pre = new CurrencyEvent.Pre(player, value, Currency.BELLY, source);
         if (MinecraftForge.EVENT_BUS.post(pre)) {
            return false;
         }

         value = pre.getAmount();
      }

      this.belly = WyHelper.clamp(this.belly + value, 0L, 999999999L);
      pre = this.owner;
      if (pre instanceof Player player) {
         CurrencyEvent.Post post = new CurrencyEvent.Post(player, value, Currency.BELLY, source);
         MinecraftForge.EVENT_BUS.post(post);
         if (player instanceof ServerPlayer serverPlayer) {
            ModAdvancements.OBTAIN_BELLY.trigger(serverPlayer, (int)this.belly, false);
         }
      }

      return true;
   }

   public void setBelly(long value) {
      this.belly = value;
   }

   public long getExtol() {
      return this.extol;
   }

   public boolean alterExtol(long value, StatChangeSource source) {
      LivingEntity extolEvent = this.owner;
      if (extolEvent instanceof Player player) {
         CurrencyEvent extolEvent = new CurrencyEvent(player, value, Currency.EXTOL, source);
         if (MinecraftForge.EVENT_BUS.post(extolEvent)) {
            return false;
         }

         value = extolEvent.getAmount();
      }

      this.extol = WyHelper.clamp(this.extol + value, 0L, 999999999L);
      extolEvent = this.owner;
      if (extolEvent instanceof Player player) {
         CurrencyEvent.Post post = new CurrencyEvent.Post(player, value, Currency.EXTOL, source);
         MinecraftForge.EVENT_BUS.post(post);
      }

      return true;
   }

   public void setExtol(long value) {
      this.extol = value;
   }

   public long getBounty() {
      return this.bounty;
   }

   public boolean alterBounty(long value, StatChangeSource source) {
      LivingEntity pre = this.owner;
      if (pre instanceof Player player) {
         BountyEvent.Pre pre = new BountyEvent.Pre(player, value, source);
         if (MinecraftForge.EVENT_BUS.post(pre)) {
            return false;
         }

         value = pre.getBounty();
      }

      this.bounty = WyHelper.clamp(this.bounty + value, 0L, 100000000000L);
      pre = this.owner;
      if (pre instanceof Player player) {
         BountyEvent.Post post = new BountyEvent.Post(player, value, source);
         MinecraftForge.EVENT_BUS.post(post);
         LivingEntity var7 = this.owner;
         if (var7 instanceof ServerPlayer serverPlayer) {
            ModAdvancements.OBTAIN_BOUNTY.trigger(serverPlayer, (int)this.bounty);
         }
      }

      return true;
   }

   public void setBounty(long value) {
      this.bounty = value;
   }

   public int getCola() {
      return this.cola;
   }

   public void alterCola(int value) {
      this.cola = Mth.m_14045_(this.cola + value, 0, this.getMaxCola());
   }

   public void setCola(int value) {
      this.cola = Mth.m_14045_(value, 0, this.getMaxCola());
   }

   public void updateCola() {
      this.cola = Mth.m_14045_(this.cola, 0, this.getMaxCola());
   }

   public int getMaxCola() {
      int maxCola = 100;
      maxCola += this.ultraCola * 20;
      if (this.owner != null) {
         boolean hasColaBackpackPassive = AbilityCapability.get(this.owner).map((props) -> (ColaBackpackBonusAbility)props.getPassiveAbility((AbilityCore)ColaBackpackBonusAbility.INSTANCE.get())).isPresent();
         if (hasColaBackpackPassive) {
            maxCola += 500;
         }
      }

      maxCola = Mth.m_14045_(maxCola, 0, 1000);
      return maxCola;
   }

   public int getUltraCola() {
      return this.ultraCola;
   }

   public void setUltraCola(int value) {
      this.ultraCola = Mth.m_14045_(value, 0, 20);
   }

   public void addUltraCola(int value) {
      this.ultraCola = Mth.m_14045_(this.ultraCola + value, 0, 20);
   }

   public double getLoyalty() {
      return this.loyalty;
   }

   public boolean alterLoyalty(double value, StatChangeSource source) {
      LivingEntity pre = this.owner;
      if (pre instanceof Player player) {
         LoyaltyEvent.Pre pre = new LoyaltyEvent.Pre(player, value, source);
         if (MinecraftForge.EVENT_BUS.post(pre)) {
            return false;
         }

         value = pre.getLoyalty();
      }

      this.loyalty = Mth.m_14008_(this.loyalty + value, (double)-5.0F, (double)100.0F);
      pre = this.owner;
      if (pre instanceof Player player) {
         LoyaltyEvent.Post post = new LoyaltyEvent.Post(player, value, source);
         MinecraftForge.EVENT_BUS.post(post);
         if (value > (double)0.0F) {
            player.m_5661_(ModI18n.INFO_LOYALTY_INCREASED, true);
            if (player instanceof ServerPlayer) {
               ModAdvancements.OBTAIN_LOYALTY.trigger((ServerPlayer)this.owner, (int)this.loyalty);
            }
         } else if (value < (double)0.0F) {
            if (this.loyalty <= (double)-5.0F) {
               this.setFaction((Faction)ModFactions.PIRATE.get());
               Component message = Component.m_237110_(ModI18n.INFO_FACTION_CHANGE, new Object[]{((Faction)ModFactions.PIRATE.get()).getLabel().getString()});
               WyHelper.sendMessage(player, message);
            } else {
               WyHelper.sendMessage(player, ModI18n.INFO_LOYALTY_DROPPED);
            }
         }
      }

      return true;
   }

   public void setLoyalty(double value) {
      this.loyalty = value;
   }

   public int getInvulnerableTime() {
      return this.invulnerableTime;
   }

   public void alterInvulnerableTime(int value) {
      this.invulnerableTime = Math.max(0, this.invulnerableTime + value);
   }

   public void setInvulnerableTime(int value) {
      this.invulnerableTime = Math.max(value, 0);
   }

   public <E extends Enum<E> & IFactionRank> Optional<E> getRank() {
      if (this.faction.isPresent()) {
         Faction faction = (Faction)this.faction.get();
         if (faction.getRanks() == null) {
            return Optional.empty();
         }

         E[] ranks = faction.getRanks();
         if (this.loyalty <= (double)0.0F) {
            return Optional.of(ranks[0]);
         }

         for(int i = 0; i < ranks.length; ++i) {
            E rank = ranks[i];
            if (((IFactionRank)rank).isUnlocked(this.owner)) {
               return Optional.of(rank);
            }
         }
      }

      return Optional.empty();
   }

   public <E extends Enum<E> & IFactionRank> boolean hasRank(E rank) {
      Optional<E> rankOpt = this.<E>getRank();
      if (rankOpt.isEmpty()) {
         return false;
      } else {
         return ((Enum)rankOpt.get()).ordinal() >= rank.ordinal();
      }
   }

   public boolean isPirate() {
      Optional var10000 = this.faction;
      Faction var10001 = (Faction)ModFactions.PIRATE.get();
      Objects.requireNonNull(var10001);
      return (Boolean)var10000.map(var10001::equals).orElse(false);
   }

   public boolean isMarine() {
      Optional var10000 = this.faction;
      Faction var10001 = (Faction)ModFactions.MARINE.get();
      Objects.requireNonNull(var10001);
      return (Boolean)var10000.map(var10001::equals).orElse(false);
   }

   public boolean isBountyHunter() {
      Optional var10000 = this.faction;
      Faction var10001 = (Faction)ModFactions.BOUNTY_HUNTER.get();
      Objects.requireNonNull(var10001);
      return (Boolean)var10000.map(var10001::equals).orElse(false);
   }

   public boolean isRevolutionary() {
      Optional var10000 = this.faction;
      Faction var10001 = (Faction)ModFactions.REVOLUTIONARY_ARMY.get();
      Objects.requireNonNull(var10001);
      return (Boolean)var10000.map(var10001::equals).orElse(false);
   }

   public boolean isBandit() {
      Optional var10000 = this.faction;
      Faction var10001 = (Faction)ModFactions.BANDIT.get();
      Objects.requireNonNull(var10001);
      return (Boolean)var10000.map(var10001::equals).orElse(false);
   }

   public boolean isWorldGovernment() {
      Optional var10000 = this.faction;
      Faction var10001 = (Faction)ModFactions.WORLD_GOVERNMENT.get();
      Objects.requireNonNull(var10001);
      return (Boolean)var10000.map(var10001::equals).orElse(false);
   }

   public boolean isCivilian() {
      Optional var10000 = this.faction;
      Faction var10001 = (Faction)ModFactions.CIVILIAN.get();
      Objects.requireNonNull(var10001);
      return (Boolean)var10000.map(var10001::equals).orElse(false);
   }

   public boolean hasFaction() {
      return this.faction.isPresent();
   }

   public void setFaction(@Nullable Faction value) {
      this.faction = Optional.ofNullable(value);
   }

   public Optional<Faction> getFaction() {
      return this.faction;
   }

   public boolean isHuman() {
      return DevilFruitCapability.hasDevilFruit(this.owner, (AkumaNoMiItem)ModFruits.HITO_HITO_NO_MI.get()) || (Boolean)this.race.map((race) -> race.equals(ModRaces.HUMAN.get())).orElse(false);
   }

   public boolean isFishman() {
      return (Boolean)this.race.map((race) -> race.equals(ModRaces.FISHMAN.get())).orElse(false);
   }

   public boolean isCyborg() {
      return (Boolean)this.race.map((race) -> race.equals(ModRaces.CYBORG.get())).orElse(false);
   }

   public boolean isMink() {
      return (Boolean)this.race.map((race) -> race.equals(ModRaces.MINK.get())).orElse(false);
   }

   public boolean hasRace() {
      return this.race.isPresent();
   }

   public void setRace(@Nullable Race value) {
      this.race = Optional.ofNullable(value);
   }

   public Optional<Race> getRace() {
      return this.race;
   }

   public boolean isSwordsman() {
      return (Boolean)this.fightingStyle.map((fs) -> fs.equals(ModFightingStyles.SWORDSMAN.get())).orElse(false);
   }

   public boolean isSniper() {
      return (Boolean)this.fightingStyle.map((fs) -> fs.equals(ModFightingStyles.SNIPER.get())).orElse(false);
   }

   public boolean isDoctor() {
      return (Boolean)this.fightingStyle.map((fs) -> fs.equals(ModFightingStyles.DOCTOR.get())).orElse(false);
   }

   public boolean isWeatherWizard() {
      return (Boolean)this.fightingStyle.map((fs) -> fs.equals(ModFightingStyles.ART_OF_WEATHER.get())).orElse(false);
   }

   public boolean isBlackLeg() {
      return (Boolean)this.fightingStyle.map((fs) -> fs.equals(ModFightingStyles.BLACK_LEG.get())).orElse(false);
   }

   public boolean isBrawler() {
      return (Boolean)this.fightingStyle.map((fs) -> fs.equals(ModFightingStyles.BRAWLER.get())).orElse(false);
   }

   public boolean hasFightingStyle() {
      return this.fightingStyle.isPresent();
   }

   public void setFightingStyle(@Nullable FightingStyle value) {
      this.fightingStyle = Optional.ofNullable(value);
   }

   public Optional<FightingStyle> getFightingStyle() {
      return this.fightingStyle;
   }

   public boolean hasShadow() {
      return this.hasShadow;
   }

   public void setShadow(boolean value) {
      this.hasShadow = value;
   }

   public boolean hasHeart() {
      return this.hasHeart;
   }

   public void setHeart(boolean value) {
      this.hasHeart = value;
   }

   public boolean isInCombatMode() {
      return this.inCombatMode;
   }

   public void setCombatMode(boolean value) {
      this.inCombatMode = value;
   }

   public boolean hasStrawDoll() {
      return this.hasStrawDoll;
   }

   public void setStrawDoll(boolean value) {
      this.hasStrawDoll = value;
   }

   public double getDamageMultiplier() {
      return this.damageMultiplier;
   }

   public void setDamageMultiplier(double multiplier) {
      this.damageMultiplier = multiplier;
   }

   public boolean isBunnyMink() {
      return (Boolean)this.subRace.map((fs) -> fs.equals(ModRaces.MINK_BUNNY.get())).orElse(false);
   }

   public boolean isDogMink() {
      return (Boolean)this.subRace.map((fs) -> fs.equals(ModRaces.MINK_DOG.get())).orElse(false);
   }

   public boolean isLionMink() {
      return (Boolean)this.subRace.map((fs) -> fs.equals(ModRaces.MINK_LION.get())).orElse(false);
   }

   public boolean isSawsharkFishman() {
      return (Boolean)this.subRace.map((fs) -> fs.equals(ModRaces.FISHMAN_SAWSHARK.get())).orElse(false);
   }

   public void setSubRace(@Nullable Race race) {
      if (race == null || !race.isSubRace()) {
         race = null;
      }

      this.subRace = Optional.ofNullable(race);
   }

   public Optional<Race> getSubRace() {
      return this.subRace;
   }

   public void setSkinTint(int color) {
      this.skinTint = Optional.of(-16777216 | color);
   }

   public Optional<Integer> getSkinTint() {
      return this.skinTint;
   }

   public boolean isRogue() {
      return this.isRogue;
   }

   public void setRogue(boolean value) {
      this.isRogue = value;
      LivingEntity var3 = this.owner;
      if (var3 instanceof ServerPlayer player) {
         ModNetwork.sendTo(new SUpdateRogueStateEventPacket(player), player);
      }

   }

   public float getLeftImpulse() {
      return this.leftImpulse;
   }

   public void setLeftImpulse(float value) {
      this.leftImpulse = Mth.m_14036_(value, -1.0F, 1.0F);
   }

   public float getForwardImpulse() {
      return this.forwardImpulse;
   }

   public void setForwardImpulse(float value) {
      this.forwardImpulse = Mth.m_14036_(value, -1.0F, 1.0F);
   }

   public boolean isJumping() {
      return this.isJumping;
   }

   public void setJumping(boolean value) {
      this.isJumping = value;
   }

   public int getJumpTicks() {
      return this.jumpTicks;
   }

   public void setJumpTicks(int value) {
      this.jumpTicks = value;
   }

   public void alterJumpTicks(int value) {
      this.jumpTicks += value;
   }

   public void addScreenShader(ResourceLocation shader) {
      if (!this.screenShaders.contains(shader)) {
         this.screenShaders.addLast(shader);
         this.updateScreenShader();
      }
   }

   public boolean hasScreenShaderActive(ResourceLocation shader) {
      return this.screenShaders.contains(shader);
   }

   public void removeScreenShader(ResourceLocation shader) {
      if (this.screenShaders.contains(shader)) {
         this.screenShaders.removeLastOccurrence(shader);
         this.updateScreenShader();
      }
   }

   public void updateScreenShader() {
      GameRenderer gameRenderer = Minecraft.m_91087_().f_91063_;
      ResourceLocation shader = (ResourceLocation)this.screenShaders.peekFirst();
      if (shader != null) {
         gameRenderer.m_109128_(shader);
      } else {
         gameRenderer.m_109149_().close();
      }

   }

   public boolean hadChiyuEffect() {
      return this.hadChiyuEffect;
   }

   public void setChiyuEffect(boolean value) {
      this.hadChiyuEffect = value;
   }

   public float getOriginalChiyupopoHealth() {
      return this.originalChiyupopoHealth;
   }

   public void setOriginalChiyupopoHealth(float health) {
      this.originalChiyupopoHealth = health;
   }

   public void setDyeLayerCheck(boolean flag) {
      this.dyeLayersCheck = flag;
   }

   public boolean isDyeLayerCheck() {
      return this.dyeLayersCheck;
   }

   public int getTrainingPoints(TrainingPointType pType) {
      return (Integer)this.trainingPoints.getOrDefault(pType, 0);
   }

   public void alterTrainingPoints(TrainingPointType pType, int pPoints) {
      int currentPoints = (Integer)this.trainingPoints.getOrDefault(pType, 0);
      this.trainingPoints.put(pType, Math.max(currentPoints + pPoints, 0));
   }

   public int getFreedSlaves() {
      return this.freedSlaves;
   }

   public void addFreedSlaves(int count) {
      this.freedSlaves += count;
      LivingEntity var3 = this.owner;
      if (var3 instanceof ServerPlayer serverPlayer) {
         ModAdvancements.FREED_SLAVES.trigger(serverPlayer, this.freedSlaves);
      }

   }

   public void setFreedSlaves(int count) {
      this.freedSlaves = count;
   }

   public void setFlyingSpeed(Vec3 move) {
      this.flyingSpeed = move;
   }

   public Vec3 getFlyingSpeed() {
      return this.flyingSpeed;
   }

   public CompoundTag serializeNBT() {
      CompoundTag nbt = new CompoundTag();
      nbt.m_128347_("doriki", this.getDoriki());
      nbt.m_128405_("cola", this.getCola());
      nbt.m_128405_("ultraCola", this.getUltraCola());
      nbt.m_128347_("loyalty", this.getLoyalty());
      nbt.m_128405_("invulnerableTime", this.getInvulnerableTime());
      nbt.m_128347_("damageMultiplier", this.getDamageMultiplier());
      nbt.m_128356_("bounty", this.getBounty());
      nbt.m_128356_("belly", this.getBelly());
      nbt.m_128356_("extol", this.getExtol());
      this.faction.ifPresent((faction) -> nbt.m_128359_("faction", faction.getRegistryName().toString()));
      this.race.ifPresent((race) -> nbt.m_128359_("race", race.getRegistryName().toString()));
      this.subRace.ifPresent((race) -> nbt.m_128359_("subRace", race.getRegistryName().toString()));
      this.fightingStyle.ifPresent((style) -> nbt.m_128359_("fightingStyle", style.getRegistryName().toString()));
      nbt.m_128379_("hasShadow", this.hasShadow());
      nbt.m_128379_("hasHeart", this.hasHeart());
      nbt.m_128379_("hasStrawDoll", this.hasStrawDoll());
      nbt.m_128379_("isInCombatMode", this.isInCombatMode());
      nbt.m_128379_("isRogue", this.isRogue());
      nbt.m_128379_("hadChiyuEffect", this.hadChiyuEffect());
      nbt.m_128350_("originalChiyupopoHealth", this.getOriginalChiyupopoHealth());
      nbt.m_128405_("freedSlaves", this.getFreedSlaves());
      this.getSkinTint().ifPresent((tint) -> nbt.m_128405_("skinTint", tint));
      CompoundTag trainingPointsTag = new CompoundTag();

      for(Map.Entry<TrainingPointType, Integer> entry : this.trainingPoints.entrySet()) {
         trainingPointsTag.m_128405_(((TrainingPointType)entry.getKey()).name(), (Integer)entry.getValue());
      }

      nbt.m_128365_("trainingPoints", trainingPointsTag);
      return nbt;
   }

   public void deserializeNBT(CompoundTag nbt) {
      this.setDoriki(nbt.m_128459_("doriki"));
      this.setUltraCola(nbt.m_128451_("ultraCola"));
      this.setCola(nbt.m_128451_("cola"));
      this.setLoyalty((double)nbt.m_128451_("loyalty"));
      this.setInvulnerableTime(nbt.m_128451_("invulnerableTime"));
      this.setDamageMultiplier(nbt.m_128459_("damageMultiplier"));
      this.setBelly(nbt.m_128454_("belly"));
      this.setBounty(nbt.m_128454_("bounty"));
      this.setExtol(nbt.m_128454_("extol"));
      if (nbt.m_128441_("faction")) {
         ResourceLocation id = ResourceLocation.parse(nbt.m_128461_("faction"));
         this.setFaction(Faction.get(id));
      }

      if (nbt.m_128441_("race")) {
         ResourceLocation id = ResourceLocation.parse(nbt.m_128461_("race"));
         this.setRace(Race.get(id));
      }

      if (nbt.m_128441_("subRace")) {
         ResourceLocation id = ResourceLocation.parse(nbt.m_128461_("subRace"));
         Race race = Race.get(id);
         if (race != null && race.isSubRace()) {
            this.setSubRace(race);
         }
      }

      if (nbt.m_128441_("fightingStyle")) {
         ResourceLocation id = ResourceLocation.parse(nbt.m_128461_("fightingStyle"));
         this.setFightingStyle(FightingStyle.get(id));
      }

      this.setShadow(nbt.m_128471_("hasShadow"));
      this.setHeart(nbt.m_128471_("hasHeart"));
      this.setStrawDoll(nbt.m_128471_("hasStrawDoll"));
      this.setCombatMode(nbt.m_128471_("isInCombatMode"));
      this.setRogue(nbt.m_128471_("isRogue"));
      this.setChiyuEffect(nbt.m_128471_("hadChiyuEffect"));
      this.setOriginalChiyupopoHealth(nbt.m_128457_("originalChiyupopoHealth"));
      this.setFreedSlaves(nbt.m_128451_("freedSlaves"));
      if (nbt.m_128441_("skinTint")) {
         this.skinTint = Optional.of(nbt.m_128451_("skinTint"));
      }

      this.trainingPoints.clear();
      CompoundTag trainingPointsTag = nbt.m_128469_("trainingPoints");

      for(String key : trainingPointsTag.m_128431_()) {
         TrainingPointType type = TrainingPointType.valueOf(key);
         int value = trainingPointsTag.m_128451_(key);
         this.trainingPoints.put(type, value);
      }

   }
}
