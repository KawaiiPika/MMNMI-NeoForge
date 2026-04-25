package xyz.pixelatedw.mineminenomi.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.data.tags.TagsProvider.TagLookup;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import xyz.pixelatedw.mineminenomi.ModMain;
import xyz.pixelatedw.mineminenomi.init.ModArmors;
import xyz.pixelatedw.mineminenomi.init.ModItems;

import java.util.concurrent.CompletableFuture;

public class ModItemTagsProvider extends ItemTagsProvider {

    public static final TagKey<Item> CURIOS_HEAD = ItemTags.create(ResourceLocation.fromNamespaceAndPath("curios", "head"));
    public static final TagKey<Item> CURIOS_FACE = ItemTags.create(ResourceLocation.fromNamespaceAndPath("curios", "face"));
    public static final TagKey<Item> CURIOS_BACK = ItemTags.create(ResourceLocation.fromNamespaceAndPath("curios", "back"));
    public static final TagKey<Item> CURIOS_CHARM = ItemTags.create(ResourceLocation.fromNamespaceAndPath("curios", "charm"));

    public ModItemTagsProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> future, CompletableFuture<TagLookup<Block>> provider, ExistingFileHelper existingFileHelper) {
        super(packOutput, future, provider, ModMain.PROJECT_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        this.tag(CURIOS_HEAD)
            .add(ModArmors.CELESTIAL_DRAGON_BUBBLE.get())
            .add(ModArmors.BICORNE.get())
            .add(ModArmors.CHOPPER_HAT.get())
            .add(ModArmors.KILLER_MASK.get())
            .add(ModArmors.LAW_HAT.get())
            .add(ModArmors.TRICORNE.get())
            .add(ModArmors.SABO_HAT.get())
            .add(ModArmors.MIHAWK_HAT.get())
            .add(ModArmors.FLEET_ADMIRAL_HAT.get())
            .add(ModArmors.PLUME_HAT.get())
            .add(ModArmors.WIDE_BRIM_HAT.get())
            .add(ModArmors.ACE_HAT.get())
            .add(ModArmors.STRAW_HAT.get());

        this.tag(CURIOS_FACE)
            .add(ModArmors.FRANKY_GLASSES.get())
            .add(ModArmors.CABAJI_SCARF.get())
            .add(ModArmors.BIG_RED_NOSE.get())
            .add(ModArmors.KURO_GLASSES.get())
            .add(ModArmors.MR3_GLASSES.get())
            .add(ModArmors.MR5_GLASSES.get())
            .add(ModArmors.SNIPER_GOGGLES.get())
            .add(ModArmors.MH5_GAS_MASK.get())
            .add(ModArmors.KIZARU_GLASSES.get())
            .add(ModArmors.DOFFY_GLASSES.get())
            .add(ModArmors.HEART_GLASSES.get());

        this.tag(CURIOS_CHARM)
            .add(ModItems.CIGAR.get());

        this.tag(CURIOS_BACK)
            .add(ModArmors.FLUFFY_CAPE.get())
            .add(ModArmors.MARINE_CAPTAIN_CAPE.get())
            .add(ModArmors.PIRATE_CAPTAIN_CAPE.get())
            .add(ModArmors.COLA_BACKPACK.get())
            .add(ModArmors.MEDIC_BAG.get())
            .add(ModArmors.TOMOE_DRUMS.get());
    }
}
