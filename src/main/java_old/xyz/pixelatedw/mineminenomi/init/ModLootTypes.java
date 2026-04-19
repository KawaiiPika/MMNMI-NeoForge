package xyz.pixelatedw.mineminenomi.init;

import java.util.function.Consumer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraft.world.level.storage.loot.parameters.LootContextParam;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import net.minecraftforge.registries.RegistryObject;
import xyz.pixelatedw.mineminenomi.api.challenges.ChallengeCore;
import xyz.pixelatedw.mineminenomi.data.conditions.FirstCompletionRewardCondition;
import xyz.pixelatedw.mineminenomi.data.conditions.RandomChanceWithLuckCondition;
import xyz.pixelatedw.mineminenomi.data.conditions.RandomizedFruitsCondition;
import xyz.pixelatedw.mineminenomi.data.functions.EncyclopediaCompletionFunction;
import xyz.pixelatedw.mineminenomi.data.functions.FakeWeaponFunction;
import xyz.pixelatedw.mineminenomi.data.functions.FruitAlreadyExistsFunction;
import xyz.pixelatedw.mineminenomi.data.functions.IncreaseBellyFunction;
import xyz.pixelatedw.mineminenomi.data.functions.IncreaseBountyFunction;
import xyz.pixelatedw.mineminenomi.data.functions.IncreaseBusoExpFunction;
import xyz.pixelatedw.mineminenomi.data.functions.IncreaseDorikiFunction;
import xyz.pixelatedw.mineminenomi.data.functions.IncreaseExtolFunction;
import xyz.pixelatedw.mineminenomi.data.functions.IncreaseKenExpFunction;
import xyz.pixelatedw.mineminenomi.data.functions.IncreaseLoyaltyFunction;
import xyz.pixelatedw.mineminenomi.data.functions.IncreaseStatFunction;
import xyz.pixelatedw.mineminenomi.data.functions.SetBellyInPouchFunction;
import xyz.pixelatedw.mineminenomi.data.functions.SetExtolInPouchFunction;
import xyz.pixelatedw.mineminenomi.data.functions.SetFruitClueFunction;
import xyz.pixelatedw.mineminenomi.data.functions.SetInfiniteStockFunction;
import xyz.pixelatedw.mineminenomi.data.functions.SetItemColorFunction;
import xyz.pixelatedw.mineminenomi.data.functions.SetPriceFunction;
import xyz.pixelatedw.mineminenomi.data.functions.UnlockChallengesFunction;

public class ModLootTypes {
   public static final LootContextParam<ChallengeCore<?>> COMPLETED_CHALLENGE = createParameter("completed_challenge");
   public static final LootContextParamSet CHALLENGE = createParamSet("challenge", (builder) -> builder.m_81406_(LootContextParams.f_81460_).m_81406_(COMPLETED_CHALLENGE).m_81408_(LootContextParams.f_81455_).m_81408_(LootContextParams.f_81458_));
   public static final RegistryObject<LootItemConditionType> RANDOMIZED_FRUIT = ModRegistry.registerLootCondition("randomized_fruits", new RandomizedFruitsCondition.Serializer());
   public static final RegistryObject<LootItemConditionType> FIRST_COMPLETION = ModRegistry.registerLootCondition("first_completion", new FirstCompletionRewardCondition.Serializer());
   public static final RegistryObject<LootItemConditionType> RANDOM_CHANCE_WITH_LUCK = ModRegistry.registerLootCondition("random_chance_with_luck", new RandomChanceWithLuckCondition.Serializer());
   public static final RegistryObject<LootItemFunctionType> SET_PRICE = ModRegistry.registerLootFunction("set_price", new SetPriceFunction.Serializer());
   public static final RegistryObject<LootItemFunctionType> INFINITE_STOCK = ModRegistry.registerLootFunction("infinite_stock", new SetInfiniteStockFunction.Serializer());
   public static final RegistryObject<LootItemFunctionType> SET_BELLY_IN_POUCH = ModRegistry.registerLootFunction("set_belly_in_pouch", new SetBellyInPouchFunction.Serializer());
   public static final RegistryObject<LootItemFunctionType> SET_EXTOL_IN_POUCH = ModRegistry.registerLootFunction("set_extol_in_pouch", new SetExtolInPouchFunction.Serializer());
   public static final RegistryObject<LootItemFunctionType> FAKE_WEAPON = ModRegistry.registerLootFunction("fake_weapon", new FakeWeaponFunction.Serializer());
   public static final RegistryObject<LootItemFunctionType> FRUIT_CLUE = ModRegistry.registerLootFunction("fruit_clue", new SetFruitClueFunction.Serializer());
   public static final RegistryObject<LootItemFunctionType> ENCYCLOPEDIA_COMPLETION = ModRegistry.registerLootFunction("encyclopedia_completion", new EncyclopediaCompletionFunction.Serializer());
   public static final RegistryObject<LootItemFunctionType> SET_ITEM_COLOR = ModRegistry.registerLootFunction("set_item_color", new SetItemColorFunction.Serializer());
   public static final RegistryObject<LootItemFunctionType> INCREASE_DORIKI = ModRegistry.registerLootFunction("increase_doriki", new IncreaseStatFunction.Serializer(IncreaseDorikiFunction::create));
   public static final RegistryObject<LootItemFunctionType> INCREASE_BOUNTY = ModRegistry.registerLootFunction("increase_bounty", new IncreaseStatFunction.Serializer(IncreaseBountyFunction::create));
   public static final RegistryObject<LootItemFunctionType> INCREASE_BELLY = ModRegistry.registerLootFunction("increase_belly", new IncreaseStatFunction.Serializer(IncreaseBellyFunction::create));
   public static final RegistryObject<LootItemFunctionType> INCREASE_EXTOL = ModRegistry.registerLootFunction("increase_extol", new IncreaseStatFunction.Serializer(IncreaseExtolFunction::create));
   public static final RegistryObject<LootItemFunctionType> INCREASE_LOYALTY = ModRegistry.registerLootFunction("increase_loyalty", new IncreaseStatFunction.Serializer(IncreaseLoyaltyFunction::create));
   public static final RegistryObject<LootItemFunctionType> INCREASE_BUSO_EXP = ModRegistry.registerLootFunction("increase_hardening_exp", new IncreaseStatFunction.Serializer(IncreaseBusoExpFunction::create));
   public static final RegistryObject<LootItemFunctionType> INCREASE_KEN_EXP = ModRegistry.registerLootFunction("increase_observation_exp", new IncreaseStatFunction.Serializer(IncreaseKenExpFunction::create));
   public static final RegistryObject<LootItemFunctionType> FRUIT_ALREADY_EXISTS = ModRegistry.registerLootFunction("fruit_already_exists", new FruitAlreadyExistsFunction.Serializer());
   public static final RegistryObject<LootItemFunctionType> UNLOCK_CHALLENGES = ModRegistry.registerLootFunction("unlock_challenges", new UnlockChallengesFunction.Serializer());

   private static <T> LootContextParam<T> createParameter(String id) {
      return new LootContextParam(ResourceLocation.fromNamespaceAndPath("mineminenomi", id));
   }

   private static LootContextParamSet createParamSet(String id, Consumer<LootContextParamSet.Builder> consumer) {
      LootContextParamSet.Builder builder = new LootContextParamSet.Builder();
      consumer.accept(builder);
      LootContextParamSet set = builder.m_81405_();
      return set;
   }

   public static void init() {
   }
}
