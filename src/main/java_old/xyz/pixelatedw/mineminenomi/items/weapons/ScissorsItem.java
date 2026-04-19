package xyz.pixelatedw.mineminenomi.items.weapons;

import java.util.List;
import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.IForgeShearable;
import xyz.pixelatedw.mineminenomi.abilities.kage.KageGiriAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityCapability;
import xyz.pixelatedw.mineminenomi.data.entity.stats.EntityStatsCapability;
import xyz.pixelatedw.mineminenomi.data.entity.stats.IEntityStats;
import xyz.pixelatedw.mineminenomi.init.ModItems;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.init.ModTiers;
import xyz.pixelatedw.mineminenomi.init.ModWeapons;
import xyz.pixelatedw.mineminenomi.packets.server.ability.SRemoveShadowEventsPacket;

public class ScissorsItem extends ModSwordItem {
   public ScissorsItem() {
      super(ModTiers.SCISSORS, 5, -2.8F);
   }

   public boolean m_7579_(ItemStack itemStack, LivingEntity target, LivingEntity attacker) {
      if (!(attacker instanceof Player playerAttacker)) {
         return super.m_7579_(itemStack, target, attacker);
      } else {
         KageGiriAbility passive = (KageGiriAbility)AbilityCapability.get(attacker).map((props) -> (KageGiriAbility)props.getPassiveAbility((AbilityCore)KageGiriAbility.INSTANCE.get())).orElse((Object)null);
         boolean hasPassive = passive != null && passive.canUse(attacker).isSuccess();
         IEntityStats targetProps = (IEntityStats)EntityStatsCapability.get(target).orElse((Object)null);
         if (targetProps != null && hasPassive && itemStack != null && itemStack.m_41720_() == ModWeapons.SCISSORS.get() && targetProps.hasShadow()) {
            if (passive != null && passive.addIfValid(target.m_20148_())) {
               targetProps.setShadow(false);
               playerAttacker.m_150109_().m_36054_(new ItemStack((ItemLike)ModItems.SHADOW.get()));
               ModNetwork.sendToAllTracking(new SRemoveShadowEventsPacket(target), target);
            }

            return super.m_7579_(itemStack, target, attacker);
         } else {
            return super.m_7579_(itemStack, target, attacker);
         }
      }
   }

   public boolean m_6813_(ItemStack stack, Level world, BlockState state, BlockPos pos, LivingEntity entityLiving) {
      if (!world.f_46443_) {
         stack.m_41622_(1, entityLiving, (entity) -> entity.m_21166_(EquipmentSlot.MAINHAND));
      }

      Block block = state.m_60734_();
      return !state.m_204336_(BlockTags.f_13035_) && block != Blocks.f_50033_ && block != Blocks.f_50034_ && block != Blocks.f_50035_ && block != Blocks.f_50036_ && block != Blocks.f_50191_ && block != Blocks.f_50267_ && !state.m_204336_(BlockTags.f_13089_) ? super.m_6813_(stack, world, state, pos, entityLiving) : true;
   }

   public boolean m_8096_(BlockState blockIn) {
      Block block = blockIn.m_60734_();
      return block == Blocks.f_50033_ || block == Blocks.f_50088_ || block == Blocks.f_50267_;
   }

   public float m_8102_(ItemStack stack, BlockState state) {
      Block block = state.m_60734_();
      if (block != Blocks.f_50033_ && !state.m_204336_(BlockTags.f_13035_)) {
         return state.m_204336_(BlockTags.f_13089_) ? 5.0F : super.m_8102_(stack, state);
      } else {
         return 15.0F;
      }
   }

   public InteractionResult m_6880_(ItemStack stack, Player playerIn, LivingEntity entity, InteractionHand hand) {
      if (entity.m_9236_().f_46443_) {
         return InteractionResult.PASS;
      } else if (entity instanceof IForgeShearable) {
         IForgeShearable target = (IForgeShearable)entity;
         BlockPos pos = entity.m_20183_();
         if (target.isShearable(stack, entity.m_9236_(), pos)) {
            List<ItemStack> drops = target.onSheared(playerIn, stack, entity.m_9236_(), pos, stack.getEnchantmentLevel(Enchantments.f_44987_));
            Random rand = new Random();
            drops.forEach((d) -> {
               ItemEntity ent = entity.m_5552_(d, 1.0F);
               ent.m_20256_(ent.m_20184_().m_82520_((double)((rand.nextFloat() - rand.nextFloat()) * 0.1F), (double)(rand.nextFloat() * 0.05F), (double)((rand.nextFloat() - rand.nextFloat()) * 0.1F)));
            });
            stack.m_41622_(1, entity, (e) -> e.m_21190_(hand));
         }

         return InteractionResult.SUCCESS;
      } else {
         return InteractionResult.PASS;
      }
   }
}
