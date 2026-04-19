package xyz.pixelatedw.mineminenomi.entities.commands;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import org.jetbrains.annotations.Nullable;
import xyz.pixelatedw.mineminenomi.abilities.CommandAbility;
import xyz.pixelatedw.mineminenomi.api.caps.command.ICommandReceiver;
import xyz.pixelatedw.mineminenomi.api.enums.NPCCommand;
import xyz.pixelatedw.mineminenomi.api.factions.MarineRank;
import xyz.pixelatedw.mineminenomi.data.entity.stats.EntityStatsCapability;
import xyz.pixelatedw.mineminenomi.data.entity.stats.IEntityStats;

public class MarineCommand implements ICommandReceiver {
   private final Mob owner;
   private final MarineRank neededRank;
   private long lastCommandTime;
   private LivingEntity lastCommandSender;
   private NPCCommand currentCommand;

   public MarineCommand(Mob entity, MarineRank rank) {
      this.currentCommand = NPCCommand.IDLE;
      this.owner = entity;
      this.neededRank = rank;
      CommandAbility.addCommandGoals(entity, this);
   }

   public boolean canReceiveCommandFrom(LivingEntity commandSender) {
      boolean isMarine = (Boolean)EntityStatsCapability.get(this.owner).map((props) -> props.isMarine()).orElse(false);
      if (!isMarine) {
         return false;
      } else {
         IEntityStats senderProps = (IEntityStats)EntityStatsCapability.get(commandSender).orElse((Object)null);
         if (senderProps == null) {
            return false;
         } else if (!senderProps.isMarine()) {
            return false;
         } else {
            return senderProps.hasRank(this.neededRank);
         }
      }
   }

   public void setCurrentCommand(@Nullable LivingEntity commandSender, NPCCommand command) {
      this.lastCommandTime = commandSender.m_9236_().m_46467_();
      this.lastCommandSender = commandSender;
      this.currentCommand = command;
   }

   public boolean canMaintainCommand() {
      if (this.lastCommandSender != null && this.lastCommandSender.m_6084_()) {
         boolean isRogue = (Boolean)EntityStatsCapability.get(this.lastCommandSender).map((props) -> props.isRogue()).orElse(false);
         if (isRogue) {
            return false;
         }
      }

      return true;
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
