import re

with open('src/main/java/xyz/pixelatedw/mineminenomi/datagen/ModItemModelProvider.java', 'r') as f:
    content = f.read()

new_basic_item = """    private void basicItem(String name) {
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
"""

content = re.sub(r'    private void basicItem\(String name\) \{.*?\}', new_basic_item, content, flags=re.DOTALL)

new_handheld_item = """    private void handheldItem(String name) {
        String textureName = name;
        java.io.File textureFile = new java.io.File("src/main/resources/assets/mineminenomi/textures/items/" + textureName + ".png");
        if (!textureFile.exists()) {
            return; // silently skip if missing
        }
        withExistingParent(name, "item/handheld")
                .texture("layer0", ResourceLocation.fromNamespaceAndPath(ModMain.PROJECT_ID, "items/" + textureName));
    }
"""

content = re.sub(r'    private void handheldItem\(String name\) \{.*?\}', new_handheld_item, content, flags=re.DOTALL)

with open('src/main/java/xyz/pixelatedw/mineminenomi/datagen/ModItemModelProvider.java', 'w') as f:
    f.write(content)
