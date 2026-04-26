package xyz.pixelatedw.mineminenomi.abilities.beta;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
public class BetaImmunityAbility extends Ability {
    private static final ResourceLocation FRUIT = ResourceLocation.fromNamespaceAndPath("mineminenomi", "beta_beta_no_mi");
    public BetaImmunityAbility() { super(FRUIT); }
    @Override public boolean isPassive() { return true; }
    @Override protected void startUsing(LivingEntity entity) {}
    @Override protected void onTick(LivingEntity entity, long duration) {
        if (!entity.level().isClientSide) {
            entity.removeEffect(MobEffects.CONFUSION);
            entity.removeEffect(MobEffects.MOVEMENT_SLOWDOWN);
        }
    }
    @Override public Component getDisplayName() { return Component.translatable("ability.mineminenomi.beta_immunities"); }
}
