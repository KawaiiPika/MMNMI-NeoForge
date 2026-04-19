package xyz.pixelatedw.mineminenomi.effects;

import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.data.entity.devilfruit.DevilFruitCapability;
import xyz.pixelatedw.mineminenomi.data.entity.devilfruit.IDevilFruit;
import xyz.pixelatedw.mineminenomi.init.ModFruits;

public class WetEffect extends BaseEffect {
   public WetEffect() {
      super(MobEffectCategory.NEUTRAL, 0);
   }

   public void m_6742_(LivingEntity entity, int amp) {
      if (entity.m_6084_()) {
         int timer = entity.m_21124_(this).m_19557_();
         IDevilFruit props = (IDevilFruit)DevilFruitCapability.get(entity).orElse((Object)null);
         if (props == null) {
            return;
         }

         if (props.hasDevilFruit(ModFruits.SUNA_SUNA_NO_MI)) {
            AbilityHelper.disableAbilities(entity, timer, (abl) -> abl.getCore().getCategory() == AbilityCategory.DEVIL_FRUITS);
         }
      }

   }
}
