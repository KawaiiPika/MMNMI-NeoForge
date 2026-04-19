package xyz.pixelatedw.mineminenomi.world;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.mojang.serialization.Lifecycle;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.function.BiFunction;
import net.minecraft.client.renderer.DimensionSpecialEffects;
import net.minecraft.client.renderer.DimensionSpecialEffects.SkyType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.LayeredRegistryAccess;
import net.minecraft.core.MappedRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.RegistryLayer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.progress.ChunkProgressListener;
import net.minecraft.world.RandomSequences;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.BiomeManager;
import net.minecraft.world.level.border.BorderChangeListener;
import net.minecraft.world.level.border.WorldBorder;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.WorldOptions;
import net.minecraft.world.level.storage.DerivedLevelData;
import net.minecraft.world.level.storage.LevelStorageSource;
import net.minecraft.world.level.storage.WorldData;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.level.LevelEvent;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.packets.server.SSyncDynDimensionsPacket;

public class DynamicDimensionManager {
   private static final Set<ResourceKey<Level>> VANILLA_WORLDS;
   private static Set<ResourceKey<Level>> pendingWorldsToUnregister;

   public static ServerLevel getOrCreateWorld(MinecraftServer server, ResourceKey<Level> worldKey, BiFunction<MinecraftServer, ResourceKey<LevelStem>, LevelStem> dimensionFactory) {
      Map<ResourceKey<Level>, ServerLevel> map = server.forgeGetWorldMap();
      ServerLevel existingWorld = (ServerLevel)map.get(worldKey);
      return existingWorld != null ? existingWorld : createAndRegisterWorldAndDimension(server, map, worldKey, dimensionFactory);
   }

   public static void markDimensionForUnregistration(MinecraftServer server, ResourceKey<Level> WorldToRemove) {
      if (!VANILLA_WORLDS.contains(WorldToRemove)) {
         pendingWorldsToUnregister.add(WorldToRemove);
      }

   }

   public static Set<ResourceKey<Level>> getWorldsPendingUnregistration() {
      return Collections.unmodifiableSet(pendingWorldsToUnregister);
   }

   /** @deprecated */
   @Deprecated
   public static void unregisterScheduledDimensions(MinecraftServer server) {
      Set<ResourceKey<Level>> keysToRemove = pendingWorldsToUnregister;
      pendingWorldsToUnregister = new HashSet();
      WorldOptions worldGenSettings = server.m_129910_().m_246337_();
      Set<ResourceKey<Level>> removedWorldKeys = new HashSet();
      ServerLevel overworld = server.m_129880_(Level.f_46428_);

      for(ResourceKey<Level> keyToRemove : keysToRemove) {
         ServerLevel removedWorld = (ServerLevel)server.forgeGetWorldMap().remove(keyToRemove);
         if (removedWorld != null) {
            for(ServerPlayer player : Lists.newArrayList(removedWorld.m_6907_())) {
               ResourceKey<Level> respawnKey = player.m_8963_();
               if (keysToRemove.contains(respawnKey)) {
                  respawnKey = Level.f_46428_;
                  player.m_9158_(Level.f_46428_, (BlockPos)null, 0.0F, false, false);
               }

               if (respawnKey == null) {
                  respawnKey = Level.f_46428_;
               }

               ServerLevel destinationWorld = server.m_129880_(respawnKey);
               BlockPos destinationPos = player.m_8961_();
               if (destinationPos == null) {
                  destinationPos = destinationWorld.m_220360_();
               }

               float respawnAngle = player.m_8962_();
               player.m_8999_(destinationWorld, (double)destinationPos.m_123341_(), (double)destinationPos.m_123342_(), (double)destinationPos.m_123343_(), respawnAngle, 0.0F);
            }

            MinecraftForge.EVENT_BUS.post(new LevelEvent.Unload(removedWorld));
            WorldBorder overworldBorder = overworld.m_6857_();
            WorldBorder removedWorldBorder = removedWorld.m_6857_();
            List<BorderChangeListener> listeners = overworldBorder.f_61905_;
            BorderChangeListener targetListener = null;

            for(BorderChangeListener listener : listeners) {
               if (listener instanceof BorderChangeListener.DelegateBorderChangeListener && removedWorldBorder == ((BorderChangeListener.DelegateBorderChangeListener)listener).f_61864_) {
                  targetListener = listener;
                  break;
               }
            }

            if (targetListener != null) {
               overworldBorder.m_156096_(targetListener);
            }

            removedWorldKeys.add(keyToRemove);
         }
      }

      if (!removedWorldKeys.isEmpty()) {
         LayeredRegistryAccess<RegistryLayer> registries = server.m_247573_();
         RegistryAccess.ImmutableRegistryAccess composite = (RegistryAccess.ImmutableRegistryAccess)registries.m_247579_();
         Map<? extends ResourceKey<? extends Registry<?>>, ? extends Registry<?>> hashMap = new HashMap();
         ResourceKey<?> key = ResourceKey.m_135785_(ResourceKey.m_135788_(ResourceLocation.parse("root")), ResourceLocation.parse("dimension"));
         Registry<LevelStem> oldRegistry = (Registry)hashMap.get(key);
         MappedRegistry<LevelStem> newRegistry = new MappedRegistry(Registries.f_256862_, oldRegistry.m_203658_(), false);

         for(Map.Entry<ResourceKey<LevelStem>, LevelStem> entry : oldRegistry.m_6579_()) {
            ResourceKey<LevelStem> oldKey = (ResourceKey)entry.getKey();
            ResourceKey<Level> oldWorldKey = ResourceKey.m_135785_(Registries.f_256858_, oldKey.m_135782_());
            LevelStem dimension = (LevelStem)entry.getValue();
            if (dimension != null && !removedWorldKeys.contains(oldWorldKey)) {
               Registry.m_122965_(newRegistry, oldKey.m_135782_(), dimension);
            }
         }

         composite.f_206223_ = hashMap;
         server.markWorldsDirty();
         ModNetwork.sendToAll(new SSyncDynDimensionsPacket(ImmutableSet.of(), removedWorldKeys));
      }

   }

   private static ServerLevel createAndRegisterWorldAndDimension(MinecraftServer server, Map<ResourceKey<Level>, ServerLevel> map, ResourceKey<Level> worldKey, BiFunction<MinecraftServer, ResourceKey<LevelStem>, LevelStem> dimensionFactory) {
      ServerLevel overworld = server.m_129880_(Level.f_46428_);
      ResourceKey<LevelStem> dimensionKey = ResourceKey.m_135785_(Registries.f_256862_, worldKey.m_135782_());
      LevelStem dimension = (LevelStem)dimensionFactory.apply(server, dimensionKey);
      ChunkProgressListener chunkProgressListener = server.f_129756_.m_9620_(11);
      Executor executor = server.f_129738_;
      LevelStorageSource.LevelStorageAccess anvilConverter = server.f_129744_;
      WorldData worldData = server.m_129910_();
      WorldOptions worldGenSettings = worldData.m_246337_();
      DerivedLevelData derivedWorldData = new DerivedLevelData(worldData, worldData.m_5996_());
      LayeredRegistryAccess<RegistryLayer> registries = server.m_247573_();
      RegistryAccess.ImmutableRegistryAccess composite = (RegistryAccess.ImmutableRegistryAccess)registries.m_247579_();
      Map<ResourceKey<? extends Registry<?>>, Registry<?>> hashMap = new HashMap(composite.f_206223_);
      ResourceKey<? extends Registry<?>> key = ResourceKey.m_135785_(ResourceKey.m_135788_(ResourceLocation.parse("root")), ResourceLocation.parse("dimension"));
      MappedRegistry<LevelStem> oldRegistry = (MappedRegistry)hashMap.get(key);
      Lifecycle oldLifecycle = oldRegistry.m_203658_();
      MappedRegistry<LevelStem> newRegistry = new MappedRegistry(Registries.f_256862_, oldLifecycle, false);

      for(Map.Entry<ResourceKey<LevelStem>, LevelStem> entry : oldRegistry.m_6579_()) {
         ResourceKey<LevelStem> oldKey = (ResourceKey)entry.getKey();
         ResourceKey<Level> oldLevelKey = ResourceKey.m_135785_(Registries.f_256858_, oldKey.m_135782_());
         LevelStem dim = (LevelStem)entry.getValue();
         if (dim != null && oldLevelKey != worldKey) {
            Registry.m_194579_(newRegistry, oldKey, dim);
         }
      }

      Registry.m_194579_(newRegistry, dimensionKey, dimension);
      hashMap.replace(key, newRegistry);
      composite.f_206223_ = hashMap;
      ServerLevel newWorld = new ServerLevel(server, executor, anvilConverter, derivedWorldData, worldKey, dimension, chunkProgressListener, false, BiomeManager.m_47877_(worldGenSettings.m_245499_()), ImmutableList.of(), false, (RandomSequences)null);
      newWorld.f_8564_ = true;
      overworld.m_6857_().m_61929_(new BorderChangeListener.DelegateBorderChangeListener(newWorld.m_6857_()));
      map.put(worldKey, newWorld);
      server.markWorldsDirty();
      MinecraftForge.EVENT_BUS.post(new LevelEvent.Load(newWorld));
      ModNetwork.sendToAll(new SSyncDynDimensionsPacket(ImmutableSet.of(worldKey), ImmutableSet.of()));
      return newWorld;
   }

   static {
      VANILLA_WORLDS = ImmutableSet.of(Level.f_46428_, Level.f_46429_, Level.f_46430_);
      pendingWorldsToUnregister = new HashSet();
   }

   @OnlyIn(Dist.CLIENT)
   public static class ChallengeDimensionRenderInfo extends DimensionSpecialEffects {
      public ChallengeDimensionRenderInfo() {
         super(Float.NaN, false, SkyType.NORMAL, false, false);
      }

      public Vec3 m_5927_(Vec3 p_230494_1_, float p_230494_2_) {
         return p_230494_1_.m_82542_((double)(p_230494_2_ * 0.94F + 0.06F), (double)(p_230494_2_ * 0.94F + 0.06F), (double)(p_230494_2_ * 0.91F + 0.09F));
      }

      public boolean m_5781_(int p_230493_1_, int p_230493_2_) {
         return false;
      }
   }
}
