package xyz.pixelatedw.mineminenomi.items.armors;

import java.util.function.Consumer;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.Nullable;
import xyz.pixelatedw.mineminenomi.init.ModArmors;
import xyz.pixelatedw.mineminenomi.init.ModMaterials;

public class PearlArmorItem extends ArmorItem {
   public PearlArmorItem(ArmorItem.Type type) {
      super(ModMaterials.PEARL_MATERIAL, type, new Item.Properties());
   }

   public @Nullable String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
      return String.format("%s:textures/models/armor/pearl_armor.png", "mineminenomi");
   }

   @OnlyIn(Dist.CLIENT)
   public void initializeClient(Consumer<IClientItemExtensions> consumer) {
      consumer.accept(new IClientItemExtensions() {
         public HumanoidModel<?> getHumanoidArmorModel(LivingEntity livingEntity, ItemStack itemStack, EquipmentSlot equipmentSlot, HumanoidModel<?> original) {
            HumanoidModel<LivingEntity> replacement = (HumanoidModel)ModArmors.Client.ARMOR_MODELS.get(itemStack.m_41720_());
            return replacement != null ? replacement : original;
         }
      });
   }
}
