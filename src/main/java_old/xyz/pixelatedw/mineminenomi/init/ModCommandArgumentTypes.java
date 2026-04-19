package xyz.pixelatedw.mineminenomi.init;

import net.minecraft.commands.synchronization.ArgumentTypeInfos;
import net.minecraft.commands.synchronization.SingletonArgumentInfo;
import net.minecraftforge.registries.RegistryObject;
import xyz.pixelatedw.mineminenomi.api.commands.AbilityArgument;
import xyz.pixelatedw.mineminenomi.api.commands.ChallengeArgument;
import xyz.pixelatedw.mineminenomi.api.commands.CrewArgument;
import xyz.pixelatedw.mineminenomi.api.commands.CurrencyTypeArgument;
import xyz.pixelatedw.mineminenomi.api.commands.FactionArgument;
import xyz.pixelatedw.mineminenomi.api.commands.FightingStyleArgument;
import xyz.pixelatedw.mineminenomi.api.commands.FruitArgument;
import xyz.pixelatedw.mineminenomi.api.commands.HakiTypeArgument;
import xyz.pixelatedw.mineminenomi.api.commands.QuestArgument;
import xyz.pixelatedw.mineminenomi.api.commands.RaceArgument;
import xyz.pixelatedw.mineminenomi.api.enums.Currency;
import xyz.pixelatedw.mineminenomi.api.enums.HakiType;

public class ModCommandArgumentTypes {
   private static final RegistryObject<SingletonArgumentInfo<AbilityArgument>> ABILITY = ModRegistry.registerCommandArgumentType("ability", () -> (SingletonArgumentInfo)ArgumentTypeInfos.registerByClass(AbilityArgument.class, SingletonArgumentInfo.m_235451_(AbilityArgument::ability)));
   private static final RegistryObject<SingletonArgumentInfo<ChallengeArgument>> CHALLENGE = ModRegistry.registerCommandArgumentType("challenge", () -> (SingletonArgumentInfo)ArgumentTypeInfos.registerByClass(ChallengeArgument.class, SingletonArgumentInfo.m_235451_(ChallengeArgument::challenge)));
   private static final RegistryObject<SingletonArgumentInfo<CrewArgument>> CREW = ModRegistry.registerCommandArgumentType("crew", () -> (SingletonArgumentInfo)ArgumentTypeInfos.registerByClass(CrewArgument.class, SingletonArgumentInfo.m_235451_(CrewArgument::crew)));
   private static final RegistryObject<SingletonArgumentInfo<FruitArgument>> FRUIT = ModRegistry.registerCommandArgumentType("fruit", () -> (SingletonArgumentInfo)ArgumentTypeInfos.registerByClass(FruitArgument.class, SingletonArgumentInfo.m_235451_(FruitArgument::fruit)));
   private static final RegistryObject<CurrencyTypeArgument.Info<Currency>> CURRENCY_TYPE = ModRegistry.registerCommandArgumentType("currency_type", () -> (CurrencyTypeArgument.Info)ArgumentTypeInfos.registerByClass(CurrencyTypeArgument.class, new CurrencyTypeArgument.Info()));
   private static final RegistryObject<HakiTypeArgument.Info<HakiType>> HAKI_TYPE = ModRegistry.registerCommandArgumentType("haki_type", () -> (HakiTypeArgument.Info)ArgumentTypeInfos.registerByClass(HakiTypeArgument.class, new HakiTypeArgument.Info()));
   private static final RegistryObject<SingletonArgumentInfo<QuestArgument>> QUEST = ModRegistry.registerCommandArgumentType("quest", () -> (SingletonArgumentInfo)ArgumentTypeInfos.registerByClass(QuestArgument.class, SingletonArgumentInfo.m_235451_(QuestArgument::quest)));
   private static final RegistryObject<SingletonArgumentInfo<FactionArgument>> FACTION = ModRegistry.registerCommandArgumentType("faction", () -> (SingletonArgumentInfo)ArgumentTypeInfos.registerByClass(FactionArgument.class, SingletonArgumentInfo.m_235451_(FactionArgument::faction)));
   private static final RegistryObject<SingletonArgumentInfo<RaceArgument>> RACE = ModRegistry.registerCommandArgumentType("race", () -> (SingletonArgumentInfo)ArgumentTypeInfos.registerByClass(RaceArgument.class, SingletonArgumentInfo.m_235451_(RaceArgument::race)));
   private static final RegistryObject<SingletonArgumentInfo<FightingStyleArgument>> FIGHTING_STYLE = ModRegistry.registerCommandArgumentType("fighting_style", () -> (SingletonArgumentInfo)ArgumentTypeInfos.registerByClass(FightingStyleArgument.class, SingletonArgumentInfo.m_235451_(FightingStyleArgument::fightingStyle)));

   public static void init() {
   }
}
