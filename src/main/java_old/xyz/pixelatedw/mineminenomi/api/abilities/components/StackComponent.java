package xyz.pixelatedw.mineminenomi.api.abilities.components;

import java.util.Optional;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.Nullable;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityStat;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.helpers.RendererHelper;
import xyz.pixelatedw.mineminenomi.api.util.PriorityEventPool;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityCapability;
import xyz.pixelatedw.mineminenomi.data.entity.ability.IAbilityData;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18nAbilities;
import xyz.pixelatedw.mineminenomi.packets.server.ability.component.SSetStacksPacket;

public class StackComponent extends AbilityComponent<IAbility> {
   private int defaultStacks;
   private int stacks;
   private final PriorityEventPool<IStacksChangeEvent> changeStackEvents;

   public static AbilityDescriptionLine.IDescriptionLine getTooltip(int stacks) {
      return getTooltip(stacks, stacks);
   }

   public static AbilityDescriptionLine.IDescriptionLine getTooltip(int min, int max) {
      return (e, a) -> {
         AbilityStat.Builder statBuilder = new AbilityStat.Builder(ModI18nAbilities.DESCRIPTION_STAT_NAME_STACKS, min, max);
         return statBuilder.build().getStatDescription();
      };
   }

   public StackComponent(IAbility ability) {
      this(ability, 0);
   }

   public StackComponent(IAbility ability, int defaultStacks) {
      super((AbilityComponentKey)ModAbilityComponents.STACK.get(), ability);
      this.defaultStacks = 0;
      this.stacks = -1;
      this.changeStackEvents = new PriorityEventPool<IStacksChangeEvent>();
      this.defaultStacks = defaultStacks;
   }

   public void postInit(IAbility ability) {
      ability.getComponent((AbilityComponentKey)ModAbilityComponents.SLOT_DECORATION.get()).ifPresent((component) -> component.addPostRenderEvent(10, (entity, minecraft, stack, buffer, rect, x, y, partialTicks) -> {
            if (this.getStacks() > 0) {
               stack.m_85836_();
               stack.m_252880_(x + 16.0F, y + 14.0F, 5.0F);
               stack.m_85841_(0.76F, 0.76F, 0.76F);
               RendererHelper.drawStringWithBorder(minecraft.f_91062_, stack, buffer, Component.m_237113_("" + this.getStacks()).m_7532_(), 0, 0, -1, 0);
               stack.m_85849_();
            }

         }));
      if (ability instanceof Ability ability2) {
         ability2.addEquipEvent(this::onAbilityEquipped);
      }

   }

   public StackComponent addStackChangeEvent(IStacksChangeEvent event) {
      this.changeStackEvents.addEvent(event);
      return this;
   }

   public StackComponent addStackChangeEvent(int priority, IStacksChangeEvent event) {
      this.changeStackEvents.addEvent(priority, event);
      return this;
   }

   public void revertStacksToDefault(LivingEntity entity, IAbility ability) {
      this.setStacks(entity, ability, this.defaultStacks);
   }

   public void addStacks(LivingEntity entity, IAbility ability, int stacks) {
      this.setStacks(entity, ability, this.stacks + stacks);
   }

   public void setStacks(LivingEntity entity, IAbility ability, int stacks) {
      this.ensureIsRegistered();
      if (this.stacks != stacks) {
         this.stacks = stacks;
         this.changeStackEvents.dispatch((event) -> event.onStacksChange(entity, this.getAbility(), stacks));
         if (!entity.m_9236_().f_46443_) {
            Optional<PoolComponent> poolComponent = this.getAbility().<PoolComponent>getComponent((AbilityComponentKey)ModAbilityComponents.POOL.get());
            if (poolComponent.isPresent()) {
               IAbilityData abilityDataProps = (IAbilityData)AbilityCapability.get(entity).orElse((Object)null);
               if (abilityDataProps != null) {
                  for(IAbility abl : abilityDataProps.getEquippedAndPassiveAbilities()) {
                     if (abl != this.getAbility()) {
                        int otherStacks = stacks;
                        Optional<PoolComponent> otherPoolComponent = abl.<PoolComponent>getComponent((AbilityComponentKey)ModAbilityComponents.POOL.get());
                        Optional<StackComponent> stackComponent = abl.<StackComponent>getComponent((AbilityComponentKey)ModAbilityComponents.STACK.get());
                        if (otherPoolComponent.isPresent() && ((PoolComponent)poolComponent.get()).hasAtLeastOneSamePool((PoolComponent)otherPoolComponent.get()) && stackComponent.isPresent()) {
                           boolean sharesStacks = ((PoolComponent)otherPoolComponent.get()).getPools().stream().anyMatch((pool) -> (Boolean)pool.getFlagValue("shareStacks", () -> false));
                           if (sharesStacks) {
                              if (stacks > ((StackComponent)stackComponent.get()).getDefaultStacks()) {
                                 otherStacks = ((StackComponent)stackComponent.get()).getDefaultStacks();
                              }

                              ((StackComponent)stackComponent.get()).stacks = otherStacks;
                              ModNetwork.sendToAllTrackingAndSelf(new SSetStacksPacket(entity, abl, otherStacks), entity);
                           }
                        }
                     }
                  }
               }
            }

            ModNetwork.sendToAllTrackingAndSelf(new SSetStacksPacket(entity, ability, stacks), entity);
         }

      }
   }

   public void setDefaultStacks(int defaultStacks) {
      this.defaultStacks = defaultStacks;
   }

   public int getDefaultStacks() {
      return this.defaultStacks;
   }

   public int getStacks() {
      return this.stacks;
   }

   private void onAbilityEquipped(LivingEntity entity, IAbility ability) {
      if (this.stacks < 0) {
         this.stacks = this.defaultStacks;
      }

   }

   public @Nullable CompoundTag save() {
      CompoundTag nbt = super.save();
      nbt.m_128405_("defaultStacks", this.defaultStacks);
      nbt.m_128405_("stacks", this.stacks);
      return nbt;
   }

   public void load(CompoundTag nbt) {
      super.load(nbt);
      this.defaultStacks = nbt.m_128451_("defaultStacks");
      this.stacks = nbt.m_128451_("stacks");
   }

   @FunctionalInterface
   public interface IStacksChangeEvent {
      void onStacksChange(LivingEntity var1, IAbility var2, int var3);
   }
}
