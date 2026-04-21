package xyz.pixelatedw.mineminenomi.blocks.blockentity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.LongArrayTag;
import net.minecraft.nbt.LongTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
// import net.neoforged.neoforge.registries.IForgeRegistry;
// import xyz.pixelatedw.mineminenomi.api.WyRegistry;
import xyz.pixelatedw.mineminenomi.api.crew.Crew;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Faction;
import xyz.pixelatedw.mineminenomi.api.enums.CanvasSize;
import xyz.pixelatedw.mineminenomi.blocks.FlagBlock;
// import xyz.pixelatedw.mineminenomi.data.entity.stats.IEntityStats;
// import xyz.pixelatedw.mineminenomi.data.entity.stats.IEntityStats;
import xyz.pixelatedw.mineminenomi.init.ModBlockEntities;
import xyz.pixelatedw.mineminenomi.init.ModBlocks;
import xyz.pixelatedw.mineminenomi.items.FlagItem;

public class FlagBlockEntity extends BlockEntity {
   private UUID ownerUUID;
   private Optional<Faction> faction;
   private boolean isOnFire;
   private int fireTick;
   @Nullable
   private Entity lastAttacker;
   private BlockPos masterPos;
   private FlagBlockEntity master;
   private boolean isSub;
   private List<BlockPos> subsList;
   private Optional<Crew> crew;

   public FlagBlockEntity(BlockPos pos, BlockState state) {
      super((BlockEntityType)ModBlockEntities.FLAG.get(), pos, state);
      this.ownerUUID = net.minecraft.Util.NIL_UUID;
      this.faction = Optional.empty();
      this.fireTick = 200;
      this.subsList = new ArrayList();
      this.crew = Optional.empty();
   }

   public void setCrew(Crew crew) {
      this.crew = Optional.ofNullable(crew);
   }

   @Nullable
   public Optional<Crew> getCrew() {
      return this.crew;
   }

   public void setOnFire(boolean onFire) {
      this.isOnFire = onFire;
      if (onFire) {
         this.fireTick = 200;
      }

      this.setChanged();
   }

   public boolean isOnFire() {
      return this.isOnFire;
   }

   public void setLastAttacker(Entity attacker) {
      this.lastAttacker = attacker;
   }

   @Nullable
   public Entity getLastAttacker() {
      return this.lastAttacker;
   }

   public void setFaction(Faction factionName) {
      this.faction = Optional.ofNullable(factionName);
      this.setChanged();
   }

   public void setOwner(@Nullable LivingEntity owner) {
      if (owner != null) {
         this.ownerUUID = owner.getUUID();
         // this.faction = (Optional)EntityStatsCapability.get(owner).map(IEntityStats::getFaction).orElse(Optional.empty());
         this.setChanged();
      }

   }

   public Optional<Faction> getFaction() {
      return this.faction;
   }

   public UUID getOwnerUUID() {
      return this.ownerUUID;
   }

   public boolean isMaster() {
      return !this.isSub();
   }

   public boolean isSub() {
      return this.isSub;
   }

   @Nullable
   public FlagBlockEntity getMaster() {
      if (!this.isSub) {
         return this;
      } else {
         if (this.level != null && this.masterPos != null && this.master == null) {
            BlockEntity tileEntity = this.level.getBlockEntity(this.masterPos);
            if (tileEntity instanceof FlagBlockEntity) {
               this.master = (FlagBlockEntity)tileEntity;
            }
         }

         return this.master;
      }
   }

   public void setMaster(FlagBlockEntity flagTile) {
      this.masterPos = flagTile.getBlockPos();
      this.master = flagTile;
      this.isSub = true;
   }

   public void addSub(BlockPos pos) {
      this.subsList.add(pos);
   }

   public static void tick(Level level, BlockPos pos, BlockState state, FlagBlockEntity blockEntity) {
      if (blockEntity.level != null) {
         if (blockEntity.isOnFire()) {
            if (blockEntity.isSub) {
               if (blockEntity.level.isClientSide()) {
                  blockEntity.master.setOnFire(false);
                  return;
               }

               blockEntity.master.setOnFire(true);
            } else {
               if (blockEntity.level.isClientSide()) {
                  blockEntity.setOnFire(false);
                  return;
               }

               if (--blockEntity.fireTick <= 0) {
                  blockEntity.breakAllBlocks(false);
               }
            }
         }

      }
   }

   public void breakAllBlocks(boolean instabuild) {
      if (!this.isSub()) {
         boolean drop = !this.isOnFire;
         if (instabuild) {
            drop = false;
         }

         if (this.getLevel() != null) {
            if (drop) {
               BlockState masterState = this.level.getBlockState(this.getBlockPos());
               if (!masterState.isAir()) {
                  ItemStack flagStack = new ItemStack((ItemLike)ModBlocks.FLAG.get());
                  if (masterState.hasProperty(FlagBlock.SIZE)) {
                     // FlagItem.setCanvasSize(flagStack, masterState.getValue(FlagBlock.SIZE));
                  }

                  ItemEntity itemDrop = new ItemEntity(this.level, (double)this.getBlockPos().getX(), (double)this.getBlockPos().getY(), (double)this.getBlockPos().getZ(), flagStack);
                  this.level.addFreshEntity(itemDrop);
               }
            }

            this.getLevel().destroyBlock(this.getBlockPos(), false);

            for(BlockPos pos : this.subsList) {
               this.getLevel().destroyBlock(pos, false);
            }
         }

      }
   }

   public void saveAdditional(CompoundTag nbt, net.minecraft.core.HolderLookup.Provider provider) {
      super.saveAdditional(nbt, provider);
      nbt.putUUID("ownerUUID", this.ownerUUID);
      this.faction.ifPresent((f) -> nbt.putString("faction", f.getRegistryName().toString()));
      nbt.putBoolean("isOnFire", this.isOnFire);
      nbt.putInt("fireTicks", this.fireTick);
      nbt.putBoolean("isSub", this.isSub);
      if (this.isSub) {
         nbt.putLong("masterPos", this.masterPos.asLong());
      } else {
         LongArrayTag subs = new LongArrayTag(new ArrayList());

         for(BlockPos pos : this.subsList) {
            long longPos = pos.asLong();
            subs.add(LongTag.valueOf(longPos));
         }

         nbt.put("subs", subs);
      }

      this.crew.ifPresent((crew) -> nbt.put("Crew", crew.write()));
   }

   public void loadAdditional(CompoundTag nbt, net.minecraft.core.HolderLookup.Provider provider) {
      super.loadAdditional(nbt, provider);
      this.ownerUUID = nbt.getUUID("ownerUUID");
      if (nbt.contains("faction")) {
         // Faction fac = null;
         // this.faction = Optional.ofNullable(fac);
      }

      this.isOnFire = nbt.getBoolean("isOnFire");
      this.fireTick = nbt.getInt("fireTicks");
      this.isSub = nbt.getBoolean("isSub");
      if (this.isSub) {
         this.masterPos = BlockPos.of(nbt.getLong("masterPos"));
      } else {
         long[] subs = nbt.getLongArray("subs");

         for(int i = 0; i < subs.length; ++i) {
            BlockPos subPos = BlockPos.of(subs[i]);
            this.subsList.add(subPos);
         }
      }

      if (nbt.contains("Crew", 10)) {
         Crew crew = Crew.from(nbt.getCompound("Crew"));
         this.setCrew(crew);
      }

      this.setChanged();
   }

   public CompoundTag getUpdateTag(net.minecraft.core.HolderLookup.Provider provider) {
      CompoundTag tag = super.getUpdateTag(provider);
      this.saveAdditional(tag, provider);
      return tag;
   }

   @Nullable
   public ClientboundBlockEntityDataPacket getUpdatePacket() {
      return net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket.create(this);
   }
}
