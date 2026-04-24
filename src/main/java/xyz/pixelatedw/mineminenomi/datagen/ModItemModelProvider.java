package xyz.pixelatedw.mineminenomi.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredHolder;
import xyz.pixelatedw.mineminenomi.ModMain;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.items.armors.ModArmorItem;
import xyz.pixelatedw.mineminenomi.items.weapons.ModSwordItem;
import xyz.pixelatedw.mineminenomi.items.weapons.ModSpearItem;
import xyz.pixelatedw.mineminenomi.items.weapons.ModGunItem;

public class ModItemModelProvider extends ItemModelProvider {

    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, ModMain.PROJECT_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        for (DeferredHolder<Item, ? extends Item> itemEntry : ModRegistry.ITEMS.getEntries()) {
            Item item = itemEntry.get();
            String name = itemEntry.getId().getPath();

            // Check if the texture file actually exists in the assets directory
            if (!existingFileHelper.exists(ResourceLocation.fromNamespaceAndPath(ModMain.PROJECT_ID, "item/" + name), net.minecraft.server.packs.PackType.CLIENT_RESOURCES, ".png", "textures")) {
                continue;
            }

            if (item instanceof ModSwordItem || item instanceof ModSpearItem || item instanceof ModGunItem) {
                handheldItem(name);
            } else {
                basicItem(name);
            }
        }
    }

    private String resolveTextureName(String name) {
        switch (name) {
            case "belly_pouch": return "belly_pouch_0";
            case "extol_pouch": return "extol_pouch_0";
            case "gold_den_den_mushi": return "den_den_mushi";
            case "golden_devil_fruit_box": return "golden_box";
            case "wooden_devil_fruit_box": return "wooden_box";
            case "iron_devil_fruit_box": return "iron_box";
            case "cigar": return "jolly_rogers/details/single_cigar";
            case "net": return "rope_net";
            case "normal_handcuffs": return "handcuffs";
            case "hammer_5t": return "5t_hammer";
            case "hammer_5t_haki": return "5t_hammer_haki";
            case "axe_hand": return "axe";
            case "bazooka": return "items/models/bazooka";
            case "marine_shirt": return "marine_chest";
            case "pirate_shirt": return "pirate_chest";
            case "kizarus_glasses": return "kizaru_glasses";
            case "mihawks_hat": return "mihawks_hat_0";
            case "plume_hat": return "plume_hat_0";
            case "bicorne": return "bicorne_0";
            case "ope": return "blocks/ope";
            case "suna_sand": return "blocks/suna_sand";
            case "wax_block": return "blocks/wax_block";
            case "candy_block": return "blocks/candy_block";
            case "poison": return "blocks/poison";
            case "demon_poison": return "blocks/demon_poison";
            case "kairoseki_block": return "blocks/kairoseki_block";
            case "kairoseki_ore": return "blocks/kairoseki_ore";
            case "deepslate_kairoseki_ore": return "blocks/deepslate_kairoseki_ore";
            case "poneglyph": return "blocks/poneglyph1";
        }

        if (name.contains("_no_mi_") && !name.contains("_no_mi_model_")) {
            return name.replace("_no_mi_", "_no_mi_model_");
        }

        return name;
    }

    private void basicItem(String name) {
        String textureName = resolveTextureName(name);

        ResourceLocation loc = ResourceLocation.fromNamespaceAndPath(ModMain.PROJECT_ID, "items/" + textureName);
        if (textureName.contains("/")) {
            loc = ResourceLocation.fromNamespaceAndPath(ModMain.PROJECT_ID, textureName);
        }

        withExistingParent(name, "item/generated")
                .texture("layer0", loc);
    }

    private void handheldItem(String name) {
        String textureName = resolveTextureName(name);

        ResourceLocation loc = ResourceLocation.fromNamespaceAndPath(ModMain.PROJECT_ID, "items/" + textureName);
        if (textureName.contains("/")) {
            loc = ResourceLocation.fromNamespaceAndPath(ModMain.PROJECT_ID, textureName);
        }

        withExistingParent(name, "item/handheld")
                .texture("layer0", loc);
    }
}
