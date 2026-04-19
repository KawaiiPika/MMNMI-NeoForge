package xyz.pixelatedw.mineminenomi.data.entity.devilfruit;

import com.google.common.base.Strings;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.Nullable;
import xyz.pixelatedw.mineminenomi.api.WyRegistry;
import xyz.pixelatedw.mineminenomi.api.morph.MorphInfo;
import xyz.pixelatedw.mineminenomi.config.ServerConfig;
import xyz.pixelatedw.mineminenomi.items.AkumaNoMiItem;

public class DevilFruitBase implements IDevilFruit {
   private int version = 2;
   private LivingEntity owner;
   private Optional<ResourceLocation> devilFruit = Optional.empty();
   private boolean hasAwakenedFruit;
   private Deque<ResourceLocation> morphs = new ArrayDeque();
   private boolean isMorphDirty;
   private Optional<MorphInfo> lastMorph = Optional.empty();
   private boolean hasYamiPower = false;
   private boolean hasYomiPower = false;
   private boolean hasStrongWaterWeakness = false;
   private boolean hasWeakWaterWeakness = false;
   private boolean hasKairosekiWeakness = false;

   public DevilFruitBase(LivingEntity owner) {
      this.owner = owner;
   }

   public int getVersion() {
      return this.version;
   }

   public Optional<ResourceLocation> getDevilFruit() {
      return this.devilFruit;
   }

   public Item getDevilFruitItem() {
      return !this.hasAnyDevilFruit() ? Items.f_41852_ : (Item)ForgeRegistries.ITEMS.getValue((ResourceLocation)this.getDevilFruit().get());
   }

   public void setDevilFruit(@Nullable ResourceLocation value) {
      this.devilFruit = Optional.ofNullable(value);
   }

   public void setDevilFruit(AkumaNoMiItem fruit) {
      this.devilFruit = Optional.ofNullable(fruit.getRegistryKey());
   }

   public boolean hasAnyDevilFruit() {
      return this.devilFruit.isPresent();
   }

   public boolean hasDevilFruit(RegistryObject<AkumaNoMiItem> fruit) {
      return !fruit.isPresent() ? false : this.hasDevilFruit((AkumaNoMiItem)fruit.get());
   }

   public boolean hasDevilFruit(AkumaNoMiItem fruit) {
      return (Boolean)this.getDevilFruit().map((rs) -> rs != null && rs.equals(fruit.getRegistryKey())).orElse(false);
   }

   public void removeDevilFruit() {
      this.setDevilFruit((ResourceLocation)null);
      this.morphs.clear();
      this.setYamiPower(false);
      this.setYomiPower(false);
   }

   public boolean isLogia() {
      return false;
   }

   public boolean hasYamiPower() {
      return this.hasYamiPower;
   }

   public void setYamiPower(boolean value) {
      this.hasYamiPower = value;
   }

   public boolean hasYomiPower() {
      return this.hasYomiPower;
   }

   public void setYomiPower(boolean value) {
      this.hasYomiPower = value;
   }

   public Optional<MorphInfo> getCurrentMorph() {
      if (!this.isMorphDirty && this.lastMorph.isPresent()) {
         return this.lastMorph;
      } else {
         ResourceLocation morphId = (ResourceLocation)this.morphs.peekLast();
         if (morphId != null) {
            MorphInfo morph = (MorphInfo)((IForgeRegistry)WyRegistry.MORPHS.get()).getValue(morphId);
            if (morph != null && !morph.isPartial()) {
               this.lastMorph = Optional.ofNullable(morph);
               this.isMorphDirty = false;
               return this.lastMorph;
            }
         }

         return Optional.empty();
      }
   }

   public Set<MorphInfo> getActiveMorphs() {
      return (Set)this.morphs.stream().map((id) -> (MorphInfo)((IForgeRegistry)WyRegistry.MORPHS.get()).getValue(id)).filter(Objects::nonNull).collect(Collectors.toSet());
   }

   public boolean hasMorphInQueue(MorphInfo morph) {
      return !this.isMorphDirty && this.lastMorph.isPresent() && ((MorphInfo)this.lastMorph.get()).equals(morph) ? true : this.morphs.contains(morph.getKey());
   }

   public void addMorph(MorphInfo morph) {
      if (!this.morphs.contains(morph.getKey())) {
         this.morphs.addLast(morph.getKey());
         this.isMorphDirty = true;
         if (this.owner != null) {
            morph.updateMorphSize(this.owner);
         }

      }
   }

   public void removeMorph(MorphInfo morph) {
      if (this.morphs.size() > 0) {
         this.morphs.removeLastOccurrence(morph.getKey());
         this.isMorphDirty = true;
      }
   }

   public void removeMorph() {
      if (this.morphs.size() > 0) {
         this.morphs.removeLast();
         this.isMorphDirty = true;
      }
   }

   public void clearMorphs() {
      this.morphs.clear();
      this.isMorphDirty = true;
   }

   public boolean hasAwakenedFruit() {
      if (!ServerConfig.hasAwakeningsEnabled()) {
         return false;
      } else if (this.owner == null) {
         return false;
      } else {
         return this.owner.m_20194_() != null && this.owner.m_20194_().m_6982_() ? false : this.hasAwakenedFruit;
      }
   }

   public void setAwakenedFruit(boolean flag) {
      this.hasAwakenedFruit = flag;
   }

   public boolean hasStrongWaterWeakness() {
      return this.hasStrongWaterWeakness;
   }

   public void setStrongWaterWeakness(boolean flag) {
      this.hasStrongWaterWeakness = flag;
   }

   public boolean hasWeakWaterWeakness() {
      return this.hasWeakWaterWeakness;
   }

   public void setWeakWaterWeakness(boolean flag) {
      this.hasWeakWaterWeakness = flag;
   }

   public boolean hasKairosekiWeakness() {
      return this.hasKairosekiWeakness;
   }

   public void setKairosekiWeakness(boolean flag) {
      this.hasKairosekiWeakness = flag;
   }

   public CompoundTag serializeNBT() {
      CompoundTag props = new CompoundTag();
      props.m_128405_("version", this.getVersion());
      String devilFruit = (String)this.getDevilFruit().map(ResourceLocation::toString).orElse("");
      props.m_128359_("devilFruit", devilFruit);
      props.m_128379_("hasAwakenedFruit", this.hasAwakenedFruit());
      props.m_128379_("hasYamiPower", this.hasYamiPower());
      props.m_128379_("hasYomiPower", this.hasYomiPower());
      int morphs = this.morphs.size();
      props.m_128405_("morphsSize", morphs);

      for(int i = 0; i < morphs; ++i) {
         ResourceLocation morph = (ResourceLocation)this.morphs.peek();
         props.m_128359_("morph" + i, morph.toString());
      }

      return props;
   }

   public void deserializeNBT(CompoundTag nbt) {
      String devilFruitProp = nbt.m_128461_("devilFruit");
      ResourceLocation devilFruit = null;
      if (nbt.m_128441_("devilFruit") && !Strings.isNullOrEmpty(devilFruitProp)) {
         devilFruit = ResourceLocation.parse(devilFruitProp);
      }

      this.setDevilFruit(devilFruit);
      if (ServerConfig.hasAwakeningsEnabled()) {
         this.setAwakenedFruit(nbt.m_128471_("hasAwakenedFruit"));
      } else {
         this.setAwakenedFruit(false);
      }

      this.setYamiPower(nbt.m_128471_("hasYamiPower"));
      this.setYomiPower(nbt.m_128471_("hasYomiPower"));
      int morphs = nbt.m_128451_("morphsSize");

      for(int i = morphs; i > 0; --i) {
         ResourceLocation id = ResourceLocation.parse(nbt.m_128461_("morph" + (i - 1)));
         this.morphs.add(id);
      }

   }
}
