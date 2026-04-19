package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.hana;

import com.google.common.collect.ImmutableList;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import xyz.pixelatedw.mineminenomi.api.NuWorld;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.damagesources.TargetPredicate;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.TargetHelper;
import xyz.pixelatedw.mineminenomi.api.protection.DefaultProtectionRules;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;

public class HanaFeetEntity extends NuProjectileEntity {
   private static final TargetPredicate TARGET_PREDICATE = (new TargetPredicate()).testEnemyFaction();

   public HanaFeetEntity(EntityType<? extends HanaFeetEntity> type, Level world) {
      super(type, world);
   }

   public HanaFeetEntity(Level world, LivingEntity player, IAbility ability) {
      super((EntityType)ModProjectiles.HANA_FEET.get(), world, player, ability);
      this.setMaxLife(35);
      this.setDamage(10.0F);
      this.setPassThroughEntities();
      super.setFist();
      this.addBlockHitEvent(100, this::blockImpactEvent);
   }

   public void blockImpactEvent(BlockHitResult result) {
      if (this.getOwner() != null) {
         List<LivingEntity> list = TargetHelper.<LivingEntity>getEntitiesInArea(this.m_9236_(), this.getOwner(), result.m_82425_(), (double)5.0F, TARGET_PREDICATE, LivingEntity.class);
         list.remove(this.getOwner());

         for(LivingEntity target : list) {
            if (target.m_6469_(this.getDamageSource(), this.getDamage())) {
               AbilityHelper.setDeltaMovement(target, target.m_20184_().m_82520_((double)0.0F, (double)1.0F, (double)0.0F));
            }
         }

         for(BlockPos pos : WyHelper.getNearbyBlocks(this.m_20183_(), this.m_9236_(), 5, (p) -> DefaultProtectionRules.FOLIAGE.isApproved(this.m_9236_().m_8055_(p)), ImmutableList.of(Blocks.f_50016_))) {
            BlockState blockState = this.m_9236_().m_8055_(pos);

            for(int i = 0; i < 150; ++i) {
               double offsetX = WyHelper.randomDouble();
               double offsetY = WyHelper.randomDouble();
               double offsetZ = WyHelper.randomDouble();
               ((ServerLevel)this.m_9236_()).m_8767_(new BlockParticleOption(ParticleTypes.f_123794_, blockState), (double)pos.m_123341_() + offsetX, (double)pos.m_123342_() + offsetY, (double)pos.m_123343_() + offsetZ, 1, (double)0.0F, (double)0.0F, (double)0.0F, (double)0.0F);
            }

            NuWorld.setBlockState((Entity)this.getOwner(), pos, Blocks.f_50016_.m_49966_(), 3, DefaultProtectionRules.FOLIAGE);
         }

         BlockState blockState = this.m_9236_().m_8055_(result.m_82425_());

         for(int i = 0; i < 150; ++i) {
            double x = WyHelper.randomDouble();
            double z = WyHelper.randomDouble();
            ((ServerLevel)this.m_9236_()).m_8767_(new BlockParticleOption(ParticleTypes.f_123794_, blockState), (double)result.m_82425_().m_123341_() + WyHelper.randomWithRange(-3, 3) + x, (double)(result.m_82425_().m_123342_() + 1), (double)result.m_82425_().m_123343_() + WyHelper.randomWithRange(-3, 3) + z, 1, (double)0.0F, (double)0.0F, (double)0.0F, (double)0.0F);
         }

      }
   }
}
