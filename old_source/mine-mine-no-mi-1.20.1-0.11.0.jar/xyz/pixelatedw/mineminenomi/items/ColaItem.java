package xyz.pixelatedw.mineminenomi.items;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import xyz.pixelatedw.mineminenomi.api.WyDebug;
import xyz.pixelatedw.mineminenomi.api.blockgen.BlockGenerators;
import xyz.pixelatedw.mineminenomi.api.blockgen.SimpleBlockPlacer;
import xyz.pixelatedw.mineminenomi.data.entity.stats.EntityStatsCapability;
import xyz.pixelatedw.mineminenomi.data.entity.stats.IEntityStats;
import xyz.pixelatedw.mineminenomi.init.ModFoods;
import xyz.pixelatedw.mineminenomi.init.ModItems;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.packets.server.entity.SUpdateColaAmountPacket;

public class ColaItem extends Item {
   public static final int COLA_REFILL = 25;

   public ColaItem() {
      super((new Item.Properties()).m_41487_(16).m_41489_(ModFoods.COLA));
   }

   public void m_6883_(ItemStack pStack, Level world, Entity pEntity, int pItemSlot, boolean pIsSelected) {
      if (WyDebug.isDebug() && !world.f_46443_) {
      }

   }

   public InteractionResultHolder<ItemStack> m_7203_(Level world, Player player, InteractionHand hand) {
      if (WyDebug.isDebug() && !world.f_46443_) {
      }

      ItemStack itemstack = player.m_21120_(hand);
      player.m_6672_(hand);
      return InteractionResultHolder.m_19090_(itemstack);
   }

   public ItemStack m_5922_(ItemStack itemStack, Level world, LivingEntity entity) {
      if (!world.f_46443_ && entity instanceof ServerPlayer player) {
         IEntityStats props = (IEntityStats)EntityStatsCapability.get(player).orElse((Object)null);
         if (props == null) {
            return itemStack;
         }

         if (props.isCyborg()) {
            props.alterCola(25);
         }

         player.m_5584_(world, itemStack);
         if (!player.m_150110_().f_35937_) {
            player.m_36356_(new ItemStack((ItemLike)ModItems.EMPTY_COLA.get()));
         }

         ModNetwork.sendTo(new SUpdateColaAmountPacket(entity), player);
      }

      return itemStack;
   }

   public UseAnim m_6164_(ItemStack stack) {
      return UseAnim.DRINK;
   }

   class BlockPlacerThread extends Thread {
      private final Level level;
      private final BlockPos pos;
      private final BlockState state;

      public BlockPlacerThread(Level level, BlockPos pos, BlockState state) {
         this.level = level;
         this.pos = pos;
         this.state = state;
         this.setDaemon(true);
      }

      public void run() {
         SimpleBlockPlacer placer = (new SimpleBlockPlacer()).setBlock(this.state).setSize(10, 10).setFlag(3);
         placer.generate(this.level, this.pos, BlockGenerators.CUBE);
      }
   }
}
