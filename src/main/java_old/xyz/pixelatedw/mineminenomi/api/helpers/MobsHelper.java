package xyz.pixelatedw.mineminenomi.api.helpers;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.function.Supplier;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.OpenDoorGoal;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.animal.Ocelot;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.Nullable;
import xyz.pixelatedw.mineminenomi.abilities.haki.BusoshokuHakiEmissionAbility;
import xyz.pixelatedw.mineminenomi.abilities.haki.BusoshokuHakiFullBodyHardeningAbility;
import xyz.pixelatedw.mineminenomi.abilities.haki.BusoshokuHakiHardeningAbility;
import xyz.pixelatedw.mineminenomi.abilities.haki.BusoshokuHakiImbuingAbility;
import xyz.pixelatedw.mineminenomi.abilities.haki.BusoshokuHakiInternalDestructionAbility;
import xyz.pixelatedw.mineminenomi.api.NuWorld;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityUnlock;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.caps.command.CommandReceiverCapability;
import xyz.pixelatedw.mineminenomi.api.entities.ITamableEntity;
import xyz.pixelatedw.mineminenomi.api.entities.IThreatLevel;
import xyz.pixelatedw.mineminenomi.api.util.Result;
import xyz.pixelatedw.mineminenomi.config.ServerConfig;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityCapability;
import xyz.pixelatedw.mineminenomi.data.entity.ability.IAbilityData;
import xyz.pixelatedw.mineminenomi.data.entity.haki.HakiCapability;
import xyz.pixelatedw.mineminenomi.data.entity.stats.EntityStatsCapability;
import xyz.pixelatedw.mineminenomi.data.entity.stats.IEntityStats;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.FactionHurtByTargetGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.RunAwayFromFearGoal;
import xyz.pixelatedw.mineminenomi.entities.mobs.OPBossEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.animals.AbstractDugongEntity;
import xyz.pixelatedw.mineminenomi.init.ModEntityPredicates;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.init.ModWeapons;
import xyz.pixelatedw.mineminenomi.packets.server.ability.SEquipAbilityPacket;

public class MobsHelper {
   public static final int MARINE_BLUE_COLOR = 33980;
   public static final ArrayList<Supplier<? extends Item>> PIRATE_SWORDS;
   public static final ArrayList<Supplier<? extends Item>> MARINE_SWORDS;
   public static final ArrayList<Supplier<? extends Item>> MARINE_CAPTAIN_SWORDS;
   public static final ArrayList<Supplier<? extends Item>> BANDIT_SWORDS;
   public static final ArrayList<Supplier<? extends Item>> BRUTE_SWORDS;
   public static final ArrayList<Supplier<? extends Item>> GORILLA_AXES;
   public static final ResourceLocation[] PIRATE_TEXTURES;
   public static final ResourceLocation[] PIRATE_FISHMEN_TEXTURES;
   public static final ResourceLocation[] PIRATE_CAPTAINS_TEXTURES;
   public static final ResourceLocation[] PIRATE_TRADERS_TEXTURES;
   public static final ResourceLocation[] MARINE_TEXTURES;
   public static final ResourceLocation[] MARINE_CAPTAINS_TEXTURES;
   public static final ResourceLocation[] MARINE_TRADERS_TEXTURES;
   public static final ResourceLocation[] MARINE_VICE_ADMIRAL_TEXTURES;
   public static final ResourceLocation[] CELESTIAL_DRAGONS_TEXTURES;
   public static final ResourceLocation[] BANDIT_TEXTURES;
   public static final ResourceLocation[] SKYPEAN_TEXTURES;
   public static final ResourceLocation[] SKYPEAN_TRADERS_TEXTURES;
   public static final ResourceLocation[] BARKEEPER_TEXTURES;
   public static final ResourceLocation[] SWORDSMAN_TRAINER_TEXTURES;
   public static final ResourceLocation[] SNIPER_TRAINER_TEXTURES;
   public static final ResourceLocation[] DOCTOR_TRAINER_TEXTURES;
   public static final ResourceLocation[] BRAWLER_TRAINER_TEXTURES;
   public static final ResourceLocation[] BLACK_LEG_TRAINERS_TEXTURES;
   public static final ResourceLocation[] WEATHER_TRAINER_TEXTURES;
   public static final ResourceLocation[] DEN_DEN_MUSHI_TEXTURES;
   public static final ResourceLocation[] YAGARA_BULL_TEXTURES;
   public static final ResourceLocation[] NIGHTMARE_SOLDIER_TEXTURES;
   public static final ResourceLocation[] DUGONG_HEAD_SCARS_TEXTURES;
   public static final ResourceLocation[] DUGONG_CHEST_SCARS_TEXTURES;
   public static final ResourceLocation[] DUGONG_ARMS_SCARS_TEXTURES;
   public static final ResourceLocation[] DUGONG_TAIL_SCARS_TEXTURES;
   public static final Comparator<LivingEntity> ENTITY_THREAT;
   public static final Ability.ICanUseEvent BELOW_90_HP_CHECK;

   private static ArrayList<Supplier<? extends Item>> createMarineCaptainWeaponsList() {
      ArrayList<Supplier<? extends Item>> list = Lists.newArrayList(new Supplier[]{ModWeapons.JITTE, ModWeapons.BISENTO, () -> Items.f_42388_});
      list.addAll(MARINE_SWORDS);
      return list;
   }

   public static void removeFearGoals(Mob mob) {
      mob.f_21345_.m_262460_((g) -> g instanceof RunAwayFromFearGoal || g instanceof PanicGoal);
   }

   public static float getThreatLevel(LivingEntity entity) {
      if (entity instanceof IThreatLevel) {
         return ((IThreatLevel)entity).getThreatLevel();
      } else {
         double doriki = (Double)EntityStatsCapability.get(entity).map((props) -> props.getDoriki()).orElse((double)0.0F);
         doriki /= (double)ServerConfig.getDorikiLimit();
         doriki = Mth.m_14008_(doriki, (double)0.0F, (double)1.0F);
         double haki = (double)(Float)HakiCapability.get(entity).map((props) -> props.getTotalHakiExp()).orElse(0.0F);
         haki /= (double)(ServerConfig.getHakiExpLimit() * 2);
         haki = Mth.m_14008_(haki, (double)0.0F, (double)1.0F);
         float abilities = (float)(Integer)AbilityCapability.get(entity).map((props) -> props.getUnlockedAbilities().size()).orElse(0);
         abilities = abilities >= 30.0F ? 1.0F : 0.0F;
         if (entity instanceof Player) {
            abilities = 0.0F;
         }

         doriki *= (double)0.4F;
         haki *= (double)0.5F;
         abilities *= 0.1F;
         float threatLevel = (float)(doriki + haki + (double)abilities);
         threatLevel = Mth.m_14036_(threatLevel, 0.0F, 1.0F);
         return threatLevel;
      }
   }

   public static Predicate<Entity> shouldAttackTarget(LivingEntity entity) {
      return (targetEntity) -> {
         if (targetEntity instanceof Player target) {
            if (NuWorld.isChallengeDimension(entity.m_9236_())) {
               return true;
            } else {
               if (entity instanceof Mob) {
                  Mob mob = (Mob)entity;
                  if (mob.getSpawnType() == MobSpawnType.STRUCTURE || mob.getSpawnType() == MobSpawnType.EVENT) {
                     return true;
                  }
               }

               double entityThreatLevel = (double)getThreatLevel(entity);
               double targetThreatLevel = (double)getThreatLevel(target);
               if (entity.m_9236_().m_46791_().m_19028_() <= 2) {
                  float var10000;
                  switch (entity.m_9236_().m_46791_()) {
                     case EASY -> var10000 = 0.6F;
                     case NORMAL -> var10000 = 0.3F;
                     default -> var10000 = 0.0F;
                  }

                  float mod = var10000;
                  if (entityThreatLevel * (double)mod > targetThreatLevel) {
                     return false;
                  }
               }

               targetThreatLevel *= (double)0.25F;
               if (entityThreatLevel >= targetThreatLevel) {
                  return true;
               } else {
                  return false;
               }
            }
         } else {
            return true;
         }
      };
   }

   public static @Nullable LivingEntity getTamer(LivingEntity entity) {
      if (!ModEntityPredicates.canBeTamed().test(entity)) {
         return null;
      } else {
         if (entity instanceof Mob) {
            Mob mob = (Mob)entity;
            LivingEntity tamer = (LivingEntity)CommandReceiverCapability.get(mob).resolve().map((props) -> props.getLastCommandSender()).orElse((Object)null);
            if (tamer != null) {
               return tamer;
            }
         }

         if (entity instanceof TamableAnimal) {
            TamableAnimal tamable = (TamableAnimal)entity;
            return tamable.m_269323_();
         } else if (entity instanceof AbstractHorse) {
            AbstractHorse horse = (AbstractHorse)entity;
            UUID ownerUuid = horse.m_21805_();
            return ownerUuid == null ? null : entity.m_9236_().m_46003_(ownerUuid);
         } else if (entity instanceof ITamableEntity) {
            ITamableEntity tamableEntity = (ITamableEntity)entity;
            return tamableEntity.getOwnerIfAlive();
         } else {
            return null;
         }
      }
   }

   public static <T extends IAbility> @Nullable T unlockAndEquipAbility(LivingEntity entity, AbilityCore<T> core) {
      T ability = unlockAbility(entity, core);
      IAbilityData props = (IAbilityData)AbilityCapability.get(entity).orElse((Object)null);
      if (props != null && ability != null) {
         int availableSlot = props.getEquippedAbilities().size();
         props.setEquippedAbility(availableSlot, ability);
         if (!entity.m_9236_().f_46443_) {
            ModNetwork.sendToAllAround(new SEquipAbilityPacket(entity.m_19879_(), availableSlot, core), entity);
         }
      }

      return ability;
   }

   public static <T extends IAbility> @Nullable T unlockAbility(LivingEntity entity, AbilityCore<T> core) {
      IAbilityData props = (IAbilityData)AbilityCapability.get(entity).orElse((Object)null);
      if (props != null) {
         if (props.hasUnlockedAbility(core)) {
            return null;
         }

         props.addUnlockedAbility(core, AbilityUnlock.COMMAND);
      }

      T ability = core.createAbility();
      return ability;
   }

   private static ResourceLocation entityTexture(String texture) {
      return entityTexture("mineminenomi", texture);
   }

   public static ResourceLocation entityTexture(String modId, String texture) {
      return ResourceLocation.fromNamespaceAndPath(modId, "textures/models/" + texture + ".png");
   }

   private static ResourceLocation denMushiTexture(String texture) {
      return ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/blocks/" + texture + ".png");
   }

   public static void addBasicNPCGoals(PathfinderMob entity) {
      ((GroundPathNavigation)entity.m_21573_()).m_26477_(true);
      boolean isBoss = entity instanceof OPBossEntity;
      if (!isBoss) {
         entity.m_21573_().m_7008_(true);
         entity.f_21345_.m_25352_(0, new FloatGoal(entity));
         entity.f_21345_.m_25352_(2, new WaterAvoidingRandomStrollGoal(entity, 0.8));
      }

      entity.f_21345_.m_25352_(0, new OpenDoorGoal(entity, true));
      entity.f_21345_.m_25352_(3, new LookAtPlayerGoal(entity, Player.class, 8.0F));
      entity.f_21345_.m_25352_(3, new RandomLookAroundGoal(entity));
      Predicate<Entity> hurtByCheck = ModEntityPredicates.getEnemyFactions(entity).and(ModEntityPredicates.IS_ENTITY_HARMLESS.negate());
      Predicate<LivingEntity> hurtByLivingCheck = (target) -> ModEntityPredicates.getEnemyFactions(entity).and(ModEntityPredicates.IS_ENTITY_HARMLESS.negate()).test(target);
      Predicate<LivingEntity> basicHurtCheck = hurtByLivingCheck.and(ModEntityPredicates.IS_INVISIBLE.negate());
      Predicate<LivingEntity> targetMobCheck = basicHurtCheck.and(shouldAttackTarget(entity));
      Predicate<LivingEntity> targetPlayerCheck = targetMobCheck;
      IEntityStats props = (IEntityStats)EntityStatsCapability.get(entity).orElse((Object)null);
      if (props != null) {
         FactionHurtByTargetGoal hurtByTargetGoal = new FactionHurtByTargetGoal(entity, hurtByCheck, new Class[0]);
         if (props.getFaction().isPresent()) {
            hurtByTargetGoal.m_26044_(new Class[0]);
         }

         if (props.isMarine() || props.isBountyHunter()) {
            targetPlayerCheck = targetMobCheck.or(basicHurtCheck.and(ModEntityPredicates.TARGET_HAS_ISSUED_BOUNTY));
         }

         entity.f_21346_.m_25352_(1, hurtByTargetGoal);
         entity.f_21346_.m_25352_(2, new NearestAttackableTargetGoal(entity, Mob.class, 10, true, true, targetMobCheck));
         entity.f_21346_.m_25352_(2, new NearestAttackableTargetGoal(entity, Player.class, 10, true, true, targetPlayerCheck));
      }
   }

   public static Set<AbilityCore<?>> getBasicHakiAbilities(Mob entity, int chance) {
      Set<AbilityCore<?>> set = new HashSet();
      if ((double)chance > WyHelper.randomWithRange(0, 100)) {
         set.add((AbilityCore)BusoshokuHakiImbuingAbility.INSTANCE.get());
         if (entity.m_217043_().m_188503_(3) == 0) {
            set.add((AbilityCore)BusoshokuHakiFullBodyHardeningAbility.INSTANCE.get());
         } else {
            set.add((AbilityCore)BusoshokuHakiHardeningAbility.INSTANCE.get());
         }
      }

      return set;
   }

   public static Set<AbilityCore<?>> getAdvancedHakiAbilities(Mob entity, int chance) {
      Set<AbilityCore<?>> set = new HashSet();
      if ((double)chance > WyHelper.randomWithRange(0, 100)) {
         set.add((AbilityCore)BusoshokuHakiImbuingAbility.INSTANCE.get());
         if (entity.m_217043_().m_188503_(3) == 0) {
            set.add((AbilityCore)BusoshokuHakiEmissionAbility.INSTANCE.get());
         } else {
            set.add((AbilityCore)BusoshokuHakiInternalDestructionAbility.INSTANCE.get());
         }
      }

      return set;
   }

   public static boolean canBeTamed(LivingEntity target) {
      if (!(target instanceof TamableAnimal) && !(target instanceof AbstractHorse) && !(target instanceof Ocelot) && !(target instanceof AbstractDugongEntity)) {
         if (target instanceof Mob) {
            Mob mob = (Mob)target;
            boolean canReceiveCommands = CommandReceiverCapability.get(mob).orElse((Object)null) != null;
            if (canReceiveCommands) {
               return true;
            }
         }

         return false;
      } else {
         return true;
      }
   }

   public static boolean isTamedBy(LivingEntity entity, @Nullable LivingEntity tamer) {
      if (tamer == null) {
         return false;
      } else if (!canBeTamed(entity)) {
         return false;
      } else {
         if (entity instanceof Mob) {
            Mob mob = (Mob)entity;
            boolean canReceiveCommand = (Boolean)CommandReceiverCapability.get(mob).resolve().map((props) -> props.canReceiveCommandFrom(tamer)).orElse(false);
            if (canReceiveCommand) {
               return true;
            }
         }

         if (entity instanceof TamableAnimal) {
            TamableAnimal animal = (TamableAnimal)entity;
            return animal.m_269323_() != null && animal.m_269323_().equals(tamer);
         } else if (entity instanceof AbstractHorse) {
            AbstractHorse horse = (AbstractHorse)entity;
            return horse.m_21805_() != null && horse.m_21805_().equals(tamer.m_20148_());
         } else if (entity instanceof AbstractDugongEntity) {
            AbstractDugongEntity dugong = (AbstractDugongEntity)entity;
            return dugong.m_269323_() != null && dugong.m_269323_().equals(tamer);
         } else if (!(entity instanceof ITamableEntity)) {
            return false;
         } else {
            ITamableEntity tamableEntity = (ITamableEntity)entity;
            return tamableEntity.getOwnerIfAlive() != null && tamableEntity.getOwner().equals(tamer);
         }
      }
   }

   static {
      PIRATE_SWORDS = Lists.newArrayList(new Supplier[]{ModWeapons.CUTLASS, ModWeapons.KATANA, ModWeapons.AXE, () -> Items.f_42383_, () -> Items.f_42425_, () -> Items.f_42428_, () -> Items.f_42386_});
      MARINE_SWORDS = Lists.newArrayList(new Supplier[]{ModWeapons.BROADSWORD, ModWeapons.KATANA, ModWeapons.SPEAR, () -> Items.f_42383_, () -> Items.f_42425_});
      MARINE_CAPTAIN_SWORDS = createMarineCaptainWeaponsList();
      BANDIT_SWORDS = Lists.newArrayList(new Supplier[]{ModWeapons.DAGGER, ModWeapons.AXE, ModWeapons.KATANA, () -> Items.f_42428_, () -> Items.f_42386_});
      BRUTE_SWORDS = Lists.newArrayList(new Supplier[]{ModWeapons.MACE, ModWeapons.DALTONS_SPADE, ModWeapons.CLEAVER, () -> Items.f_42386_});
      GORILLA_AXES = Lists.newArrayList(new Supplier[]{ModWeapons.AXE, () -> Items.f_42386_, () -> Items.f_42433_, () -> Items.f_42428_, () -> Items.f_42391_});
      PIRATE_TEXTURES = new ResourceLocation[]{entityTexture("pirate6"), entityTexture("pirate7"), entityTexture("pirate8")};
      PIRATE_FISHMEN_TEXTURES = new ResourceLocation[]{entityTexture("fishman_pirate1"), entityTexture("fishman_pirate2"), entityTexture("fishman_pirate3")};
      PIRATE_CAPTAINS_TEXTURES = new ResourceLocation[]{entityTexture("pirate_captain1"), entityTexture("pirate_captain2"), entityTexture("pirate_captain3"), entityTexture("pirate_captain4"), entityTexture("pirate_captain5"), entityTexture("pirate_captain6"), entityTexture("pirate_captain7"), entityTexture("pirate_captain8")};
      PIRATE_TRADERS_TEXTURES = new ResourceLocation[]{entityTexture("pirate_trader1"), entityTexture("pirate_trader2")};
      MARINE_TEXTURES = new ResourceLocation[]{entityTexture("marine1"), entityTexture("marine2"), entityTexture("marine3"), entityTexture("marine4"), entityTexture("marine5")};
      MARINE_CAPTAINS_TEXTURES = new ResourceLocation[]{entityTexture("marine_captain1"), entityTexture("marine_captain2"), entityTexture("marine_captain3"), entityTexture("marine_captain4"), entityTexture("marine_captain5")};
      MARINE_TRADERS_TEXTURES = new ResourceLocation[]{entityTexture("marine_trader1"), entityTexture("marine_trader2")};
      MARINE_VICE_ADMIRAL_TEXTURES = new ResourceLocation[]{entityTexture("marine_vice_admiral1"), entityTexture("marine_vice_admiral2"), entityTexture("marine_vice_admiral3")};
      CELESTIAL_DRAGONS_TEXTURES = new ResourceLocation[]{entityTexture("celestial_dragon1"), entityTexture("celestial_dragon2")};
      BANDIT_TEXTURES = new ResourceLocation[]{entityTexture("bandit1"), entityTexture("bandit2"), entityTexture("bandit3")};
      SKYPEAN_TEXTURES = new ResourceLocation[]{entityTexture("skypiean_civilian1"), entityTexture("skypiean_civilian2"), entityTexture("skypiean_civilian3")};
      SKYPEAN_TRADERS_TEXTURES = new ResourceLocation[]{entityTexture("skypiean_trader1"), entityTexture("skypiean_trader2")};
      BARKEEPER_TEXTURES = new ResourceLocation[]{entityTexture("barkeeper1")};
      SWORDSMAN_TRAINER_TEXTURES = new ResourceLocation[]{entityTexture("swordsman_trainer1"), entityTexture("swordsman_trainer2"), entityTexture("swordsman_trainer3")};
      SNIPER_TRAINER_TEXTURES = new ResourceLocation[]{entityTexture("bow_master1"), entityTexture("bow_master2")};
      DOCTOR_TRAINER_TEXTURES = new ResourceLocation[]{entityTexture("doctor1"), entityTexture("doctor2")};
      BRAWLER_TRAINER_TEXTURES = new ResourceLocation[]{entityTexture("brawler_trainer1"), entityTexture("brawler_trainer2")};
      BLACK_LEG_TRAINERS_TEXTURES = new ResourceLocation[]{entityTexture("black_leg_trainer1"), entityTexture("black_leg_trainer2")};
      WEATHER_TRAINER_TEXTURES = new ResourceLocation[]{entityTexture("weather_wizard1")};
      DEN_DEN_MUSHI_TEXTURES = new ResourceLocation[]{denMushiTexture("den_den_mushi1"), denMushiTexture("den_den_mushi2"), denMushiTexture("den_den_mushi3")};
      YAGARA_BULL_TEXTURES = new ResourceLocation[]{entityTexture("yagara_bull1"), entityTexture("yagara_bull2"), entityTexture("yagara_bull3")};
      NIGHTMARE_SOLDIER_TEXTURES = (ResourceLocation[])WyHelper.concatAllArrays(PIRATE_TEXTURES, PIRATE_FISHMEN_TEXTURES, MARINE_TEXTURES, BANDIT_TEXTURES);
      DUGONG_HEAD_SCARS_TEXTURES = new ResourceLocation[]{entityTexture("dugong_scars/head_0"), entityTexture("dugong_scars/head_1"), entityTexture("dugong_scars/head_2"), entityTexture("dugong_scars/head_3"), entityTexture("dugong_scars/head_4")};
      DUGONG_CHEST_SCARS_TEXTURES = new ResourceLocation[]{entityTexture("dugong_scars/chest_0"), entityTexture("dugong_scars/chest_1"), entityTexture("dugong_scars/chest_2")};
      DUGONG_ARMS_SCARS_TEXTURES = new ResourceLocation[]{entityTexture("dugong_scars/arms_0"), entityTexture("dugong_scars/arms_1")};
      DUGONG_TAIL_SCARS_TEXTURES = new ResourceLocation[]{entityTexture("dugong_scars/tail_0"), entityTexture("dugong_scars/tail_1"), entityTexture("dugong_scars/tail_2")};
      ENTITY_THREAT = new Comparator<LivingEntity>() {
         public int compare(LivingEntity e1, LivingEntity e2) {
            double e1Threat = (Double)EntityStatsCapability.get(e1).map((props) -> props.getDoriki()).orElse((double)0.0F);
            double e2Threat = (Double)EntityStatsCapability.get(e2).map((props) -> props.getDoriki()).orElse((double)0.0F);
            return (int)(e1Threat - e2Threat);
         }
      };
      BELOW_90_HP_CHECK = (entity, ability) -> {
         float per = entity.m_21223_() / entity.m_21233_();
         return (double)per > 0.9 ? Result.fail((Component)null) : Result.success();
      };
   }
}
