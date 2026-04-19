package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.kage;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;

public class TsunoTokagePillarEntity extends NuProjectileEntity {
   public TsunoTokagePillarEntity(EntityType<TsunoTokagePillarEntity> type, Level world) {
      super(type, world);
   }

   public TsunoTokagePillarEntity(Level world, LivingEntity player, IAbility ability) {
      super((EntityType)ModProjectiles.TSUNO_TOKAGE.get(), world, player, ability);
      this.setDamage(30.0F);
      this.setMaxLife(8);
      this.setPassThroughEntities();
      this.setPassThroughBlocks();
      this.setEntityCollisionSize((double)3.0F, (double)5.0F, (double)3.0F);
   }
}
