package xyz.pixelatedw.mineminenomi.abilities.bari;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

/** Bari Bari no Pistol — fires a hardened barrier fragment as a projectile. */
public class BariBarriNoPistolAbility extends Ability {
    private static final ResourceLocation FRUIT = ResourceLocation.fromNamespaceAndPath("mineminenomi", "bari_bari_no_mi");
    public BariBarriNoPistolAbility() { super(FRUIT); }

    @Override
    protected void startUsing(LivingEntity entity) {
        Vec3 look = entity.getLookAngle();
        // Barrier shard projectile — hits the first entity in look direction
        for (LivingEntity living : entity.level().getEntitiesOfClass(LivingEntity.class, entity.getBoundingBox().inflate(2.0).move(look.scale(15.0)))) {
            if (living != entity) {
                living.hurt(entity.damageSources().mobAttack(entity), 10.0F);
                living.setDeltaMovement(look.scale(2.0));
                living.hurtMarked = true;
                return;
            }
        }
    }

    @Override
    public Component getDisplayName() { return Component.translatable("ability.mineminenomi.bari_bari_no_pistol"); }
}
