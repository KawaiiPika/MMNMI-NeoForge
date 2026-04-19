package xyz.pixelatedw.mineminenomi.api.abilities;

import java.util.function.Function;
import java.util.function.Predicate;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.IExtensibleEnum;
import org.jetbrains.annotations.Nullable;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.init.ModValues;

public enum AbilityCategory implements IExtensibleEnum {
   DEVIL_FRUITS(AbilityHelper.DF_CATEGORY_ICON),
   RACIAL(AbilityHelper.RACE_CATEGORY_ICON),
   STYLE((Function)null),
   HAKI(AbilityHelper.HAKI_CATEGORY_ICON),
   FACTION((Function)null),
   EQUIPMENT((Function)null),
   ALL((Function)null);

   private Function<Player, ResourceLocation> iconFunction = (entity) -> ModValues.NIL_LOCATION;

   private AbilityCategory(Function<Player, ResourceLocation> function) {
      this.iconFunction = function;
   }

   public @Nullable ResourceLocation getIcon(Player player) {
      return this.iconFunction != null && player != null ? (ResourceLocation)this.iconFunction.apply(player) : null;
   }

   public Predicate<IAbility> isAbilityPartofCategory() {
      return this == ALL ? (abl) -> true : (abl) -> abl.getCore().getCategory().equals(this);
   }

   public Predicate<AbilityCore<?>> isCorePartofCategory() {
      return this == ALL ? (core) -> true : (core) -> core.getCategory().equals(this);
   }

   public static AbilityCategory create(String name, Function<Player, ResourceLocation> function) {
      throw new IllegalStateException("Enum not extended");
   }

   // $FF: synthetic method
   private static AbilityCategory[] $values() {
      return new AbilityCategory[]{DEVIL_FRUITS, RACIAL, STYLE, HAKI, FACTION, EQUIPMENT, ALL};
   }
}
