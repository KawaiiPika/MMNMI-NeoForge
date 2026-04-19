package xyz.pixelatedw.mineminenomi.blocks;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.EntityCollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.crew.Crew;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceElement;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.api.enums.CanvasSize;
import xyz.pixelatedw.mineminenomi.api.events.FlagDestroyedEvent;
import xyz.pixelatedw.mineminenomi.api.events.FlagPlacedEvent;
import xyz.pixelatedw.mineminenomi.api.helpers.BlocksHelper;
import xyz.pixelatedw.mineminenomi.blocks.blockentity.FlagBlockEntity;
import xyz.pixelatedw.mineminenomi.init.ModBlockEntities;
import xyz.pixelatedw.mineminenomi.init.ModBlocks;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.items.FlagItem;
import xyz.pixelatedw.mineminenomi.packets.server.SSetFlagOnFirePacket;

public class FlagBlock extends BaseEntityBlock {
   private static final int[] SCALE_XZ = new int[]{1, 3, 6, 9};
   private static final int[] SCALE_Y = new int[]{1, 2, 4, 6};
   public static final DirectionProperty FACING;
   public static final EnumProperty<CanvasSize> SIZE;

   public FlagBlock() {
      super(Properties.m_284310_().m_60978_(0.2F).m_60955_().m_60960_(BlocksHelper::never));
      this.m_49959_((BlockState)((BlockState)((BlockState)this.f_49792_.m_61090_()).m_61124_(FACING, Direction.NORTH)).m_61124_(SIZE, CanvasSize.SMALL));
   }

   public VoxelShape m_5940_(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
      return Shapes.m_83144_();
   }

   public VoxelShape m_5939_(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
      if (context instanceof EntityCollisionContext collisionContext) {
         Entity entity = collisionContext.m_193113_();
         if (entity instanceof Projectile) {
            return state.m_60808_(level, pos);
         }
      }

      return Shapes.m_83040_();
   }

   public boolean m_7898_(BlockState state, LevelReader world, BlockPos pos) {
      Direction direction = (Direction)state.m_61143_(FACING);
      BlockState supportState = world.m_8055_(pos.m_121955_(direction.m_122424_().m_122436_()));
      return supportState.m_280296_();
   }

   @Nullable
   public BlockState m_5573_(BlockPlaceContext context) {
      BlockState blockstate = this.m_49966_();
      BlockPos blockpos = context.m_8083_();
      Direction[] adirection = context.m_6232_();
      CanvasSize size = CanvasSize.values()[context.m_43722_().m_41784_().m_128451_("canvasSize")];
      blockstate = (BlockState)blockstate.m_61124_(SIZE, size);

      for(Direction direction : adirection) {
         if (direction.m_122434_().m_122479_()) {
            Direction direction1 = direction.m_122424_();
            blockstate = (BlockState)blockstate.m_61124_(FACING, direction1);
            BlockState supportBlock = context.m_43725_().m_8055_(blockpos.m_121955_(((Direction)blockstate.m_61143_(FACING)).m_122424_().m_122436_()));
            if (supportBlock.m_60734_() == ModBlocks.FLAG.get()) {
               return null;
            }

            if (blockstate.m_60710_(context.m_43725_(), blockpos)) {
               return blockstate;
            }
         }
      }

      return null;
   }

   public BlockEntity m_142194_(BlockPos pos, BlockState state) {
      return new FlagBlockEntity(pos, state);
   }

   public void m_5707_(Level level, BlockPos pos, BlockState state, Player player) {
      this.breakFlag(level, pos, player);
   }

   /** @deprecated */
   @Deprecated
   public List<ItemStack> m_49635_(BlockState state, LootParams.Builder builder) {
      return new ArrayList();
   }

   public void m_5581_(Level level, BlockState state, BlockHitResult hit, Projectile projectile) {
      if (!level.f_46443_) {
         boolean fireFlag = projectile.m_6060_();
         boolean waterFlag = false;
         if (projectile instanceof NuProjectileEntity) {
            NuProjectileEntity nuProjectile = (NuProjectileEntity)projectile;
            if (nuProjectile.getParent() != null) {
               fireFlag = projectile.m_6060_() || ((IAbility)nuProjectile.getParent().get()).getCore().getSourceElement().equals(SourceElement.FIRE);
               waterFlag = ((IAbility)nuProjectile.getParent().get()).getCore().getSourceElement().equals(SourceElement.WATER);
            }
         }

         if (fireFlag || waterFlag) {
            BlockEntity tileEntity = level.m_7702_(hit.m_82425_());
            if (tileEntity != null && tileEntity instanceof FlagBlockEntity) {
               FlagBlockEntity flagTile = (FlagBlockEntity)tileEntity;
               if (flagTile.isSub()) {
                  flagTile = flagTile.getMaster();
               }

               BlockPos masterTilePos = flagTile.m_58899_();
               if (fireFlag) {
                  flagTile.setOnFire(true);
                  flagTile.setLastAttacker(projectile);
                  ModNetwork.sendToAllAroundDistance(new SSetFlagOnFirePacket(masterTilePos, true), level, hit.m_82450_(), 256);
               } else if (waterFlag) {
                  flagTile.setOnFire(false);
                  ModNetwork.sendToAllAroundDistance(new SSetFlagOnFirePacket(masterTilePos, false), level, hit.m_82450_(), 256);
               }
            }
         }

      }
   }

   public void m_6402_(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
      BlockEntity tileEntity = level.m_7702_(pos);
      if (tileEntity != null && tileEntity instanceof FlagBlockEntity flagTile) {
         flagTile.setOwner(placer);
         if (stack.m_41782_()) {
            Crew crew = Crew.from(stack.m_41783_().m_128469_("crew"));
            flagTile.setCrew(crew);
         }

         boolean drop = false;
         if (placer != null && placer instanceof Player player) {
            drop = player.m_150110_().f_35937_;
         }

         placeSubs(level, pos, state, drop);
         if (placer != null && placer instanceof Player player) {
            FlagPlacedEvent event = new FlagPlacedEvent(player, state, pos, flagTile);
            if (MinecraftForge.EVENT_BUS.post(event)) {
               level.m_7731_(pos, Blocks.f_50016_.m_49966_(), 3);
            }
         }
      }

   }

   public static void placeSubs(LevelAccessor world, BlockPos pos, BlockState state, boolean drop) {
      FlagBlockEntity flagTile = (FlagBlockEntity)world.m_7702_(pos);
      Direction dir = (Direction)state.m_61143_(FACING);
      CanvasSize size = (CanvasSize)state.m_61143_(SIZE);
      if (size.ordinal() > CanvasSize.SMALL.ordinal()) {
         int xzSize = SCALE_XZ[size.ordinal()];
         int ySize = SCALE_Y[size.ordinal()];

         for(int y = 0; y < ySize; ++y) {
            for(int xz = 0; xz < xzSize; ++xz) {
               if (y != 0 || xz != 0) {
                  BlockPos pos2 = pos.m_5484_(dir, xz).m_7918_(0, -y, 0);
                  boolean isAir = world.m_8055_(pos2).m_60795_();
                  if (!isAir || !world.m_7731_(pos2, ((Block)ModBlocks.FLAG.get()).m_49966_(), 3)) {
                     flagTile.breakAllBlocks(drop);
                     return;
                  }

                  FlagBlockEntity subFlagTile = (FlagBlockEntity)world.m_7702_(pos2);
                  if (subFlagTile != null) {
                     subFlagTile.setMaster(flagTile);
                     flagTile.addSub(pos2);
                  }
               }
            }
         }
      }

   }

   private void breakFlag(LevelAccessor level, BlockPos pos, @Nullable LivingEntity entity) {
      BlockEntity tileEntity = level.m_7702_(pos);
      if (tileEntity != null && tileEntity instanceof FlagBlockEntity flagEntity) {
         if (flagEntity.isSub()) {
            flagEntity = flagEntity.getMaster();
         }

         if (flagEntity == null) {
            return;
         }

         flagEntity = flagEntity.getMaster();
         BlockState masterState = level.m_8055_(flagEntity.m_58899_());
         if (masterState.m_60795_()) {
            return;
         }

         ItemStack flagStack = new ItemStack((ItemLike)ModBlocks.FLAG.get());
         if (masterState.m_61138_(SIZE)) {
            FlagItem.setCanvasSize(flagStack, (CanvasSize)masterState.m_61143_(SIZE));
         }

         boolean drop = false;
         if (entity != null && entity instanceof Player player) {
            drop = player.m_150110_().f_35937_;
         }

         if (level instanceof Level actualLevel) {
            if (!drop) {
               ItemEntity itemDrop = new ItemEntity(actualLevel, (double)pos.m_123341_(), (double)pos.m_123342_(), (double)pos.m_123343_(), flagStack);
               level.m_7967_(itemDrop);
            }
         }

         flagEntity.breakAllBlocks(true);
         FlagDestroyedEvent event = new FlagDestroyedEvent(masterState, pos, flagEntity.getLastAttacker(), flagEntity);
         MinecraftForge.EVENT_BUS.post(event);
      }

   }

   @Nullable
   public <T extends BlockEntity> BlockEntityTicker<T> m_142354_(Level level, BlockState state, BlockEntityType<T> blockEntity) {
      return m_152132_(blockEntity, (BlockEntityType)ModBlockEntities.FLAG.get(), FlagBlockEntity::tick);
   }

   public boolean m_7420_(BlockState state, BlockGetter reader, BlockPos pos) {
      return true;
   }

   @OnlyIn(Dist.CLIENT)
   public boolean m_6104_(BlockState state, BlockState adjacentBlockState, Direction side) {
      return true;
   }

   public BlockState m_7417_(BlockState state, Direction facing, BlockState facingState, LevelAccessor world, BlockPos currentPos, BlockPos facingPos) {
      boolean var10000;
      label22: {
         if (world.m_7702_(currentPos) != null) {
            BlockEntity var9 = world.m_7702_(currentPos);
            if (var9 instanceof FlagBlockEntity) {
               FlagBlockEntity flagTile = (FlagBlockEntity)var9;
               if (flagTile.isSub()) {
                  var10000 = true;
                  break label22;
               }
            }
         }

         var10000 = false;
      }

      boolean isSub = var10000;
      if (!isSub && facing.m_122424_() == state.m_61143_(FACING) && !state.m_60710_(world, currentPos)) {
         this.breakFlag(world, currentPos, (LivingEntity)null);
         return Blocks.f_50016_.m_49966_();
      } else {
         return state;
      }
   }

   public BlockState m_6843_(BlockState state, Rotation rot) {
      return (BlockState)state.m_61124_(FACING, rot.m_55954_((Direction)state.m_61143_(FACING)));
   }

   public BlockState m_6943_(BlockState state, Mirror mirrorIn) {
      return state.m_60717_(mirrorIn.m_54846_((Direction)state.m_61143_(FACING)));
   }

   protected void m_7926_(StateDefinition.Builder<Block, BlockState> builder) {
      builder.m_61104_(new Property[]{FACING});
      builder.m_61104_(new Property[]{SIZE});
   }

   public boolean m_7357_(BlockState pState, BlockGetter pLevel, BlockPos pPos, PathComputationType pType) {
      return false;
   }

   static {
      FACING = HorizontalDirectionalBlock.f_54117_;
      SIZE = BlocksHelper.CANVAS_SIZE;
   }
}
