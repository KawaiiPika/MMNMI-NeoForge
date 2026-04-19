package xyz.pixelatedw.mineminenomi.api.events;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.eventbus.api.Event;
import xyz.pixelatedw.mineminenomi.blocks.blockentity.FlagBlockEntity;

public class FlagDestroyedEvent extends Event {
   @Nullable
   private Entity destroyer;
   private BlockState state;
   private BlockPos pos;
   private FlagBlockEntity flagTile;

   public FlagDestroyedEvent(BlockState state, BlockPos pos, @Nullable Entity destroyer, FlagBlockEntity flagTile) {
      this.destroyer = destroyer;
      this.state = state;
      this.pos = pos;
      this.flagTile = flagTile;
   }

   @Nullable
   public Entity getDestroyer() {
      return this.destroyer;
   }

   public BlockState getState() {
      return this.state;
   }

   public BlockPos getPos() {
      return this.pos;
   }

   public FlagBlockEntity getTile() {
      return this.flagTile;
   }
}
