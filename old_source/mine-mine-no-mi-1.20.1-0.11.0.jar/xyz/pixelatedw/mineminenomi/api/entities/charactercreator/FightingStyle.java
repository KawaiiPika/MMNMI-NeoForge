package xyz.pixelatedw.mineminenomi.api.entities.charactercreator;

import net.minecraft.Util;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistry;
import org.jetbrains.annotations.Nullable;
import xyz.pixelatedw.mineminenomi.api.WyRegistry;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class FightingStyle implements ICharacterCreatorEntry {
   private ResourceLocation key;
   private String labelId;
   private Component label;
   private CharacterCreatorSelectionInfo bookInfo;
   private boolean inBook = false;

   protected String getOrCreateLabelId() {
      if (this.labelId == null) {
         ResourceLocation key = ((IForgeRegistry)WyRegistry.FIGHTING_STYLES.get()).getKey(this);
         if (key != null) {
            this.labelId = Util.m_137492_(ModRegistry.I18nCategory.STYLE.getId(), key);
         }
      }

      return this.labelId;
   }

   public String getTitleId() {
      return this.labelId;
   }

   public Component getLabel() {
      if (this.label == null) {
         this.label = Component.m_237115_(this.getOrCreateLabelId());
      }

      return this.label;
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

   public static @Nullable FightingStyle get(ResourceLocation res) {
      return (FightingStyle)((IForgeRegistry)WyRegistry.FIGHTING_STYLES.get()).getValue(res);
   }

   public ResourceLocation getRegistryName() {
      if (this.key == null) {
         this.key = ((IForgeRegistry)WyRegistry.FIGHTING_STYLES.get()).getKey(this);
      }

      return this.key;
   }
}
