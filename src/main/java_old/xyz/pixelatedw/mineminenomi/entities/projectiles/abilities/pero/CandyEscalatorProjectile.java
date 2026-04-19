package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.pero;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.blockgen.BlockGenerators;
import xyz.pixelatedw.mineminenomi.api.blockgen.SimpleBlockPlacer;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.api.protection.DefaultProtectionRules;
import xyz.pixelatedw.mineminenomi.init.ModBlocks;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;

public class CandyEscalatorProjectile extends NuProjectileEntity {
   private final SimpleBlockPlacer placer;

   public CandyEscalatorProjectile(EntityType<? extends CandyEscalatorProjectile> type, Level world) {
      super(type, world);
      this.placer = (new SimpleBlockPlacer()).setBlock(((Block)ModBlocks.CANDY.get()).m_49966_()).setRule(DefaultProtectionRules.AIR).setSize(1);
   }

   public CandyEscalatorProjectile(Level world, LivingEntity player, IAbility ability) {
      super((EntityType)ModProjectiles.CANDY_ESCALATOR.get(), world, player, ability);
      this.placer = (new SimpleBlockPlacer()).setBlock(((Block)ModBlocks.CANDY.get()).m_49966_()).setRule(DefaultProtectionRules.AIR).setSize(1);
      this.setMaxLife(30);
      this.setPhysical();
      this.setPassThroughEntities();
      this.setPassThroughBlocks();
      this.addTickEvent(100, this::onTickEvent);
   }

   private void onTickEvent() {
      this.placer.generate(this.m_9236_(), this.m_20183_().m_6625_(2), BlockGenerators.CUBE);
   }
}
