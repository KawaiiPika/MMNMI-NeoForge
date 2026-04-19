package xyz.pixelatedw.mineminenomi.blocks;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.material.MapColor;
import xyz.pixelatedw.mineminenomi.abilities.suna.DesertGirasoleAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityCapability;
import xyz.pixelatedw.mineminenomi.data.entity.devilfruit.DevilFruitCapability;
import xyz.pixelatedw.mineminenomi.init.ModFruits;

public class SunaSandBlock extends TrapAbilityBlock {
   public SunaSandBlock() {
      super(Properties.m_284310_().m_284180_(MapColor.f_283761_).m_60910_().m_222994_().m_60913_(5.0F, 3.0F));
      this.setDamageDealt(4);
      this.setHorizontalFallSpeed(0.3);
      this.setVerticalFallSpeed(0.15);
      this.setPotionEffect(() -> new MobEffectInstance(MobEffects.f_19599_, 80, 1, false, false, false));
   }

   public boolean canWalkOn(LivingEntity entity) {
      boolean hasFruit = DevilFruitCapability.hasDevilFruit(entity, ModFruits.SUNA_SUNA_NO_MI);
      boolean hasAbility = AbilityCapability.hasUnlockedAbility(entity, (AbilityCore)DesertGirasoleAbility.INSTANCE.get());
      return hasFruit || hasAbility;
   }
}
