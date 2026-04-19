package xyz.pixelatedw.mineminenomi.api.helpers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.ClipContext.Block;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.Event.Result;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.Nullable;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityUnlock;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponentKey;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.AbilityNode;
import xyz.pixelatedw.mineminenomi.api.chat.WyComponent;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.events.ability.UnlockAbilityEvent;
import xyz.pixelatedw.mineminenomi.api.events.entity.SetOnFireEvent;
import xyz.pixelatedw.mineminenomi.api.morph.MorphInfo;
import xyz.pixelatedw.mineminenomi.config.ServerConfig;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityCapability;
import xyz.pixelatedw.mineminenomi.data.entity.ability.IAbilityData;
import xyz.pixelatedw.mineminenomi.data.entity.devilfruit.DevilFruitCapability;
import xyz.pixelatedw.mineminenomi.data.entity.devilfruit.IDevilFruit;
import xyz.pixelatedw.mineminenomi.data.entity.haki.HakiCapability;
import xyz.pixelatedw.mineminenomi.data.entity.stats.EntityStatsCapability;
import xyz.pixelatedw.mineminenomi.data.entity.stats.IEntityStats;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;
import xyz.pixelatedw.mineminenomi.init.ModAbilityPools;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModFruits;
import xyz.pixelatedw.mineminenomi.init.ModMorphs;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.ModSounds;
import xyz.pixelatedw.mineminenomi.init.ModTags;
import xyz.pixelatedw.mineminenomi.init.ModValues;
import xyz.pixelatedw.mineminenomi.items.AkumaNoMiItem;
import xyz.pixelatedw.mineminenomi.mixins.IMobEffectInstanceMixin;
import xyz.pixelatedw.mineminenomi.packets.server.ability.component.SDisableAbilityPacket;
import xyz.pixelatedw.mineminenomi.particles.effects.GuardParticleEffect;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class AbilityHelper {
   public static final int CLOUD_HEIGHT = 192;
   public static final ArrayList<SourceType> NO_SOURCE_TYPE;
   private static final Object[] EMPTY_ARGS;
   public static final Function<Player, ResourceLocation> DF_CATEGORY_ICON;
   public static final Function<Player, ResourceLocation> RACE_CATEGORY_ICON;
   public static final Function<Player, ResourceLocation> HAKI_CATEGORY_ICON;
   public static final Comparator<LivingEntity> COMPARE_DISTANCE;

   @SafeVarargs
   public static Component[] registerDescriptionText(String modid, String abilityName, Pair<String, Object[]>... pairs) {
      Component[] components = new Component[pairs.length];

      for(int i = 0; i < pairs.length; ++i) {
         String key = String.format("ability.%s.%s.description.%s", modid, abilityName, i);
         key = ModRegistry.registerName(key, (String)pairs[i].getKey());
         Object[] args = pairs[i].getValue();
         if (args != null) {
            for(int j = 0; j < args.length; ++j) {
               Object o = args[j];
               if (o instanceof RegistryObject) {
                  RegistryObject<?> reg = (RegistryObject)o;
                  args[j] = WyComponent.reference(reg);
               } else {
                  Component comp = MentionHelper.tryMentionObject(o);
                  if (comp != null) {
                     args[j] = comp;
                  }
               }
            }
         } else {
            args = EMPTY_ARGS;
         }

         Component comp = Component.m_237110_(key, args);
         components[i] = comp;
      }

      return components;
   }

   public static AbilityCore.ICanUnlock generateFruitUnlock(RegistryObject<AkumaNoMiItem> fruit) {
      return (entity) -> {
         IDevilFruit fruitData = (IDevilFruit)DevilFruitCapability.get(entity).orElse((Object)null);
         if (fruitData == null) {
            return false;
         } else {
            return fruitData.hasDevilFruit(fruit);
         }
      };
   }

   public static void setSecondsOnFireBy(Entity entity, int seconds, @Nullable LivingEntity source) {
      SetOnFireEvent event = new SetOnFireEvent(source, entity, seconds);
      if (!MinecraftForge.EVENT_BUS.post(event)) {
         entity.m_20254_(event.getFireTime());
      }

   }

   public static void emergencyStopAbility(LivingEntity entity, IAbility ability) {
      ability.getComponent((AbilityComponentKey)ModAbilityComponents.CONTINUOUS.get()).ifPresent((comp) -> {
         if (comp.isContinuous()) {
            comp.stopContinuity(entity);
         }

      });
      ability.getComponent((AbilityComponentKey)ModAbilityComponents.CHARGE.get()).ifPresent((comp) -> {
         if (comp.isCharging()) {
            comp.forceStopCharging(entity);
            ability.getComponent((AbilityComponentKey)ModAbilityComponents.COOLDOWN.get()).ifPresent((cooldownComp) -> cooldownComp.startCooldown(entity, 40.0F));
         }

      });
      ability.getComponent((AbilityComponentKey)ModAbilityComponents.ANIMATION.get()).ifPresent((comp) -> comp.stop(entity));
   }

   public static void enableAbilities(LivingEntity entity, Predicate<IAbility> check) {
      IAbilityData abilityData = (IAbilityData)AbilityCapability.get(entity).orElse((Object)null);
      if (abilityData != null) {
         List<IAbility> abilities = (List)abilityData.getEquippedAndPassiveAbilities().stream().filter((abilityx) -> check.test(abilityx)).collect(Collectors.toList());
         Set<IAbility> markedAbilities = new HashSet();

         for(IAbility ability : abilities) {
            ability.getComponent((AbilityComponentKey)ModAbilityComponents.DISABLE.get()).ifPresent((component) -> {
               component.stopDisable(entity);
               markedAbilities.add(ability);
            });
         }

         if (!entity.m_9236_().f_46443_) {
            ModNetwork.sendToAllTrackingAndSelf(new SDisableAbilityPacket(entity.m_19879_(), markedAbilities, 0, false), entity);
         }

      }
   }

   public static void disableAbilities(LivingEntity entity, int duration, Predicate<IAbility> check) {
      IAbilityData abilityData = (IAbilityData)AbilityCapability.get(entity).orElse((Object)null);
      if (abilityData != null) {
         List<IAbility> abilities = (List)abilityData.getEquippedAndPassiveAbilities().stream().filter((abilityx) -> check.test(abilityx)).collect(Collectors.toList());
         Set<IAbility> markedAbilities = new HashSet();

         for(IAbility ability : abilities) {
            ability.getComponent((AbilityComponentKey)ModAbilityComponents.DISABLE.get()).ifPresent((component) -> {
               emergencyStopAbility(entity, ability);
               component.stopDisable(entity);
               component.startDisable(entity, (float)duration);
               markedAbilities.add(ability);
            });
         }

         if (!entity.m_9236_().f_46443_) {
            ModNetwork.sendToAllTrackingAndSelf(new SDisableAbilityPacket(entity.m_19879_(), markedAbilities, duration, true), entity);
         }

      }
   }

   public static void forceCooldownOnAllAbilities(LivingEntity entity, int cooldown) {
      AbilityCapability.get(entity).ifPresent((props) -> props.getEquippedAbilities().forEach((abl) -> abl.getComponent((AbilityComponentKey)ModAbilityComponents.COOLDOWN.get()).ifPresent((comp) -> comp.startCooldown(entity, (float)cooldown))));
   }

   public static boolean verifyIfAbilityIsBanned(AbilityCore<?> core) {
      return ServerConfig.getBannedAbilities().stream().anyMatch((abl) -> abl.equals(core));
   }

   private static boolean canUnlockFruitAbility(LivingEntity entity, AbilityCore<?> abilityCore) {
      if (abilityCore.getCategory() == AbilityCategory.DEVIL_FRUITS) {
         IDevilFruit fruitData = (IDevilFruit)DevilFruitCapability.get(entity).orElse((Object)null);
         if (fruitData == null) {
            return false;
         }

         if (fruitData.hasDevilFruit(ModFruits.YOMI_YOMI_NO_MI) && !((MorphInfo)ModMorphs.YOMI_SKELETON.get()).isActive(entity)) {
            return false;
         }

         Item df = fruitData.getDevilFruitItem();
         if (df == null || df == Items.f_41852_ || !(df instanceof AkumaNoMiItem)) {
            return false;
         }

         AkumaNoMiItem fruit = (AkumaNoMiItem)df;
         if (fruitData.hasYamiPower()) {
            for(AbilityCore<?> core : ((AkumaNoMiItem)ModFruits.YAMI_YAMI_NO_MI.get()).getAbilities()) {
               if (abilityCore.equals(core)) {
                  return true;
               }
            }
         }

         for(AbilityCore<?> core : fruit.getAbilities()) {
            if (abilityCore.equals(core)) {
               boolean canUnlock = core.hasUnlockCheck() ? core.canUnlock(entity) : true;
               if (canUnlock) {
                  return true;
               }
            }
         }
      }

      return false;
   }

   public static boolean checkAndUnlockAbility(LivingEntity entity, AbilityCore<?> core) {
      IAbilityData abilityData = (IAbilityData)AbilityCapability.get(entity).orElse((Object)null);
      if (abilityData == null) {
         return false;
      } else {
         boolean hasAbilityUnlocked = abilityData.hasUnlockedAbility(core);
         boolean isAbilityBanned = ServerConfig.isAbilityFraudChecksEnabled() && verifyIfAbilityIsBanned(core);
         boolean isFruitAbilityAndCanUnlock = canUnlockFruitAbility(entity, core);
         AbilityNode node = core.getNode(entity);
         boolean isNodeUnlocked = node != null && node.isUnlocked(entity);
         boolean canUnlock = core.canUnlock(entity) || isFruitAbilityAndCanUnlock || isNodeUnlocked;
         UnlockAbilityEvent event = new UnlockAbilityEvent(entity, core);
         MinecraftForge.EVENT_BUS.post(event);
         if (event.getResult() == Result.ALLOW) {
            canUnlock = true;
         } else if (event.getResult() == Result.DENY) {
            canUnlock = false;
         }

         if (hasAbilityUnlocked) {
            if (isAbilityBanned) {
               abilityData.removeUnlockedAbility(core);
               return true;
            } else {
               if (!canUnlock) {
                  AbilityUnlock unlockType = abilityData.getUnlockTypeForAbility(core);
                  if (abilityData.hasUnlockedAbility(core) && unlockType == AbilityUnlock.PROGRESSION) {
                     abilityData.removeUnlockedAbility(core);
                     return true;
                  }
               }

               return false;
            }
         } else if (!isAbilityBanned && canUnlock) {
            abilityData.addUnlockedAbility(core, AbilityUnlock.PROGRESSION);
            return true;
         } else {
            return false;
         }
      }
   }

   public static void setDeltaMovement(Entity entity, double x, double y, double z) {
      setDeltaMovement(entity, new Vec3(x, y, z), false);
   }

   public static void setDeltaMovement(Entity entity, Vec3 velocity) {
      setDeltaMovement(entity, velocity, false);
   }

   public static void setDeltaMovement(Entity entity, double x, double y, double z, boolean isPrecise) {
      setDeltaMovement(entity, new Vec3(x, y, z), isPrecise);
   }

   public static void setDeltaMovement(Entity entity, Vec3 velocity, boolean isPrecise) {
      entity.m_20256_(velocity);
      if (velocity.m_7098_() > (double)0.0F) {
         entity.m_6853_(false);
      }

      if (entity instanceof ServerPlayer player) {
         Vec3 position = player.m_20182_();
         player.f_19854_ = position.f_82479_;
         player.f_19855_ = position.f_82480_;
         player.f_19856_ = position.f_82481_;
         player.f_19790_ = position.f_82479_;
         player.f_19791_ = position.f_82480_;
         player.f_19792_ = position.f_82481_;
         if (isPrecise) {
            entity.m_6478_(MoverType.SELF, velocity);
         }

         player.f_8906_.m_9829_(new ClientboundSetEntityMotionPacket(player));
      } else if (!entity.m_20160_()) {
         entity.f_19864_ = true;
      }

   }

   public static void slowEntityFall(LivingEntity player) {
      slowEntityFall(player, 5);
   }

   public static void slowEntityFall(LivingEntity player, int duration) {
      player.m_7292_(new MobEffectInstance((MobEffect)ModEffects.REDUCED_FALL.get(), duration, 0));
   }

   public static boolean isAffectedByWater(LivingEntity entity) {
      return isAffectedByWater(entity, 0.5F);
   }

   public static boolean isAffectedByWater(LivingEntity entity, float waterLevel) {
      if (entity.m_5833_()) {
         return false;
      } else if (entity.m_21023_((MobEffect)ModEffects.BUBBLY_CORAL.get())) {
         return false;
      } else {
         boolean isUnderDaSea = entity.isEyeInFluidType((FluidType)ForgeMod.WATER_TYPE.get());
         if (isUnderDaSea) {
            return true;
         } else if (entity.m_20159_()) {
            return false;
         } else {
            FluidState fluidState = entity.m_9236_().m_6425_(entity.m_20183_());
            float entityHeight = entity.m_20206_();
            int waterLevelCheck = Math.max(1, Math.round(entityHeight * waterLevel));
            boolean isInWater = fluidState.m_205070_(FluidTags.f_13131_);
            if (isInWater) {
               boolean hasWaterUnder = false;
               BlockPos.MutableBlockPos mutpos = new BlockPos.MutableBlockPos();

               for(int i = 0; i <= waterLevelCheck; ++i) {
                  mutpos.m_122190_(entity.m_20183_().m_6625_(i));
                  boolean isWater = entity.m_9236_().m_6425_(mutpos).m_205070_(FluidTags.f_13131_);
                  if (!isWater) {
                     hasWaterUnder = false;
                     break;
                  }

                  hasWaterUnder = true;
               }

               if (hasWaterUnder) {
                  return true;
               }
            }

            if (fluidState.m_76182_() < 0.8F && !fluidState.m_76170_()) {
               return false;
            } else {
               float waterLevelHeight = entityHeight * waterLevel;
               return entity.getFluidTypeHeight((FluidType)ForgeMod.WATER_TYPE.get()) >= (double)waterLevelHeight;
            }
         }
      }
   }

   public static boolean isKairosekiNearby(LivingEntity entity) {
      if (entity.m_5833_()) {
         return false;
      } else if (entity.m_21023_((MobEffect)ModEffects.BUBBLY_CORAL.get())) {
         return false;
      } else {
         return WyHelper.isBlockNearby(entity, 1, (Predicate)((state) -> state.m_204336_(ModTags.Blocks.KAIROSEKI))) || ItemsHelper.hasKairosekiItem(entity);
      }
   }

   public static boolean isWeakenedByKairosekiOrWater(LivingEntity entity) {
      return isKairosekiNearby(entity) || isAffectedByWater(entity);
   }

   public static double getDifferenceToFloor(Entity entity) {
      return entity.m_20182_().m_82546_(getFloorLevel(entity)).f_82480_;
   }

   public static Vec3 getFloorLevel(Entity entity) {
      Vec3 startVec = entity.m_20182_();
      Vec3 endVec = startVec.m_82520_((double)0.0F, (double)-256.0F, (double)0.0F);
      BlockHitResult blockResult = entity.m_9236_().m_45547_(new ClipContext(startVec, endVec, Block.OUTLINE, Fluid.ANY, entity));
      return blockResult.m_82450_();
   }

   public static void reduceEffect(@Nullable MobEffectInstance effect, double reduction) {
      if (effect != null) {
         try {
            double duration = (double)effect.m_19557_() / reduction;
            ((IMobEffectInstanceMixin)effect).setDuration((int)duration);
         } catch (Exception e) {
            e.printStackTrace();
         }

      }
   }

   public static boolean isInCreativeOrSpectator(@Nullable LivingEntity entity) {
      if (entity == null) {
         return false;
      } else if (entity.m_5833_()) {
         return true;
      } else {
         if (entity instanceof Player) {
            Player player = (Player)entity;
            if (player.m_7500_()) {
               return true;
            }
         }

         return false;
      }
   }

   public static void vanillaFlightThreshold(LivingEntity entity, int height) {
      if (entity.m_20186_() > (double)height) {
         setDeltaMovement(entity, entity.m_20184_().m_82520_((double)0.0F, (double)0.25F * (double)((float)height / 5.0F), (double)0.0F).m_82542_((double)1.0F, -0.15, (double)1.0F));
      }

   }

   public static boolean flyingAtMaxHeight(LivingEntity player, double maxDifference) {
      return maxDifference > getDifferenceToFloor(player);
   }

   public static boolean isGuardBlocking(LivingEntity entity) {
      IAbilityData abilityData = (IAbilityData)AbilityCapability.get(entity).orElse((Object)null);
      if (abilityData == null) {
         return false;
      } else {
         MobEffectInstance instance = entity.m_21124_((MobEffect)ModEffects.UNCONSCIOUS.get());
         if (instance != null && instance.m_19564_() > 0) {
            return false;
         } else {
            boolean hasGuardAbilityActive = abilityData.getEquippedAbilitiesInPool(ModAbilityPools.GUARD_ABILITY).anyMatch((abl) -> abl.hasComponent((AbilityComponentKey)ModAbilityComponents.CONTINUOUS.get()) && ((ContinuousComponent)abl.getComponent((AbilityComponentKey)ModAbilityComponents.CONTINUOUS.get()).get()).isContinuous());
            if (hasGuardAbilityActive) {
               entity.m_9236_().m_5594_((Player)null, entity.m_20183_(), (SoundEvent)ModSounds.GUARD.get(), SoundSource.PLAYERS, 2.0F, 1.0F);
               (new GuardParticleEffect()).spawn(entity, entity.m_9236_(), (double)0.0F, (double)0.0F, (double)0.0F, (ParticleEffect.NoDetails)(new ParticleEffect.NoDetails()));
            }

            return hasGuardAbilityActive;
         }
      }
   }

   static {
      NO_SOURCE_TYPE = new ArrayList(Arrays.asList(SourceType.UNKNOWN));
      EMPTY_ARGS = new Object[0];
      DF_CATEGORY_ICON = (player) -> {
         IDevilFruit props = (IDevilFruit)DevilFruitCapability.get(player).orElse((Object)null);
         if (props == null) {
            return null;
         } else {
            ResourceLocation icon = null;
            if (props.hasAnyDevilFruit()) {
               ResourceLocation res;
               if (props.hasYamiPower()) {
                  res = ((AkumaNoMiItem)ModFruits.YAMI_YAMI_NO_MI.get()).getRegistryKey();
               } else {
                  res = (ResourceLocation)props.getDevilFruit().orElse(ModValues.NIL_LOCATION);
               }

               icon = ResourceLocation.fromNamespaceAndPath(res.m_135827_(), "textures/items/" + res.m_135815_() + ".png");
            }

            return icon;
         }
      };
      RACE_CATEGORY_ICON = (player) -> {
         String iconName = null;
         IEntityStats props = (IEntityStats)EntityStatsCapability.get(player).orElse((Object)null);
         if (props == null) {
            return null;
         } else {
            if (props.isHuman()) {
               iconName = "human-rokushiki";
            } else if (props.isFishman()) {
               iconName = "fishman-karate";
            } else if (props.isCyborg()) {
               iconName = "cyborg-abilities";
            } else if (props.isMink()) {
               iconName = "mink-electro";
            }

            return iconName != null ? ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/gui/icons/" + iconName + ".png") : null;
         }
      };
      HAKI_CATEGORY_ICON = (player) -> {
         float totalHaki = (Float)HakiCapability.get(player).map((props) -> props.getTotalHakiExp()).orElse(0.0F);
         float haoLevel = totalHaki / 100.0F;
         String id = "haoshoku_haki";
         String level = "0";
         if (haoLevel > 1.0F && (double)haoLevel <= (double)1.75F) {
            level = "1";
         } else if ((double)haoLevel > (double)1.75F) {
            level = "2";
         }

         return ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/abilities/" + id + "_" + level + ".png");
      };
      COMPARE_DISTANCE = (o1, o2) -> 1;
   }
}
