package xyz.pixelatedw.mineminenomi.api.protection;

import com.google.common.collect.Lists;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class BlockProtectionRule {
   private Set<TagKey<Block>> approvedTags = new HashSet();
   private Set<TagKey<Block>> bannedTags = new HashSet();
   private Set<Supplier<Block>> approvedBlocks = new HashSet();
   private Set<Supplier<Block>> bannedBlocks = new HashSet();
   private Set<IReplaceBlockRule> replaceRules = new HashSet();

   public void setApprovedBlocks(Set<Supplier<Block>> blocks) {
      this.approvedBlocks = blocks;
   }

   public Set<Supplier<Block>> getApprovedBlocks() {
      return this.approvedBlocks;
   }

   public void setBannedBlocks(Set<Supplier<Block>> blocks) {
      this.bannedBlocks = blocks;
   }

   public Set<Supplier<Block>> getBannedBlocks() {
      return this.bannedBlocks;
   }

   private void setApprovedTags(Set<TagKey<Block>> tags) {
      this.approvedTags = tags;
   }

   public Set<TagKey<Block>> getApprovedTags() {
      return this.approvedTags;
   }

   public void setBannedTags(Set<TagKey<Block>> tags) {
      this.bannedTags = tags;
   }

   public Set<TagKey<Block>> getBannedTags() {
      return this.bannedTags;
   }

   private void setReplaceRules(Set<IReplaceBlockRule> fns) {
      this.replaceRules = fns;
   }

   public Set<IReplaceBlockRule> getReplaceRules() {
      return this.replaceRules;
   }

   public boolean check(Level world, BlockPos pos, BlockState state) {
      if (this.isBanned(state)) {
         return false;
      } else {
         for(IReplaceBlockRule fn : this.replaceRules) {
            boolean replaced = fn.replace(world, pos, state);
            if (!replaced) {
               return false;
            }
         }

         return this.isApproved(state);
      }
   }

   public boolean isApproved(BlockState state) {
      if (this.approvedTags.stream().anyMatch((tag) -> state.m_204336_(tag))) {
         return true;
      } else {
         return this.approvedBlocks.stream().anyMatch((block) -> state.m_60734_().equals(block.get()));
      }
   }

   public boolean isBanned(BlockState state) {
      if (this.bannedTags.stream().anyMatch((tag) -> state.m_204336_(tag))) {
         return true;
      } else {
         return this.bannedBlocks.stream().anyMatch((block) -> state.m_60734_().equals(block.get()));
      }
   }

   public static class Builder {
      private Set<TagKey<Block>> approvedTags = new HashSet();
      private Set<TagKey<Block>> bannedTags = new HashSet();
      private Set<Supplier<Block>> approvedBlocks = new HashSet();
      private Set<Supplier<Block>> bannedBlocks = new HashSet();
      private Set<IReplaceBlockRule> replaceRules = new HashSet();

      public Builder(BlockProtectionRule... rules) {
         for(BlockProtectionRule rule : rules) {
            this.replaceRules.addAll(rule.getReplaceRules());
            this.approvedTags.addAll(rule.getApprovedTags());
            this.bannedTags.addAll(rule.getBannedTags());
            this.approvedBlocks.addAll(rule.getApprovedBlocks());
            this.bannedBlocks.addAll(rule.getBannedBlocks());
         }

      }

      public Builder addReplaceRules(IReplaceBlockRule fn) {
         this.replaceRules.add(fn);
         return this;
      }

      public Builder addApprovedTags(TagKey<Block>... tags) {
         this.approvedTags.addAll(Lists.newArrayList(tags));
         return this;
      }

      public Builder addBannedTags(TagKey<Block>... tags) {
         this.bannedTags.addAll(Lists.newArrayList(tags));
         return this;
      }

      public Builder addApprovedBlocks(Supplier<Block>... blocks) {
         this.approvedBlocks.addAll(Lists.newArrayList(blocks));
         return this;
      }

      public Builder addBannedBlocks(Supplier<Block>... blocks) {
         this.bannedBlocks.addAll(Lists.newArrayList(blocks));
         return this;
      }

      public Builder setBypassGriefRule() {
         return this;
      }

      public BlockProtectionRule build() {
         BlockProtectionRule rule = new BlockProtectionRule();
         rule.setReplaceRules(this.replaceRules);
         rule.setApprovedTags(this.approvedTags);
         rule.setBannedTags(this.bannedTags);
         rule.setApprovedBlocks(this.approvedBlocks);
         rule.setBannedBlocks(this.bannedBlocks);
         return rule;
      }
   }

   @FunctionalInterface
   public interface IReplaceBlockRule {
      boolean replace(Level var1, BlockPos var2, BlockState var3);
   }
}
