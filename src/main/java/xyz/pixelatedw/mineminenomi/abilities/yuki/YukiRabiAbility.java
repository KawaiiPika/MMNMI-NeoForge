package xyz.pixelatedw.mineminenomi.abilities.yuki;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.yuki.YukiRabiProjectile;

public class YukiRabiAbility extends Ability {

    private static final int COOLDOWN = 120;
    private static final int PROJECTILES = 6;
    private static final int TICK_DELAY = 3;

    public YukiRabiAbility() {
        super(ResourceLocation.fromNamespaceAndPath("mineminenomi", "yuki_yuki_no_mi"));
    }

    @Override
    protected void startUsing(LivingEntity entity) {}

    @Override
    protected void onTick(LivingEntity entity, long duration) {
        if (!entity.level().isClientSide && duration % TICK_DELAY == 0) {
            int fired = (int) (duration / TICK_DELAY);
            if (fired < PROJECTILES) {
                YukiRabiProjectile proj = new YukiRabiProjectile(entity.level(), entity);
                proj.shootFromRotation(entity, entity.getXRot(), entity.getYRot(), 0.0F, 2.0F, 2.0F);
                entity.level().addFreshEntity(proj);
            } else {
                this.startCooldown(entity, COOLDOWN);
                this.stop(entity);
            }
        }
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.yuki_rabi");
    }
}
