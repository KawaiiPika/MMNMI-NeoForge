package xyz.pixelatedw.mineminenomi.entities.ai.goals.commands;

import java.util.EnumSet;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import xyz.pixelatedw.mineminenomi.api.caps.command.ICommandReceiver;
import xyz.pixelatedw.mineminenomi.api.entities.ai.TickedGoal;
import xyz.pixelatedw.mineminenomi.api.enums.NPCCommand;

public class StayCommandGoal extends TickedGoal<Mob> {
   private ICommandReceiver command;

   public StayCommandGoal(Mob entity, ICommandReceiver command) {
      super(entity);
      this.command = command;
      this.m_7021_(EnumSet.of(Flag.JUMP, Flag.MOVE, Flag.TARGET));
   }

   public boolean m_8036_() {
      if (this.command == null) {
         return false;
      } else {
         return this.command.getCurrentCommand().equals(NPCCommand.STAY);
      }
   }

   public boolean m_8045_() {
      if (!this.command.getCurrentCommand().equals(NPCCommand.STAY)) {
         return false;
      } else if (this.entity.m_21188_() != null) {
         this.entity.m_6710_(this.entity.m_21188_());
         return false;
      } else {
         return true;
      }
   }

   public void m_8056_() {
      super.m_8056_();
      this.entity.m_6710_((LivingEntity)null);
      this.entity.m_6703_((LivingEntity)null);
      this.entity.m_21573_().m_26573_();
      Mob var2 = this.entity;
      if (var2 instanceof TamableAnimal animal) {
         animal.m_21837_(true);
         animal.m_21839_(true);
      }

   }

   public void m_8037_() {
      super.m_8037_();
      this.entity.m_6710_((LivingEntity)null);
      this.entity.m_21573_().m_26573_();
   }

   public void m_8041_() {
      super.m_8041_();
      Mob var2 = this.entity;
      if (var2 instanceof TamableAnimal animal) {
         animal.m_21837_(false);
         animal.m_21839_(false);
      }

   }
}
