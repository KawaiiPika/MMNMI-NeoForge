package xyz.pixelatedw.mineminenomi.items.bullets;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.items.BulletItem;

public class ExplodingBulletItem extends BulletItem {
    public ExplodingBulletItem() {
        super(new Item.Properties());
    }

    @Override
    public Object createProjectile(Level level, LivingEntity shooter) {
        // TODO: Phase 3 - Return NuProjectileEntity (ExplodingBulletProjectile) when ported
        return null;
    }
}
