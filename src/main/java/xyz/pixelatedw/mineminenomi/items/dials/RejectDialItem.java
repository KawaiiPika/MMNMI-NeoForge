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

public class RejectDialItem extends BlockItem {
   public RejectDialItem(Block block) {
      super(block, (new Item.Properties()).stacksTo(16).component(xyz.pixelatedw.mineminenomi.init.ModDataComponents.ENERGY.get(), 0));
   }

   public boolean hurtEnemy(ItemStack itemStack, LivingEntity target, LivingEntity attacker) {
      if (!attacker.level().isClientSide && attacker instanceof Player playerAttacker) {
         if (playerAttacker.getCooldowns().getCooldownPercent(this, 0.0F) > 0.0F) {
            return false;
         } else {
             net.neoforged.neoforge.energy.IEnergyStorage energy = itemStack.getCapability(net.neoforged.neoforge.capabilities.Capabilities.EnergyStorage.ITEM);
             if (energy != null) {
                 energy.receiveEnergy(1000, false);
             }

            playerAttacker.getCooldowns().addCooldown(this, 400);
            playerAttacker.getItemInHand(playerAttacker.getUsedItemHand()).shrink(1);
            playerAttacker.addEffect(new MobEffectInstance(MobEffects.WITHER, 600, 2, false, false));
            playerAttacker.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 600, 1, false, false));
            playerAttacker.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 600, 1, false, false));
            DamageSource source = playerAttacker.damageSources().magic();
            if (!playerAttacker.isDeadOrDying()) {
               attacker.hurt(source, attacker.getMaxHealth() - 1.0F);
            }

            target.hurt(source, target.getMaxHealth() + 1.0F);
            return true;
         }
      } else {
         return false;
      }
   }
}
