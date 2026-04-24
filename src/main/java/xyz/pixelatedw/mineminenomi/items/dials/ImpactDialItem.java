package xyz.pixelatedw.mineminenomi.items.dials;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.energy.IEnergyStorage;

public class ImpactDialItem extends BlockItem {
   public ImpactDialItem(Block block) {
      super(block, (new Item.Properties()).stacksTo(16).component(xyz.pixelatedw.mineminenomi.init.ModDataComponents.ENERGY.get(), 0));
   }

   public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
      ItemStack itemstack = player.getItemInHand(hand);
      if (!world.isClientSide) {

         IEnergyStorage energy = itemstack.getCapability(net.neoforged.neoforge.capabilities.Capabilities.EnergyStorage.ITEM);
         if (energy != null && energy.getEnergyStored() > 0) {
             player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 2, 4));
             float explosionRadius = Math.max(1.0F, energy.getEnergyStored() / 2000.0F);
             world.explode(player, player.getX(), player.getY(), player.getZ(), explosionRadius, Level.ExplosionInteraction.NONE);

             energy.extractEnergy(energy.getEnergyStored(), false);

             player.getCooldowns().addCooldown(this, 10);
         }
      }

      return InteractionResultHolder.success(itemstack);
   }

   @Override
   public boolean hurtEnemy(ItemStack itemStack, net.minecraft.world.entity.LivingEntity target, net.minecraft.world.entity.LivingEntity attacker) {
      if (!attacker.level().isClientSide) {
          IEnergyStorage energy = itemStack.getCapability(net.neoforged.neoforge.capabilities.Capabilities.EnergyStorage.ITEM);
          if (energy != null) {
              energy.receiveEnergy(1000, false);
          }
      }
      return super.hurtEnemy(itemStack, target, attacker);
   }

}
