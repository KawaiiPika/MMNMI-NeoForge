package xyz.pixelatedw.mineminenomi.loot;

import com.mojang.serialization.MapCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import xyz.pixelatedw.mineminenomi.api.entities.WantedPosterData;
import xyz.pixelatedw.mineminenomi.init.ModLootFunctions;

import java.util.List;

public class SetWantedPosterDataFunction extends LootItemConditionalFunction {
    public static final MapCodec<SetWantedPosterDataFunction> CODEC = com.mojang.serialization.codecs.RecordCodecBuilder.mapCodec(
            instance -> commonFields(instance).apply(instance, SetWantedPosterDataFunction::new)
    );

    protected SetWantedPosterDataFunction(List<LootItemCondition> conditions) {
        super(conditions);
    }

    @Override
    public LootItemFunctionType<SetWantedPosterDataFunction> getType() {
        return ModLootFunctions.SET_WANTED_POSTER_DATA.get();
    }

    @Override
    protected ItemStack run(ItemStack stack, LootContext context) {
        // TODO: we need to populate dynamic wanted poster data using legacy logic (random bounty from player or FactionsWorldData etc)
        // Since FactionsWorldData does exist, we can use that.
        // For Phase 3, we just use a generic bounty of 10,000 for now.
        // A complete port would recreate the spawnNotoriousPosters, spawnChallengePosters, spawnPlayerPosters from legacy

        xyz.pixelatedw.mineminenomi.data.world.FactionsWorldData factionsData = xyz.pixelatedw.mineminenomi.data.world.FactionsWorldData.get();
        if (factionsData != null) {
            Object[] randomBounty = factionsData.getRandomBounty();
            if (randomBounty != null && randomBounty.length == 2 && randomBounty[0] instanceof java.util.UUID uuid) {
                long bounty = (Long) randomBounty[1];
                net.minecraft.server.level.ServerPlayer player = context.getLevel().getServer().getPlayerList().getPlayer(uuid);
                if (player != null) {
                    WantedPosterData data = new WantedPosterData();
                    // Legacy used: new WantedPosterData(player, bounty)
                    // Let's create an empty one and apply custom data since WantedPosterData has limited args right now
                    net.minecraft.world.item.component.CustomData.update(net.minecraft.core.component.DataComponents.CUSTOM_DATA, stack, tag -> {
                        tag.put("WPData", data.write()); // Since write() returns empty right now, this sets it up for phase 3
                    });
                    return stack;
                }
            }
        }

        WantedPosterData data = new WantedPosterData();
        net.minecraft.world.item.component.CustomData.update(net.minecraft.core.component.DataComponents.CUSTOM_DATA, stack, tag -> {
            tag.put("WPData", data.write());
        });

        return stack;
    }

    public static LootItemConditionalFunction.Builder<?> builder() {
        return simpleBuilder(SetWantedPosterDataFunction::new);
    }
}