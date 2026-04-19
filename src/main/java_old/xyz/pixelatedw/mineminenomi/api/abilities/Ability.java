package xyz.pixelatedw.mineminenomi.api.abilities;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Stream;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Component.Serializer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.loading.FMLEnvironment;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponentKey;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AnimeScreamComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DisableComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.PoolComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.SlotDecorationComponent;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceElement;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.events.ability.AbilityTickEvent;
import xyz.pixelatedw.mineminenomi.api.events.ability.AbilityUseEvent;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.protection.ProtectedArea;
import xyz.pixelatedw.mineminenomi.api.util.Interval;
import xyz.pixelatedw.mineminenomi.api.util.PriorityEventPool;
import xyz.pixelatedw.mineminenomi.api.util.Result;
import xyz.pixelatedw.mineminenomi.config.ServerConfig;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityCapability;
import xyz.pixelatedw.mineminenomi.data.entity.ability.IAbilityData;
import xyz.pixelatedw.mineminenomi.data.entity.gcd.GCDCapability;
import xyz.pixelatedw.mineminenomi.data.world.ProtectedAreasData;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18nAbilities;
import xyz.pixelatedw.mineminenomi.packets.server.ability.SStartGCDPacket;

public abstract class Ability implements IAbility, IDescriptiveAbility {
   private Component displayName;
   private ResourceLocation displayIcon;
   private AbilityUnlock unlock;
   private final AbilityCore<? extends IAbility> core;
   private long lastUseTime;
   private boolean isOGCD;
   protected final DisableComponent disableComponent;
   protected final CooldownComponent cooldownComponent;
   protected final AnimeScreamComponent screamComponent;
   private Map<AbilityComponentKey<?>, AbilityComponent<?>> components;
   protected final Random random;
   private final PriorityEventPool<ICanUseEvent> onCanUseEvents;
   private final PriorityEventPool<ICanUseEvent> onContinueUseEvents;
   private final PriorityEventPool<IOnUseEvent> onUseEvents;
   private final PriorityEventPool<IOnTickEvent> onTickEvents;
   private final PriorityEventPool<IOnEquipEvent> onEquipEvents;
   private final PriorityEventPool<IOnRemoveEvent> onRemoveEvents;
   private Interval continueUseCheckInterval;

   public Ability(AbilityCore<? extends IAbility> core) {
      this.unlock = AbilityUnlock.PROGRESSION;
      this.disableComponent = new DisableComponent(this);
      this.cooldownComponent = new CooldownComponent(this);
      this.screamComponent = new AnimeScreamComponent(this);
      this.components = new LinkedHashMap();
      this.random = new Random();
      this.onCanUseEvents = new PriorityEventPool<ICanUseEvent>();
      this.onContinueUseEvents = new PriorityEventPool<ICanUseEvent>();
      this.onUseEvents = new PriorityEventPool<IOnUseEvent>();
      this.onTickEvents = new PriorityEventPool<IOnTickEvent>();
      this.onEquipEvents = new PriorityEventPool<IOnEquipEvent>();
      this.onRemoveEvents = new PriorityEventPool<IOnRemoveEvent>();
      this.continueUseCheckInterval = new Interval(20);
      this.core = core;
      if (this.isClientSide()) {
         SlotDecorationComponent slotDecorationComponent = new SlotDecorationComponent(this);
         this.addComponents(slotDecorationComponent);
      }

      this.addComponents(this.cooldownComponent, this.disableComponent, this.screamComponent);
   }

   public final void use(LivingEntity entity) {
      Result result = this.canUse(entity);
      if (result.isFail()) {
         if (result.getMessage() != null) {
            WyHelper.sendMessage(entity, result.getMessage());
         }

      } else {
         AbilityUseEvent pre = new AbilityUseEvent.Pre(entity, this);
         if (!MinecraftForge.EVENT_BUS.post(pre)) {
            this.dispatchUseEvents(entity);
            this.lastUseTime = entity.m_9236_().m_46467_();
            AbilityUseEvent post = new AbilityUseEvent.Post(entity, this);
            MinecraftForge.EVENT_BUS.post(post);
            AbilityCapability.get(entity).ifPresent((props) -> props.setPreviouslyUsedAbility(this));
            if (entity instanceof Mob && !this.isOGCD()) {
               GCDCapability.startGCD(entity);
               if (entity instanceof Player) {
                  ModNetwork.sendTo(new SStartGCDPacket(entity.m_19879_()), (Player)entity);
               }
            }

         }
      }
   }

   public void onEquip(LivingEntity entity) {
      this.onEquipEvents.dispatch((event) -> event.onEquip(entity, this));
   }

   public void onRemove(LivingEntity entity) {
      this.getComponent((AbilityComponentKey)ModAbilityComponents.CONTINUOUS.get()).ifPresent((comp) -> comp.stopContinuity(entity));
      this.getComponent((AbilityComponentKey)ModAbilityComponents.MORPH.get()).ifPresent((comp) -> comp.stopMorph(entity));
      this.getComponent((AbilityComponentKey)ModAbilityComponents.CHANGE_STATS.get()).ifPresent((comp) -> comp.removeModifiers(entity));
      this.onRemoveEvents.dispatch((event) -> event.onRemove(entity, this));
   }

   public void addEquipEvent(IOnEquipEvent event) {
      this.addEquipEvent(100, event);
   }

   public void addEquipEvent(int priority, IOnEquipEvent event) {
      this.onEquipEvents.addEvent(priority, event);
   }

   public void addRemoveEvent(IOnRemoveEvent event) {
      this.addRemoveEvent(100, event);
   }

   public void addRemoveEvent(int priority, IOnRemoveEvent event) {
      this.onRemoveEvents.addEvent(priority, event);
   }

   public void addUseEvent(IOnUseEvent event) {
      this.addUseEvent(100, event);
   }

   public void addUseEvent(int priority, IOnUseEvent event) {
      this.onUseEvents.addEvent(priority, event);
   }

   public boolean hasUseEvent(IOnUseEvent event) {
      return this.onUseEvents.getEventsStream().anyMatch((e) -> e.equals(event));
   }

   public void removeUseEvent(IOnUseEvent event) {
      this.onUseEvents.removeEvent(event);
   }

   public void dispatchUseEvents(LivingEntity entity) {
      this.onUseEvents.dispatch((event) -> event.onUse(entity, this));
   }

   public void addTickEvent(IOnTickEvent event) {
      this.addTickEvent(100, event);
   }

   public void addTickEvent(int priority, IOnTickEvent event) {
      this.onTickEvents.addEvent(priority, event);
   }

   public void clearUseChecks() {
      this.onCanUseEvents.clearEvents();
   }

   public void addCanUseCheck(ICanUseEvent event) {
      this.addCanUseCheck(100, event);
   }

   public void addCanUseCheck(int priority, ICanUseEvent event) {
      this.onCanUseEvents.addEvent(priority, event);
   }

   public Stream<ICanUseEvent> getCanUseChecksStream() {
      return this.onCanUseEvents.getEventsStream();
   }

   public void clearContinueChecks() {
      this.onContinueUseEvents.clearEvents();
   }

   public void addContinueUseCheck(ICanUseEvent event) {
      this.addContinueUseCheck(100, event);
   }

   public void addContinueUseCheck(int priority, ICanUseEvent event) {
      this.onContinueUseEvents.addEvent(priority, event);
   }

   public Stream<ICanUseEvent> getContinueUseChecksStream() {
      return this.onContinueUseEvents.getEventsStream();
   }

   public boolean isOnCooldown() {
      Optional<CooldownComponent> cooldownComponent = this.<CooldownComponent>getComponent((AbilityComponentKey)ModAbilityComponents.COOLDOWN.get());
      return cooldownComponent.isPresent() && ((CooldownComponent)cooldownComponent.get()).isOnCooldown();
   }

   public boolean isContinuous() {
      Optional<ContinuousComponent> continuousComponent = this.<ContinuousComponent>getComponent((AbilityComponentKey)ModAbilityComponents.CONTINUOUS.get());
      return continuousComponent.isPresent() && ((ContinuousComponent)continuousComponent.get()).isContinuous();
   }

   public boolean isCharging() {
      Optional<ChargeComponent> chargeComponent = this.<ChargeComponent>getComponent((AbilityComponentKey)ModAbilityComponents.CHARGE.get());
      return chargeComponent.isPresent() && ((ChargeComponent)chargeComponent.get()).isCharging();
   }

   public boolean isDisabled() {
      Optional<DisableComponent> disableComponent = this.<DisableComponent>getComponent((AbilityComponentKey)ModAbilityComponents.DISABLE.get());
      return disableComponent.isPresent() && ((DisableComponent)disableComponent.get()).isDisabled();
   }

   public String getDisplayNameOld() {
      return this.displayName != null ? this.displayName.getString() : this.getCore().getLocalizedName().getString();
   }

   public boolean hasCustomName() {
      return this.displayName != null;
   }

   public Component getDisplayName() {
      return this.displayName != null ? this.displayName : this.getCore().getLocalizedName();
   }

   public void setDisplayName(Component name) {
      this.displayName = name;
   }

   public boolean hasCustomIcon() {
      return this.displayIcon != null;
   }

   public ResourceLocation getIcon(LivingEntity entity) {
      return this.hasCustomIcon() ? this.displayIcon : this.getCore().getIcon();
   }

   public void setDisplayIcon(ResourceLocation texture) {
      this.displayIcon = texture;
   }

   public void setDisplayIcon(AbilityCore<?> core) {
      this.displayIcon = core.getIcon();
   }

   public AbilityCategory getCategory() {
      return this.getCore().getCategory();
   }

   public SourceHakiNature getSourceHakiNature() {
      return this.getCore().getSourceHakiNature();
   }

   public ArrayList<SourceType> getSourceTypes() {
      return this.getCore().getSourceTypes();
   }

   public boolean hasSourceTypes(SourceType type) {
      return this.getCore().hasType(type);
   }

   public SourceElement getSourceElement() {
      return this.getCore().getSourceElement();
   }

   public long getLastUseGametime() {
      return this.lastUseTime;
   }

   public AbilityCore<? extends IAbility> getCore() {
      return this.core;
   }

   public void setOGCD() {
      this.isOGCD = true;
   }

   public boolean isOGCD() {
      return this.isOGCD;
   }

   public final void tick(LivingEntity entity) {
      if (!entity.m_9236_().f_46443_) {
         boolean shouldStopUsage = false;
         if (this.continueUseCheckInterval.canTick()) {
            shouldStopUsage = this.getContinueUseChecksStream().anyMatch((p) -> p.canUse(entity, this).isFail());
         }

         if (this.isDisabled() || shouldStopUsage) {
            AbilityHelper.emergencyStopAbility(entity, this);
         }
      }

      this.onTickEvents.dispatch((event) -> event.tick(entity, this));
      this.components.values().forEach((component) -> component.tick(entity));
      AbilityTickEvent event = new AbilityTickEvent(entity, this);
      MinecraftForge.EVENT_BUS.post(event);
   }

   public boolean equals(Object abl) {
      if (!(abl instanceof Ability)) {
         return false;
      } else if (this.getCore() != null && ((Ability)abl).getCore() != null) {
         return this.getCore().equals(((Ability)abl).getCore());
      } else {
         return false;
      }
   }

   public final void save(CompoundTag nbt) {
      IAbility.super.save(nbt);
      if (this.hasCustomName()) {
         nbt.m_128359_("displayName", Serializer.m_130703_(this.getDisplayName()));
      }

      if (this.hasCustomIcon()) {
         nbt.m_128359_("displayIcon", this.displayIcon.toString());
      }

      this.saveAdditional(nbt);
   }

   public final void load(CompoundTag nbt) {
      IAbility.super.load(nbt);
      if (nbt.m_128441_("displayName")) {
         this.setDisplayName(Serializer.m_130701_(nbt.m_128461_("displayName")));
      }

      if (nbt.m_128441_("displayIcon")) {
         this.setDisplayIcon(ResourceLocation.parse(nbt.m_128461_("displayIcon")));
      }

      this.loadAdditional(nbt);
   }

   public void saveAdditional(CompoundTag tag) {
   }

   public void loadAdditional(CompoundTag tag) {
   }

   public void addComponents(AbilityComponent<?>... comps) {
      for(AbilityComponent<?> comp : comps) {
         this.components.put(comp.getKey(), comp);
      }

      if (FMLEnvironment.dist.isClient()) {
         this.components.values().stream().forEach((c) -> c.postInit(this));
      } else {
         this.components.values().stream().filter((compx) -> !compx.isClientSided()).forEach((c) -> c.postInit(this));
      }

   }

   public boolean hasComponent(AbilityComponentKey<?> key) {
      return this.components.containsKey(key);
   }

   public <C extends AbilityComponent<?>> Optional<C> getComponent(AbilityComponentKey<C> key) {
      if (!this.hasComponent(key)) {
         return Optional.empty();
      } else {
         C comp = (C)(this.components.get(key));
         return comp.isClientSided() && !FMLEnvironment.dist.isClient() ? Optional.empty() : Optional.of(comp);
      }
   }

   public Map<AbilityComponentKey<?>, AbilityComponent<?>> getComponents() {
      return this.components;
   }

   public final Result canUse(LivingEntity entity) {
      if (entity.m_21023_((MobEffect)ModEffects.IN_EVENT.get())) {
         return Result.fail((Component)null);
      } else if (!this.isOGCD && GCDCapability.isOnGCD(entity)) {
         return Result.fail((Component)null);
      } else if (this.disableComponent.isDisabled()) {
         return Result.fail((Component)null);
      } else if (this.cooldownComponent.isOnCooldown()) {
         return Result.fail((Component)null);
      } else {
         if (!entity.m_9236_().f_46443_) {
            ServerLevel serverLevel = (ServerLevel)entity.m_9236_();
            ProtectedAreasData worldData = ProtectedAreasData.get(serverLevel);
            Optional<ProtectedArea> area = worldData.getProtectedArea(entity.m_20183_().m_123341_(), entity.m_20183_().m_123342_(), entity.m_20183_().m_123343_());
            if (area.isPresent() && !((ProtectedArea)area.get()).canUseAbility(this.getCore())) {
               return Result.fail(ModI18nAbilities.MESSAGE_CANNOT_USE_HERE);
            }
         }

         IAbilityData abilityDataProps = (IAbilityData)AbilityCapability.get(entity).orElse((Object)null);
         Optional<PoolComponent> poolComponent = this.<PoolComponent>getComponent((AbilityComponentKey)ModAbilityComponents.POOL.get());
         if (poolComponent.isPresent()) {
            boolean switchAbility = ((PoolComponent)poolComponent.get()).getPools().stream().anyMatch((pool) -> (Boolean)pool.getFlagValue("switchContinuous", () -> false));
            if (switchAbility) {
               Optional<IAbility> otherAbility = abilityDataProps.getEquippedAbilities().stream().filter((abl) -> abl.hasComponent((AbilityComponentKey)ModAbilityComponents.CONTINUOUS.get()) && ((ContinuousComponent)abl.getComponent((AbilityComponentKey)ModAbilityComponents.CONTINUOUS.get()).get()).isContinuous()).filter((abl) -> abl.hasComponent((AbilityComponentKey)ModAbilityComponents.POOL.get()) && ((PoolComponent)abl.getComponent((AbilityComponentKey)ModAbilityComponents.POOL.get()).get()).getPools().stream().anyMatch((pool) -> (Boolean)pool.getFlagValue("switchContinuous", () -> false))).findFirst();
               if (otherAbility.isPresent()) {
                  ((ContinuousComponent)((IAbility)otherAbility.get()).getComponent((AbilityComponentKey)ModAbilityComponents.CONTINUOUS.get()).get()).stopContinuity(entity);
                  ((PoolComponent)((IAbility)otherAbility.get()).getComponent((AbilityComponentKey)ModAbilityComponents.POOL.get()).get()).stopPoolInUse(entity);
               }
            }

            if (((PoolComponent)poolComponent.get()).isPoolInUse()) {
               return Result.fail(ModI18nAbilities.MESSAGE_POOL_ALREADY_IN_USE);
            }
         }

         Optional<ContinuousComponent> continuousComponent = this.<ContinuousComponent>getComponent((AbilityComponentKey)ModAbilityComponents.CONTINUOUS.get());
         if (abilityDataProps != null && continuousComponent.isPresent() && !((ContinuousComponent)continuousComponent.get()).isContinuous() && !((ContinuousComponent)continuousComponent.get()).isParallel()) {
            for(IAbility ability : abilityDataProps.getEquippedAbilities()) {
               if (!ability.equals(this)) {
                  Optional<ContinuousComponent> otherComponent = ability.<ContinuousComponent>getComponent((AbilityComponentKey)ModAbilityComponents.CONTINUOUS.get());
                  if (otherComponent.isPresent() && ((ContinuousComponent)otherComponent.get()).isContinuous() && !((ContinuousComponent)otherComponent.get()).isParallel()) {
                     if (!ServerConfig.getStopContinuousAbility() || !this.hasComponent((AbilityComponentKey)ModAbilityComponents.CONTINUOUS.get())) {
                        return Result.fail((Component)null);
                     }

                     ((ContinuousComponent)otherComponent.get()).stopContinuity(entity);
                  }
               }
            }
         }

         if (continuousComponent.isPresent() && ((ContinuousComponent)continuousComponent.get()).isContinuous()) {
            return Result.success();
         } else {
            Optional<ChargeComponent> chargeComponent = this.<ChargeComponent>getComponent((AbilityComponentKey)ModAbilityComponents.CHARGE.get());
            if (chargeComponent.isPresent() && ((ChargeComponent)chargeComponent.get()).isCharging()) {
               return Result.success();
            } else {
               Optional<Result> canUseResult = this.onCanUseEvents.getEventsStream().map((event) -> event.canUse(entity, this)).filter((result) -> result.isFail()).findFirst();
               if (canUseResult.isPresent()) {
                  return (Result)canUseResult.get();
               } else {
                  return Result.success();
               }
            }
         }
      }
   }

   public void appendDescription(Player player, List<Component> desc) {
   }

   public boolean isClientSide() {
      return FMLEnvironment.dist.isClient();
   }

   @FunctionalInterface
   public interface ICanUseEvent {
      Result canUse(LivingEntity var1, IAbility var2);

      default ICanUseEvent and(ICanUseEvent check) {
         return (entity, ability) -> {
            Result result1 = this.canUse(entity, ability);
            if (result1.isFail()) {
               return result1;
            } else {
               Result result2 = check.canUse(entity, ability);
               return result2.isFail() ? result2 : Result.success();
            }
         };
      }

      default ICanUseEvent or(ICanUseEvent check) {
         return (entity, ability) -> {
            Result result1 = this.canUse(entity, ability);
            Result result2 = check.canUse(entity, ability);
            return !result1.isSuccess() && !result2.isSuccess() ? (result1.isFail() ? result1 : result2) : Result.success();
         };
      }

      default ICanUseEvent not() {
         return (entity, ability) -> {
            Result result = this.canUse(entity, ability);
            return result.isSuccess() ? Result.fail((Component)null) : Result.success();
         };
      }
   }

   @FunctionalInterface
   public interface IOnEquipEvent {
      void onEquip(LivingEntity var1, IAbility var2);
   }

   @FunctionalInterface
   public interface IOnRemoveEvent {
      void onRemove(LivingEntity var1, IAbility var2);
   }

   @FunctionalInterface
   public interface IOnTickEvent {
      void tick(LivingEntity var1, IAbility var2);
   }

   @FunctionalInterface
   public interface IOnUseEvent {
      void onUse(LivingEntity var1, IAbility var2);
   }
}
