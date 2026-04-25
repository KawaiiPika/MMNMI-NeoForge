package xyz.pixelatedw.mineminenomi.api.entities.charactercreator;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public interface ICharacterCreatorEntry extends Comparable<ICharacterCreatorEntry> {
    ICharacterCreatorEntry RANDOM = new ICharacterCreatorEntry() {
        private static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath("mineminenomi", "random");
        private static final CharacterCreatorSelectionInfo SELECTION_INFO = new CharacterCreatorSelectionInfo(xyz.pixelatedw.mineminenomi.init.ModResources.RANDOM, 0);

        @Override public Component getLabel() { return Component.translatable("gui.mineminenomi.random"); }
        @Override public CharacterCreatorSelectionInfo getSelectionInfo() { return SELECTION_INFO; }
        @Override public ResourceLocation getRegistryName() { return ID; }
        @Override public boolean isInBook() { return true; }
        @Override public int getBookOrder() { return 0; }
    };

    Component getLabel();

    CharacterCreatorSelectionInfo getSelectionInfo();

    ResourceLocation getRegistryName();

    boolean isInBook();

    int getBookOrder();

    @Override
    default int compareTo(ICharacterCreatorEntry other) {
        return Integer.compare(this.getBookOrder(), other.getBookOrder());
    }
}
