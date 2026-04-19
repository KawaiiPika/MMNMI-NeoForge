package xyz.pixelatedw.mineminenomi.items;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.RegistryObject;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModItems;

public class HandcuffsItem extends Item {
   private static final int HANDCUFF_TIME = 2400;
   private RegistryObject<MobEffect> handcuffEffect;

   public HandcuffsItem(RegistryObject<MobEffect> handcuffed) {
      super((new Item.Properties()).m_41487_(1));
      this.handcuffEffect = handcuffed;
   }

   public RegistryObject<MobEffect> getEffect() {
      return this.handcuffEffect;
   }

   public boolean m_7579_(ItemStack stack, LivingEntity target, LivingEntity attacker) {
      MobEffectInstance inst = target.m_21124_((MobEffect)ModEffects.UNCONSCIOUS.get());
      if (inst != null && inst.m_19564_() > 0) {
         handleHandcuffActivation(stack, target, 2.0F);
         return false;
      } else {
         return true;
      }
   }

   public static boolean handleHandcuffActivation(ItemStack stack, LivingEntity target, float damage) {
      if (!(damage <= 0.0F) && !(target.m_21223_() - damage > 0.0F)) {
         if (!stack.m_41619_() && stack.m_41720_() instanceof HandcuffsItem) {
            MobEffectInstance instance = new MobEffectInstance((MobEffect)ModEffects.HANDCUFFED.get(), 2400, 0);
            target.m_21153_(2.0F);
            if (stack.m_41720_() == ModItems.NORMAL_HANDCUFFS.get()) {
               instance = new MobEffectInstance((MobEffect)ModEffects.HANDCUFFED.get(), 2400, 0);
            } else if (stack.m_41720_() == ModItems.KAIROSEKI_HANDCUFFS.get()) {
               instance = new MobEffectInstance((MobEffect)ModEffects.HANDCUFFED_KAIROSEKI.get(), 2400, 0);
            } else if (stack.m_41720_() == ModItems.EXPLOSIVE_HANDCUFFS.get()) {
               instance = new MobEffectInstance((MobEffect)ModEffects.HANDCUFFED_EXPLOSIVE.get(), 2400, 0);
            }

            target.m_7292_(instance);
            target.f_19802_ = 100;
            stack.m_41774_(1);
            return true;
         } else {
            return false;
         }
      } else {
         return false;
      }
   }
}
