package xyz.pixelatedw.mineminenomi.abilities.kira;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
public class CabochonKnuckleAbility extends Ability {
    private static final ResourceLocation FRUIT = ResourceLocation.fromNamespaceAndPath("mineminenomi", "kira_kira_no_mi");
    public CabochonKnuckleAbility() { super(FRUIT); }
    @Override public xyz.pixelatedw.mineminenomi.api.util.Result canUse(LivingEntity entity) {
        xyz.pixelatedw.mineminenomi.api.util.Result superResult = super.canUse(entity);
        if (superResult.isFail()) return superResult;
        xyz.pixelatedw.mineminenomi.data.entity.PlayerStats stats = xyz.pixelatedw.mineminenomi.data.entity.PlayerStats.get(entity);
        if (stats != null) {
            ResourceLocation diamondId = xyz.pixelatedw.mineminenomi.init.ModAbilities.REGISTRY.getKey(xyz.pixelatedw.mineminenomi.init.ModAbilities.REGISTRY.get(ResourceLocation.fromNamespaceAndPath("mineminenomi", "diamond_body")));
            if (diamondId != null && !stats.isAbilityActive(diamondId.toString())) { return xyz.pixelatedw.mineminenomi.api.util.Result.fail(Component.translatable("ability.mineminenomi.error.requires_diamond_body")); }
        }
        return xyz.pixelatedw.mineminenomi.api.util.Result.success();
    }
    @Override protected void startUsing(LivingEntity entity) {}
    @Override public float onAttack(LivingEntity entity, LivingEntity target, DamageSource source, float amount) {
        if (this.isUsing(entity)) {
            if (!entity.level().isClientSide) { this.startCooldown(entity, 100); }
            this.stop(entity);
            return 10.0F;
        }
        return amount;
    }
    @Override public Component getDisplayName() { return Component.translatable("ability.mineminenomi.cabochon_knuckle"); }
}
