package xyz.pixelatedw.mineminenomi.items;

import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;

import java.util.List;

// WantedPosterPackage contains a random wanted poster
public class WantedPosterPackageItem extends Item {
    public WantedPosterPackageItem() {
        super(new Item.Properties().stacksTo(1));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);
        if (!world.isClientSide()) {
            ServerLevel serverLevel = (ServerLevel) world;
            ResourceKey<LootTable> lootTableKey = ResourceKey.create(Registries.LOOT_TABLE, ResourceLocation.fromNamespaceAndPath("mineminenomi", "gameplay/wanted_poster_package"));
            LootTable table = serverLevel.getServer().reloadableRegistries().getLootTable(lootTableKey);

            LootParams params = new LootParams.Builder(serverLevel)
                    .withParameter(LootContextParams.THIS_ENTITY, player)
                    .withParameter(LootContextParams.ORIGIN, player.position())
                    .create(LootContextParamSets.GIFT);

            List<ItemStack> generatedItems = table.getRandomItems(params);

            for (ItemStack stack : generatedItems) {
                if (!player.getInventory().add(stack)) {
                    player.drop(stack, false);
                }
            }

            if (!player.getAbilities().instabuild) {
                itemStack.shrink(1);
            }
        }
        return InteractionResultHolder.sidedSuccess(itemStack, world.isClientSide());
    }
}
