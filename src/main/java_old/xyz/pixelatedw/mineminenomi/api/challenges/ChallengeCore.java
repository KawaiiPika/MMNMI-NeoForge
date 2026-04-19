package xyz.pixelatedw.mineminenomi.api.challenges;

import com.google.common.collect.HashMultimap;
import java.util.Iterator;
import java.util.Set;
import java.util.function.Supplier;
import net.minecraft.Util;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.IForgeRegistry;
import org.jetbrains.annotations.Nullable;
import xyz.pixelatedw.mineminenomi.api.WyRegistry;

public class ChallengeCore<T extends Challenge> {
   private ResourceLocation key;
   private String id;
   private @Nullable Component title;
   private final String unlocalizedTitle;
   private Component objective;
   private final String unlocalizedObjective;
   private final String category;
   private int timeLimit;
   private ResourceLocation rewards;
   private ChallengeDifficulty difficulty;
   private int difficultyStars;
   private float rewardsFactor;
   private HashMultimap<ArenaStyle, ArenaEntry> arenas = HashMultimap.create();
   private ITargetShowcase[] targetShowcase;
   private boolean isTargetCacheDirty = false;
   private LivingEntity[] targetsCache = new LivingEntity[4];
   private String[] bannedFactions;
   private final IFactory<T> factory;
   private ICanStart startCheck;
   private IEnemySpawns enemySpawns;
   private @Nullable String titleI18nId;
   private @Nullable String objectiveI18nId;
   private int order;

   public ChallengeCore(String id, String unlocalizedTitle, String unlocalizedObjective, String category, IFactory<T> factory) {
      this.id = id;
      this.unlocalizedTitle = unlocalizedTitle;
      this.unlocalizedObjective = unlocalizedObjective;
      this.category = category;
      this.factory = factory;
   }

   public @Nullable T createChallenge() {
      return this.factory.create(this);
   }

   private void setTargetShowcase(ITargetShowcase... targets) {
      this.targetShowcase = targets;
      this.isTargetCacheDirty = true;
   }

   private void setRewardFactor(float factor) {
      this.rewardsFactor = factor;
   }

   private void setRewards(ResourceLocation rewards) {
      this.rewards = rewards;
   }

   private void setArenas(HashMultimap<ArenaStyle, ArenaEntry> map) {
      this.arenas = map;
   }

   private void setDifficulty(ChallengeDifficulty difficulty) {
      this.difficulty = difficulty;
   }

   private void setDifficultyStars(int stars) {
      this.difficultyStars = stars;
   }

   private void setTimeLimit(int minutes) {
      this.timeLimit = minutes;
   }

   public void setBannedFactions(String... factions) {
      this.bannedFactions = factions;
   }

   public void setStartCheck(ICanStart startCheck) {
      this.startCheck = startCheck;
   }

   public void setEnemySpawns(IEnemySpawns spawns) {
      this.enemySpawns = spawns;
   }

   public String getId() {
      return this.id;
   }

   public float getRewardsFactor() {
      return this.rewardsFactor;
   }

   public ResourceLocation getRewards() {
      return this.rewards;
   }

   public Component getLocalizedTitle() {
      if (this.title == null) {
         this.title = Component.m_237115_(this.getUnlocalizedTitle());
      }

      return this.title;
   }

   public Component getLocalizedObjective() {
      if (this.objective == null) {
         this.objective = Component.m_237115_(this.getUnlocalizedObjective());
      }

      return this.objective;
   }

   private String getTitleLocalizationId() {
      if (this.titleI18nId == null) {
         ResourceLocation key = ((IForgeRegistry)WyRegistry.CHALLENGES.get()).getKey(this);
         if (key != null) {
            this.titleI18nId = Util.m_137492_("challenge", key);
         }
      }

      return this.titleI18nId;
   }

   private String getObjectiveLocalizationId() {
      if (this.titleI18nId == null) {
         ResourceLocation key = ((IForgeRegistry)WyRegistry.CHALLENGES.get()).getKey(this);
         if (key != null) {
            this.objectiveI18nId = Util.m_137492_("challenge", key) + ".objective";
         }
      }

      return this.objectiveI18nId;
   }

   public LivingEntity[] getTargetShowcase(Level world) {
      if (this.isTargetCacheDirty && this.targetShowcase != null) {
         int i = 0;

         for(ITargetShowcase showcase : this.targetShowcase) {
            this.targetsCache[i] = showcase.createShowcase(world);
            ++i;
         }

         this.isTargetCacheDirty = false;
      }

      return this.targetsCache;
   }

   public @Nullable ArenaEntry getArenaFromStyle(ArenaStyle style, String clzId) {
      for(ArenaEntry entry : this.arenas.get(style)) {
         if (entry.arena.getClass().getName().equals(clzId)) {
            return entry;
         }
      }

      return null;
   }

   public @Nullable ArenaEntry pickRandomArena() {
      ArenaStyle configStyle = ArenaStyle.SIMPLE;
      Iterator var2 = this.arenas.get(configStyle).iterator();
      if (var2.hasNext()) {
         ArenaEntry entry = (ArenaEntry)var2.next();
         return entry;
      } else {
         return null;
      }
   }

   private void setOrder(int order) {
      this.order = order;
   }

   public int getOrder() {
      return this.order;
   }

   public ChallengeDifficulty getDifficulty() {
      return this.difficulty;
   }

   public int getDifficultyStars() {
      return this.difficultyStars;
   }

   public String getUnlocalizedTitle() {
      return this.unlocalizedTitle;
   }

   public String getUnlocalizedObjective() {
      return this.unlocalizedObjective;
   }

   public IFactory<? extends T> getFactory() {
      return this.factory;
   }

   public Component getObjective() {
      return this.objective;
   }

   public String getCategory() {
      return this.category;
   }

   public int getTimeLimit() {
      return this.timeLimit;
   }

   public String[] getBannedFactions() {
      return this.bannedFactions;
   }

   public ICanStart getStartCheck() {
      return this.startCheck;
   }

   public IEnemySpawns getEnemySpawns() {
      return this.enemySpawns;
   }

   public @Nullable ResourceLocation getRegistryKey() {
      if (this.key == null) {
         this.key = ((IForgeRegistry)WyRegistry.CHALLENGES.get()).getKey(this);
      }

      return this.key;
   }

   public static class Builder<T extends Challenge> {
      private String id;
      private String unlocalizedTitle;
      private String unlocalizedObjective;
      private String category = "No Category";
      private int timeLimit = 10;
      private ResourceLocation rewards;
      private ChallengeDifficulty difficulty;
      private int difficultyStars;
      private float rewardsFactor;
      private HashMultimap<ArenaStyle, ArenaEntry> arenas;
      private ITargetShowcase[] targetShowcase;
      private String[] bannedFactions;
      private IFactory<T> factory;
      private ICanStart startCheck;
      private IEnemySpawns enemySpawns;
      private int order;

      public Builder(String id, String title, String objective, String category, IFactory<T> factory) {
         this.difficulty = ChallengeDifficulty.STANDARD;
         this.difficultyStars = 1;
         this.rewardsFactor = 1.0F;
         this.arenas = HashMultimap.create();
         this.startCheck = (entity) -> true;
         this.order = Integer.MAX_VALUE;
         this.id = id;
         this.unlocalizedTitle = title;
         this.unlocalizedObjective = objective;
         this.category = category;
         this.factory = factory;
      }

      public Builder<T> setOrder(int order) {
         this.order = order;
         return this;
      }

      public Builder<T> setTimeLimit(int minutes) {
         this.timeLimit = minutes;
         return this;
      }

      public Builder<T> setRewardsFactor(float factor) {
         this.rewardsFactor = factor;
         return this;
      }

      public Builder<T> setRewards(ResourceLocation rewards) {
         this.rewards = rewards;
         return this;
      }

      public Builder<T> setBannedFactions(String... factions) {
         this.bannedFactions = factions;
         return this;
      }

      public Builder<T> setStartCheck(ICanStart startCheck) {
         this.startCheck = startCheck;
         return this;
      }

      public Builder<T> setDifficulty(ChallengeDifficulty difficulty) {
         this.difficulty = difficulty;
         return this;
      }

      public Builder<T> setDifficultyStars(int stars) {
         this.difficultyStars = Math.min(stars, 10);
         return this;
      }

      public Builder<T> addArena(ArenaStyle style, ChallengeArena arena, IChallengerPosition challengerPosition, IEnemyPosition enemyPosition) {
         ArenaEntry arenaEntry = new ArenaEntry(arena, challengerPosition, enemyPosition);
         this.arenas.put(style, arenaEntry);
         return this;
      }

      public Builder<T> setEnemySpawns(IEnemySpawns spawns) {
         this.enemySpawns = spawns;
         return this;
      }

      public Builder<T> setTargetShowcase(Supplier<? extends EntityType<?>>... targets) {
         this.targetShowcase = new ITargetShowcase[targets.length];

         for(int i = 0; i < targets.length; ++i) {
            this.targetShowcase[i] = (world) -> (LivingEntity)((EntityType)targets[i].get()).m_20615_(world);
         }

         return this;
      }

      public Builder<T> setTargetShowcase(ITargetShowcase... targets) {
         this.targetShowcase = targets;
         return this;
      }

      public ChallengeCore<T> build() {
         ChallengeCore<T> challenge = new ChallengeCore<T>(this.id, this.unlocalizedTitle, this.unlocalizedObjective, this.category, this.factory);
         challenge.setTimeLimit(this.timeLimit);
         challenge.setRewardFactor(this.rewardsFactor);
         challenge.setRewards(this.rewards);
         challenge.setBannedFactions(this.bannedFactions);
         challenge.setStartCheck(this.startCheck);
         challenge.setDifficulty(this.difficulty);
         int difficultyStars = this.difficultyStars;
         if (this.difficulty == ChallengeDifficulty.HARD) {
            difficultyStars += 10;
         } else if (this.difficulty == ChallengeDifficulty.ULTIMATE) {
            difficultyStars += 20;
         }

         challenge.setDifficultyStars(difficultyStars);
         challenge.setArenas(this.arenas);
         challenge.setTargetShowcase(this.targetShowcase);
         challenge.setEnemySpawns(this.enemySpawns);
         if (this.difficulty == ChallengeDifficulty.HARD) {
            ++this.order;
         } else if (this.difficulty == ChallengeDifficulty.ULTIMATE) {
            this.order += 2;
         }

         challenge.setOrder(this.order);
         return challenge;
      }
   }

   public static record ArenaEntry(ChallengeArena arena, IChallengerPosition challengerPosition, IEnemyPosition enemyPosition) {
   }

   @FunctionalInterface
   public interface ICanStart {
      boolean canStart(Player var1);
   }

   @FunctionalInterface
   public interface IChallengerPosition {
      ChallengeArena.SpawnPosition getChallengerSpawnPos(int var1, InProgressChallenge var2);
   }

   @FunctionalInterface
   public interface IEnemyPosition {
      ChallengeArena.SpawnPosition[] getEnemySpawnPos(InProgressChallenge var1);
   }

   @FunctionalInterface
   public interface IEnemySpawns {
      Set<ChallengeArena.EnemySpawn> getEnemySpawns(InProgressChallenge var1, ChallengeArena.SpawnPosition[] var2);
   }

   @FunctionalInterface
   public interface IFactory<A extends Challenge> {
      A create(ChallengeCore<A> var1);
   }

   @FunctionalInterface
   public interface ITargetShowcase {
      LivingEntity createShowcase(Level var1);
   }
}
