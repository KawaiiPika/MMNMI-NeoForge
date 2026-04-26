package xyz.pixelatedw.mineminenomi.init;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.HashMap;

public class ModRegistry {
    private static HashMap<String, String> langMap = new HashMap<>();

    public static final DeferredRegister.Items ITEMS = ModDeferredRegistry.ITEMS;
    public static final DeferredRegister.Blocks BLOCKS = ModDeferredRegistry.BLOCKS;
    public static final DeferredRegister<MenuType<?>> CONTAINER_TYPES = ModDeferredRegistry.CONTAINER_TYPES;
    public static final DeferredRegister<MobEffect> EFFECTS = ModDeferredRegistry.EFFECTS;
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = ModDeferredRegistry.ENTITY_TYPES;
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = ModDeferredRegistry.BLOCK_ENTITIES;
    public static final DeferredRegister<SoundEvent> SOUNDS = ModDeferredRegistry.SOUNDS;
    public static final DeferredRegister<CreativeModeTab> CREATIVE_TABS = ModDeferredRegistry.CREATIVE_TABS;
    public static final DeferredRegister<net.minecraft.world.item.ArmorMaterial> ARMOR_MATERIALS = ModDeferredRegistry.ARMOR_MATERIALS;
    public static final DeferredRegister<net.neoforged.neoforge.attachment.AttachmentType<?>> ATTACHMENT_TYPES = ModDeferredRegistry.ATTACHMENT_TYPES;
    public static final DeferredRegister<net.minecraft.commands.synchronization.ArgumentTypeInfo<?, ?>> COMMAND_ARGUMENT_TYPES = ModDeferredRegistry.COMMAND_ARGUMENT_TYPES;




    // Other custom registries will be ported in later steps


    public static HashMap<String, String> getLangMap() {
        return langMap;
    }

    public static void init(IEventBus eventBus) {
        ModDeferredRegistry.init(eventBus);
    }

}
