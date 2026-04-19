package xyz.pixelatedw.mineminenomi.api.entities.charactercreator;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import net.minecraft.Util;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.registries.IForgeRegistry;
import org.jetbrains.annotations.Nullable;
import xyz.pixelatedw.mineminenomi.api.WyRegistry;
import xyz.pixelatedw.mineminenomi.api.factions.IFactionRank;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class Faction implements ICharacterCreatorEntry {
   private ResourceLocation key;
   private String labelId;
   private Component label;
   private CharacterCreatorSelectionInfo bookInfo;
   private boolean inBook = false;
   private Predicate<LivingEntity> canReceiveBounty = (entity) -> false;
   private Predicate<LivingEntity> canReceiveLoyalty = (entity) -> false;
   private List<BiPredicate<LivingEntity, LivingEntity>> canHurtList = new ArrayList();
   private Class<? extends Enum<?>> ranks = null;
   private Color flagBackgroundColor;

   public Faction() {
      this.flagBackgroundColor = Color.WHITE;
   }

   protected String getOrCreateLabelId() {
      if (this.labelId == null) {
         ResourceLocation key = ((IForgeRegistry)WyRegistry.FACTIONS.get()).getKey(this);
         if (key != null) {
            this.labelId = Util.m_137492_(ModRegistry.I18nCategory.FACTION.getId(), key);
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

   public Faction setBookDetails(CharacterCreatorSelectionInfo info) {
      this.bookInfo = info;
      this.inBook = true;
      return this;
   }

   public boolean canReceiveBounty(LivingEntity player) {
      return this.canReceiveBounty.test(player);
   }

   public Faction setCanReceiveBountyCheck(Predicate<LivingEntity> check) {
      this.canReceiveBounty = check;
      return this;
   }

   public boolean canReceiveLoyalty(LivingEntity player) {
      return this.canReceiveLoyalty.test(player);
   }

   public Faction setCanReceiveLoyaltyCheck(Predicate<LivingEntity> check) {
      this.canReceiveLoyalty = check;
      return this;
   }

   public boolean canHurt(LivingEntity attacker, LivingEntity target) {
      for(BiPredicate<LivingEntity, LivingEntity> canHurt : this.canHurtList) {
         if (!canHurt.test(attacker, target)) {
            return false;
         }
      }

      return true;
   }

   public Faction addCanHurtCheck(BiPredicate<LivingEntity, LivingEntity> check) {
      this.canHurtList.add(check);
      return this;
   }

   public <E extends Enum<E> & IFactionRank> Faction setRanks(Class<E> ranks) {
      this.ranks = ranks;
      return this;
   }

   public <E extends Enum<E> & IFactionRank> E[] getRanks() {
      if (this.ranks == null) {
         return null;
      } else {
         E[] modes = (E[])(this.ranks.getEnumConstants());
         return modes;
      }
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

   public Faction setFlagBackgroundColor(int color) {
      this.flagBackgroundColor = new Color(color);
      return this;
   }

   public Color getFlagBackgroundColor() {
      return this.flagBackgroundColor;
   }

   public static @Nullable Faction get(ResourceLocation res) {
      return (Faction)((IForgeRegistry)WyRegistry.FACTIONS.get()).getValue(res);
   }

   public ResourceLocation getRegistryName() {
      if (this.key == null) {
         this.key = ((IForgeRegistry)WyRegistry.FACTIONS.get()).getKey(this);
      }

      return this.key;
   }

   public boolean equals(Object other) {
      if (other instanceof Faction otherFaction) {
         return this.getRegistryName() != null && otherFaction.getRegistryName() != null ? this.getRegistryName().equals(otherFaction.getRegistryName()) : false;
      } else {
         return false;
      }
   }
}
