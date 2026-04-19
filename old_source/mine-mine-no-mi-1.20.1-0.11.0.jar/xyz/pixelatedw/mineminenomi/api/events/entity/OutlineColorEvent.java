package xyz.pixelatedw.mineminenomi.api.events.entity;

import java.awt.Color;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.event.entity.EntityEvent;

public class OutlineColorEvent extends EntityEvent {
   private Color color;

   public OutlineColorEvent(Entity entity) {
      super(entity);
      this.color = Color.WHITE;
   }

   public void setColor(Color color) {
      this.color = color;
   }

   public Color getColor() {
      return this.color;
   }
}
