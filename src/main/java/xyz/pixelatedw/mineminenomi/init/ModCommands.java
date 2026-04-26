package xyz.pixelatedw.mineminenomi.init;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import xyz.pixelatedw.mineminenomi.ModMain;
import xyz.pixelatedw.mineminenomi.commands.*;

@EventBusSubscriber(modid = ModMain.PROJECT_ID)
public class ModCommands {

    @SubscribeEvent
    public static void onRegisterCommands(RegisterCommandsEvent event) {
        BellyCommand.register(event.getDispatcher(), event.getBuildContext());
        BountyCommand.register(event.getDispatcher(), event.getBuildContext());
        DorikiCommand.register(event.getDispatcher(), event.getBuildContext());
        ExtolCommand.register(event.getDispatcher(), event.getBuildContext());
        HakiExpCommand.register(event.getDispatcher(), event.getBuildContext());
        RemoveDFCommand.register(event.getDispatcher(), event.getBuildContext());
        PointsCommand.register(event.getDispatcher(), event.getBuildContext());
        AbilityCommand.register(event.getDispatcher(), event.getBuildContext());
        QuestCommand.register(event.getDispatcher(), event.getBuildContext());
        ChallengeCommand.register(event.getDispatcher(), event.getBuildContext());
        CheckFruitsCommand.register(event.getDispatcher(), event.getBuildContext());
        FGCommand.register(event.getDispatcher(), event.getBuildContext());
        LoyaltyCommand.register(event.getDispatcher(), event.getBuildContext());
        CheckPlayerCommand.register(event.getDispatcher(), event.getBuildContext());
    }
}
