package xyz.pixelatedw.mineminenomi.blocks;

import java.util.function.Function;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.EntityCollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public abstract class TrapAbilityBlock extends Block {
   private int damageDealt = 0;
   private double horizontalFallSpeed = 0.05;
   private double verticalFallSpeed = (double)0.25F;
   private Function<Level, DamageSource> damageSource;
   private Supplier<MobEffectInstance> potionEffect = null;

   public TrapAbilityBlock(BlockBehaviour.Properties props) {
      super(props);
   }

   public abstract boolean canWalkOn(LivingEntity var1);

   public void entityInside(BlockState state, net.minecraft.world.level.Level worldIn, BlockPos pos, Entity entity) {
      if (entity instanceof LivingEntity living) {
         if (!this.canWalkOn(living)) {
            entity.makeStuckInBlock(state, new Vec3(this.getHorizontalFallSpeed(), this.getVerticalFallSpeed(), this.getHorizontalFallSpeed()));
         }
      } else if (entity instanceof Boat boat) {
         boat.getWaterLevelAbove();
      } else {
         entity.makeStuckInBlock(state, new Vec3(this.getVerticalFallSpeed(), this.getHorizontalFallSpeed(), this.getVerticalFallSpeed()));
      }

   }

   public net.minecraft.world.phys.shapes.VoxelShape getCollisionShape(BlockState state, BlockGetter worldIn, BlockPos pos, net.minecraft.world.phys.shapes.CollisionContext context) {
      if (context instanceof EntityCollisionContext collisionContext) {
         Entity entity = collisionContext.getEntity();
         if (entity instanceof LivingEntity living) {
            if (this.canWalkOn(living)) {
               return state.getShape(worldIn, pos);
            }
         }
      }

      return Shapes.empty();
   }

   public int getDamageDealt() {
      return this.damageDealt;
   }

   public void setDamageDealt(int damageDealt) {
      this.damageDealt = damageDealt;
   }

   public double getVerticalFallSpeed() {
      return this.verticalFallSpeed;
   }

   public void setVerticalFallSpeed(double verticalFallSpeed) {
      this.verticalFallSpeed = verticalFallSpeed;
   }

   @Nullable
   public DamageSource getDamageSource(Level level) {
      return this.damageSource == null ? null : (DamageSource)this.damageSource.apply(level);
   }

   public void setDamageSource(Function<Level, DamageSource> damageSource) {
      this.damageSource = damageSource;
   }

   public MobEffectInstance getPotionEffect() {
      return (MobEffectInstance)this.potionEffect.get();
   }

   public void setPotionEffect(Supplier<MobEffectInstance> potionEffect) {
      this.potionEffect = potionEffect;
   }

   public boolean hasAnalogOutputSignal(BlockState state) {
      return false;
   }

   public double getHorizontalFallSpeed() {
      return this.horizontalFallSpeed;
   }

   public void setHorizontalFallSpeed(double horizontalFallSpeed) {
      this.horizontalFallSpeed = horizontalFallSpeed;
   }
}
