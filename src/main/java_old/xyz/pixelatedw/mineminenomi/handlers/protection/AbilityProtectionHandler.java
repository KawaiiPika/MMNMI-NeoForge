package xyz.pixelatedw.mineminenomi.handlers.protection;

import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import xyz.pixelatedw.mineminenomi.api.helpers.RendererHelper;
import xyz.pixelatedw.mineminenomi.api.protection.ProtectedArea;
import xyz.pixelatedw.mineminenomi.data.world.ProtectedAreasData;
import xyz.pixelatedw.mineminenomi.init.ModEffects;

public class AbilityProtectionHandler {
   public static final Map<String, ProtectedArea> CLIENT_AREAS = new HashMap();

   public static boolean shouldMobSpawnInProtectionArea(Mob target) {
      MobSpawnType spawnType = target.getSpawnType();
      if (spawnType != MobSpawnType.COMMAND && spawnType != MobSpawnType.SPAWN_EGG) {
         ProtectedAreasData worldData = ProtectedAreasData.get((ServerLevel)target.m_9236_());
         if (worldData != null) {
            Optional<ProtectedArea> area = worldData.getProtectedArea((int)target.m_20185_(), (int)target.m_20186_(), (int)target.m_20189_());
            if (area.isPresent()) {
               return ((ProtectedArea)area.get()).canMobsSpawn();
            }
         }

         return true;
      } else {
         return true;
      }
   }

   public static boolean shouldHurtEntityInProtectionArea(LivingEntity target, DamageSource source) {
      ProtectedAreasData worldData = ProtectedAreasData.get((ServerLevel)target.m_9236_());
      if (worldData != null) {
         Optional<ProtectedArea> area = worldData.getProtectedArea((int)target.m_20185_(), (int)target.m_20186_(), (int)target.m_20189_());
         if (area.isPresent()) {
            if (target instanceof Player) {
               return ((ProtectedArea)area.get()).canHurtPlayers();
            }

            return ((ProtectedArea)area.get()).canHurtEntities();
         }
      }

      return true;
   }

   public static boolean shouldLoseStatsInProtectionArea(LivingEntity living) {
      Level var2 = living.m_9236_();
      if (var2 instanceof ServerLevel serverLevel) {
         ProtectedAreasData worldData = ProtectedAreasData.get(serverLevel);
         BlockPos pos = living.m_20183_();
         Optional<ProtectedArea> area = worldData.getProtectedArea(pos.m_123341_(), pos.m_123342_(), pos.m_123343_());
         if (area.isPresent() && !((ProtectedArea)area.get()).canLoseStats()) {
            return false;
         }
      }

      return true;
   }

   public static boolean shouldDieInProtectedArea(LivingEntity living) {
      Level var2 = living.m_9236_();
      if (var2 instanceof ServerLevel serverLevel) {
         ProtectedAreasData worldData = ProtectedAreasData.get(serverLevel);
         if (worldData != null) {
            Optional<ProtectedArea> area = worldData.getProtectedArea(living.m_20183_().m_123341_(), living.m_20183_().m_123342_(), living.m_20183_().m_123343_());
            if (area.isPresent() && !((ProtectedArea)area.get()).canDie()) {
               living.m_7292_(new MobEffectInstance((MobEffect)ModEffects.UNCONSCIOUS.get(), ((ProtectedArea)area.get()).getUnconsciousTime(), 0, false, false));
               living.m_21153_(5.0F);
               return false;
            }
         }
      }

      return true;
   }

   @OnlyIn(Dist.CLIENT)
   public static void renderAbilityProtections(PoseStack stack, Camera info) {
      Minecraft mc = Minecraft.m_91087_();
      Player player = mc.f_91074_;
      if (player != null) {
         BufferBuilder buffer = Tesselator.m_85913_().m_85915_();

         for(ProtectedArea area : CLIENT_AREAS.values()) {
            RendererHelper.renderAbilityProtectionArea(stack, info, buffer, (double)area.getCenter().m_123341_(), (double)area.getCenter().m_123342_(), (double)area.getCenter().m_123343_(), (float)area.getSize(), (float)area.getSize(), (float)area.getSize());
         }

      }
   }

   public static final void handleExplosions(ServerLevel level, Explosion explosion, List<BlockPos> affectedBlocks, List<Entity> affectedEntities) {
      ProtectedAreasData worldData = ProtectedAreasData.get(level);
      Vec3 explosionPos = explosion.getPosition();
      Optional<ProtectedArea> area = worldData.getProtectedArea((int)explosionPos.f_82479_, (int)explosionPos.f_82480_, (int)explosionPos.f_82481_);
      if (area.isPresent()) {
         if (!((ProtectedArea)area.get()).canDestroyBlocks()) {
            affectedBlocks.clear();
         } else if (((ProtectedArea)area.get()).canRestoreBlocks()) {
            Map<BlockPos, ProtectedArea.RestorationEntry> map = (Map)affectedBlocks.stream().filter((pos) -> !level.m_8055_(pos).m_60795_() && ((ProtectedArea)area.get()).isInside(level, pos.m_123341_(), pos.m_123342_(), pos.m_123343_())).distinct().collect(Collectors.toMap((pos) -> pos, (pos) -> new ProtectedArea.RestorationEntry(level, pos)));
            ((ProtectedArea)area.get()).queueForRestoration(map);
         }

         if (!((ProtectedArea)area.get()).canHurtEntities()) {
            affectedEntities.removeIf((target) -> !(target instanceof Player));
         }

         if (!((ProtectedArea)area.get()).canHurtPlayers()) {
            affectedEntities.removeIf((target) -> target instanceof Player);
         }
      }

   }
}
