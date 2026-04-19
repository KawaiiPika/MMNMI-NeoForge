package xyz.pixelatedw.mineminenomi.abilities.electro;

import com.mojang.blaze3d.systems.RenderSystem;
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
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityCapability;
import xyz.pixelatedw.mineminenomi.data.entity.stats.EntityStatsCapability;
import xyz.pixelatedw.mineminenomi.data.entity.stats.IEntityStats;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class SulongCheckAbility extends PassiveAbility {
   public static final RegistryObject<AbilityCore<SulongCheckAbility>> INSTANCE = ModRegistry.registerAbility("sulong_check", "Sulong Check", (id, name) -> (new AbilityCore.Builder(id, name, AbilityCategory.RACIAL, AbilityType.PASSIVE, SulongCheckAbility::new)).setUnlockCheck(SulongCheckAbility::canUnlock).setHidden().build("mineminenomi"));

   public SulongCheckAbility(AbilityCore<SulongCheckAbility> core) {
      super(core);
      if (super.isClientSide()) {
         GaugeComponent gaugeComponent = new GaugeComponent(this, this::renderGauge);
         super.addComponents(gaugeComponent);
      }

   }

   @OnlyIn(Dist.CLIENT)
   public void renderGauge(Player player, GuiGraphics graphics, int posX, int posY, SulongCheckAbility ability) {
      RenderSystem.enableBlend();
      if (ElectroHelper.canTransform(player.m_9236_())) {
         RendererHelper.drawIcon(((AbilityCore)SulongAbility.INSTANCE.get()).getIcon(), graphics.m_280168_(), (float)posX, (float)(posY - 38), 0.0F, 32.0F, 32.0F);
      }

      RenderSystem.disableBlend();
   }

   private static boolean canUnlock(LivingEntity entity) {
      IEntityStats props = (IEntityStats)EntityStatsCapability.get(entity).orElse((Object)null);
      if (props == null) {
         return false;
      } else {
         boolean hasSulong = (Boolean)AbilityCapability.get(entity).map((p) -> p.hasUnlockedAbility((AbilityCore)SulongAbility.INSTANCE.get())).orElse(false);
         return props.isMink() && hasSulong;
      }
   }
}
