package xyz.pixelatedw.mineminenomi.api.challenges;

import com.google.common.base.Predicates;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentUtils;
import net.minecraft.network.protocol.game.ClientboundSetActionBarTextPacket;
import net.minecraft.network.protocol.game.ClientboundSetSubtitleTextPacket;
import net.minecraft.network.protocol.game.ClientboundSetTitleTextPacket;
import net.minecraft.network.protocol.game.ClientboundSetTitlesAnimationPacket;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraftforge.common.world.ForgeChunkManager;
import xyz.pixelatedw.mineminenomi.api.NuWorld;
import xyz.pixelatedw.mineminenomi.api.WyDebug;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponentKey;
import xyz.pixelatedw.mineminenomi.api.blockgen.BlockQueue;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.GoalHelper;
import xyz.pixelatedw.mineminenomi.api.protection.BlockProtectionRule;
import xyz.pixelatedw.mineminenomi.api.protection.DefaultProtectionRules;
import xyz.pixelatedw.mineminenomi.api.util.Interval;
import xyz.pixelatedw.mineminenomi.api.util.TPSDelta;
import xyz.pixelatedw.mineminenomi.challenges.arenas.PoneglyphLowspecArena;
import xyz.pixelatedw.mineminenomi.config.ServerConfig;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityCapability;
import xyz.pixelatedw.mineminenomi.data.entity.ability.IAbilityData;
import xyz.pixelatedw.mineminenomi.data.entity.challenge.ChallengeCapability;
import xyz.pixelatedw.mineminenomi.data.entity.challenge.IChallengeData;
import xyz.pixelatedw.mineminenomi.data.entity.haki.HakiCapability;
import xyz.pixelatedw.mineminenomi.data.entity.haki.IHakiData;
import xyz.pixelatedw.mineminenomi.data.world.ChallengesWorldData;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModTags;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18nChallenges;

public class InProgressChallenge {
   private static final int COUNTDOWN_SECONDS = 10;
   private static final int COUNTDOWN_TICKS = 200;
   private static final String[] LOADING_FRAMES = new String[]{"o o o", "O o o", "o O o", "o o O", "o o o"};
   private static final BlockProtectionRule REPAIR_RULE;
   private final UUID id;
   private int inProgressTick;
   private Interval readyInterval = (Interval)Interval.startAtMax(10).trackTPS();
   private Interval endWaitInterval = (Interval)Interval.startAtMax(10).trackTPS();
   private Interval.Mutable tickInterval = (Interval.Mutable)(new Interval.Mutable(5)).trackTPS();
   private Phase phase;
   private Result result;
   private ServerPlayer owner;
   private BlockPos returnPosition;
   private ResourceKey<Level> returnDimension;
   private ServerLevel shard;
   private Challenge challenge;
   private ChallengeCore.ArenaEntry arenaEntry;
   private BlockPos pos;
   private List<LivingEntity> group;
   private HashMap<UUID, ChallengeCache> groupCache;
   private Set<LivingEntity> enemies;
   private int tickLimit;
   private long startTick;
   private long finishTick;
   private ChallengeCore<?> core;
   private boolean isFree;
   private boolean isCompleted;
   private Set<BlockPos> blocksPlaced;
   private Optional<IChallengeData> ownerProps;
   private Random random;
   private ChallengeTeleporter teleporter;
   private BlockQueue blockQueue;
   private long startBuildTime;
   private int totalBlocksPlaced;
   private float deltaTime;
   private Set<BlockPos> trackedManuallyPlacedBlocks;

   public InProgressChallenge(UUID id, ServerPlayer owner, ServerLevel shard, @Nullable List<LivingEntity> group, Challenge challenge, boolean isFree) {
      this.phase = InProgressChallenge.Phase.BUILD;
      this.result = InProgressChallenge.Result.TBD;
      this.groupCache = new HashMap();
      this.enemies = new HashSet();
      this.isFree = false;
      this.isCompleted = false;
      this.ownerProps = Optional.empty();
      this.deltaTime = 0.0F;
      this.trackedManuallyPlacedBlocks = new HashSet();
      this.id = id;
      this.owner = owner;
      this.returnPosition = this.owner.m_20183_();
      this.returnDimension = this.owner.m_9236_().m_46472_();
      this.challenge = challenge;
      this.core = challenge.getCore();
      this.shard = shard;
      this.arenaEntry = PoneglyphLowspecArena.ARENA_INFO;
      this.pos = new BlockPos(0, 50, 0);
      this.group = new ArrayList();
      this.group.add(owner);
      if (group != null) {
         this.group.addAll(group);
      }

      this.isFree = isFree;
      this.ownerProps = ChallengeCapability.get(this.owner);
      this.random = new Random();
      this.teleporter = new ChallengeTeleporter(this);
      this.blockQueue = (new BlockQueue(this.shard)).setSpeed(ServerConfig.getArenaGenerationSpeed());
      this.tickLimit = challenge.getCore().getTimeLimit() * 60 * 20;
      this.inProgressTick = 0;
      this.tickInterval.restartIntervalToMax();
      this.readyInterval.restartIntervalToMax();
      this.endWaitInterval.restartIntervalToMax();
   }

   public void tick() {
      this.deltaTime = TPSDelta.INSTANCE.getDeltaTime();
      this.inProgressTick = (int)((float)this.inProgressTick + this.deltaTime);
      if ((this.phase == InProgressChallenge.Phase.RUN || this.phase == InProgressChallenge.Phase.END) && this.group.isEmpty()) {
         this.wipeOldEntities(false);
         this.phase = InProgressChallenge.Phase.POSTEND;
      } else if (this.tickInterval.canTick()) {
         if (this.phase.equals(InProgressChallenge.Phase.POSTEND)) {
            this.executePostEndPhase();
         } else if (this.phase.equals(InProgressChallenge.Phase.END)) {
            this.executeEndPhase();
         } else if (this.phase.equals(InProgressChallenge.Phase.RUN)) {
            this.executeRunPhase();
         } else if (this.phase.equals(InProgressChallenge.Phase.BUILD)) {
            this.executeBuildPhase();
         } else if (this.phase.equals(InProgressChallenge.Phase.SPAWN)) {
            this.executeSpawnPhase();
         } else if (this.phase.equals(InProgressChallenge.Phase.READY)) {
            this.executeReadyPhase();
         }

      }
   }

   private void executeBuildPhase() {
      IChallengeData props = (IChallengeData)this.ownerProps.orElse((Object)null);
      if (props == null) {
         this.result = InProgressChallenge.Result.TBD;
         this.phase = InProgressChallenge.Phase.POSTEND;
      } else {
         if (!this.blockQueue.isRunning()) {
            ForgeChunkManager.forceChunk(this.shard, "mineminenomi", this.pos, 0, 0, true, false);
            if (props.isArenaDirty()) {
               long cleanStart = System.nanoTime();
               ChallengeArena previousArena = this.arenaEntry.arena();
               new BoundingBox(-100, -100, -100, 100, 100, 100);
               if (props.getPreviousChallenge() != null) {
                  ChallengeCore.ArenaEntry entry = props.getPreviousChallenge().getArenaFromStyle(props.getPreviousArenaStyle(), props.getPreviousArenaClass());
                  if (entry != null) {
                     previousArena = entry.arena();
                  }
               }

               if (previousArena != null) {
                  BoundingBox previousArenBB = previousArena.getArenaLimits();
               }

               if (previousArena == null) {
                  previousArena = this.arenaEntry.arena();
               }

               long cleanEnd = (System.nanoTime() - cleanStart) / 1000000L;
               WyDebug.debug("EMERGENCY CLEAR TIME: " + (float)cleanEnd / 1000.0F + "s");
            }

            this.trackedManuallyPlacedBlocks.clear();
            this.startBuildTime = this.shard.m_46467_();
            this.arenaEntry.arena().buildArena(this, this.blockQueue);
            this.totalBlocksPlaced = this.blockQueue.getQueueSize();
            WyDebug.debug("Size: " + this.blockQueue.getQueueSize() + " blocks");
            this.blockQueue.start();
         } else {
            if (this.blockQueue.isDone()) {
               this.sendGroupActionbar(ModI18nChallenges.STARTING_CHALLENGE, 5, 20, 5);
               float buildEnd = (float)(this.shard.m_46467_() - this.startBuildTime) / 20.0F;
               WyDebug.debug("BUILD TIME: " + buildEnd + "s");
               props.markArenaDirty(true);
               props.setPreviousChallenge(this.core, this.arenaEntry.arena().getStyle(), this.arenaEntry.arena().getClass().getName());
               this.phase = InProgressChallenge.Phase.SPAWN;
               this.tickInterval.setInterval(100);
               this.blockQueue.stop();
               return;
            }

            this.blockQueue.tick();
            StringBuilder builder = new StringBuilder("Generating Arena - ");
            long buildEnd = (this.shard.m_46467_() - this.startBuildTime) / 20L;
            builder.append(buildEnd + "s - ");
            float complation = (1.0F - (float)this.blockQueue.getQueueSize() / (float)this.totalBlocksPlaced) * 100.0F;
            Object[] var10002 = new Object[]{complation};
            builder.append(String.format("%.1f", var10002) + "%");
            this.sendGroupActionbar(Component.m_237113_(builder.toString()), 5, 20, 5);
         }

      }
   }

   private void executeSpawnPhase() {
      this.wipeOldEntities(true);
      this.spawnChallengers();
      this.spawnEnemies();
      this.phase = InProgressChallenge.Phase.READY;
      this.tickInterval.setInterval(20);
   }

   private void executeReadyPhase() {
      if (this.readyInterval.getTick() <= 10) {
         String var10000 = String.valueOf(ChatFormatting.GOLD);
         Component countdownMessage = Component.m_237113_(var10000 + (this.readyInterval.getTick() - 1));
         this.sendGroupTitle(countdownMessage, 5, (int)Math.ceil((double)(10.0F * (1.0F + this.deltaTime))), 5);
      }

      if (this.readyInterval.canTick()) {
         this.phase = InProgressChallenge.Phase.RUN;
         this.group.forEach((entity) -> entity.m_21195_((MobEffect)ModEffects.IN_EVENT.get()));
         this.enemies.forEach((entity) -> entity.m_21195_((MobEffect)ModEffects.IN_EVENT.get()));
         this.sendGroupTitle(ModI18nChallenges.MESSAGE_START_FIGHT, 2, (int)Math.ceil((double)(5.0F * (1.0F + this.deltaTime))), 2);
         this.startTick = this.shard.m_46467_();
      }

   }

   private void executeRunPhase() {
      if (this.inProgressTick > this.tickLimit) {
         this.phase = InProgressChallenge.Phase.END;
         this.result = InProgressChallenge.Result.TIMEOUT;
         this.tickInterval.setInterval(20);
      } else {
         int groupAlive = 0;

         for(LivingEntity groupMember : this.group) {
            if (groupMember.m_21023_((MobEffect)ModEffects.CHALLENGE_FAILED.get())) {
               this.despawnChallenger(groupMember);
            } else if (groupMember.m_6084_() && NuWorld.isChallengeDimension(groupMember.m_9236_())) {
               ++groupAlive;
               break;
            }
         }

         if (groupAlive <= 0) {
            this.group.clear();
            this.phase = InProgressChallenge.Phase.END;
            this.result = InProgressChallenge.Result.DEATH;
         } else {
            if (!this.enemies.isEmpty()) {
               this.enemies.removeIf((entity) -> entity == null || !entity.m_6084_());
            } else if (this.enemies.isEmpty()) {
               this.phase = InProgressChallenge.Phase.END;
               this.result = InProgressChallenge.Result.WIN;
               this.finishTick = this.shard.m_46467_();
               this.tickInterval.setInterval(20);
               return;
            }

         }
      }
   }

   private void executePostEndPhase() {
      if (this.blockQueue.isRunning()) {
         if (this.blockQueue.isDone()) {
            this.blockQueue.stop();
         }

         this.blockQueue.tick();
      }

   }

   private void executeEndPhase() {
      if (this.endWaitInterval.canTick()) {
         this.phase = InProgressChallenge.Phase.POSTEND;
         long cleanStart = System.nanoTime();
         new BoundingBox(-50, -50, -50, 50, 50, 50);
         long cleanEnd = (System.nanoTime() - cleanStart) / 1000000L;
         WyDebug.debug("CLEAR TIME: " + (float)cleanEnd / 1000.0F + "s");
         ForgeChunkManager.forceChunk(this.shard, "mineminenomi", this.pos, 0, 0, false, false);

         for(LivingEntity entity : this.getGroup()) {
            this.despawnChallenger(entity);
         }

         if (this.result == InProgressChallenge.Result.WIN && !this.isCompleted) {
            this.completeChallenge();
            this.isCompleted = true;
         }

         this.groupCache.clear();
         this.ownerProps.ifPresent((props) -> props.markArenaDirty(false));
         ChallengesWorldData.get().stopChallenge(this);
      } else {
         String var10000 = ModI18nChallenges.MESSAGE_END_COUNTDOWN;
         Object[] var10001 = new Object[]{this.endWaitInterval.getTick()};
         Component countdownMessage = Component.m_237113_("§f§l" + Component.m_237110_(var10000, var10001).getString() + "§r");
         this.sendGroupActionbar(countdownMessage, 5, Math.round(20.0F * (1.0F + this.deltaTime)), 5);
      }

   }

   private void completeChallenge() {
      for(LivingEntity entity : this.getGroup()) {
         if (entity.m_6084_() && entity instanceof ServerPlayer player) {
            IChallengeData props = (IChallengeData)ChallengeCapability.get(player).orElse((Object)null);
            if (props != null) {
               Challenge challenge = props.getChallenge(this.getCore());
               if (challenge != null) {
                  int time = Math.round((float)(this.finishTick - this.startTick) / 20.0F);
                  String mode = "";
                  if (this.hasRestrictions() && !this.hasActiveRestrictions()) {
                     mode = " (TRAINING)";
                  } else if (challenge.isPersonalBest(time)) {
                     mode = " (PB)";
                  }

                  String timeStr = String.format("§2§l%02d:%02d§r%s", time / 60, time % 60, mode);
                  String rewardsMessage = "";
                  if (this.hasRewards()) {
                     rewardsMessage = challenge.getRewards(player);
                     challenge.tryUpdateBestTime(time);
                     challenge.setComplete(player, true);
                  }

                  String var10000 = ModI18nChallenges.MESSAGE_COMPLETION_REPORT.getString();
                  Component reportStr = Component.m_237113_(var10000 + timeStr);
                  player.m_213846_(this.core.getLocalizedTitle());
                  player.m_213846_(reportStr);
                  if (rewardsMessage != null) {
                     player.m_213846_(Component.m_237113_(rewardsMessage));
                  }
               }
            }
         }
      }

   }

   public void despawnChallenger(LivingEntity entity) {
      if (entity.m_6084_() && NuWorld.isChallengeDimension(entity.m_9236_()) && entity instanceof ServerPlayer player) {
         AbilityHelper.disableAbilities(player, 1, Predicates.alwaysTrue());
         ChallengeCache cache = (ChallengeCache)this.groupCache.get(player.m_20148_());
         if (cache != null) {
            cache.restore(entity);
         }

         player.f_19789_ = 0.0F;
         player.m_21219_();
         player.m_7311_(0);
         player.m_21195_((MobEffect)ModEffects.CHALLENGE_FAILED.get());
         this.teleporter.teleportToHomeWorld(entity);
      }

   }

   private void spawnEnemies() {
      Set<ChallengeArena.EnemySpawn> positions = this.challenge.getCore().getEnemySpawns().getEnemySpawns(this, this.arenaEntry.enemyPosition().getEnemySpawnPos(this));
      this.enemies = (Set)positions.stream().map(ChallengeArena.EnemySpawn::getEntity).collect(Collectors.toSet());

      for(ChallengeArena.EnemySpawn spawnPoint : positions) {
         LivingEntity entity = spawnPoint.getEntity();
         this.getShard().m_7967_(entity);
         entity.m_6021_((double)spawnPoint.getSpawnPos().getPos().m_123341_(), (double)spawnPoint.getSpawnPos().getPos().m_123342_(), (double)spawnPoint.getSpawnPos().getPos().m_123343_());
         entity.m_146922_(spawnPoint.getSpawnPos().getYaw());
         entity.m_146926_(spawnPoint.getSpawnPos().getPitch());
         if (entity instanceof Mob mob) {
            mob.m_6710_(this.getOwner());
            GoalHelper.lookAtEntity(mob, this.getOwner());
         }

         entity.m_7292_(new MobEffectInstance((MobEffect)ModEffects.IN_EVENT.get(), 300, 0));
         entity.m_5634_(entity.m_21233_());
      }

   }

   private void spawnChallengers() {
      for(LivingEntity entity : this.group) {
         if (entity instanceof ServerPlayer player) {
            this.teleporter.teleportToChallengeWorld(entity);
            if (ServerConfig.isChallengesCachingEnabled()) {
               this.groupCache.put(entity.m_20148_(), ChallengeCache.from((LivingEntity)player));
            }
         }

         entity.m_5634_(entity.m_21233_());
         if (entity instanceof Player player) {
            player.m_36324_().m_38707_(20, 20.0F);
         }

         entity.m_21219_();
         entity.m_7292_(new MobEffectInstance((MobEffect)ModEffects.IN_EVENT.get(), 300, 0));
         IAbilityData props = (IAbilityData)AbilityCapability.get(entity).orElse((Object)null);
         IHakiData hakiProps = (IHakiData)HakiCapability.get(entity).orElse((Object)null);
         if (props != null) {
            for(IAbility ability : props.getEquippedAbilities()) {
               if (ability != null) {
                  ability.getComponent((AbilityComponentKey)ModAbilityComponents.DISABLE.get()).ifPresent((c) -> c.startDisable(entity, 1.0F));
                  ability.getComponent((AbilityComponentKey)ModAbilityComponents.DISABLE.get()).ifPresent((c) -> c.stopDisable(entity));
                  ability.getComponent((AbilityComponentKey)ModAbilityComponents.COOLDOWN.get()).ifPresent((c) -> c.stopCooldown(entity));
               }
            }
         }

         if (hakiProps != null) {
            hakiProps.setHakiOveruse(0);
         }
      }

      this.shard.m_46465_();
   }

   public void wipeOldEntities(boolean force) {
      try {
         this.shard.m_142646_().m_142273_().forEach((entity) -> {
            if (entity != null && !(entity instanceof Player)) {
               entity.m_146870_();
            }

         });
      } catch (Exception e) {
         WyDebug.error("Something went wrong while wiping old entities from a challenge: " + String.valueOf(this.challenge.getCore().getRegistryKey()), e);
      }

   }

   private void sendGroupTitle(Component title, int fadeInTime, int stayTime, int fadeOutTime) {
      this.sendGroupTitle(title, (Component)null, fadeInTime, stayTime, fadeOutTime);
   }

   private void sendGroupTitle(Component title, @Nullable Component subtitle, int fadeInTime, int stayTime, int fadeOutTime) {
      for(LivingEntity groupMember : this.group) {
         if (groupMember instanceof ServerPlayer player) {
            try {
               player.f_8906_.m_9829_(new ClientboundSetTitlesAnimationPacket(fadeInTime, stayTime, fadeOutTime));
               Component titleComponent = ComponentUtils.m_130731_(player.m_20203_(), title, player, 0);
               player.f_8906_.m_9829_(new ClientboundSetTitleTextPacket(titleComponent));
               if (subtitle != null) {
                  Component subtitleComponent = ComponentUtils.m_130731_(player.m_20203_(), subtitle, player, 0);
                  player.f_8906_.m_9829_(new ClientboundSetSubtitleTextPacket(subtitleComponent));
               }
            } catch (Exception e) {
               WyDebug.error("Failed to send group title", e);
            }
         }
      }

   }

   private void sendGroupActionbar(Component text, int fadeInTime, int stayTime, int fadeOutTime) {
      for(LivingEntity groupMember : this.group) {
         if (groupMember.m_6084_() && groupMember instanceof ServerPlayer player) {
            try {
               player.f_8906_.m_9829_(new ClientboundSetTitlesAnimationPacket(fadeInTime, stayTime, fadeOutTime));
               Component titleComponent = ComponentUtils.m_130731_(player.m_20203_(), text, player, 0);
               player.f_8906_.m_9829_(new ClientboundSetActionBarTextPacket(titleComponent));
            } catch (Exception e) {
               WyDebug.error("Failed to send group actionbar", e);
            }
         }
      }

   }

   public boolean hasRewards() {
      return this.isStandardDifficulty() ? true : this.hasActiveRestrictions();
   }

   public boolean hasRestrictions() {
      return !this.isStandardDifficulty();
   }

   public boolean hasActiveRestrictions() {
      return !this.isFree;
   }

   public boolean canDelete() {
      return this.phase == InProgressChallenge.Phase.POSTEND;
   }

   public boolean isBuilding() {
      return this.phase == InProgressChallenge.Phase.BUILD || this.phase == InProgressChallenge.Phase.POSTEND;
   }

   public boolean isStandardDifficulty() {
      return this.core.getDifficulty() == ChallengeDifficulty.STANDARD;
   }

   public boolean isHardDifficulty() {
      return this.core.getDifficulty() == ChallengeDifficulty.HARD;
   }

   public boolean isUltimateDifficulty() {
      return this.core.getDifficulty() == ChallengeDifficulty.ULTIMATE;
   }

   public BlockPos getArenaPos() {
      return this.pos;
   }

   public UUID getId() {
      return this.id;
   }

   public ChallengeCore<?> getCore() {
      return this.core;
   }

   public List<LivingEntity> getGroup() {
      return this.group;
   }

   public HashMap<UUID, ChallengeCache> getGroupCache() {
      return this.groupCache;
   }

   public Set<LivingEntity> getEnemies() {
      return this.enemies;
   }

   public ServerPlayer getOwner() {
      return this.owner;
   }

   public ServerLevel getShard() {
      return this.shard;
   }

   public ChallengeCore.IChallengerPosition getChallengerPosition() {
      return this.arenaEntry.challengerPosition();
   }

   public ChallengeArena getArena() {
      return this.arenaEntry.arena();
   }

   public Random getRNG() {
      return this.random;
   }

   public ResourceKey<Level> getReturnDimension() {
      return this.returnDimension;
   }

   public BlockPos getReturnPosition() {
      return this.returnPosition;
   }

   public void trackBlockPos(BlockPos pos) {
      this.trackedManuallyPlacedBlocks.add(pos);
   }

   public void trackBlockPos(Set<BlockPos> pos) {
      this.trackedManuallyPlacedBlocks.addAll(pos);
   }

   static {
      REPAIR_RULE = (new BlockProtectionRule.Builder(new BlockProtectionRule[]{DefaultProtectionRules.CORE_FOLIAGE})).addApprovedTags(ModTags.Blocks.BLOCK_PROT_LIQUIDS, ModTags.Blocks.BLOCK_PROT_RESTRICTED).addBannedTags(ModTags.Blocks.BLOCK_PROT_AIR).build();
   }

   private static enum Phase {
      BUILD,
      SPAWN,
      READY,
      RUN,
      END,
      POSTEND;

      // $FF: synthetic method
      private static Phase[] $values() {
         return new Phase[]{BUILD, SPAWN, READY, RUN, END, POSTEND};
      }
   }

   private static enum Result {
      TBD,
      WIN,
      TIMEOUT,
      DEATH;

      // $FF: synthetic method
      private static Result[] $values() {
         return new Result[]{TBD, WIN, TIMEOUT, DEATH};
      }
   }
}
