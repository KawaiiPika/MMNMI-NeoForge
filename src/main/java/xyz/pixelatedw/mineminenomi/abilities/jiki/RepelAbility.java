package xyz.pixelatedw.mineminenomi.abilities.jiki;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

public class RepelAbility extends Ability {

    public RepelAbility() {
        super(ResourceLocation.fromNamespaceAndPath("mineminenomi", "jiki_jiki_no_mi"));
    }

    @Override
    protected void startUsing(LivingEntity entity) {
        // Handled in onTick
    }

    @Override
    protected void stopUsing(LivingEntity entity) {
        this.startCooldown(entity, 100);
    }

    @Override
    public void onTick(LivingEntity entity, long duration) {
        if (duration > 100) {
            this.stop(entity);
            return;
        }

        if (duration % 5 != 0) return;

        var aabb = entity.getBoundingBox().inflate(20.0);

        for (ItemEntity itemEntity : entity.level().getEntitiesOfClass(ItemEntity.class, aabb)) {
            if (itemEntity.getItem().getItem() instanceof net.minecraft.world.item.ArmorItem || itemEntity.getItem().getItem() instanceof net.minecraft.world.item.TieredItem) {
                Vec3 push = itemEntity.position().subtract(entity.position()).normalize().scale(1.2);
                itemEntity.push(push.x, 0.2, push.z);
                itemEntity.setNoPickUpDelay();
            }
        }

        for (LivingEntity living : entity.level().getEntitiesOfClass(LivingEntity.class, aabb, e -> e != entity)) {
            boolean hasArmor = false;
            for (var armor : living.getArmorSlots()) {
                if (!armor.isEmpty()) { hasArmor = true; break; }
            }
            if (hasArmor || (!living.getMainHandItem().isEmpty() && living.getMainHandItem().getItem() instanceof net.minecraft.world.item.TieredItem)) {
                Vec3 push = living.position().subtract(entity.position()).normalize().scale(0.8);
                living.push(push.x, 0.2, push.z);
            }
        }
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.repel");
    }
}
