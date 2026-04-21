package xyz.pixelatedw.mineminenomi.blocks.blockentity;

import java.util.ArrayList;
import java.util.UUID;
import java.util.function.Consumer;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
// import xyz.pixelatedw.mineminenomi.config.ServerConfig;
import xyz.pixelatedw.mineminenomi.init.ModBlockEntities;

public class CustomSpawnerBlockEntity extends BlockEntity {
   private EntityType<?> entityToSpawn;
   private int spawnLimit;
   private int playerDistance;
   private ArrayList<UUID> spawnedEntities;
   private boolean lock;

   public CustomSpawnerBlockEntity(BlockPos pos, BlockState state) {
      super((BlockEntityType)ModBlockEntities.CUSTOM_SPAWNER.get(), pos, state);
      this.entityToSpawn = net.minecraft.world.entity.EntityType.PIG;
      this.spawnLimit = 5;
      this.playerDistance = 100;
      this.spawnedEntities = new ArrayList();
   }

   public CustomSpawnerBlockEntity setSpawnerMob(EntityType<?> toSpawn) {
      this.entityToSpawn = toSpawn;
      this.setChanged();
      return this;
   }

   public CustomSpawnerBlockEntity setSpawnerLimit(int limit) {
      this.spawnLimit = limit;
      this.setChanged();
      return this;
   }

   public CustomSpawnerBlockEntity setPlayerDistance(int distance) {
      this.playerDistance = distance;
      this.setChanged();
      return this;
   }

   public void lock() {
      this.lock = true;
   }

   private boolean isActivated() {
      BlockPos blockpos = this.getBlockPos();
      return false;
   }

   public static void tick(Level level, BlockPos pos, BlockState state, CustomSpawnerBlockEntity blockEntity) {
      if (level != null && !blockEntity.lock) {
         if (!level.isClientSide() && level.getGameTime() % 10L == 0L) {
            if (blockEntity.isActivated()) {
               if (level.getBlockState(blockEntity.getBlockPos().below()).getBlock() == net.minecraft.world.level.block.Blocks.AIR) {
                  level.setBlockAndUpdate(blockEntity.getBlockPos(), net.minecraft.world.level.block.Blocks.AIR.defaultBlockState());
                  return;
               }

               if (blockEntity.spawnedEntities.size() > 0) {
                  int alive = 0;

                  for(UUID spawnUUID : blockEntity.spawnedEntities) {
                     Entity target = ((ServerLevel)level).getEntity(spawnUUID);
                     if (target != null && target.isAlive()) {
                        ++alive;
                     }
                  }

                  if (false && alive == 0) {
                     level.setBlockAndUpdate(blockEntity.getBlockPos(), net.minecraft.world.level.block.Blocks.AIR.defaultBlockState());
                  }
               }

               if (blockEntity.entityToSpawn != null && blockEntity.spawnedEntities.size() < blockEntity.spawnLimit) {
                  CompoundTag nbt = new CompoundTag();
                  nbt.putBoolean("isSpawned", true);
                  LivingEntity newSpawn = (LivingEntity)blockEntity.entityToSpawn.spawn((ServerLevel)level, blockEntity.getBlockPos().below(), MobSpawnType.STRUCTURE);
                  if (newSpawn != null) {
                     int r1 = (int)((level.random.nextFloat() - level.random.nextFloat()) * (double)2.0F + (double)0.5F);
                     int r2 = (int)((level.random.nextFloat() - level.random.nextFloat()) * (double)2.0F + (double)0.5F);
                     BlockPos newPos = blockEntity.getBlockPos().offset(r1, 0, r2);
                     newSpawn.moveTo((double)newPos.getX(), (double)newPos.getY(), (double)newPos.getZ(), 0.0F, 0.0F);
                     blockEntity.spawnedEntities.add(newSpawn.getUUID());
                     blockEntity.setChanged();
                  }
               }
            } else if (blockEntity.spawnedEntities.size() > 0) {
               for(UUID spawnUUID : blockEntity.spawnedEntities) {
                  Entity target = ((ServerLevel)level).getEntity(spawnUUID);
                  if (target != null && target.isAlive()) {
                     target.discard();
                  }
               }

               blockEntity.spawnedEntities.clear();
               blockEntity.setChanged();
            }
         }

      }
   }

   public void saveAdditional(CompoundTag nbt, net.minecraft.core.HolderLookup.Provider provider) {
      super.saveAdditional(nbt, provider);
      nbt.putInt("spawnLimit", this.spawnLimit);
      nbt.putInt("playerDistance", this.playerDistance);
      nbt.putString("entityToSpawn", net.minecraft.core.registries.BuiltInRegistries.ENTITY_TYPE.getKey(this.entityToSpawn).toString());
      ListTag spawnedEntities = new ListTag();

      for(UUID uuid : this.spawnedEntities) {
         CompoundTag nbtEntity = new CompoundTag();
         nbtEntity.putUUID("uuid", uuid);
         spawnedEntities.add(nbtEntity);
      }

      nbt.put("spawns", spawnedEntities);
   }

   public void loadAdditional(CompoundTag nbt, net.minecraft.core.HolderLookup.Provider provider) {
      super.loadAdditional(nbt, provider);
      this.spawnLimit = nbt.getInt("spawnLimit");
      this.playerDistance = nbt.getInt("playerDistance");
      if (this.playerDistance <= 0) {
         this.playerDistance = 100;
      }

      this.entityToSpawn = (EntityType)net.minecraft.core.registries.BuiltInRegistries.ENTITY_TYPE.getOptional(net.minecraft.resources.ResourceLocation.parse(nbt.getString("entityToSpawn"))).orElse(net.minecraft.world.entity.EntityType.PIG);
      ListTag spawnedEntities = nbt.getList("spawns", 10);

      for(int i = 0; i < spawnedEntities.size(); ++i) {
         CompoundTag nbtEntity = spawnedEntities.getCompound(i);
         UUID nbtUUID = nbtEntity.getUUID("uuid");
         this.spawnedEntities.add(nbtUUID);
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
