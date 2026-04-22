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
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.init.ModDataComponents;
import xyz.pixelatedw.mineminenomi.init.ModFoods;
import xyz.pixelatedw.mineminenomi.init.ModItems;

import java.util.UUID;

public class SakeCupItem extends Item {
    public SakeCupItem() {
        super(new Item.Properties().stacksTo(1).food(ModFoods.ALCOHOL));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        if (this.getLeader(itemstack, world) != null) {
            player.startUsingItem(hand);
            return InteractionResultHolder.consume(itemstack);
        } else {
            int slot = WyHelper.getIndexOfItemStack(ModItems.SAKE_BOTTLE.get(), player.getInventory());
            if (slot >= 0) {
                ItemStack bottleStack = player.getInventory().getItem(slot);
                if (!player.getAbilities().instabuild) {
                    bottleStack.shrink(1);
                }
                this.setLeader(itemstack, player);
                return InteractionResultHolder.consume(itemstack);
            }
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

        ItemStack result = super.finishUsingItem(itemStack, world, entity);

        if (entity instanceof Player player && player.getAbilities().instabuild) {
            return result;
        }

        return new ItemStack(ModItems.SAKE_CUP.get());
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
