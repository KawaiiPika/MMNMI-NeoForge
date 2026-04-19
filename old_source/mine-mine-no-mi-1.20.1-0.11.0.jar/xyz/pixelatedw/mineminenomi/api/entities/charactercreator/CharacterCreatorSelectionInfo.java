package xyz.pixelatedw.mineminenomi.api.entities.charactercreator;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import net.minecraft.resources.ResourceLocation;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;

public class CharacterCreatorSelectionInfo {
   private final ResourceLocation icon;
   private final int order;
   private List<Supplier<? extends AbilityCore<?>>> topAbilities = new ArrayList();
   private List<Supplier<? extends AbilityCore<?>>> bottomAbilities = new ArrayList();

   public CharacterCreatorSelectionInfo(ResourceLocation icon, int order) {
      this.icon = icon;
      this.order = order;
   }

   public void addTopAbilities(Supplier<? extends AbilityCore<?>>... cores) {
      this.topAbilities.addAll(Lists.newArrayList(cores));
   }

   public void addBottomAbilities(Supplier<? extends AbilityCore<?>>... cores) {
      this.bottomAbilities.addAll(Lists.newArrayList(cores));
   }

   public ResourceLocation getIcon() {
      return this.icon;
   }

   public int getOrder() {
      return this.order;
   }

   public List<Supplier<? extends AbilityCore<?>>> getTopAbilities() {
      return this.topAbilities;
   }

   public List<Supplier<? extends AbilityCore<?>>> getBottomAbilities() {
      return this.bottomAbilities;
   }
}
