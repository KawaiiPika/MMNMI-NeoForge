package xyz.pixelatedw.mineminenomi.entities.projectiles;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.TheEndGatewayBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult.Type;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.event.ForgeEventFactory;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceElement;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.damagesources.TargetPredicate;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.ItemsHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.TargetHelper;
import xyz.pixelatedw.mineminenomi.init.ModEntities;

public class ThrowingWeaponEntity extends NuProjectileEntity {
   private ItemStack itemStack;
   private boolean isStuck;
   private float zRot;

   public ThrowingWeaponEntity(EntityType<? extends ThrowingWeaponEntity> type, Level world) {
      super(type, world);
      this.itemStack = ItemStack.f_41583_;
   }

   public ThrowingWeaponEntity(Level world, LivingEntity thrower) {
      this(world, thrower, thrower.m_21205_().m_41777_());
   }

   public ThrowingWeaponEntity(Level world, LivingEntity thrower, ItemStack stack) {
      super((EntityType)ModEntities.THROWING_WEAPON.get(), world, thrower, (IAbility)null, SourceElement.NONE, SourceHakiNature.IMBUING, SourceType.BLUNT);
      this.itemStack = ItemStack.f_41583_;
      this.itemStack = stack;
      float damage = ItemsHelper.getItemDamage(this.itemStack);
      this.setDamage(damage * 1.2F);
      this.setMaxLife(1200);
      this.setPassThroughEntities();
      this.setPassThroughBlocks();
      this.setEntityCollisionSize((double)1.5F, (double)1.5F, (double)1.5F);
      this.addBlockHitEvent(100, this::onBlockImpactEvent);
   }

   public void m_8119_() {
      HitResult raytraceresult = ProjectileUtil.m_278158_(this, (x$0) -> this.m_5603_(x$0));
      boolean flag = false;
      if (raytraceresult.m_6662_() == Type.BLOCK) {
         BlockPos blockpos = ((BlockHitResult)raytraceresult).m_82425_();
         BlockState blockstate = this.m_9236_().m_8055_(blockpos);
         if (blockstate.m_60713_(Blocks.f_50142_)) {
            this.m_20221_(blockpos);
            flag = true;
         } else if (blockstate.m_60713_(Blocks.f_50446_)) {
            BlockEntity tileentity = this.m_9236_().m_7702_(blockpos);
            if (tileentity instanceof TheEndGatewayBlockEntity && TheEndGatewayBlockEntity.m_59940_(this)) {
               TheEndGatewayBlockEntity.m_155828_(this.m_9236_(), blockpos, blockstate, this, (TheEndGatewayBlockEntity)tileentity);
            }

            flag = true;
         }
      }

      if (raytraceresult.m_6662_() == Type.ENTITY) {
         this.m_6034_(this.m_20185_(), this.m_20186_(), this.m_20189_());
         AbilityHelper.setDeltaMovement(this, (double)0.0F, (double)0.0F, (double)0.0F);
      }

      if (raytraceresult.m_6662_() != Type.MISS && !flag && !ForgeEventFactory.onProjectileImpact(this, raytraceresult)) {
         this.m_6532_(raytraceresult);
      }

      BlockPos blockpos = this.m_20183_();
      BlockState blockstate = this.m_9236_().m_8055_(blockpos);
      if (!blockstate.m_60795_()) {
         VoxelShape voxelshape = blockstate.m_60812_(this.m_9236_(), blockpos);
         if (!voxelshape.m_83281_()) {
            Vec3 vector3d1 = this.m_20182_();

            for(AABB axisalignedbb : voxelshape.m_83299_()) {
               if (axisalignedbb.m_82338_(blockpos).m_82390_(vector3d1)) {
                  this.m_6034_(this.m_20185_(), this.m_20186_() + (double)1.0F, this.m_20189_());
                  this.isStuck = true;
                  break;
               }
            }
         }
      }

      if (!this.isStuck) {
         this.zRot = (float)(this.f_19797_ * 50 % 360);
         Vec3 motion = this.m_20184_();
         double motionX = motion.f_82479_;
         double motionY = motion.f_82480_;
         double motionZ = motion.f_82481_;
         float horDist = Mth.m_14116_((float)motion.m_165925_());
         double newPosX = this.m_20185_() + motionX;
         double newPosY = this.m_20186_() + motionY;
         double newPosZ = this.m_20189_() + motionZ;
         this.m_146922_((float)(Mth.m_14136_(motionX, motionZ) * (double)(180F / (float)Math.PI)));
         this.m_146926_((float)(Mth.m_14136_(motionY, (double)horDist) * (double)(180F / (float)Math.PI)));
         this.m_146926_(m_37273_(this.f_19860_, this.m_146909_()));
         this.m_146922_(m_37273_(this.f_19859_, this.m_146908_()));
         AbilityHelper.setDeltaMovement(this, motion.m_82490_((double)0.99F));
         motion = this.m_20184_();
         AbilityHelper.setDeltaMovement(this, motion.f_82479_, motion.f_82480_ - (double)this.m_7139_(), motion.f_82481_);
         this.m_6034_(newPosX, newPosY, newPosZ);
      } else {
         this.tickDespawn();
         this.zRot = -170.0F;
         if (!this.m_9236_().f_46443_) {
            boolean shouldFall = this.m_9236_().m_45772_((new AABB(this.m_20182_(), this.m_20182_())).m_82400_((double)0.5F));
            if (shouldFall) {
               this.isStuck = false;
               Vec3 vector3d = this.m_20184_();
               AbilityHelper.setDeltaMovement(this, vector3d.m_82542_((double)(this.f_19796_.m_188501_() * 0.2F), (double)(this.f_19796_.m_188501_() * 0.2F), (double)(this.f_19796_.m_188501_() * 0.2F)));
            }
         }

         if (!this.m_9236_().f_46443_) {
            AABB aabb = (new AABB(this.m_20183_())).m_82377_((double)1.5F, (double)1.5F, (double)1.5F);

            for(LivingEntity living : TargetHelper.getEntitiesInArea(this.m_9236_(), (LivingEntity)null, aabb, TargetPredicate.EVERYTHING, LivingEntity.class)) {
               boolean isOwner = this.getOwner() != null && this.getOwner().m_20148_() == living.m_20148_();
               if (isOwner && ItemsHelper.giveItemStackToEntity(living, this.itemStack, EquipmentSlot.MAINHAND)) {
                  this.m_146870_();
               }
            }
         }
      }

   }

   private void onBlockImpactEvent(BlockHitResult result) {
      this.isStuck = true;
   }

   public void m_6074_() {
      if (this.isStuck) {
         super.m_6074_();
      } else {
         this.isStuck = true;
      }

   }

   public ItemStack getItem() {
      return this.itemStack;
   }

   protected float m_7139_() {
      return 0.05F;
   }

   public float getRotation() {
      return this.zRot;
   }

   public void writeSpawnData(FriendlyByteBuf buffer) {
      super.writeSpawnData(buffer);
      buffer.m_130055_(this.itemStack);
   }

   public void readSpawnData(FriendlyByteBuf buffer) {
      super.readSpawnData(buffer);
      this.itemStack = buffer.m_130267_();
   }
}
