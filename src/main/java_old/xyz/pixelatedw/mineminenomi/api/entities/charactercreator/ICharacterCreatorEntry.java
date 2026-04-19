package xyz.pixelatedw.mineminenomi.api.entities.charactercreator;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import xyz.pixelatedw.mineminenomi.init.ModResources;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18n;

public interface ICharacterCreatorEntry extends Comparable<ICharacterCreatorEntry> {
   ICharacterCreatorEntry RANDOM = new ICharacterCreatorEntry() {
      private static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath("mineminenomi", "random");
      private static final CharacterCreatorSelectionInfo SELECTION_INFO;

      public Component getLabel() {
         return ModI18n.GUI_RANDOM;
      }

      public CharacterCreatorSelectionInfo getSelectionInfo() {
         return SELECTION_INFO;
      }

      public ResourceLocation getRegistryName() {
         return ID;
      }

      public boolean isInBook() {
         return true;
      }

      public int getBookOrder() {
         return 0;
      }

      static {
         SELECTION_INFO = new CharacterCreatorSelectionInfo(ModResources.RANDOM, 0);
      }
   };

   Component getLabel();

   CharacterCreatorSelectionInfo getSelectionInfo();

   ResourceLocation getRegistryName();

   boolean isInBook();

   int getBookOrder();

   default int compareTo(ICharacterCreatorEntry other) {
      return this.getBookOrder() - other.getBookOrder();
   }
}
