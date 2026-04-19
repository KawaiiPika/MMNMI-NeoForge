package xyz.pixelatedw.mineminenomi.data.entity.ability;

import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Stream;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.util.INBTSerializable;
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.Nullable;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCoreUnlockWrapper;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityPool;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityUnlock;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponentKey;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.AbilityNode;

public interface IAbilityData extends INBTSerializable<CompoundTag> {
   <A extends IAbility> boolean addUnlockedAbility(AbilityCore<A> var1, AbilityUnlock var2);

   <A extends IAbility> boolean addUnlockedAbility(AbilityCoreUnlockWrapper<A> var1);

   boolean removeUnlockedAbility(AbilityCore<?> var1);

   boolean hasUnlockedAbility(AbilityCore<?> var1);

   AbilityUnlock getUnlockTypeForAbility(AbilityCore<?> var1);

   Set<AbilityCoreUnlockWrapper<?>> getUnlockedAbilities();

   Set<AbilityCoreUnlockWrapper<?>> getUnlockedAbilities(Predicate<AbilityCore<?>> var1);

   <A extends IAbility> Stream<AbilityCoreUnlockWrapper<A>> getUnlockedAbilitiesStream();

   Set<AbilityCore<?>> clearUnlockedAbilities();

   Set<AbilityCore<?>> clearUnlockedAbilities(Predicate<AbilityCore<?>> var1);

   int countUnlockedAbilities();

   int countUnlockedAbilities(Predicate<AbilityCore<?>> var1);

   boolean addPassiveAbility(IAbility var1);

   boolean removePassiveAbility(IAbility var1);

   boolean removePassiveAbility(AbilityCore<?> var1);

   boolean hasPassiveAbility(IAbility var1);

   boolean hasPassiveAbility(AbilityCore<?> var1);

   <T extends IAbility> @Nullable T getPassiveAbility(AbilityCore<T> var1);

   Set<IAbility> getPassiveAbilities();

   Set<IAbility> getPassiveAbilities(Predicate<IAbility> var1);

   void clearPassiveAbilities();

   void clearPassiveAbilities(Predicate<IAbility> var1);

   int countPassiveAbilities();

   int countPassiveAbilities(Predicate<IAbility> var1);

   boolean setEquippedAbility(int var1, @Nullable IAbility var2);

   boolean removeEquippedAbility(IAbility var1);

   boolean removeEquippedAbility(AbilityCore<?> var1);

   boolean hasEquippedAbility(IAbility var1);

   boolean hasEquippedAbility(AbilityCore<?> var1);

   int getEquippedAbilitySlot(IAbility var1);

   int getEquippedAbilitySlot(AbilityCore<?> var1);

   <T extends IAbility> @Nullable T getEquippedAbility(T var1);

   <T extends IAbility> @Nullable T getEquippedAbility(AbilityCore<T> var1);

   <T extends IAbility> @Nullable T getEquippedAbility(int var1);

   <T extends IAbility> List<T> getRawEquippedAbilities();

   <T extends IAbility> Set<T> getEquippedAbilities();

   <T extends IAbility> Set<T> getEquippedAbilities(Predicate<IAbility> var1);

   <T extends IAbility> Set<T> getEquippedAbilitiesWith(AbilityComponentKey<?>... var1);

   Stream<IAbility> getEquippedAbilitiesInPool(AbilityPool var1);

   void clearEquippedAbilities();

   void clearEquippedAbilities(Predicate<IAbility> var1);

   int countEquippedAbilities();

   int countEquippedAbilities(Predicate<IAbility> var1);

   <T extends IAbility> @Nullable T getEquippedOrPassiveAbility(AbilityCore<T> var1);

   Set<IAbility> getEquippedAndPassiveAbilities();

   Set<IAbility> getEquippedAndPassiveAbilities(Predicate<IAbility> var1);

   <T extends IAbility> T getPreviouslyUsedAbility();

   void setPreviouslyUsedAbility(IAbility var1);

   int getCombatBarSet();

   void nextCombatBarSet(int var1);

   void prevCombatBarSet(int var1);

   void setCombatBarSet(int var1);

   int getLastCombatBarSet();

   Pair<AbilityCore<? extends IAbility>, Integer> getCoreIndexPair(AbilityNode var1);

   Set<AbilityNode> getNodes();

   AbilityNode getNode(AbilityCore<? extends IAbility> var1, int var2);

   void addNode(AbilityCore<? extends IAbility> var1, int var2, AbilityNode var3);
}
