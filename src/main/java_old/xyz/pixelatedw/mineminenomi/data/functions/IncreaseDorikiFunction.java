package xyz.pixelatedw.mineminenomi.data.functions;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;
import xyz.pixelatedw.mineminenomi.api.challenges.Challenge;
import xyz.pixelatedw.mineminenomi.api.challenges.ChallengeCore;
import xyz.pixelatedw.mineminenomi.api.enums.StatChangeSource;
import xyz.pixelatedw.mineminenomi.config.ServerConfig;
import xyz.pixelatedw.mineminenomi.data.entity.challenge.ChallengeCapability;
import xyz.pixelatedw.mineminenomi.data.entity.challenge.IChallengeData;
import xyz.pixelatedw.mineminenomi.data.entity.stats.EntityStatsCapability;
import xyz.pixelatedw.mineminenomi.data.entity.stats.IEntityStats;
import xyz.pixelatedw.mineminenomi.init.ModLootTypes;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18n;
import xyz.pixelatedw.mineminenomi.packets.server.SSyncEntityStatsPacket;

public class IncreaseDorikiFunction extends IncreaseStatFunction {
   protected IncreaseDorikiFunction(LootItemCondition[] conditions, NumberProvider amount, StatChangeSource source) {
      super(conditions, amount, source);
   }

   protected ItemStack m_7372_(ItemStack stack, LootContext context) {
      Entity entity = (Entity)context.m_78953_(LootContextParams.f_81455_);
      if (entity != null && entity instanceof LivingEntity living) {
         Entity killer = (Entity)context.m_78953_(LootContextParams.f_81458_);
         int amount = this.amount.m_142683_(context);
         if (killer != null && killer instanceof LivingEntity killerLiving) {
            living = killerLiving;
            IEntityStats props = (IEntityStats)EntityStatsCapability.get(killerLiving).orElse((Object)null);
            if (this.source == StatChangeSource.KILL_NPC) {
               if (ServerConfig.isMobRewardsEnabled()) {
                  double doriki = (double)Mth.m_14045_(amount * 100, 0, ServerConfig.getDorikiLimit());
                  if (props != null && props.getDoriki() > doriki) {
                     amount /= 100;
                     if (amount < 1 && ServerConfig.isMinimumDorikiPerKillEnabled()) {
                        amount = 1;
                     }
                  }

                  amount = (int)((double)amount * ServerConfig.getDorikiRewardMultiplier());
               } else {
                  amount = 0;
               }
            }
         }

         if (this.source == StatChangeSource.CHALLENGE && entity instanceof Player player) {
            if (this.scaleDownChallengeCompletion) {
               ChallengeCore<?> core = (ChallengeCore)context.m_78953_(ModLootTypes.COMPLETED_CHALLENGE);
               IChallengeData challengeData = (IChallengeData)ChallengeCapability.get(player).orElse((Object)null);
               if (challengeData != null) {
                  Challenge challenge = challengeData.getChallenge(core);
                  amount = this.scaleValueFromCompletions(challenge, amount);
               }
            }
         }

         IEntityStats props = (IEntityStats)EntityStatsCapability.get(living).orElse((Object)null);
         if (props != null) {
            boolean hasUpdate = props.alterDoriki((double)amount, this.source);
            if (living instanceof ServerPlayer) {
               ServerPlayer player = (ServerPlayer)living;
               if (hasUpdate) {
                  ModNetwork.sendTo(new SSyncEntityStatsPacket(player), player);
               }
            }

            stack.m_41714_(ModI18n.REWARDS_FUNC_NAME);
            stack.m_41784_().m_128405_("_rewardsDoriki", amount);
         }
      }

      return stack;
   }

   public LootItemFunctionType m_7162_() {
      return (LootItemFunctionType)ModLootTypes.INCREASE_DORIKI.get();
   }

   public static LootItemConditionalFunction.Builder<?> builder(int amount, StatChangeSource source) {
      return builder(ConstantValue.m_165692_((float)amount), source);
   }

   public static LootItemConditionalFunction.Builder<?> builder(NumberProvider amount, StatChangeSource source) {
      return builder(amount, source, false);
   }

   public static LootItemConditionalFunction.Builder<?> builder(NumberProvider amount, StatChangeSource source, boolean scaleDown) {
      return m_80683_((condition) -> {
         IncreaseDorikiFunction func = new IncreaseDorikiFunction(condition, amount, source);
         func.scaleDownChallengeCompletion = scaleDown;
         return func;
      });
   }

   public static IncreaseDorikiFunction create(LootItemCondition[] conditions, NumberProvider amount, StatChangeSource source) {
      return new IncreaseDorikiFunction(conditions, amount, source);
   }
}
