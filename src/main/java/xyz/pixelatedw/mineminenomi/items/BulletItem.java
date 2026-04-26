package xyz.pixelatedw.mineminenomi.items;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

// BulletItem is an abstract base for projectile items. NuProjectileEntity will be ported in Phase 3.
public abstract class BulletItem extends Item implements net.minecraft.world.item.ProjectileItem {
    public BulletItem(Item.Properties props) {
        super(props);
    }

    // TODO: Phase 3 - Restore createProjectile once NuProjectileEntity is ported
    public abstract Object createProjectile(Level level, LivingEntity shooter);

    @Override
    public net.minecraft.world.entity.projectile.Projectile asProjectile(Level level, net.minecraft.core.Position pos, ItemStack stack, net.minecraft.core.Direction direction) {
        Object proj = createProjectile(level, null);
        if (proj instanceof net.minecraft.world.entity.projectile.Projectile p) {
            p.setPos(pos.x(), pos.y(), pos.z());
            return p;
        }
        return null; // Should not happen in typical usage
    }

}
