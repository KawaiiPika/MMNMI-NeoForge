package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.jiki;

import java.util.List;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import xyz.pixelatedw.mineminenomi.abilities.jiki.JikiHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;

public class PunkPistolProjectile extends NuProjectileEntity {
   private List<ItemStack> magneticItems;

   public PunkPistolProjectile(EntityType<? extends PunkPistolProjectile> type, Level world) {
      super(type, world);
   }

   public PunkPistolProjectile(Level world, LivingEntity player, List<ItemStack> items, IAbility ability) {
      super((EntityType)ModProjectiles.PUNK_PISTOL.get(), world, player, ability);
      this.setDamage(10.0F);
      this.magneticItems = items;
      this.addBlockHitEvent(100, this::onBlockImpactEvent);
   }

   private void onBlockImpactEvent(BlockHitResult result) {
      JikiHelper.dropComponentItems(this.m_9236_(), result.m_82450_(), this.magneticItems);
   }
}
