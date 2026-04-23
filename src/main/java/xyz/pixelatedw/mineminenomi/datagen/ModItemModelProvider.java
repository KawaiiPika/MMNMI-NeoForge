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

            if (item instanceof net.minecraft.world.item.BlockItem) {
                if (name.equals("tangerine")) {
                    withExistingParent(name, "item/generated").texture("layer0", ResourceLocation.fromNamespaceAndPath(ModMain.PROJECT_ID, "items/" + name));
                } else {
                    try {
                        withExistingParent(name, ModMain.PROJECT_ID + ":block/" + name);
                    } catch(Exception e) {
                        System.err.println("Block model missing for " + name);
                    }
                }
            } else if (item instanceof ModSwordItem || item instanceof ModSpearItem || item instanceof ModGunItem) {
                handheldItem(name);
            } else {
                basicItem(name);
            }
        }
    }

    private void basicItem(String name) {
        String textureName = name;
        if (name.equals("belly_pouch")) textureName = "belly_pouch_0";
        if (name.equals("extol_pouch")) textureName = "extol_pouch_0";
        if (name.equals("gold_den_den_mushi")) textureName = "den_den_mushi";

        java.io.File textureFile = new java.io.File("src/main/resources/assets/mineminenomi/textures/items/" + textureName + ".png");
        if (!textureFile.exists()) {
            return; // silently skip if missing
        }

        withExistingParent(name, "item/generated")
                .texture("layer0", ResourceLocation.fromNamespaceAndPath(ModMain.PROJECT_ID, "items/" + textureName));
    }


    private void handheldItem(String name) {
        String textureName = name;
        java.io.File textureFile = new java.io.File("src/main/resources/assets/mineminenomi/textures/items/" + textureName + ".png");
        if (!textureFile.exists()) {
            return; // silently skip if missing
        }
        withExistingParent(name, "item/handheld")
                .texture("layer0", ResourceLocation.fromNamespaceAndPath(ModMain.PROJECT_ID, "items/" + textureName));
    }


}
