package xyz.pixelatedw.mineminenomi.abilities.yuki;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

public class YukiRabiAbility extends Ability {
    private static final ResourceLocation FRUIT = ResourceLocation.fromNamespaceAndPath("mineminenomi", "yuki_yuki_no_mi");
    private static final int COOLDOWN = 120;

    public YukiRabiAbility() {
        super(FRUIT);
    }

    @Override
    protected void startUsing(LivingEntity entity) {}

    @Override
    public void tick(LivingEntity entity) {
        super.tick(entity);
        if (!entity.level().isClientSide() && this.isUsing(entity)) {
            long timeUsed = this.getDuration(entity);
            if (timeUsed % 6 == 0) {
                xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.yuki.YukiRabiProjectile projectile = new xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.yuki.YukiRabiProjectile(entity.level(), entity, this);
                projectile.shootFromRotation(entity, entity.getXRot(), entity.getYRot(), 0.0F, 2.0F, 2.0F);
                entity.level().addFreshEntity(projectile);
            }
        }
    }

    @Override
    protected void stopUsing(LivingEntity entity) {
        xyz.pixelatedw.mineminenomi.data.entity.PlayerStats stats = xyz.pixelatedw.mineminenomi.data.entity.PlayerStats.get(entity);
        if (stats != null && getAbilityId() != null) {
            stats.setAbilityCooldown(getAbilityId().toString(), COOLDOWN, entity.level().getGameTime());
        }
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.yuki_rabi");
    }
}
