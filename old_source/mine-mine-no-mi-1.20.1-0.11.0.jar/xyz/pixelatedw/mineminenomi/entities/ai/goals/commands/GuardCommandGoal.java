package xyz.pixelatedw.mineminenomi.entities.ai.goals.commands;

import java.util.EnumSet;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.caps.command.ICommandReceiver;
import xyz.pixelatedw.mineminenomi.api.damagesources.TargetPredicate;
import xyz.pixelatedw.mineminenomi.api.entities.ai.TickedGoal;
import xyz.pixelatedw.mineminenomi.api.enums.NPCCommand;
import xyz.pixelatedw.mineminenomi.api.helpers.TargetHelper;

public class GuardCommandGoal extends TickedGoal<Mob> {
   private static final TargetPredicate PREDICATE = (new TargetPredicate()).testEnemyFaction().testSimpleInView();
   private Vec3 guardPosition;
   private long lastManualStart;
   private ICommandReceiver command;

   public GuardCommandGoal(Mob entity, ICommandReceiver command) {
      super(entity);
      this.command = command;
      this.m_7021_(EnumSet.of(Flag.MOVE, Flag.TARGET));
   }

   public boolean m_8036_() {
      if (this.command == null) {
         return false;
      } else if (!this.command.getCurrentCommand().equals(NPCCommand.GUARD)) {
         return false;
      } else {
         return this.hasTimePassedSinceLastEnd(WyHelper.secondsToTicks(5.0F));
      }
   }

   public boolean m_8045_() {
      if (!this.command.getCurrentCommand().equals(NPCCommand.GUARD)) {
         return false;
      } else {
         return this.entity.m_5448_() == null || !this.entity.m_5448_().m_6084_();
      }
   }

   public void m_8056_() {
      super.m_8056_();
      this.entity.m_6710_((LivingEntity)null);
      this.entity.m_6703_((LivingEntity)null);
      this.entity.m_21573_().m_26573_();
      if (this.guardPosition == null || this.lastManualStart != this.command.getLastCommandTime()) {
         this.guardPosition = this.entity.m_20182_();
      }

      this.lastManualStart = this.command.getLastCommandTime();
   }

   public void m_8037_() {
      super.m_8037_();
      if (this.getTickCount() % 20L == 0L) {
         if (this.entity.m_20182_().m_82554_(this.guardPosition) > (double)6.0F) {
            this.entity.m_21573_().m_26519_(this.guardPosition.f_82479_, this.guardPosition.f_82480_, this.guardPosition.f_82481_, (double)1.25F);
         }

         LivingEntity possibleTarget = (LivingEntity)TargetHelper.getEntitiesInArea(this.entity.m_9236_(), this.entity, (double)10.0F, PREDICATE, LivingEntity.class).stream().sorted(TargetHelper.closestComparator(this.guardPosition)).findFirst().orElse((Object)null);
         this.entity.m_6710_(possibleTarget);
      }
   }

   public void m_8041_() {
      super.m_8041_();
   }
}
