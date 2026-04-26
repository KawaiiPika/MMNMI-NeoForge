package xyz.pixelatedw.mineminenomi.abilities.beta;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
public class BetaBetaChainAbility extends Ability {
    private static final ResourceLocation FRUIT = ResourceLocation.fromNamespaceAndPath("mineminenomi", "beta_beta_no_mi");
    public BetaBetaChainAbility() { super(FRUIT); }
    @Override protected void startUsing(LivingEntity entity) {}
    @Override protected void onTick(LivingEntity entity, long duration) { if (getDuration(entity) >= 600) { this.stop(entity); } }
    @Override protected void stopUsing(LivingEntity entity) {
        if (!entity.level().isClientSide) { entity.addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, 40, 1)); this.startCooldown(entity, 120); }
    }
    @Override public Component getDisplayName() { return Component.translatable("ability.mineminenomi.beta_beta_chain"); }
}
