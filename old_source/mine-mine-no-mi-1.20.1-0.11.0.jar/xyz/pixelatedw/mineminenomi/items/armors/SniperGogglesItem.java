package xyz.pixelatedw.mineminenomi.items.armors;

import java.util.function.Consumer;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ArmorItem.Type;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import xyz.pixelatedw.mineminenomi.init.ModArmors;
import xyz.pixelatedw.mineminenomi.init.ModMaterials;
import xyz.pixelatedw.mineminenomi.models.armors.SniperGogglesModel;

public class SniperGogglesItem extends Mod3DArmorItem {
   public SniperGogglesItem() {
      super(ModMaterials.SNIPER_GOGGLES_MATERIAL, "sniper_goggles", Type.HELMET);
   }

   @OnlyIn(Dist.CLIENT)
   public void initializeClient(Consumer<IClientItemExtensions> consumer) {
      consumer.accept(new IClientItemExtensions() {
         public HumanoidModel<?> getHumanoidArmorModel(LivingEntity livingEntity, ItemStack itemStack, EquipmentSlot equipmentSlot, HumanoidModel<?> original) {
            HumanoidModel<LivingEntity> replacement = (HumanoidModel)ModArmors.Client.ARMOR_MODELS.get(itemStack.m_41720_());
            if (replacement != null && replacement instanceof SniperGogglesModel gogglesModel) {
               gogglesModel.updateState(livingEntity);
            }

            return replacement != null ? replacement : original;
         }
      });
   }
}
