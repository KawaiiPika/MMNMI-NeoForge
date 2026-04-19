package xyz.pixelatedw.mineminenomi.items.armors;

import java.util.Arrays;
import java.util.function.Consumer;
import javax.annotation.Nonnull;
import joptsimple.internal.Strings;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import xyz.pixelatedw.mineminenomi.api.helpers.RendererHelper;
import xyz.pixelatedw.mineminenomi.api.items.IArmorVisibility;
import xyz.pixelatedw.mineminenomi.api.items.IMultiChannelColorItem;
import xyz.pixelatedw.mineminenomi.data.entity.devilfruit.DevilFruitCapability;
import xyz.pixelatedw.mineminenomi.init.ModArmors;
import xyz.pixelatedw.mineminenomi.init.ModMaterials;

public class ModArmorItem extends ArmorItem implements IMultiChannelColorItem {
   private static final int DEFAULT_LAYERS = 1;
   protected String textureName;
   protected boolean isFit;
   private boolean isDyeable;
   private int availableLayers;
   private int[] defaultLayerColors;

   public ModArmorItem(String name, ArmorItem.Type type) {
      this(ModMaterials.SIMPLE_LEATHER_MATERIAL, name, type, new Item.Properties());
   }

   public ModArmorItem(ArmorMaterial mat, String name, ArmorItem.Type type) {
      this(mat, name, type, new Item.Properties());
   }

   public ModArmorItem(ArmorMaterial mat, String name, ArmorItem.Type type, Item.Properties props) {
      super(mat, type, props);
      this.isFit = true;
      this.isDyeable = false;
      this.availableLayers = 0;
      this.defaultLayerColors = null;
      this.textureName = name;
   }

   @Nonnull
   public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
      String typeId = String.format("_%s", slot == EquipmentSlot.LEGS ? "bot" : "top");
      String layerName = "";
      if (!Strings.isNullOrEmpty(type) && this.availableLayers > 0) {
         layerName = String.format("_%s", type);
      }

      return String.format("%s:textures/models/armor/%s%s%s.png", "mineminenomi", this.textureName, typeId, layerName);
   }

   @OnlyIn(Dist.CLIENT)
   public void initializeClient(Consumer<IClientItemExtensions> consumer) {
      consumer.accept(new IClientItemExtensions() {
         public HumanoidModel<?> getHumanoidArmorModel(LivingEntity livingEntity, ItemStack itemStack, EquipmentSlot equipmentSlot, HumanoidModel<?> original) {
            Item var6 = itemStack.m_41720_();
            if (var6 instanceof IArmorVisibility armorVisibility) {
               armorVisibility.setPartVisibility(original, itemStack, equipmentSlot);
            } else {
               RendererHelper.setPartVisibility(original, equipmentSlot);
            }

            HumanoidModel<LivingEntity> replacement = (HumanoidModel)ModArmors.Client.ARMOR_MODELS.get(itemStack.m_41720_());
            if (replacement != null) {
               replacement.m_6973_(livingEntity, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
               original = replacement;
            }

            if (livingEntity instanceof Player player) {
               boolean isMorphed = (Boolean)DevilFruitCapability.get(player).map((fruit) -> fruit.getCurrentMorph().isPresent()).orElse(false);
               if (!isMorphed) {
                  Item var9 = itemStack.m_41720_();
                  if (var9 instanceof ModArmorItem) {
                     ModArmorItem armorItem = (ModArmorItem)var9;
                     if (armorItem.isFit) {
                        this.slimDown(equipmentSlot, original);
                     }
                  }
               }
            }

            return original;
         }

         public <T extends LivingEntity> void slimDown(EquipmentSlot slot, HumanoidModel<T> model) {
            float scaleTop = slot == EquipmentSlot.LEGS ? 0.9F : 0.85F;
            float scaleBot = slot == EquipmentSlot.FEET ? 0.7F : 0.85F;
            model.f_102808_.f_233553_ = scaleTop;
            model.f_102808_.f_233555_ = scaleTop;
            model.f_102809_.f_233553_ = scaleTop;
            model.f_102809_.f_233555_ = scaleTop;
            model.f_102810_.f_233553_ = scaleTop;
            model.f_102810_.f_233555_ = scaleTop;
            model.f_102811_.f_233553_ = scaleTop;
            model.f_102811_.f_233555_ = scaleTop;
            model.f_102812_.f_233553_ = scaleTop;
            model.f_102812_.f_233555_ = scaleTop;
            model.f_102813_.f_233553_ = scaleBot;
            model.f_102813_.f_233555_ = scaleBot;
            model.f_102814_.f_233553_ = scaleBot;
            model.f_102814_.f_233555_ = scaleBot;
         }
      });
   }

   public <T extends ModArmorItem> T setBaggyTrousers() {
      this.isFit = false;
      return (T)this;
   }

   public <T extends ModArmorItem> T setDyeable() {
      return (T)this.setDyeable(1);
   }

   public <T extends ModArmorItem> T setDyeable(int maxLayers) {
      this.isDyeable = true;
      this.availableLayers = maxLayers;
      this.defaultLayerColors = new int[maxLayers];
      Arrays.fill(this.defaultLayerColors, -1);
      return (T)this;
   }

   public <T extends ModArmorItem> T setDefaultColor(int color) {
      return (T)this.setDefaultLayerColor(0, color);
   }

   public <T extends ModArmorItem> T setDefaultLayerColor(int layer, int color) {
      if (this.isDyeable && this.availableLayers != 0) {
         if (layer > this.availableLayers) {
            throw new RuntimeException("Tried assigning default color for layer " + layer + " but the item only has " + this.availableLayers + " available layers");
         } else {
            this.defaultLayerColors[layer] = color;
            return (T)this;
         }
      } else {
         throw new RuntimeException("Item is not dyeable or has 0 registered layers!");
      }
   }

   public int getDefaultLayerColor(int layer) {
      return this.defaultLayerColors != null && this.defaultLayerColors.length >= 1 && layer < this.defaultLayerColors.length ? this.defaultLayerColors[layer] : -1;
   }

   public boolean canBeDyed() {
      return this.isDyeable;
   }

   public int getMaxLayers() {
      return this.availableLayers;
   }
}
