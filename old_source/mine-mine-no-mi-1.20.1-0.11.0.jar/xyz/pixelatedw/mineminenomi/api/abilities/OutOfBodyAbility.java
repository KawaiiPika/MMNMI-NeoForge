package xyz.pixelatedw.mineminenomi.api.abilities;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.player.Player;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.util.Result;
import xyz.pixelatedw.mineminenomi.init.ModDamageSources;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18nAbilities;

public abstract class OutOfBodyAbility extends Ability {
   private LivingEntity body;
   private BlockPos pivotPoint;
   protected final ContinuousComponent continuousComponent = (new ContinuousComponent(this, true)).addStartEvent(200, this::startContinuityEvent).addTickEvent(200, this::duringContinuityEvent).addEndEvent(200, this::stopContinuityEvent);

   public OutOfBodyAbility(AbilityCore<? extends OutOfBodyAbility> core) {
      super(core);
      this.addComponents(new AbilityComponent[]{this.continuousComponent});
      this.addCanUseCheck((entity, ability) -> AbilityHelper.isInCreativeOrSpectator(entity) ? Result.fail(ModI18nAbilities.MESSAGE_SUVIVAL_ONLY) : Result.success());
      this.addUseEvent(this::useEvent);
   }

   private void useEvent(LivingEntity entity, IAbility ability) {
      this.continuousComponent.triggerContinuity(entity, (float)this.getHoldTime());
   }

   private void startContinuityEvent(LivingEntity entity, IAbility ability) {
      this.setPivotPoint(this.body.m_20183_());
      entity.m_6853_(false);
      if (entity instanceof Player player) {
         player.m_150110_().f_35935_ = true;
         if (player instanceof ServerPlayer serverPlayer) {
            serverPlayer.m_6885_();
         }
      }

   }

   private void stopContinuityEvent(LivingEntity entity, IAbility ability) {
      if (this.body != null) {
         entity.m_6021_((double)this.getPivotPoint().m_123341_(), (double)this.getPivotPoint().m_123342_(), (double)this.getPivotPoint().m_123343_());
         this.body.m_142687_(RemovalReason.DISCARDED);
         this.body = null;
      }

      if (!this.isPhysical()) {
         entity.f_19794_ = false;
      }

      if (entity instanceof Player player) {
         player.m_150110_().f_35935_ = false;
         if (player instanceof ServerPlayer serverPlayer) {
            serverPlayer.m_6885_();
         }
      }

   }

   private void duringContinuityEvent(LivingEntity entity, IAbility ability) {
      if (entity.m_20159_()) {
         entity.m_8127_();
      }

      if (!this.isPhysical()) {
         entity.m_6853_(false);
         entity.f_19794_ = true;
         entity.m_20095_();
      }

      if (!entity.m_9236_().f_46443_) {
         if (!(Math.sqrt(entity.m_20275_((double)this.getPivotPoint().m_123341_(), (double)this.getPivotPoint().m_123342_(), (double)this.getPivotPoint().m_123343_())) > (double)this.getMaxRange()) && this.getOriginalBody() != null) {
            if ((double)this.getPivotPoint().m_123341_() != this.getOriginalBody().m_20182_().f_82479_ || (double)this.getPivotPoint().m_123343_() != this.getOriginalBody().m_20182_().f_82481_ || (double)this.getPivotPoint().m_123342_() != this.getOriginalBody().m_20182_().f_82480_) {
               this.setPivotPoint(this.getOriginalBody().m_20183_());
            }

            if (this.getOriginalBody() == null) {
               this.continuousComponent.stopContinuity(entity);
            } else {
               if (!this.getOriginalBody().m_6084_()) {
                  entity.m_6469_(ModDamageSources.getInstance().outOfBody(), entity.m_21233_());
               }

            }
         } else {
            this.continuousComponent.stopContinuity(entity);
         }
      }
   }

   public LivingEntity getOriginalBody() {
      return this.body;
   }

   public double getDistanceFromPivot(Entity entity) {
      return this.getPivotPoint() == null ? (double)-1.0F : Math.sqrt(entity.m_20275_((double)this.getPivotPoint().m_123341_(), (double)this.getPivotPoint().m_123342_(), (double)this.getPivotPoint().m_123343_()));
   }

   public void setOriginalBody(LivingEntity body) {
      this.body = body;
   }

   public BlockPos getPivotPoint() {
      return this.pivotPoint;
   }

   public void setPivotPoint(BlockPos pos) {
      this.pivotPoint = pos;
   }

   public void saveAdditional(CompoundTag nbt) {
      if (this.pivotPoint != null) {
         nbt.m_128347_("x", (double)this.pivotPoint.m_123341_());
         nbt.m_128347_("y", (double)this.pivotPoint.m_123342_());
         nbt.m_128347_("z", (double)this.pivotPoint.m_123343_());
      }

   }

   public void loadAdditional(CompoundTag nbt) {
      double x = nbt.m_128459_("x");
      double y = nbt.m_128459_("y");
      double z = nbt.m_128459_("z");
      this.pivotPoint = BlockPos.m_274561_(x, y, z);
   }

   public int getHoldTime() {
      return -1;
   }

   public boolean isActive() {
      return this.continuousComponent.isContinuous();
   }

   public abstract float getMaxRange();

   public abstract boolean isPhysical();
}
