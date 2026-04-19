package xyz.pixelatedw.mineminenomi.api.challenges;

import com.google.common.collect.Lists;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraftforge.registries.IForgeRegistry;
import xyz.pixelatedw.mineminenomi.ModMain;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.WyRegistry;
import xyz.pixelatedw.mineminenomi.api.enums.StatChangeSource;
import xyz.pixelatedw.mineminenomi.config.ServerConfig;
import xyz.pixelatedw.mineminenomi.data.entity.haki.HakiCapability;
import xyz.pixelatedw.mineminenomi.data.entity.haki.IHakiData;
import xyz.pixelatedw.mineminenomi.data.entity.stats.EntityStatsCapability;
import xyz.pixelatedw.mineminenomi.data.entity.stats.IEntityStats;
import xyz.pixelatedw.mineminenomi.init.ModAdvancements;
import xyz.pixelatedw.mineminenomi.init.ModLootTypes;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.packets.server.SSyncEntityStatsPacket;

public abstract class Challenge {
   private final ChallengeCore<?> core;
   private int completions;
   private int bestTime;

   public Challenge(ChallengeCore<?> core) {
      this.core = core;
   }

   public String getCategory() {
      return this.core.getCategory();
   }

   public void resetBestTime() {
      this.bestTime = 0;
   }

   public void tryUpdateBestTime(int time) {
      if (this.getBestTimeTick() == 0) {
         this.bestTime = time;
      } else {
         if (this.isPersonalBest(time)) {
            this.bestTime = time;
         }

      }
   }

   public boolean isPersonalBest(int time) {
      int pb = this.getBestTimeTick();
      return pb > 0 && time < pb;
   }

   public int getBestTimeTick() {
      return this.bestTime;
   }

   public String getFormattedBestTime() {
      return WyHelper.formatTimeMMSS((long)this.bestTime);
   }

   public void setComplete(Player player, boolean isComplete) {
      if (isComplete) {
         this.complete(player);
      } else {
         this.completions = 0;
      }

   }

   public void complete(Player player) {
      ++this.completions;
      if (this.isFirstCompletion() && player instanceof ServerPlayer serverPlayer) {
         ModAdvancements.COMPLETE_CHALLENGE.trigger(serverPlayer, this.core);
      }

   }

   public boolean isFirstCompletion() {
      return this.completions == 1;
   }

   public boolean isComplete() {
      return this.completions > 0;
   }

   public int getCompletions() {
      return this.completions;
   }

   public ChallengeCore<?> getCore() {
      return this.core;
   }

   @Nullable
   public String getRewards(Player player) {
      IEntityStats props = (IEntityStats)EntityStatsCapability.get(player).orElse((Object)null);
      IHakiData hakiData = (IHakiData)HakiCapability.get(player).orElse((Object)null);
      if (props != null && hakiData != null) {
         List<ItemStack> stacks = Lists.newArrayList();
         if (this.core.getRewards() != null) {
            LootTable lootTable = player.m_9236_().m_7654_().m_278653_().m_278676_(this.core.getRewards());
            if (lootTable == LootTable.f_79105_) {
               ModMain.LOGGER.warn(String.valueOf(this.core.getRewards()) + " reward could not be found.");
               return null;
            }

            LootParams.Builder builder = (new LootParams.Builder((ServerLevel)player.m_9236_())).m_287286_(LootContextParams.f_81455_, player).m_287286_(ModLootTypes.COMPLETED_CHALLENGE, this.core);
            LootParams context = builder.m_287235_(LootContextParamSets.f_81410_);
            stacks.addAll(lootTable.m_287195_(context));
         }

         StringBuilder sb = new StringBuilder();
         sb.append("\n§aRewards§r\n");
         boolean canReceiveStats = false;
         if (this.completions == 0 || this.core.getDifficulty() == ChallengeDifficulty.HARD) {
            canReceiveStats = true;
         }

         if (canReceiveStats) {
            float registeredChallengesTotalScale = (Float)((IForgeRegistry)WyRegistry.CHALLENGES.get()).getValues().stream().filter((oc) -> oc.getDifficulty().equals(this.core.getDifficulty())).map(ChallengeCore::getRewardsFactor).reduce(0.0F, (acc, val) -> acc + val);
            float challengeRewardFactor = this.core.getRewardsFactor() / registeredChallengesTotalScale;
            float rawDorikiAmount = challengeRewardFactor * (float)ServerConfig.getDorikiRewardPoolForDifficulty(this.core.getDifficulty());
            float rawBellyAmount = challengeRewardFactor * (float)ServerConfig.getBellyRewardPoolForDifficulty(this.core.getDifficulty());
            float rawHakiAmount = challengeRewardFactor * (float)ServerConfig.getHakiRewardPoolForDifficulty(this.core.getDifficulty());
            int dorikiAmount = this.scaleValueWithRounding(rawDorikiAmount);
            int bellyAmount = this.scaleValueWithRounding(rawBellyAmount);
            float hakiAmount = this.scaleValueFromCompletions(rawHakiAmount);
            hakiAmount = (float)Math.round(hakiAmount * 100.0F) / 100.0F;
            if (dorikiAmount > 0) {
               props.alterDoriki((double)dorikiAmount, StatChangeSource.CHALLENGE);
               sb.append("  " + dorikiAmount + " Doriki\n");
            }

            if (bellyAmount > 0) {
               props.alterBelly((long)bellyAmount, StatChangeSource.CHALLENGE);
               sb.append("  " + bellyAmount + " Belly\n");
            }

            if (hakiAmount > 0.0F) {
               if (player.m_217043_().m_188499_()) {
                  hakiData.alterBusoshokuHakiExp(hakiAmount, StatChangeSource.CHALLENGE);
                  sb.append("  " + hakiAmount + " Busoshoku Haki Experience\n");
               } else {
                  hakiData.alterKenbunshokuHakiExp(hakiAmount, StatChangeSource.CHALLENGE);
                  sb.append("  " + hakiAmount + " Kenbunshoku Haki Experience\n");
               }
            }
         }

         for(ItemStack stack : stacks) {
            if (stack.m_41783_() != null && stack.m_41786_().getString().contains("_rewards")) {
               int dorikiAmount = stack.m_41783_().m_128451_("_rewardsDoriki");
               int bellyAmount = stack.m_41783_().m_128451_("_rewardsBelly");
               int extolAmount = stack.m_41783_().m_128451_("_rewardsExtol");
               int bountyAmount = stack.m_41783_().m_128451_("_rewardsBounty");
               int busoHakiAmount = stack.m_41783_().m_128451_("_rewardsBusoHaki");
               int obsHakiAmount = stack.m_41783_().m_128451_("_rewardsKenHaki");
               if (dorikiAmount > 0) {
                  sb.append("  " + dorikiAmount + " Doriki\n");
               }

               if (bellyAmount > 0) {
                  sb.append("  " + bellyAmount + " Belly\n");
               }

               if (extolAmount > 0) {
                  sb.append("  " + extolAmount + " Extol\n");
               }

               if (bountyAmount > 0) {
                  sb.append("  " + bountyAmount + " Bounty\n");
               }

               if (bountyAmount > 0) {
                  sb.append("  " + bountyAmount + " Bounty\n");
               }

               if (busoHakiAmount > 0) {
                  sb.append("  " + busoHakiAmount + " Busoshoku Haki Experience\n");
               }

               if (obsHakiAmount > 0) {
                  sb.append("  " + obsHakiAmount + " Kenbunshoku Haki Experience\n");
               }

               int newUnlocks = stack.m_41783_().m_128451_("_unlocksAmount");
               if (newUnlocks > 0) {
                  ListTag unlocksList = stack.m_41783_().m_128437_("_unlocks", 8);

                  for(int i = 0; i < unlocksList.size(); ++i) {
                     String title = unlocksList.m_128778_(i);
                     sb.append("  New Challenge unlocked " + title + "\n");
                  }
               }
            } else {
               ItemStack stackCopy = stack.m_41777_();
               String var10001 = stackCopy.m_41613_() > 1 ? stackCopy.m_41613_() + " " : "";
               sb.append("  " + var10001 + stackCopy.m_41786_().getString() + "\n");
               player.m_36356_(stackCopy);
            }
         }

         ModNetwork.sendTo(new SSyncEntityStatsPacket(player, props), player);
         sb.append("\n");
         return sb.toString();
      } else {
         return null;
      }
   }

   public int scaleValueWithRounding(float amount) {
      amount = this.scaleValueFromCompletions(amount);
      if (amount % 10.0F != 0.0F && amount > 10.0F) {
         amount = (float)WyHelper.roundToNiceNumber(amount);
      }

      return (int)amount;
   }

   public float scaleValueFromCompletions(float amount) {
      int completions = this.getCompletions();
      completions = Math.min(completions, 10);
      if (completions > 0) {
         float d = (float)completions * 1.5F;
         amount /= d;
      }

      return amount;
   }

   public CompoundTag save(CompoundTag nbt) {
      nbt.m_128405_("completions", this.completions);
      nbt.m_128405_("bestTime", this.bestTime);
      return nbt;
   }

   public void load(CompoundTag nbt) {
      this.completions = nbt.m_128451_("completions");
      this.bestTime = nbt.m_128451_("bestTime");
   }

   public boolean equals(Object object) {
      if (object instanceof ChallengeCore challengeCore) {
         if (challengeCore.equals(this.getCore())) {
            return true;
         }
      }

      if (object instanceof Challenge challenge) {
         if (this.getCore() != null && challenge.getCore() != null) {
            return this.getCore().equals(challenge.getCore());
         } else {
            return false;
         }
      } else {
         return false;
      }
   }
}
