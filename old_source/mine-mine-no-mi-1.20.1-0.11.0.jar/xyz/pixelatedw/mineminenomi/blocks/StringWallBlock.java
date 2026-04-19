package xyz.pixelatedw.mineminenomi.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.EntityCollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import xyz.pixelatedw.mineminenomi.abilities.SlashDamageImmunityAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityCapability;
import xyz.pixelatedw.mineminenomi.data.entity.devilfruit.DevilFruitCapability;
import xyz.pixelatedw.mineminenomi.init.ModFruits;

public class StringWallBlock extends Block {
   protected static final VoxelShape COLLISION_SHAPE = Block.m_49796_((double)1.0F, (double)0.0F, (double)1.0F, (double)15.0F, (double)15.0F, (double)15.0F);
   protected static final VoxelShape OUTLINE_SHAPE = Block.m_49796_((double)1.0F, (double)0.0F, (double)1.0F, (double)15.0F, (double)16.0F, (double)15.0F);

   public StringWallBlock() {
      super(Properties.m_284310_().m_284180_(MapColor.f_283930_).m_60955_().m_222994_().m_60913_(-1.0F, 10000.0F));
   }

   public VoxelShape m_5939_(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
      if (context instanceof EntityCollisionContext collisionContext) {
         Entity var7 = collisionContext.m_193113_();
         if (var7 instanceof Player player) {
            boolean hasBara = DevilFruitCapability.hasDevilFruit(player, ModFruits.BARA_BARA_NO_MI);
            boolean hasSlashImmunity = AbilityCapability.hasUnlockedAbility(player, (AbilityCore)SlashDamageImmunityAbility.INSTANCE.get());
            if (hasBara || hasSlashImmunity) {
               return Shapes.m_83040_();
            }
         }
      }

      return COLLISION_SHAPE;
   }

   public VoxelShape m_5940_(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
      return OUTLINE_SHAPE;
   }

   public boolean m_7420_(BlockState state, BlockGetter reader, BlockPos pos) {
      return true;
   }

   @OnlyIn(Dist.CLIENT)
   public boolean m_6104_(BlockState state, BlockState adjacentBlockState, Direction side) {
      return adjacentBlockState.m_60734_() == this;
   }

   public int m_7753_(BlockState state, BlockGetter worldIn, BlockPos pos) {
      return 0;
   }

   public void m_7892_(BlockState state, Level world, BlockPos pos, Entity entity) {
      if (entity instanceof Player player) {
         boolean hasBara = DevilFruitCapability.hasDevilFruit(player, ModFruits.BARA_BARA_NO_MI);
         boolean hasSlashImmunity = AbilityCapability.hasUnlockedAbility(player, (AbilityCore)SlashDamageImmunityAbility.INSTANCE.get());
         if (hasBara || hasSlashImmunity) {
            return;
         }
      }

      entity.m_6469_(entity.m_269291_().m_269425_(), 1.0F);
   }
}
