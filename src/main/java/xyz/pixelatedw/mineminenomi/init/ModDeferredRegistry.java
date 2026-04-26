package xyz.pixelatedw.mineminenomi.init;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class ModDeferredRegistry {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems("mineminenomi");
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks("mineminenomi");
    public static final DeferredRegister<MenuType<?>> CONTAINER_TYPES = DeferredRegister.create(Registries.MENU, "mineminenomi");
    public static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(Registries.MOB_EFFECT, "mineminenomi");
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(Registries.ENTITY_TYPE, "mineminenomi");
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, "mineminenomi");
    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(Registries.SOUND_EVENT, "mineminenomi");
    public static final DeferredRegister<CreativeModeTab> CREATIVE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, "mineminenomi");
    public static final DeferredRegister<ArmorMaterial> ARMOR_MATERIALS = DeferredRegister.create(Registries.ARMOR_MATERIAL, "mineminenomi");
    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, "mineminenomi");
    public static final DeferredRegister<net.minecraft.commands.synchronization.ArgumentTypeInfo<?, ?>> COMMAND_ARGUMENT_TYPES = DeferredRegister.create(Registries.COMMAND_ARGUMENT_TYPE, "mineminenomi");

    public static void init(IEventBus eventBus) {
        ITEMS.register(eventBus);
        BLOCKS.register(eventBus);
        CONTAINER_TYPES.register(eventBus);
        EFFECTS.register(eventBus);
        ENTITY_TYPES.register(eventBus);
        BLOCK_ENTITIES.register(eventBus);
        SOUNDS.register(eventBus);
        CREATIVE_TABS.register(eventBus);
        ARMOR_MATERIALS.register(eventBus);
        ATTACHMENT_TYPES.register(eventBus);
        COMMAND_ARGUMENT_TYPES.register(eventBus);
    }
}
