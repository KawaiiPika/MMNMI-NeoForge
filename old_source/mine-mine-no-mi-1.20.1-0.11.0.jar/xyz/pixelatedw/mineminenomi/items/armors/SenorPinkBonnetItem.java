package xyz.pixelatedw.mineminenomi.items.armors;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ArmorItem.Type;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import xyz.pixelatedw.mineminenomi.api.items.IArmorVisibility;
import xyz.pixelatedw.mineminenomi.init.ModMaterials;

public class SenorPinkBonnetItem extends ModArmorItem implements IArmorVisibility<LivingEntity, HumanoidModel<LivingEntity>> {
   public SenorPinkBonnetItem() {
      super(ModMaterials.SIMPLE_LEATHER_MATERIAL, "senorpink_bonnet", Type.HELMET);
   }

   @OnlyIn(Dist.CLIENT)
   public void setPartVisibility(HumanoidModel<LivingEntity> model, ItemStack itemStack, EquipmentSlot slot) {
      model.m_8009_(false);
      if (slot == EquipmentSlot.HEAD) {
         model.f_102808_.f_104207_ = true;
         model.f_102809_.f_104207_ = true;
         model.f_102810_.f_104207_ = true;
         model.f_102811_.f_104207_ = true;
         model.f_102812_.f_104207_ = true;
      }

   }
}
