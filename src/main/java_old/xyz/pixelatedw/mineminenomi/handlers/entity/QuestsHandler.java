package xyz.pixelatedw.mineminenomi.handlers.entity;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.helpers.ItemsHelper;
import xyz.pixelatedw.mineminenomi.api.quests.objectives.ICollectItemObjective;
import xyz.pixelatedw.mineminenomi.api.quests.objectives.ICureEffectObjective;
import xyz.pixelatedw.mineminenomi.api.quests.objectives.IEntityInteractObjective;
import xyz.pixelatedw.mineminenomi.api.quests.objectives.IEquipItemObjective;
import xyz.pixelatedw.mineminenomi.api.quests.objectives.IHealEntityObjective;
import xyz.pixelatedw.mineminenomi.api.quests.objectives.IHitEntityObjective;
import xyz.pixelatedw.mineminenomi.api.quests.objectives.IKillEntityObjective;
import xyz.pixelatedw.mineminenomi.api.quests.objectives.IReachDorikiObjective;
import xyz.pixelatedw.mineminenomi.api.quests.objectives.ISurviveObjective;
import xyz.pixelatedw.mineminenomi.api.quests.objectives.IUseAbilityObjective;
import xyz.pixelatedw.mineminenomi.api.quests.objectives.IUseItemObjective;
import xyz.pixelatedw.mineminenomi.api.quests.objectives.Objective;
import xyz.pixelatedw.mineminenomi.data.entity.quest.IQuestData;
import xyz.pixelatedw.mineminenomi.data.entity.quest.QuestCapability;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.packets.server.SSyncQuestDataPacket;
import xyz.pixelatedw.mineminenomi.quests.objectives.TameEntityObjective;

public class QuestsHandler {
   public static void handleTameObjective(Player player) {
      IQuestData questProps = (IQuestData)QuestCapability.get(player).orElse((Object)null);
      if (questProps != null) {
         for(Objective obj : questProps.getInProgressObjectives()) {
            if (obj instanceof TameEntityObjective) {
               obj.alterProgress(player, 1.0F);
               ModNetwork.sendTo(new SSyncQuestDataPacket(player), player);
            }
         }

      }
   }

   public static void handleCureEffectObjective(Player player, MobEffectInstance effect) {
      IQuestData questProps = (IQuestData)QuestCapability.get(player).orElse((Object)null);
      if (questProps != null) {
         for(Objective obj : questProps.getInProgressObjectives()) {
            if (obj instanceof ICureEffectObjective) {
               ICureEffectObjective cureObjective = (ICureEffectObjective)obj;
               if (cureObjective.checkEffect(player, effect)) {
                  obj.alterProgress(player, 1.0F);
                  ModNetwork.sendTo(new SSyncQuestDataPacket(player), player);
               }
            }
         }

      }
   }

   public static void handleReachDorikiObjective(Player player, double doriki) {
      IQuestData questProps = (IQuestData)QuestCapability.get(player).orElse((Object)null);
      if (questProps != null) {
         for(Objective obj : questProps.getInProgressObjectives()) {
            if (obj instanceof IReachDorikiObjective) {
               IReachDorikiObjective dorikiObjective = (IReachDorikiObjective)obj;
               if (dorikiObjective.checkDoriki(player)) {
                  obj.setProgress(player, (float)doriki, true);
                  ModNetwork.sendTo(new SSyncQuestDataPacket(player), player);
               }
            }
         }

      }
   }

   public static void handleEntityInteractionObjective(Player player, LivingEntity target) {
      IQuestData questProps = (IQuestData)QuestCapability.get(player).orElse((Object)null);
      if (questProps != null) {
         for(Objective obj : questProps.getInProgressObjectives()) {
            if (obj instanceof IEntityInteractObjective) {
               IEntityInteractObjective interactObjective = (IEntityInteractObjective)obj;
               if (interactObjective.checkInteraction(player, target)) {
                  obj.alterProgress(player, 1.0F);
                  ModNetwork.sendTo(new SSyncQuestDataPacket(player), player);
                  return;
               }
            }
         }

      }
   }

   public static void handleRemainingCollectedItems(Player player) {
      IQuestData questProps = (IQuestData)QuestCapability.get(player).orElse((Object)null);
      if (questProps != null) {
         for(Objective obj : questProps.getInProgressObjectives()) {
            if (obj instanceof ICollectItemObjective) {
               ICollectItemObjective collectObjective = (ICollectItemObjective)obj;
               if (obj.getProgress() > 0.0F) {
                  boolean noItemFound = true;

                  for(ItemStack stack : ItemsHelper.getAllInventoryItems(player)) {
                     if (!stack.m_41619_() && collectObjective.checkItem(stack)) {
                        obj.setProgress(player, (float)stack.m_41613_(), true);
                        ModNetwork.sendTo(new SSyncQuestDataPacket(player), player);
                        noItemFound = false;
                     }
                  }

                  if (noItemFound) {
                     obj.setProgress(player, 0.0F, true);
                     ModNetwork.sendTo(new SSyncQuestDataPacket(player), player);
                  }
               }
            }
         }

      }
   }

   public static void handleCollectItemObjective(Player player, ItemStack stack, int count) {
      IQuestData questProps = (IQuestData)QuestCapability.get(player).orElse((Object)null);
      if (questProps != null) {
         for(Objective obj : questProps.getInProgressObjectives()) {
            if (obj instanceof ICollectItemObjective) {
               ICollectItemObjective collectItemObjective = (ICollectItemObjective)obj;
               if (collectItemObjective.checkItem(stack)) {
                  obj.alterProgress(player, (float)count, true);
                  ModNetwork.sendTo(new SSyncQuestDataPacket(player), player);
               }
            }
         }

      }
   }

   public static void handleHealObjective(Player player, LivingEntity target, float healAmount) {
      IQuestData questProps = (IQuestData)QuestCapability.get(player).orElse((Object)null);
      if (questProps != null) {
         for(Objective obj : questProps.getInProgressObjectives()) {
            if (obj instanceof IHealEntityObjective) {
               IHealEntityObjective healObjective = (IHealEntityObjective)obj;
               if (healObjective.checkHeal(player, target, healAmount)) {
                  obj.alterProgress(player, 1.0F);
                  ModNetwork.sendTo(new SSyncQuestDataPacket(player), player);
               }
            }
         }

      }
   }

   public static void handleHitObjective(LivingEntity attacker, LivingEntity target, DamageSource damageSource, float damageAmount) {
      if (attacker instanceof Player player) {
         IQuestData questProps = (IQuestData)QuestCapability.get(player).orElse((Object)null);
         if (questProps != null) {
            for(Objective obj : questProps.getInProgressObjectives()) {
               if (obj instanceof IHitEntityObjective) {
                  IHitEntityObjective hitObjective = (IHitEntityObjective)obj;
                  if (hitObjective.checkHit(player, target, damageSource, damageAmount)) {
                     obj.alterProgress(player, 1.0F);
                     ModNetwork.sendTo(new SSyncQuestDataPacket(player), player);
                  }
               }
            }

         }
      }
   }

   public static void handleKillObjective(LivingEntity target, DamageSource damageSource) {
      Entity attacker = damageSource.m_7639_();
      if (attacker instanceof Player player) {
         IQuestData questProps = (IQuestData)QuestCapability.get(player).orElse((Object)null);
         if (questProps != null) {
            for(Objective obj : questProps.getInProgressObjectives()) {
               if (obj instanceof IKillEntityObjective) {
                  IKillEntityObjective killObjective = (IKillEntityObjective)obj;
                  if (killObjective.checkKill(player, target, damageSource)) {
                     obj.alterProgress(player, 1.0F);
                     ModNetwork.sendTo(new SSyncQuestDataPacket(player), player);
                  }
               }
            }

         }
      }
   }

   public static void handleEquipmentObjective(Player player, EquipmentSlot slot, ItemStack toStack) {
      IQuestData questProps = (IQuestData)QuestCapability.get(player).orElse((Object)null);
      if (questProps != null) {
         for(Objective obj : questProps.getInProgressObjectives()) {
            if (obj instanceof IEquipItemObjective) {
               IEquipItemObjective equipObjective = (IEquipItemObjective)obj;
               if (equipObjective.checkSlot(slot)) {
                  if (equipObjective.checkEquippedItem(player, toStack)) {
                     obj.alterProgress(player, 1.0F);
                     ModNetwork.sendTo(new SSyncQuestDataPacket(player), player);
                  } else {
                     obj.alterProgress(player, -1.0F, true);
                     ModNetwork.sendTo(new SSyncQuestDataPacket(player), player);
                  }
               }
            }
         }

      }
   }

   public static void handleSurvivalObjective(Player player) {
      IQuestData questProps = (IQuestData)QuestCapability.get(player).orElse((Object)null);
      if (questProps != null) {
         for(Objective obj : questProps.getInProgressObjectives()) {
            if (obj instanceof ISurviveObjective) {
               ISurviveObjective surviveObjective = (ISurviveObjective)obj;
               if (surviveObjective.checkTime(player)) {
                  obj.alterProgress(player, 1.0F);
                  ModNetwork.sendTo(new SSyncQuestDataPacket(player), player);
               }
            }
         }

      }
   }

   public static void handleUseItemObjective(Player player, ItemStack stack, int useDuration) {
      IQuestData questProps = (IQuestData)QuestCapability.get(player).orElse((Object)null);
      if (questProps != null) {
         for(Objective obj : questProps.getInProgressObjectives()) {
            if (obj instanceof IUseItemObjective) {
               IUseItemObjective useItemObjective = (IUseItemObjective)obj;
               if (useItemObjective.checkItem(player, stack, useDuration)) {
                  obj.alterProgress(player, 1.0F);
                  ModNetwork.sendTo(new SSyncQuestDataPacket(player), player);
               }
            }
         }

      }
   }

   public static void handleUseAbilityObjective(Player player, IAbility ability) {
      IQuestData questProps = (IQuestData)QuestCapability.get(player).orElse((Object)null);
      if (questProps != null) {
         for(Objective obj : questProps.getInProgressObjectives()) {
            if (obj instanceof IUseAbilityObjective) {
               IUseAbilityObjective useAbilityObjective = (IUseAbilityObjective)obj;
               if (useAbilityObjective.checkAbility(player, ability)) {
                  obj.alterProgress(player, 1.0F);
                  ModNetwork.sendTo(new SSyncQuestDataPacket(player), player);
               }
            }
         }

      }
   }
}
