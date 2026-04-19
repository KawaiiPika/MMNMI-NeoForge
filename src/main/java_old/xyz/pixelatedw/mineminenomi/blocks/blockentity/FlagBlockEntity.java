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
import net.minecraftforge.registries.IForgeRegistry;
import xyz.pixelatedw.mineminenomi.api.WyRegistry;
import xyz.pixelatedw.mineminenomi.api.crew.Crew;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Faction;
import xyz.pixelatedw.mineminenomi.api.enums.CanvasSize;
import xyz.pixelatedw.mineminenomi.blocks.FlagBlock;
import xyz.pixelatedw.mineminenomi.data.entity.stats.EntityStatsCapability;
import xyz.pixelatedw.mineminenomi.data.entity.stats.IEntityStats;
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
      this.ownerUUID = Util.f_137441_;
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

      this.m_6596_();
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
      this.m_6596_();
   }

   public void setOwner(@Nullable LivingEntity owner) {
      if (owner != null) {
         this.ownerUUID = owner.m_20148_();
         this.faction = (Optional)EntityStatsCapability.get(owner).map(IEntityStats::getFaction).orElse(Optional.empty());
         this.m_6596_();
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
         if (this.f_58857_ != null && this.masterPos != null && this.master == null) {
            BlockEntity tileEntity = this.f_58857_.m_7702_(this.masterPos);
            if (tileEntity instanceof FlagBlockEntity) {
               this.master = (FlagBlockEntity)tileEntity;
            }
         }

         return this.master;
      }
   }

   public void setMaster(FlagBlockEntity flagTile) {
      this.masterPos = flagTile.m_58899_();
      this.master = flagTile;
      this.isSub = true;
   }

   public void addSub(BlockPos pos) {
      this.subsList.add(pos);
   }

   public static void tick(Level level, BlockPos pos, BlockState state, FlagBlockEntity blockEntity) {
      if (blockEntity.f_58857_ != null) {
         if (blockEntity.isOnFire()) {
            if (blockEntity.isSub) {
               if (blockEntity.f_58857_.m_46471_()) {
                  blockEntity.master.setOnFire(false);
                  return;
               }

               blockEntity.master.setOnFire(true);
            } else {
               if (blockEntity.f_58857_.m_46471_()) {
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

         if (this.m_58904_() != null) {
            if (drop) {
               BlockState masterState = this.f_58857_.m_8055_(this.m_58899_());
               if (!masterState.m_60795_()) {
                  ItemStack flagStack = new ItemStack((ItemLike)ModBlocks.FLAG.get());
                  if (masterState.m_61138_(FlagBlock.SIZE)) {
                     FlagItem.setCanvasSize(flagStack, (CanvasSize)masterState.m_61143_(FlagBlock.SIZE));
                  }

                  ItemEntity itemDrop = new ItemEntity(this.f_58857_, (double)this.m_58899_().m_123341_(), (double)this.m_58899_().m_123342_(), (double)this.m_58899_().m_123343_(), flagStack);
                  this.f_58857_.m_7967_(itemDrop);
               }
            }

            this.m_58904_().m_46961_(this.m_58899_(), false);

            for(BlockPos pos : this.subsList) {
               this.m_58904_().m_46961_(pos, false);
            }
         }

      }
   }

   public void m_183515_(CompoundTag nbt) {
      super.m_183515_(nbt);
      nbt.m_128362_("ownerUUID", this.ownerUUID);
      this.faction.ifPresent((f) -> nbt.m_128359_("faction", f.getRegistryName().toString()));
      nbt.m_128379_("isOnFire", this.isOnFire);
      nbt.m_128405_("fireTicks", this.fireTick);
      nbt.m_128379_("isSub", this.isSub);
      if (this.isSub) {
         nbt.m_128356_("masterPos", this.masterPos.m_121878_());
      } else {
         LongArrayTag subs = new LongArrayTag(new ArrayList());

         for(BlockPos pos : this.subsList) {
            long longPos = pos.m_121878_();
            subs.add(LongTag.m_128882_(longPos));
         }

         nbt.m_128365_("subs", subs);
      }

      this.crew.ifPresent((crew) -> nbt.m_128365_("Crew", crew.write()));
   }

   public void m_142466_(CompoundTag nbt) {
      super.m_142466_(nbt);
      this.ownerUUID = nbt.m_128342_("ownerUUID");
      if (nbt.m_128441_("faction")) {
         Faction fac = (Faction)((IForgeRegistry)WyRegistry.FACTIONS.get()).getValue(ResourceLocation.parse(nbt.m_128461_("faction")));
         this.faction = Optional.ofNullable(fac);
      }

      this.isOnFire = nbt.m_128471_("isOnFire");
      this.fireTick = nbt.m_128451_("fireTicks");
      this.isSub = nbt.m_128471_("isSub");
      if (this.isSub) {
         this.masterPos = BlockPos.m_122022_(nbt.m_128454_("masterPos"));
      } else {
         long[] subs = nbt.m_128467_("subs");

         for(int i = 0; i < subs.length; ++i) {
            BlockPos subPos = BlockPos.m_122022_(subs[i]);
            this.subsList.add(subPos);
         }
      }

      if (nbt.m_128425_("Crew", 10)) {
         Crew crew = Crew.from(nbt.m_128469_("Crew"));
         this.setCrew(crew);
      }

      this.m_6596_();
   }

   public CompoundTag m_5995_() {
      CompoundTag tag = super.m_5995_();
      this.m_183515_(tag);
      return tag;
   }

   @Nullable
   public ClientboundBlockEntityDataPacket getUpdatePacket() {
      return ClientboundBlockEntityDataPacket.m_195640_(this);
   }
}
