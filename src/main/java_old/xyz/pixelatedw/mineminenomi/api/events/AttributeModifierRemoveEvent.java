package xyz.pixelatedw.mineminenomi.api.events;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraftforge.event.entity.living.LivingEvent;

public class AttributeModifierRemoveEvent extends LivingEvent {
   private AttributeMap map;

   public AttributeModifierRemoveEvent(LivingEntity entity, AttributeMap map, int amplifier) {
      super(entity);
      this.map = map;
   }

   public AttributeInstance getAttribute(Attribute attr) {
      return this.map.m_22146_(attr);
   }
}
