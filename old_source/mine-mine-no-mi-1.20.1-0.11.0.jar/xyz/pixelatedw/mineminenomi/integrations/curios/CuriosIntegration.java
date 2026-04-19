package xyz.pixelatedw.mineminenomi.integrations.curios;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import net.minecraft.core.Direction;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import top.theillusivec4.curios.api.CuriosCapability;
import top.theillusivec4.curios.api.client.CuriosRendererRegistry;
import top.theillusivec4.curios.api.type.capability.ICurio;
import xyz.pixelatedw.mineminenomi.init.ModArmors;
import xyz.pixelatedw.mineminenomi.init.ModItems;
import xyz.pixelatedw.mineminenomi.integrations.curios.renderers.ArmorCurioRenderer;
import xyz.pixelatedw.mineminenomi.integrations.curios.renderers.ItemCurioRenderer;

public class CuriosIntegration {
   private static final Map<Item, Function<ItemStack, ICurio>> CURIO_ITEMS = new HashMap();

   public static void setupCurioCapabilities(AttachCapabilitiesEvent<ItemStack> event) {
      ItemStack stack = (ItemStack)event.getObject();
      Item item = stack.m_41720_();
      if (CURIO_ITEMS.containsKey(item)) {
         final LazyOptional<ICurio> curioItem = LazyOptional.of(() -> (ICurio)((Function)CURIO_ITEMS.get(item)).apply(stack));
         event.addCapability(CuriosCapability.ID_ITEM, new ICapabilityProvider() {
            public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
               return CuriosCapability.ITEM.orEmpty(cap, curioItem);
            }
         });
      }

   }

   public static void registerCurioItems() {
      CURIO_ITEMS.put((Item)ModArmors.CELESTIAL_DRAGON_BUBBLE.get(), SimpleCurioItem::new);
      CURIO_ITEMS.put((Item)ModArmors.BICORNE.get(), SimpleCurioItem::new);
      CURIO_ITEMS.put((Item)ModArmors.CHOPPER_HAT.get(), SimpleCurioItem::new);
      CURIO_ITEMS.put((Item)ModArmors.KILLER_MASK.get(), SimpleCurioItem::new);
      CURIO_ITEMS.put((Item)ModArmors.LAW_HAT.get(), SimpleCurioItem::new);
      CURIO_ITEMS.put((Item)ModArmors.TRICORNE.get(), SimpleCurioItem::new);
      CURIO_ITEMS.put((Item)ModArmors.SABO_HAT.get(), SimpleCurioItem::new);
      CURIO_ITEMS.put((Item)ModArmors.MIHAWK_HAT.get(), SimpleCurioItem::new);
      CURIO_ITEMS.put((Item)ModArmors.FLEET_ADMIRAL_HAT.get(), SimpleCurioItem::new);
      CURIO_ITEMS.put((Item)ModArmors.PLUME_HAT.get(), SimpleCurioItem::new);
      CURIO_ITEMS.put((Item)ModArmors.WIDE_BRIM_HAT.get(), SimpleCurioItem::new);
      CURIO_ITEMS.put((Item)ModArmors.STRAW_HAT.get(), SimpleCurioItem::new);
      CURIO_ITEMS.put((Item)ModArmors.ACE_HAT.get(), SimpleCurioItem::new);
      CURIO_ITEMS.put((Item)ModArmors.FRANKY_GLASSES.get(), SimpleCurioItem::new);
      CURIO_ITEMS.put((Item)ModArmors.CABAJI_SCARF.get(), SimpleCurioItem::new);
      CURIO_ITEMS.put((Item)ModArmors.BIG_RED_NOSE.get(), SimpleCurioItem::new);
      CURIO_ITEMS.put((Item)ModArmors.KURO_GLASSES.get(), SimpleCurioItem::new);
      CURIO_ITEMS.put((Item)ModArmors.MR3_GLASSES.get(), SimpleCurioItem::new);
      CURIO_ITEMS.put((Item)ModArmors.MR5_GLASSES.get(), SimpleCurioItem::new);
      CURIO_ITEMS.put((Item)ModArmors.SNIPER_GOGGLES.get(), SimpleCurioItem::new);
      CURIO_ITEMS.put((Item)ModArmors.MH5_GAS_MASK.get(), SimpleCurioItem::new);
      CURIO_ITEMS.put((Item)ModArmors.KIZARU_GLASSES.get(), SimpleCurioItem::new);
      CURIO_ITEMS.put((Item)ModArmors.DOFFY_GLASSES.get(), SimpleCurioItem::new);
      CURIO_ITEMS.put((Item)ModArmors.HEART_GLASSES.get(), SimpleCurioItem::new);
      CURIO_ITEMS.put((Item)ModItems.CIGAR.get(), SimpleCurioItem::new);
      CURIO_ITEMS.put((Item)ModItems.CIGARETTE.get(), SimpleCurioItem::new);
      CURIO_ITEMS.put((Item)ModItems.SMOKING_PIPE.get(), SimpleCurioItem::new);
      CURIO_ITEMS.put((Item)ModItems.THREE_CIGARS.get(), SimpleCurioItem::new);
      CURIO_ITEMS.put((Item)ModArmors.FLUFFY_CAPE.get(), SimpleCurioItem::new);
      CURIO_ITEMS.put((Item)ModArmors.MARINE_CAPTAIN_CAPE.get(), SimpleCurioItem::new);
      CURIO_ITEMS.put((Item)ModArmors.PIRATE_CAPTAIN_CAPE.get(), SimpleCurioItem::new);
      CURIO_ITEMS.put((Item)ModArmors.COLA_BACKPACK.get(), SimpleCurioItem::new);
      CURIO_ITEMS.put((Item)ModArmors.MEDIC_BAG.get(), SimpleCurioItem::new);
      CURIO_ITEMS.put((Item)ModArmors.TOMOE_DRUMS.get(), SimpleCurioItem::new);
      CURIO_ITEMS.put((Item)ModItems.COLOR_PALETTE.get(), SimpleCurioItem::new);
   }

   public static void registerCurioRenderers() {
      CuriosRendererRegistry.register((Item)ModArmors.CELESTIAL_DRAGON_BUBBLE.get(), ArmorCurioRenderer::new);
      CuriosRendererRegistry.register((Item)ModArmors.BICORNE.get(), ArmorCurioRenderer::new);
      CuriosRendererRegistry.register((Item)ModArmors.CHOPPER_HAT.get(), ArmorCurioRenderer::new);
      CuriosRendererRegistry.register((Item)ModArmors.KILLER_MASK.get(), ArmorCurioRenderer::new);
      CuriosRendererRegistry.register((Item)ModArmors.LAW_HAT.get(), ArmorCurioRenderer::new);
      CuriosRendererRegistry.register((Item)ModArmors.TRICORNE.get(), ArmorCurioRenderer::new);
      CuriosRendererRegistry.register((Item)ModArmors.SABO_HAT.get(), ArmorCurioRenderer::new);
      CuriosRendererRegistry.register((Item)ModArmors.MIHAWK_HAT.get(), ArmorCurioRenderer::new);
      CuriosRendererRegistry.register((Item)ModArmors.FLEET_ADMIRAL_HAT.get(), ArmorCurioRenderer::new);
      CuriosRendererRegistry.register((Item)ModArmors.PLUME_HAT.get(), ArmorCurioRenderer::new);
      CuriosRendererRegistry.register((Item)ModArmors.WIDE_BRIM_HAT.get(), ArmorCurioRenderer::new);
      CuriosRendererRegistry.register((Item)ModArmors.STRAW_HAT.get(), ArmorCurioRenderer::new);
      CuriosRendererRegistry.register((Item)ModArmors.ACE_HAT.get(), ArmorCurioRenderer::new);
      CuriosRendererRegistry.register((Item)ModArmors.FRANKY_GLASSES.get(), ArmorCurioRenderer::new);
      CuriosRendererRegistry.register((Item)ModArmors.CABAJI_SCARF.get(), ArmorCurioRenderer::new);
      CuriosRendererRegistry.register((Item)ModArmors.BIG_RED_NOSE.get(), ArmorCurioRenderer::new);
      CuriosRendererRegistry.register((Item)ModArmors.KURO_GLASSES.get(), ArmorCurioRenderer::new);
      CuriosRendererRegistry.register((Item)ModArmors.MR3_GLASSES.get(), ArmorCurioRenderer::new);
      CuriosRendererRegistry.register((Item)ModArmors.MR5_GLASSES.get(), ArmorCurioRenderer::new);
      CuriosRendererRegistry.register((Item)ModArmors.SNIPER_GOGGLES.get(), ArmorCurioRenderer::new);
      CuriosRendererRegistry.register((Item)ModArmors.MH5_GAS_MASK.get(), ArmorCurioRenderer::new);
      CuriosRendererRegistry.register((Item)ModArmors.KIZARU_GLASSES.get(), ArmorCurioRenderer::new);
      CuriosRendererRegistry.register((Item)ModArmors.DOFFY_GLASSES.get(), ArmorCurioRenderer::new);
      CuriosRendererRegistry.register((Item)ModArmors.HEART_GLASSES.get(), ArmorCurioRenderer::new);
      CuriosRendererRegistry.register((Item)ModItems.CIGAR.get(), ItemCurioRenderer::new);
      CuriosRendererRegistry.register((Item)ModItems.CIGARETTE.get(), ItemCurioRenderer::new);
      CuriosRendererRegistry.register((Item)ModItems.SMOKING_PIPE.get(), ItemCurioRenderer::new);
      CuriosRendererRegistry.register((Item)ModItems.THREE_CIGARS.get(), ItemCurioRenderer::new);
      CuriosRendererRegistry.register((Item)ModArmors.FLUFFY_CAPE.get(), ArmorCurioRenderer::new);
      CuriosRendererRegistry.register((Item)ModArmors.MARINE_CAPTAIN_CAPE.get(), ArmorCurioRenderer::new);
      CuriosRendererRegistry.register((Item)ModArmors.PIRATE_CAPTAIN_CAPE.get(), ArmorCurioRenderer::new);
      CuriosRendererRegistry.register((Item)ModArmors.COLA_BACKPACK.get(), ArmorCurioRenderer::new);
      CuriosRendererRegistry.register((Item)ModArmors.MEDIC_BAG.get(), ArmorCurioRenderer::new);
      CuriosRendererRegistry.register((Item)ModArmors.TOMOE_DRUMS.get(), ArmorCurioRenderer::new);
   }
}
