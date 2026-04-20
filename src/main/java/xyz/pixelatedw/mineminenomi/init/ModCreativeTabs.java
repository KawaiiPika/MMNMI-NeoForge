package xyz.pixelatedw.mineminenomi.init;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import java.util.function.Supplier;

import xyz.pixelatedw.mineminenomi.items.AkumaNoMiItem;

public class ModCreativeTabs {

    public static final Supplier<CreativeModeTab> DEVIL_FRUITS_TAB = ModRegistry.CREATIVE_TABS.register("devil_fruits", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.mineminenomi.devil_fruits"))
            .icon(() -> new ItemStack(ModFruits.GOMU_GOMU_NO_MI.get())) 
            .displayItems((params, output) -> {
                ModRegistry.ITEMS.getEntries().forEach(item -> {
                    if (item.get() instanceof AkumaNoMiItem) {
                        output.accept(item.get());
                    }
                });
            }).build());

    public static final Supplier<CreativeModeTab> WEAPONS_TAB = ModRegistry.CREATIVE_TABS.register("weapons", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.mineminenomi.weapons"))
            .icon(() -> new ItemStack(ModItems.CANNON.get())) 
            .displayItems((params, output) -> {
                output.accept(ModItems.CANNON.get());
                output.accept(ModItems.STRIKER.get());
                output.accept(ModItems.UNICYCLE.get());
                output.accept(ModItems.NET.get());
            }).build());

    public static final Supplier<CreativeModeTab> EQUIPMENT_TAB = ModRegistry.CREATIVE_TABS.register("equipment", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.mineminenomi.equipment"))
            .icon(() -> new ItemStack(ModItems.KAIROSEKI.get())) 
            .displayItems((params, output) -> {
                output.accept(ModItems.KAIROSEKI.get());
                output.accept(ModItems.DENSE_KAIROSEKI.get());
                output.accept(ModItems.NORMAL_HANDCUFFS.get());
                output.accept(ModItems.KAIROSEKI_HANDCUFFS.get());
                output.accept(ModItems.EXPLOSIVE_HANDCUFFS.get());
            }).build());

    public static final Supplier<CreativeModeTab> MISC_TAB = ModRegistry.CREATIVE_TABS.register("misc", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.mineminenomi.misc"))
            .icon(() -> new ItemStack(ModItems.BELLY_POUCH.get())) 
            .displayItems((params, output) -> {
                ModRegistry.ITEMS.getEntries().forEach(item -> {
                    if (!(item.get() instanceof AkumaNoMiItem)) {
                        output.accept(item.get());
                    }
                });
            }).build());

    public static void init() {
    }
}
