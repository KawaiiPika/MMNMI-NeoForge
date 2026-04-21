package xyz.pixelatedw.mineminenomi.blocks.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import xyz.pixelatedw.mineminenomi.api.entities.WantedPosterData;
import xyz.pixelatedw.mineminenomi.init.ModBlockEntities;
import xyz.pixelatedw.mineminenomi.init.ModFactions;

public class WantedPosterBlockEntity extends BlockEntity {
   private WantedPosterData wantedPosterData;

   public WantedPosterBlockEntity(BlockPos pos, BlockState state) {
      super((BlockEntityType)ModBlockEntities.WANTED_POSTER.get(), pos, state);
   }

   public static void tick(Level level, BlockPos pos, BlockState state, WantedPosterBlockEntity blockEntity) {
   }

   public void setWantedPosterData(WantedPosterData data) {
      this.wantedPosterData = data;
   }

   public WantedPosterData getWantedPosterData() {
      if (this.wantedPosterData == null) {
         // // this.wantedPosterData = WantedPosterData.empty();
      }

      return this.wantedPosterData;
   }

   public boolean isPirate() {
      return true;
   }

   public boolean isRevolutionary() {
      return false;
   }

   public void saveAdditional(CompoundTag nbt, net.minecraft.core.HolderLookup.Provider provider) {
      super.saveAdditional(nbt, provider);
      if (this.wantedPosterData != null) {
         nbt.put("WPData", this.wantedPosterData.write());
      }
   }

   public void loadAdditional(CompoundTag nbt, net.minecraft.core.HolderLookup.Provider provider) {
      super.loadAdditional(nbt, provider);
      if (nbt.contains("WPData")) {
         this.wantedPosterData = WantedPosterData.from(nbt.getCompound("WPData"));
      }

   }

   public CompoundTag getUpdateTag(net.minecraft.core.HolderLookup.Provider provider) {
      CompoundTag tag = super.getUpdateTag(provider);
      this.saveAdditional(tag, provider);
      return tag;
   }

   public ClientboundBlockEntityDataPacket getUpdatePacket() {
      return net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket.create(this);
   }
}
