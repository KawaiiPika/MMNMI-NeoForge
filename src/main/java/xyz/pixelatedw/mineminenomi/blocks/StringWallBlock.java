package xyz.pixelatedw.mineminenomi.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.EntityCollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
// import xyz.pixelatedw.mineminenomi.abilities.SlashDamageImmunityAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
// import xyz.pixelatedw.mineminenomi.data.entity.IAbilityData;
// import xyz.pixelatedw.mineminenomi.data.entity.IDevilFruitData;
import xyz.pixelatedw.mineminenomi.init.ModFruits;

public class StringWallBlock extends Block {
   protected static final VoxelShape COLLISION_SHAPE = Block.box((double)1.0F, (double)0.0F, (double)1.0F, (double)15.0F, (double)15.0F, (double)15.0F);
   protected static final VoxelShape OUTLINE_SHAPE = Block.box((double)1.0F, (double)0.0F, (double)1.0F, (double)15.0F, (double)16.0F, (double)15.0F);

   public StringWallBlock() {
      super(BlockBehaviour.Properties.of().mapColor(net.minecraft.world.level.material.MapColor.WOOL).ignitedByLava().noOcclusion().strength(-1.0F, 10000.0F));
   }

   public VoxelShape getCollisionShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
      if (context instanceof EntityCollisionContext collisionContext) {
         Entity var7 = collisionContext.getEntity();
         if (var7 instanceof Player player) {
            boolean hasBara = false; // DevilFruitCapability.hasDevilFruit(player, ModFruits.BARA_BARA_NO_MI);
            boolean hasSlashImmunity = false; // AbilityCapability.hasUnlockedAbility(player, (AbilityCore)SlashDamageImmunityAbility.INSTANCE.get());
            if (hasBara || hasSlashImmunity) {
               return Shapes.empty();
            }
         }
      }

      return COLLISION_SHAPE;
   }

   public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
      return OUTLINE_SHAPE;
   }

   public boolean propagatesSkylightDown(BlockState state, BlockGetter reader, BlockPos pos) {
      return true;
   }


   public boolean skipRendering(BlockState state, BlockState adjacentBlockState, net.minecraft.core.Direction side) {
      return adjacentBlockState.getBlock() == this;
   }

   public int getLightBlock(BlockState state, BlockGetter worldIn, BlockPos pos) {
      return 0;
   }

   public void entityInside(BlockState state, net.minecraft.world.level.Level world, BlockPos pos, Entity entity) {
      if (entity instanceof Player player) {
         boolean hasBara = false; // DevilFruitCapability.hasDevilFruit(player, ModFruits.BARA_BARA_NO_MI);
         boolean hasSlashImmunity = false; // AbilityCapability.hasUnlockedAbility(player, (AbilityCore)SlashDamageImmunityAbility.INSTANCE.get());
         if (hasBara || hasSlashImmunity) {
            return;
         }
      }

      entity.hurt(entity.damageSources().cactus(), 1.0F);
   }
}
