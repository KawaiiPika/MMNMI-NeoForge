package xyz.pixelatedw.mineminenomi.items;

import com.google.common.base.Strings;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.init.ModDataComponents;

import java.util.UUID;

public class SakeCupItem extends Item {
    public SakeCupItem() {
        // TODO: Port ModFoods.ALCOHOL
        super(new Item.Properties().stacksTo(1));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        if (this.getLeader(itemstack, world) != null) {
            player.startUsingItem(hand);
            return InteractionResultHolder.consume(itemstack);
        } else {
            // Find Sake Bottle
            // Since we haven't ported SakeBottleItem yet, we'll just check by registry name or class later.
            // For now we'll pass until SakeBottle is ported
            // TODO: Implement filling logic
        }

        return InteractionResultHolder.pass(itemstack);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack itemStack, Level world, LivingEntity entity) {
        if (!world.isClientSide() && entity instanceof Player player) {
            Player leader = this.getLeader(itemStack, player.level());
            if (leader != null) {
                // TODO: Phase 3 - Faction & Crew logic
            }

            // Give back a Sake Cup
            // player.getInventory().add(new ItemStack(ModItems.SAKE_CUP.get()));
            itemStack.shrink(1);
        }

        return itemStack;
    }

    public void setLeader(ItemStack itemStack, Player leader) {
        itemStack.set(ModDataComponents.LEADER.get(), leader.getUUID().toString());
    }

    public Player getLeader(ItemStack itemStack, Level world) {
        String leaderUUID = itemStack.getOrDefault(ModDataComponents.LEADER.get(), "");
        return !Strings.isNullOrEmpty(leaderUUID) ? world.getPlayerByUUID(UUID.fromString(leaderUUID)) : null;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.DRINK;
    }
}
