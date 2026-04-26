package xyz.pixelatedw.mineminenomi.items.bullets;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.entities.projectiles.CannonBallProjectile;
import xyz.pixelatedw.mineminenomi.items.BulletItem;
import xyz.pixelatedw.mineminenomi.api.entities.projectiles.NuProjectileEntity;

public class CannonBallItem extends BulletItem {
    public CannonBallItem() {
        super(new Item.Properties());
    }

    @Override
    public Object createProjectile(Level level, LivingEntity thrower) {
        return new CannonBallProjectile(level, thrower);
    }
}
