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

   public static void tick(Level level, BlockPos pos, BlockState state, CustomSpawnerBlockEntity blockEntity) {
   }

   public void setWantedPosterData(WantedPosterData data) {
      this.wantedPosterData = data;
   }

   public WantedPosterData getWantedPosterData() {
      if (this.wantedPosterData == null) {
         this.wantedPosterData = WantedPosterData.empty();
      }

      return this.wantedPosterData;
   }

   public boolean isPirate() {
      return (Boolean)this.wantedPosterData.getFaction().map((f) -> f.equals(ModFactions.PIRATE.get())).orElse(false);
   }

   public boolean isRevolutionary() {
      return (Boolean)this.wantedPosterData.getFaction().map((f) -> f.equals(ModFactions.REVOLUTIONARY_ARMY.get())).orElse(false);
   }

   public void m_183515_(CompoundTag nbt) {
      super.m_183515_(nbt);
      if (this.wantedPosterData != null) {
         nbt.m_128365_("WPData", this.wantedPosterData.write());
      }
   }

   public void m_142466_(CompoundTag nbt) {
      super.m_142466_(nbt);
      if (nbt.m_128441_("WPData")) {
         this.wantedPosterData = WantedPosterData.from(nbt.m_128469_("WPData"));
      }

   }

   public CompoundTag m_5995_() {
      CompoundTag tag = super.m_5995_();
      this.m_183515_(tag);
      return tag;
   }

   public ClientboundBlockEntityDataPacket getUpdatePacket() {
      return ClientboundBlockEntityDataPacket.m_195640_(this);
   }
}
