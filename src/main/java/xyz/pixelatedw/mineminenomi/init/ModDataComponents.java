package xyz.pixelatedw.mineminenomi.init;

import com.mojang.serialization.Codec;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModDataComponents {

    public static final DeferredRegister<DataComponentType<?>> DATA_COMPONENT_TYPES = DeferredRegister.create(Registries.DATA_COMPONENT_TYPE, "mineminenomi");

    public static final Supplier<DataComponentType<Integer>> TYPE = DATA_COMPONENT_TYPES.register("type", () -> 
            DataComponentType.<Integer>builder().persistent(Codec.INT).networkSynchronized(ByteBufCodecs.VAR_INT).build());

    public static final Supplier<DataComponentType<Integer>> BASE_COLOR = DATA_COMPONENT_TYPES.register("base_color", () -> 
            DataComponentType.<Integer>builder().persistent(Codec.INT).networkSynchronized(ByteBufCodecs.VAR_INT).build());

    public static final Supplier<DataComponentType<Integer>> STEM_COLOR = DATA_COMPONENT_TYPES.register("stem_color", () -> 
            DataComponentType.<Integer>builder().persistent(Codec.INT).networkSynchronized(ByteBufCodecs.VAR_INT).build());

    public static final Supplier<DataComponentType<Long>> BELLY = DATA_COMPONENT_TYPES.register("belly", () -> 
            DataComponentType.<Long>builder().persistent(Codec.LONG).networkSynchronized(ByteBufCodecs.VAR_LONG).build());

    public static final Supplier<DataComponentType<Long>> EXTOL = DATA_COMPONENT_TYPES.register("extol", () -> 
            DataComponentType.<Long>builder().persistent(Codec.LONG).networkSynchronized(ByteBufCodecs.VAR_LONG).build());

    public static final Supplier<DataComponentType<Float>> HEALTH = DATA_COMPONENT_TYPES.register("health", () -> 
            DataComponentType.<Float>builder().persistent(Codec.FLOAT).networkSynchronized(ByteBufCodecs.FLOAT).build());

    public static final Supplier<DataComponentType<Float>> AMOUNT = DATA_COMPONENT_TYPES.register("amount", () -> 
            DataComponentType.<Float>builder().persistent(Codec.FLOAT).networkSynchronized(ByteBufCodecs.FLOAT).build());

    public static final Supplier<DataComponentType<Integer>> TEARS = DATA_COMPONENT_TYPES.register("tears", () -> 
            DataComponentType.<Integer>builder().persistent(Codec.INT).networkSynchronized(ByteBufCodecs.VAR_INT).build());

    public static final Supplier<DataComponentType<Integer>> CANVAS_SIZE = DATA_COMPONENT_TYPES.register("canvas_size", () -> 
            DataComponentType.<Integer>builder().persistent(Codec.INT).networkSynchronized(ByteBufCodecs.VAR_INT).build());

    public static final Supplier<DataComponentType<String>> OWNER_UUID = DATA_COMPONENT_TYPES.register("owner_uuid", () -> 
            DataComponentType.<String>builder().persistent(Codec.STRING).networkSynchronized(ByteBufCodecs.STRING_UTF8).build());

    public static final Supplier<DataComponentType<String>> OWNER_USERNAME = DATA_COMPONENT_TYPES.register("owner_username", () -> 
            DataComponentType.<String>builder().persistent(Codec.STRING).networkSynchronized(ByteBufCodecs.STRING_UTF8).build());

    public static final Supplier<DataComponentType<Boolean>> IN_USE = DATA_COMPONENT_TYPES.register("in_use", () -> 
            DataComponentType.<Boolean>builder().persistent(Codec.BOOL).networkSynchronized(ByteBufCodecs.BOOL).build());

    public static final Supplier<DataComponentType<String>> LEADER = DATA_COMPONENT_TYPES.register("leader", () -> 
            DataComponentType.<String>builder().persistent(Codec.STRING).networkSynchronized(ByteBufCodecs.STRING_UTF8).build());

    public static final Supplier<DataComponentType<Integer>> COUNTDOWN = DATA_COMPONENT_TYPES.register("countdown", () -> 
            DataComponentType.<Integer>builder().persistent(Codec.INT).networkSynchronized(ByteBufCodecs.VAR_INT).build());

    public static final Supplier<DataComponentType<Integer>> GUNPOWDER = DATA_COMPONENT_TYPES.register("gunpowder", () ->
            DataComponentType.<Integer>builder().persistent(Codec.INT).networkSynchronized(ByteBufCodecs.VAR_INT).build());

    public static void init(IEventBus eventBus) {
        DATA_COMPONENT_TYPES.register(eventBus);
    }
}
