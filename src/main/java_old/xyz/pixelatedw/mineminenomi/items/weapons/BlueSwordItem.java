package xyz.pixelatedw.mineminenomi.items.weapons;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import xyz.pixelatedw.mineminenomi.abilities.gasu.BlueSwordAbility;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityCapability;
import xyz.pixelatedw.mineminenomi.init.ModEntityPredicates;
import xyz.pixelatedw.mineminenomi.init.ModTiers;

public class BlueSwordItem extends ModSwordItem {
   public BlueSwordItem() {
      super(ModTiers.BLUE_SWORD, 6);
   }

   public InteractionResultHolder<ItemStack> m_7203_(Level world, Player player, InteractionHand hand) {
      boolean hasBlueSwordUnlocked = (Boolean)AbilityCapability.get(player).map((props) -> props.hasUnlockedAbility((AbilityCore)BlueSwordAbility.INSTANCE.get())).orElse(false);
      if (!hasBlueSwordUnlocked) {
         return InteractionResultHolder.m_19100_(player.m_21120_(hand));
      } else {
         for(BlockPos pos : WyHelper.getNearbyBlocks(player, 10)) {
            if (world.m_8055_(pos.m_7494_()).m_60795_() && pos.hashCode() % (world.f_46441_.m_188503_(35) + 1) == 0) {
               world.m_7731_(pos.m_7494_(), Blocks.f_50083_.m_49966_(), 3);
            }
         }

         for(LivingEntity target : WyHelper.getNearbyLiving(player.m_20182_(), player.m_9236_(), (double)10.0F, ModEntityPredicates.getEnemyFactions(player))) {
            AbilityHelper.setSecondsOnFireBy(target, 20, player);
         }

         player.m_36335_().m_41524_(this, 600);
         return new InteractionResultHolder(InteractionResult.PASS, player.m_21120_(hand));
      }
   }

   public boolean m_7579_(ItemStack itemStack, LivingEntity target, LivingEntity attacker) {
      boolean hasBlueSwordUnlocked = (Boolean)AbilityCapability.get(attacker).map((props) -> props.hasUnlockedAbility((AbilityCore)BlueSwordAbility.INSTANCE.get())).orElse(false);
      if (!hasBlueSwordUnlocked) {
         return false;
      } else {
         AbilityHelper.setSecondsOnFireBy(target, 20, attacker);
         return true;
      }
   }
}
