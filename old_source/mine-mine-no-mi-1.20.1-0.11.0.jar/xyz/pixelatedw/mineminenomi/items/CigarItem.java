package xyz.pixelatedw.mineminenomi.items;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.ItemsHelper;
import xyz.pixelatedw.mineminenomi.data.entity.devilfruit.DevilFruitCapability;
import xyz.pixelatedw.mineminenomi.data.entity.devilfruit.IDevilFruit;
import xyz.pixelatedw.mineminenomi.init.ModFruits;

public class CigarItem extends Item {
   private int smokeFreqency = 1;

   public CigarItem(int smokeFreqency) {
      super((new Item.Properties()).m_41503_(1000));
      this.smokeFreqency = smokeFreqency;
   }

   public InteractionResultHolder<ItemStack> m_7203_(Level world, Player player, InteractionHand hand) {
      ItemStack heldStack = player.m_21120_(hand);
      if (player.m_6844_(EquipmentSlot.HEAD).m_41619_()) {
         ItemStack stack = new ItemStack(heldStack.m_41720_(), 1);
         player.m_8061_(EquipmentSlot.HEAD, stack);
         player.m_5496_(SoundEvents.f_11942_, 1.0F, 1.0F);
         player.m_21120_(hand).m_41774_(1);
      }

      return InteractionResultHolder.m_19090_(heldStack);
   }

   public boolean canEquip(ItemStack stack, EquipmentSlot armorType, Entity entity) {
      return armorType.equals(EquipmentSlot.HEAD);
   }

   public void onInventoryTick(ItemStack stack, Level level, Player player, int slotIndex, int selectedIndex) {
      boolean isEquipped = ItemsHelper.hasItemInSlot(player, EquipmentSlot.HEAD, this);
      if (isEquipped && player.f_19797_ % this.smokeFreqency == 0) {
         if (!player.m_5842_()) {
            Vec3 vec = player.m_20154_().m_82490_((double)0.5F + (double)(player.m_20205_() / 2.0F)).m_82524_((float)Math.toRadians((double)-20.0F));
            level.m_7106_(ParticleTypes.f_123777_, player.m_20185_() + vec.f_82479_, vec.f_82480_ + player.m_20188_(), player.m_20189_() + vec.f_82481_, (double)0.0F, 0.04, (double)0.0F);
         }

         if (!level.f_46443_) {
            IDevilFruit props = (IDevilFruit)DevilFruitCapability.get(player).orElse((Object)null);
            if (props == null) {
               return;
            }

            if (props.hasDevilFruit(ModFruits.MOKU_MOKU_NO_MI) || props.hasDevilFruit(ModFruits.GASU_GASU_NO_MI)) {
               return;
            }

            stack.m_41622_(1, player, (user) -> user.m_21166_(EquipmentSlot.HEAD));
         }
      }

      super.onInventoryTick(stack, level, player, slotIndex, selectedIndex);
   }

   public Component m_7626_(ItemStack stack) {
      return (Component)(WyHelper.isAprilFirst() ? Component.m_237113_("Lollipop") : super.m_7626_(stack));
   }
}
