package xyz.pixelatedw.mineminenomi.items;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;

// BulletItem is an abstract base for projectile items. NuProjectileEntity will be ported in Phase 3.
public abstract class BulletItem extends Item {
    public BulletItem(Item.Properties props) {
        super(props);
    }

    // TODO: Phase 3 - Restore createProjectile once NuProjectileEntity is ported
    public abstract Object createProjectile(Level level, LivingEntity shooter);
}
