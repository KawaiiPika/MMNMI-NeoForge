package xyz.pixelatedw.mineminenomi.abilities.karu;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

public class KarmaAbility extends Ability {

    public KarmaAbility() {
        super(ResourceLocation.fromNamespaceAndPath("mineminenomi", "karu_karu_no_mi"));
    }

    @Override
    public boolean isPassive() {
        return true;
    }

    @Override
    public void onDamageTake(LivingEntity entity, net.minecraft.world.damagesource.DamageSource source, float amount) {
        // Handled by LivingDamageEvent.Post -> onDamageTaken
    }

    @Override
    public void onDamageTaken(LivingEntity entity, net.minecraft.world.damagesource.DamageSource source) {
        if (!entity.level().isClientSide && !source.is(net.minecraft.world.damagesource.DamageTypes.IN_WALL) && !source.is(net.minecraft.world.damagesource.DamageTypes.FELL_OUT_OF_WORLD)) {
            int currentKarma = entity.getData(xyz.pixelatedw.mineminenomi.init.ModDataAttachments.KARMA_VALUE);
            int newKarma = Math.min(100, currentKarma + 5);
            entity.setData(xyz.pixelatedw.mineminenomi.init.ModDataAttachments.KARMA_VALUE, newKarma);
        }
    }

    @Override
    protected void startUsing(LivingEntity entity) {}

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.karma");
    }
}
