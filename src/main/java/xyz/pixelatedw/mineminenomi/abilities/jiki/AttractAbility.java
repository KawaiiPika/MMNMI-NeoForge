package xyz.pixelatedw.mineminenomi.abilities.jiki;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

public class AttractAbility extends Ability {

    public AttractAbility() {
        super(ResourceLocation.fromNamespaceAndPath("mineminenomi", "jiki_jiki_no_mi"));
    }

    @Override
    protected void startUsing(LivingEntity entity) {
        // Continuous usage handled in onTick
    }

    @Override
    protected void stopUsing(LivingEntity entity) {
        this.startCooldown(entity, 60);
    }

    @Override
    public void onTick(LivingEntity entity, long duration) {
        if (duration > 100) {
            this.stop(entity);
            return;
        }
        if (duration % 5 != 0) return; // Optimize queries to every 5 ticks

        var aabb = entity.getBoundingBox().inflate(40.0);

        for (ItemEntity itemEntity : entity.level().getEntitiesOfClass(ItemEntity.class, aabb)) {
            // Check if item is magnetic (ideally via tags, assuming any armor/weapon for now)
            if (itemEntity.getItem().getItem() instanceof net.minecraft.world.item.ArmorItem || itemEntity.getItem().getItem() instanceof net.minecraft.world.item.TieredItem) {
                Vec3 pull = entity.position().add(0, 1.0, 0).subtract(itemEntity.position()).normalize().scale(0.6);
                itemEntity.push(pull.x, pull.y, pull.z);
                itemEntity.setNoPickUpDelay();
            }
        }

        for (LivingEntity living : entity.level().getEntitiesOfClass(LivingEntity.class, aabb, e -> e != entity)) {
            boolean hasArmor = false;
            for (var armor : living.getArmorSlots()) {
                if (!armor.isEmpty()) { hasArmor = true; break; }
            }
            if (hasArmor || (!living.getMainHandItem().isEmpty() && living.getMainHandItem().getItem() instanceof net.minecraft.world.item.TieredItem)) {
                Vec3 pull = entity.position().subtract(living.position()).normalize().scale(0.4);
                living.push(pull.x, pull.y, pull.z);
            }
        }
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.attract");
    }
}
