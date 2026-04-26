package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.zushi;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.BlockHitResult;
import xyz.pixelatedw.mineminenomi.api.entities.projectiles.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.init.ModEntities;
import net.neoforged.neoforge.entity.IEntityWithComplexSpawn;
import net.minecraft.network.RegistryFriendlyByteBuf;

public class SagariNoRyuseiProjectile extends NuProjectileEntity implements IEntityWithComplexSpawn {
   private float size;

   public SagariNoRyuseiProjectile(EntityType<? extends SagariNoRyuseiProjectile> type, Level world) {
      super(type, world);
   }

   public SagariNoRyuseiProjectile(Level world, LivingEntity player) {
      super(ModEntities.SAGARI_NO_RYUSEI_PROJECTILE.get(), player, world);
      this.setDamage(200.0F);
   }

   @Override
   protected void onHitBlock(BlockHitResult result) {
      super.onHitBlock(result);
      if (!this.level().isClientSide) {
         float mult = this.size / 30.0F;
         this.level().explode(this.getOwner(), this.getX(), this.getY(), this.getZ(), 20.0F * mult, true, Level.ExplosionInteraction.MOB);

         int numBlocks = (int)(20 * mult);
         for(int i = 0; i < numBlocks; ++i) {
            FallingBlockEntity entity = FallingBlockEntity.fall(this.level(), this.blockPosition().above(), Blocks.DIRT.defaultBlockState());
            entity.setDeltaMovement((this.random.nextDouble() - 0.5) * mult, (0.75 + this.random.nextDouble()) * mult, (this.random.nextDouble() - 0.5) * mult);
            entity.dropItem = false;
            entity.time = 1;
         }
         this.discard();
      }
   }

   @Override
   public void tick() {
      super.tick();
      float mult = this.size / 30.0F;

      if (!this.level().isClientSide) {
         for(int i = 0; i < 5; ++i) {
            double offsetX = (this.random.nextDouble() - 0.5) * 5.0 * mult;
            double offsetY = this.random.nextDouble();
            double offsetZ = (this.random.nextDouble() - 0.5) * 5.0 * mult;
            ((ServerLevel)this.level()).sendParticles(ParticleTypes.CAMPFIRE_COSY_SMOKE, this.getX() + offsetX, this.getY() + offsetY, this.getZ() + offsetZ, 1, 0, 0, 0, 0);
            ((ServerLevel)this.level()).sendParticles(ParticleTypes.FLAME, this.getX() + offsetX, this.getY() + offsetY, this.getZ() + offsetZ, 1, 0, 0, 0, 0);
         }
      }
   }

   public void setSize(float size) {
      this.size = size;
   }

   @Override
   public void writeSpawnData(RegistryFriendlyByteBuf buffer) {
      buffer.writeFloat(this.size);
   }

   @Override
   public void readSpawnData(RegistryFriendlyByteBuf buffer) {
      this.size = buffer.readFloat();
   }
}
