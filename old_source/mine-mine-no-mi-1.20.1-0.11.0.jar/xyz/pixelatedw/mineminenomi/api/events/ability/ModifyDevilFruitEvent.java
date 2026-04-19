package xyz.pixelatedw.mineminenomi.api.events.ability;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import java.util.List;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.registries.RegistryObject;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.items.AkumaNoMiItem;

public class ModifyDevilFruitEvent extends Event {
   private final AkumaNoMiItem fruit;
   private final ImmutableList<RegistryObject<? extends AbilityCore<?>>> originalAbilities;
   private List<RegistryObject<? extends AbilityCore<?>>> abilities;

   public ModifyDevilFruitEvent(AkumaNoMiItem fruit, RegistryObject<? extends AbilityCore<?>>... abilities) {
      this.fruit = fruit;
      this.originalAbilities = ImmutableList.copyOf(abilities);
      this.abilities = Lists.newArrayList(abilities);
   }

   public void addAbilityCore(RegistryObject<? extends AbilityCore<?>> core) {
      this.abilities.add(core);
   }

   public void removeAbilityCore(RegistryObject<? extends AbilityCore<?>> core) {
      this.abilities.remove(core);
   }

   public void removeAbilityCore(Class<? extends Ability> clz) {
      try {
         this.abilities.removeIf((abl) -> ((AbilityCore)abl.get()).createAbility().getClass().equals(clz));
      } catch (Exception var3) {
      }

   }

   public RegistryObject<? extends AbilityCore<?>>[] getAbilities() {
      return (RegistryObject[])this.abilities.toArray(new RegistryObject[0]);
   }

   public final ImmutableList<RegistryObject<? extends AbilityCore<?>>> getOriginalAbilities() {
      return this.originalAbilities;
   }

   public final AkumaNoMiItem getFruitItem() {
      return this.fruit;
   }
}
