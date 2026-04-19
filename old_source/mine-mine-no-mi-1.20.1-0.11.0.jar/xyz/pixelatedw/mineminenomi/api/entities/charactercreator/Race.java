package xyz.pixelatedw.mineminenomi.api.entities.charactercreator;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.Util;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.Nullable;
import xyz.pixelatedw.mineminenomi.api.WyRegistry;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class Race implements ICharacterCreatorEntry {
   private ResourceLocation key;
   private String labelId;
   private Component label;
   private CharacterCreatorSelectionInfo bookInfo;
   private boolean inBook = false;
   private boolean hasRenderFeatures = false;
   private boolean isSubRace;
   private List<RegistryObject<Race>> subRaces = new ArrayList();

   public static Race subRace() {
      Race race = new Race();
      race.isSubRace = true;
      return race;
   }

   public Race setRenderFeatures() {
      this.hasRenderFeatures = true;
      return this;
   }

   public Race setBookDetails(CharacterCreatorSelectionInfo info) {
      this.bookInfo = info;
      this.inBook = true;
      return this;
   }

   public boolean hasRenderFeatures() {
      return this.hasRenderFeatures;
   }

   public boolean isMainRace() {
      return !this.isSubRace;
   }

   public boolean isSubRace() {
      return this.isSubRace;
   }

   public boolean hasSubRaces() {
      return this.subRaces.size() > 0;
   }

   public List<RegistryObject<Race>> getSubRaces() {
      return this.subRaces;
   }

   public Race setSubRaces(List<RegistryObject<Race>> subRaces) {
      this.subRaces = subRaces;
      return this;
   }

   protected String getOrCreateLabelId() {
      if (this.labelId == null) {
         ResourceLocation key = ((IForgeRegistry)WyRegistry.RACES.get()).getKey(this);
         if (key != null) {
            this.labelId = Util.m_137492_(ModRegistry.I18nCategory.RACE.getId(), key);
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

   public CharacterCreatorSelectionInfo getSelectionInfo() {
      return this.bookInfo;
   }

   public boolean isInBook() {
      return this.inBook;
   }

   public int getBookOrder() {
      return this.bookInfo == null ? -1 : this.bookInfo.getOrder();
   }

   public static @Nullable Race get(ResourceLocation res) {
      return (Race)((IForgeRegistry)WyRegistry.RACES.get()).getValue(res);
   }

   public ResourceLocation getRegistryName() {
      if (this.key == null) {
         this.key = ((IForgeRegistry)WyRegistry.RACES.get()).getKey(this);
      }

      return this.key;
   }
}
