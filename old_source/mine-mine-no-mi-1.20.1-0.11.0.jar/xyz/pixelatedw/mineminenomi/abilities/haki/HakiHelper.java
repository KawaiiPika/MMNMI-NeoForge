package xyz.pixelatedw.mineminenomi.abilities.haki;

import java.awt.Color;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityAttributeModifier;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponentKey;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.enums.HakiRank;
import xyz.pixelatedw.mineminenomi.api.enums.HakiType;
import xyz.pixelatedw.mineminenomi.api.helpers.ItemsHelper;
import xyz.pixelatedw.mineminenomi.config.ClientConfig;
import xyz.pixelatedw.mineminenomi.config.ServerConfig;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityCapability;
import xyz.pixelatedw.mineminenomi.data.entity.ability.IAbilityData;
import xyz.pixelatedw.mineminenomi.data.entity.haki.HakiCapability;
import xyz.pixelatedw.mineminenomi.data.entity.haki.IHakiData;
import xyz.pixelatedw.mineminenomi.data.entity.stats.EntityStatsCapability;
import xyz.pixelatedw.mineminenomi.entities.mobs.OPBossEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.OPEntity;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;

public class HakiHelper {
   public static final String IMBUING_TAG = "imbuingHakiActive";
   private static final UUID ADVANCED_BUSOSHOKU_HAKI_REACH_BONUS_UUID = UUID.fromString("9e7fec38-e291-49e0-9ba7-10b18c87a6a4");
   public static final UUID ADVANCED_BUSOSHOKU_HAKI_STRENGTH_BONUS_UUID = UUID.fromString("cf88c7dd-fa1d-4b36-a4de-4268efae6378");
   public static final UUID BASIC_BUSOSHOKU_HAKI_STRENGTH_BONUS_UUID = UUID.fromString("0150e464-35c2-4cf0-a510-fd572c55d1d7");

   @OnlyIn(Dist.CLIENT)
   public static int getConfigHaoshokuColour(LivingEntity player) {
      if (ServerConfig.isHaoColoringCustom()) {
         return ClientConfig.getHakiColor().getRGB();
      } else if (ServerConfig.isHaoColoringRandom()) {
         if (player == null) {
            return 16711680;
         } else {
            Random rand = new Random(player.m_20148_().getMostSignificantBits());
            int r = (int)WyHelper.randomWithRange((Random)rand, 0, 255);
            int g = (int)WyHelper.randomWithRange((Random)rand, 0, 255);
            int b = (int)WyHelper.randomWithRange((Random)rand, 0, 255);
            Color c = new Color(r, g, b);
            return c.getRGB();
         }
      } else {
         return 16711680;
      }
   }

   public static int getHaoshokuColour(LivingEntity player) {
      return player == null ? 16711680 : (Integer)HakiCapability.get(player).map((props) -> props.getHaoshokuHakiColour()).orElse(16711680);
   }

   public static double getBasicBusoshokuHakiDamageBoost(LivingEntity entity, float originalAmount) {
      double doriki = (Double)EntityStatsCapability.get(entity).map((props) -> props.getDoriki()).orElse((double)0.0F);
      float busoHaki = (Float)HakiCapability.get(entity).map((props) -> props.getBusoshokuHakiExp()).orElse(0.0F);
      double dorikiMultiplier = doriki / (double)ServerConfig.getDorikiLimit();
      float hakiMultiplier = busoHaki / (float)ServerConfig.getHakiExpLimit();
      return (double)originalAmount * 0.35 * (0.2 * dorikiMultiplier + 0.8 * (double)hakiMultiplier);
   }

   public static double getAdvancedBusoshokuHakiDamageBoost(LivingEntity entity, float originalAmount) {
      double doriki = (Double)EntityStatsCapability.get(entity).map((props) -> props.getDoriki()).orElse((double)0.0F);
      float busoHaki = (Float)HakiCapability.get(entity).map((props) -> props.getBusoshokuHakiExp()).orElse(0.0F);
      double dorikiMultiplier = doriki / (double)ServerConfig.getDorikiLimit();
      float hakiMultiplier = busoHaki / (float)ServerConfig.getHakiExpLimit();
      return (double)2.0F + (double)originalAmount * 0.45 * (0.2 * dorikiMultiplier + 0.8 * (double)hakiMultiplier);
   }

   /** @deprecated */
   @Deprecated
   public static AbilityAttributeModifier getAdvancedBusoshokuHakiAttackReachBonus(AbilityCore<?> core, double amount) {
      return new AbilityAttributeModifier(ADVANCED_BUSOSHOKU_HAKI_REACH_BONUS_UUID, core, "Advanced Busoshoku Haki Attack Reach Modifier", amount, Operation.ADDITION);
   }

   /** @deprecated */
   @Deprecated
   public static AbilityAttributeModifier getAdvancedBusoshokuHakiAttackDamageBonus(AbilityCore<?> core, double amount) {
      return new AbilityAttributeModifier(ADVANCED_BUSOSHOKU_HAKI_STRENGTH_BONUS_UUID, core, "Advanced Busoshoku Haki Strength Attack Modifier", amount, Operation.ADDITION);
   }

   /** @deprecated */
   @Deprecated
   public static AbilityAttributeModifier getBasicBusoshokuHakiAttackDamageBonus(AbilityCore<?> core, double amount) {
      return new AbilityAttributeModifier(BASIC_BUSOSHOKU_HAKI_STRENGTH_BONUS_UUID, core, "Basic Busoshoku Haki Strength Attack Modifier", amount, Operation.ADDITION);
   }

   public static boolean isHaoshokuBorn(Player player) {
      if (!ServerConfig.isHaoshokuUnlockLogicChanceBased()) {
         return false;
      } else {
         boolean isKing = false;
         String[] bits = ("" + player.m_20148_().getMostSignificantBits()).split("");
         int playerBitsSum = 0;

         for(String bit : bits) {
            if (!bit.equalsIgnoreCase("-")) {
               playerBitsSum += Integer.parseInt(bit);
            }
         }

         playerBitsSum = Mth.m_14045_(playerBitsSum & 10, 0, 10);
         if (ServerConfig.getHaoshokuUnlockLogic() == ServerConfig.HaoshokuUnlockLogic.TRUE_RANDOM) {
            String[] seedBits = String.valueOf(((ServerLevel)player.m_9236_()).m_7328_()).split("");
            int worldBitsSum = 0;

            for(String bit : seedBits) {
               if (!bit.equalsIgnoreCase("-")) {
                  worldBitsSum += Integer.parseInt(bit);
               }
            }

            worldBitsSum = Mth.m_14045_(worldBitsSum & 10, 0, 10);
            isKing = playerBitsSum == worldBitsSum;
         } else if (ServerConfig.getHaoshokuUnlockLogic() == ServerConfig.HaoshokuUnlockLogic.RANDOM || ServerConfig.getHaoshokuUnlockLogic() == ServerConfig.HaoshokuUnlockLogic.COMBINED) {
            isKing = playerBitsSum <= 1;
         }

         return isKing;
      }
   }

   public static double getBusoshokuHardeningExpNeeded(LivingEntity user) {
      RandomSource rand = user.m_217043_();
      rand.m_188584_(user.m_20148_().getMostSignificantBits());
      return (double)30.0F + WyHelper.randomWithRange((RandomSource)rand, -2, 25);
   }

   public static double getBusoshokuFullBodyExpNeeded(LivingEntity user) {
      RandomSource rand = user.m_217043_();
      rand.m_188584_(user.m_20148_().getMostSignificantBits());
      return (double)50.0F + WyHelper.randomWithRange((RandomSource)rand, 0, 20);
   }

   public static double getBusoshokuEmissionExpNeeded(LivingEntity user) {
      RandomSource rand = user.m_217043_();
      rand.m_188584_(user.m_20148_().getMostSignificantBits());
      return (double)70.0F + WyHelper.randomWithRange((RandomSource)rand, 0, 10);
   }

   public static double getBusoshokuInternalDestructionExpNeeded(LivingEntity user) {
      RandomSource rand = user.m_217043_();
      rand.m_188584_(user.m_20148_().getMostSignificantBits());
      return (double)80.0F + WyHelper.randomWithRange((RandomSource)rand, 0, 15);
   }

   public static double getKenbunshokuAuraExpNeeded(LivingEntity user) {
      RandomSource rand = user.m_217043_();
      rand.m_188584_(user.m_20148_().getMostSignificantBits());
      return (double)30.0F + WyHelper.randomWithRange((RandomSource)rand, -5, 20);
   }

   public static double getKenbunshokuFutureSightExpNeeded(LivingEntity user) {
      RandomSource rand = user.m_217043_();
      rand.m_188584_(user.m_20148_().getMostSignificantBits());
      return (double)50.0F + WyHelper.randomWithRange((RandomSource)rand, 0, 30);
   }

   public static HakiRank getHakiRank(HakiType type, LivingEntity entity) {
      HakiRank rank = HakiRank.values()[0];
      IAbilityData abilityData = (IAbilityData)AbilityCapability.get(entity).orElse((Object)null);
      if (abilityData == null) {
         return rank;
      } else {
         IHakiData hakiData = (IHakiData)HakiCapability.get(entity).orElse((Object)null);
         if (hakiData == null) {
            return rank;
         } else {
            float exp = 0.0F;
            boolean check = false;
            if (type == HakiType.BUSOSHOKU) {
               exp = hakiData.getBusoshokuHakiExp();
               check = abilityData.hasUnlockedAbility((AbilityCore)BusoshokuHakiHardeningAbility.INSTANCE.get()) || abilityData.hasUnlockedAbility((AbilityCore)BusoshokuHakiImbuingAbility.INSTANCE.get());
            } else if (type == HakiType.KENBUNSHOKU) {
               exp = hakiData.getKenbunshokuHakiExp();
               check = abilityData.hasUnlockedAbility((AbilityCore)KenbunshokuHakiAuraAbility.INSTANCE.get());
            }

            float per = exp / (float)ServerConfig.getHakiExpLimit() * 100.0F;
            if (per >= 0.0F && per < 15.0F) {
               rank = HakiRank.BEGINNER;
            } else if (per >= 15.0F && check) {
               if (per >= 15.0F && per < 30.0F) {
                  rank = HakiRank.INITIATE;
               } else if (per >= 30.0F && per < 50.0F) {
                  rank = HakiRank.ADEPT;
               } else if (per >= 50.0F && per < 90.0F) {
                  rank = HakiRank.PROFICIENT;
               } else if (per >= 90.0F) {
                  rank = HakiRank.MASTER;
               }
            }

            return rank;
         }
      }
   }

   public static boolean hasAnyHakiEnabled(LivingEntity entity) {
      if (entity == null) {
         return false;
      } else {
         IAbilityData props = (IAbilityData)AbilityCapability.get(entity).orElse((Object)null);
         if (props == null) {
            return false;
         } else {
            boolean hasAnyHaki = false;

            for(IAbility abl : props.getEquippedAbilities(AbilityCategory.HAKI.isAbilityPartofCategory())) {
               if (abl != null) {
                  Optional<ContinuousComponent> comp = abl.<ContinuousComponent>getComponent((AbilityComponentKey)ModAbilityComponents.CONTINUOUS.get());
                  if (comp.isPresent() && ((ContinuousComponent)comp.get()).isContinuous()) {
                     hasAnyHaki = true;
                  }
               }
            }

            return hasAnyHaki;
         }
      }
   }

   public static boolean hasAdvancedBusoActive(LivingEntity entity) {
      return hasBusoEmissionActive(entity) || hasBusoInternalDestructionActive(entity);
   }

   public static boolean hasBusoFullbodyActive(LivingEntity entity) {
      if (entity == null) {
         return false;
      } else {
         boolean isFullbodyActive = (Boolean)AbilityCapability.get(entity).map((props) -> (BusoshokuHakiFullBodyHardeningAbility)props.getEquippedAbility((AbilityCore)BusoshokuHakiFullBodyHardeningAbility.INSTANCE.get())).map((abl) -> abl != null && abl.isContinuous()).orElse(false);
         return isFullbodyActive;
      }
   }

   public static boolean hasBusoEmissionActive(LivingEntity entity) {
      if (entity == null) {
         return false;
      } else {
         boolean isEmissionActive = (Boolean)AbilityCapability.get(entity).map((props) -> (BusoshokuHakiEmissionAbility)props.getEquippedAbility((AbilityCore)BusoshokuHakiEmissionAbility.INSTANCE.get())).map((abl) -> abl != null && abl.isContinuous()).orElse(false);
         return isEmissionActive;
      }
   }

   public static boolean hasBusoInternalDestructionActive(LivingEntity entity) {
      if (entity == null) {
         return false;
      } else {
         boolean isInternalDestructionHaki = (Boolean)AbilityCapability.get(entity).map((props) -> (BusoshokuHakiInternalDestructionAbility)props.getEquippedAbility((AbilityCore)BusoshokuHakiInternalDestructionAbility.INSTANCE.get())).map((abl) -> abl != null && abl.isContinuous()).orElse(false);
         return isInternalDestructionHaki;
      }
   }

   public static boolean hasInfusionActive(LivingEntity entity) {
      if (entity == null) {
         return false;
      } else {
         boolean isInfusion = (Boolean)AbilityCapability.get(entity).map((props) -> (HaoshokuHakiInfusionAbility)props.getEquippedAbility((AbilityCore)HaoshokuHakiInfusionAbility.INSTANCE.get())).map((abl) -> abl != null && abl.isContinuous()).orElse(false);
         return isInfusion;
      }
   }

   public static boolean hasHardeningActive(LivingEntity entity) {
      return hasHardeningActive(entity, true, true);
   }

   public static boolean hasHardeningActive(LivingEntity entity, boolean includeInfusion, boolean includeAdvancedBuso) {
      if (entity == null) {
         return false;
      } else {
         IAbilityData attackerAbilityProps = (IAbilityData)AbilityCapability.get(entity).orElse((Object)null);
         if (attackerAbilityProps == null) {
            return false;
         } else {
            boolean hasHakiAbility = false;
            BusoshokuHakiHardeningAbility hardeningHaki = (BusoshokuHakiHardeningAbility)attackerAbilityProps.getEquippedAbility((AbilityCore)BusoshokuHakiHardeningAbility.INSTANCE.get());
            boolean hasHardeningBusoHakiActive = hardeningHaki != null && hardeningHaki.isContinuous();
            hasHakiAbility |= hardeningHaki != null;
            if (hasHardeningBusoHakiActive) {
               return true;
            } else {
               BusoshokuHakiFullBodyHardeningAbility fullbodyHaki = (BusoshokuHakiFullBodyHardeningAbility)attackerAbilityProps.getEquippedAbility((AbilityCore)BusoshokuHakiFullBodyHardeningAbility.INSTANCE.get());
               boolean hasFullbodyBusoHakiActive = fullbodyHaki != null && fullbodyHaki.isContinuous();
               hasHakiAbility |= fullbodyHaki != null;
               if (hasFullbodyBusoHakiActive) {
                  return true;
               } else {
                  if (includeInfusion) {
                     HaoshokuHakiInfusionAbility infusionHaki = (HaoshokuHakiInfusionAbility)attackerAbilityProps.getEquippedAbility((AbilityCore)HaoshokuHakiInfusionAbility.INSTANCE.get());
                     boolean hasInfusionActive = infusionHaki != null && infusionHaki.isContinuous();
                     hasHakiAbility |= infusionHaki != null;
                     if (hasInfusionActive) {
                        return true;
                     }
                  }

                  if (includeAdvancedBuso) {
                     BusoshokuHakiEmissionAbility emissionHaki = (BusoshokuHakiEmissionAbility)attackerAbilityProps.getEquippedAbility((AbilityCore)BusoshokuHakiEmissionAbility.INSTANCE.get());
                     boolean hasEmissionHakiActive = emissionHaki != null && emissionHaki.isContinuous();
                     hasHakiAbility |= emissionHaki != null;
                     if (hasEmissionHakiActive) {
                        return true;
                     }

                     BusoshokuHakiInternalDestructionAbility idHaki = (BusoshokuHakiInternalDestructionAbility)attackerAbilityProps.getEquippedAbility((AbilityCore)BusoshokuHakiInternalDestructionAbility.INSTANCE.get());
                     boolean hasIDHakiActive = idHaki != null && idHaki.isContinuous();
                     hasHakiAbility |= idHaki != null;
                     if (hasIDHakiActive) {
                        return true;
                     }
                  }

                  return entity instanceof Mob && !(entity instanceof OPEntity) && !(entity instanceof OPBossEntity) && !hasHakiAbility && hasNativeHaki(entity);
               }
            }
         }
      }
   }

   public static boolean hasImbuingActive(LivingEntity entity) {
      return hasImbuingActive(entity, true, true);
   }

   public static boolean hasImbuingActive(LivingEntity entity, boolean includeInfusion, boolean includeAdvancedBuso) {
      if (entity == null) {
         return false;
      } else {
         IAbilityData attackerAbilityProps = (IAbilityData)AbilityCapability.get(entity).orElse((Object)null);
         if (attackerAbilityProps == null) {
            return false;
         } else {
            boolean hasHakiAbility = false;
            BusoshokuHakiImbuingAbility imbuingHaki = (BusoshokuHakiImbuingAbility)attackerAbilityProps.getEquippedAbility((AbilityCore)BusoshokuHakiImbuingAbility.INSTANCE.get());
            boolean hasImbuingBusoHakiActive = imbuingHaki != null && imbuingHaki.isContinuous();
            hasHakiAbility |= imbuingHaki != null;
            if (hasImbuingBusoHakiActive) {
               return true;
            } else {
               if (includeInfusion) {
                  HaoshokuHakiInfusionAbility infusionHaki = (HaoshokuHakiInfusionAbility)attackerAbilityProps.getEquippedAbility((AbilityCore)HaoshokuHakiInfusionAbility.INSTANCE.get());
                  boolean hasInfusionActive = infusionHaki != null && infusionHaki.isContinuous();
                  hasHakiAbility |= infusionHaki != null;
                  if (hasInfusionActive) {
                     return true;
                  }
               }

               if (includeAdvancedBuso) {
                  BusoshokuHakiEmissionAbility emissionHaki = (BusoshokuHakiEmissionAbility)attackerAbilityProps.getEquippedAbility((AbilityCore)BusoshokuHakiEmissionAbility.INSTANCE.get());
                  boolean hasEmissionHakiActive = emissionHaki != null && emissionHaki.isContinuous();
                  hasHakiAbility |= emissionHaki != null;
                  if (hasEmissionHakiActive) {
                     return true;
                  }

                  BusoshokuHakiInternalDestructionAbility idHaki = (BusoshokuHakiInternalDestructionAbility)attackerAbilityProps.getEquippedAbility((AbilityCore)BusoshokuHakiInternalDestructionAbility.INSTANCE.get());
                  boolean hasIDHakiActive = idHaki != null && idHaki.isContinuous();
                  hasHakiAbility |= idHaki != null;
                  if (hasIDHakiActive) {
                     return true;
                  }
               }

               if (entity instanceof Mob && !(entity instanceof OPEntity) && !(entity instanceof OPBossEntity)) {
                  if (entity.m_21205_() == null || entity.m_21205_().m_41619_() || !ItemsHelper.isImbuable(entity.m_21205_())) {
                     return false;
                  }

                  if (!hasHakiAbility && hasNativeHaki(entity)) {
                     return true;
                  }
               }

               return false;
            }
         }
      }
   }

   public static boolean hasNativeHaki(LivingEntity entity) {
      AttributeInstance attrAtk = entity.m_21204_().m_22146_(Attributes.f_22281_);
      double atk = attrAtk != null ? attrAtk.m_22115_() : (double)0.0F;
      boolean hasDoriki = (Boolean)EntityStatsCapability.get(entity).map((props) -> props.getDoriki() >= (double)5000.0F).orElse(false);
      boolean hasPseudoHaki = atk >= (double)(entity.m_9236_().m_46791_() == Difficulty.HARD ? 5 : 7) && ServerConfig.isNativeHakiEnabled();
      return hasPseudoHaki || hasDoriki;
   }

   public static boolean checkForHakiOveruse(LivingEntity entity, int overuseBonus) {
      if (entity.m_9236_().f_46443_) {
         return false;
      } else {
         HakiCapability.get(entity).ifPresent((props) -> {
            if (overuseBonus != -1) {
               props.alterHakiOveruse(1 + overuseBonus);
            }

         });
         return !canEnableHaki(entity);
      }
   }

   public static boolean canEnableHaki(LivingEntity entity) {
      IHakiData props = (IHakiData)HakiCapability.get(entity).orElse((Object)null);
      if (props == null) {
         return false;
      } else {
         return props.getHakiOveruse() < props.getMaxOveruse();
      }
   }
}
