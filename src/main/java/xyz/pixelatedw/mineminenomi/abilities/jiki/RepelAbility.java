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
        if (!entity.level().isClientSide) {
            for (var target : entity.level().getEntities(entity, entity.getBoundingBox().inflate(20.0))) {
                if (target instanceof ItemEntity itemEntity) {
                    Vec3 push = itemEntity.position().subtract(entity.position()).normalize().scale(2.0);
                    itemEntity.setDeltaMovement(push.x, 0.5, push.z);
                    itemEntity.setNoPickUpDelay();
                } else if (target instanceof LivingEntity living) {
                    // Similar to Attract, simple push logic for armor wearers in this port
                    boolean hasArmor = false;
                    for (var armor : living.getArmorSlots()) {
                        if (!armor.isEmpty()) { hasArmor = true; break; }
                    }
                    if (hasArmor) {
                        Vec3 push = living.position().subtract(entity.position()).normalize().scale(2.0);
                        living.setDeltaMovement(push.x, 0.5, push.z);
                    }
                }
            }
            this.startCooldown(entity, 100);
        }
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.repel");
    }
}
