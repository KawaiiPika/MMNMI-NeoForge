package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.ori;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.EntityHitResult;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponentKey;
import xyz.pixelatedw.mineminenomi.api.blockgen.BlockGenerators;
import xyz.pixelatedw.mineminenomi.api.blockgen.SimpleBlockPlacer;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.api.protection.DefaultProtectionRules;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;
import xyz.pixelatedw.mineminenomi.init.ModBlocks;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;

public class AwaseBaoriProjectile extends NuProjectileEntity {
   public AwaseBaoriProjectile(EntityType<? extends AwaseBaoriProjectile> type, Level world) {
      super(type, world);
   }

   public AwaseBaoriProjectile(Level world, LivingEntity player, IAbility parent) {
      super((EntityType)ModProjectiles.AWASE_BAORI.get(), world, player, parent);
      this.setDamage(6.0F);
      this.setMaxLife(10);
      this.addEntityHitEvent(100, this::onEntityImpactEvent);
   }

   private void onEntityImpactEvent(EntityHitResult result) {
      this.getParent().ifPresent((abl) -> abl.getComponent((AbilityComponentKey)ModAbilityComponents.BLOCK_TRACKER.get()).ifPresent((comp) -> {
            SimpleBlockPlacer placer = (new SimpleBlockPlacer()).setBlock(((Block)ModBlocks.ORI_BARS.get()).m_49966_()).setSize(2).setHollow().setRule(DefaultProtectionRules.AIR_FOLIAGE_LIQUID).setFlag(259);
            placer.generate(this.m_9236_(), this.m_20183_(), BlockGenerators.CUBE);
            comp.addPositions(placer.getPlacedPositions());
         }));
   }
}
