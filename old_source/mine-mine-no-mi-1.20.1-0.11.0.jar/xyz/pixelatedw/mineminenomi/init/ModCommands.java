package xyz.pixelatedw.mineminenomi.init;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import java.util.HashSet;
import java.util.Set;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import xyz.pixelatedw.mineminenomi.commands.AbilityCommand;
import xyz.pixelatedw.mineminenomi.commands.AbilityProtectionCommand;
import xyz.pixelatedw.mineminenomi.commands.BellyCommand;
import xyz.pixelatedw.mineminenomi.commands.BountyCommand;
import xyz.pixelatedw.mineminenomi.commands.ChallengeCommand;
import xyz.pixelatedw.mineminenomi.commands.ChangeCharacterCommand;
import xyz.pixelatedw.mineminenomi.commands.CheckFruitsCommand;
import xyz.pixelatedw.mineminenomi.commands.CheckPlayerCommand;
import xyz.pixelatedw.mineminenomi.commands.DamageMultiplierCommand;
import xyz.pixelatedw.mineminenomi.commands.DorikiCommand;
import xyz.pixelatedw.mineminenomi.commands.EventsCommand;
import xyz.pixelatedw.mineminenomi.commands.ExtolCommand;
import xyz.pixelatedw.mineminenomi.commands.FGCommand;
import xyz.pixelatedw.mineminenomi.commands.GoRogueCommand;
import xyz.pixelatedw.mineminenomi.commands.HakiExpCommand;
import xyz.pixelatedw.mineminenomi.commands.IssueBountyCommand;
import xyz.pixelatedw.mineminenomi.commands.LoyaltyCommand;
import xyz.pixelatedw.mineminenomi.commands.PointsCommand;
import xyz.pixelatedw.mineminenomi.commands.PouchCommand;
import xyz.pixelatedw.mineminenomi.commands.QuestCommand;
import xyz.pixelatedw.mineminenomi.commands.RemoveDFCommand;
import xyz.pixelatedw.mineminenomi.config.SystemConfig;
import xyz.pixelatedw.mineminenomi.handlers.entity.PatreonHandler;

@EventBusSubscriber(
   modid = "mineminenomi"
)
public class ModCommands {
   @SubscribeEvent
   public static void registerCommands(RegisterCommandsEvent event) {
      CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();
      boolean masterCommandFlag = (Boolean)SystemConfig.MASTER_COMMAND.get();
      Set<LiteralArgumentBuilder<CommandSourceStack>> commands = new HashSet();
      commands.add(AbilityProtectionCommand.create());
      commands.add(AbilityCommand.create());
      commands.add(DorikiCommand.create());
      commands.add(BountyCommand.create());
      commands.add(BellyCommand.create());
      commands.add(ExtolCommand.create());
      commands.add(IssueBountyCommand.create());
      commands.add(RemoveDFCommand.create());
      commands.add(HakiExpCommand.create());
      commands.add(PouchCommand.create());
      commands.add(CheckPlayerCommand.create());
      commands.add(LoyaltyCommand.create());
      commands.add(GoRogueCommand.create());
      commands.add(ChallengeCommand.create());
      commands.add(DamageMultiplierCommand.create());
      commands.add(CheckFruitsCommand.create());
      commands.add(QuestCommand.create());
      commands.add(ChangeCharacterCommand.create());
      commands.add(EventsCommand.create());
      commands.add(PointsCommand.create());
      if (!PatreonHandler.isReleaseBuild()) {
         commands.add(FGCommand.create(event.getBuildContext()));
      }

      for(LiteralArgumentBuilder<CommandSourceStack> builder : commands) {
         if (masterCommandFlag) {
            builder = (LiteralArgumentBuilder)Commands.m_82127_("mmnm").then(builder);
         }

         dispatcher.register(builder);
      }

   }
}
