package xyz.pixelatedw.mineminenomi.init;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.HashMap;

public class ModRegistry {
    private static HashMap<String, String> langMap = new HashMap<>();

    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems("mineminenomi");
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks("mineminenomi");
    public static final DeferredRegister<MenuType<?>> CONTAINER_TYPES = DeferredRegister.create(BuiltInRegistries.MENU, "mineminenomi");
    public static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(BuiltInRegistries.MOB_EFFECT, "mineminenomi");
    // public static final DeferredRegister<Enchantment> ENCHANTMENTS = DeferredRegister.create(Registries.ENCHANTMENT, "mineminenomi");
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, "mineminenomi");
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, "mineminenomi");
    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(BuiltInRegistries.SOUND_EVENT, "mineminenomi");
    public static final DeferredRegister<CreativeModeTab> CREATIVE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, "mineminenomi");
    public static final DeferredRegister<net.minecraft.world.item.ArmorMaterial> ARMOR_MATERIALS = DeferredRegister.create(Registries.ARMOR_MATERIAL, "mineminenomi");
    public static final net.neoforged.neoforge.registries.DeferredHolder<CreativeModeTab, CreativeModeTab> MINEMINENOMI_TAB = CREATIVE_TABS.register("mineminenomi_tab", () -> CreativeModeTab.builder()
            .title(net.minecraft.network.chat.Component.translatable("itemGroup.mineminenomi"))
            .icon(() -> new net.minecraft.world.item.ItemStack(ModFruits.GOMU_GOMU_NO_MI.get()))
            .displayItems((params, output) -> {
                ITEMS.getEntries().forEach(item -> output.accept(item.get()));
            })
            .build());

    public static final DeferredRegister<net.neoforged.neoforge.attachment.AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(net.neoforged.neoforge.registries.NeoForgeRegistries.ATTACHMENT_TYPES, "mineminenomi");
    public static final DeferredRegister<net.minecraft.core.particles.ParticleType<?>> PARTICLE_TYPES = DeferredRegister.create(BuiltInRegistries.PARTICLE_TYPE, "mineminenomi");

    // Other custom registries will be ported in later steps

    public static HashMap<String, String> getLangMap() {
        return langMap;
    }

    public static void init(IEventBus eventBus) {
        ITEMS.register(eventBus);
        BLOCKS.register(eventBus);
        CONTAINER_TYPES.register(eventBus);
        EFFECTS.register(eventBus);
        // ENCHANTMENTS.register(eventBus);
        ENTITY_TYPES.register(eventBus);
        BLOCK_ENTITIES.register(eventBus);
        SOUNDS.register(eventBus);
        CREATIVE_TABS.register(eventBus);
        ARMOR_MATERIALS.register(eventBus);
        ATTACHMENT_TYPES.register(eventBus);
        PARTICLE_TYPES.register(eventBus);
    }
}
