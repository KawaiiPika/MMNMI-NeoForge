package xyz.pixelatedw.mineminenomi.entities.commands;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.TraceableEntity;
import org.jetbrains.annotations.Nullable;
import xyz.pixelatedw.mineminenomi.abilities.CommandAbility;
import xyz.pixelatedw.mineminenomi.api.caps.command.ICommandReceiver;
import xyz.pixelatedw.mineminenomi.api.enums.NPCCommand;

public class TraceableCommand implements ICommandReceiver {
   private final Mob entity;
   private long lastCommandTime;
   private LivingEntity lastCommandSender;
   private NPCCommand currentCommand;

   public TraceableCommand(Mob entity) {
      this.currentCommand = NPCCommand.IDLE;
      this.entity = entity;
      CommandAbility.addCommandGoals(entity, this);
   }

   public boolean canReceiveCommandFrom(LivingEntity commandSender) {
      Mob var3 = this.entity;
      boolean var10000;
      if (var3 instanceof TraceableEntity traceable) {
         if (traceable.m_19749_().equals(commandSender)) {
            var10000 = true;
            return var10000;
         }
      }

      var10000 = false;
      return var10000;
   }

   public void setCurrentCommand(@Nullable LivingEntity commandSender, NPCCommand command) {
      this.lastCommandTime = this.entity.m_9236_().m_46467_();
      this.lastCommandSender = commandSender;
      this.currentCommand = command;
   }

   public NPCCommand getCurrentCommand() {
      return this.currentCommand;
   }

   public @Nullable LivingEntity getLastCommandSender() {
      return this.lastCommandSender;
   }

   public long getLastCommandTime() {
      return this.lastCommandTime;
   }
}
