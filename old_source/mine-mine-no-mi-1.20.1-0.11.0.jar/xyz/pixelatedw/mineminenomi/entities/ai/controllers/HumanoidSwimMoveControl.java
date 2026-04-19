package xyz.pixelatedw.mineminenomi.entities.ai.controllers;

import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.control.MoveControl.Operation;
import net.minecraftforge.common.ForgeMod;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.GoalHelper;

public class HumanoidSwimMoveControl extends MoveControl {
   private final Mob entity;
   private final Style style;

   public HumanoidSwimMoveControl(Mob entity) {
      this(entity, HumanoidSwimMoveControl.Style.AGGRESSIVE);
   }

   public HumanoidSwimMoveControl(Mob entity, Style style) {
      super(entity);
      this.entity = entity;
      this.style = style;
   }

   public void m_8126_() {
      AbilityHelper.setDeltaMovement(this.entity, this.entity.m_20184_().m_82520_((double)0.0F, 0.005, (double)0.0F));
      if (this.entity.m_5448_() != null) {
         if (this.entity.m_5448_().m_20186_() - this.entity.m_20186_() > (double)2.0F) {
            AbilityHelper.setDeltaMovement(this.entity, this.entity.m_20184_().m_82520_((double)0.0F, 0.05, (double)0.0F));
         } else if (this.entity.m_20186_() - this.entity.m_5448_().m_20186_() > (double)2.0F) {
            AbilityHelper.setDeltaMovement(this.entity, this.entity.m_20184_().m_82520_((double)0.0F, -0.05, (double)0.0F));
         }
      }

      if (this.f_24981_ == Operation.MOVE_TO && !this.entity.m_21573_().m_26571_()) {
         double d0 = this.f_24975_ - this.entity.m_20185_();
         double d1 = this.f_24976_ - this.entity.m_20186_();
         double d2 = this.f_24977_ - this.entity.m_20189_();
         double distanceSqr = d0 * d0 + d1 * d1 + d2 * d2;
         if (this.style == HumanoidSwimMoveControl.Style.AGGRESSIVE) {
            if (distanceSqr < (double)2.5F) {
               this.f_24974_.m_21564_(0.0F);
            } else {
               GoalHelper.lookAtEntity(this.entity, this.entity.m_5448_());
               float speed = (float)(this.f_24978_ * this.entity.m_21133_((Attribute)ForgeMod.SWIM_SPEED.get()));
               this.entity.m_7910_(speed);
            }
         } else if (this.style == HumanoidSwimMoveControl.Style.STALLING) {
         }
      } else {
         this.entity.m_7910_(0.0F);
         this.entity.m_21570_(0.0F);
         this.entity.m_21567_(0.0F);
         this.entity.m_21564_(0.0F);
      }

   }

   public static enum Style {
      AGGRESSIVE,
      STALLING;

      // $FF: synthetic method
      private static Style[] $values() {
         return new Style[]{AGGRESSIVE, STALLING};
      }
   }
}
