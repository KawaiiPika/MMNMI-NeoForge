package xyz.pixelatedw.mineminenomi.abilities.kage;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityType;
import xyz.pixelatedw.mineminenomi.api.abilities.PassiveAbility;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class KageGiriAbility extends PassiveAbility {
   public static final RegistryObject<AbilityCore<KageGiriAbility>> INSTANCE = ModRegistry.registerAbility("kage_giri", "Kage Giri", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Allows the user to cut an enemy's shadow using a pair of Scissors", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, AbilityType.PASSIVE, KageGiriAbility::new)).addDescriptionLine(desc).build("mineminenomi");
   });
   private Map<UUID, Long> shadowsList = new HashMap();

   public KageGiriAbility(AbilityCore<KageGiriAbility> ability) {
      super(ability);
   }

   public boolean addIfValid(UUID id) {
      if (this.shadowsList.containsKey(id)) {
         if (this.canTakeShadow((Long)this.shadowsList.get(id))) {
            this.shadowsList.remove(id);
            return true;
         } else {
            return false;
         }
      } else {
         this.shadowsList.put(id, System.currentTimeMillis());
         return true;
      }
   }

   public boolean canTakeShadow(long t) {
      return System.currentTimeMillis() - t >= 600000L;
   }

   public void saveAdditional(CompoundTag nbt) {
      ListTag shadows = new ListTag();

      for(Map.Entry<UUID, Long> entry : this.shadowsList.entrySet()) {
         if (!this.canTakeShadow((Long)entry.getValue())) {
            CompoundTag entryNbt = new CompoundTag();
            entryNbt.m_128362_("id", (UUID)entry.getKey());
            entryNbt.m_128356_("time", (Long)entry.getValue());
            shadows.add(entryNbt);
         }
      }

      nbt.m_128365_("shadows", shadows);
   }

   public void loadAdditional(CompoundTag nbt) {
      ListTag shadows = nbt.m_128437_("shadows", 10);
      this.shadowsList.clear();

      for(int i = 0; i < shadows.size(); ++i) {
         CompoundTag entryNbt = shadows.m_128728_(i);
         UUID id = entryNbt.m_128342_("id");
         long time = entryNbt.m_128454_("time");
         if (!this.canTakeShadow(time)) {
            this.shadowsList.put(id, time);
         }
      }

   }
}
