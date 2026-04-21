package xyz.pixelatedw.mineminenomi.blocks.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import xyz.pixelatedw.mineminenomi.init.ModBlockEntities;

public class PoneglyphBlockEntity extends BlockEntity {
   private Type entryType;

   public PoneglyphBlockEntity(BlockPos pos, BlockState state) {
      super(ModBlockEntities.PONEGLYPH.get(), pos, state);
      this.entryType = Type.NONE;
   }

   public Type getEntryType() {
      return this.entryType;
   }

   public void setEntryType(Type entryType) {
      this.entryType = entryType;
   }

   @Override
   public void saveAdditional(CompoundTag nbt, HolderLookup.Provider provider) {
      super.saveAdditional(nbt, provider);
      nbt.putString("Type", this.entryType.name());
   }

   @Override
   public void loadAdditional(CompoundTag nbt, HolderLookup.Provider provider) {
      super.loadAdditional(nbt, provider);
      if (nbt.contains("Type")) {
         this.entryType = Type.valueOf(nbt.getString("Type"));
      }
   }

   @Override
   public CompoundTag getUpdateTag(HolderLookup.Provider provider) {
      CompoundTag tag = new CompoundTag();
      this.saveAdditional(tag, provider);
      return tag;
   }

   @Override
   public ClientboundBlockEntityDataPacket getUpdatePacket() {
      return ClientboundBlockEntityDataPacket.create(this);
   }

   public static enum Type {
      NONE,
      HISTORY,
      ROAD,
      INSTRUCTION;
   }
}
