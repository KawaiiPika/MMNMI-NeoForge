package xyz.pixelatedw.mineminenomi.abilities.karu;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import xyz.pixelatedw.mineminenomi.api.abilities.zoan.ZoanAbility;
import xyz.pixelatedw.mineminenomi.init.ModDataAttachments;

public class IngaZarashiAbility extends ZoanAbility {

    public IngaZarashiAbility() {
        super(ResourceLocation.fromNamespaceAndPath("mineminenomi", "karu_karu_no_mi"));
    }

    @Override
    public ResourceLocation getMorphModelId() {
        return ResourceLocation.fromNamespaceAndPath("mineminenomi", "inga_zarashi");
    }

    @Override
    public void onTick(LivingEntity entity, long duration) {
        if (!entity.level().isClientSide && duration % 20 == 0) {
            int karma = entity.getData(ModDataAttachments.KARMA_VALUE);
            int newKarma = Math.max(0, karma - (karma / 100)); // Decay karma slowly while active
            entity.setData(ModDataAttachments.KARMA_VALUE, newKarma);
        }
    }

    private float getKarma(LivingEntity entity) {
        if (entity == null) return 0;
        Integer karma = entity.getData(ModDataAttachments.KARMA_VALUE);
        return karma != null ? karma.floatValue() : 0;
    }

    @Override
    public double getScaleModifier() {
        return 1.5; // Fixed scale because parameter isn't supported,
                    // ideally we'd pass entity, but ZoanAbility doesn't.
    }

    @Override
    public double getHealthModifier() {
        return 8.0;
    }

    @Override
    public double getDamageModifier() {
        return 10.0;
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.inga_zarashi");
    }
}
