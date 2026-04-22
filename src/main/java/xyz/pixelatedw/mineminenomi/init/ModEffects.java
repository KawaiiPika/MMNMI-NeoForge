package xyz.pixelatedw.mineminenomi.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import xyz.pixelatedw.mineminenomi.ModMain;
import xyz.pixelatedw.mineminenomi.effects.*;
import xyz.pixelatedw.mineminenomi.effects.BubblyCoralEffect;

import java.util.function.Supplier;

public class ModEffects {
    public static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(Registries.MOB_EFFECT, ModMain.PROJECT_ID);

    public static final net.neoforged.neoforge.registries.DeferredHolder<MobEffect, MobEffect> DF_CURSE = EFFECTS.register("df_curse", DFCurseEffect::new);
    public static final net.neoforged.neoforge.registries.DeferredHolder<MobEffect, MobEffect> DOKU_POISON = EFFECTS.register("doku_poison", DokuPoisonEffect::new);
    public static final net.neoforged.neoforge.registries.DeferredHolder<MobEffect, MobEffect> MOVEMENT_BLOCKED = EFFECTS.register("movement_blocked", MovementBlockedEffect::new);
    public static final net.neoforged.neoforge.registries.DeferredHolder<MobEffect, MobEffect> HAKI_OVERUSE = EFFECTS.register("haki_overuse", HakiOveruseEffect::new);
    public static final net.neoforged.neoforge.registries.DeferredHolder<MobEffect, MobEffect> FROZEN = EFFECTS.register("frozen", FrozenEffect::new);
    public static final net.neoforged.neoforge.registries.DeferredHolder<MobEffect, MobEffect> PARALYSIS = EFFECTS.register("paralysis", ParalysisEffect::new);
    public static final net.neoforged.neoforge.registries.DeferredHolder<MobEffect, MobEffect> DIZZY = EFFECTS.register("dizzy", DizzyEffect::new);
    public static final net.neoforged.neoforge.registries.DeferredHolder<MobEffect, MobEffect> FATIGUE = EFFECTS.register("fatigue", FatigueEffect::new);
    public static final net.neoforged.neoforge.registries.DeferredHolder<MobEffect, MobEffect> FRAGILE = EFFECTS.register("fragile", FragileEffect::new);
    public static final net.neoforged.neoforge.registries.DeferredHolder<MobEffect, MobEffect> FROSTBITE = EFFECTS.register("frostbite", FrostbiteEffect::new);
    public static final net.neoforged.neoforge.registries.DeferredHolder<MobEffect, MobEffect> LOVESTRUCK = EFFECTS.register("lovestruck", LovestruckEffect::new);
    public static final net.neoforged.neoforge.registries.DeferredHolder<MobEffect, MobEffect> NEGATIVE = EFFECTS.register("negative", NegativeEffect::new);
    public static final net.neoforged.neoforge.registries.DeferredHolder<MobEffect, MobEffect> UNCONSCIOUS = EFFECTS.register("unconscious", UnconsciousEffect::new);
    public static final net.neoforged.neoforge.registries.DeferredHolder<MobEffect, MobEffect> CANDLE_LOCK = EFFECTS.register("candle_lock", CandleLockEffect::new);
    public static final net.neoforged.neoforge.registries.DeferredHolder<MobEffect, MobEffect> CANDY_STUCK = EFFECTS.register("candy_stuck", CandyStuckEffect::new);
    public static final net.neoforged.neoforge.registries.DeferredHolder<MobEffect, MobEffect> ANTIDOTE = EFFECTS.register("antidote", AntidoteEffect::new);
    public static final net.neoforged.neoforge.registries.DeferredHolder<MobEffect, MobEffect> DEHYDRATION = EFFECTS.register("dehydration", DehydrationEffect::new);
    public static final net.neoforged.neoforge.registries.DeferredHolder<MobEffect, MobEffect> TENSION_HORMONE = EFFECTS.register("tension_hormone", TensionHormoneEffect::new);
    public static final net.neoforged.neoforge.registries.DeferredHolder<MobEffect, MobEffect> CHIYU_HORMONE = EFFECTS.register("chiyu_hormone", ChiyuHormoneEffect::new);
    public static final net.neoforged.neoforge.registries.DeferredHolder<MobEffect, MobEffect> HUNGER = EFFECTS.register("hunger", () -> new HungerOverTimeEffect(2.0F, 20));
    public static final net.neoforged.neoforge.registries.DeferredHolder<MobEffect, MobEffect> DRUNK = EFFECTS.register("drunk", DrunkEffect::new);
    public static final net.neoforged.neoforge.registries.DeferredHolder<MobEffect, MobEffect> STICKY = EFFECTS.register("sticky", StickyEffect::new);
    public static final net.neoforged.neoforge.registries.DeferredHolder<MobEffect, MobEffect> MUMMY_VIRUS = EFFECTS.register("mummy_virus", MummyVirusEffect::new);
    public static final net.neoforged.neoforge.registries.DeferredHolder<MobEffect, MobEffect> ICE_ONI = EFFECTS.register("ice_oni", IceOniEffect::new);
    public static final net.neoforged.neoforge.registries.DeferredHolder<MobEffect, MobEffect> ICE_ONI_FROSTBITE = EFFECTS.register("ice_oni_frostbite", () -> new FrostbiteEffect());
    public static final net.neoforged.neoforge.registries.DeferredHolder<MobEffect, MobEffect> BLEEDING = EFFECTS.register("bleeding", () -> new DamageOverTimeEffect(0x8B0000, (entity) -> ModDamageSources.getInstance().bleed(entity), 2.0F, 20));
    public static final net.neoforged.neoforge.registries.DeferredHolder<MobEffect, MobEffect> HANDCUFFED = EFFECTS.register("handcuffed", () -> new HandcuffedEffect(xyz.pixelatedw.mineminenomi.api.enums.HandcuffType.NORMAL));
    public static final net.neoforged.neoforge.registries.DeferredHolder<MobEffect, MobEffect> HANDCUFFED_KAIROSEKI = EFFECTS.register("handcuffed_kairoseki", () -> new HandcuffedEffect(xyz.pixelatedw.mineminenomi.api.enums.HandcuffType.KAIROSEKI));
    public static final net.neoforged.neoforge.registries.DeferredHolder<MobEffect, MobEffect> HANDCUFFED_EXPLOSIVE = EFFECTS.register("handcuffed_explosive", () -> new HandcuffedEffect(xyz.pixelatedw.mineminenomi.api.enums.HandcuffType.EXPLOSIVE));
    public static final net.neoforged.neoforge.registries.DeferredHolder<MobEffect, MobEffect> WASHED = EFFECTS.register("washed", WashedEffect::new);
    public static final net.neoforged.neoforge.registries.DeferredHolder<MobEffect, MobEffect> BUBBLY_CORAL = EFFECTS.register("bubbly_coral", BubblyCoralEffect::new);

    public static void register(IEventBus bus) {
        EFFECTS.register(bus);
    }
}
