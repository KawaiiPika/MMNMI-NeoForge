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
        if (!entity.level().isClientSide) {
            for (var target : entity.level().getEntities(entity, entity.getBoundingBox().inflate(40.0))) {
                if (target instanceof ItemEntity itemEntity) {
                    // Pull item toward user
                    Vec3 pull = entity.position().add(0, 1.5, 0).subtract(itemEntity.position()).normalize().scale(0.8);
                    itemEntity.setDeltaMovement(itemEntity.getDeltaMovement().add(pull));
                    itemEntity.setNoPickUpDelay();
                } else if (target instanceof LivingEntity living) {
                    // For simplicity in this port, we pull the entity slightly if they are wearing armor
                    boolean hasArmor = false;
                    for (var armor : living.getArmorSlots()) {
                        if (!armor.isEmpty()) { hasArmor = true; break; }
                    }
                    if (hasArmor) {
                        Vec3 pull = entity.position().subtract(living.position()).normalize().scale(0.5);
                        living.setDeltaMovement(living.getDeltaMovement().add(pull));
                    }
                }
            }
            this.startCooldown(entity, 60);
        }
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.attract");
    }
}
