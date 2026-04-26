package xyz.pixelatedw.mineminenomi.abilities.doa;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
public class KaitenDoorAbility extends Ability {
    private static final ResourceLocation FRUIT = ResourceLocation.fromNamespaceAndPath("mineminenomi", "doa_doa_no_mi");
    public KaitenDoorAbility() { super(FRUIT); }
    @Override protected void startUsing(LivingEntity entity) {}
    @Override public float onAttack(LivingEntity entity, LivingEntity target, DamageSource source, float amount) {
        if (this.isUsing(entity)) {
            if (!entity.level().isClientSide) {
                target.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 100, 1, false, false));
                target.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 100, 0, false, false));
                this.startCooldown(entity, 200);
            }
            this.stop(entity);
            return 4.0F;
        }
        return amount;
    }
    @Override public Component getDisplayName() { return Component.translatable("ability.mineminenomi.kaiten_door"); }
}
