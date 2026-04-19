package xyz.pixelatedw.mineminenomi.api.abilities;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Component.Serializer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.fml.loading.FMLEnvironment;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponentKey;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DisableComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.PauseTickComponent;
import xyz.pixelatedw.mineminenomi.api.protection.ProtectedArea;
import xyz.pixelatedw.mineminenomi.api.util.PriorityEventPool;
import xyz.pixelatedw.mineminenomi.api.util.Result;
import xyz.pixelatedw.mineminenomi.data.world.ProtectedAreasData;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;

public abstract class PassiveAbility implements IAbility {
   private final AbilityCore<? extends PassiveAbility> core;
   private Component displayName;
   private ResourceLocation displayIcon;
   private ResourceLocation cachedIcon;
   private float tickRate = 1.0F;
   private float currentTick = 0.0F;
   protected final DisableComponent disableComponent = new DisableComponent(this);
   protected final CooldownComponent cooldownComponent = new CooldownComponent(this);
   protected final PauseTickComponent pauseTickComponent = new PauseTickComponent(this);
   private PriorityEventPool<Ability.ICanUseEvent> onCanUseEvents = new PriorityEventPool<Ability.ICanUseEvent>();
   private PriorityEventPool<IDuringPassiveEvent> duringPassiveEvents = new PriorityEventPool<IDuringPassiveEvent>();
   private PriorityEventPool<IOnEquipEvent> onEquipEvents = new PriorityEventPool<IOnEquipEvent>();
   private PriorityEventPool<IOnRemoveEvent> onRemoveEvents = new PriorityEventPool<IOnRemoveEvent>();
   private Map<AbilityComponentKey<?>, AbilityComponent<?>> components = new LinkedHashMap();
   protected final Random random = new Random();

   public PassiveAbility(AbilityCore<? extends PassiveAbility> core) {
      this.core = core;
      this.addComponents(this.cooldownComponent, this.disableComponent, this.pauseTickComponent);
   }

   public void onEquip(LivingEntity entity) {
      this.onEquipEvents.dispatch((event) -> event.onEquip(entity, this));
   }

   public void onRemove(LivingEntity entity) {
      this.getComponent((AbilityComponentKey)ModAbilityComponents.CHANGE_STATS.get()).ifPresent((comp) -> comp.removeModifiers(entity));
      this.onRemoveEvents.dispatch((event) -> event.onRemove(entity, this));
   }

   public void use(LivingEntity entity) {
   }

   public void tick(LivingEntity entity) {
      this.components.values().forEach((component) -> component.tick(entity));
      if (!this.canUse(entity).isFail()) {
         if (!entity.m_9236_().f_46443_) {
            ProtectedAreasData worldData = ProtectedAreasData.get((ServerLevel)entity.m_9236_());
            Optional<ProtectedArea> area = worldData.getProtectedArea(entity.m_20183_().m_123341_(), entity.m_20183_().m_123342_(), entity.m_20183_().m_123343_());
            if (area.isPresent() && !((ProtectedArea)area.get()).canUseAbility(this.getCore())) {
               return;
            }
         }

         if (!(--this.currentTick > 0.0F)) {
            this.currentTick = this.tickRate;
            this.duringPassiveEvents.dispatch((event) -> event.duringPassive(entity));
         }
      }
   }

   public Result canUse(LivingEntity entity) {
      if (!this.disableComponent.isDisabled() && !this.pauseTickComponent.isPaused() && !this.cooldownComponent.isOnCooldown()) {
         Optional<Result> canUseResult = this.onCanUseEvents.getEventsStream().map((event) -> event.canUse(entity, this)).filter((result) -> result.isFail()).findFirst();
         return canUseResult.isPresent() ? (Result)canUseResult.get() : Result.success();
      } else {
         return Result.fail((Component)null);
      }
   }

   public void addEquipEvent(IOnEquipEvent event) {
      this.onEquipEvents.addEvent(event);
   }

   public void addEquipEvent(int priority, IOnEquipEvent event) {
      this.onEquipEvents.addEvent(priority, event);
   }

   public void addRemoveEvent(IOnRemoveEvent event) {
      this.onRemoveEvents.addEvent(event);
   }

   public void addRemoveEvent(int priority, IOnRemoveEvent event) {
      this.onRemoveEvents.addEvent(priority, event);
   }

   public void addCanUseCheck(Ability.ICanUseEvent event) {
      this.onCanUseEvents.addEvent(event);
   }

   public void addCanUseCheck(int priority, Ability.ICanUseEvent event) {
      this.onCanUseEvents.addEvent(priority, event);
   }

   public void addDuringPassiveEvent(IDuringPassiveEvent event) {
      this.duringPassiveEvents.addEvent(event);
   }

   public void addDuringPassiveEvent(int priority, IDuringPassiveEvent event) {
      this.duringPassiveEvents.addEvent(priority, event);
   }

   public void setTickRate(float tickRate) {
      this.tickRate = tickRate;
      this.currentTick = tickRate;
   }

   public AbilityCore<?> getCore() {
      return this.core;
   }

   public boolean hasCustomName() {
      return this.displayName != null;
   }

   public void setDisplayName(Component name) {
      this.displayName = name;
   }

   public Component getDisplayName() {
      return this.hasCustomName() ? this.displayName : this.getCore().getLocalizedName();
   }

   public boolean hasCustomIcon() {
      return this.displayIcon != null;
   }

   public void setDisplayIcon(ResourceLocation texture) {
      this.displayIcon = texture;
   }

   public ResourceLocation getIcon(LivingEntity entity) {
      if (this.core.getIcon() == null && this.cachedIcon == null) {
         ResourceLocation key = this.core.getRegistryKey();
         this.cachedIcon = ResourceLocation.fromNamespaceAndPath(key.m_135827_(), "textures/abilities/" + key.m_135815_() + ".png");
         return this.cachedIcon;
      } else if (this.hasCustomIcon()) {
         return this.displayIcon;
      } else {
         return this.getCore().getIcon() != null ? this.getCore().getIcon() : this.cachedIcon;
      }
   }

   public boolean isPaused() {
      return this.pauseTickComponent.isPaused();
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

   public boolean hasComponent(AbilityComponentKey<?> key) {
      return this.components.containsKey(key);
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

   public boolean isClientSide() {
      return FMLEnvironment.dist.isClient();
   }

   public boolean isOGCD() {
      return true;
   }

   @FunctionalInterface
   public interface IDuringPassiveEvent {
      void duringPassive(LivingEntity var1);
   }

   @FunctionalInterface
   public interface IOnEquipEvent {
      void onEquip(LivingEntity var1, PassiveAbility var2);
   }

   @FunctionalInterface
   public interface IOnRemoveEvent {
      void onRemove(LivingEntity var1, PassiveAbility var2);
   }
}
