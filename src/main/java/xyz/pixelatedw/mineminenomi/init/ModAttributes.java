package xyz.pixelatedw.mineminenomi.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;
import xyz.pixelatedw.mineminenomi.ModMain;

public class ModAttributes {
    public static final DeferredRegister<Attribute> ATTRIBUTES = DeferredRegister.create(Registries.ATTRIBUTE, ModMain.PROJECT_ID);

    public static final DeferredHolder<Attribute, Attribute> FALL_RESISTANCE = ATTRIBUTES.register("fall_resistance", () -> new RangedAttribute("attribute.name.mineminenomi.fall_resistance", 0.0, -256.0, 256.0).setSyncable(true));
    public static final DeferredHolder<Attribute, Attribute> JUMP_HEIGHT = ATTRIBUTES.register("jump_height", () -> new RangedAttribute("attribute.name.mineminenomi.jump_height", 1.0, -256.0, 256.0).setSyncable(true));
    public static final DeferredHolder<Attribute, Attribute> REGEN_RATE = ATTRIBUTES.register("regen_rate", () -> new RangedAttribute("attribute.name.mineminenomi.regen_rate", 1.0, 0.0, 32.0).setSyncable(true));
    public static final DeferredHolder<Attribute, Attribute> PUNCH_DAMAGE = ATTRIBUTES.register("punch_damage", () -> new RangedAttribute("attribute.name.mineminenomi.punch_damage", 0.0, -1024.0, 1024.0).setSyncable(true));
    public static final DeferredHolder<Attribute, Attribute> TIME_PROGRESSION = ATTRIBUTES.register("time_progression", () -> new RangedAttribute("attribute.name.mineminenomi.time_progression", 1.0, 0.0, 1024.0).setSyncable(true));
    public static final DeferredHolder<Attribute, Attribute> FLOATING_TIME = ATTRIBUTES.register("floating_time", () -> new RangedAttribute("attribute.name.mineminenomi.floating_time", 100.0, 0.0, 2048.0).setSyncable(true));
    public static final DeferredHolder<Attribute, Attribute> TOUGHNESS = ATTRIBUTES.register("toughness", () -> new RangedAttribute("attribute.name.mineminenomi.toughness", 0.0, 0.0, 20.0).setSyncable(true));
    public static final DeferredHolder<Attribute, Attribute> GCD = ATTRIBUTES.register("gcd", () -> new RangedAttribute("attribute.name.mineminenomi.gcd", 40.0, 0.0, 72000.0).setSyncable(true));
    public static final DeferredHolder<Attribute, Attribute> MINING_SPEED = ATTRIBUTES.register("mining_speed", () -> new RangedAttribute("attribute.name.mineminenomi.mining_speed", 1.0, -128.0, 128.0).setSyncable(true));
    public static final DeferredHolder<Attribute, Attribute> FRICTION = ATTRIBUTES.register("friction", () -> new RangedAttribute("attribute.name.mineminenomi.friction", 0.0, 0.0, 0.99).setSyncable(true));

    public static void register(IEventBus bus) {
        ATTRIBUTES.register(bus);
    }
}
