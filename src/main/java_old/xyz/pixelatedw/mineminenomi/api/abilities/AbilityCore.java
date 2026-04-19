package xyz.pixelatedw.mineminenomi.api.abilities;

import com.google.common.base.Strings;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import net.minecraft.Util;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraftforge.registries.IForgeRegistry;
import org.jetbrains.annotations.Nullable;
import xyz.pixelatedw.mineminenomi.api.WyRegistry;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.AbilityNode;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceElement;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.entities.ai.AbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityCapability;
import xyz.pixelatedw.mineminenomi.data.entity.ability.IAbilityData;

public class AbilityCore<A extends IAbility> implements Comparable<AbilityCore<?>> {
   public static final ResourceLocation MISSING_TEXTURE = ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/abilities/missing.png");
   private ResourceLocation key;
   private final String name;
   private final String id;
   private final AbilityCategory category;
   private final AbilityType type;
   private final IFactory<A> factory;
   private ResourceLocation icon;
   private Set<AbilityDescriptionLine> description;
   private SourceHakiNature sourceHakiNature;
   private ArrayList<SourceType> sourceTypes;
   private SourceElement sourceElement;
   private ICanUnlock unlockCheck;
   private List<INodeFactory> nodeFactories = new ArrayList();
   private boolean isHidden = false;
   private @Nullable String i18nId;
   private ResourceLocation phantomKey;
   private Component localizedDisplayName;

   protected AbilityCore(String name, String id, AbilityCategory category, AbilityType type, IFactory<A> factory) {
      this.name = name;
      this.id = id;
      this.category = category;
      this.type = type;
      this.factory = factory;
   }

   public String getUnlocalizedName() {
      return this.name;
   }

   public String getId() {
      return this.id;
   }

   public Component getLocalizedName() {
      String id = this.getLocalizationId();
      return Strings.isNullOrEmpty(id) ? Component.m_237113_(this.getUnlocalizedName()) : Component.m_237115_(id);
   }

   public String getLocalizationId() {
      if (this.i18nId == null) {
         ResourceLocation key = ((IForgeRegistry)WyRegistry.ABILITIES.get()).getKey(this);
         if (key == null && this.phantomKey != null) {
            key = this.phantomKey;
         }

         if (key != null) {
            this.i18nId = Util.m_137492_("ability", key);
         }
      }

      return this.i18nId;
   }

   public AbilityCategory getCategory() {
      return this.category;
   }

   public AbilityType getType() {
      return this.type;
   }

   public IFactory<? extends A> getFactory() {
      return this.factory;
   }

   private void setUnlockCheck(ICanUnlock check) {
      this.unlockCheck = check;
   }

   public boolean canUnlock(LivingEntity entity) {
      return this.unlockCheck == null ? false : this.unlockCheck.canUnlock(entity);
   }

   public boolean hasUnlockCheck() {
      return this.unlockCheck != null;
   }

   private void setNodeFactories(INodeFactory... nodeFactories) {
      this.nodeFactories = List.of(nodeFactories);
   }

   public int getNodeCount() {
      return this.nodeFactories.size();
   }

   public @Nullable AbilityNode getNode(LivingEntity entity) {
      return this.getNode(entity, 0);
   }

   public @Nullable AbilityNode getNode(LivingEntity entity, int index) {
      if (this.nodeFactories.size() <= index) {
         return null;
      } else {
         IAbilityData abilityProps = (IAbilityData)AbilityCapability.getLazy(entity).orElse((Object)null);
         if (abilityProps == null) {
            return null;
         } else {
            AbilityNode node = abilityProps.getNode(this, index);
            if (node == null) {
               node = ((INodeFactory)this.nodeFactories.get(index)).create(entity);
               abilityProps.addNode(this, index, node);
            }

            return node;
         }
      }
   }

   private void setSourceHakiNature(SourceHakiNature sourceHakiNature) {
      this.sourceHakiNature = sourceHakiNature;
   }

   public SourceHakiNature getSourceHakiNature() {
      return this.sourceHakiNature;
   }

   private void setSourceTypes(SourceType... sourceTypes) {
      this.sourceTypes = new ArrayList(Arrays.asList(sourceTypes));
   }

   public ArrayList<SourceType> getSourceTypes() {
      return new ArrayList(this.sourceTypes);
   }

   public SourceType[] getSourceTypesArray() {
      return (SourceType[])this.sourceTypes.toArray(new SourceType[0]);
   }

   public boolean hasType(SourceType type) {
      boolean flag = false;

      for(SourceType t : this.sourceTypes) {
         if (t.equals(type)) {
            flag = true;
            break;
         }
      }

      return flag;
   }

   private void setSourceElement(SourceElement sourceElement) {
      this.sourceElement = sourceElement;
   }

   public SourceElement getSourceElement() {
      return this.sourceElement;
   }

   private void setDescription(Set<AbilityDescriptionLine> description) {
      this.description = description;
   }

   public @Nullable Set<AbilityDescriptionLine> getDescription() {
      return this.description;
   }

   private void setIcon(ResourceLocation res) {
      this.icon = res;
   }

   public ResourceLocation getIcon() {
      return this.icon;
   }

   private void setHidden() {
      this.isHidden = true;
   }

   public boolean isHidden() {
      return this.isHidden;
   }

   public boolean isVisible() {
      return !this.isHidden();
   }

   private void setPhantomKey(ResourceLocation key) {
      this.phantomKey = key;
   }

   public A createAbility() {
      return this.factory.create(this);
   }

   public static <A extends IAbility> @Nullable AbilityCore<?> get(ResourceLocation res) {
      AbilityCore<?> core = (AbilityCore)((IForgeRegistry)WyRegistry.ABILITIES.get()).getValue(res);
      return core;
   }

   public @Nullable ResourceLocation getRegistryKey() {
      if (this.key == null) {
         this.key = ((IForgeRegistry)WyRegistry.ABILITIES.get()).getKey(this);
      }

      return this.key;
   }

   public String toString() {
      return this.getUnlocalizedName();
   }

   public boolean equals(Object other) {
      if (other instanceof AbilityCore otherCore) {
         if (this.getRegistryKey() != null && otherCore.getRegistryKey() != null) {
            return this.getRegistryKey().equals(otherCore.getRegistryKey());
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   public int compareTo(AbilityCore<?> other) {
      if (other == null) {
         return 1;
      } else {
         String id = this.getLocalizedName().getString();
         String oid = other.getLocalizedName().getString();
         return id.compareToIgnoreCase(oid);
      }
   }

   public static class Builder<A extends IAbility> {
      private final String name;
      private final String id;
      private final AbilityCategory category;
      private final AbilityType type;
      private final IFactory<A> factory;
      private Set<AbilityDescriptionLine> descriptionLines;
      private SourceHakiNature sourceHakiNature;
      private SourceType[] sourceTypes;
      private SourceElement sourceElement;
      private ICanUnlock unlockCheck;
      private INodeFactory[] nodeFactories;
      private boolean isHidden;
      private Set<Consumer<Builder<A>>> consumerExtensions;
      private ResourceLocation icon;
      private ResourceLocation phantomKey;

      public Builder(String id, String name, AbilityCategory category, IFactory<A> factory) {
         this(id, name, category, AbilityType.ACTION, factory);
      }

      public Builder(String id, String name, AbilityCategory category, AbilityType type, IFactory<A> factory) {
         this.descriptionLines = new LinkedHashSet();
         this.sourceHakiNature = SourceHakiNature.UNKNOWN;
         this.sourceTypes = new SourceType[]{SourceType.UNKNOWN};
         this.sourceElement = SourceElement.NONE;
         this.nodeFactories = new INodeFactory[0];
         this.isHidden = false;
         this.consumerExtensions = new LinkedHashSet();
         this.id = id;
         this.name = name;
         this.category = category;
         this.type = type;
         this.factory = factory;
      }

      public Builder<A> setUnlockCheck(ICanUnlock unlockCheck) {
         this.unlockCheck = unlockCheck;
         return this;
      }

      public Builder<A> setNodeFactories(INodeFactory... nodeFactories) {
         this.nodeFactories = nodeFactories;
         return this;
      }

      public Builder<A> setSourceHakiNature(SourceHakiNature hakiNature) {
         this.sourceHakiNature = hakiNature;
         return this;
      }

      public Builder<A> setSourceType(SourceType... sourceTypes) {
         this.sourceTypes = sourceTypes;
         return this;
      }

      public Builder<A> setSourceElement(SourceElement sourceElement) {
         this.sourceElement = sourceElement;
         return this;
      }

      public Builder<A> setIcon(ResourceLocation icon) {
         this.icon = icon;
         return this;
      }

      /** @deprecated */
      @Deprecated
      public Builder<A> addDescriptionLine(String desc, Object... args) {
         this.descriptionLines.add(AbilityDescriptionLine.of((Component)Component.m_237110_(desc, args), false));
         return this;
      }

      /** @deprecated */
      @Deprecated
      public Builder<A> addExtension(Consumer<Builder<A>> consumer) {
         this.consumerExtensions.add(consumer);
         return this;
      }

      public Builder<A> addDescriptionLine(AbilityDescriptionLine... lines) {
         Collections.addAll(this.descriptionLines, lines);
         return this;
      }

      public Builder<A> addDescriptionLine(AbilityDescriptionLine.IDescriptionLine... lines) {
         for(AbilityDescriptionLine.IDescriptionLine line : lines) {
            this.descriptionLines.add(AbilityDescriptionLine.of(line, false));
         }

         return this;
      }

      public Builder<A> addAdvancedDescriptionLine(AbilityDescriptionLine.IDescriptionLine... lines) {
         for(AbilityDescriptionLine.IDescriptionLine line : lines) {
            this.descriptionLines.add(AbilityDescriptionLine.of(line, true));
         }

         return this;
      }

      public Builder<A> addDescriptionLine(Component... components) {
         for(Component comp : components) {
            this.descriptionLines.add(AbilityDescriptionLine.of(comp, false));
         }

         return this;
      }

      public Builder<A> setHidden() {
         this.isHidden = true;
         return this;
      }

      public Builder<A> setPhantomKey(ResourceLocation key) {
         this.phantomKey = key;
         return this;
      }

      /** @deprecated */
      @Deprecated
      public AbilityCore<A> build() {
         return this.build((String)null);
      }

      public AbilityCore<A> build(String projectId) {
         AbilityCore<A> core = new AbilityCore<A>(this.name, this.id, this.category, this.type, this.factory);

         for(Consumer<Builder<A>> c : this.consumerExtensions) {
            c.accept(this);
         }

         Set<AbilityDescriptionLine> set = new LinkedHashSet();

         for(AbilityDescriptionLine line : this.descriptionLines) {
            set.add(line);
         }

         core.setDescription(set);
         core.setUnlockCheck(this.unlockCheck);
         core.setNodeFactories(this.nodeFactories);
         core.setSourceHakiNature(this.sourceHakiNature);
         core.setSourceTypes(this.sourceTypes);
         core.setSourceElement(this.sourceElement);
         if (this.icon == null && projectId != null) {
            core.setIcon(ResourceLocation.fromNamespaceAndPath(projectId, "textures/abilities/" + this.id + ".png"));
         } else if (this.icon == null && projectId == null) {
            core.setIcon(AbilityCore.MISSING_TEXTURE);
         } else {
            core.setIcon(this.icon);
         }

         core.setPhantomKey(this.phantomKey);
         if (this.isHidden) {
            core.setHidden();
         }

         return core;
      }
   }

   @FunctionalInterface
   public interface ICanUnlock {
      boolean canUnlock(LivingEntity var1);

      default ICanUnlock and(ICanUnlock check) {
         return (entity) -> this.canUnlock(entity) && check.canUnlock(entity);
      }

      default ICanUnlock or(ICanUnlock check) {
         return (entity) -> this.canUnlock(entity) || check.canUnlock(entity);
      }
   }

   @FunctionalInterface
   public interface IAbilityGoalFactory<A extends IAbility> {
      AbilityWrapperGoal<Mob, A> create(Mob var1, AbilityCore<A> var2);
   }

   @FunctionalInterface
   public interface IFactory<A extends IAbility> {
      A create(AbilityCore<A> var1);
   }

   @FunctionalInterface
   public interface INodeFactory {
      AbilityNode create(LivingEntity var1);
   }
}
