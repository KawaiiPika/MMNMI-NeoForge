package xyz.pixelatedw.mineminenomi.entities.mobs;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.pacifista.PacifistaRadicalBeamGoal;

public class PacifistaEntity extends OPEntity {
   public static final byte START_CHARGE_LASER_EVENT = 100;
   public static final byte END_CHARGE_LASER_EVENT = 101;
   private boolean isChargingLaser = false;

   public PacifistaEntity(EntityType<? extends PacifistaEntity> type, Level world) {
      super(type, world);
      if (world != null && !world.isClientSide) {
         this.goalSelector.addGoal(2, new PacifistaRadicalBeamGoal(this));
      }
   }

   public static AttributeSupplier.Builder createAttributes() {
      return OPEntity.createAttributes()
              .add(Attributes.MAX_HEALTH, 60.0D)
              .add(Attributes.MOVEMENT_SPEED, 0.25D)
              .add(Attributes.ATTACK_DAMAGE, 6.0D)
              .add(Attributes.FOLLOW_RANGE, 150.0D)
              .add(Attributes.ARMOR, 20.0D)
              .add(Attributes.KNOCKBACK_RESISTANCE, 0.8D)
              .add(Attributes.ATTACK_KNOCKBACK, 2.0D);
   }

   @Override
   public void die(DamageSource cause) {
      super.die(cause);
   }

   @Override
   public void handleEntityEvent(byte id) {
      switch (id) {
         case 100 -> this.isChargingLaser = true;
         case 101 -> this.isChargingLaser = false;
      }
      super.handleEntityEvent(id);
   }

   public boolean isChargingLaser() {
      return this.isChargingLaser;
   }
}
