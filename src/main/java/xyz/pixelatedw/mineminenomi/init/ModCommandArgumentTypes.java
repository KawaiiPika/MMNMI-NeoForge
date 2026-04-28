package xyz.pixelatedw.mineminenomi.init;

import net.minecraft.commands.synchronization.ArgumentTypeInfo;
import net.minecraft.commands.synchronization.ArgumentTypeInfos;
import net.minecraft.commands.synchronization.SingletonArgumentInfo;
import net.neoforged.neoforge.registries.DeferredHolder;
import xyz.pixelatedw.mineminenomi.api.commands.AbilityArgument;
import xyz.pixelatedw.mineminenomi.api.commands.ChallengeArgument;
import xyz.pixelatedw.mineminenomi.api.commands.FruitArgument;
import xyz.pixelatedw.mineminenomi.api.commands.QuestArgument;

public class ModCommandArgumentTypes {

    public static final DeferredHolder<ArgumentTypeInfo<?, ?>, SingletonArgumentInfo<AbilityArgument>> ABILITY = ModRegistry.COMMAND_ARGUMENT_TYPES.register("ability", () -> ArgumentTypeInfos.registerByClass(AbilityArgument.class, SingletonArgumentInfo.contextFree(AbilityArgument::ability)));
    public static final DeferredHolder<ArgumentTypeInfo<?, ?>, SingletonArgumentInfo<ChallengeArgument>> CHALLENGE = ModRegistry.COMMAND_ARGUMENT_TYPES.register("challenge", () -> ArgumentTypeInfos.registerByClass(ChallengeArgument.class, SingletonArgumentInfo.contextFree(ChallengeArgument::challenge)));
    public static final DeferredHolder<ArgumentTypeInfo<?, ?>, SingletonArgumentInfo<FruitArgument>> FRUIT = ModRegistry.COMMAND_ARGUMENT_TYPES.register("fruit", () -> ArgumentTypeInfos.registerByClass(FruitArgument.class, SingletonArgumentInfo.contextFree(FruitArgument::fruit)));
    public static final DeferredHolder<ArgumentTypeInfo<?, ?>, SingletonArgumentInfo<QuestArgument>> QUEST = ModRegistry.COMMAND_ARGUMENT_TYPES.register("quest", () -> ArgumentTypeInfos.registerByClass(QuestArgument.class, SingletonArgumentInfo.contextFree(QuestArgument::quest)));

    public static void init() {
    }
}
