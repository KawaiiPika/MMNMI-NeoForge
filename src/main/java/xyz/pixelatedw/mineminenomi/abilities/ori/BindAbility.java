package xyz.pixelatedw.mineminenomi.abilities.ori;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
public class BindAbility extends Ability {
    private static final ResourceLocation FRUIT = ResourceLocation.fromNamespaceAndPath("mineminenomi", "ori_ori_no_mi");
    public BindAbility() { super(FRUIT); }
    @Override protected void startUsing(LivingEntity entity) {}
    @Override public float onAttack(LivingEntity entity, LivingEntity target, DamageSource source, float amount) {
        if (this.isUsing(entity)) {
            if (!entity.level().isClientSide) {
                target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 200, 0, false, true));
                this.startCooldown(entity, 100);
            }
            this.stop(entity);
            return 4.0F;
        }
        return amount;
    }
    @Override public Component getDisplayName() { return Component.translatable("ability.mineminenomi.bind"); }
}
