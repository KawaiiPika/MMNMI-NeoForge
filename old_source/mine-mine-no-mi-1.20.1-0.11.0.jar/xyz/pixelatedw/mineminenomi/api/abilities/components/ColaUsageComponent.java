package xyz.pixelatedw.mineminenomi.api.abilities.components;

import java.util.UUID;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityStat;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.util.Result;
import xyz.pixelatedw.mineminenomi.data.entity.stats.EntityStatsCapability;
import xyz.pixelatedw.mineminenomi.data.entity.stats.IEntityStats;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18nAbilities;
import xyz.pixelatedw.mineminenomi.packets.server.entity.SUpdateColaAmountPacket;

public class ColaUsageComponent extends AbilityComponent<IAbility> {
   private static final UUID COLA_BONUS_MANAGER_UUID = UUID.fromString("6c6757f6-23e3-4af1-bc32-7c18acb37327");
   private final BonusManager colaBonusManager;

   public static Ability.ICanUseEvent hasEnoughCola(int colaNeeded) {
      return (entity, ability) -> {
         IEntityStats props = (IEntityStats)EntityStatsCapability.get(entity).orElse((Object)null);
         if (props == null) {
            return Result.fail((Component)null);
         } else {
            ColaUsageComponent comp = (ColaUsageComponent)ability.getComponent((AbilityComponentKey)ModAbilityComponents.COLA_USAGE.get()).orElse((Object)null);
            if (comp == null) {
               return Result.fail((Component)null);
            } else {
               int finalColaNeeded = Math.round(comp.colaBonusManager.applyBonus((float)colaNeeded));
               return props.getCola() - finalColaNeeded < 0 ? Result.fail(ModI18nAbilities.MESSAGE_NOT_ENOUGH_COLA) : Result.success();
            }
         }
      };
   }

   public static AbilityDescriptionLine.IDescriptionLine getColaTooltip(int cola) {
      return (e, ability) -> {
         ColaUsageComponent comp = (ColaUsageComponent)ability.getComponent((AbilityComponentKey)ModAbilityComponents.COLA_USAGE.get()).orElse((Object)null);
         int neededCola = cola;
         if (comp != null) {
            neededCola = Math.round(comp.colaBonusManager.applyBonus((float)cola));
         }

         AbilityStat.Builder statBuilder = new AbilityStat.Builder(ModI18nAbilities.DESCRIPTION_STAT_NAME_COLA, neededCola);
         return statBuilder.build().getStatDescription();
      };
   }

   public ColaUsageComponent(IAbility ability) {
      super((AbilityComponentKey)ModAbilityComponents.COLA_USAGE.get(), ability);
      this.colaBonusManager = new BonusManager(COLA_BONUS_MANAGER_UUID);
      this.addBonusManager(this.colaBonusManager);
   }

   public boolean consumeCola(LivingEntity entity, int cola) {
      if (cola <= 0) {
         return true;
      } else {
         IEntityStats props = (IEntityStats)EntityStatsCapability.get(entity).orElse((Object)null);
         if (props == null) {
            return false;
         } else {
            int colaNeeded = Math.round(this.colaBonusManager.applyBonus((float)cola));
            if (props.getCola() < colaNeeded) {
               return false;
            } else {
               props.alterCola(-colaNeeded);
               if (entity instanceof ServerPlayer) {
                  ServerPlayer player = (ServerPlayer)entity;
                  ModNetwork.sendTo(new SUpdateColaAmountPacket(entity), player);
               }

               return true;
            }
         }
      }
   }

   public BonusManager getColaBonusManager() {
      return this.colaBonusManager;
   }
}
