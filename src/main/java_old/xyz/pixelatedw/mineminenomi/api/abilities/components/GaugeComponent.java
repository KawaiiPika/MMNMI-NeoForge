package xyz.pixelatedw.mineminenomi.api.abilities.components;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;

public class GaugeComponent extends AbilityComponent<IAbility> {
   private IGaugeRenderer<? extends IAbility> renderer;

   public <A extends IAbility> GaugeComponent(IAbility ability, IGaugeRenderer<A> renderer) {
      super((AbilityComponentKey)ModAbilityComponents.GAUGE.get(), ability);
      this.renderer = renderer;
      this.setClientSide();
   }

   public <A extends IAbility> IGaugeRenderer<A> getRenderer() {
      return this.renderer;
   }

   @FunctionalInterface
   public interface IGaugeRenderer<A extends IAbility> {
      @OnlyIn(Dist.CLIENT)
      void renderGauge(Player var1, GuiGraphics var2, int var3, int var4, A var5);
   }
}
