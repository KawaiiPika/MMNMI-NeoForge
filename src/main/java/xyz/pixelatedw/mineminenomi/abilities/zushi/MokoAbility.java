package xyz.pixelatedw.mineminenomi.abilities.zushi;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Arrow;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

public class MokoAbility extends Ability {
    private static final int COOLDOWN = 280;
    private int shotsFired = 0;

    public MokoAbility() {
        super(ResourceLocation.fromNamespaceAndPath("mineminenomi", "zushi_zushi_no_mi"));
    }

    @Override
    protected void startUsing(LivingEntity entity) {
        this.shotsFired = 0;
    }

    @Override
    protected void onTick(LivingEntity entity, long duration) {
        if (!entity.level().isClientSide) {
            if (this.shotsFired < 4 && duration % 6 == 0) {
                Arrow proj = new Arrow(entity.level(), entity, entity.getItemInHand(entity.getUsedItemHand()), null);
                proj.setOwner(entity);
                proj.shootFromRotation(entity, entity.getXRot(), entity.getYRot(), 0.0F, 3.0F, 2.0F);
                entity.level().addFreshEntity(proj);
                this.shotsFired++;
            }
            if (this.shotsFired >= 4) {
                this.stop(entity);
            }
        }
    }

    @Override
    protected void stopUsing(LivingEntity entity) {
        this.startCooldown(entity, COOLDOWN);
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.moko");
    }
}
