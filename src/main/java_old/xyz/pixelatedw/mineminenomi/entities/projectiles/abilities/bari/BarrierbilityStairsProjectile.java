package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.bari;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import xyz.pixelatedw.mineminenomi.abilities.bari.BarrierbilityStairsAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.blockgen.BlockGenerators;
import xyz.pixelatedw.mineminenomi.api.blockgen.SimpleBlockPlacer;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.api.protection.BlockProtectionRule;
import xyz.pixelatedw.mineminenomi.api.protection.DefaultProtectionRules;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityCapability;
import xyz.pixelatedw.mineminenomi.data.entity.ability.IAbilityData;
import xyz.pixelatedw.mineminenomi.init.ModBlocks;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;

public class BarrierbilityStairsProjectile extends NuProjectileEntity {
   private static final BlockProtectionRule GRIEF_RULE;

   public BarrierbilityStairsProjectile(EntityType<BarrierbilityStairsProjectile> type, Level world) {
      super(type, world);
   }

   public BarrierbilityStairsProjectile(Level world, LivingEntity player, IAbility parent) {
      super((EntityType)ModProjectiles.BARRIERBILITY_STAIRS.get(), world, player, parent);
      this.setMaxLife(40);
      this.setPhysical();
      this.setPassThroughEntities();
      this.setPassThroughBlocks();
      this.addTickEvent(100, this::tickEvent);
   }

   private void tickEvent() {
      if (this.getOwner() != null) {
         IAbilityData abilityProps = (IAbilityData)AbilityCapability.get(this.getOwner()).orElse((Object)null);
         if (abilityProps != null) {
            BarrierbilityStairsAbility ability = (BarrierbilityStairsAbility)abilityProps.getEquippedAbility((AbilityCore)BarrierbilityStairsAbility.INSTANCE.get());
            if (ability != null && ability.isContinuous()) {
               SimpleBlockPlacer placer = (new SimpleBlockPlacer()).setBlock(((Block)ModBlocks.BARRIER.get()).m_49966_()).setRule(GRIEF_RULE).setSize(1);
               placer.generate(this.m_9236_(), this.m_20183_().m_6625_(2), BlockGenerators.CUBE);
               ability.fillBlocksList(placer.getPlacedPositions());
            }
         }

      }
   }

   static {
      GRIEF_RULE = (new BlockProtectionRule.Builder(new BlockProtectionRule[]{DefaultProtectionRules.AIR})).build();
   }
}
