package xyz.pixelatedw.mineminenomi.init;

import net.minecraftforge.registries.RegistryObject;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponentKey;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AltModeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AnimationComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AnimeScreamComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.BlockTrackerComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.BowTriggerComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChangeStatsComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ColaUsageComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ConsumptionComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DamageTakenComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DashComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DealDamageComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DisableComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ExplosionComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.GaugeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.GrabEntityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.HealComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.HitTrackerComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.HitTriggerComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ItemSpawnComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.MobEffectComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.MorphComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.PauseTickComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.PoolComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ProjectileComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RepeaterComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.SkinOverlayComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.SlotDecorationComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.StackComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.SwingTriggerComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.TeleportComponent;

public class ModAbilityComponents {
   public static final RegistryObject<AbilityComponentKey<CooldownComponent>> COOLDOWN = ModRegistry.registerAbilityComponent("cooldown");
   public static final RegistryObject<AbilityComponentKey<DisableComponent>> DISABLE = ModRegistry.registerAbilityComponent("disable");
   public static final RegistryObject<AbilityComponentKey<ContinuousComponent>> CONTINUOUS = ModRegistry.registerAbilityComponent("continuous");
   public static final RegistryObject<AbilityComponentKey<ChargeComponent>> CHARGE = ModRegistry.registerAbilityComponent("charge");
   public static final RegistryObject<AbilityComponentKey<RepeaterComponent>> REPEATER = ModRegistry.registerAbilityComponent("repeater");
   public static final RegistryObject<AbilityComponentKey<AnimationComponent>> ANIMATION = ModRegistry.registerAbilityComponent("animation");
   public static final RegistryObject<AbilityComponentKey<HitTrackerComponent>> HIT_TRACKER = ModRegistry.registerAbilityComponent("hit_tracker");
   public static final RegistryObject<AbilityComponentKey<GaugeComponent>> GAUGE = ModRegistry.registerAbilityComponent("gauge");
   public static final RegistryObject<AbilityComponentKey<SkinOverlayComponent>> SKIN_OVERLAY = ModRegistry.registerAbilityComponent("skin_overlay");
   public static final RegistryObject<AbilityComponentKey<ChangeStatsComponent>> CHANGE_STATS = ModRegistry.registerAbilityComponent("change_stats");
   public static final RegistryObject<AbilityComponentKey<SlotDecorationComponent>> SLOT_DECORATION = ModRegistry.registerAbilityComponent("slot_decoration");
   public static final RegistryObject<AbilityComponentKey<PoolComponent>> POOL = ModRegistry.registerAbilityComponent("pool_component");
   public static final RegistryObject<AbilityComponentKey<BowTriggerComponent>> BOW_TRIGGER = ModRegistry.registerAbilityComponent("bow_trigger");
   public static final RegistryObject<AbilityComponentKey<DamageTakenComponent>> DAMAGE_TAKEN = ModRegistry.registerAbilityComponent("damage_taken");
   public static final RegistryObject<AbilityComponentKey<ItemSpawnComponent>> ITEM_SPAWN = ModRegistry.registerAbilityComponent("item_spawn");
   public static final RegistryObject<AbilityComponentKey<StackComponent>> STACK = ModRegistry.registerAbilityComponent("stack");
   public static final RegistryObject<AbilityComponentKey<AltModeComponent<?>>> ALT_MODE = ModRegistry.registerAbilityComponent("alt_mode");
   public static final RegistryObject<AbilityComponentKey<DealDamageComponent>> DAMAGE = ModRegistry.registerAbilityComponent("damage");
   public static final RegistryObject<AbilityComponentKey<PauseTickComponent>> PAUSE_TICK = ModRegistry.registerAbilityComponent("pause_tick");
   public static final RegistryObject<AbilityComponentKey<HitTriggerComponent>> HIT_TRIGGER = ModRegistry.registerAbilityComponent("hit_trigger");
   public static final RegistryObject<AbilityComponentKey<AnimeScreamComponent>> ANIME_SCREAM = ModRegistry.registerAbilityComponent("anime_scream");
   public static final RegistryObject<AbilityComponentKey<SwingTriggerComponent>> SWING_TRIGGER = ModRegistry.registerAbilityComponent("swing_trigger");
   public static final RegistryObject<AbilityComponentKey<RangeComponent>> RANGE = ModRegistry.registerAbilityComponent("range");
   public static final RegistryObject<AbilityComponentKey<MorphComponent>> MORPH = ModRegistry.registerAbilityComponent("morph");
   public static final RegistryObject<AbilityComponentKey<GrabEntityComponent>> GRAB = ModRegistry.registerAbilityComponent("grab");
   public static final RegistryObject<AbilityComponentKey<ProjectileComponent>> PROJECTILE = ModRegistry.registerAbilityComponent("projectile");
   public static final RegistryObject<AbilityComponentKey<HealComponent>> HEAL = ModRegistry.registerAbilityComponent("heal");
   public static final RegistryObject<AbilityComponentKey<ConsumptionComponent>> CONSUMPTION = ModRegistry.registerAbilityComponent("consumption");
   public static final RegistryObject<AbilityComponentKey<ExplosionComponent>> EXPLOSION = ModRegistry.registerAbilityComponent("explosion");
   public static final RegistryObject<AbilityComponentKey<BlockTrackerComponent>> BLOCK_TRACKER = ModRegistry.registerAbilityComponent("block_tracker");
   public static final RegistryObject<AbilityComponentKey<MobEffectComponent>> MOB_EFFECT = ModRegistry.registerAbilityComponent("mob_effect");
   public static final RegistryObject<AbilityComponentKey<DashComponent>> DASH = ModRegistry.registerAbilityComponent("dash");
   public static final RegistryObject<AbilityComponentKey<TeleportComponent>> TELEPORT = ModRegistry.registerAbilityComponent("teleport");
   public static final RegistryObject<AbilityComponentKey<ColaUsageComponent>> COLA_USAGE = ModRegistry.registerAbilityComponent("cola_usage");

   public static void init() {
   }
}
