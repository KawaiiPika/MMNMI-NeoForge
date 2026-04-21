package xyz.pixelatedw.mineminenomi.items;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Set;

// AkumaNoMiBoxItem opens to give a random devil fruit from its loot table tier.
// Loot table access and OneFruitWorldData deferred to Phase 3/5.
public class AkumaNoMiBoxItem extends Item {
    public static final Pair<Integer, ResourceLocation> TIER_1_FRUITS =
            ImmutablePair.of(1, ResourceLocation.fromNamespaceAndPath("mineminenomi", "dfboxes/wooden_box"));
    public static final Pair<Integer, ResourceLocation> TIER_2_FRUITS =
            ImmutablePair.of(2, ResourceLocation.fromNamespaceAndPath("mineminenomi", "dfboxes/iron_box"));
    public static final Pair<Integer, ResourceLocation> TIER_3_FRUITS =
            ImmutablePair.of(3, ResourceLocation.fromNamespaceAndPath("mineminenomi", "dfboxes/golden_box"));

    private final Pair<Integer, ResourceLocation> tier;

    public AkumaNoMiBoxItem(Pair<Integer, ResourceLocation> tier) {
        super(new Item.Properties().stacksTo(1));
        this.tier = tier;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);
        if (world.isClientSide()) return InteractionResultHolder.consume(itemStack);

        // Check for key in inventory
        boolean hasKey = false;
        for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
            ItemStack slot = player.getInventory().getItem(i);
            if (!slot.isEmpty() && slot.is(net.minecraft.world.item.Items.TRIPWIRE_HOOK)) { // placeholder until ModItems.KEY is wired
                hasKey = true;
                break;
            }
        }

        if (!hasKey) {
            player.displayClientMessage(net.minecraft.network.chat.Component.translatable("item.message.need_key"), false);
            return InteractionResultHolder.fail(itemStack);
        }

        // TODO: Phase 3/5 - Query loot table and OneFruitWorldData to give a valid fruit
        player.displayClientMessage(net.minecraft.network.chat.Component.literal("(TODO) Opening box tier " + tier.getKey()), false);
        if (!player.getAbilities().instabuild) itemStack.shrink(1);
        return InteractionResultHolder.consume(itemStack);
    }

    public int getTierLevel() { return tier.getKey(); }
}
