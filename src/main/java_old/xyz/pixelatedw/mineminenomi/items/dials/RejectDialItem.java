package xyz.pixelatedw.mineminenomi.items.dials;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import xyz.pixelatedw.mineminenomi.init.ModDamageSources;

public class RejectDialItem extends BlockItem {
   public RejectDialItem(Block block) {
      super(block, (new Item.Properties()).m_41487_(16));
   }

   public boolean m_7579_(ItemStack itemStack, LivingEntity target, LivingEntity attacker) {
      if (!attacker.m_9236_().f_46443_ && attacker instanceof Player playerAttacker) {
         if (playerAttacker.m_36335_().m_41521_(this, 0.0F) > 0.0F) {
            return false;
         } else {
            playerAttacker.m_36335_().m_41524_(this, 400);
            playerAttacker.m_21120_(playerAttacker.m_7655_()).m_41774_(1);
            playerAttacker.m_7292_(new MobEffectInstance(MobEffects.f_19613_, 600, 2, false, false));
            playerAttacker.m_7292_(new MobEffectInstance(MobEffects.f_19597_, 600, 1, false, false));
            playerAttacker.m_7292_(new MobEffectInstance(MobEffects.f_19599_, 600, 1, false, false));
            DamageSource source = ModDamageSources.getInstance().rejectDial(playerAttacker);
            if (!playerAttacker.m_7500_()) {
               attacker.m_6469_(source, attacker.m_21233_() - 1.0F);
            }

            target.m_6469_(source, target.m_21233_() + 1.0F);
            return true;
         }
      } else {
         return false;
      }
   }
}
