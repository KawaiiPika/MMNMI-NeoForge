package xyz.pixelatedw.mineminenomi.blocks;

import java.util.List;
import java.util.function.Predicate;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.Level.ExplosionInteraction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.ticks.TickPriority;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import xyz.pixelatedw.mineminenomi.abilities.beta.BetaLauncherAbility;
import xyz.pixelatedw.mineminenomi.abilities.blackleg.DiableJambeAbility;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.config.ServerConfig;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityCapability;
import xyz.pixelatedw.mineminenomi.init.ModEffects;

public class MucusBlock extends Block {
   protected static final VoxelShape SHAPE = Block.m_49796_((double)0.0F, (double)0.0F, (double)0.0F, (double)16.0F, (double)1.0F, (double)16.0F);
   private int ticks = 1200;

   public MucusBlock() {
      super(Properties.m_284310_().m_60978_(8.0F).m_60977_());
   }

   public VoxelShape m_5939_(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
      return SHAPE;
   }

   public VoxelShape m_5940_(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
      return SHAPE;
   }

   public boolean isFullCube(BlockState state) {
      return false;
   }

   public boolean m_7898_(BlockState state, LevelReader world, BlockPos pos) {
      return world.m_8055_(pos.m_7495_()).m_60783_(world, pos, Direction.UP);
   }

   public BlockState m_7417_(BlockState state, Direction facing, BlockState facingState, LevelAccessor world, BlockPos pos, BlockPos facingPos) {
      return !state.m_60710_(world, pos) ? Blocks.f_50016_.m_49966_() : state;
   }

   @OnlyIn(Dist.CLIENT)
   public boolean m_6104_(BlockState state, BlockState adjacentBlockState, Direction side) {
      return adjacentBlockState.m_60734_() == this;
   }

   public void m_7892_(BlockState state, Level world, BlockPos pos, Entity entity) {
      if (entity instanceof LivingEntity living) {
         boolean isBetaUser = AbilityCapability.get(living).map((props) -> (BetaLauncherAbility)props.getEquippedAbility((AbilityCore)BetaLauncherAbility.INSTANCE.get())).isPresent();
         if (isBetaUser) {
            return;
         }

         if (!living.m_21023_((MobEffect)ModEffects.STICKY.get())) {
            living.m_7292_(new MobEffectInstance((MobEffect)ModEffects.STICKY.get(), 10, 0, false, false, false));
         }
      }

   }

   public void m_6807_(BlockState state, Level world, BlockPos pos, BlockState oldState, boolean isMoving) {
      world.m_186460_(pos, this, 1);
   }

   public void m_213897_(BlockState state, ServerLevel world, BlockPos pos, RandomSource rand) {
      if (!world.f_46443_) {
         if (this.ticks > 0) {
            --this.ticks;
            if (this.areFlamesClose(world, pos)) {
               world.m_255391_((Entity)null, (double)pos.m_123341_(), (double)pos.m_123342_(), (double)pos.m_123343_(), 4.0F, true, ServerConfig.isAbilityGriefingEnabled() ? ExplosionInteraction.BLOCK : ExplosionInteraction.NONE);
            }
         } else {
            world.m_46597_(pos, Blocks.f_50016_.m_49966_());
            this.ticks = 1200 + rand.m_188503_(400);
         }

         world.m_186464_(pos, this, 1, TickPriority.EXTREMELY_HIGH);
      }
   }

   private boolean areFlamesClose(Level world, BlockPos pos) {
      int range = 2;

      for(int i = -range; i < range; ++i) {
         for(int j = -range; j < range; ++j) {
            for(int k = -range; k < range; ++k) {
               int posX = pos.m_123341_() + i;
               int posY = pos.m_123342_() + j;
               int posZ = pos.m_123343_() + k;
               Block currentBlock = world.m_8055_(new BlockPos(posX, posY, posZ)).m_60734_();
               if (currentBlock == Blocks.f_50083_) {
                  return true;
               }
            }
         }
      }

      List<LivingEntity> list = WyHelper.<LivingEntity>getNearbyLiving(new Vec3((double)pos.m_123341_(), (double)pos.m_123342_(), (double)pos.m_123343_()), world, (double)range, (Predicate)null);
      if (!list.isEmpty()) {
         for(LivingEntity target : list) {
            if (target.m_6060_()) {
               return true;
            }

            if (target instanceof Player) {
               Player player = (Player)target;
               DiableJambeAbility abl = (DiableJambeAbility)AbilityCapability.get(player).map((props) -> (DiableJambeAbility)props.getEquippedAbility((AbilityCore)DiableJambeAbility.INSTANCE.get())).orElse((Object)null);
               if (abl != null && abl.isContinuous()) {
                  return true;
               }
            }
         }
      }

      return false;
   }

   public int getFireSpreadSpeed(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
      return 600;
   }

   public int getFlammability(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
      return 1000;
   }
}
