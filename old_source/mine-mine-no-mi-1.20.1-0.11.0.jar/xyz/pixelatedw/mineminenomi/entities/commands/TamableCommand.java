package xyz.pixelatedw.mineminenomi.entities.commands;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import org.jetbrains.annotations.Nullable;
import xyz.pixelatedw.mineminenomi.abilities.CommandAbility;
import xyz.pixelatedw.mineminenomi.api.caps.command.ICommandReceiver;
import xyz.pixelatedw.mineminenomi.api.enums.NPCCommand;

public class TamableCommand implements ICommandReceiver {
   private TamableAnimal owner;
   private long lastCommandTime;
   private LivingEntity lastCommandSender;
   private NPCCommand currentCommand;

   public TamableCommand(TamableAnimal entity) {
      this.currentCommand = NPCCommand.IDLE;
      this.owner = entity;
      CommandAbility.addCommandGoals(entity, this);
   }

   public boolean canReceiveCommandFrom(LivingEntity commandSender) {
      return this.owner.m_21830_(commandSender);
   }

   public void setCurrentCommand(@Nullable LivingEntity commandSender, NPCCommand command) {
      this.lastCommandTime = this.owner.m_9236_().m_46467_();
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
