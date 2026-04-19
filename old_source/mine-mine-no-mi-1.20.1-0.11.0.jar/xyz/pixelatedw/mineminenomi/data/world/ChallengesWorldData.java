package xyz.pixelatedw.mineminenomi.data.world;

import com.google.common.collect.UnmodifiableIterator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraftforge.server.ServerLifecycleHooks;
import org.jetbrains.annotations.Nullable;
import xyz.pixelatedw.mineminenomi.api.WyDebug;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.challenges.Challenge;
import xyz.pixelatedw.mineminenomi.api.challenges.ChallengeCache;
import xyz.pixelatedw.mineminenomi.api.challenges.ChallengeCore;
import xyz.pixelatedw.mineminenomi.api.challenges.InProgressChallenge;
import xyz.pixelatedw.mineminenomi.config.ServerConfig;
import xyz.pixelatedw.mineminenomi.data.entity.challenge.ChallengeCapability;
import xyz.pixelatedw.mineminenomi.data.entity.challenge.IChallengeData;
import xyz.pixelatedw.mineminenomi.init.ModResources;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18nChallenges;
import xyz.pixelatedw.mineminenomi.world.ChallengesChunkGenerator;
import xyz.pixelatedw.mineminenomi.world.DynamicDimensionManager;

public class ChallengesWorldData extends SavedData {
   private static final String IDENTIFIER = "mineminenomi-challenges";
   private static final ChallengesWorldData INSTANCE = initWorldData();
   private Map<UUID, InProgressChallenge> inProgressChallenges = new HashMap();
   private Map<Integer, InProgressChallenge> inProgressChallengesHashCache = new HashMap();
   private Map<UUID, ChallengeCache> challengerCache = new HashMap();

   private static ChallengesWorldData initWorldData() {
      return (ChallengesWorldData)ServerLifecycleHooks.getCurrentServer().m_129783_().m_8895_().m_164861_(ChallengesWorldData::load, ChallengesWorldData::new, "mineminenomi-challenges");
   }

   public static ChallengesWorldData get() {
      return INSTANCE;
   }

   public static ChallengesWorldData load(CompoundTag nbt) {
      ChallengesWorldData data = new ChallengesWorldData();
      data.challengerCache.clear();
      ListTag cacheTag = nbt.m_128437_("cache", 10);

      for(int i = 0; i < cacheTag.size(); ++i) {
         CompoundTag entryNbt = cacheTag.m_128728_(i);
         UUID id = entryNbt.m_128342_("id");
         ChallengeCache cache = ChallengeCache.from(entryNbt.m_128469_("data"));
         data.challengerCache.put(id, cache);
      }

      return data;
   }

   public CompoundTag m_7176_(CompoundTag nbt) {
      ListTag cache = new ListTag();

      for(Map.Entry<UUID, ChallengeCache> entry : this.challengerCache.entrySet()) {
         CompoundTag entryNbt = new CompoundTag();
         entryNbt.m_128362_("id", (UUID)entry.getKey());
         entryNbt.m_128365_("data", ((ChallengeCache)entry.getValue()).save());
         cache.add(entryNbt);
      }

      nbt.m_128365_("cache", cache);
      return nbt;
   }

   public boolean startChallenge(ServerPlayer player, @Nullable List<LivingEntity> group, ChallengeCore<?> core, boolean isFree) {
      if (!ServerConfig.isChallengesEnabled()) {
         return false;
      } else {
         IChallengeData props = (IChallengeData)ChallengeCapability.get(player).orElse((Object)null);
         if (props == null) {
            return false;
         } else {
            Challenge challenge = props.getChallenge(core);
            if (challenge == null) {
               WyHelper.sendMessage(player, ModI18nChallenges.MESSAGE_NOT_UNLOCKED);
               return false;
            } else {
               String var10000 = player.m_36316_().getName();
               WyDebug.info(var10000 + " started " + challenge.getCore().getId().toUpperCase() + " challenge.");
               long start = System.nanoTime();
               ResourceLocation dimName = ResourceLocation.fromNamespaceAndPath("mineminenomi", "challenges_" + player.m_20149_());
               ResourceKey<Level> dimension = ResourceKey.m_135785_(Registries.f_256858_, dimName);
               RegistryAccess registryAccess = player.m_9236_().m_9598_();
               Holder<DimensionType> type = registryAccess.m_175515_(Registries.f_256787_).m_246971_(ResourceKey.m_135785_(Registries.f_256787_, ModResources.DIMENSION_TYPE_CHALLENGES));
               ServerLevel shard = DynamicDimensionManager.getOrCreateWorld(player.m_20194_(), dimension, (minecraftServer, levelStemResourceKey) -> {
                  Holder.Reference<Biome> biomeReference = ((HolderLookup.RegistryLookup)registryAccess.m_254861_(Registries.f_256952_).get()).m_255043_(Biomes.f_48173_);
                  ChunkGenerator generator = new ChallengesChunkGenerator(biomeReference);
                  return new LevelStem(type, generator);
               });
               UUID id = player.m_20148_();
               InProgressChallenge inProgressChallenge = new InProgressChallenge(id, player, shard, group, challenge, isFree);
               if (this.inProgressChallenges.containsKey(id)) {
                  this.stopChallenge(inProgressChallenge);
               }

               this.inProgressChallenges.put(id, inProgressChallenge);

               for(Map.Entry<UUID, ChallengeCache> entry : inProgressChallenge.getGroupCache().entrySet()) {
                  this.challengerCache.put((UUID)entry.getKey(), (ChallengeCache)entry.getValue());
               }

               this.m_77762_();
               long end = System.nanoTime();
               long elapsed = (end - start) / 1000000L;
               WyDebug.debug("Dimension setup: " + elapsed + "ms");
               return true;
            }
         }
      }
   }

   public void stopChallenge(InProgressChallenge inProgChallenge) {
      IChallengeData challengeProps = (IChallengeData)ChallengeCapability.get(inProgChallenge.getOwner()).orElse((Object)null);
      if (challengeProps != null) {
         UnmodifiableIterator var3 = challengeProps.getGroupMembersIds().iterator();

         while(var3.hasNext()) {
            UUID uuid = (UUID)var3.next();
            Player groupMember = inProgChallenge.getShard().m_7654_().m_6846_().m_11259_(uuid);
            if (groupMember != null) {
               ChallengeCapability.get(groupMember).ifPresent((props) -> props.setInGroup((UUID)null));
               challengeProps.removeGroupMember(groupMember.m_20148_());
            }
         }

         challengeProps.setInGroup((UUID)null);
         challengeProps.removeGroupMember(inProgChallenge.getOwner().m_20148_());
      }

      WyDebug.info(inProgChallenge.getOwner().m_36316_().getName() + "'s challenge stopped.");

      for(UUID key : inProgChallenge.getGroupCache().keySet()) {
         this.challengerCache.remove(key);
      }

      this.inProgressChallenges.remove(inProgChallenge.getId());
      this.inProgressChallengesHashCache.remove(inProgChallenge.getShard().hashCode());
      this.m_77762_();
   }

   public @Nullable InProgressChallenge getInProgressChallengeFor(LivingEntity entity) {
      UUID id = entity.m_20148_();
      return (InProgressChallenge)this.inProgressChallenges.get(id);
   }

   public @Nullable InProgressChallenge getInProgressChallengeFor(ServerLevel world) {
      if (this.inProgressChallengesHashCache.containsKey(world.hashCode())) {
         return (InProgressChallenge)this.inProgressChallengesHashCache.get(world.hashCode());
      } else {
         for(InProgressChallenge challenge : this.inProgressChallenges.values()) {
            if (challenge.getShard().equals(world)) {
               this.inProgressChallengesHashCache.put(world.hashCode(), challenge);
               return challenge;
            }
         }

         return null;
      }
   }

   public Optional<ChallengeCache> getChallengerCache(UUID key) {
      return Optional.ofNullable((ChallengeCache)this.challengerCache.get(key));
   }

   public void removeChallengerCache(UUID key) {
      this.challengerCache.remove(key);
      this.m_77762_();
   }

   public void tick(ServerLevel world) {
      InProgressChallenge challenge = this.getInProgressChallengeFor(world);
      if (challenge != null) {
         if (challenge.canDelete()) {
            this.stopChallenge(challenge);
         } else {
            challenge.tick();
         }
      }
   }
}
