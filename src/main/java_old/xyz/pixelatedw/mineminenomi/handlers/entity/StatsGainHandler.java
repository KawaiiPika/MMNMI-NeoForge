package xyz.pixelatedw.mineminenomi.handlers.entity;

import java.util.Optional;
import net.minecraft.util.Mth;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootTable;
import xyz.pixelatedw.mineminenomi.abilities.haki.HakiHelper;
import xyz.pixelatedw.mineminenomi.abilities.haki.KenbunshokuHakiAuraAbility;
import xyz.pixelatedw.mineminenomi.abilities.haki.KenbunshokuHakiFutureSightAbility;
import xyz.pixelatedw.mineminenomi.api.NuWorld;
import xyz.pixelatedw.mineminenomi.api.WyDebug;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.damagesources.AbilityDamageSource;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.api.enums.StatChangeSource;
import xyz.pixelatedw.mineminenomi.api.helpers.ItemsHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.MathHelper;
import xyz.pixelatedw.mineminenomi.api.poi.INotoriousTarget;
import xyz.pixelatedw.mineminenomi.commands.FGCommand;
import xyz.pixelatedw.mineminenomi.config.ServerConfig;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityCapability;
import xyz.pixelatedw.mineminenomi.data.entity.ability.IAbilityData;
import xyz.pixelatedw.mineminenomi.data.entity.haki.HakiCapability;
import xyz.pixelatedw.mineminenomi.data.entity.haki.IHakiData;
import xyz.pixelatedw.mineminenomi.data.entity.stats.EntityStatsCapability;
import xyz.pixelatedw.mineminenomi.data.entity.stats.IEntityStats;
import xyz.pixelatedw.mineminenomi.entities.mobs.NotoriousEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.abilities.BlackKnightEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.abilities.DoppelmanEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.abilities.MirageCloneEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.abilities.NightmareSoldierEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.abilities.WaxCloneEntity;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.packets.server.SSyncEntityStatsPacket;

public class StatsGainHandler {
   private static final double HEALTH_STAT_MAX = (double)750.0F;
   private static final double ATTACK_STAT_MAX = (double)40.0F;

   public static void setSpawnStats(Mob entity) {
      AttributeInstance attrAtk = entity.m_21204_().m_22146_(Attributes.f_22281_);
      AttributeInstance attrHP = entity.m_21204_().m_22146_(Attributes.f_22276_);
      if (attrAtk != null && attrHP != null) {
         boolean isHardDifficulty = entity.m_9236_().m_46791_().m_19028_() > Difficulty.NORMAL.m_19028_();
         boolean wasUpdated = false;
         double attackValue = attrAtk.m_22135_();
         double healthValue = attrHP.m_22135_();
         IEntityStats props = (IEntityStats)EntityStatsCapability.get(entity).orElse((Object)null);
         IHakiData hakiProps = (IHakiData)HakiCapability.get(entity).orElse((Object)null);
         if (props != null && props.getDoriki() <= (double)0.0F) {
            double dorikiLimit = (double)10000.0F;
            double dummyDoriki = healthValue / (double)750.0F * 0.3 + attackValue / (double)40.0F * 0.7 * dorikiLimit * 0.8;
            int randomExtraDoriki = (int)Math.round(dummyDoriki / (double)10.0F);
            if (randomExtraDoriki > 0) {
               dummyDoriki += (double)entity.m_217043_().m_188503_(randomExtraDoriki);
            }

            if (isHardDifficulty) {
               dummyDoriki += WyHelper.randomWithRange(250, 750);
            }

            dummyDoriki = Mth.m_14008_(dummyDoriki, (double)50.0F, dorikiLimit);
            long dummyBelly = (long)(dummyDoriki / (double)100.0F);
            int randomExtraBelly = Math.round((float)dummyBelly / 2.0F);
            if (randomExtraBelly > 0) {
               dummyBelly += (long)entity.m_217043_().m_188503_(randomExtraBelly);
            }

            dummyBelly = MathHelper.clamp(dummyBelly, 1L, 999999999L);
            props.setDoriki(dummyDoriki);
            props.setBelly(dummyBelly);
            wasUpdated = true;
         }

         if (hakiProps != null) {
            double hakiLimit = (double)100.0F;
            if (hakiProps.getKenbunshokuHakiExp() <= 0.0F) {
               double dummyObs = healthValue / (double)750.0F * 0.6 + attackValue / (double)40.0F * 0.4 * hakiLimit * 0.8;
               int randomExtraObs = (int)Math.round(dummyObs / (double)10.0F);
               if (randomExtraObs > 0) {
                  dummyObs += (double)entity.m_217043_().m_188503_(randomExtraObs);
               }

               if (isHardDifficulty) {
                  dummyObs += WyHelper.randomWithRange(5, 10);
               }

               dummyObs = Mth.m_14008_(dummyObs, (double)2.5F, hakiLimit);
               hakiProps.setKenbunshokuHakiExp((float)dummyObs);
               wasUpdated = true;
            }

            if (hakiProps.getBusoshokuHakiExp() <= 0.0F) {
               double dummyBuso = healthValue / (double)750.0F * 0.4 + attackValue / (double)40.0F * 0.6 * hakiLimit * 0.8;
               int randomExtraBuso = (int)Math.round(dummyBuso / (double)10.0F);
               if (randomExtraBuso > 0) {
                  dummyBuso += (double)entity.m_217043_().m_188503_(randomExtraBuso);
               }

               if (isHardDifficulty) {
                  dummyBuso += WyHelper.randomWithRange(5, 10);
               }

               dummyBuso = Mth.m_14008_(dummyBuso, (double)2.5F, hakiLimit);
               hakiProps.setBusoshokuHakiExp((float)dummyBuso);
               wasUpdated = true;
            }
         }

         if (wasUpdated && FGCommand.SHOW_DEBUG_STATS && props != null && hakiProps != null) {
            String var10000 = entity.m_7755_().getString();
            WyDebug.debug("\nTarget: " + var10000 + "\n Doriki: " + props.getDoriki() + "\n Belly: " + props.getBelly() + "\n Haki(B/K): " + hakiProps.getBusoshokuHakiExp() + "/" + hakiProps.getKenbunshokuHakiExp());
         }

      }
   }

   public static void givePassiveKenbunshokuExp(Player player) {
      IAbilityData abilityProps = (IAbilityData)AbilityCapability.get(player).orElse((Object)null);
      IHakiData hakiProps = (IHakiData)HakiCapability.get(player).orElse((Object)null);
      if (abilityProps != null && hakiProps != null) {
         KenbunshokuHakiFutureSightAbility ability = (KenbunshokuHakiFutureSightAbility)abilityProps.getEquippedAbility((AbilityCore)KenbunshokuHakiFutureSightAbility.INSTANCE.get());
         if (ability != null && ability.isContinuous() && hakiProps.getKenbunshokuHakiExp() >= 60.0F && player.f_19797_ % 600 == 0) {
            float finalHakiExp = (float)((double)0.025F * ServerConfig.getHakiExpMultiplier());
            hakiProps.alterKenbunshokuHakiExp(finalHakiExp, StatChangeSource.NATURAL);
         }

      }
   }

   public static void giveOnHitKenbunshokuExp(Player player, DamageSource source, float hitAmount) {
      Entity var4 = source.m_7640_();
      if (var4 instanceof NuProjectileEntity projectile) {
         if (!projectile.isPhysical()) {
            return;
         }
      }

      IAbilityData abilityProps = (IAbilityData)AbilityCapability.get(player).orElse((Object)null);
      IHakiData hakiProps = (IHakiData)HakiCapability.get(player).orElse((Object)null);
      if (abilityProps != null && hakiProps != null) {
         KenbunshokuHakiAuraAbility ability = (KenbunshokuHakiAuraAbility)abilityProps.getEquippedAbility((AbilityCore)KenbunshokuHakiAuraAbility.INSTANCE.get());
         if (ability != null && ability.isContinuous() || (double)hakiProps.getKenbunshokuHakiExp() <= (double)30.0F + HakiHelper.getKenbunshokuAuraExpNeeded(player)) {
            float exp = hitAmount / (20.0F + 100.0F * (hakiProps.getKenbunshokuHakiExp() / 100.0F));
            if (exp <= 0.0F) {
               exp = 1.0E-5F;
            }

            float finalHakiExp = exp * (float)ServerConfig.getHakiExpMultiplier();
            hakiProps.alterKenbunshokuHakiExp(finalHakiExp, StatChangeSource.NATURAL);
         }

      }
   }

   public static void giveBusoshokuExp(LivingEntity target, DamageSource damageSource) {
      Player player = null;
      boolean isHardeningDamage = false;
      boolean isImbuingDamage = false;
      Entity heldStack = damageSource.m_7639_();
      if (heldStack instanceof Player playerSource) {
         player = playerSource;
         ItemStack heldStack = playerSource.m_21205_();
         isHardeningDamage = heldStack.m_41619_();
         isImbuingDamage = ItemsHelper.isImbuable(heldStack);
      } else {
         heldStack = damageSource.m_7639_();
         if (heldStack instanceof NuProjectileEntity projSource) {
            LivingEntity var8 = projSource.getOwner();
            if (var8 instanceof Player projOwner) {
               if (projSource.isPhysical()) {
                  player = projOwner;
                  isHardeningDamage = projSource.isAffectedByHardening();
                  isImbuingDamage = projSource.isAffectedByImbuing();
               }
            }
         }
      }

      if (player != null) {
         IHakiData hakiProps = (IHakiData)HakiCapability.get(player).orElse((Object)null);
         if (hakiProps != null) {
            AttributeInstance attrAtk = target.m_21204_().m_22146_(Attributes.f_22281_);
            AttributeInstance attrHP = target.m_21204_().m_22146_(Attributes.f_22276_);
            double atk = attrAtk != null ? attrAtk.m_22115_() : (double)0.0F;
            double hp = attrHP != null ? attrHP.m_22115_() : (double)0.0F;
            float expValue = (float)(atk + hp);
            int mult = 2;
            boolean flag = false;
            if (damageSource instanceof AbilityDamageSource) {
               AbilityDamageSource ablSource = (AbilityDamageSource)damageSource;
               isHardeningDamage &= ablSource.getHakiNature() == SourceHakiNature.HARDENING;
               isImbuingDamage &= ablSource.getHakiNature() == SourceHakiNature.IMBUING;
            }

            if (isHardeningDamage || isImbuingDamage) {
               if (expValue < hakiProps.getBusoshokuHakiExp()) {
                  mult = 10;
               }

               boolean hasHardeningActive = HakiHelper.hasHardeningActive(player);
               boolean hasImbuingActive = HakiHelper.hasImbuingActive(player);
               if (hasHardeningActive || hasImbuingActive || (double)hakiProps.getBusoshokuHakiExp() <= (double)30.0F + HakiHelper.getBusoshokuHardeningExpNeeded(player)) {
                  flag = true;
               }
            }

            if (flag) {
               StatChangeSource statSource = target instanceof Player ? StatChangeSource.KILL_PLAYER : StatChangeSource.KILL_NPC;
               float currentHaki = hakiProps.getBusoshokuHakiExp();
               float finalExp = expValue * ((float)ServerConfig.getHakiExpLimit() - currentHaki) / (float)mult / 10000.0F;
               float exp = Math.max(0.001F, finalExp);
               float finalHakiExp = (float)((double)exp * ServerConfig.getHakiExpMultiplier());
               hakiProps.alterBusoshokuHakiExp(finalHakiExp, statSource);
            }

         }
      }
   }

   public static void increaseLoyaltyOverTime(Player player) {
      IEntityStats props = (IEntityStats)EntityStatsCapability.get(player).orElse((Object)null);
      if (props != null) {
         boolean hasLoyalty = (Boolean)props.getFaction().map((f) -> f.canReceiveLoyalty(player)).orElse(false);
         if (hasLoyalty && !(props.getLoyalty() >= (double)100.0F)) {
            if (!(props.getDoriki() < (double)4000.0F) || !(props.getLoyalty() > (double)15.0F)) {
               if (player.m_9236_().m_46467_() % 24000L == 0L) {
                  int loyaltyGain = Mth.m_14107_((double)1.0F * ServerConfig.getLoyaltyMultiplier());
                  props.alterLoyalty((double)loyaltyGain, StatChangeSource.NATURAL);
               }

            }
         }
      }
   }

   public static void giveStats(LivingEntity target, DamageSource damageSource) {
      if (!target.m_9236_().f_46443_) {
         if (ServerConfig.isMobRewardsEnabled()) {
            if (!NuWorld.isChallengeDimension(target.m_9236_())) {
               Player player = null;
               if (damageSource.m_7639_() instanceof Player) {
                  player = (Player)damageSource.m_7639_();
               } else if (damageSource instanceof AbilityDamageSource && damageSource.m_7640_() instanceof Player) {
                  player = (Player)damageSource.m_7640_();
               }

               if (player != null) {
                  IEntityStats props = (IEntityStats)EntityStatsCapability.get(player).orElse((Object)null);
                  if (props != null) {
                     long plusBelly = 0L;
                     long plusBounty = 0L;
                     double plusDoriki = (double)0.0F;
                     StatChangeSource statSource = StatChangeSource.KILL_NPC;
                     Optional<IEntityStats> targetprops = EntityStatsCapability.get(target);
                     boolean isTargetMarine = (Boolean)targetprops.map((data) -> data.isMarine()).orElse(false);
                     boolean isTargetBountyHunter = (Boolean)targetprops.map((data) -> data.isBountyHunter()).orElse(false);
                     boolean isTargetRevo = (Boolean)targetprops.map((data) -> data.isRevolutionary()).orElse(false);
                     double targetDoriki = (Double)targetprops.map((data) -> data.getDoriki()).orElse((double)0.0F);
                     long targetBounty = (Long)targetprops.map((data) -> data.getBounty()).orElse(0L);
                     long targetBelly = (Long)targetprops.map((data) -> data.getBelly()).orElse(0L);
                     if (props.isMarine()) {
                        if (!isTargetMarine && !isTargetBountyHunter) {
                           if (target instanceof INotoriousTarget) {
                              StatChangeSource loyaltySource = target instanceof Player ? StatChangeSource.KILL_PLAYER : StatChangeSource.KILL_NPC;
                              props.alterLoyalty((double)0.5F, loyaltySource);
                           }
                        } else {
                           StatChangeSource loyaltySource = target instanceof Player ? StatChangeSource.KILL_PLAYER : StatChangeSource.KILL_NPC;
                           props.alterLoyalty((double)-0.5F, loyaltySource);
                        }
                     }

                     if (target instanceof Player) {
                        double dorikiLost = targetDoriki - WyHelper.percentage((double)ServerConfig.getDorikiKeepPercentage(), targetDoriki);
                        plusDoriki = dorikiLost / (double)4.0F;
                        double bountyLost = (double)targetBounty - WyHelper.percentage((double)ServerConfig.getBountyKeepPercentage(), (double)targetBounty);
                        plusBounty = (long)(bountyLost / (double)2.0F);
                        double bellyLost = (double)targetBelly - WyHelper.percentage((double)ServerConfig.getBellyKeepPercentage(), (double)targetBelly);
                        plusBelly = (long)bellyLost;
                        statSource = StatChangeSource.KILL_PLAYER;
                     } else {
                        if (target instanceof DoppelmanEntity || target instanceof NightmareSoldierEntity || target instanceof MirageCloneEntity || target instanceof BlackKnightEntity || target instanceof WaxCloneEntity) {
                           return;
                        }

                        if (props.isMarine() && isTargetMarine) {
                           return;
                        }

                        if (props.isRevolutionary() && isTargetRevo) {
                           return;
                        }

                        LootTable loottable = target.m_9236_().m_7654_().m_278653_().m_278676_(target.m_5743_());
                        if (loottable.getPool("mineminenomi:stats") != null) {
                           return;
                        }

                        if (targetDoriki > (double)0.0F) {
                           plusDoriki = targetDoriki / (double)100.0F;
                           if (props.getDoriki() > targetDoriki) {
                              plusDoriki = targetDoriki / (double)10000.0F;
                           }

                           if (plusDoriki < (double)1.0F && ServerConfig.isMinimumDorikiPerKillEnabled()) {
                              plusDoriki = (double)1.0F;
                           }

                           plusBounty = (long)targetDoriki / 20L;
                           plusBelly = targetBelly;
                        }

                        if (target instanceof Villager) {
                           plusBounty = 250L;
                        } else if (target instanceof NotoriousEntity && plusDoriki < (double)100.0F) {
                           plusDoriki = (double)100.0F;
                        }

                        plusDoriki *= ServerConfig.getDorikiRewardMultiplier();
                        plusBounty = (long)((double)plusBounty * ServerConfig.getBountyRewardMultiplier());
                        plusBelly = (long)((double)plusBelly * ServerConfig.getBellyRewardMultiplier());
                        statSource = StatChangeSource.KILL_NPC;
                     }

                     if (FGCommand.SHOW_DEBUG_REWARDS) {
                        WyDebug.debug("Stats Gain\nDoriki: " + plusDoriki + "\nBelly: " + plusBelly + "\nBounty: " + plusBounty);
                     }

                     if (plusDoriki > (double)0.0F) {
                        props.alterDoriki(plusDoriki, statSource);
                     }

                     if (EntityStatsCapability.canReceiveBounty(player) && plusBounty > 0L) {
                        props.alterBounty(plusBounty, statSource);
                     }

                     if (plusBelly > 0L) {
                        props.alterBelly(plusBelly, statSource);
                     }

                     ModNetwork.sendTo(new SSyncEntityStatsPacket(player), player);
                  }
               }
            }
         }
      }
   }
}
