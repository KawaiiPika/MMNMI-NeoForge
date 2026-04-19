package xyz.pixelatedw.mineminenomi.abilities.cyborg;

import com.mojang.blaze3d.systems.RenderSystem;
import java.awt.Color;
import java.util.Optional;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.RegistryObject;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityType;
import xyz.pixelatedw.mineminenomi.api.abilities.PassiveAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.GaugeComponent;
import xyz.pixelatedw.mineminenomi.api.helpers.RendererHelper;
import xyz.pixelatedw.mineminenomi.data.entity.stats.EntityStatsCapability;
import xyz.pixelatedw.mineminenomi.data.entity.stats.IEntityStats;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.ModResources;

public class ColaFuelAbility extends PassiveAbility {
   public static final RegistryObject<AbilityCore<ColaFuelAbility>> INSTANCE = ModRegistry.registerAbility("cola_fuel", "Cola Fuel", (id, name) -> (new AbilityCore.Builder(id, name, AbilityCategory.RACIAL, AbilityType.PASSIVE, ColaFuelAbility::new)).setHidden().setUnlockCheck(ColaFuelAbility::canUnlock).build("mineminenomi"));

   public ColaFuelAbility(AbilityCore<ColaFuelAbility> ability) {
      super(ability);
      if (super.isClientSide()) {
         GaugeComponent gaugeComponent = new GaugeComponent(this, this::renderGauge);
         super.addComponents(gaugeComponent);
      }

   }

   @OnlyIn(Dist.CLIENT)
   private void renderGauge(Player player, GuiGraphics graphics, int posX, int posY, ColaFuelAbility ability) {
      RenderSystem.enableBlend();
      Optional<IEntityStats> entityStatsProps = EntityStatsCapability.get(player);
      float cola = (float)(Integer)entityStatsProps.map((props) -> props.getCola()).orElse(0);
      float maxCola = (float)(Integer)entityStatsProps.map((props) -> props.getMaxCola()).orElse(1);
      Minecraft mc = Minecraft.m_91087_();
      graphics.m_280218_(ModResources.WIDGETS, posX + 6, posY - 42, 0, 52, 23, 40);
      int barHeight = (int)(cola / maxCola * 30.0F) + 23;
      if (barHeight > 0 && barHeight < 24) {
         barHeight = 24;
      } else if (barHeight > 52) {
         barHeight = 52;
      }

      graphics.m_280218_(ModResources.WIDGETS, posX + 10, posY - 42, 32, barHeight, 16, 32);
      String colaLabel = "" + cola;
      RendererHelper.drawStringWithBorder(mc.f_91062_, graphics, colaLabel, posX + 18 - mc.f_91062_.m_92895_(colaLabel) / 2, posY - 12, Color.WHITE.getRGB());
      RenderSystem.disableBlend();
   }

   private static boolean canUnlock(LivingEntity entity) {
      return (Boolean)EntityStatsCapability.get(entity).map((props) -> props.isCyborg()).orElse(false);
   }
}
