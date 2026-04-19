package xyz.pixelatedw.mineminenomi.api.events.onefruit;

import javax.annotation.Nonnull;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.Cancelable;

public class EatDevilFruitEvent extends PlayerEvent {
   private final ItemStack devilFruit;

   private EatDevilFruitEvent(Player entity, @Nonnull ItemStack devilFruit) {
      super(entity);
      this.devilFruit = devilFruit;
   }

   public ItemStack getItem() {
      return this.devilFruit;
   }

   @Cancelable
   public static class Pre extends EatDevilFruitEvent {
      public Pre(Player entity, @Nonnull ItemStack devilFruit) {
         super(entity, devilFruit);
      }
   }

   public static class Post extends EatDevilFruitEvent {
      public Post(Player entity, @Nonnull ItemStack devilFruit) {
         super(entity, devilFruit);
      }
   }
}
