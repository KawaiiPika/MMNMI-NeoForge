package xyz.pixelatedw.mineminenomi.blocks;

import com.google.common.collect.ImmutableSet;
import com.mojang.logging.LogUtils;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;

import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.BlockHitResult;
// import net.neoforged.neoforge.registries.IForgeRegistry;
// import xyz.pixelatedw.mineminenomi.api.NuWorld;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
// import xyz.pixelatedw.mineminenomi.api.WyRegistry;
import xyz.pixelatedw.mineminenomi.blocks.blockentity.PoneglyphBlockEntity;

public class PoneglyphBlock extends Block implements EntityBlock {
   public static final IntegerProperty TEXTURE = IntegerProperty.create("texture", 0, 2);

   public PoneglyphBlock() {
      super(BlockBehaviour.Properties.of().strength(Float.MAX_VALUE).noOcclusion());
      this.registerDefaultState((BlockState)((BlockState)this.stateDefinition.any()).setValue(TEXTURE, 0));
   }

   public boolean propagatesSkylightDown(BlockState state, BlockGetter reader, BlockPos pos) {
      return true;
   }

   /* /* public void initPoneglyph(LevelAccessor world, BlockPos pos) {
      ChallengeCore<?> challenge = null;

      for(BlockPos checkPos : WyHelper.getNearbyBlocks(pos, world, 2)) {
         BlockEntity te = world.m_7702_(checkPos);
         if (!checkPos.equals(pos) && te instanceof PoneglyphBlockEntity poneglyphTile) {
            if (poneglyphTile.getChallenge() != null) {
               challenge = poneglyphTile.getChallenge();
               break;
            }
         }
      }

      if (challenge == null) {
         List<ChallengeCore<?>> list = (List)((IForgeRegistry)WyRegistry.CHALLENGES.get()).getValues().stream().filter((core) -> core.getDifficulty() == ChallengeDifficulty.STANDARD).collect(Collectors.toList());
         if (list.isEmpty()) {
            LogUtils.getLogger().warn("Challenges registry is empty!");
            return;
         }

         Collections.shuffle(list);
         ChallengeCore<?> possible = (ChallengeCore)list.get(0);
         challenge = possible != null ? possible : (ChallengeCore)ModChallenges.MORGAN.get();
      }

      PoneglyphBlockEntity tileEntity = (PoneglyphBlockEntity)world.m_7702_(pos);
      tileEntity.setChallenge(challenge);
   }

   @Nullable
   }
    */
    public BlockState getStateForPlacement(net.minecraft.world.item.context.BlockPlaceContext context) {
      BlockState blockstate = this.defaultBlockState();
      blockstate = (BlockState)blockstate.setValue(TEXTURE, context.getLevel().random.nextInt(3));
      return blockstate;
   }

   /* /* public net.minecraft.world.InteractionResult useWithoutItem(BlockState state, net.minecraft.world.level.Level world, BlockPos pos, net.minecraft.world.entity.player.Player player, net.minecraft.world.phys.BlockHitResult hit) {
      PoneglyphBlockEntity tileEntity = (PoneglyphBlockEntity)world.m_7702_(pos);
      if (NuWorld.isChallengeDimension(world)) {
         return InteractionResult.PASS;
      } else if (world.f_46443_) {
         return InteractionResult.SUCCESS;
      } else {
         if (tileEntity.getChallenge() == null) {
            this.initPoneglyph(world, pos);
         }

         if (tileEntity.getEntryType() == PoneglyphBlockEntity.Type.CHALLENGE) {
            boolean hasPaper = player.m_150109_().m_18949_(ImmutableSet.of(Items.f_42516_));
            if (hasPaper) {
               for(int i = 0; i < player.m_150109_().m_6643_(); ++i) {
                  ItemStack stack = player.m_150109_().m_8020_(i);
                  if (stack != null && !stack.m_41619_() && stack.m_41720_() == Items.f_42516_ && stack.m_41784_().m_128451_("type") <= 0) {
                     stack.m_41774_(1);
                     IChallengeData props = (IChallengeData)ChallengeCapability.get(player).orElse((Object)null);
                     if (props == null) {
                        return InteractionResult.FAIL;
                     }

                     ChallengeCore<?> challenge = tileEntity.getChallenge();
                     if (challenge == null) {
                        LogUtils.getLogger().warn("Poneglyph has no challenge!");
                        return InteractionResult.FAIL;
                     }

                     if (props.hasChallenge(challenge)) {
                        player.m_213846_(ModI18nChallenges.MESSAGE_ALREADY_UNLOCKED);
                     } else {
                        props.addChallenge(challenge);
                        player.m_213846_(Component.m_237110_(ModI18nChallenges.MESSAGE_UNLOCKED, new Object[]{challenge.getLocalizedTitle()}));
                     }

                     return InteractionResult.SUCCESS;
                  }
               }
            } else {
               player.m_213846_(ModI18nChallenges.MESSAGE_INSCRIPTION_NO_PAPER);
            }
         }

         return InteractionResult.SUCCESS;
      }
   }

   }
    */
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
      builder.add(new Property[]{TEXTURE});
   }

   public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
      return new PoneglyphBlockEntity(pos, state);
   }
}
