package xyz.pixelatedw.mineminenomi.effects;

import java.util.List;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.item.ItemStack;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.effects.IIgnoreMilkEffect;
import xyz.pixelatedw.mineminenomi.api.effects.ILingeringEffect;

public class BaseEffect extends MobEffect implements IIgnoreMilkEffect, ILingeringEffect {
   public BaseEffect(MobEffectCategory category, int color) {
      super(category, color);
   }

   public void m_6385_(LivingEntity entity, AttributeMap map, int amp) {
      super.m_6385_(entity, map, amp);
      MobEffectInstance instance = entity.m_21124_(this);
      this.startEffect(entity, instance);
      if (this.shouldUpdateClient()) {
         WyHelper.sendApplyEffectToAllNearby(entity, entity.m_20182_(), 100, entity.m_21124_(this));
      }

   }

   public void m_6386_(LivingEntity entity, AttributeMap map, int amp) {
      super.m_6386_(entity, map, amp);
      this.stopEffect(entity);
      if (this.shouldUpdateClient()) {
         WyHelper.sendRemoveEffectToAllNearby(entity, entity.m_20182_(), 100, this);
      }

   }

   public void startEffect(LivingEntity entity, MobEffectInstance instance) {
   }

   public void stopEffect(LivingEntity entity) {
   }

   public boolean shouldUpdateClient() {
      return false;
   }

   public List<ItemStack> getCurativeItems() {
      return !this.isRemoveable() ? List.of() : super.getCurativeItems();
   }
}
