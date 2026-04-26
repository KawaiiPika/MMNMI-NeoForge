package xyz.pixelatedw.mineminenomi.items;

import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileItem;
import net.minecraft.world.level.Level;

// BulletItem is an abstract base for projectile items. NuProjectileEntity will be ported in Phase 3.
public abstract class BulletItem extends Item implements ProjectileItem {
    public BulletItem(Item.Properties props) {
        super(props);
    }

    // TODO: Phase 3 - Restore createProjectile once NuProjectileEntity is ported
    public abstract Object createProjectile(Level level, LivingEntity shooter);

    @Override
    public Projectile asProjectile(Level level, Position pos, ItemStack stack, Direction direction) {
        // Fallback or specific default behavior for dispenser usage
        return null; // Will be properly implemented in Phase 3
    }
}
