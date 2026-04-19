package xyz.pixelatedw.mineminenomi.api.events;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.eventbus.api.Cancelable;
import net.minecraftforge.eventbus.api.Event;
import xyz.pixelatedw.mineminenomi.blocks.blockentity.FlagBlockEntity;

@Cancelable
public class FlagPlacedEvent extends Event {
   private Player placer;
   private BlockState state;
   private BlockPos pos;
   private FlagBlockEntity flagTile;

   public FlagPlacedEvent(Player placer, BlockState state, BlockPos pos, FlagBlockEntity flagTile) {
      this.placer = placer;
      this.state = state;
      this.pos = pos;
      this.flagTile = flagTile;
   }

   public Player getPlacer() {
      return this.placer;
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
