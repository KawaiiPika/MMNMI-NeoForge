package xyz.pixelatedw.mineminenomi.data.entity.ability;

import com.google.common.collect.Lists;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.common.MinecraftForge;
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.Nullable;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCoreUnlockWrapper;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityPool;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityType;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityUnlock;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponentKey;
import xyz.pixelatedw.mineminenomi.api.abilities.components.PoolComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.AbilityNode;
import xyz.pixelatedw.mineminenomi.api.events.ability.EquipAbilityEvent;
import xyz.pixelatedw.mineminenomi.config.ServerConfig;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;
import xyz.pixelatedw.mineminenomi.init.ModAdvancements;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.packets.client.ability.CSetCombatBarPacket;
import xyz.pixelatedw.mineminenomi.renderers.layers.AuraLayer;

public class AbilityDataBase implements IAbilityData {
   private LivingEntity owner;
   private Set<AbilityCoreUnlockWrapper<?>> unlockedAbilities = new LinkedHashSet();
   private Set<IAbility> passiveAbilities = new LinkedHashSet();
   private IAbility[] activeAbilities = new IAbility[80];
   private IAbility previouslyUsedAbility;
   private int currentCombatBarSet = 0;
   private int lastCombatBarSet = 0;
   private final Map<Pair<AbilityCore<? extends IAbility>, Integer>, AbilityNode> nodes = new HashMap();

   public AbilityDataBase(LivingEntity owner) {
      this.owner = owner;
   }

   public <A extends IAbility> boolean addUnlockedAbility(AbilityCore<A> core, AbilityUnlock unlockMethod) {
      if (core != null && !this.hasUnlockedAbility(core)) {
         AbilityCoreUnlockWrapper<A> unlockedCore = new AbilityCoreUnlockWrapper<A>(this.owner, core, unlockMethod);
         this.addUnlockedAbility(unlockedCore);
         if (core.getType() == AbilityType.PASSIVE) {
            this.addPassiveAbility(core.createAbility());
         }

         return true;
      } else {
         return false;
      }
   }

   public <A extends IAbility> boolean addUnlockedAbility(AbilityCoreUnlockWrapper<A> unlockedCore) {
      AbilityCore<A> core = unlockedCore.getAbilityCore();
      if (core != null && !this.hasUnlockedAbility(core)) {
         this.unlockedAbilities.add(unlockedCore);
         if (this.owner != null) {
            LivingEntity var4 = this.owner;
            if (var4 instanceof ServerPlayer) {
               ServerPlayer player = (ServerPlayer)var4;
               ModAdvancements.UNLOCK_ABILITY.trigger(player, core);
            }
         }

         return true;
      } else {
         return false;
      }
   }

   public AbilityUnlock getUnlockTypeForAbility(AbilityCore<?> core) {
      return (AbilityUnlock)this.unlockedAbilities.stream().filter((pair) -> pair.getAbilityCore().equals(core)).map((pair) -> pair.getUnlockType()).findFirst().orElse(AbilityUnlock.NONE);
   }

   public boolean removeUnlockedAbility(AbilityCore<?> core) {
      if (!this.hasUnlockedAbility(core)) {
         return false;
      } else {
         boolean updateClient = false;
         updateClient |= this.removeEquippedAbility(core);
         updateClient |= this.removePassiveAbility(core);
         if (updateClient) {
         }

         this.unlockedAbilities.removeIf((pair) -> pair.getAbilityCore().equals(core));
         return true;
      }
   }

   public boolean hasUnlockedAbility(AbilityCore<?> core) {
      return this.getUnlockedAbilities().stream().anyMatch((wrapper) -> wrapper.getAbilityCore().equals(core));
   }

   public Set<AbilityCoreUnlockWrapper<? extends IAbility>> getUnlockedAbilities() {
      return (Set)this.unlockedAbilities.stream().filter(Objects::nonNull).collect(Collectors.toSet());
   }

   public Set<AbilityCoreUnlockWrapper<? extends IAbility>> getUnlockedAbilities(Predicate<AbilityCore<?>> check) {
      return (Set)this.getUnlockedAbilities().stream().filter((pair) -> check.test(pair.getAbilityCore())).collect(Collectors.toSet());
   }

   public Stream<AbilityCoreUnlockWrapper<? extends IAbility>> getUnlockedAbilitiesStream() {
      return this.unlockedAbilities.stream().filter(Objects::nonNull);
   }

   public Set<AbilityCore<?>> clearUnlockedAbilities() {
      Set<AbilityCore<?>> removed = (Set)this.unlockedAbilities.stream().map(AbilityCoreUnlockWrapper::getAbilityCore).collect(Collectors.toSet());
      removed.forEach((core) -> this.removeUnlockedAbility(core));
      this.unlockedAbilities.clear();
      return removed;
   }

   public Set<AbilityCore<?>> clearUnlockedAbilities(Predicate<AbilityCore<?>> check) {
      Set<AbilityCoreUnlockWrapper<?>> removed = (Set)this.unlockedAbilities.stream().filter((pair) -> check.test(pair.getAbilityCore())).collect(Collectors.toSet());
      removed.forEach((pair) -> this.removeUnlockedAbility(pair.getAbilityCore()));
      this.unlockedAbilities.removeAll(removed);
      return (Set)removed.stream().map(AbilityCoreUnlockWrapper::getAbilityCore).collect(Collectors.toSet());
   }

   public int countUnlockedAbilities() {
      this.unlockedAbilities.removeIf((ability) -> ability == null);
      return this.unlockedAbilities.size();
   }

   public int countUnlockedAbilities(Predicate<AbilityCore<?>> check) {
      return this.getUnlockedAbilities((core) -> check.test(core)).stream().mapToInt((a) -> 1).sum();
   }

   public boolean addPassiveAbility(IAbility abl) {
      if (this.hasPassiveAbility(abl)) {
         return false;
      } else if (!this.hasUnlockedAbility(abl.getCore())) {
         return false;
      } else {
         abl.onEquip(this.owner);
         this.passiveAbilities.add(abl);
         return true;
      }
   }

   public boolean removePassiveAbility(IAbility abl) {
      return this.removePassiveAbility(abl.getCore());
   }

   public boolean removePassiveAbility(AbilityCore<?> core) {
      return this.passiveAbilities.removeIf((abl) -> {
         if (abl.getCore().equals(core)) {
            abl.onRemove(this.owner);
            return true;
         } else {
            return false;
         }
      });
   }

   public boolean hasPassiveAbility(IAbility abl) {
      return this.hasPassiveAbility(abl.getCore());
   }

   public boolean hasPassiveAbility(AbilityCore<?> core) {
      return this.getPassiveAbilities().stream().anyMatch((abl) -> abl.getCore().equals(core));
   }

   public <T extends IAbility> @Nullable T getPassiveAbility(AbilityCore<T> core) {
      return (T)(this.getPassiveAbilities().stream().filter((otherAbl) -> otherAbl.getCore().equals(core)).findFirst().orElse((Object)null));
   }

   public Set<IAbility> getPassiveAbilities() {
      return (Set)this.passiveAbilities.stream().filter(Objects::nonNull).collect(Collectors.toSet());
   }

   public Set<IAbility> getPassiveAbilities(Predicate<IAbility> check) {
      return (Set)this.getPassiveAbilities().stream().filter((abl) -> check.test(abl) && check.test(abl)).collect(Collectors.toSet());
   }

   public void clearPassiveAbilities() {
      for(IAbility ability : this.passiveAbilities) {
         ability.onRemove(this.owner);
      }

      this.passiveAbilities.clear();
   }

   public void clearPassiveAbilities(Predicate<IAbility> predicate) {
      for(IAbility ability : this.passiveAbilities) {
         if (predicate.test(ability)) {
            ability.onRemove(this.owner);
         }
      }

      this.passiveAbilities.removeIf((abl) -> predicate.test(abl));
   }

   public int countPassiveAbilities() {
      return this.getPassiveAbilities().size();
   }

   public int countPassiveAbilities(Predicate<IAbility> check) {
      return this.getPassiveAbilities().stream().filter((abl) -> check.test(abl)).mapToInt((a) -> 1).sum();
   }

   public boolean setEquippedAbility(int slot, @Nullable IAbility abl) {
      if (slot < 0) {
         return false;
      } else {
         if (abl != null) {
            if (this.hasEquippedAbility(abl)) {
               return false;
            }

            EquipAbilityEvent event = new EquipAbilityEvent(this.owner, abl);
            if (MinecraftForge.EVENT_BUS.post(event)) {
               return false;
            }

            IAbility ability = event.getAbility();
            if (this.owner.m_9236_().f_46443_) {
               ability.getComponent((AbilityComponentKey)ModAbilityComponents.COOLDOWN.get()).ifPresent((component) -> {
                  component.addStartEvent(AuraLayer::addAbilityIcon);
                  component.addEndEvent(AuraLayer::addAbilityIcon);
               });
               ability.getComponent((AbilityComponentKey)ModAbilityComponents.CHARGE.get()).ifPresent((component) -> {
                  component.addStartEvent(AuraLayer::addAbilityIcon);
                  component.addEndEvent(AuraLayer::addAbilityIcon);
               });
               ability.getComponent((AbilityComponentKey)ModAbilityComponents.CONTINUOUS.get()).ifPresent((component) -> {
                  component.addStartEvent(AuraLayer::addAbilityIcon);
                  component.addEndEvent(AuraLayer::addAbilityIcon);
               });
            }

            ability.onEquip(this.owner);
         } else if (this.activeAbilities[slot] != null) {
            this.activeAbilities[slot].onRemove(this.owner);
         }

         this.activeAbilities[slot] = abl;
         return true;
      }
   }

   public boolean removeEquippedAbility(IAbility abl) {
      return abl == null ? false : this.removeEquippedAbility(abl.getCore());
   }

   public boolean removeEquippedAbility(AbilityCore<?> core) {
      for(int i = 0; i < this.activeAbilities.length; ++i) {
         IAbility ability = this.activeAbilities[i];
         if (ability != null && ability.getCore().equals(core)) {
            ability.onRemove(this.owner);
            this.activeAbilities[i] = null;
            return true;
         }
      }

      return false;
   }

   public boolean hasEquippedAbility(IAbility abl) {
      return abl == null ? false : this.hasEquippedAbility(abl.getCore());
   }

   public boolean hasEquippedAbility(AbilityCore<?> core) {
      return Arrays.stream(this.activeAbilities).filter((ability) -> ability != null).anyMatch((ability) -> ability.getCore().equals(core));
   }

   public int getEquippedAbilitySlot(IAbility abl) {
      return abl == null ? -1 : this.getEquippedAbilitySlot(abl.getCore());
   }

   public int getEquippedAbilitySlot(AbilityCore<?> core) {
      if (core == null) {
         return -1;
      } else {
         for(int i = 0; i < this.activeAbilities.length; ++i) {
            IAbility ability = this.activeAbilities[i];
            if (ability != null && ability.getCore().equals(core)) {
               return i;
            }
         }

         return -1;
      }
   }

   public <T extends IAbility> @Nullable T getEquippedAbility(T abl) {
      return (T)(Arrays.stream(this.activeAbilities).filter((ability) -> ability != null && ability.equals(abl)).findFirst().orElse((Object)null));
   }

   public <T extends IAbility> @Nullable T getEquippedAbility(AbilityCore<T> core) {
      return (T)(Arrays.stream(this.activeAbilities).filter((ability) -> ability != null && ability.getCore().equals(core)).findFirst().orElse((Object)null));
   }

   public <T extends IAbility> @Nullable T getEquippedAbility(int slot) {
      if (slot < 0) {
         return null;
      } else {
         return (T)(this.activeAbilities.length < slot ? null : this.activeAbilities[slot]);
      }
   }

   public List<IAbility> getRawEquippedAbilities() {
      LinkedList<IAbility> list = Lists.newLinkedList();
      Collections.addAll(list, this.activeAbilities);
      return list;
   }

   public <T extends IAbility> Set<T> getEquippedAbilities() {
      return (Set)Arrays.stream(this.activeAbilities).filter(Objects::nonNull).collect(Collectors.toSet());
   }

   public <T extends IAbility> Set<T> getEquippedAbilities(Predicate<IAbility> check) {
      return (Set)Arrays.stream(this.activeAbilities).filter((abl) -> abl != null && check.test(abl)).collect(Collectors.toSet());
   }

   public <T extends IAbility> Set<T> getEquippedAbilitiesWith(AbilityComponentKey<?>... keys) {
      return (Set)Arrays.stream(this.activeAbilities).filter((abl) -> abl != null && Arrays.stream(keys).allMatch((key) -> abl.hasComponent(key))).collect(Collectors.toSet());
   }

   public Stream<IAbility> getEquippedAbilitiesInPool(AbilityPool pool) {
      return this.getEquippedAndPassiveAbilities().stream().filter((ability) -> ability.hasComponent((AbilityComponentKey)ModAbilityComponents.POOL.get()) && ((PoolComponent)ability.getComponent((AbilityComponentKey)ModAbilityComponents.POOL.get()).get()).containsPool(pool));
   }

   public void clearEquippedAbilities() {
      for(int i = 0; i < this.activeAbilities.length; ++i) {
         IAbility ability = this.activeAbilities[i];
         if (ability != null) {
            ability.onRemove(this.owner);
         }

         this.activeAbilities[i] = null;
      }

   }

   public void clearEquippedAbilities(Predicate<IAbility> predicate) {
      for(int i = 0; i < this.activeAbilities.length; ++i) {
         IAbility ability = this.activeAbilities[i];
         if (ability != null && predicate.test(ability)) {
            ability.onRemove(this.owner);
            this.activeAbilities[i] = null;
         }
      }

   }

   public int countEquippedAbilities() {
      return Arrays.stream(this.activeAbilities).filter((ability) -> ability != null).mapToInt((a) -> 1).sum();
   }

   public int countEquippedAbilities(Predicate<IAbility> check) {
      return Arrays.stream(this.activeAbilities).filter((ability) -> ability != null && check.test(ability)).mapToInt((a) -> 1).sum();
   }

   public <T extends IAbility> @Nullable T getEquippedOrPassiveAbility(AbilityCore<T> core) {
      return (T)(this.getEquippedAndPassiveAbilities().stream().filter((abl) -> abl.getCore().equals(core)).findFirst().orElse((Object)null));
   }

   public Set<IAbility> getEquippedAndPassiveAbilities() {
      Set<IAbility> set = new HashSet();
      set.addAll(this.getPassiveAbilities());
      set.addAll(this.getEquippedAbilities());
      return set;
   }

   public Set<IAbility> getEquippedAndPassiveAbilities(Predicate<IAbility> check) {
      Set<IAbility> set = new HashSet();
      set.addAll(this.getPassiveAbilities(check));
      set.addAll(this.getEquippedAbilities(check));
      return set;
   }

   public <T extends IAbility> T getPreviouslyUsedAbility() {
      return (T)this.previouslyUsedAbility;
   }

   public void setPreviouslyUsedAbility(IAbility abl) {
      this.previouslyUsedAbility = abl;
   }

   public int getCombatBarSet() {
      return Math.max(0, this.currentCombatBarSet);
   }

   public void nextCombatBarSet(int amount) {
      this.lastCombatBarSet = this.currentCombatBarSet;
      this.currentCombatBarSet = Mth.m_14045_(this.currentCombatBarSet + amount, 0, ServerConfig.getAbilityBars() - 1);
      if (this.owner != null && this.owner.m_9236_().f_46443_) {
         ModNetwork.sendToServer(new CSetCombatBarPacket(this.getCombatBarSet()));
      }

   }

   public void prevCombatBarSet(int amount) {
      this.lastCombatBarSet = this.currentCombatBarSet;
      this.currentCombatBarSet = Mth.m_14045_(this.currentCombatBarSet - amount, 0, ServerConfig.getAbilityBars() - 1);
      if (this.owner != null && this.owner.m_9236_().f_46443_) {
         ModNetwork.sendToServer(new CSetCombatBarPacket(this.getCombatBarSet()));
      }

   }

   public int getLastCombatBarSet() {
      return this.lastCombatBarSet;
   }

   public void setCombatBarSet(int set) {
      this.lastCombatBarSet = this.currentCombatBarSet;
      this.currentCombatBarSet = set;
      if (this.owner != null && this.owner.m_9236_().f_46443_) {
         ModNetwork.sendToServer(new CSetCombatBarPacket(this.getCombatBarSet()));
      }

   }

   public @Nullable Pair<AbilityCore<? extends IAbility>, Integer> getCoreIndexPair(AbilityNode node) {
      for(Map.Entry<Pair<AbilityCore<? extends IAbility>, Integer>, AbilityNode> entry : this.nodes.entrySet()) {
         if (node == entry.getValue()) {
            return (Pair)entry.getKey();
         }
      }

      return null;
   }

   public Set<AbilityNode> getNodes() {
      return Set.copyOf(this.nodes.values());
   }

   public AbilityNode getNode(AbilityCore<? extends IAbility> core, int index) {
      return (AbilityNode)this.nodes.get(Pair.of(core, index));
   }

   public void addNode(AbilityCore<? extends IAbility> core, int index, AbilityNode node) {
      this.nodes.put(Pair.of(core, index), node);
   }

   public CompoundTag serializeNBT() {
      CompoundTag props = new CompoundTag();

      try {
         ListTag unlockedAbilities = new ListTag();

         for(AbilityCoreUnlockWrapper<?> unlockedCore : this.getUnlockedAbilities()) {
            unlockedAbilities.add(unlockedCore.save());
         }

         props.m_128365_("unlocked_abilities", unlockedAbilities);
         ListTag passiveAbilities = new ListTag();

         for(IAbility abl : this.getPassiveAbilities()) {
            CompoundTag nbt = new CompoundTag();
            nbt.m_128359_("id", abl.getCore().getRegistryKey().toString());
            abl.save(nbt);
            passiveAbilities.add(nbt);
         }

         props.m_128365_("passive_abilities", passiveAbilities);
         ListTag equippedAbilities = new ListTag();
         int slotId = 0;

         for(IAbility abl : this.getRawEquippedAbilities()) {
            if (abl == null) {
               CompoundTag nbt = new CompoundTag();
               nbt.m_128405_("slot", slotId);
               equippedAbilities.add(nbt);
            } else {
               CompoundTag nbt = new CompoundTag();
               nbt.m_128405_("slot", slotId);
               nbt.m_128359_("id", abl.getCore().getRegistryKey().toString());
               abl.save(nbt);
               equippedAbilities.add(nbt);
            }

            ++slotId;
         }

         props.m_128365_("equipped_abilities", equippedAbilities);
         props.m_128405_("combat_bar_set", this.getCombatBarSet());
         ListTag nodesTag = new ListTag();

         for(Map.Entry<Pair<AbilityCore<? extends IAbility>, Integer>, AbilityNode> entry : this.nodes.entrySet()) {
            AbilityCore<? extends IAbility> core = (AbilityCore)((Pair)entry.getKey()).getKey();
            if (core != null) {
               int index = (Integer)((Pair)entry.getKey()).getValue();
               AbilityNode node = (AbilityNode)entry.getValue();
               if (node != null) {
                  CompoundTag nodeTag = node.save();
                  nodeTag.m_128359_("id", core.getRegistryKey().toString());
                  nodeTag.m_128405_("index", index);
                  nodesTag.add(nodeTag);
               }
            }
         }

         props.m_128365_("nodes", nodesTag);
      } catch (Exception ex) {
         ex.printStackTrace();
      }

      return props;
   }

   public void deserializeNBT(CompoundTag props) {
      // $FF: Couldn't be decompiled
   }
}
