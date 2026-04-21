package xyz.pixelatedw.mineminenomi.api;

import com.google.common.base.Predicates;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import java.awt.Color;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction8;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundLevelParticlesPacket;
import net.minecraft.network.protocol.game.ClientboundRemoveMobEffectPacket;
import net.minecraft.network.protocol.game.ClientboundUpdateMobEffectPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.SpawnPlacements.Type;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.NaturalSpawner;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.level.block.AirBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraftforge.versions.mcp.MCPVersion;
import org.jetbrains.annotations.Nullable;
import xyz.pixelatedw.mineminenomi.data.entity.combat.CombatCapability;
import xyz.pixelatedw.mineminenomi.data.entity.combat.ICombatData;
import xyz.pixelatedw.mineminenomi.init.BuildMode;
import xyz.pixelatedw.mineminenomi.init.ModEntityPredicates;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18n;
import xyz.pixelatedw.mineminenomi.packets.server.SSpawnParticleEffectPacket;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class WyHelper {
   private static final Random RANDOM = new Random();
   private static final Collector<?, ?, ?> SHUFFLER = Collectors.collectingAndThen(Collectors.toCollection(ArrayList::new), (list) -> {
      Collections.shuffle(list);
      return list;
   });

   public static void sendMessage(LivingEntity entity, Component message) {
      sendMessage(entity, message, false);
   }

   public static void sendMessage(LivingEntity entity, Component message, boolean isChat) {
      if (entity instanceof ServerPlayer player) {
         player.m_5661_(message, !isChat);
      }

   }

   public static String formatBytes(long bytes) {
      int unit = 1024;
      if (bytes < (long)unit) {
         return bytes + " B";
      } else {
         int exp = (int)(Math.log((double)bytes) / Math.log((double)unit));
         String pre = "" + "KMGTPE".charAt(exp - 1);
         return String.format("%.1f %sB", (double)bytes / Math.pow((double)unit, (double)exp), pre);
      }
   }

   public static String capitalize(String text) {
      char var10000 = Character.toUpperCase(text.charAt(0));
      return var10000 + text.substring(1).toLowerCase();
   }

   public static String getResourceName(String text) {
      return text.replaceAll("[ \\t]+$", "").replaceAll("\\(", "").replaceAll("\\)", "").replaceAll("\\s+", "_").replaceAll("[\\'\\:\\-\\,\\#]", "").replaceAll("\\&", "and").toLowerCase();
   }

   public static String escapeJSON(String raw) {
      String escaped = raw.replace("\\", "\\\\");
      escaped = escaped.replace("\"", "\\\"");
      escaped = escaped.replace("\b", "\\b");
      escaped = escaped.replace("\f", "\\f");
      escaped = escaped.replace("\n", "\\n");
      escaped = escaped.replace("\r", "\\r");
      escaped = escaped.replace("\t", "\\t");
      return escaped;
   }

   public static String formatTimeMMSS(long time) {
      return String.format("%02d:%02d", time / 60L, time % 60L);
   }

   public static Component getDirectionLocalizedName(Direction8 dir) {
      Object var10000;
      switch (dir) {
         case NORTH -> var10000 = ModI18n.GUI_NORTH;
         case EAST -> var10000 = ModI18n.GUI_EAST;
         case SOUTH -> var10000 = ModI18n.GUI_SOUTH;
         case WEST -> var10000 = ModI18n.GUI_WEST;
         case NORTH_EAST -> var10000 = ModI18n.GUI_NORTH_EAST;
         case NORTH_WEST -> var10000 = ModI18n.GUI_NORTH_WEST;
         case SOUTH_EAST -> var10000 = ModI18n.GUI_SOUTH_EAST;
         case SOUTH_WEST -> var10000 = ModI18n.GUI_SOUTH_WEST;
         default -> var10000 = Component.m_237119_();
      }

      return (Component)var10000;
   }

   public static Color hslToColor(float h, float s, float l) {
      if (!(s < 0.0F) && !(s > 100.0F)) {
         if (!(l < 0.0F) && !(l > 100.0F)) {
            h %= 360.0F;
            h /= 360.0F;
            s /= 100.0F;
            l /= 100.0F;
            float q = 0.0F;
            if ((double)l < (double)0.5F) {
               q = l * (1.0F + s);
            } else {
               q = l + s - s * l;
            }

            float p = 2.0F * l - q;
            float r = Math.max(0.0F, hueToRGB(p, q, h + 0.33333334F));
            float g = Math.max(0.0F, hueToRGB(p, q, h));
            float b = Math.max(0.0F, hueToRGB(p, q, h - 0.33333334F));
            r = Math.min(r, 1.0F);
            g = Math.min(g, 1.0F);
            b = Math.min(b, 1.0F);
            return new Color(r, g, b);
         } else {
            String message = "Color parameter outside of expected range - Luminance";
            throw new IllegalArgumentException(message);
         }
      } else {
         String message = "Color parameter outside of expected range - Saturation";
         throw new IllegalArgumentException(message);
      }
   }

   private static float hueToRGB(float p, float q, float h) {
      if (h < 0.0F) {
         ++h;
      }

      if (h > 1.0F) {
         --h;
      }

      if (6.0F * h < 1.0F) {
         return p + (q - p) * 6.0F * h;
      } else if (2.0F * h < 1.0F) {
         return q;
      } else {
         return 3.0F * h < 2.0F ? p + (q - p) * 6.0F * (0.6666667F - h) : p;
      }
   }

   public static Color intToRGB(int rgb, int alpha) {
      int val = -16777216 | rgb;
      int r = val >> 16 & 255;
      int g = val >> 8 & 255;
      int b = val >> 0 & 255;
      return new Color(r, g, b, alpha);
   }

   public static int rgbToInt(int red, int green, int blue, int alpha) {
      alpha = alpha << 32 & -16777216;
      red = red << 16 & 16711680;
      green = green << 8 & '\uff00';
      blue &= 255;
      return alpha | red | green | blue;
   }

   public static String rgbToHex(int red, int green, int blue) {
      return String.format("#%02X%02X%02X", red, green, blue);
   }

   public static Color hexToRGB(String hexColor) {
      if (Strings.isNullOrEmpty(hexColor)) {
         return Color.WHITE;
      } else {
         if (hexColor.startsWith("#")) {
            hexColor = hexColor.substring(1);
         }

         if (hexColor.length() == 8) {
            int r = Integer.parseInt(hexColor.substring(0, 2), 16);
            int g = Integer.parseInt(hexColor.substring(2, 4), 16);
            int b = Integer.parseInt(hexColor.substring(4, 6), 16);
            int a = Integer.parseInt(hexColor.substring(6, 8), 16);
            return new Color(r, g, b, a);
         } else {
            return Color.decode("#" + hexColor);
         }
      }
   }

   public static Color getComplementaryColor(Color color) {
      return new Color(255 - color.getRed(), 255 - color.getGreen(), 255 - color.getBlue());
   }

   public static float colorTolerance(float tolerance) {
      return colorTolerance(tolerance, false);
   }

   public static float colorTolerance(float tolerance, boolean hasDisturbance) {
      float color = RANDOM.nextFloat();
      return !(color <= tolerance) && (hasDisturbance || !((double)color >= (double)tolerance + 0.3)) ? color : tolerance;
   }

   public static Direction8 getLookDirection(LivingEntity entity) {
      float angle = (float)(Mth.m_14107_((double)(-entity.m_146908_()) / (double)45.0F + (double)0.5F) & 7);
      return Direction8.values()[(int)Mth.m_14154_(angle % (float)Direction8.values().length)];
   }

   public static boolean isInCombat(LivingEntity entity) {
      if (NuWorld.isChallengeDimension(entity.m_9236_())) {
         return false;
      } else {
         ICombatData props = (ICombatData)CombatCapability.get(entity).orElse((Object)null);
         if (props == null) {
            return false;
         } else {
            LivingEntity lastAttacker = entity.m_21188_();
            LivingEntity lastAttackerCache = props.getLastAttacker();
            if (lastAttacker == null && lastAttackerCache != null) {
               lastAttacker = lastAttackerCache;
            }

            if (lastAttacker != null && lastAttacker.m_6084_() && Math.abs(lastAttacker.m_20280_(entity)) <= (double)10000.0F) {
               return true;
            } else {
               return props.isInCombatCache();
            }
         }
      }
   }

   public static void sendApplyEffectToAllNearby(LivingEntity entity, Vec3 pos, int distance, MobEffectInstance effect) {
      entity.m_20194_().m_6846_().m_11241_((Player)null, pos.f_82479_, pos.f_82480_, pos.f_82481_, (double)distance, entity.m_20193_().m_46472_(), new ClientboundUpdateMobEffectPacket(entity.m_19879_(), effect));
   }

   public static void sendRemoveEffectToAllNearby(LivingEntity entity, Vec3 pos, int distance, MobEffect effect) {
      entity.m_20194_().m_6846_().m_11241_((Player)null, pos.f_82479_, pos.f_82480_, pos.f_82481_, (double)distance, entity.m_20193_().m_46472_(), new ClientboundRemoveMobEffectPacket(entity.m_19879_(), effect));
   }

   public static void spawnParticles(ParticleOptions data, ServerLevel world, double posX, double posY, double posZ) {
      spawnParticles(data, world, posX, posY, posZ, 0.0F, 0.0F, 0.0F);
   }

   public static void spawnParticles(ParticleOptions data, ServerLevel world, double posX, double posY, double posZ, float offsetX, float offsetY, float offsetZ) {
      spawnParticles(data, world, posX, posY, posZ, offsetX, offsetY, offsetZ, 1);
   }

   public static void spawnParticles(ParticleOptions data, ServerLevel world, double posX, double posY, double posZ, float offsetX, float offsetY, float offsetZ, int amount) {
      Packet<?> ipacket = new ClientboundLevelParticlesPacket(data, true, (double)((float)posX), (double)((float)posY), (double)((float)posZ), offsetX, offsetY, offsetZ, 0.0F, amount);

      for(int j = 0; j < world.m_6907_().size(); ++j) {
         ServerPlayer player = (ServerPlayer)world.m_6907_().get(j);
         BlockPos blockpos = player.m_20183_();
         if (blockpos.m_203195_(player.m_20182_(), (double)512.0F)) {
            player.f_8906_.m_9829_(ipacket);
         }
      }

   }

   public static void spawnParticleEffect(ParticleEffect<?> effect, Entity spawner, double posX, double posY, double posZ) {
      spawnParticleEffect(effect, spawner, posX, posY, posZ, (ParticleEffect.Details)null);
   }

   public static void spawnParticleEffect(ParticleEffect<?> effect, Entity spawner, double posX, double posY, double posZ, ParticleEffect.@Nullable Details details) {
      if (spawner != null) {
         if (!spawner.m_9236_().f_46443_) {
            ModNetwork.sendToAllAroundDistance(new SSpawnParticleEffectPacket(effect, spawner, posX, posY, posZ, details), spawner.m_9236_(), spawner.m_20182_(), 256);
         }
      }
   }

   public static void spawnParticleEffectForOwner(ParticleEffect<?> effect, Player spawner, double posX, double posY, double posZ, ParticleEffect.@Nullable Details details) {
      if (spawner != null && !spawner.m_9236_().f_46443_) {
         ModNetwork.sendTo(new SSpawnParticleEffectPacket(effect, spawner, posX, posY, posZ, details), spawner);
      }
   }

   /** @deprecated */
   @Deprecated
   public static <T extends Entity> List<T> getNearbyEntities(Vec3 pos, LevelAccessor world, double radius, @Nullable Predicate<Entity> predicate, Class<? extends T>... clazzez) {
      return getNearbyEntities(pos, world, radius, radius, radius, predicate, clazzez);
   }

   /** @deprecated */
   @Deprecated
   public static <T extends Entity> List<T> getNearbyEntities(Vec3 pos, LevelAccessor world, double sizeX, double sizeY, double sizeZ, @Nullable Predicate<Entity> predicate, Class<? extends T>... clazzez) {
      AABB aabb = (new AABB(pos, pos.m_82520_((double)1.0F, (double)1.0F, (double)1.0F))).m_82377_(sizeX, sizeY, sizeZ);
      return getNearbyEntities(world, aabb, predicate, clazzez);
   }

   /** @deprecated */
   @Deprecated
   @SafeVarargs
   public static <T extends Entity> List<T> getNearbyEntities(LevelAccessor world, AABB aabb, @Nullable Predicate<Entity> predicate, Class<? extends T>... clazzez) {
      if (clazzez.length <= 0) {
         clazzez = new Class[]{Entity.class};
      }

      if (predicate == null) {
         predicate = Predicates.alwaysTrue();
      }

      Predicate<Entity> var9 = ModEntityPredicates.IS_ALIVE_AND_SURVIVAL.and(predicate);
      List<T> list = new ArrayList();

      for(Class<? extends T> clz : clazzez) {
         list.addAll(world.m_6443_(clz, aabb, var9));
      }

      return list;
   }

   /** @deprecated */
   @Deprecated
   public static <T extends LivingEntity> List<T> getNearbyLiving(Vec3 pos, LevelAccessor world, double sizeX, double sizeY, double sizeZ, @Nullable Predicate<Entity> predicate) {
      return getNearbyEntities(pos, world, sizeX, sizeY, sizeZ, predicate, LivingEntity.class);
   }

   /** @deprecated */
   @Deprecated
   public static <T extends LivingEntity> List<T> getNearbyLiving(Vec3 pos, LevelAccessor world, double radius, @Nullable Predicate<Entity> predicate) {
      return getNearbyEntities(pos, world, radius, predicate, LivingEntity.class);
   }

   public static HitResult rayTraceBlocksAndEntities(Entity entity) {
      return rayTraceBlocksAndEntities(entity, (double)1024.0F, 0.4F);
   }

   public static HitResult rayTraceBlocksAndEntities(Entity entity, double distance) {
      return rayTraceBlocksAndEntities(entity, distance, 0.2F);
   }

   public static HitResult rayTraceBlocksAndEntities(Entity entity, double distance, float entityBoxRange) {
      Vec3 lookVec = entity.m_20154_();
      Vec3 startVec = entity.m_20182_().m_82520_((double)0.0F, (double)entity.m_20192_(), (double)0.0F);
      Vec3 endVec = startVec.m_82549_(entity.m_20154_().m_82490_(distance));
      HitResult blockResult = entity.m_9236_().m_45547_(new ClipContext(startVec, endVec, net.minecraft.world.level.ClipContext.Block.COLLIDER, Fluid.NONE, entity));
      HitResult entityResult = null;

      for(int i = 0; (double)i < distance * (double)2.0F && entityResult == null; ++i) {
         float scale = (float)i / 2.0F;
         Vec3 pos = startVec.m_82549_(lookVec.m_82490_((double)scale));
         Vec3 min = pos.m_82520_((double)entityBoxRange, (double)entityBoxRange, (double)entityBoxRange);
         Vec3 max = pos.m_82520_((double)(-entityBoxRange), (double)(-entityBoxRange), (double)(-entityBoxRange));
         List<Entity> list = entity.m_9236_().m_45933_(entity, new AABB(min.f_82479_, min.f_82480_, min.f_82481_, max.f_82479_, max.f_82480_, max.f_82481_));
         list.remove(entity);

         for(Entity e : list) {
            if (!(e instanceof LightningBolt)) {
               entityResult = new EntityHitResult(e, pos);
               break;
            }
         }
      }

      return entityResult != null && entityResult.m_82450_().m_82554_(startVec) <= blockResult.m_82450_().m_82554_(startVec) ? entityResult : blockResult;
   }

   public static BlockPos rayTraceBlockSafe(LivingEntity entity, float range) {
      Level world = entity.m_9236_();
      Vec3 startVec = entity.m_20182_().m_82520_((double)0.0F, (double)entity.m_20192_(), (double)0.0F);
      Vec3 endVec = startVec.m_82549_(entity.m_20154_().m_82490_((double)range));
      BlockHitResult result = world.m_45547_(new ClipContext(startVec, endVec, net.minecraft.world.level.ClipContext.Block.COLLIDER, Fluid.NONE, entity));
      BlockPos dashPos = result.m_82434_().equals(Direction.DOWN) ? result.m_82425_().m_6625_(2) : result.m_82425_().m_121955_(result.m_82434_().m_122436_());
      if (dashPos.m_123342_() > entity.m_9236_().m_151558_()) {
         dashPos = dashPos.m_7918_(0, entity.m_9236_().m_151558_() - dashPos.m_123342_(), 0);
      }

      return dashPos;
   }

   public static boolean isPosClearForPlayer(Level world, BlockPos pos) {
      return (world.m_46859_(pos) || world.m_8055_(pos).m_60812_(world, pos).m_83281_()) && (world.m_46859_(pos.m_7494_()) || world.m_8055_(pos.m_7494_()).m_60812_(world, pos.m_7494_()).m_83281_());
   }

   public static BlockPos getClearPositionForPlayer(LivingEntity entity, BlockPos pos) {
      boolean posIsFree = isPosClearForPlayer(entity.m_9236_(), pos);

      for(int i = 0; !posIsFree; ++i) {
         Direction dir = Direction.values()[i];
         pos = pos.m_7918_(-dir.m_122429_() * 2, -dir.m_122430_() * 2, -dir.m_122431_() * 2);
         posIsFree = isPosClearForPlayer(entity.m_9236_(), pos);
         if (posIsFree || i >= Direction.values().length - 1) {
            break;
         }
      }

      return posIsFree ? pos : null;
   }

   public static BlockHitResult rayTraceBlocks(Entity source, double distance) {
      Vec3 startVec = source.m_20182_().m_82520_((double)0.0F, (double)source.m_20192_(), (double)0.0F);
      Vec3 endVec = startVec.m_82549_(source.m_20154_().m_82490_(distance));
      return source.m_9236_().m_45547_(new ClipContext(startVec, endVec, net.minecraft.world.level.ClipContext.Block.COLLIDER, Fluid.NONE, source));
   }

   public static BlockHitResult rayTraceBlocks(Level world, Vec3 startVec, Vec3 endVec) {
      return world.m_45547_(new ClipContext(startVec, endVec, net.minecraft.world.level.ClipContext.Block.COLLIDER, Fluid.NONE, (Entity)null));
   }

   public static EntityHitResult rayTraceEntities(Entity source, double distance) {
      return rayTraceEntities(source, distance, (double)1.25F, (Predicate)null);
   }

   public static EntityHitResult rayTraceEntities(Entity source, double distance, double width, Predicate<Entity> check) {
      Vec3 startVec = source.m_20182_().m_82520_((double)0.0F, (double)source.m_20192_(), (double)0.0F);
      Vec3 endVec = startVec.m_82549_(source.m_20154_().m_82490_(distance));
      AABB boundingBox = source.m_20191_().m_82400_(distance);

      for(Entity entity : source.m_9236_().m_6249_(source, boundingBox, (entityx) -> entityx != source)) {
         AABB entityBB = entity.m_20191_().m_82400_((double)1.0F);
         Optional<Vec3> optional = entityBB.m_82371_(startVec, endVec);
         if (optional.isPresent()) {
            Vec3 targetVec = (Vec3)optional.get();
            double distFromSource = (double)Mth.m_14116_((float)startVec.m_82557_(targetVec));
            if (distFromSource < distance) {
               List<Entity> targets = getNearbyEntities(targetVec, source.m_9236_(), width, check);
               targets.remove(source);
               Optional<Entity> target = targets.stream().findFirst();
               if (target.isPresent()) {
                  return new EntityHitResult((Entity)target.get(), endVec);
               }
            }
         }
      }

      return new EntityHitResult((Entity)null, endVec);
   }

   public static boolean isBlockNearby(Entity entity, int radius, Predicate<BlockState> test) {
      BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();

      for(int x = -radius; x <= radius; ++x) {
         for(int y = -radius; y <= radius; ++y) {
            for(int z = -radius; z <= radius; ++z) {
               pos.m_122169_(entity.m_20185_() + (double)x, entity.m_20186_() + (double)y, entity.m_20189_() + (double)z);
               BlockState state = entity.m_9236_().m_8055_(pos);
               if (test.test(state)) {
                  return true;
               }
            }
         }
      }

      return false;
   }

   public static boolean isBlockNearby(Entity entity, int radius, Block... blocks) {
      List<Block> list = Arrays.asList(blocks);
      return isBlockNearby(entity, radius, (Predicate)((state) -> list.contains(state.m_60734_())));
   }

   public static List<BlockPos> getNearbyBlocks(Entity entity, int radius) {
      return getNearbyBlocks(entity.m_20183_(), entity.m_9236_(), radius);
   }

   public static List<BlockPos> getNearbyBlocks(BlockPos pos, LevelAccessor world, int radius) {
      return getNearbyBlocks(pos, world, radius, radius, radius, (state) -> !(state.m_60734_() instanceof AirBlock));
   }

   /** @deprecated */
   @Deprecated
   public static List<BlockPos> getNearbyBlocks(BlockPos pos, LevelAccessor world, int radius, List<Block> bannedBlocks) {
      return getNearbyBlocks(pos, world, radius, (Predicate)null, bannedBlocks);
   }

   /** @deprecated */
   @Deprecated
   public static List<BlockPos> getNearbyBlocks(BlockPos pos, LevelAccessor world, int radius, @Nullable Predicate<BlockPos> predicate, List<Block> bannedBlocks) {
      predicate = predicate == null ? (blockPos) -> true : predicate;
      BlockPos.MutableBlockPos mutpos = new BlockPos.MutableBlockPos();
      List<BlockPos> blockLocations = new ArrayList();

      for(int x = -radius; x <= radius; ++x) {
         for(int y = -radius; y <= radius; ++y) {
            for(int z = -radius; z <= radius; ++z) {
               mutpos.m_122178_(pos.m_123341_() + x, pos.m_123342_() + y, pos.m_123343_() + z);
               if (predicate.test(mutpos)) {
                  Block block = world.m_8055_(mutpos).m_60734_();
                  if (!bannedBlocks.contains(block)) {
                     blockLocations.add(mutpos.m_7949_());
                  }
               }
            }
         }
      }

      return blockLocations;
   }

   public static List<BlockPos> getNearbyBlocks(BlockPos pos, LevelAccessor world, int sizeX, int sizeY, int sizeZ, @Nullable Predicate<BlockState> predicate) {
      predicate = predicate == null ? (blockPos) -> true : predicate;
      BlockPos.MutableBlockPos mutpos = new BlockPos.MutableBlockPos();
      List<BlockPos> blockLocations = new ArrayList();

      for(int x = -sizeX; x <= sizeX; ++x) {
         for(int y = -sizeY; y <= sizeY; ++y) {
            for(int z = -sizeZ; z <= sizeZ; ++z) {
               mutpos.m_122178_(pos.m_123341_() + x, pos.m_123342_() + y, pos.m_123343_() + z);
               BlockState state = world.m_8055_(mutpos);
               if (predicate.test(state)) {
                  blockLocations.add(mutpos.m_7949_());
               }
            }
         }
      }

      return blockLocations;
   }

   public static List<BlockPos> getNearbyTileEntities(Entity player, int radius) {
      return getNearbyTileEntities(player.m_20183_(), player.m_9236_(), radius);
   }

   public static List<BlockPos> getNearbyTileEntities(BlockPos pos, Level world, int radius) {
      BlockPos.MutableBlockPos mutpos = new BlockPos.MutableBlockPos();
      List<BlockPos> blockLocations = new ArrayList();

      for(int x = -radius; x <= radius; ++x) {
         for(int y = -radius; y <= radius; ++y) {
            for(int z = -radius; z <= radius; ++z) {
               mutpos.m_122178_(pos.m_123341_() + x, pos.m_123342_() + y, pos.m_123343_() + z);
               if (world.m_8055_(mutpos).m_60734_() != Blocks.f_50016_ && world.m_7702_(mutpos) != null) {
                  blockLocations.add(mutpos.m_7949_());
               }
            }
         }
      }

      return blockLocations;
   }

   public static BlockPos findOnGroundSpawnLocation(Level world, EntityType<?> type, BlockPos spawnLocation, int radius) {
      return findOnGroundSpawnLocation(world, type, spawnLocation, radius, 0);
   }

   public static @Nullable BlockPos findOnGroundSpawnLocation(Level world, EntityType<?> type, BlockPos spawnLocation, int radius, int offset) {
      BlockPos blockpos = null;

      for(int i = 0; i < 10; ++i) {
         int x = (int)randomWithRange(spawnLocation.m_123341_() - offset - radius, spawnLocation.m_123341_() + offset + radius);
         int z = (int)randomWithRange(spawnLocation.m_123343_() - offset - radius, spawnLocation.m_123343_() + offset + radius);
         int y = world.m_6924_(Types.WORLD_SURFACE, x, z);
         BlockPos blockpos1 = new BlockPos(x, y, z);
         if (NaturalSpawner.canSpawnAtBody(Type.ON_GROUND, world, blockpos1, type)) {
            blockpos = blockpos1;
            break;
         }
      }

      return blockpos;
   }

   public static Vec3 findValidGroundLocation(Level level, Vec3 spawnPos, int radius, int offset) {
      Vec3 vec3 = null;

      for(int i = 0; i < 10; ++i) {
         int x = (int)randomWithRange(spawnPos.m_7096_() - (double)offset - (double)radius, spawnPos.m_7096_() + (double)offset + (double)radius);
         int z = (int)randomWithRange(spawnPos.m_7094_() - (double)offset - (double)radius, spawnPos.m_7094_() + (double)offset + (double)radius);

         for(int j = -5; j < 0; ++j) {
            int y = (int)(spawnPos.m_7098_() + (double)j);
            BlockPos pos = new BlockPos(x, y, z);
            BlockState state = level.m_8055_(pos.m_7495_());
            if (!state.m_60795_() && state.m_280296_() && !state.m_60812_(level, pos).equals(Shapes.m_83040_())) {
               vec3 = new Vec3((double)x, (double)y, (double)z);
               break;
            }
         }

         if (vec3 != null) {
            break;
         }
      }

      return vec3;
   }

   public static BlockPos findValidGroundLocation(Level level, BlockPos spawnPos, int radius, int offset) {
      BlockPos blockpos = null;

      for(int i = 0; i < 10; ++i) {
         int x = (int)randomWithRange(spawnPos.m_123341_() - offset - radius, spawnPos.m_123341_() + offset + radius);
         int z = (int)randomWithRange(spawnPos.m_123343_() - offset - radius, spawnPos.m_123343_() + offset + radius);

         for(int j = -5; j < 0; ++j) {
            int y = spawnPos.m_123342_() + j;
            BlockPos pos = new BlockPos(x, y, z);
            BlockState state = level.m_8055_(pos.m_7495_());
            if (!state.m_60795_() && state.m_280296_() && !state.m_60812_(level, pos).equals(Shapes.m_83040_())) {
               blockpos = pos;
               break;
            }
         }

         if (blockpos != null) {
            break;
         }
      }

      return blockpos;
   }

   public static BlockPos findValidGroundLocation(Entity entity, BlockPos spawnPos, int radius, int offset) {
      BlockPos blockpos = entity.m_20183_();

      for(int i = 0; i < 10; ++i) {
         int x = (int)randomWithRange(spawnPos.m_123341_() - offset - radius, spawnPos.m_123341_() + offset + radius);
         int z = (int)randomWithRange(spawnPos.m_123343_() - offset - radius, spawnPos.m_123343_() + offset + radius);
         BlockPos pos = new BlockPos(x, spawnPos.m_123342_(), z);
         BlockState state = entity.m_9236_().m_8055_(pos.m_7495_());
         if (!state.m_60795_() && state.m_280296_() && !state.m_60812_(entity.m_9236_(), pos).equals(Shapes.m_83040_())) {
            blockpos = pos;
            break;
         }
      }

      return blockpos;
   }

   public static String getTextureName(String texture) {
      for(String s : texture.split("/")) {
         if (s.contains(".png")) {
            return s.replace(".png", "");
         }
      }

      return null;
   }

   public static void spawnDamageIndicatorParticles(Level world, LivingEntity target, int amount) {
      if (world instanceof ServerLevel) {
         ((ServerLevel)world).m_8767_(ParticleTypes.f_123798_, target.m_20185_(), target.m_20227_((double)0.5F), target.m_20189_(), amount, 0.1, (double)0.0F, 0.1, 0.2);
      }

   }

   public static boolean isAprilFirst() {
      Calendar cal = Calendar.getInstance();
      int day = cal.get(5);
      int month = cal.get(2) + 1;
      return month == 4 && day == 1;
   }

   public static long getDaysSince(Date date) {
      Date now = new Date();
      long diffInMillies = Math.abs(now.getTime() - date.getTime());
      long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
      return diff;
   }

   public static int roundToNiceNumber(float n) {
      float n1 = n - n % 10.0F;
      float n2 = n + 10.0F - n % 10.0F;
      float res = n - n1 > n2 - n ? n2 : n1;
      return (int)res;
   }

   public static float minutesToTicks(float minutes) {
      return secondsToTicks(minutes * 60.0F);
   }

   public static float secondsToTicks(float seconds) {
      return seconds * 20.0F;
   }

   public static double percentage(double percent, double value) {
      return percent / (double)100.0F * value;
   }

   public static double randomWithRange(int min, int max) {
      return (double)(RANDOM.nextInt(max + 1 - min) + min);
   }

   public static double randomWithRange(double min, double max) {
      return RANDOM.nextDouble(max + (double)1.0F - min) + min;
   }

   public static double randomWithRange(Random rand, int min, int max) {
      return (double)(rand.nextInt(max + 1 - min) + min);
   }

   public static double randomWithRange(RandomSource rand, int min, int max) {
      return (double)(rand.m_188503_(max + 1 - min) + min);
   }

   public static double randomDouble() {
      return RANDOM.nextDouble() * (double)2.0F - (double)1.0F;
   }

   public static double randomDouble(Random rand) {
      return rand.nextDouble() * (double)2.0F - (double)1.0F;
   }

   public static double randomDouble(RandomSource rand) {
      return rand.m_188500_() * (double)2.0F - (double)1.0F;
   }

   public static int round(int value) {
      String valueString = "" + value;
      return valueString.length() < 1 ? value : round(value, valueString.length() - 1);
   }

   public static int round(int value, int nth) {
      String valueString = "" + value;
      if (valueString.length() >= 1 && nth >= 0) {
         if (nth == 0) {
            nth = 1;
         }

         int n = (int)Math.pow((double)10.0F, (double)(nth - 1));
         int r = 5 * (n / 10);
         return (value + r) / n * n;
      } else {
         return value;
      }
   }

   public static long clamp(long num, long min, long max) {
      return num < min ? min : Math.min(num, max);
   }

   public static double getDifferenceToFloor(Entity entity) {
      return entity.m_20182_().m_82546_(getFloorLevel(entity)).f_82480_;
   }

   public static Vec3 getFloorLevel(Entity entity) {
      Vec3 startVec = entity.m_20182_();
      Vec3 endVec = startVec.m_82520_((double)0.0F, (double)-256.0F, (double)0.0F);
      BlockHitResult blockResult = entity.m_9236_().m_45547_(new ClipContext(startVec, endVec, net.minecraft.world.level.ClipContext.Block.OUTLINE, Fluid.ANY, entity));
      return blockResult.m_82450_();
   }

   public static String escapeChatFormattingChars(String text) {
      return text.replaceAll("§[0-9a-f]", "");
   }

   /** @deprecated */
   @Deprecated
   public static void generateJSONLangs() {
      Map<String, String> sorted = sortAlphabetically(ModRegistry.getLangMap());
      Set<Map.Entry<String, String>> set = sorted.entrySet();
      Iterator<Map.Entry<String, String>> iter = set.iterator();
      Map.Entry<String, String> prevEntry = null;
      File langFolder = new File(getResourceFolderPath() + "/assets/mineminenomi/lang/");
      langFolder.mkdirs();
      if (langFolder.exists()) {
         try {
            Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(getResourceFolderPath() + "/assets/mineminenomi/lang/en_us.json"), "UTF-8"));

            try {
               writer.write("{\n");

               Map.Entry<String, String> entry;
               for(; iter.hasNext(); prevEntry = entry) {
                  entry = (Map.Entry)iter.next();
                  if (prevEntry != null && !((String)prevEntry.getKey()).substring(0, 2).equals(((String)entry.getKey()).substring(0, 2))) {
                     writer.write("\n");
                  }

                  String value = escapeJSON((String)entry.getValue());
                  if (iter.hasNext()) {
                     String var10001 = (String)entry.getKey();
                     writer.write("\t\"" + var10001 + "\": \"" + value + "\",\n");
                  } else {
                     String var11 = (String)entry.getKey();
                     writer.write("\t\"" + var11 + "\": \"" + value + "\"\n");
                  }
               }

               writer.write("}\n");
               writer.close();
            } catch (Throwable var9) {
               try {
                  writer.close();
               } catch (Throwable var8) {
                  var9.addSuppressed(var8);
               }

               throw var9;
            }

            writer.close();
         } catch (Exception e) {
            e.printStackTrace();
         }
      }

   }

   public static @Nullable String getResourceFolderPath() {
      if (!WyDebug.isDebug()) {
         return null;
      } else {
         String basicPath = System.getProperty("user.dir");
         String var10000 = basicPath.replaceAll("/run/.*", "");
         return var10000.replaceAll("\\\\run\\\\.*", "") + "/src/main/resources";
      }
   }

   public static <T> List<T> shuffle(List<T> ar) {
      List<T> newList = new ArrayList(ar);
      Collections.shuffle(newList);
      return newList;
   }

   public static <T> List<T> shuffle(List<T> ar, long seed) {
      List<T> newList = new ArrayList(ar);
      Random rand = new Random(seed);
      Collections.shuffle(newList, rand);
      return newList;
   }

   public static <T> Collector<T, ?, List<T>> toShuffledList() {
      return SHUFFLER;
   }

   public static <K extends Comparable<K>, V extends Comparable<V>> Map<K, V> sortAlphabetically(Map<K, V> map) {
      List<Map.Entry<K, V>> entries = new LinkedList(map.entrySet());
      Collections.sort(entries, (o1, o2) -> ((Comparable)o1.getKey()).compareTo((Comparable)o2.getKey()));
      Map<K, V> sortedMap = new LinkedHashMap();

      for(Map.Entry<K, V> entry : entries) {
         sortedMap.put((Comparable)entry.getKey(), (Comparable)entry.getValue());
      }

      return sortedMap;
   }

   public static BlockPos[][] splitArray(BlockPos[] arrayToSplit, int chunkSize) {
      if (chunkSize <= 0) {
         return null;
      } else {
         int rest = arrayToSplit.length % chunkSize;
         int chunks = arrayToSplit.length / chunkSize + (rest > 0 ? 1 : 0);
         BlockPos[][] arrays = new BlockPos[chunks][];

         for(int i = 0; i < (rest > 0 ? chunks - 1 : chunks); ++i) {
            arrays[i] = (BlockPos[])Arrays.copyOfRange(arrayToSplit, i * chunkSize, i * chunkSize + chunkSize);
         }

         if (rest > 0) {
            arrays[chunks - 1] = (BlockPos[])Arrays.copyOfRange(arrayToSplit, (chunks - 1) * chunkSize, (chunks - 1) * chunkSize + rest);
         }

         return arrays;
      }
   }

   public static final int getIndexOfItemStack(Item item, Inventory inven) {
      for(int i = 0; i < inven.m_6643_(); ++i) {
         if (inven.m_8020_(i).m_41720_() == item) {
            return i;
         }
      }

      return -1;
   }

   public static <T> @Nullable T sendGET(String sendUrl, Class<?> resultType) throws IOException {
      T result = null;
      URL url = new URL("https://pixelatedw.xyz/api/v1" + sendUrl);
      HttpURLConnection connection = (HttpURLConnection)url.openConnection();
      connection.setRequestMethod("GET");
      String var10002 = MCPVersion.getMCVersion();
      connection.setRequestProperty("User-Agent", "mineminenomi/0.11.0-" + var10002 + "-" + BuildMode.MODE.toString().toLowerCase());
      connection.setConnectTimeout(30000);
      int responseCode = connection.getResponseCode();
      if (responseCode != 200 && responseCode != 202) {
         WyDebug.error("==============ERROR WHILE RETRIEVING SERVER DATA==============");
         WyDebug.error("Response Code: " + responseCode + " - " + connection.getResponseMessage());
         WyDebug.error("=============================================================");
      } else {
         BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

         try {
            StringBuilder sb = new StringBuilder();

            String line;
            while((line = in.readLine()) != null) {
               sb.append(line + "\n");
            }

            result = (T)(new Gson()).fromJson(sb.toString(), resultType);
         } catch (Throwable var10) {
            try {
               in.close();
            } catch (Throwable var9) {
               var10.addSuppressed(var9);
            }

            throw var10;
         }

         in.close();
      }

      return result;
   }

   public static void removeAllModifiers(AttributeInstance attr) {
      Collection<AttributeModifier> collection = attr.m_22122_();
      if (collection != null) {
         for(AttributeModifier attributemodifier : Lists.newArrayList(collection)) {
            attr.m_22130_(attributemodifier);
         }
      }

   }

   public static <T> T[] concatAllArrays(T[] first, T[]... rest) {
      int totalLength = first.length;

      for(T[] array : rest) {
         totalLength += array.length;
      }

      T[] result = (T[])Arrays.copyOf(first, totalLength);
      int offset = first.length;

      for(T[] array : rest) {
         System.arraycopy(array, 0, result, offset, array.length);
         offset += array.length;
      }

      return result;
   }

   public static String stringToSnakeCase(String input) {
      String cleanedInput = input.replaceAll("[^a-zA-Z0-9 ]", "");
      return (String)Arrays.stream(cleanedInput.split(" ")).map(String::toLowerCase).collect(Collectors.joining("_"));
   }

   public static <T, E> @Nullable T getPrivateValue(Class<? super E> classToAccess, E instance, String fieldName) {
      try {
         Field field = classToAccess.getDeclaredField(fieldName);
         field.setAccessible(true);
         return (T)field.get(instance);
      } catch (Exception e) {
         e.printStackTrace();
         return null;
      }
   }
}
