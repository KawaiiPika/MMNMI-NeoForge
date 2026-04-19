package xyz.pixelatedw.mineminenomi.abilities.jiki;

import com.mojang.blaze3d.systems.RenderSystem;
import java.awt.Color;
import java.text.DecimalFormat;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityType;
import xyz.pixelatedw.mineminenomi.api.abilities.PassiveAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.GaugeComponent;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.ItemsHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.RendererHelper;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.ModResources;
import xyz.pixelatedw.mineminenomi.packets.server.ability.SUpdateAbilityNBTPacket;

public class MagneticItemsAbility extends PassiveAbility {
   private static final DecimalFormat FORMAT = new DecimalFormat("#0.0");
   private float ironValue;
   private float prevIronValue = -1.0F;
   public static final RegistryObject<AbilityCore<MagneticItemsAbility>> INSTANCE = ModRegistry.registerAbility("magnetic_items", "Magnetic Items", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Displays the total value of maginetic items in the user's inventory", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, AbilityType.PASSIVE, MagneticItemsAbility::new)).addDescriptionLine(desc).build("mineminenomi");
   });

   public MagneticItemsAbility(AbilityCore<MagneticItemsAbility> core) {
      super(core);
      if (this.isClientSide()) {
         GaugeComponent gaugeComponent = new GaugeComponent(this, this::renderGauge);
         this.addComponents(new AbilityComponent[]{gaugeComponent});
      }

      this.addDuringPassiveEvent(this::duringPassiveEvent);
   }

   public void duringPassiveEvent(LivingEntity entity) {
      if (!entity.m_9236_().f_46443_) {
         List<ItemStack> inventory = ItemsHelper.getAllInventoryItems(entity);
         this.ironValue = JikiHelper.getIronAmount(inventory);
         if (this.ironValue != this.prevIronValue) {
            if (entity instanceof Player) {
               Player player = (Player)entity;
               ModNetwork.sendTo(new SUpdateAbilityNBTPacket(entity, this), player);
            }

            this.prevIronValue = this.ironValue;
         }
      }

   }

   public void saveAdditional(CompoundTag nbt) {
      nbt.m_128350_("ironValue", this.ironValue);
   }

   public void loadAdditional(CompoundTag nbt) {
      this.ironValue = nbt.m_128457_("ironValue");
   }

   @OnlyIn(Dist.CLIENT)
   public void renderGauge(Player player, GuiGraphics graphics, int posX, int posY, MagneticItemsAbility ability) {
      RenderSystem.enableBlend();
      Minecraft mc = Minecraft.m_91087_();
      RenderSystem.setShaderTexture(0, ModResources.WIDGETS);
      RendererHelper.drawIcon(((AbilityCore)INSTANCE.get()).getIcon(), graphics.m_280168_(), (float)posX, (float)(posY - 38), 0.0F, 32.0F, 32.0F);
      String value = FORMAT.format((double)ability.ironValue);
      RendererHelper.drawStringWithBorder(mc.f_91062_, graphics, value, posX + 16 - mc.f_91062_.m_92895_(value) / 2, posY - 15, Color.WHITE.getRGB());
      RenderSystem.disableBlend();
   }
}
