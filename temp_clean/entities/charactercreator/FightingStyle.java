package xyz.pixelatedw.mineminenomi.api.entities.charactercreator;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class FightingStyle implements ICharacterCreatorEntry {
    private ResourceLocation key;
    private Component label;
    private CharacterCreatorSelectionInfo bookInfo;
    private boolean inBook = false;

    public Component getLabel() {
        if (this.label == null) {
            if (this.key == null && xyz.pixelatedw.mineminenomi.init.ModRegistries.FIGHTING_STYLES != null) {
                this.key = xyz.pixelatedw.mineminenomi.init.ModRegistries.FIGHTING_STYLES.getKey(this);
            }
            if (this.key != null) {
                this.label = Component.translatable("style.mineminenomi." + this.key.getPath());
            }
        }
        return this.label != null ? this.label : Component.literal("Unknown Style");
    }

    public FightingStyle setBookDetails(CharacterCreatorSelectionInfo info) {
        this.bookInfo = info;
        this.inBook = true;
        return this;
    }

    public CharacterCreatorSelectionInfo getSelectionInfo() {
        return this.bookInfo;
    }

    public boolean isInBook() {
        return this.inBook;
    }

    public int getBookOrder() {
        return this.bookInfo == null ? -1 : this.bookInfo.getOrder();
    }

    public void setRegistryName(ResourceLocation name) {
        this.key = name;
    }

    @Override
    public ResourceLocation getRegistryName() {
        return this.key;
    }
}
