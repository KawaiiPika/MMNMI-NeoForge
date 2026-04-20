package xyz.pixelatedw.mineminenomi.init;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import xyz.pixelatedw.mineminenomi.client.particles.SimpleParticle;
import xyz.pixelatedw.mineminenomi.particles.SimpleParticleType;

import static xyz.pixelatedw.mineminenomi.init.ModRegistry.PARTICLE_TYPES;

public class ModParticleTypes {

    private static DeferredHolder<ParticleType<?>, SimpleParticleType> register(String name) {
        return PARTICLE_TYPES.register(name, () -> new SimpleParticleType(true));
    }

    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> YUKI = register("yuki_particle");
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> YUKI2 = register("yuki2_particle");
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> YUKI3 = register("yuki3_particle");
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> PIKA = register("pika_particle");
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> MERA = register("mera_particle");
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> MERA2 = register("mera2_particle");
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> MOKU = register("moku_particle");
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> MOKU2 = register("moku2_particle");
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> SUNA = register("suna_particle");
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> SUNA2 = register("suna2_particle");
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> GASU = register("gasu_particle");
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> GASU2 = register("gasu2_particle");
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> BLUE_FLAME = register("blue_flame_particle");
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> DARKNESS = register("darkness_particle");
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> DARKNESS_STATIC = register("darkness_static_particle");
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> KUROUZU = register("kurouzu_particle");
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> GORO = register("goro_particle");
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> GORO2 = register("goro2_particle");
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> GORO3 = register("goro3_particle");
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> MAGU = register("magu_particle");
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> DOKU = register("doku_particle");
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> DOKU_PINK = register("doku_pink_particle");
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> ITO = register("ito_particle");
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> GURA = register("gura_particle");
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> GURA2 = register("gura2_particle");
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> HIE = register("hie_particle");
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> HIE_FLAKES = register("hie_flakes_particle");
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> MERO = register("mero_particle");
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> HORU = register("horu_particle");
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> CHIYU = register("chiyu_particle");
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> RUST = register("rust_particle");
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> AWA = register("awa_particle");
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> AWA2 = register("awa2_particle");
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> AWA3 = register("awa3_particle");
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> AWA_FOAM = register("awa_foam_particle");
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> WASHED = register("washed_particle");
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> BETA = register("beta_particle");
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> NETSU = register("netsu_particle");
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> NETSU2 = register("netsu2_particle");
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> GRILL = register("grill_particle");
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> MEDIC = register("medic_particle");
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> GORO_YELLOW = register("goro_yellow_particle");
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> GORO2_YELLOW = register("goro2_yellow_particle");
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> GORO3_YELLOW = register("goro3_yellow_particle");
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> HANA = register("hana_particle");
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> MAGNET = register("magnet_particle");
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> SIMPLE_CIRCLE = register("simple_circle");
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> DOUBLE_CIRCLE = register("double_circle");
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> HOLE = register("hole");
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> COMMAND_MARK = register("command_mark_particle");

    @EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD, value = net.neoforged.api.distmarker.Dist.CLIENT)
    public static class Client {
        @SubscribeEvent
        public static void registerParticleFactories(RegisterParticleProvidersEvent event) {
            register(event, YUKI, "yuki");
            register(event, YUKI2, "yuki2");
            register(event, YUKI3, "yuki3");
            register(event, PIKA, "pika");
            register(event, MERA, "mera", 8);
            register(event, MERA2, "mera2");
            register(event, MOKU, "moku");
            register(event, MOKU2, "moku2");
            register(event, SUNA, "suna");
            register(event, SUNA2, "suna2");
            register(event, GASU, "gasu");
            register(event, GASU2, "gasu2");
            register(event, BLUE_FLAME, "blue_flame", 8);
            register(event, DARKNESS, "darkness", 8);
            register(event, DARKNESS_STATIC, "darkness_static");
            register(event, KUROUZU, "kurouzu");
            register(event, GORO, "goro");
            register(event, GORO2, "goro2");
            register(event, GORO3, "goro3");
            register(event, MAGU, "magu");
            register(event, DOKU, "doku", 6);
            register(event, DOKU_PINK, "doku_pink");
            register(event, ITO, "ito");
            register(event, GURA, "gura", 4);
            register(event, GURA2, "gura2");
            register(event, HIE, "hie", 8);
            register(event, HIE_FLAKES, "hie_flakes");
            register(event, MERO, "mero");
            register(event, HORU, "horu");
            register(event, CHIYU, "chiyu");
            register(event, RUST, "rust");
            register(event, AWA, "awa");
            register(event, AWA2, "awa2");
            register(event, AWA3, "awa3");
            register(event, AWA_FOAM, "awa_foam");
            register(event, WASHED, "washed");
            register(event, BETA, "beta");
            register(event, NETSU, "netsu");
            register(event, NETSU2, "netsu2");
            register(event, GRILL, "grill");
            register(event, MEDIC, "medic");
            register(event, GORO_YELLOW, "goro_yellow");
            register(event, GORO2_YELLOW, "goro2_yellow");
            register(event, GORO3_YELLOW, "goro3_yellow");
            register(event, HANA, "hana");
            register(event, MAGNET, "magnet");
            register(event, SIMPLE_CIRCLE, "simple_circle");
            register(event, DOUBLE_CIRCLE, "double_circle");
            register(event, HOLE, "hole");
            register(event, COMMAND_MARK, "mark");
        }

        private static void register(RegisterParticleProvidersEvent event, DeferredHolder<ParticleType<?>, SimpleParticleType> type, String texture) {
            register(event, type, texture, 1);
        }

        private static void register(RegisterParticleProvidersEvent event, DeferredHolder<ParticleType<?>, SimpleParticleType> type, String texture, int maxFrames) {
            event.registerSpecial(type.get(), new SimpleParticle.Factory(ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/particle/" + texture + ".png"), maxFrames));
        }
    }
}
