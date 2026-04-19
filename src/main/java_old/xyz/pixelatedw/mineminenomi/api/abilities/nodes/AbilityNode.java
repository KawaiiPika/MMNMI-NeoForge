package xyz.pixelatedw.mineminenomi.api.abilities.nodes;

import java.util.LinkedHashSet;
import java.util.Set;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.actions.NodeUnlockAction;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.NodeUnlockCondition;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityCapability;
import xyz.pixelatedw.mineminenomi.data.entity.ability.IAbilityData;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18nNodes;

public class AbilityNode {
   private final Set<AbilityNode> prerequisites = new LinkedHashSet();
   private boolean unlocked = false;
   private final Component localizedName;
   private final ResourceLocation icon;
   private final NodePos position;
   private final MutableComponent tooltip = Component.m_237119_();
   private NodeUnlockCondition unlockCondition;
   private NodeUnlockAction unlockAction;

   public AbilityNode(Component localizedName, ResourceLocation icon, NodePos position) {
      this.localizedName = localizedName;
      this.icon = icon;
      this.position = position;
   }

   public Component getLocalizedName() {
      return this.localizedName;
   }

   public ResourceLocation getIcon() {
      return this.icon;
   }

   public NodePos getPosition() {
      return this.position;
   }

   public Component getTooltip() {
      return this.tooltip;
   }

   public void appendTooltip(Component comp) {
      this.tooltip.m_7220_(comp);
   }

   public Set<AbilityNode> getPrerequisites() {
      return Set.copyOf(this.prerequisites);
   }

   @SafeVarargs
   public final void addPrerequisites(AbilityNode... nodes) {
      for(AbilityNode node : nodes) {
         this.prerequisites.add(node);
         this.appendTooltip(Component.m_237110_(ModI18nNodes.NODE_CHECK, new Object[]{node.getLocalizedName().getString()}));
         this.appendTooltip(Component.m_237113_("\n"));
      }

   }

   public void setUnlockRule(NodeUnlockCondition unlockCondition, NodeUnlockAction unlockAction) {
      this.unlockCondition = unlockCondition;
      this.unlockAction = unlockAction;
      this.appendTooltip(unlockCondition.getTooltip());
   }

   public boolean isUnlocked(LivingEntity entity) {
      return this.unlocked;
   }

   public boolean canUnlock(LivingEntity entity) {
      if (this.isUnlocked(entity)) {
         return false;
      } else if (this.unlockCondition != null && !this.unlockCondition.test(entity)) {
         return false;
      } else {
         for(AbilityNode node : this.prerequisites) {
            if (!node.isUnlocked(entity)) {
               return false;
            }
         }

         return true;
      }
   }

   public void unlockNode(LivingEntity entity) {
      if (!this.unlocked) {
         IAbilityData abilityProps = (IAbilityData)AbilityCapability.getLazy(entity).orElse((Object)null);
         if (abilityProps != null) {
            this.unlocked = true;
            if (this.unlockAction != null) {
               this.unlockAction.onUnlock(entity);
            }

         }
      }
   }

   public void lockNode(LivingEntity entity) {
      if (this.unlocked) {
         if (this.unlockAction != null) {
            this.unlockAction.onLock(entity);
         }

         this.unlocked = false;
      }
   }

   public CompoundTag save() {
      CompoundTag tag = new CompoundTag();
      tag.m_128379_("unlocked", this.unlocked);
      return tag;
   }

   public void load(CompoundTag tag) {
      this.unlocked = tag.m_128471_("unlocked");
   }

   public static record NodePos(float x, float y) {
   }
}
