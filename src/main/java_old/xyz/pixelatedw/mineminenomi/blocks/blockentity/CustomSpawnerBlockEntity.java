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
import xyz.pixelatedw.mineminenomi.config.ServerConfig;
import xyz.pixelatedw.mineminenomi.init.ModBlockEntities;

public class CustomSpawnerBlockEntity extends BlockEntity {
   private EntityType<?> entityToSpawn;
   private int spawnLimit;
   private int playerDistance;
   private ArrayList<UUID> spawnedEntities;
   private boolean lock;

   public CustomSpawnerBlockEntity(BlockPos pos, BlockState state) {
      super((BlockEntityType)ModBlockEntities.CUSTOM_SPAWNER.get(), pos, state);
      this.entityToSpawn = EntityType.f_20510_;
      this.spawnLimit = 5;
      this.playerDistance = 100;
      this.spawnedEntities = new ArrayList();
   }

   public CustomSpawnerBlockEntity setSpawnerMob(EntityType<?> toSpawn) {
      this.entityToSpawn = toSpawn;
      this.m_6596_();
      return this;
   }

   public CustomSpawnerBlockEntity setSpawnerLimit(int limit) {
      this.spawnLimit = limit;
      this.m_6596_();
      return this;
   }

   public CustomSpawnerBlockEntity setPlayerDistance(int distance) {
      this.playerDistance = distance;
      this.m_6596_();
      return this;
   }

   public void lock() {
      this.lock = true;
   }

   private boolean isActivated() {
      BlockPos blockpos = this.m_58899_();
      return this.m_58904_().m_45914_((double)blockpos.m_123341_() + (double)0.5F, (double)blockpos.m_123342_() + (double)0.5F, (double)blockpos.m_123343_() + (double)0.5F, (double)this.playerDistance);
   }

   public static void tick(Level level, BlockPos pos, BlockState state, CustomSpawnerBlockEntity blockEntity) {
      if (level != null && !blockEntity.lock) {
         if (!level.f_46443_ && level.m_46467_() % 10L == 0L) {
            if (blockEntity.isActivated()) {
               if (level.m_8055_(blockEntity.f_58858_.m_7495_()).m_60734_() == Blocks.f_50016_) {
                  level.m_46597_(blockEntity.f_58858_, Blocks.f_50016_.m_49966_());
                  return;
               }

               if (blockEntity.spawnedEntities.size() > 0) {
                  int alive = 0;

                  for(UUID spawnUUID : blockEntity.spawnedEntities) {
                     Entity target = ((ServerLevel)level).m_8791_(spawnUUID);
                     if (target != null && target.m_6084_()) {
                        ++alive;
                     }
                  }

                  if (ServerConfig.getDestroySpawner() && alive == 0) {
                     level.m_46597_(blockEntity.f_58858_, Blocks.f_50016_.m_49966_());
                  }
               }

               if (blockEntity.entityToSpawn != null && blockEntity.spawnedEntities.size() < blockEntity.spawnLimit) {
                  CompoundTag nbt = new CompoundTag();
                  nbt.m_128379_("isSpawned", true);
                  LivingEntity newSpawn = (LivingEntity)blockEntity.entityToSpawn.m_262455_((ServerLevel)level, nbt, (Consumer)null, blockEntity.f_58858_.m_7494_(), MobSpawnType.STRUCTURE, false, false);
                  if (newSpawn != null) {
                     int r1 = (int)((level.f_46441_.m_188500_() - level.f_46441_.m_188500_()) * (double)2.0F + (double)0.5F);
                     int r2 = (int)((level.f_46441_.m_188500_() - level.f_46441_.m_188500_()) * (double)2.0F + (double)0.5F);
                     BlockPos newPos = blockEntity.m_58899_().m_7918_(r1, 0, r2);
                     newSpawn.m_7678_((double)newPos.m_123341_(), (double)newPos.m_123342_(), (double)newPos.m_123343_(), 0.0F, 0.0F);
                     blockEntity.spawnedEntities.add(newSpawn.m_20148_());
                     blockEntity.m_6596_();
                  }
               }
            } else if (blockEntity.spawnedEntities.size() > 0) {
               for(UUID spawnUUID : blockEntity.spawnedEntities) {
                  Entity target = ((ServerLevel)level).m_8791_(spawnUUID);
                  if (target != null && target.m_6084_()) {
                     target.m_142687_(RemovalReason.DISCARDED);
                  }
               }

               blockEntity.spawnedEntities.clear();
               blockEntity.m_6596_();
            }
         }

      }
   }

   public void m_183515_(CompoundTag nbt) {
      super.m_183515_(nbt);
      nbt.m_128405_("spawnLimit", this.spawnLimit);
      nbt.m_128405_("playerDistance", this.playerDistance);
      nbt.m_128359_("entityToSpawn", EntityType.m_20613_(this.entityToSpawn).toString());
      ListTag spawnedEntities = new ListTag();

      for(UUID uuid : this.spawnedEntities) {
         CompoundTag nbtEntity = new CompoundTag();
         nbtEntity.m_128362_("uuid", uuid);
         spawnedEntities.add(nbtEntity);
      }

      nbt.m_128365_("spawns", spawnedEntities);
   }

   public void m_142466_(CompoundTag nbt) {
      super.m_142466_(nbt);
      this.spawnLimit = nbt.m_128451_("spawnLimit");
      this.playerDistance = nbt.m_128451_("playerDistance");
      if (this.playerDistance <= 0) {
         this.playerDistance = 100;
      }

      this.entityToSpawn = (EntityType)EntityType.m_20632_(nbt.m_128461_("entityToSpawn")).orElse(EntityType.f_20510_);
      ListTag spawnedEntities = nbt.m_128437_("spawns", 10);

      for(int i = 0; i < spawnedEntities.size(); ++i) {
         CompoundTag nbtEntity = spawnedEntities.m_128728_(i);
         UUID nbtUUID = nbtEntity.m_128342_("uuid");
         this.spawnedEntities.add(nbtUUID);
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
