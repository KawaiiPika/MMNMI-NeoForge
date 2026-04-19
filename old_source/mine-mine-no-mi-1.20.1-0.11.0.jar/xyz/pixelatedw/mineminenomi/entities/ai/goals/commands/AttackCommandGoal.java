package xyz.pixelatedw.mineminenomi.entities.ai.goals.commands;

import java.util.EnumSet;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import xyz.pixelatedw.mineminenomi.api.caps.command.ICommandReceiver;
import xyz.pixelatedw.mineminenomi.api.entities.ai.TickedGoal;
import xyz.pixelatedw.mineminenomi.api.enums.NPCCommand;

public class AttackCommandGoal extends TickedGoal<Mob> {
   private ICommandReceiver command;

   public AttackCommandGoal(Mob entity, ICommandReceiver command) {
      super(entity);
      this.command = command;
      this.m_7021_(EnumSet.of(Flag.TARGET));
   }

   public boolean m_8036_() {
      if (this.command == null) {
         return false;
      } else {
         return this.command.getCurrentCommand().equals(NPCCommand.ATTACK);
      }
   }

   public boolean m_8045_() {
      if (!this.command.getCurrentCommand().equals(NPCCommand.ATTACK)) {
         return false;
      } else {
         return this.entity.m_5448_() != null && this.entity.m_5448_().m_6084_();
      }
   }

   public void m_8056_() {
      super.m_8056_();
   }

   public void m_8037_() {
      super.m_8037_();
   }

   public void m_8041_() {
      super.m_8041_();
   }
}
