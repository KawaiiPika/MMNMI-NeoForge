package xyz.pixelatedw.mineminenomi.items.bullets;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.items.BulletItem;

public class NormalBulletItem extends BulletItem {
    public NormalBulletItem() {
        super(new Item.Properties());
    }

    @Override
    public Object createProjectile(Level level, LivingEntity shooter) {
        // TODO: Phase 3 - Return NuProjectileEntity (NormalBulletProjectile) when ported
        return null;
    }
}
