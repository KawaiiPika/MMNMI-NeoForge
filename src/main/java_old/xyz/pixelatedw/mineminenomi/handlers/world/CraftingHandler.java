package xyz.pixelatedw.mineminenomi.handlers.world;

import java.util.Optional;
import java.util.function.Consumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.inventory.CraftingScreen;
import net.minecraft.network.protocol.game.ClientboundContainerSetSlotPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.inventory.ResultContainer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import xyz.pixelatedw.mineminenomi.api.DFEncyclopediaEntry;
import xyz.pixelatedw.mineminenomi.api.crafting.MultiChannelDyeRecipe;
import xyz.pixelatedw.mineminenomi.api.items.IMultiChannelColorItem;
import xyz.pixelatedw.mineminenomi.data.entity.stats.EntityStatsCapability;
import xyz.pixelatedw.mineminenomi.data.entity.stats.IEntityStats;
import xyz.pixelatedw.mineminenomi.init.ModItems;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.items.DFEncyclopediaItem;
import xyz.pixelatedw.mineminenomi.items.FlagItem;
import xyz.pixelatedw.mineminenomi.items.WantedPosterItem;
import xyz.pixelatedw.mineminenomi.packets.server.ui.SToggleDyeLayersPacket;
import xyz.pixelatedw.mineminenomi.ui.components.DyeLayerComponent;

public class CraftingHandler {
   public static final ResourceLocation FLAG_UPGRADE_RECIPE = ResourceLocation.fromNamespaceAndPath("mineminenomi", "flag_upgrade");
   public static final ResourceLocation WANTED_POSTER_UPGRADE_RECIPE = ResourceLocation.fromNamespaceAndPath("mineminenomi", "wanted_poster_upgrade");
   public static final ResourceLocation ENCYCLOPEDIA_MERGE_RECIPE = ResourceLocation.fromNamespaceAndPath("mineminenomi", "devil_fruit_encyclopedia_merge");

   @OnlyIn(Dist.CLIENT)
   public static void initNewDyeLayerWidget(Minecraft minecraft, CraftingScreen screen, Consumer<GuiEventListener> add) {
      int posX = screen.getGuiLeft();
      int posY = screen.f_96544_ / 2 - 59;
      DyeLayerComponent comp = new DyeLayerComponent(minecraft, screen, posX, posY);
      add.accept(comp);
   }

   public static boolean onGridChanged(AbstractContainerMenu menu, Level level, Player player, CraftingContainer container, ResultContainer resultContainer) {
      if (!level.f_46443_ && player instanceof ServerPlayer serverPlayer) {
         Optional<IEntityStats> props = EntityStatsCapability.get(serverPlayer);
         Optional<CraftingRecipe> optional = level.m_7654_().m_129894_().m_44015_(RecipeType.f_44107_, container, level);
         ItemStack stack = ItemStack.f_41583_;
         if (optional.isPresent()) {
            CraftingRecipe recipe = (CraftingRecipe)optional.get();
            if (recipe.m_6423_().equals(FLAG_UPGRADE_RECIPE)) {
               if (resultContainer.m_40135_(level, serverPlayer, recipe)) {
                  stack = container.m_8020_(4).m_41777_();
                  stack = handleFlagUpgrades(stack);
               }
            } else if (recipe.m_6423_().equals(WANTED_POSTER_UPGRADE_RECIPE)) {
               if (resultContainer.m_40135_(level, serverPlayer, recipe)) {
                  stack = container.m_8020_(4).m_41777_();
                  stack = handleWantedPosterUpgrades(stack);
               }
            } else if (recipe.m_6423_().equals(ENCYCLOPEDIA_MERGE_RECIPE)) {
               if (resultContainer.m_40135_(level, serverPlayer, recipe)) {
                  ItemStack stack2 = ItemStack.f_41583_;

                  for(int i = 0; i < container.m_6643_(); ++i) {
                     ItemStack containerStack = container.m_8020_(i);
                     if (!containerStack.m_41619_() && containerStack.m_41720_().equals(ModItems.DEVIL_FRUIT_ENCYCLOPEDIA.get())) {
                        if (stack.m_41619_()) {
                           stack = containerStack.m_41777_();
                        } else {
                           stack2 = containerStack.m_41777_();
                        }
                     }
                  }

                  if (!stack.m_41619_() && !stack2.m_41619_()) {
                     stack = handleEncyclopediaMerge(stack, stack2);
                  }
               }
            } else if (recipe instanceof MultiChannelDyeRecipe) {
               MultiChannelDyeRecipe dyeRecipe = (MultiChannelDyeRecipe)recipe;
               if (resultContainer.m_40135_(level, serverPlayer, recipe)) {
                  ItemStack dyeableStack = ItemStack.f_41583_;

                  for(int i = 0; i < container.m_6643_(); ++i) {
                     ItemStack containerStack = container.m_8020_(i);
                     if (!containerStack.m_41619_()) {
                        Item var15 = containerStack.m_41720_();
                        if (var15 instanceof IMultiChannelColorItem) {
                           IMultiChannelColorItem colorItem = (IMultiChannelColorItem)var15;
                           if (colorItem.canBeDyed()) {
                              dyeableStack = containerStack;
                           }
                        }
                     }
                  }

                  if (!dyeableStack.m_41619_()) {
                     Item containerStack = dyeableStack.m_41720_();
                     if (containerStack instanceof IMultiChannelColorItem) {
                        IMultiChannelColorItem colorItem = (IMultiChannelColorItem)containerStack;
                        int layer = Math.min(dyeRecipe.getLayer(), colorItem.getMaxLayers() - 1);
                        props.ifPresent((data) -> data.setDyeLayerCheck(true));
                        ModNetwork.sendTo(new SToggleDyeLayersPacket(true, layer, colorItem.getMaxLayers()), serverPlayer);
                     }
                  }
               }
            }

            if (!stack.m_41619_()) {
               resultContainer.m_6836_(0, stack);
               serverPlayer.f_8906_.m_9829_(new ClientboundContainerSetSlotPacket(menu.f_38840_, menu.m_182425_(), 0, stack));
               return true;
            }
         } else {
            boolean update = (Boolean)props.map(IEntityStats::isDyeLayerCheck).orElse(false);
            if (update) {
               props.ifPresent((data) -> data.setDyeLayerCheck(false));
               ModNetwork.sendTo(new SToggleDyeLayersPacket(false, 0, 0), serverPlayer);
            }
         }
      }

      return false;
   }

   private static ItemStack handleEncyclopediaMerge(ItemStack output, ItemStack other) {
      for(DFEncyclopediaEntry entry : DFEncyclopediaItem.getEntries(other)) {
         DFEncyclopediaItem.addFruitClues(output, entry.getDevilFruit().getRegistryKey(), entry);
      }

      return output;
   }

   private static ItemStack handleWantedPosterUpgrades(ItemStack output) {
      return WantedPosterItem.upgradeCanvasSize(output) ? output : ItemStack.f_41583_;
   }

   private static ItemStack handleFlagUpgrades(ItemStack output) {
      return FlagItem.upgradeCanvasSize(output) ? output : ItemStack.f_41583_;
   }
}
