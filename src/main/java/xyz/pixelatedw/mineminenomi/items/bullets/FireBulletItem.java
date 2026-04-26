package xyz.pixelatedw.mineminenomi.items.bullets;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.entities.projectiles.FireBulletProjectile;
import xyz.pixelatedw.mineminenomi.items.BulletItem;

public class FireBulletItem extends BulletItem {
    public FireBulletItem() {
        super(new Item.Properties());
    }

    @Override
    public Object createProjectile(Level level, LivingEntity thrower) {
        return new FireBulletProjectile(level, thrower);
    }
}
